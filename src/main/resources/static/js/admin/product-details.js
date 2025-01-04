/**
 * 유효성 검사 시 정규식 활용하도록 하는 method 를 추가하는 함수.
 */
$.validator.addMethod("regex", function(value, element, regexp) {
    const regex = new RegExp(regexp);
    return regex.test(value);
});

$.validator.addMethod("isInteger", function(value) {
    return Number.isInteger(Number(value)) && Number.isFinite(Number(value));
})

/**
 * 상품 상위 분류 선택 여부 확인
 */
$.validator.addMethod("isProdCategorySelected", function() {
    return $('select#select-prod-category').val() !== 'none';
});

$.validator.addMethod("maxFiles", function(value, element, param) {
    return element.files.length <= param;
});

/**
 * 상품 정보 form 유효성 정의.
 */
$('form#form-product-details').validate({
    rules: {
        prodName: {
            required: true,
            minlength: 10,
            maxlength: 100,
        },
        prodCategory: {
            required: true,
            isProdCategorySelected: true
        },
        prodKind: {
            required: true
        },
        prodCnt: {
            required: true,
            min: 0,
            isInteger: true
        },
        prodPrice: {
            required: true,
            min: 1,
            isInteger: true
        },
        discntRate: {
            required: true,
            min: 0
        }
    },
    messages: {
        prodName: {
            required: "상품 명을 입력하세요.",
            minlength: "상품 명은 10글자 이상 100글자 이하입니다.",
            maxlength: "상품 명은 10글자 이상 100글자 이하입니다.",
        },
        prodCategory: {
            required: "상품 상위 분류를 선택하세요.",
            isProdCategorySelected: "상품 상위 분류를 선택하세요."
        },
        prodKind: {
            required: "상품 하위 분류를 선택하세요."
        },
        prodCnt: {
            required: "상품 개수를 입력하세요.",
            min: "상품 개수는 반드시 0 이상의 정수여야 합니다.",
            isInteger: "상품 개수는 반드시 0 이상의 정수여야 합니다."
        },
        prodPrice: {
            required: "상품 가격을 입력하세요.",
            min: "상품 가격은 반드시 양의 정수여야 합니다.",
            isInteger: "상품 가격은 반드시 양의 정수여야 합니다."
        },
        discntRate: {
            required: "할인율을 입력하세요.",
            min: "할인율은 반드시 0 이상의 수여야 합니다."
        }
    },
    onkeyup: false,
    onfocusout: function (element) {
        $(element).valid();
    },
    errorPlacement: function (error, element) {
        console.log('invalid');
        $(element).closest('.form-element').find('.invalid-feedback').text($(error).text());
    },
    highlight: function (element) {
        if (!$(element).hasClass('no-need-validation')) {
            $(element).removeClass('is-valid');
            $(element).addClass('is-invalid');
        }
    },
    unhighlight: function (element) {
        if (!$(element).hasClass('no-need-validation')) {
            $(element).removeClass('is-invalid');
            $(element).addClass('is-valid');
            $(element).closest('div').find('.invalid-feedback').text('');
        }
    },
    submitHandler: function (form) {
        const prodCode = $('#text-prodcode').val();
        const formData = $(form).serialize();
        const csrfHeader = $("meta[name='_csrf_header']").attr("content");
        const csrfToken = $("meta[name='_csrf']").attr("content");

        $.ajax({
            url: `/admin/products/details/${prodCode}`,
            type: 'PUT',
            data: formData,
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function() {
                location.href = "/admin/products"
            },
            error: function() {
                alert('잠시 후 다시 시도해주십시오');
                location.reload();
            }
        });
    }
});

$(() => {
    $('span#span-userdetail-page').on('click', function() {
        const userCode = $('input#text-userCode').val();
        location.href = `/admin/users/details/${userCode}`;
    });
    $('select#text-prodcategory').on('change', function() {
        getProdKind();
    });
    $('form').on('submit', function(e) {
        e.preventDefault();
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
