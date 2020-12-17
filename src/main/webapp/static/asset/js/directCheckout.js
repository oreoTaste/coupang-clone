const prices = document.querySelectorAll(".js-price"),
      totalCost = document.querySelector(".js-total-cost"),
      productPrice = document.querySelector(".js-product-price"),
      deliveryPrice = document.querySelector(".js-delivery-price"),
      registerAddress = document.querySelector("#registerAddress");

// 천단위 콤마
const setComma = () => {
    prices.forEach(el => el.innerText = makeCommas(el.innerText));
}
const makeCommas = (x) => {
    return x.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
}
const commasToFlat = (x) => {
    return x.toString().replace(/,/g, "");
}
// 총결제금액 계산
const setTotalCost = () => {
    const dp = (Number)(commasToFlat(deliveryPrice.innerText))
    const pp = (Number)(commasToFlat(productPrice.innerText));
    totalCost.innerText = makeCommas(dp + pp);
}
// 주소 등록하기
const setAddressRegisteration = () => {
    registerAddress.addEventListener("click", () => {
        window.open('/register/address/','주소등록','width=400,height=500,location=no,status=no,scrollbars=no');
    });
}
function init() {
    setComma();
    setTotalCost();
    setAddressRegisteration();
}

init();