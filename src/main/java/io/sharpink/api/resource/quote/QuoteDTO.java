package io.sharpink.api.resource.quote;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

@Getter
@Setter
@Accessors(chain = true)
public class QuoteDTO {
    private String id;
    private String author;
    private String text;
    private String from; // the original text from which the quote comes from (can be a book, a poem,...)
    private String language; // 'en / 'fr'
}
