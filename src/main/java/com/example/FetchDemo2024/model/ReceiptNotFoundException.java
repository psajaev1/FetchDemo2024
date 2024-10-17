package com.example.FetchDemo2024.model;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

@ResponseStatus(value=HttpStatus.NOT_FOUND, reason="id does not correspond to receipt")
public class ReceiptNotFoundException extends RuntimeException {
    
}
