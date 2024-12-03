$(() => {
    submit(0);
});

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

// 새로운 검색 조회
function getNewSearch() {
    submit(0);
}

// request 전송 및 반환 값 처리
function submit(pageNum) {
    curPageElement.val(pageNum);

    // CSRF token
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");
    const csrfToken = $("meta[name='_csrf']").attr("content");

    // form data
    // const formData = new FormData($('form#form-conditions')[0]);
    const formData = $('form#form-conditions').serialize();

    $.ajax({
        url: '/admin/users/',
        type: 'GET',
        data: formData,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function(result) {
            printResult(result);
        },
        error: function() {
            alert('잠시 후 다시 시도해주십시오.');
            re();
        }
    })
}

// 새로고침
function re() {
    location.href = '/admin/users';
}

// 결과 출력
function printResult(result) {
    printUsersData(result);
    printPaginationData(result);
}

// 사용자 정보 테이블에 출력
function printUsersData(data) {
    let tbody = $('table#table-users > tbody');

    // 테이블 데이터 초기화
    tbody.html('');

    // UsersPagingRequestDTO#content
    const contents = data.content;

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

// 페이징 정보 출력
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