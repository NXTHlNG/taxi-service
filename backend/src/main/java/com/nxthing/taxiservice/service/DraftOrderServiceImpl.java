package com.nxthing.taxiservice.service;

import com.nxthing.taxiservice.dto.PrepareOrderDto;
import com.nxthing.taxiservice.entity.Customer;
import com.nxthing.taxiservice.entity.DraftOrder;
import com.nxthing.taxiservice.repository.DraftOrderRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Map;

@Service
public class DraftOrderServiceImpl implements DraftOrderService {

    private final DraftOrderRepository draftOrderRepository;

    public DraftOrderServiceImpl(DraftOrderRepository draftOrderRepository) {
        this.draftOrderRepository = draftOrderRepository;
    }

    @Override
    public DraftOrder create(PrepareOrderDto prepareOrderDto, Customer customer, Map<String, Double> prices) {
        return draftOrderRepository.save(DraftOrder.builder()
                .id(prepareOrderDto.getId())
                .origin(prepareOrderDto.getOrigin())
                .destination(prepareOrderDto.getDestination())
                .distance(prepareOrderDto.getDistance())
                .duration(prepareOrderDto.getDuration())
                .prices(prices)
                .date(LocalDateTime.now())
                .customer(customer)
                .paymentMethod(prepareOrderDto.getPaymentMethod())
                .build());
    }

    @Override
    public DraftOrder getById(Long id) {
        return draftOrderRepository.findById(id).orElseThrow();
    }

    @Override
    @Transactional
    public void deleteExpired(LocalDateTime expirationDate) {
        draftOrderRepository.deleteAllByDateBefore(expirationDate);
    }
}
