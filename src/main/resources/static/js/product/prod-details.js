$(() => {
    prodCont();
})

/*
    현재까지는, image/* 타입인 prodcont 에 대해서 정상적으로 이미지가 화면에 출력되도록 구현한 상태.
    FileData.java 의 download method 에 큰 수정이 있음.
    다만, PDF 나 HTML 등 image/* 가 아닌 다른 타입의 blob 은 <img> 태그에서 출력할 수 없으므로,
    이에 대한 추가 로직을 구현할 필요가 있음.
 */
function prodCont() {
    const contNames = $('#div-prod-cont input[type=hidden]');
    contNames.each((i, el) => {
        downloadFile($(el).val());
    });
}

function downloadFile(filename) {
    if (filename != null) {
        console.log('download');

        const csrfHeader = $("meta[name='_csrf_header']").attr("content");
        const csrfToken = $("meta[name='_csrf']").attr("content");
        $.ajax({
            url: `/api/file/file/${filename}`,
            type: 'GET',
            beforeSend: function(xhr) {
                xhr.setRequestHeader(csrfHeader, csrfToken);
            },
            /*
                AJAX 요청의 XMLHttpRequest 객체에 직접 설정할 값을 지정한다.
                responseType: 'blob' 은, XMLHttpRequest 객체의 responseType 속성을 'blob' 으로 설정한다.
                이는, Server 에서 반환하는 데이터의 타입을 정의한다.
                Blob 이란, 불변의 데이터로써 주로 파일 내용을 나타내는 데에 사용된다.
                서버에서 반환된 파일 데이터를 client 에서 사용할 수 있도록 한다.
             */
            xhrFields: {
                responseType: "blob"
            },
            success: function(result) {
                console.log('succeeded');
                console.log(result);
                console.log(typeof(result));
                printProdCont(filename, result);
            },
            error: function() {
                alert('error');
            }
        });
    }
}

function printProdCont(filename, data) {
    let url = URL.createObjectURL(data);
    console.log(filename);

    let addingElement;
    let extension = filename.split(".").pop();
    if (extension === 'pdf') {
        addingElement = `<embed type="application/pdf" src="${url}" width="300" height="500" />`
    } else if (extension === 'html') {
        addingElement = `<embed type="text/html" src="${url}" width="300" height="500" />`
    } else {
        addingElement = `<img src="${url}" alt="product-cont">`;
    }


    $('#div-prod-cont').append(addingElement);

}