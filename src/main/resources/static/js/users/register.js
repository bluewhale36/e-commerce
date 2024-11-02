/**
 * 유효성 검사 시 정규식 활용하도록 하는 method 를 추가하는 함수.
 */
$.validator.addMethod("regex", function(value, element, regexp) {
    const regex = new RegExp(regexp);
    return regex.test(value);
});

/**
 * 유효성 검사 시 비밀번호 동일 여부 확인하도록 하는 method 를 추가하는 함수.
 */
$.validator.addMethod("isPswdEqual", function(value, element) {
    return value === $('input#pswd-text').val();
});

/**
 * 유효성 검사 시 아이디 중복 체크 여부 확인하도록 하는 method 를 추가하는 함수.
 */
$.validator.addMethod("isDuplicateIdChecked", function(value, element) {
    return $('#duplicate-id-checked').val() === 'true';
});

/**
 * 유효성 검사 시 이메일 중복 체크 여부 확인하도록 하는 method 를 추가하는 함수.
 */
$.validator.addMethod("isDuplicateEmailChecked", function(value, element) {
    return $('#duplicate-email-checked').val() === 'true';
});

/**
 * 유효성 검사 시 이메일 인증 여부 확인하도록 하는 method 를 추가하는 함수.
 */
$.validator.addMethod("isEmailVerified", function(value, element) {
    return $('#email-verified').val() === 'true';
});


/**
 * 등록 form 유효성 정의.
 */
$('form#regi-form').validate({
    rules: {
        userId: {
            required: true,
            minlength: 6,
            maxlength: 13,
            regex: /^(?=.*[a-zA-Z])(?=.*\d)[a-zA-Z]{1}[a-zA-Z\d]{5,12}$/,
            isDuplicateIdChecked: true
        },
        exPswd: {
            required: true,
            minlength: 8,
            maxlength: 16,
            regex: /^(?=.*[a-z])(?=.*[A-Z])(?=.*\d)[a-zA-Z]{1}[a-zA-Z\d\!\@\*\?\~]{7,15}$/
        },
        password: {
            required: true,
            isPswdEqual: true
        },
        email: {
            required: true,
            maxlength: 250,
            email: true,
            regex: /^(?=.{1,250})[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*@[0-9a-zA-Z]([-_\.]?[0-9a-zA-Z])*\.[a-zA-Z]{2,3}$/,
            isDuplicateEmailChecked: true,
            isEmailVerified: true
        },
        verificationCode: {
            required: true,
            regex: /^[0-9A-Z]{7}$/
        },
        name: {
            required: true,
            minlength: 2,
        },
        tel: {
            required: true,
            regex: /^(01[016789]{1})-?[0-9]{3,4}-?[0-9]{4}$/
        }
    },
    messages: {
        userId: {
            required: "아이디를 입력하세요.",
            minlength: "6글자 이상, 13글자 이하로 입력해야 합니다.",
            maxlength: "6글자 이상, 13글자 이하로 입력해야 합니다.",
            regex: "영어 대문자 또는 소문자, 숫자를 각각 포함해야 합니다. 영어로 시작해야 합니다.",
            isDuplicateIdChecked: "아이디 중복 확인을 진행하세요."
        },
        exPswd: {
            required: "비밀번호를 입력하세요.",
            minlength: "8글자 이상, 16글자 이하로 입력해야 합니다.",
            maxlength: "8글자 이상, 16글자 이하로 입력해야 합니다.",
            regex: "영어 대문자와 소문자, 숫자를 각각 포함해야 합니다. 특수문자는 선택이며, 영어로 시작해야 합니다."
        },
        password: {
            required: "비밀번호를 다시 입력하세요.",
            isPswdEqual: "비밀번호가 맞지 않습니다."
        },
        email: {
            required: "이메일을 입력하세요.",
            maxlength: "250자 이하로 입력해야 합니다.",
            email: "정확한 이메일 형식을 입력해야 합니다.",
            regex: "정확한 이메일 형식을 입력해야 합니다.",
            isDuplicateEmailChecked: "이메일 인증을 진행하세요.",
            isEmailVerified: "인증을 확인하세요."
        },
        verificationCode: {
            required: "인증 코드를 입력하세요.",
            regex: "정확한 인증 코드 형식을 입력해야 합니다."
        },
        name: {
            required: "이름을 입력하세요.",
            minlength: "이름은 2글자 이상 입력해야 합니다.",
            maxlength: "이름은 20글자 이하로 입력해야 합니다."
        },
        tel: {
            required: "전화번호를 입력하세요.",
            regex: "전화번호 형식에 맞추어 입력해야 합니다."
        }
    },
    onkeyup: false,
    onfocusout: function (element) {
        $(element).valid();
    },
    errorPlacement: function (error, element) {
        $(element).closest('div.regi-info').find('.invalid-feedback').text($(error).text());
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
            $(element).closest('div.regi-info').find('.invalid-feedback').text('');
        }
    },
    submitHandler: function (form) {
        const formData = new FormData(form);
        const csrfHeader = $("meta[name='_csrf_header']").attr("content");
        const csrfToken = $("meta[name='_csrf']").attr("content");
        $.ajax({
            url: '/users/join',
            method: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function() {
                alert('join completed.');
                location.href="/users/login";
            },
            error: function(xhr) {
                alert(xhr.responseText);
            }
        });
    }
});

/**
 * User Id 중복 여부 확인 요청.
 * @param element 중복 확인 버튼 element.
 */
function chkDuplicatedUserId(element) {
    const btn = $(element);
    const idElement = $('#id-text');
    idElement.rules("remove", "isDuplicateIdChecked");

    /*
        input 에 올바른 형식으로 값이 입력되어 있을 경우.
     */
    if (idElement.valid()) {
        btn.attr('disabled', true);
        btn.text('확인 중');

        const csrfHeader = $("meta[name='_csrf_header']").attr("content");
        const csrfToken = $("meta[name='_csrf']").attr("content");

        $.ajax({
            url: '/users/dup/userid',
            method: 'POST',
            data: {
                userId: idElement.val()
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function(result) {
                const dupIdFlag = $('#duplicate-id-checked');
                if (result === 0) {
                    alert('사용 가능한 아이디 입니다.');
                    dupIdFlag.val(true);
                    btn.text('확인 완료');
                } else {
                    alert('중복된 아이디 입니다. 다른 아이디를 입력하세요.');
                    dupIdFlag.val(false);
                    btn.text('중복 확인');
                }
            },
            error: function(xhr) {
                alert(xhr.responseText);
            },
            complete: function() {
                idElement.rules("add", { isDuplicateIdChecked: true });
                idElement.valid();
                btn.attr('disabled', false);
            }
        });
    } else {
        idElement.rules("add", { isDuplicateIdChecked: true });
    }
}


/**
 * Email 중복 여부 확인
 * @param element 인증번호 전송 버튼.
 */
function chkDuplicatedEmail(element) {

    const btn = $(element);
    const emailElement = $('#email-text');
    // 중복 이메일 확인 여부 및 이메일 인증 여부 유효성 일시 제거.
    emailElement.rules("remove", "isDuplicateEmailChecked isEmailVerified");

    /*
        input 에 값이 입력되어 있으며, 이메일 형식에 맞을 경우.
     */
    if (emailElement.valid()) {

        // 인증번호 전송 버튼 UI 변경.
        btn.attr('disabled', true);
        btn.text('확인 중');

        const csrfHeader = $("meta[name='_csrf_header']").attr("content");
        const csrfToken = $("meta[name='_csrf']").attr("content");

        $.ajax({
            url: '/users/dup/email',
            method: 'POST',
            data: {
                email: emailElement.val()
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function(result) {
                // form 내 input:hidden 태그. email 중복 체크 여부 true/false 문자열 저장.
                const dupEmailFlag = $('#duplicate-email-checked');

                // 중복된 이메일 없을 경우 0, 있을 경우 1 이상 반환.
                if (result === 0) {
                    dupEmailFlag.val(true);
                    // 해당 이메일에 인증 코드 전송.
                    sendVerificationCode(btn);
                } else {
                    alert ('이미 가입된 이메일입니다. 다른 이메일로 시도하세요.');
                    dupEmailFlag.val(false);
                    // 인증번호 전송 버튼 UI 변경.
                    btn.attr('disabled', false);
                    btn.text('인증');
                }
            },
            error: function(xhr) {
                alert(xhr.responseText);
                // 인증번호 전송 버튼 UI 변경.
                btn.attr('disabled', false);
                btn.text('인증');
            },
            complete: function() {

                // 제거했던 유효성 재설정 및 유효성 확인.
                emailElement.rules("add", {
                    isDuplicateEmailChecked: true,
                    isEmailVerified: true
                });
                emailElement.valid();
            }
        })
    } else {

        // 제거했던 유효성 다시 설정.
        emailElement.rules("add", {
            isDuplicateEmailChecked: true,
            isEmailVerified: true
        });
    }

}

/**
 * 이메일 인증 코드 전송 요청.
 * @param element 인증번호 전송 버튼.
 */
function sendVerificationCode(element) {
    const btn = $(element);
    const emailElement = $('#email-text');
    // 이메일 인증 여부 유효성 일시 제거.
    emailElement.rules("remove", "isEmailVerified");

    /*
        input 에 이메일 형식에 맞는 값이 입력되어 있으며, 중복 가입 여부가 확인된 경우.
     */
    if (emailElement.valid()) {
        btn.attr('disabled', true);
        btn.text('전송 중');
        const csrfHeader = $("meta[name='_csrf_header']").attr("content");
        const csrfToken = $("meta[name='_csrf']").attr("content");
        $.ajax({
            url: '/email',
            method: 'POST',
            data: {
                email: emailElement.val()
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (response) {
                alert('인증 번호가 전송되었습니다.\n재전송은 1분 뒤 가능합니다.');

                const div = $('div.email-verification');
                div.removeClass('verification-before');
                div.removeClass('verification-completed');
                div.addClass('verification-ongoing');
                btn.text('전송 완료');

                setTimeout(function () {
                    btn.attr('disabled', false);
                    btn.text('인증번호');
                }, 60000);
            },
            error: function (xhr) {
                // verification.exception.MailNotSentException, ...
                alert(xhr.responseText + '\n잠시 후 다시 시도해주세요.');
            },
            complete: function() {
                // 제거한 유효성 재설정 및 유효성 확인.
                emailElement.rules("add", { isEmailVerified: true });
                emailElement.valid();
            }
        });
    } else {
        // 제거한 유효성 재설정.
        emailElement.rules("add", { isEmailVerified: true });
    }
}

/**
 * 이메일 인증 코드 인증 요청
 * @param element 인증 코드 인증 요청 버튼
 */
function verifyEmail(element) {
    const btn = $(element);
    // 인증 코드 작성하는 input:text element.
    const codeElement = $('#verification-code-text');
    const emailElement = $('#email-text');

    /*
        인증코드가 작성되어 있으며, 그 형식이 올바른 경우.
     */
    if (codeElement.valid()) {
        // 인증 버튼 UI 변경.
        btn.attr('disabled', true);
        btn.text('확인 중');

        const csrfHeader = $("meta[name='_csrf_header']").attr("content");
        const csrfToken = $("meta[name='_csrf']").attr("content");

        $.ajax({
            url: '/email/code',
            method: 'POST',
            data: {
                code: codeElement.val(),
                email: emailElement.val()
            },
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function (result) {
                // 인증 완료 시 true 반환. 실패 시 Exception 발생.
                if (result) {
                    alert('인증되었습니다.');
                    $('#email-verified').val('true');
                    emailElement.valid();
                    codeElement.valid();
                }
            },
            error: function (xhr) {
                // VerificationInfoNotFoundException, CodeNotMatchException, ...
                alert(xhr.responseText);
            },
            complete: function() {
                btn.attr('disabled', false);
                btn.text('인증');
            }
        })
    }
}

/**
 * 전화번호 하이픈 자동 추가.
 * @param element 전화번호 입력 input element.
 */
function telHyphen(element) {
    element.value = element.value
        .replace(/[^0-9]/g, '')
        .replace(/^(\d{3})(\d{0,4})(\d{0,4})$/g, "$1-$2-$3")
        .replace(/(\-{1,2})$/g, "");
}


/**
 * input#id-text onchange 시 아이디 중복 체크 여부 초기화.
 */
function resetDupIdChk() {

    // ID 중복 확인 여부 저장의 input:hidden element.
    $('#duplicate-id-checked').val(false);

    // ID 중복 확인 버튼.
    $('#id-dup-chk-btn').val('중복 확인');
}

/**
 * input#pswd-text onchange 시 비밀번호 확인 input element 유효성 재검사.
 */
function pswdChanged() {

    // 비밀번호 재입력 input:password element 유효성 재검사.
    $('#pswd-chk-text').valid();
}

/**
 * input#email-text 및 input#verification-code-text onchange 시 인증 여부 초기화.
 */
function emailChanged() {

    // email 중복 확인 여부 저장의 input:hidden element.
    $('#duplicate-email-checked').val(false);

    // email 인증 여부 저장의 input:hidden element.
    $('#email-verified').val(false);

    // 인증 코드 전송 요청 버튼.
    $('#verification-req-btn').text('인증번호');
}