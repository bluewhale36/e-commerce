const userCode = $('input#text-usercode').val();
$(() => {
    $('form#form-user-details').on('submit', (e) => {
        e.preventDefault();
    });
});


function goBackPage() {
    history.back();
}

$('form#form-user-details').validate({
    rules: {
        roleList: {
            required: true
        }
    },
    messages: {
        roleList: {
            required: "권한은 하나 이상 존재해야 합니다."
        }
    },
    onfocusout: function (element) {
        $(element).valid();
    },
    errorPlacement: function (error, element) {
        $(element).closest('div.col-12').find('.invalid-feedback').text($(error).text());
    },
    submitHandler: function(form) {
        modifyUserData(form);
    }
});

function modifyUserData(form) {
    const formData = $(form).serialize();
    const csrfHeader = $("meta[name='_csrf_header']").attr("content");
    const csrfToken = $("meta[name='_csrf']").attr("content");

    $.ajax({
        url: `/admin/users/details/${userCode}`,
        type: 'PUT',
        data: formData,
        beforeSend: function(xhr) {
            xhr.setRequestHeader(csrfHeader, csrfToken);
        },
        success: function() {
            goBackPage();
        },
        error: function() {
            alert("잠시 후 다시 시도해주십시오.");
            location.reload();
        }
    });
}