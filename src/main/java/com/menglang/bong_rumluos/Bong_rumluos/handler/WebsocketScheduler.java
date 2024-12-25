package com.menglang.bong_rumluos.Bong_rumluos.handler;

import com.menglang.bong_rumluos.Bong_rumluos.services.asyncTask.ProcessComingPayment;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;

@Component
public class WebsocketScheduler {
    private static final Logger log = LoggerFactory.getLogger(WebsocketScheduler.class);

    private final ProcessComingPayment processComingPayment;

    public WebsocketScheduler(ProcessComingPayment processComingPayment) {
        this.processComingPayment = processComingPayment;
    }


//    @Scheduled(fixedRate = 10000)
    @Scheduled(cron = "0 0 6 * * ?") //calling every day on 6AM
    void sendPeriodicMessages() throws IOException {
        log.debug("invoke scheduler...................................");
        String message = "Scheduled message: " + LocalDateTime.now();
        processComingPayment.execute();

    }
}
