import {checkCart, setGoButtons} from '../component/searchHeader.js';
import {makeCommas, deleteCommas} from '../component/price_quantity.js';
import {setStarRatings} from '../component/stars.js';

const originalPrice = document.querySelector(".js-original-price"),
      prices = document.querySelectorAll(".js-price"),
      lowerPrice = document.querySelector(".lower-price"),
      productQuantity = document.querySelector(".js-quantity"),
      upButton = document.querySelector(".js-quantity-up"),
      downButton = document.querySelector(".js-quantity-down"),
      thumbnailPic = document.querySelector(".product-detail-header__pic"),
      magnifiedPic = document.querySelector(".magnified-product"),
      detailBody = document.querySelector(".product-detail-body"),
      content = document.querySelector(".product-detail-content"),
      spreader = document.querySelector(".product-detail-content__spreader"),
      id = document.querySelector(".product-detail-header__product-id"),
      detailForm = document.querySelector("#detailForm"),
      directOrder = document.querySelector(".directOrder"),
      shoppingCart = document.querySelector("#shoppingCart"),
      reviewSection = document.querySelector(".product-detail-review"),
      askSection = document.querySelector(".product-detail-ask"),
      infoSection = document.querySelector(".product-detail-info"),
      divBox = document.querySelector(".ask-product-page"),
      shadow = document.querySelector(".shadow-setter"),
      closePop = document.querySelectorAll(".close-pop"),
      scrollPosBuffer = 55,
      scrollMoveBuffer = 100;

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
    setGoButtons();
    setStarRatings();
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
    const children = document.querySelector(".product-detail-tabs").children;
    [...children].forEach(el => el.style.cursor = "pointer");
    children[0].addEventListener("click", moveToContent);
    children[1].addEventListener("click", moveToComment);
    children[2].addEventListener("click", moveToAsk);
    children[3].addEventListener("click", moveToInfo);
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
    content.classList.toggle("auto-height");
    if(content.classList.contains("auto-height")) {
        e.target.innerText = "상품정보 접기 ∧"
    } else {
        e.target.innerText = "상품정보 더보기 ∨"
    }
}
const moveToContent = (e) => {
    window.scrollTo({top: window.pageYOffset +
                            document.querySelector(".product-detail-content").getBoundingClientRect().top - scrollPosBuffer,
                    behavior: 'smooth'});
}
const moveToComment = (e) => {
    window.scrollTo({top: window.pageYOffset +
                            document.querySelector(".product-detail-review").getBoundingClientRect().top - scrollPosBuffer,
                    behavior: 'smooth'});
}
const moveToAsk = (e) => {
    window.scrollTo({top: window.pageYOffset +
                            document.querySelector(".product-detail-ask").getBoundingClientRect().top - scrollPosBuffer,
                    behavior: 'smooth'});
}
const moveToInfo = (e) => {
    window.scrollTo({top: window.pageYOffset +
                            document.querySelector(".product-detail-info").getBoundingClientRect().top - scrollPosBuffer,
                    behavior: 'smooth'});
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
        checkCart();
    });
}
/*-----------------주문기능 설정 끝-------------------*/

/*-----------------위치계산 설정 시작-------------------*/
function checkPositionForTabs() {
    window.addEventListener("scroll", checkPosition);
}
function checkPosition() {
    const curPos = window.scrollY + scrollMoveBuffer;
    const reviewPos = window.pageYOffset + reviewSection.getBoundingClientRect().top;
    const askPos = window.pageYOffset + askSection.getBoundingClientRect().top;
    const infoPos = window.pageYOffset + infoSection.getBoundingClientRect().top;

    const tabs = document.querySelector(".product-detail-tabs");
    [...tabs.children].forEach(el => el.classList.remove("active"));
    if(curPos < reviewPos) {
        tabs.children.item(0).classList.add("active");
    } else if(curPos < askPos) {
        tabs.children.item(1).classList.add("active");
    } else if(curPos < infoPos) {
        tabs.children.item(2).classList.add("active");
    } else {
        tabs.children.item(3).classList.add("active");
    }
}
/*-----------------위치계산 설정 끝-------------------*/

/*-----------------상품문의 버튼설정 시작-------------------*/
function setAskProductButtons() {
    setAskProduct();
    setShadow();
    closePop.forEach(el => el.addEventListener("click", askProduct));
}
const setAskProduct = () => {
    const askProductButton = document.querySelector(".js-ask-product");
    askProductButton.addEventListener("click", askProduct);
}
const setShadow = () => {
    shadow.addEventListener("click", askProduct);
}
const askProduct = (e) => {
    divBox.classList.toggle("hidden");
    shadow.classList.toggle("hidden");
}
/*-----------------상품문의 버튼설정 끝-------------------*/

/*-----------------상품평 버튼설정 시작-------------------*/
function setReviewButton() {
    const reviewButton = document.querySelector(".js-review");
    reviewButton.addEventListener("click", setReview);
}
const setReview = () => {
    window.open("/productreview/reviewable");
}
/*-----------------상품평 버튼설정 끝-------------------*/
function init() {
    initialize();
    setButton();
    setMagnifier();
    setDirectOrder();
    setShoppingCart();
    checkPositionForTabs();
    setAskProductButtons();
    setReviewButton();
}
init();