package io.sharpink.api.admin;

import io.sharpink.api.resource.quote.QuoteDTO;
import io.sharpink.api.resource.quote.persistence.Quote;
import io.sharpink.api.resource.quote.persistence.QuoteRepository;
import org.apache.commons.text.similarity.LevenshteinDistance;
import org.springframework.stereotype.Service;

import java.util.List;

import static java.lang.Math.min;

@Service
public class QuoteComparisonService {

    private final QuoteRepository quoteRepository ;

    public QuoteComparisonService(QuoteRepository quoteRepository) {
        this.quoteRepository = quoteRepository;
    }

    // Threshold percentage to determine if we should consider two strings being similar (< N% => similar strings, >= N% => different strings)
    private static final double AUTHOR_DIFFERENT_THRESHOLD_PERCENTAGE = 20.0; // authors are considered similar below a 20% difference
    private static final double TEXT_DIFFERENT_THRESHOLD_PERCENTAGE = 20.0; // quotes texts are considered similar below a 20% difference

    /**
     * Retrieves quotes that are potential duplicates of the given quote (duplicate author/text). <br/>
     * Note : an eventually duplicate quote is only <i>potential</i> because there is a <i>similarity</i> between it and the given quote.
     * @param quoteDto the quote for which we want to check for duplicates.
     * @return an empty list if there is no suspicious matching, or a list with all potential quotes if there are some.
     */
    public List<Quote> getPotentialDuplicates(QuoteDTO quoteDto) {
        var existingQuotes = quoteRepository.findAll();
        return existingQuotes.stream()
            .filter(quote -> quoteHasSimilarAuthorAndText(quote, quoteDto.getAuthor(), quoteDto.getText()))
            .toList();
    }

    protected boolean quoteHasSimilarAuthorAndText(Quote quote, String author, String text) {
        return computeLevenshteinDistancePercentage(quote.getAuthor(), author) <= AUTHOR_DIFFERENT_THRESHOLD_PERCENTAGE
            && computeLevenshteinDistancePercentage(quote.getText(), text) <= TEXT_DIFFERENT_THRESHOLD_PERCENTAGE;
    }

    private double computeLevenshteinDistancePercentage(String str1, String str2) {
        double editDistance = new LevenshteinDistance().apply(str1, str2);
        return min(editDistance / str1.length(), editDistance / str2.length()) * 100;
    }

}
