const joysticks = document.querySelectorAll(".joystick"),
      bannerImg = document.querySelector(".main-banner__banners"),
      prices = document.querySelectorAll(".js-price"),
      id = document.querySelector("#idStorage");
//      email = document.querySelector("#emailStorage");

const changeImg = (e) => {
    const text = e.target.alt.slice(0, e.target.alt.indexOf("_")) + ".jpg";
    bannerImg.style.background = "url(../static/asset/img/banner/" + text + ")";
}

const makeCommas = (x) => {
    return x.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
}

const checkIfOrdered = () => {
    const products = sessionStorage.getItem("products");
    if(products) {
        window.location = "product/checkout/"+products;
    }
}

const setSession = () => {
    sessionStorage.setItem("id", id.value);
}

function init() {
    setSession();
    checkIfOrdered();
    [...joysticks].forEach(el => el.addEventListener("mouseover", changeImg));
    prices.forEach(el => el.innerText = makeCommas(el.innerText));
}

init();