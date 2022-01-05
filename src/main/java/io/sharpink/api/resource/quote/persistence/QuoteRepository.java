package io.sharpink.api.resource.quote.persistence;

import org.springframework.data.mongodb.repository.MongoRepository;

/**
 * Only simple queries (using derivation or @Query syntax) should go here.
 * Custom complex queries which need to use MongoTemplate / MongoOperations should instead be :
 * - declared in CustomQuoteRepository
 * - implemented in QuoteRepositoryImpl
 */
public interface QuoteRepository extends MongoRepository<Quote, String>, CustomQuoteRepository {

}
