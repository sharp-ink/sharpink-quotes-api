package io.sharpink.api.resource.quote.service;

import io.sharpink.api.resource.quote.QuoteDTO;
import io.sharpink.api.resource.quote.persistence.Quote;
import org.springframework.stereotype.Service;

@Service
public class QuoteMapper {

    public QuoteDTO toQuoteDto(Quote quote) {
        return new QuoteDTO()
            .setId(quote.getId())
            .setAuthor(quote.getAuthor())
            .setText(quote.getText())
            .setFrom(quote.getFrom());
    }

    public Quote toQuote(QuoteDTO source) {
        return new Quote()
            .setId(source.getId())
            .setAuthor(source.getAuthor())
            .setText(source.getText())
            .setFrom(source.getFrom());
    }
}
