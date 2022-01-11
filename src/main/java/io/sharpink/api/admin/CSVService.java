package io.sharpink.api.admin;

import io.sharpink.api.resource.quote.QuoteDTO;
import io.sharpink.api.resource.quote.persistence.Quote;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.commons.csv.QuoteMode;
import org.springframework.stereotype.Service;

import java.io.*;
import java.time.format.DateTimeFormatter;
import java.util.List;

import static java.time.LocalDateTime.now;
import static java.util.Arrays.stream;

@Service
@Slf4j
public class CSVService {

    private final String[] HEADERS = stream(QuoteHeaders.values()).map(QuoteHeaders::name).toArray(String[]::new);

    private final CSVFormat readCsvFormat = initReadCsvFormat(); // used to import quotes from a CSV
    private final CSVFormat writeCvFormat = initWriteCsvFormat(); // used to export quotes to a CSV

    public List<Quote> getQuotesFromCsv(InputStream inputStream) throws IOException {
        return readCsvFormat.parse(new InputStreamReader(inputStream)).stream()
            .map(csvRecord -> new Quote()
                .setAuthor(csvRecord.get(QuoteHeaders.AUTHOR))
                .setText(csvRecord.get(QuoteHeaders.TEXT))
                .setFrom(csvRecord.get(QuoteHeaders.FROM))
                .setLanguage("fr"))
            .toList();
    }

    public void createCsv(List<QuoteDTO> quotes) throws IOException {
        var file = new File("/tmp/sharpink-quotes/quotes." + now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss")) + ".csv");
        var writer = new FileWriter(file);
        try (var printer = new CSVPrinter(writer, writeCvFormat)) {
            for (QuoteDTO quote : quotes) {
                printer.printRecord(quote.getAuthor(), quote.getText(), quote.getFrom());
            }
            log.info("Fichier CSV créé avec succès : " + file.getAbsolutePath());
        }
    }

    private CSVFormat initReadCsvFormat() {
        return CSVFormat.DEFAULT.builder()
            .setHeader(HEADERS)
            .setSkipHeaderRecord(true)
            .setDelimiter(";")
            .setNullString("")
            .build();
    }

    private CSVFormat initWriteCsvFormat() {
        return CSVFormat.DEFAULT.builder()
            .setHeader(HEADERS)
            .setDelimiter(";")
            .setNullString("")
            .setQuoteMode(QuoteMode.ALL_NON_NULL)
            .build();
    }

    private enum QuoteHeaders {
        AUTHOR, TEXT, FROM
    }

}
