package uk.ac.roehampton.stockscope.interfaces.api;

import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import uk.ac.roehampton.stockscope.application.service.SharePriceService;
import uk.ac.roehampton.stockscope.domain.model.DailyPrice;
import uk.ac.roehampton.stockscope.domain.model.ShareSymbol;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/prices")
public class SharePriceController {

    private final SharePriceService sharePriceService;

    public SharePriceController(SharePriceService sharePriceService) {
        this.sharePriceService = sharePriceService;
    }

    @GetMapping
    public List<DailyPrice> getPrices(
            @RequestParam String symbol,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate from,
            @RequestParam @DateTimeFormat(iso = DateTimeFormat.ISO.DATE) LocalDate to
    ) {
        return sharePriceService.getDailyPrices(new ShareSymbol(symbol), from, to);
    }
}