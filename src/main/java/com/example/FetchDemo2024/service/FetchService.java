package com.example.FetchDemo2024.service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Map;
import java.util.Objects;
import java.util.UUID;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import com.example.FetchDemo2024.model.BadReceiptException;
import com.example.FetchDemo2024.model.BadTimeException;
import com.example.FetchDemo2024.model.DuplicateReceiptException;
import com.example.FetchDemo2024.model.IdResponse;
import com.example.FetchDemo2024.model.Item;
import com.example.FetchDemo2024.model.PointsResponse;
import com.example.FetchDemo2024.model.Receipt;
import com.example.FetchDemo2024.model.ReceiptNotFoundException;
import com.example.FetchDemo2024.util.DateTimeHelper;
import com.example.FetchDemo2024.util.KeyGenerator;
import com.example.FetchDemo2024.util.PointsCalculator;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class FetchService {

    // in memory map for tracking points. This map is a bean created at runtime. 
    @Autowired
    public Map<String, Integer> pointsMap;

    @Autowired
    private PointsCalculator pointsCalculator;

    /* 
     * Method for processing receipts and assigning a point value to a randomly generated UUID
     * Also does date validation and null item field validations
     * @Params Receipt Object 
     * @Returns IdResponse Object
     */
    public IdResponse processReceipt(Receipt receipt) {
        String hashedReceiptKey = KeyGenerator.generateUniqueReceiptKey(receipt);
        if (pointsMap.containsKey(hashedReceiptKey)) {
            throw new DuplicateReceiptException();
        }
        int points = calculateTotalReceiptPoints(receipt);
        
        log.info(String.format("Final Value of points for id receipt %s is %s", hashedReceiptKey, String.valueOf(points)));
        pointsMap.put(hashedReceiptKey, points);
        return new IdResponse(hashedReceiptKey);
    }

    /*
     * Get points of corresponding reciept ID
     */
    public PointsResponse getReceiptPoints(String id) {
        if (pointsMap.get(id) == null) {
            throw new ReceiptNotFoundException();
        }
        return new PointsResponse(pointsMap.get(id));
    }
    
    public int calculateTotalReceiptPoints(Receipt receipt) {
        int totalPoints = 0;

        totalPoints += pointsCalculator.calculateAlphaNumericPoints(receipt);
        totalPoints += pointsCalculator.calculateRoundDollarPoints(receipt);
        totalPoints += pointsCalculator.calculateMultipleTotalPoints(receipt);
        totalPoints += pointsCalculator.calculateTwoItemPoints(receipt);
        totalPoints += pointsCalculator.calculateTrimmedPoints(receipt);
        totalPoints += pointsCalculator.calculateDatePoints(receipt);

        return totalPoints;
    }
}
