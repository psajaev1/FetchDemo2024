package com.example.FetchDemo2024.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="Cannot submit duplicate receipts")
public class DuplicateReceiptException extends RuntimeException {
    
}
