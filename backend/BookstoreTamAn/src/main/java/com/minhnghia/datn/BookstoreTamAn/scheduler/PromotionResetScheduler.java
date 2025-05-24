package com.minhnghia.datn.BookstoreTamAn.scheduler;

import com.minhnghia.datn.BookstoreTamAn.service.impl.BookService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class PromotionResetScheduler {
    private final BookService bookService;

    @Scheduled(cron = "0 0 0 * * ?")
    public void resetExpiredPromotionsDaily() {
        bookService.resetExpiredPromotions();
        System.out.println("✅ Đã reset khuyến mãi hết hạn lúc 00:00");
    }
}
