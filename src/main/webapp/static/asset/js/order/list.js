import {checkCart, setGoButtons} from '../component/searchHeader.js';
import {makeCommas, deleteCommas} from "../component/price_quantity.js";
import {setAddressRegistration} from "../component/buttons.js";
import {setDeliveryStatus, setDeliveryStatusShowButton} from "../component/mycoupang.js";

function setSaveToCart() {

    if(document.querySelector(".save-to-cart") != null) {
        document.querySelectorAll(".save-to-cart").forEach(el =>
            el.addEventListener("click", (e) => {
                e.preventDefault();
                const productId = e.target.parentNode.querySelector(".productId").innerText;
                fetch(
                    '/order/shoppingCart/id=' + productId + '&quantity=' + 1, {
                    method: 'POST'
                }).then((response) => {
                    alert("장바구니에 추가되었습니다");
                });
            })
        );
    }
}

function init() {
    // 장바구니 상품수 카운트
    checkCart();
    // 로그인 로그아웃 버튼 설정
    setGoButtons();
    // 장바구니 담기 버튼 설정
    setSaveToCart();
    // 가격 태그 콤마 찍기
    document.querySelectorAll(".js-price").forEach(el => el.innerText = makeCommas(el.innerText));
    setAddressRegistration();
    setDeliveryStatus();
    setDeliveryStatusShowButton();
}

init();