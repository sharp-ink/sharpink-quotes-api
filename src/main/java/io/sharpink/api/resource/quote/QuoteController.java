package io.sharpink.api.resource.quote;

import io.sharpink.api.resource.quote.service.QuoteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/quotes")
public class QuoteController {

    private final QuoteService quoteService;

    @Autowired
    public QuoteController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("")
    public List<QuoteDTO> getAllQuotes() {
        return quoteService.getAllQuotes(null);
    }

    @GetMapping("random-quote")
    public QuoteDTO getRandomQuote() {
        return quoteService.getRandomQuote();
    }
}
