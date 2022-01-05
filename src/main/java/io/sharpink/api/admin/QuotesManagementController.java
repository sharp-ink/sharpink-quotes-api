package io.sharpink.api.admin;

import io.sharpink.api.resource.quote.QuoteDTO;
import io.sharpink.api.resource.quote.service.QuoteService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/admin")
public class QuotesManagementController {

    private final QuoteService quoteService;

    public QuotesManagementController(QuoteService quoteService) {
        this.quoteService = quoteService;
    }

    @GetMapping("")
    public String showQuotesManagementPage(Model model) {
        var quotes = quoteService.getAllQuotes();
        model.addAttribute("quotes", quotes);
        // we need to provide on the page an empty QuoteDTO object, which will be used in the quote creation form
        model.addAttribute("newQuoteDto", new QuoteDTO());
        return "manage-quotes";
    }

    @PostMapping("/add-quote")
    public String addQuote(@ModelAttribute QuoteDTO quoteDto, BindingResult result, Model model) {
        quoteService.createOrUpdateQuote(quoteDto);
        return "redirect:/admin";
    }

    @PostMapping("/update-quote")
    public String updateQuote(@ModelAttribute QuoteDTO quoteDto, BindingResult result, Model model) {
        quoteService.createOrUpdateQuote(quoteDto); // TODO
        return "redirect:/admin";
    }

    @PostMapping("remove-quote/{id}") // Thymeleaf seems to have issues dealing with DELETE (unless it's just me ??? ^^)
    public String removeQuote(@PathVariable("id") String quoteId) {
        quoteService.removeQuote(quoteId);
        return "redirect:/admin";
    }

}
