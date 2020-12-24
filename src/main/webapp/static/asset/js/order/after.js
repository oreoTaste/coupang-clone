import {checkCart, setGoButtons} from '../component/searchHeader.js';
import {makeCommas, deleteCommas} from '../component/price_quantity.js';

function init() {
    // 장바구니 상품수 카운트
    checkCart();
    // 천단위 세팅
    document.querySelectorAll("js-price").forEach(el => el.innerText = makeCommas(el.innerText));
    // 로그인 로그아웃 버튼 설정
    setGoButtons();
}
init();