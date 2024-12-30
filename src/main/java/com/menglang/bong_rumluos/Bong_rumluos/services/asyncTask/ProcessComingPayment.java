package com.menglang.bong_rumluos.Bong_rumluos.services.asyncTask;

import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.loanDetails.LoanDetailMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.loan.loanDetails.LoanDetailsResponse;
import com.menglang.bong_rumluos.Bong_rumluos.repositories.LoanDetailsRepository;
import com.menglang.bong_rumluos.Bong_rumluos.services.webSokcetPushData.WebSocketDataPusher;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.stream.Stream;

@Service
@Transactional(readOnly = true)
public class ProcessComingPayment {

    private static final int BATCH_SIZE = 100;  // Adjust batch size as needed
    private static final Logger log = LoggerFactory.getLogger(ProcessComingPayment.class);
    private final Lock lock = new ReentrantLock(); // Lock to ensure thread safety
    private final Object clientAckLock = new Object(); // Synchronize client acknowledgment
    private boolean isClientReady = true;  // Track client acknowledgment
    @Autowired
    private LoanDetailsRepository loanDetailsRepository;

    @Autowired
    private WebSocketDataPusher webSocketDataPusher;

    @Autowired
    private LoanDetailMapper loanDetailMapper;

    public void execute() {
        log.info("Invoke execute data...");
        Sort sort= Sort.by(Sort.Direction.DESC,"repaymentDate");
        Pageable pageable= PageRequest.of(0,50,sort);
        List<LoanDetailsResponse> batch = new ArrayList<>();
        Stream<LoanDetailsResponse> dataStream = loanDetailsRepository.getLoanDetailsUpComingPayment(pageable).stream().map(loanDetailMapper::toLoanResponse);

//        try {
//            Iterator<LoanDetailsResponse> iterator = dataStream.iterator();
//            log.info(" iterator: {}", iterator.next().getId());
//            while (iterator.hasNext()) {
//                if (filterComingDateRePayment(iterator.next().getRepaymentDate()))
//                    batch.add(iterator.next());
//
//                // If the batch reaches the batch size or we've processed all data
//                if (batch.size() == BATCH_SIZE || !iterator.hasNext()) {
//                    sendBatchAndWaitForAcknowledgment(batch);
//                    batch.clear(); // Reset batch after sending
//                }
//            }
//        } catch (InterruptedException e) {
//            Thread.currentThread().interrupt();
//            log.error("Task interrupted: " + e.getMessage());
//        } catch (Exception e) {
//            log.error("Error occurred while processing loan details: " + e.getMessage());
//        }
    }

    private void sendBatchAndWaitForAcknowledgment(List<LoanDetailsResponse> batch) throws InterruptedException {
        // Ensure client is ready to receive the batch
        while (!isClientReady) {
            log.info("Waiting for client acknowledgment...");
            synchronized (clientAckLock) {
                clientAckLock.wait(5000);  // Wait for client acknowledgment for up to 3 seconds
            }
        }

        // Send the batch via WebSocket
        webSocketDataPusher.pushDataToClient("/topic/message", batch);
        isClientReady = false;  // Wait for acknowledgment before sending the next batch
    }

    // This method will be invoked from client acknowledgment
    public void handleAcknowledgment() {
        synchronized (clientAckLock) {
            isClientReady = true;  // Client is ready for the next batch
            clientAckLock.notify(); // Notify the sending thread to continue
        }
    }

    private boolean filterComingDateRePayment(LocalDate repaymentDate) {
        LocalDate today = LocalDate.now();
        int isComingDate = (int) ChronoUnit.DAYS.between(today, repaymentDate);
        log.info(" day : {} date :{}", isComingDate, repaymentDate);
        return isComingDate <= 3;//filter date before 3days or after
    }
}