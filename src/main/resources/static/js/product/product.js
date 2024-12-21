$(() => {
    $('div#prod-gallery').on('click', '.gallery-item', function(e) {
        goProductDetails($(this));
    })
})

function goProductDetails(el) {
    let code = $(el).find('input[type=hidden]').val();
    location.href = `/product/details/${code}`;
}