package io.sharpink.api.admin;

import io.sharpink.api.resource.quote.persistence.Quote;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class QuoteComparisonServiceTest {

    private QuoteComparisonService quoteComparisonService;

    @BeforeEach
    void setUp() {
        quoteComparisonService = new QuoteComparisonService(null);
    }

    @DisplayName("Should return TRUE when checking if two quotes are similar with two quotes having same author and same text")
    @Test
    void quoteHasSimilarAuthorAndText_StrictlyEqualQuotes() {
        // given
        Quote quote = new Quote().setAuthor("John Doe").setText("This is a test quote.");

        // when
        var quoteHasSimilarAuthorAndText = quoteComparisonService.quoteHasSimilarAuthorAndText(quote, "John Doe", "This is a test quote.");

        // then
        assertThat(quoteHasSimilarAuthorAndText).isTrue(); // they even are exactly the same!
    }

    @DisplayName("Should return TRUE when checking if two quotes are similar with two quotes having same author and very similar text")
    @Test
    void quoteHasSimilarAuthorAndText_SameAuthorAndSimilarText() {
        // given
        // Note the similarity between quotes text (only 3 characters differs)
        Quote quote = new Quote().setAuthor("John Doe").setText("This is a test quote abc.");

        // when
        var quoteHasSimilarAuthorAndText = quoteComparisonService.quoteHasSimilarAuthorAndText(quote, "John Doe", "This is a test quote def.");

        // then
        assertThat(quoteHasSimilarAuthorAndText).isTrue();
    }

    @DisplayName("Should return TRUE when checking if two quotes are similar with two quotes having similar author and similar text")
    @Test
    void quoteHasSimilarAuthorAndText_SimilarAuthorAndSimilarText() {
        // given
        Quote quote = new Quote().setAuthor("Jane Doe").setText("This is a test quote from Jane aaa");

        // when
        var quoteHasSimilarAuthorAndText = quoteComparisonService.quoteHasSimilarAuthorAndText(quote, "Jane Do", "This is a test quote from John aaz");

        // then
        assertThat(quoteHasSimilarAuthorAndText).isTrue();
    }

    @DisplayName("Should return FALSE when checking if two quotes are similar with two quotes having similar author and different text")
    @Test
    void quoteHasSimilarAuthorAndText_SameAuthorAndDifferentText() {
        // given
        Quote quote = new Quote().setAuthor("Jane Doe").setText("This is a test quote from John aaa");

        // when
        var quoteHasSimilarAuthorAndText = quoteComparisonService.quoteHasSimilarAuthorAndText(quote, "Jane Do", "A quote which is totally different!");

        // then
        assertThat(quoteHasSimilarAuthorAndText).isFalse();
    }

}
