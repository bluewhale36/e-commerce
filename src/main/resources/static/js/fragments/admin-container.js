$(() => {
    $('div#container > nav > ul > li').on('click', function() {
        clickMenu(this);
    });
    printCurrentLocation();

});

function clickMenu(e) {
    let text = $(e).text().toLowerCase();
    console.log(text);
    if (text === 'dashboard') {
        location.href = '/admin';
    } else {
        location.href = `/admin/${text}`;
    }
}

function printCurrentLocation() {
    let url = location.href;
    let lists = $('nav > ul > li');

    for (let li of lists) {
        if (url.indexOf(li.innerText.toLowerCase()) !== -1 ||
            (li.innerText.toLowerCase() === 'dashboard' && url === 'http://localhost:8889/admin')) {
            li.classList.add('selected');
        } else {
            li.classList.remove('selected');
        }
    }
}