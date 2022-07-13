package com.nxthing.taxiservice.controller;

import com.nxthing.taxiservice.dto.DraftOrderDto;
import com.nxthing.taxiservice.dto.OrderDto;
import com.nxthing.taxiservice.dto.PrepareOrderDto;
import com.nxthing.taxiservice.dto.PublishOrderDto;
import com.nxthing.taxiservice.entity.*;
import com.nxthing.taxiservice.service.*;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Objects;

@RestController
@RequestMapping("/api/orders")
@CrossOrigin("*")
public class OrderController {
    private final OrderService orderService;

    private final DraftOrderService draftOrderService;

    private final OrderPriceCalculationService orderPriceCalculationService;

    private final OrderPublishService orderPublishService;

    private final DriverPublishService driverPublishService;

    private final TariffService tariffService;

    private final DriverService driverService;

    private final CustomerService customerService;

    private final UserService userService;

    public OrderController(OrderService orderService,
                           DraftOrderService draftOrderService,
                           OrderPriceCalculationService orderPriceCalculationService,
                           OrderPublishService orderPublishService,
                           DriverPublishService driverPublishService,
                           TariffService tariffService,
                           DriverService driverService,
                           CustomerService customerService,
                           UserService userService) {
        this.orderService = orderService;
        this.draftOrderService = draftOrderService;
        this.orderPriceCalculationService = orderPriceCalculationService;
        this.orderPublishService = orderPublishService;
        this.driverPublishService = driverPublishService;
        this.tariffService = tariffService;
        this.driverService = driverService;
        this.customerService = customerService;
        this.userService = userService;
    }

//    @GetMapping("/get-all")
//    public List<Order> getAll() {
//        return orderService.getAll();
//    }

    @GetMapping("get-active")
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    public List<OrderDto> getActive() {
        return orderService.getActive().stream().map(OrderDto::map).toList();
    }

    @PostMapping("/prepare")
    @PreAuthorize("!hasRole('ROLE_DRIVER')")
    public DraftOrderDto prepare(@AuthenticationPrincipal UserDetailsImpl user, @RequestBody PrepareOrderDto prepareOrderDto) {
        Map<String, Double> prices = orderPriceCalculationService.calculatePrices(
                prepareOrderDto.getOrigin(),
                prepareOrderDto.getDestination(),
                prepareOrderDto.getDistance(),
                prepareOrderDto.getDuration(),
                prepareOrderDto.getPaymentMethod(),
                tariffService.getAll()
        );

        return DraftOrderDto.map(draftOrderService.create(prepareOrderDto, customerService.getByUserName(user.getUsername()), prices));
    }

    @PostMapping("/publish")
    @PreAuthorize("!hasRole('DRIVER')")
    public OrderDto publish(@AuthenticationPrincipal UserDetailsImpl user, @RequestBody PublishOrderDto publishOrderDto) {
        DraftOrder draftOrder = draftOrderService.getById(publishOrderDto.getId());

        // TODO: make check below in @PreAuthorize

        if (!Objects.equals(user.getId(), draftOrder.getCustomer().getId())) {
            throw new SecurityException();
        }

        if (customerService.hasActiveOrders(draftOrder.getCustomer())) {
            throw new RuntimeException();
        }

        Order order = orderService.save(draftOrder, tariffService.getByName(publishOrderDto.getTariff()));

        orderPublishService.publish(order, driverService.getActiveWithTariff(order.getTariff()));

        return OrderDto.map(order);
    }

    @GetMapping("/get-prices")
    public Map<String, Double> getPrices(@RequestParam String origin,
                                         @RequestParam String destination,
                                         @RequestParam Double distance,
                                         @RequestParam Double duration,
                                         @RequestParam PaymentMethod paymentMethod) {
        return orderPriceCalculationService.calculatePrices(origin, destination, distance, duration, paymentMethod, tariffService.getAll());
    }

    @PostMapping("/accept")
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @Transactional
    public OrderDto accept(@AuthenticationPrincipal UserDetailsImpl user, @RequestParam Long id) {
        Driver driver = driverService.getByUsername(user.getUsername());

        Order order = orderService.accept(id, driver);

        driver.setStatus(DriverStatus.ON_THE_WAY);
        driver = driverService.save(driver);

        driverPublishService.publish(driver, order.getCustomer().getUsername());
        orderPublishService.delete(order, driverService.getActive());

        return OrderDto.map(order);
    }

    @PostMapping("/start-wait")
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @Transactional
    public OrderDto waitStart(@AuthenticationPrincipal UserDetailsImpl user, @RequestParam Long id) {
        Driver driver = driverService.getByUsername(user.getUsername());

        Order order = orderService.startWait(id, driver);

        driver.setStatus(DriverStatus.WAITING);
        driverService.save(driver);

        orderPublishService.publishStartWaitingEvent(order.getCustomer().getUsername());

        return OrderDto.map(order);
    }

    @PostMapping("/stop-wait")
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @Transactional
    public OrderDto waitStop(@AuthenticationPrincipal UserDetailsImpl user, @RequestParam Long id) {
        Driver driver = driverService.getByUsername(user.getUsername());

        Order order = orderService.stopWait(id, driver);

        driver.setStatus(DriverStatus.ON_THE_WAY);
        driverService.save(driver);

        orderPublishService.publishStopWaitingEvent(order.getCustomer().getUsername());

        return OrderDto.map(order);
    }

    @PostMapping("/done")
    @PreAuthorize("hasRole('ROLE_DRIVER')")
    @Transactional
    public OrderDto doneOrder(@AuthenticationPrincipal UserDetailsImpl user, @RequestParam Long id) {
        Driver driver = driverService.getByUsername(user.getUsername());

        Order order = orderService.doneOrder(id, driver);

        order = orderPriceCalculationService.calculateTotalPrice(order);
        order.setEndTime(LocalDateTime.now());

        order = orderService.save(order);

        driver = driverService.addBalance(driver, order.getPrice() - order.getCommission());
        driver.setStatus(DriverStatus.LOOKING_FOR_ORDER);
        driverService.save(driver);

        orderPublishService.publishDoneOrderEvent(order);

        return OrderDto.map(order);
    }

    @PostMapping("/cancel")
    @Transactional
    public OrderDto cancelOrder(@AuthenticationPrincipal UserDetailsImpl user, @RequestParam Long id) {

        Order order = orderService.getById(id);

        order.setStatus(OrderStatus.CANCELLED);

        Driver driver = order.getDriver();

        if (driver != null) {
            driver.setStatus(DriverStatus.LOOKING_FOR_ORDER);
            driver = driverService.save(driver);
        }

        order = orderService.save(order);

        orderPublishService.publishCancelOrderEvent(order);

//        String role = user.getAuthorities().stream().toList().get(0).getAuthority();
//

//
//        if (Objects.equals(role, RoleName.ROLE_CUSTOMER.name())) {
//            switch (order.getStatus()){
//                case PREPARING -> {
//                }
//                case LOOKING_FOR_DRIVER -> {
//                }
//                case IN_PROGRESS -> {
//                }
//                case WAITING -> {
//                }
//                case DONE, CANCELLED -> {
//                    throw new RuntimeException();
//                }
//            }
//        }
//
//        Order order = orderService.cancelOrder(order, user)
//
//        return OrderDto.map(order);
//    }

        return OrderDto.map(order);
    }

    @GetMapping("/get-all")
    public List<OrderDto> getAll(@AuthenticationPrincipal UserDetailsImpl user) {
        String role = user.getAuthorities().stream().toList().get(0).getAuthority();

        if (Objects.equals(role, "ROLE_CUSTOMER")) {
            return orderService.getAllByCustomerUsername(user.getUsername()).stream().map(OrderDto::map).toList();
        } else if (Objects.equals(role, "ROLE_DRIVER")) {
            return orderService.getAllByDriverUsername(user.getUsername()).stream().map(OrderDto::map).toList();
        } else {
            throw new RuntimeException();
        }
    }
}
