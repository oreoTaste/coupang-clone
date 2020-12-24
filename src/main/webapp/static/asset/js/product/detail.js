import {checkCart, setCart, setGoToLogin, setGoToLogout} from '../component/searchHeader.js';
import {makeCommas, deleteCommas} from '../component/price_quantity.js';

const originalPrice = document.querySelector(".js-original-price"),
      prices = document.querySelectorAll(".js-price"),
      lowerPrice = document.querySelector(".lower-price"),
      productQuantity = document.querySelector(".js-quantity"),
      upButton = document.querySelector(".js-quantity-up"),
      downButton = document.querySelector(".js-quantity-down"),
      thumbnailPic = document.querySelector(".product-detail-header__pic"),
      magnifiedPic = document.querySelector(".magnified-product"),
      detailBody = document.querySelector(".product-detail-body"),
      spreader = document.querySelector(".product-detail-content__spreader"),
      id = document.querySelector(".product-detail-header__product-id"),
      detailForm = document.querySelector("#detailForm"),
      directOrder = document.querySelector(".directOrder"),
      shoppingCart = document.querySelector("#shoppingCart");
let url = "";

/*-----------------초기화 설정 시작-------------------*/
function initialize() {
    // 장바구니 상품수 카운트
    checkCart();
    // form 재설정
    setFormData();
    // 천단위 세팅
    setComma();
    // 로그인 로그아웃 버튼 설정
    setGoToLogin();
    setGoToLogout();
}
const setFormData = () => {
    url = detailForm.action;
    detailForm.action = url + "&count=1";
}
const setComma = () => {
    [...prices].forEach(el => el.innerText = makeCommas(el.innerText));
}
/*-----------------초기화 설정 끝-------------------*/

/*-----------------버튼 설정 시작-------------------*/
function setButton() {
    upButton.addEventListener("click", handleUp);
    downButton.addEventListener("click", handleDown);
    spreader.addEventListener("click", handlerSpreader);
}
const handleUp = () => {
    const originalQuantity = Number(productQuantity.value);
    if(productQuantity.value < 100) {
        productQuantity.value = originalQuantity + 1;
        lowerPrice.innerText = makeCommas(Number(originalPrice.innerText) * productQuantity.value);
        detailForm.action = url + "&count=" + productQuantity.value;
    }
}
const handleDown = () => {
    if(productQuantity.value > 1) {
        productQuantity.value -= 1;
        lowerPrice.innerText = makeCommas(Number(originalPrice.innerText) * productQuantity.value)
        detailForm.action = url + "&count=" + productQuantity.value;
    }
}
const handlerSpreader = (e) => {
    e.target.setAttribute("style", "display: none");
    detailBody.setAttribute("style", "height: auto");
}
/*-----------------버튼 설정 끝-------------------*/

/*-----------------확대기능 설정 시작-------------------*/
function setMagnifier() {
    thumbnailPic.firstElementChild.addEventListener("mouseover", showBlock);
    thumbnailPic.firstElementChild.addEventListener("mouseleave", hideBlock);
    thumbnailPic.firstElementChild.addEventListener("mousemove", handlePosition);
}
const showBlock = (e) => {
    e.preventDefault();
    magnifiedPic.classList.remove("hidden");
    console.log("removed hidden")
}
const hideBlock = (e) => {
    magnifiedPic.classList.add("hidden");
}
const handlePosition = (e) => {
    magnifiedPic.firstElementChild.setAttribute("style", "object-position: -" + e.offsetX + "px -" + e.offsetY + "px");
}
/*-----------------확대기능 설정 끝-------------------*/

/*-----------------주문기능 설정 시작-------------------*/
// 1. 장바구니 담기 기능
function setShoppingCart() {
    shoppingCart.addEventListener("click", addToShoppingCart);
}
const addToShoppingCart = (e) => {
    e.preventDefault();
    pushCart();
    alert("장바구니에 추가되었습니다");
}
// 2. 바로 주문 기능 (장바구니담기 + 바로주문)
function setDirectOrder() {
    directOrder.addEventListener("click", handleOrder);
}
const handleOrder = (e) => {
    if(!sessionStorage.getItem("login")) {
        e.preventDefault();
        const items = sessionStorage.getItem("products");
        if(!items) {
            sessionStorage.setItem("products", id.value);
        }
        window.location = "/";
    }
    e.preventDefault();
    pushCart();
    document.querySelector("#detailForm").submit();
}
const pushCart = () => {
    fetch("/order/shoppingCart/id=" + id.value + "&quantity=" + productQuantity.value, {
            method: 'POST',
            mode: 'cors',
            cache: 'no-cache',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            }
    }).then((response) => {
        setCart();
    });
}
/*-----------------주문기능 설정 시작-------------------*/
function init() {
    initialize();
    setButton();
    setMagnifier();
    setDirectOrder();
    setShoppingCart();
}
init();