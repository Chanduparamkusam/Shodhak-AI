package com.ecomguard.util;

import com.ecomguard.model.Review;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Arrays;
import java.util.List;

public class ReviewAnalyzer {

    private static final List<String> suspiciousKeywords = Arrays.asList(
            "best product ever", "must buy", "highly recommended", "5 stars", 
            "excellent", "worth every penny", "top-notch", "technical excellence", 
            "flawless engineering", "AI-powered", "cloud-native", "seamless integration"
    );

    public static boolean isSuspicious(Review review, LocalDate purchaseDate) {
        if (review.getTimestamp() == null || review.getReviewText() == null) return true;

        boolean tooQuick = ChronoUnit.HOURS.between(
                purchaseDate.atStartOfDay(), 
                review.getTimestamp().toLocalDate().atStartOfDay()
        ) < 24;

        boolean keywordAbuse = suspiciousKeywords.stream()
                .anyMatch(k -> review.getReviewText().toLowerCase().contains(k.toLowerCase()));

        boolean tooShort = review.getReviewText().length() < 15;

        return tooQuick || keywordAbuse || tooShort;
    }
}

