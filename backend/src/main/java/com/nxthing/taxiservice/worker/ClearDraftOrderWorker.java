package com.nxthing.taxiservice.worker;

import com.nxthing.taxiservice.service.DraftOrderService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class ClearDraftOrderWorker {

    private final DraftOrderService draftOrderService;

    public ClearDraftOrderWorker(DraftOrderService draftOrderService) {
        this.draftOrderService = draftOrderService;
    }

    @Scheduled(fixedDelay = 5 * 1000 * 60)
    public void clearDraftOrders() {
        draftOrderService.deleteExpired(LocalDateTime.now().minusMinutes(10));
    }

}
