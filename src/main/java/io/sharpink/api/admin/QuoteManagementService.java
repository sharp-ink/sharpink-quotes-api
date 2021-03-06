package io.sharpink.api.admin;

import io.sharpink.api.resource.quote.service.QuoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@Service
@Slf4j
public class QuoteManagementService {

    private final QuoteService quoteService;
    private final CSVService csvService;

    public QuoteManagementService(QuoteService quoteService, CSVService csvService) {
        this.quoteService = quoteService;
        this.csvService = csvService;
    }

    public void importQuotesFromCsv(MultipartFile file) {
        try {
            var quotes = csvService.getQuotesFromCsv(file.getInputStream());
            quoteService.saveQuotes(quotes);
        } catch (IOException e) {
            log.error("Erreur lors de la lecture du fichier CSV", e);
        }
    }

    public void exportQuotesToCsv() {
        var allQuotes = quoteService.getAllQuotes("asc");
        try {
            csvService.createCsv(allQuotes);
        } catch (IOException e) {
            log.error("Erreur lors de la création du fichier CSV des citations", e);
        }
    }
}
