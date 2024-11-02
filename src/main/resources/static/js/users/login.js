/**
 * $(element).validate({}) : form 의 input value 에 대한 유효성 검증하는 jQuery Plugin.<br/>
 * JSON 형식으로 유효성, 에러메세지, 콜백 함수 등을 정의.
 */
$('#login-form').validate({
    /*
        rules : 각 input 태그에 대한 유효성 정의.
        input 태그의 name 속성 값으로 지정.
     */
    rules: {
        userId: {
            required: true
        },
        password: {
            required: true
        }
    },

    /*
        messages : 유효성을 위배한 input element 에 대한 에러 메세지.
        일반적으로 유효성을 위배한 input element 바로 다음에 label.error element 가 삽입되는 형태.
        -> input + label.error
     */
    messages: {
        userId: {
            required: '아이디를 입력하세요.'
        },
        password: {
            required: '비밀번호를 입력하세요.'
        }
    },

    /*
        onkeyup : input element 에 keyup event 가 발생 할 때 유효성을 검사하는 여부.
        default : true -> input element 에 값을 입력할 때마다 유효성 검사.
     */
    onkeyup: false,

    /*
        onfocusout : input element 에 onblur event 가 발생 할 떄 유효성을 검사하는 여부.
        default : true -> input element 에 focus 를 해제할 때마다 유효성 검사.
     */
    onfocusout: function (element) {
        $(element).valid();
    },

    /*
        errorPlacement : 에러 메시지의 위치 변경.
        정의된 콜백 함수는 input element 가 유효성을 위배할 때마다 실행됨.
        @param error : label.error element
        @param element : 유효성을 위배한 input element
     */
    errorPlacement: function (error, element) {
        $(element).closest('div.login-info').find('.invalid-feedback').text($(error).text());
    },

    /*
        highlight : input element 가 유효성을 위배 했을 때 실행되는 콜백 함수.
        @param element : 유효성을 위배한 input element.
     */
    highlight: function (element) {
        $(element).removeClass('is-valid');
        $(element).addClass('is-invalid');
    },

    /*
        unhighlight : input element 가 유효성을 통과 했을 때 실행되는 콜백 함수.
        @param element : 유효성을 통과한 input element.
     */
    unhighlight: function (element) { // 유효성 통과 시
        $(element).removeClass('is-invalid');
        $(element).addClass('is-valid');
        $(element).closest('div.login-info').find('.invalid-feedback').text('');
    },

    /*
        submitHandler : form 에 대한 유효성 검증이 성공 했을 때 실행되는 콜백 함수.
        명시하지 않을 경우, 기본적으로 정의된 event 가 발생한다. (form submit)
        그러나 이를 override 할 경우 form 전송은 별도로 해야 한다.
        -> submit event 는 발생하나, 브라우저 차원에서 form data 의 전송을 막는다. (event.preventDefault() 처럼)
        -> 개발자가 별도로 form data 의 전송을 명시해야 함. (ex. form.submit(), AJAX, ...)
        @param form : submit 대상 form element.
     */
    submitHandler: function (form) {
        const formData = new FormData(form);
        const csrfHeader = $("meta[name='_csrf_header']").attr("content");
        const csrfToken = $("meta[name='_csrf']").attr("content");
        $.ajax({
            url: '/users/login',
            method: 'POST',
            data: formData,
            contentType: false,
            processData: false,
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            success: function() {
                location.href="/";
            },
            error: function(xhr) {
                alert(xhr.responseText);
            }
        });
    }
});

function goJoinPage() {
    location.href='/users/join';
}