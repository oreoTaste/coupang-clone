import {setCart} from './component/searchHeader.js';
import {makeCommas, deleteCommas} from './component/price_quantity.js';

const joysticks = document.querySelectorAll(".joystick"),
      bannerImg = document.querySelector(".main-banner__banners"),
      id = document.querySelector("#idStorage"),
      prices = document.querySelectorAll(".js-price");
//      email = document.querySelector("#emailStorage");

// 배너 변경관련
const setBanner = () => {
    [...joysticks].forEach(el => el.firstChild.addEventListener("mouseover", changeImg));
}
const changeImg = (e) => {
    const text = e.target.src.slice(0, e.target.src.indexOf("_")) + ".jpg";
    bannerImg.style.background = "url(" + text + ")";
}
// 로그인하기전 바로결제하기 상품이 있는지 확인
const checkIfOrdered = () => {
    const products = sessionStorage.getItem("products");
    if(products) {
        window.location = "product/checkout/"+products;
    }
}
// 로그인 후, id 기록
const setSession = () => {
    sessionStorage.setItem("id", id.value);
}


function init() {
    setCart();
    prices.forEach(el => el.innerText = makeCommas(el.innerText));
    setSession();
    checkIfOrdered();
    setBanner();

}

init();