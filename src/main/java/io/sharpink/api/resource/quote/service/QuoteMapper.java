package io.sharpink.api.resource.quote.service;

import io.sharpink.api.resource.quote.QuoteDTO;
import io.sharpink.api.resource.quote.persistence.Quote;
import org.springframework.stereotype.Service;

import static org.apache.commons.lang3.StringUtils.isEmpty;

@Service
public class QuoteMapper {

    public QuoteDTO toQuoteDto(Quote quote) {
        return new QuoteDTO()
            .setId(quote.getId())
            .setAuthor(quote.getAuthor())
            .setText(quote.getText())
            .setFrom(quote.getFrom())
            .setLanguage(quote.getLanguage());
    }

    public Quote toQuote(QuoteDTO source) {
        return new Quote()
            .setId(source.getId())
            .setAuthor(source.getAuthor())
            .setText(source.getText())
            .setFrom("".equals(source.getFrom()) ? null : source.getFrom())
            .setLanguage(source.getLanguage());
    }
}
