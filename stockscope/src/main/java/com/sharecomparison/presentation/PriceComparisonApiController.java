package com.sharecomparison.presentation;

import com.sharecomparison.application.IPriceController;
import com.sharecomparison.domain.ComparisonResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDate;

@RestController
@RequestMapping("/api")
public class PriceComparisonApiController {

    private final IPriceController priceController;

    public PriceComparisonApiController(IPriceController priceController) {
        this.priceController = priceController;
    }

    @GetMapping("/compare")
    public ComparisonResult compare(
            @RequestParam String symbol1,
            @RequestParam String symbol2,
            @RequestParam(required = false) LocalDate startDate,
            @RequestParam(required = false) LocalDate endDate) {

        String s1 = symbol1 == null ? "" : symbol1.trim().toUpperCase();
        String s2 = symbol2 == null ? "" : symbol2.trim().toUpperCase();

        if (s1.isEmpty() || s2.isEmpty()) {
            throw new IllegalArgumentException("Both symbol1 and symbol2 are required.");
        }

        LocalDate effectiveEnd = endDate == null ? LocalDate.now() : endDate;
        LocalDate effectiveStart = startDate == null ? effectiveEnd.minusMonths(6) : startDate;

        if (effectiveStart.isAfter(effectiveEnd)) {
            throw new IllegalArgumentException("startDate must be on or before endDate.");
        }

        return priceController.comparePrices(s1, s2, effectiveStart, effectiveEnd);
    }
}
