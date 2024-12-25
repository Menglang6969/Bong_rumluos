package com.menglang.bong_rumluos.Bong_rumluos.controllers;

import com.menglang.bong_rumluos.Bong_rumluos.services.asyncTask.ProcessComingPayment;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.stereotype.Controller;


@Controller
@RequiredArgsConstructor
public class WebsocketController {
    private static final Logger log = LoggerFactory.getLogger(WebsocketController.class);
    private final ProcessComingPayment processComingPayment;

    @MessageMapping("/acknowledge")
    public void sendNotification(String message) {
        log.info("invoke sending acknowledge......");
        processComingPayment.handleAcknowledgment();
    }


}
