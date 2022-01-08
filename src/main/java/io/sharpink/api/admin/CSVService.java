package io.sharpink.api.admin;

import io.sharpink.api.resource.quote.QuoteDTO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.LocalDateTime.now;

@Service
@Slf4j
public class CSVService {

    private final String[] HEADERS = { "author", "text", "from"};

    private final CSVFormat csvFormat = initCsvFormat();

    public void createCsv(List<QuoteDTO> quotes) throws IOException {
        var file = new File("/tmp/sharpink-quotes/quotes." + now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".csv");
        var writer = new FileWriter(file);
        try (var printer = new CSVPrinter(writer, csvFormat)) {
            for (QuoteDTO quote : quotes) {
                printer.printRecord(quote.getAuthor(), quote.getText(), quote.getFrom());
            }
            log.info("Fichier CSV créé avec succès : " + file.getAbsolutePath());
        }
    }

    private CSVFormat initCsvFormat() {
        return CSVFormat.DEFAULT.builder()
            .setHeader(HEADERS)
            .setDelimiter(";")
            .setQuoteMode(QuoteMode.ALL)
            .build();
    }

}
