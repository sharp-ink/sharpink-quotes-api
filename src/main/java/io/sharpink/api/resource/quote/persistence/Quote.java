package io.sharpink.api.resource.quote.persistence;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Getter
@Setter
@Accessors(chain = true)
@Document("quotes")
public class Quote {

    @Id
    private String id;

    private String author;
    private String text;
    private String from; // the original text from which the quote comes from (can be a book, a poem,...)

    private String language; // 'en / 'fr'
}
