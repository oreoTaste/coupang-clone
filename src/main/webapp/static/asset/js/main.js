import {setCart, setGoToLogin, setGoToLogout, checkCart} from './component/searchHeader.js';
import {makeCommas, deleteCommas} from './component/price_quantity.js';

const joysticks = document.querySelectorAll(".joystick"),
      bannerImg = document.querySelector(".main-banner__banners"),
      loginStorage = document.querySelector("#loginStorage"),
      prices = document.querySelectorAll(".js-price");

/*-------------- 초기화 시작 --------------*/
function initialize() {
    // 로그인화면으로
    setGoToLogin();
    // 로그아웃화면으로
    setGoToLogout();
    // 콤마 설정
    prices.forEach(el => el.innerText = makeCommas(el.innerText));
    // 배너 그림 변환 설정
    setBanner();
}
const setBanner = () => {
    [...joysticks].forEach(el => el.firstChild.addEventListener("mouseover", changeImg));
}
const changeImg = (e) => {
    const text = e.target.src.slice(0, e.target.src.indexOf("_")) + ".jpg";
    bannerImg.style.background = "url(" + text + ")";
}
/*-------------- 초기화 끝 --------------*/

/*-------------- 세션에 로그인여부 기록 시작 --------------*/
function setSession() {
    if(loginStorage.value == "true") { // 로그인 상태
        console.log("세션 저장할게요 (로그인 되어있으시네요)");
        sessionStorage.setItem("login", "true");
    } else { // 로그아웃 상태
        console.log("쿠키에 tempid 저장할게요 (로그인 안되어있으시네요)");
        sessionStorage.removeItem("login");
    }
}
/*-------------- 세션에 로그인여부 기록 끝 --------------*/

/*-------------- 로그인 이후 바로결제하기 기록확인 시작 --------------*/
function checkIfOrdered() {
    if(loginStorage.value == "true") { // 로그인 상태
        const cookies = document.cookie.split(";");
        for(const cookie of cookies) {
            if(cookie.split("=")[0].trim() == 'bm_sv') {
                console.log("찾았다!!!");
                window.location = "cart/show/";
            }
        }
    }
}
/*-------------- 로그인 이후 바로결제하기 기록확인 끝 --------------*/

function init() {
    initialize();
    setSession();
    // 세션 세팅 이후 ,장바구니 확인
    checkCart();
//    checkIfOrdered();
}

init();