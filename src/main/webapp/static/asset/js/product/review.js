import {checkCart, setGoButtons} from '../component/searchHeader.js';
import {makeCommas, deleteCommas} from '../component/price_quantity.js';

const writeReviewButtons = document.querySelectorAll(".js-write-review"),
      starRatings = document.querySelectorAll(".js-star-rating"),
      reviewTab = document.querySelector(".review-tabs");

let url = "";

/*-----------------초기화 설정 시작-------------------*/
function initialize() {
    // 장바구니 상품수 카운트
    checkCart();
    // 로그인 로그아웃 버튼 설정
    setGoButtons();
}
/*-----------------초기화 설정 끝-------------------*/

/*-----------------구매후기 쓰기버튼 설정 시작-------------------*/
function setWriteReviewButtons() {
    [...writeReviewButtons].forEach(el => el.addEventListener("click", setWriteReview))
}
const setWriteReview = (e) => {
    const orderId = e.target.parentNode.querySelector(".orderId").value;
    window.location.href = "/productreview/register/" + orderId;
}
/*-----------------구매후기 쓰기버튼 설정 끝-------------------*/

/*-----------------별표 구현 설정 시작-------------------*/
function setStartRatings() {
    [...starRatings].forEach(el => setStar(el, el.querySelector("span").innerText));

}
const setStar = (el, val) => {
    let cnt = parseInt(val);
    let left = 5 - cnt;
    while(cnt > 0) {
        cnt--;
        const img = document.createElement("span");
        img.classList.add("yellow-star");
        el.appendChild(img);
    }
    while(left > 0) {
        left--;
        const img = document.createElement("span");
        img.classList.add("gray-star");
        el.appendChild(img);
    }
}
/*-----------------별표 구현 설정 끝-------------------*/

/*-----------------구매후기 상단탭 전환기능 시작-------------------*/
function setReviewTab() {
    const reviewTabs = reviewTab.children;
    [...reviewTabs].forEach(el => el.style.cursor = "pointer");
    reviewTabs[0].addEventListener("click", showReviewable);
    reviewTabs[1].addEventListener("click", showMyComment);
}
const showReviewable = () => {
    const products = document.querySelectorAll("section.product");
    const wrapperChiildren = document.querySelector(".section-wrapper").children;
    [...wrapperChiildren].forEach(el => el.classList.add("hidden"));
    products.forEach(el => el.classList.remove("hidden"));
}
const showMyComment = () => {
    const comments = document.querySelectorAll(".mycomment");
    const wrapperChiildren = document.querySelector(".section-wrapper").children;
    [...wrapperChiildren].forEach(el => el.classList.add("hidden"));
    comments.forEach(el => el.classList.remove("hidden"));
}
/*-----------------구매후기 상단탭 전환기능 끝-------------------*/

function init() {
    initialize();
    setWriteReviewButtons();
    setStartRatings();
    setReviewTab();
}
init();