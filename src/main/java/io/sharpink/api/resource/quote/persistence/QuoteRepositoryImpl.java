package io.sharpink.api.resource.quote.persistence;

import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.stereotype.Repository;

import static org.springframework.data.mongodb.core.aggregation.Aggregation.newAggregation;

@Repository
public class QuoteRepositoryImpl implements CustomQuoteRepository {

    private final MongoTemplate mongoTemplate;

    public QuoteRepositoryImpl(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    private final String COLLECTION_QUOTES = "quotes";

    @Override
    public Quote findRandomQuote() {
        var sampleOperation = Aggregation.sample(1);
        var aggregation = newAggregation(sampleOperation);
        var aggregationResults = mongoTemplate.aggregate(aggregation, COLLECTION_QUOTES, Quote.class);
        return aggregationResults.getUniqueMappedResult();
    }
}
