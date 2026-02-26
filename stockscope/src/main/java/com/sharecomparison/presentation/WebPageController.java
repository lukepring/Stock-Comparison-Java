package com.sharecomparison.presentation;

import com.sharecomparison.application.IPriceController;
import com.sharecomparison.domain.ComparisonResult;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.time.LocalDate;

@Controller
public class WebPageController {

    private final IPriceController priceController;

    public WebPageController(IPriceController priceController) {
        this.priceController = priceController;
    }

    @GetMapping("/")
    public String index(Model model) {
        LocalDate end = LocalDate.now();
        LocalDate start = end.minusMonths(6);

        model.addAttribute("symbol1", "AAPL");
        model.addAttribute("symbol2", "MSFT");
        model.addAttribute("startDate", start);
        model.addAttribute("endDate", end);

        return "index";
    }

    @PostMapping("/compare")
    public String compare(
            @RequestParam String symbol1,
            @RequestParam String symbol2,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {

        String s1 = symbol1 == null ? "" : symbol1.trim().toUpperCase();
        String s2 = symbol2 == null ? "" : symbol2.trim().toUpperCase();

        LocalDate effectiveEnd = endDate == null ? LocalDate.now() : endDate;
        LocalDate effectiveStart = startDate == null ? effectiveEnd.minusMonths(6) : startDate;

        model.addAttribute("symbol1", s1);
        model.addAttribute("symbol2", s2);
        model.addAttribute("startDate", effectiveStart);
        model.addAttribute("endDate", effectiveEnd);

        if (s1.isEmpty() || s2.isEmpty()) {
            model.addAttribute("error", "Both symbols are required.");
            return "index";
        }

        if (effectiveStart.isAfter(effectiveEnd)) {
            model.addAttribute("error", "Start date must be on or before end date.");
            return "index";
        }

        ComparisonResult result = priceController.comparePrices(s1, s2, effectiveStart, effectiveEnd);
        model.addAttribute("result", result);

        return "index";
    }
}
