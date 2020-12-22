import {setCart} from '../component/searchHeader.js';
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

// url(form링크) 초기값 세팅하기
const setFormData = () => {
    url = detailForm.action;
    detailForm.action = url + "&count=1";
}
// 천단위 콤마
const setComma = () => {
    [...prices].forEach(el => el.innerText = makeCommas(el.innerText));
}

// +, - 버튼 컨트롤
const setButton = () => {
    upButton.addEventListener("click", handleUp);
    downButton.addEventListener("click", handleDown);
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

// 확대기능
const setMagnifier = () => {
    thumbnailPic.firstElementChild.addEventListener("mouseover", showBlock);
    thumbnailPic.firstElementChild.addEventListener("mouseleave", hideBlock);
    thumbnailPic.firstElementChild.addEventListener("mousemove", handlePosition);
}
const handlePosition = (e) => {
    magnifiedPic.firstElementChild.setAttribute("style", "object-position: -" + e.offsetX + "px -" + e.offsetY + "px");
}
const showBlock = (e) => {
    e.preventDefault();
    magnifiedPic.classList.remove("hidden");
    console.log("removed hidden")
}
const hideBlock = (e) => {
    magnifiedPic.classList.add("hidden");
}

// 펼치기 기능
const setSpreader = () => {
    spreader.addEventListener("click", handlerSpreader);
}
const handlerSpreader = (e) => {
    e.target.setAttribute("style", "display: none");
    detailBody.setAttribute("style", "height: auto");
}
// 바로 주문 기능
const setDirectOrder = () => {
    directOrder.addEventListener("click", handleOrder);
}
const handleOrder = (e) => {
    if(!sessionStorage.getItem("id")) {
        e.preventDefault();
        const items = sessionStorage.getItem("products");
        if(!items) {
            sessionStorage.setItem("products", id.value);
        }
        window.location = "/";
    }
}
const setMovement = (e) => {
    e.preventDefault();
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
const setShoppingCart = () => {
    shoppingCart.addEventListener("click", setMovement);

}
function init() {
    setCart();
    setFormData();
    setComma();
    setButton();
    setMagnifier();
    setSpreader();
    setDirectOrder();
    setShoppingCart();
}

init();