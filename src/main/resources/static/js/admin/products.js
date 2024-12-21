function newProduct() {
    location.href = "/admin/products/new";
}

$(() => {
    submit(0);
    $('select#select-filter-type').on('change', function() {
        controlFilters($(this));
    });
    controlFilters($('select#select-filter-type'));
});

function controlFilters(e) {
    let selectedValue = $(e).val().toString();
    console.log(selectedValue);

    let optionDivs = $('div[id^=div-filter-]');
    console.log(optionDivs);

    $.each(optionDivs, function(idx, el) {
        let id =  $(el).attr('id');

        if (id.indexOf(selectedValue) !== -1) {
            $(el).attr('class', 'col');
            $(el).find('input').removeAttr('disabled');
        } else {
            $(el).attr('class', 'col hidden visible-hidden');
            $(el).find('input').attr('disabled', true);
        }
    });
}

function getNewSearch() {
    submit(0);
}

const curPageElement = $('input#hidden-current-page');

// 다음 페이지 이동
function getNextPage(e) {
    $(e).attr('disabled', true);
    submit(curPageElement.val() + 1);
}

// 이전 페이지 이동
function getPreviousPage(e) {
    $(e).attr('disabled', true);
    submit(curPageElement.val() - 1);
}

// 특정 페이지 번호 이동
function goTo(e) {
    $(e).attr('disabled', true);
    submit($(e).text() -1);
}

function submit(pageNum) {
    curPageElement.val(pageNum);

    const csrfHeader = $("meta[name='_csrf_header']").attr("content");
    const csrfToken = $("meta[name='_csrf']").attr("content");

    const formData = $('form#form-conditions').serialize();
    console.log(formData);

    $.ajax({
        url: '/admin/products/',
        type: 'GET',
        data: formData,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function(result) {
            printResult(result);
        },
        error: function(xhr, status, error) {
            alert('잠시 후 다시 시도해주십시오.');
            re();
        }
    });
}

function re() {
    location.reload();
}

function printResult(result) {
    // ProductPagingRequestDTO#hasContent
    if (result.hasContent) {
        hasData();
        printProductData(result);
        printPaginationData(result);
    } else {
        hasNoData();
    }
}

function printProductData(data) {
    let tbody = $('table#table-product > tbody');

    tbody.html('');

    // ProductPagingRequestDTO#content
    const contents = data.content;

    console.log(contents);

    for (let content of contents) {
        tbody.append(`<tr>
<td class="p-2">
<a class="text-primary" href="/admin/products/details/${content.prodCode}">${content.prodCode}</a>
</td>
<td class="p-2">${content.prodName}</td>
<td class="p-2">${content.prodCategory}</td>
<td class="p-2">${content.prodKind}</td>
<td class="p-2">${content.prodCnt}</td>
<td class="p-2">${content.prodPrice}</td>
<td class="p-2">${content.discntRate}</td>
<td class="p-2">${content.prodStatus}</td>
<td class="p-2">${content.isEnabled}</td>
</tr>`);
    }
}

function printPaginationData(data) {
    const pagingLeft = $('div#div-paging-left');
    const pagingNumber = $('span#span-pages');
    const pagingRight = $('div#div-paging-right');

    // 페이징 관련 DOM 초기화
    pagingLeft.html('');
    pagingNumber.html('');
    pagingRight.html('');

    // 이전 페이지 버튼
    if (data.hasPrevious) {
        pagingLeft.append(`<button type="button" class="btn btn-secondary-outlined col" onClick="getPreviousPage(this)">Left</button>`);
    } else {
        pagingLeft.append(`<button type="button" class="btn btn-secondary-outlined col" disabled>Left</button>`)
    }

    // 페이지 번호 출력 (로직 수정 필요)
    for (let i = 0; i < data.totalPages; i++) {
        if (i === data.currentPage) {
            pagingNumber.append(`<span class="text-primary col" id="span-current-page" onclick="goTo(this)">${i + 1}</span>`);
        } else {
            pagingNumber.append(`<span class="text-secondary col" onclick="goTo(this)">${i + 1}</span>`);
        }
    }

    // 다음 페이지 버튼
    if (data.hasNext) {
        pagingRight.append(`<button type="button" class="btn btn-secondary-outlined col" onclick="getNextPage(this)">Right</button>`);
    } else {
        pagingRight.append(`<button type="button" class="btn btn-secondary-outlined col" disabled>Right</button>`);
    }
}

// 조회 결과 있을 경우
function hasData() {
    $('div#div-has-data').attr('class', '');
    $('div#div-has-no-data').attr('class', 'visible-hidden hidden row');
}

// 조회 결과 없을 경우
function hasNoData() {
    $('div#div-has-data').attr('class', 'visible-hidden hidden');
    $('div#div-has-no-data').attr('class', 'row');
}