$(() => {submit(0)});

const curPageElement = $('input#hidden-current-page');
const currentPage = curPageElement.val();

function getNextPage(e) {
    $(e).attr('disabled', true);
    submit(currentPage + 1);
}

function getPreviousPage(e) {
    $(e).attr('disabled', true);
    submit(currentPage - 1);
}

function goTo(e) {
    $(e).attr('disabled', true);
    submit($(e).text() -1);
}

function getNewSearch() {
    console.log($('#text-search-keyword').val());
    submit(0);
}

function submit(pageNum) {
    curPageElement.val(pageNum);

    const csrfHeader = $("meta[name='_csrf_header']").attr("content");
    const csrfToken = $("meta[name='_csrf']").attr("content");
    const formData = new FormData($('form#form-conditions')[0]);
    console.log(formData);
    $.ajax({
        url: '/admin/users/rest',
        type: 'GET',
        contentType: false,
        processData: false,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        data: formData,
        success: function(result) {
            console.log("result : "+ result);
            printResult(result);
        },
        error: function() {
            alert('잠시 후 다시 시도해주십시오.');
            re();
        }
    })
}

function re() {
    location.href = '/admin/users';
}

function printResult(result) {
    printUsersData(result);
    printPaginationData(result);
}

function printUsersData(data) {
    const tbody = $('table#table-users > tbody');

    tbody.html('');

    const contents = data.content;
    console.log(data);
    console.log(contents);
    for (let content of contents) {
        let roleList = content.roleList;
        let roleSize = roleList.length;
        for (let i = 0; i < roleSize; i++) {
            if (i === 0) {
                tbody.append(`<tr>
<td class="p-2" rowspan="${roleSize}">
<a class="text-primary" href="/admin/users/details/${content.userCode}">${content.userCode}</a>
</td>
<td class="p-2" rowspan="${roleSize}">${content.userId}</td>
<td class="p-2" rowspan="${roleSize}">${content.email}</td>
<td class="p-2">${roleList[i]}</td>
<td class="p-2" rowspan="${roleSize}">${content.isEnabled}</td>
<td class="p-2" rowspan="${roleSize}">${content.isLocked}</td>
</tr>`);
            } else {
                tbody.append(`<tr><td class="p-2">${roleList[i]}</td></tr>`);
            }
        }
    }
}

function printPaginationData(data) {

}