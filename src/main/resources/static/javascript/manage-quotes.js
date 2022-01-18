
function initAuthorSort(initialAuthorSort) {
    if (initialAuthorSort === 'asc') {
        $('#authorAscSortButton').hide();
        $('#authorDescSortButton').show();
    } else if (initialAuthorSort === 'desc') {
        $('#authorAscSortButton').show();
        $('#authorDescSortButton').hide();
    }
}

function filterByAuthor() {
    const author = $('#authorFilter').val();

    const url = new URL(window.location.href);
    if (author === '') {
        url.searchParams.delete('author');
    } else {
        url.searchParams.set('author', author);
    }
    window.location.href = url.href;
}

function onkeypressAuthorFilterInput(event) {
    if (event.key === 'Enter') {
        filterByAuthor();
    }
}

function resetAuthorFilter() {
    $('#authorFilter').val('');
    filterByAuthor();
}

function sortByAuthor(newAuthorSort) {
    const url = new URL(window.location.href);
    url.searchParams.set('authorSort', newAuthorSort);
    window.location.href = url.href;
}

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
