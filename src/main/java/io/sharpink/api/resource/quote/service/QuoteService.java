package io.sharpink.api.resource.quote.service;

import io.sharpink.api.resource.quote.QuoteDTO;
import io.sharpink.api.resource.quote.persistence.Quote;
import io.sharpink.api.resource.quote.persistence.QuoteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

import static java.util.Comparator.comparing;

@Service
public class QuoteService {

    private final QuoteRepository quoteRepository;
    private final QuoteMapper quoteMapper;

    @Autowired
    public QuoteService(QuoteRepository quoteRepository, QuoteMapper quoteMapper) {
        this.quoteRepository = quoteRepository;
        this.quoteMapper = quoteMapper;
    }

    public List<QuoteDTO> getAllQuotes(String authorSort) {
        boolean ascendingAuthorSort = !"desc".equals(authorSort);
        return quoteRepository.findAll().stream()
            .sorted(ascendingAuthorSort ? comparing(Quote::getAuthor) : comparing(Quote::getAuthor).reversed())
            .map(quoteMapper::toQuoteDto).toList();
    }

    public QuoteDTO getRandomQuote() {
        var randomQuote = quoteRepository.findRandomQuote();
        return quoteMapper.toQuoteDto(randomQuote);
    }

    /**
     * Create or save the given quote in the DB (if id = null => save as a new quote, otherwise update existing quote)
     */
    public void createOrUpdateQuote(QuoteDTO quoteDto) {
        var quote = quoteMapper.toQuote(quoteDto);
        quoteRepository.save(quote);
    }

    public void removeQuote(String quoteId) {
        quoteRepository.deleteById(quoteId);
    }
}
