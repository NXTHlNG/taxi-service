package com.nxthing.taxiservice.repository;

import com.nxthing.taxiservice.entity.Order;
import com.nxthing.taxiservice.entity.OrderStatus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

public interface OrderRepository extends JpaRepository<Order, Long> {
    List<Order> findAllByStatus(OrderStatus lookingForDriver);

    List<Order> findAllByCustomerUsername(String username);

    List<Order> findAllByDriverUsername(String username);
}