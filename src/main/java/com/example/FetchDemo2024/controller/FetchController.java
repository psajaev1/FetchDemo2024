package com.example.FetchDemo2024.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import com.example.FetchDemo2024.model.IdResponse;
import com.example.FetchDemo2024.model.PointsResponse;
import com.example.FetchDemo2024.model.Receipt;
import com.example.FetchDemo2024.service.FetchService;

import lombok.NonNull;
import lombok.extern.slf4j.Slf4j;

@RestController
@RequestMapping("/receipts")
@Slf4j
public class FetchController {

    @Autowired
    FetchService fetchService;
    /* 
     * POST endpoint for processing and saving a receipt 
     * @params @RequestBody Receipt
     * @response IdResponse
     */
    @PostMapping(value="/process")
    public ResponseEntity<IdResponse> saveReceipts(@NonNull @RequestBody Receipt request) {
        log.info("Saving a receipt");
        return ResponseEntity.ok().body(fetchService.processReceipt(request));
    }

    /*
     * GET endpoint for gettinga receipt point value by ID
     * @params @PathVariable id
     * @response @PointsResponse 
     */
    @GetMapping("/{id}/points")
    public ResponseEntity<PointsResponse> getPoints(@PathVariable("id") String uuid) {
        log.info("Get receipt for " + uuid);
        return ResponseEntity.ok().body(fetchService.getReceiptPoints(uuid));
    }
}
