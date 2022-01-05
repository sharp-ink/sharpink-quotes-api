package io.sharpink.api.resource.quote.service;

import io.sharpink.api.resource.quote.QuoteDTO;
import io.sharpink.api.resource.quote.persistence.Quote;
import io.sharpink.api.resource.quote.persistence.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final QuoteMapper quoteMapper;

    @Autowired
    public QuoteService(QuoteRepository quoteRepository, QuoteMapper quoteMapper) {
        this.quoteRepository = quoteRepository;
        this.quoteMapper = quoteMapper;
    }

    public List<QuoteDTO> getAllQuotes() {
        return quoteRepository.findAll().stream()
            .map(quoteMapper::toQuoteDto).toList();
    }

    public QuoteDTO getRandomQuote() {
        var randomQuote = quoteRepository.findRandomQuote();
        return quoteMapper.toQuoteDto(randomQuote);
    }

}
