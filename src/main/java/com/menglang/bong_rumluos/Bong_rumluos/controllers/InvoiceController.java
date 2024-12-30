package com.menglang.bong_rumluos.Bong_rumluos.controllers;

import com.menglang.bong_rumluos.Bong_rumluos.dto.invoice.InvoiceMapper;
import com.menglang.bong_rumluos.Bong_rumluos.dto.invoice.InvoiceRequestDTO;
import com.menglang.bong_rumluos.Bong_rumluos.dto.invoice.InvoiceResponseDTO;
import com.menglang.bong_rumluos.Bong_rumluos.dto.pageResponse.BaseResponse;
import com.menglang.bong_rumluos.Bong_rumluos.entities.Invoice;
import com.menglang.bong_rumluos.Bong_rumluos.services.invoice.InvoiceService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/invoices")
@RequiredArgsConstructor
public class InvoiceController {

    private final InvoiceService invoiceService;
    private final InvoiceMapper invoiceMapper;

    @PostMapping("/make-payment")
    public ResponseEntity<InvoiceResponseDTO> makePayment(@RequestBody InvoiceRequestDTO requestDTO) {
        return ResponseEntity.ok(invoiceService.makeInvoice(requestDTO));
    }

    @GetMapping("/get-all")
    public ResponseEntity<BaseResponse> getPayments(
            @RequestParam(name = "page", defaultValue = "1") int page,
            @RequestParam(name = "limit", defaultValue = "10") int limit,
            @RequestParam(name = "orderBy", defaultValue = "DESC") String orderBy,
            @RequestParam(name = "sortBy", defaultValue = "name") String sortBy,
            @RequestParam(name = "query", defaultValue = "") String query
    ) {
        Page<Invoice> invoicePage = invoiceService.getAllInvoices(page, limit, orderBy, sortBy, query);
        List<InvoiceResponseDTO> invoiceResponseList = invoicePage.getContent().stream().map(invoiceMapper::toResponse).toList();

        return BaseResponse.success(invoiceResponseList, invoicePage, "successful");
    }
}
