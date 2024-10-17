package com.example.FetchDemo2024.util;

import com.example.FetchDemo2024.model.Item;
import com.example.FetchDemo2024.model.Receipt;
import org.apache.commons.codec.digest.DigestUtils;

public class KeyGenerator {
    public static String generateUniqueReceiptKey(Receipt receipt) {
        StringBuilder sb = new StringBuilder();
        sb.append(receipt.getRetailer()).append("|")
        .append(receipt.getPurchaseDate()).append("|")
        .append(receipt.getPurchaseTime()).append("|");    

        for (Item item : receipt.getItems()) {
            sb.append(item.shortDescription()).append("|").append(item.price()).append("|");
        }
    
        // Generate a SHA-256 hash of the concatenated string
        String uniqueString = sb.toString();
        return DigestUtils.sha256Hex(uniqueString);
    }
}
