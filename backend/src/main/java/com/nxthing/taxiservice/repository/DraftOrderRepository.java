package com.nxthing.taxiservice.repository;

import com.nxthing.taxiservice.entity.DraftOrder;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;

public interface DraftOrderRepository extends JpaRepository<DraftOrder, Long> {
    void deleteAllByDateBefore(LocalDateTime expirationDate);
}