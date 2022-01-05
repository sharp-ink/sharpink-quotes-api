
function updateQuote(quoteId) {
    // gets modifications on current quote from the DOM
    const quote = {
        id: quoteId,
        author: $(`#quote_${quoteId}_author`).val(),
        text: $(`#quote_${quoteId}_text`).val(),
        from: $(`#quote_${quoteId}_from`).val(),
    };
    $.post('http://localhost:8081/quotes-api/admin/update-quote', quote, response => {
        window.location.reload(true);
    });
}

function removeQuote(quoteId) {
    $.post(`http://localhost:8081/quotes-api/admin/remove-quote/${quoteId}`, response => {
        window.location.reload(true);
    });
}