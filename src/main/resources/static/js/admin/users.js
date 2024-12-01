const currentPageNum = Number($('span#span-current-page').text()) -1;

function getNextPage(e) {
    $(e).attr('disabled', true);
    location.href=`/admin/users?page=${currentPageNum + 1}`;
}

function getPreviousPage(e) {
    $(e).attr('disabled', true);
    location.href=`/admin/users?page=${currentPageNum - 1}`;
}

function goTo(e) {
    $(e).attr('disabled', true);
    location.href=`/admin/users?page=${$(e).text() - 1}`;
}