package io.sharpink.api.resource.quote.service;

import io.sharpink.api.resource.quote.QuoteDTO;
import io.sharpink.api.resource.quote.persistence.Quote;
import org.springframework.stereotype.Service;

@Service
public class QuoteMapper {
    public QuoteDTO toQuoteDto(Quote quote) {
        return new QuoteDTO()
            .setAuthor(quote.getAuthor())
            .setText(quote.getText())
            .setFrom(quote.getFrom());
    }
}
