import {checkCart, setGoButtons} from '../component/searchHeader.js';
import {makeCommas, deleteCommas} from '../component/price_quantity.js';

let url = "";

/*-----------------초기화 설정 시작-------------------*/
function initialize() {
    // 장바구니 상품수 카운트
    checkCart();
    // 천단위 세팅
    setComma();
    // 로그인 로그아웃 버튼 설정
    setGoButtons();
}
const setComma = () => {
    [...prices].forEach(el => el.innerText = makeCommas(el.innerText));
}
/*-----------------초기화 설정 끝-------------------*/

function init() {
    initialize();
}
init();