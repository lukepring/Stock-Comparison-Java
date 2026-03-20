package com.sharecomparison.presentation;

import java.time.LocalDate;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;

import com.sharecomparison.application.IPriceController;
import com.sharecomparison.domain.ComparisonResult;

@Controller
public class WebPageController {

    private final IPriceController priceController;

    public WebPageController(IPriceController priceController) {
        this.priceController = priceController;
    }

    @GetMapping("/")
    public String showForm(Model model) {
        model.addAttribute("symbol1", "AAPL");
        model.addAttribute("symbol2", "MSFT");
        model.addAttribute("startDate", LocalDate.now().minusYears(1).toString());
        model.addAttribute("endDate", LocalDate.now().toString());
        return "index";
    }

    @GetMapping("/compare")
    public String compare(
            @RequestParam String symbol1,
            @RequestParam String symbol2,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate startDate,
            @RequestParam(required = false) @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate endDate,
            Model model) {

        LocalDate start = (startDate != null) ? startDate : LocalDate.now().minusYears(1);
        LocalDate end = (endDate != null) ? endDate : LocalDate.now();

        symbol1 = symbol1.trim().toUpperCase();
        symbol2 = symbol2.trim().toUpperCase();

        if (start.isAfter(end)) {
            model.addAttribute("error", "Start date must be on or before end date.");
            model.addAttribute("symbol1", symbol1);
            model.addAttribute("symbol2", symbol2);
            model.addAttribute("startDate", start.toString());
            model.addAttribute("endDate", end.toString());
            return "index";
        }

        ComparisonResult result = priceController.comparePrices(symbol1, symbol2, start, end);

        if (result.getSymbol1Data().isEmpty() || result.getSymbol2Data().isEmpty()) {
            model.addAttribute("error", "No data available for one or both symbols in this range.");
            model.addAttribute("symbol1", symbol1);
            model.addAttribute("symbol2", symbol2);
            model.addAttribute("startDate", start.toString());
            model.addAttribute("endDate", end.toString());
            return "index";
        }

        model.addAttribute("result", result);
        model.addAttribute("symbol1", symbol1);
        model.addAttribute("symbol2", symbol2);
        model.addAttribute("startDate", start.toString());
        model.addAttribute("endDate", end.toString());

        return "index";
    }

    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public String handleDateParseError(MethodArgumentTypeMismatchException ex, Model model) {
        String paramName = ex.getName();
        if (LocalDate.class.equals(ex.getRequiredType())
                && ("startDate".equals(paramName) || "endDate".equals(paramName))) {
            model.addAttribute("error",
                    "Invalid date format for '" + paramName + "'. Please use YYYY-MM-DD (e.g. 2024-01-15).");
        } else {
            model.addAttribute("error", "Invalid value for parameter '" + paramName + "'.");
        }
        model.addAttribute("symbol1", "AAPL");
        model.addAttribute("symbol2", "MSFT");
        model.addAttribute("startDate", LocalDate.now().minusYears(1).toString());
        model.addAttribute("endDate", LocalDate.now().toString());
        return "index";
    }
}
}
