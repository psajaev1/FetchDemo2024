package com.example.FetchDemo2024.util;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Objects;

import com.example.FetchDemo2024.model.BadReceiptException;
import com.example.FetchDemo2024.model.Receipt;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class PointsCalculator {

    @Value("${points.alphaNumeric}")
    private double alphaNumericPoints;

    @Value("${points.roundDollar}")
    private double roundDollarPoints;

    @Value("${points.multipleTotal}")
    private double multipleTotalPoints;

    @Value("${points.twoItems}")
    private double twoItemPoints;

    @Value("${points.trimmed}")
    private double trimmedPoints;

    @Value("${points.oddDay}")
    private double oddDayPoints;

    @Value("${points.purchaseTime}")
    private double purchaseTimePoints;

    public int calculateAlphaNumericPoints(Receipt receipt) {
        long countAlphaNumeric = receipt.getRetailer().chars().filter(Character::isLetterOrDigit).count();
        return (int) (countAlphaNumeric * alphaNumericPoints);
    }

    public int calculateRoundDollarPoints(Receipt receipt) {
        double purchaseTotal = Double.parseDouble(receipt.getTotal());
        return purchaseTotal % 1 == 0 ? (int) roundDollarPoints : 0;
    }

    public int calculateMultipleTotalPoints(Receipt receipt) {
        double purchaseTotal = Double.parseDouble(receipt.getTotal());
        return purchaseTotal % 0.25 == 0 ? (int) multipleTotalPoints : 0;
    }

    public int calculateTwoItemPoints(Receipt receipt) {
        int itemPairs = receipt.getItems().size() / 2;
        return itemPairs * (int) twoItemPoints;
    }

    public int calculateTrimmedPoints(Receipt receipt) {
        return receipt.getItems().stream()
            .map(item -> {
                if (Objects.isNull(item.shortDescription()) || Objects.isNull(item.price())) {
                    throw new BadReceiptException();
                }
                String description = item.shortDescription();
                return (description.trim().length() % 3 == 0) ?
                    (int) Math.ceil(Double.parseDouble(item.price()) * trimmedPoints) : 0;
            }).mapToInt(Integer::intValue).sum();
    }

    public int calculateDatePoints(Receipt receipt) {
        int points = 0;
        LocalDate dateByDay = DateTimeHelper.isValidLocalDate(receipt.getPurchaseDate());
        LocalTime dateByHour = DateTimeHelper.isValidLocalTime(receipt.getPurchaseTime());

        if (dateByDay != null && dateByDay.getDayOfMonth() % 2 == 1) {
            points += oddDayPoints;
        }
        if (dateByHour != null && dateByHour.getHour() < 16 &&
            ((dateByHour.getHour() == 14 && dateByHour.getMinute() > 0) || dateByHour.getHour() == 15)) {
            points += purchaseTimePoints;
        }
        return points;
    }
}
