package io.sharpink.api.admin;

import io.sharpink.api.resource.quote.QuoteDTO;
import io.sharpink.api.resource.quote.service.QuoteService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Arrays;
import java.util.List;
import java.util.stream.IntStream;

@Controller
@RequestMapping("/admin")
@Slf4j
public class QuotesManagementController {

    private final QuoteManagementService quoteManagementService;
    private final QuoteService quoteService;
    private final QuoteComparisonService quoteComparisonService;

    public QuotesManagementController(QuoteManagementService quoteManagementService, QuoteService quoteService, QuoteComparisonService quoteComparisonService) {
        this.quoteManagementService = quoteManagementService;
        this.quoteService = quoteService;
        this.quoteComparisonService = quoteComparisonService;
    }

    @GetMapping("")
    public String showQuotesManagementPage(
        @RequestParam(value = "authorSort", required = false) String authorSort,
        @RequestParam(value = "page", required = false) Integer pageNumber,
        @RequestParam(value = "size", required = false) Integer pageSize,
        Model model)
    {
        authorSort = "desc".equals(authorSort) ? "desc": "asc";
        pageNumber = (pageNumber != null) ? pageNumber : 1;
        pageSize = (pageSize != null) ? pageSize : 15;

        model.addAttribute("initialAuthorSort", authorSort);

        var quotesPage = quoteService.getAllQuotesPaginated(authorSort, pageNumber, pageSize);
        model.addAttribute("quotesPage", quotesPage);
        int totalPages = quotesPage.getTotalPages();
        if (totalPages > 0) {
            List<Integer> pageNumbers = IntStream.rangeClosed(1, totalPages).boxed().toList();
            model.addAttribute("pageNumbers", pageNumbers);
        }

        // we need to provide on the page an empty QuoteDTO object, which will be used in the quote creation form
        model.addAttribute("newQuoteDto", new QuoteDTO());

        return "manage-quotes";
    }

    @PostMapping("/add-quote")
    public String addQuote(@ModelAttribute QuoteDTO quoteDto, BindingResult result, Model model, RedirectAttributes redirectAttributes) {
        var potentialDuplicates = quoteComparisonService.getPotentialDuplicates(quoteDto);
        if (potentialDuplicates.isEmpty()) {
            quoteService.createOrUpdateQuote(quoteDto);
        } else {
            // the most common use case will be size = 1 but we prefer handling a general case of N suspicious duplicates
            log.warn("Nouvelle citation non insérée à cause de la présence d'un doublon potentiel");
            log.warn("Liste des doublons : " + Arrays.toString(potentialDuplicates.toArray()));
            redirectAttributes.addFlashAttribute("potentialDuplicates", potentialDuplicates);
        }

        return "redirect:/admin";
    }

    @PostMapping("/update-quote")
    public String updateQuote(@ModelAttribute QuoteDTO quoteDto, BindingResult result, Model model) {
        quoteService.createOrUpdateQuote(quoteDto);
        return "redirect:/admin";
    }

    @PostMapping("/remove-quote/{id}") // Thymeleaf seems to have issues dealing with DELETE (unless it's just me ??? ^^)
    public String removeQuote(@PathVariable("id") String quoteId) {
        quoteService.removeQuote(quoteId);
        return "redirect:/admin";
    }

    @GetMapping("/import-quotes")
    public String showQuotesImportPage(Model model) {
        return "import-quotes";
    }

    @PostMapping("import-quotes")
    public String importQuotes(@RequestParam MultipartFile file) {
        quoteManagementService.importQuotesFromCsv(file);
        return "redirect:/admin";
    }

    @PostMapping("/export-csv")
    public String exportCsv() {
        quoteManagementService.exportQuotesToCsv();
        return "redirect:/admin";
    }

}
