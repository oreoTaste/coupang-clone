const joysticks = document.querySelectorAll(".joystick"),
      bannerImg = document.querySelector(".main-banner__banners"),
      prices = document.querySelectorAll(".js-price"),
      id = document.querySelector("#idStorage");
//      email = document.querySelector("#emailStorage");

// 배너 변경관련
const setBanner = () => {
    [...joysticks].forEach(el => el.firstChild.addEventListener("mouseover", changeImg));
}
const changeImg = (e) => {
    const text = e.target.src.slice(0, e.target.src.indexOf("_")) + ".jpg";
    bannerImg.style.background = "url(" + text + ")";
}
// 1000단위 콤마 관련
const makeCommas = (x) => {
    return x.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
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
    setSession();
    checkIfOrdered();
    setBanner();
    prices.forEach(el => el.innerText = makeCommas(el.innerText));

}

init();