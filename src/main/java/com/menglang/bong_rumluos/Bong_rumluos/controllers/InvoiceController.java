package com.menglang.bong_rumluos.Bong_rumluos.controllers;

import com.menglang.bong_rumluos.Bong_rumluos.dto.invoice.InvoiceRequestDTO;
import com.menglang.bong_rumluos.Bong_rumluos.dto.invoice.InvoiceResponseDTO;
import com.menglang.bong_rumluos.Bong_rumluos.services.invoice.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;

    @PostMapping("/make-payment")
    public ResponseEntity<InvoiceResponseDTO> makePayment(@RequestBody InvoiceRequestDTO requestDTO){
        return ResponseEntity.ok(invoiceService.makeInvoice(requestDTO));
    }
}
