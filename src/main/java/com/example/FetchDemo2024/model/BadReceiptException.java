package com.example.FetchDemo2024.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.BAD_REQUEST, reason="One or more required fields are missing from an item object")
public class BadReceiptException extends RuntimeException {
    
}
