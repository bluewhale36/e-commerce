
$(() => {
    $('span#span-userdetail-page').on('click', function() {
        const userCode = $('input#text-userCode').val();
        location.href = `/admin/users/details/${userCode}`;
    });
    $('select#text-prodcategory').on('change', function() {
        getProdKind();
    });
});

function goBackPage() {
    history.back();
}

function getProdKind() {
    let prodCategory = $('select#text-prodcategory').val();

    if (prodCategory !== 'none') {
        const csrfHeader = $("meta[name='_csrf_header']").attr("content");
        const csrfToken = $("meta[name='_csrf']").attr("content");
        $.ajax({
            url: '/admin/products/new-prodkind',
            method: 'GET',
            data: {
                'prodCategory': prodCategory,
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function(result) {
                console.log(result);
                printProdKind(result);
            },
            error: function(xhr, status, error) {
                alert(xhr.responseText);
            }
        });
    } else {
        clearProdKind();
    }
}

function clearProdKind() {
    let el = $('select#text-prodkind');
    $(el).html('');
    return el;
}

function printProdKind(data) {
    let el = clearProdKind();

    /*
        반환받는 Object 타입 데이터를 iterable 객체로 반환하는 method 를 통해 forEach 사용.
     */
    Object.entries(data).forEach(([key, value]) => {
        $(el).append(`<option value="${key}">${value}</option>`);
    });

}
