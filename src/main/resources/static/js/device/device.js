const delay = 50;
let timer = null;

function windowWidthInfo() {
    var w = window.innerWidth;
    var href = $('#device-template-css').attr('href');
    console.log(href);
    var curTemplate = href.split('/')[3];
    console.log(curTemplate)
    if (w > 1024) {
        href = href.replace(curTemplate, 'desktop.css');
    } else if (w < 1025 && w > 649) {
        href = href.replace(curTemplate, 'tablet.css');
    } else {
        href = href.replace(curTemplate, 'mobile.css');
    }
    $('#device-template-css').attr('href', href);
}

$(document).ready(function() {
    windowWidthInfo();
});

$(window).on('resize', function(e) {
    clearTimeout(timer);
    timer = setTimeout(function() {
        windowWidthInfo();
    }, delay);
});
