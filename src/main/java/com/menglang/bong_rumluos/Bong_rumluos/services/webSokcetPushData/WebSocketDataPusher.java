package com.menglang.bong_rumluos.Bong_rumluos.services.webSokcetPushData;

import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.loanDetails.LoanDetailsResponse;
import com.menglang.bong_rumluos.Bong_rumluos.entities.LoanDetails;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class WebSocketDataPusher {
    private final SimpMessagingTemplate simpMessagingTemplate;

    public WebSocketDataPusher(SimpMessagingTemplate simpMessagingTemplate) {
        this.simpMessagingTemplate = simpMessagingTemplate;
    }


    public void pushDataToClient(String topic, List<LoanDetailsResponse> dataChunk) {
        simpMessagingTemplate.convertAndSend(topic, dataChunk); // Push data chunk
    }
}
