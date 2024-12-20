package com.menglang.bong_rumluos.Bong_rumluos.services.invoice;

import com.menglang.bong_rumluos.Bong_rumluos.dto.invoice.InvoiceRequestByLoan;
import com.menglang.bong_rumluos.Bong_rumluos.dto.invoice.InvoiceRequestDTO;
import com.menglang.bong_rumluos.Bong_rumluos.dto.invoice.InvoiceResponseDTO;
import com.menglang.bong_rumluos.Bong_rumluos.exceptionHandler.exceptions.BadRequestException;

public interface InvoiceService {
    InvoiceResponseDTO makeInvoice(InvoiceRequestDTO requestDTO) throws BadRequestException;

    //this function use when customer buy product without loan (pay all )
    InvoiceResponseDTO makeInvoiceByLoan(InvoiceRequestByLoan dto) throws BadRequestException;
}
