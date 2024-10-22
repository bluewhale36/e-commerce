/**
 * nav 의 메뉴 버튼 누를 때 발생하는 div#menu 애니메이션 제어.
 */


// 애니메이션 동작하는 동안 버튼 이벤트 차단 및 활성화.
// 애니메이션 실행 되는 경우.
$('div#menu > div').on('animationstart', function() {
    console.log('started');
    $('#btn-menu').css('pointer-events', 'none');
});
// 애니메이션 종료 된 경우.
$('div#menu > div').on('animationend', function() {
    console.log('ended');
    $('#btn-menu').css('pointer-events', 'auto');
});



/**
 * {div#menu} 의 애니메이션 제어.<br/>
 * {div#menu} 의 class 속성이 없거나, {ani-slidein} 일 경우 메뉴 바를 드러냄.<br/>
 * {div#menu} 의 class 속성이 {ani-slideout} 일 경우 메뉴 바를 숨김.<br/>
 * {nav > btn#menu} 의 onclick event 로 동작하는 함수.
 */
function menu() {
    // 애니메이션 재활용 위한 클래스 변경 작업.
    const innerMenu = document.querySelector('div#menu > div');
    // 애니메이션 동작 클래스 변경
    if (innerMenu.classList.contains('ani-slidein')) { // 메뉴 바 나와있는 상태 (나오는 애니메이션)
        // 메뉴 바 들어가는 애니메이션으로 변경.
        innerMenu.classList.remove('ani-slidein');
        void innerMenu.offsetWidth; // 브라우저를 강제로 로딩하여 html 요소의 변화를 인식하게 함.
        innerMenu.classList.add('ani-slideout');
    } else { // 메뉴 바 들어가있는 상태 (들어가는 애니메이션 또는 클래스 없음)
        // 메뉴 바 나오는 애니메이션으로 변경.
        innerMenu.classList.remove('ani-slideout');
        void innerMenu.offsetWidth;
        innerMenu.classList.add('ani-slidein');
    }
}