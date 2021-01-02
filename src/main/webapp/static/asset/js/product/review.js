import {checkCart, setGoButtons} from '../component/searchHeader.js';
import {makeCommas, deleteCommas} from '../component/price_quantity.js';
import {setStarRatings} from '../component/stars.js';

const writeReviewButtons = document.querySelectorAll(".js-write-review"),
      reviewTab = document.querySelector(".review-tabs");

let url = "";

/*-----------------초기화 설정 시작-------------------*/
function initialize() {
    // 장바구니 상품수 카운트
    checkCart();
    // 로그인 로그아웃 버튼 설정
    setGoButtons();
    setStarRatings();
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

/*-----------------구매후기 상단탭 전환기능 시작-------------------*/
function setReviewTab() {
    const reviewTabs = reviewTab.children;
    [...reviewTabs].forEach(el => el.style.cursor = "pointer");
    reviewTabs[0].addEventListener("click", showReviewable);
    reviewTabs[1].addEventListener("click", showMyReview);
}
const preSet = () => {
    const wrapperChildren = document.querySelector(".section-wrapper").children;
    [...wrapperChildren].forEach(el => el.classList.add("hidden"));
}
const showReviewable = (e) => {
    [...e.target.parentNode.children].forEach(el => el.classList.remove("active"));
    e.target.classList.add("active");
    const products = document.querySelectorAll("section.product");
    preSet();
    products.forEach(el => el.classList.remove("hidden"));
}
const showMyReview = (e) => {
    [...e.target.parentNode.children].forEach(el => el.classList.remove("active"));
    e.target.classList.add("active");
    const reviews = document.querySelectorAll(".myreview");
    preSet();
    reviews.forEach(el => el.classList.remove("hidden"));

    // 사진 리뷰 확대보기
    const photos = document.querySelectorAll(".myreview__review__photo-review");
    [...photos].forEach(el => el.addEventListener("click", magnifyPhoto));
}
const magnifyPhoto = (e) => {
    console.log(e.target.src);
    window.open(e.target.src,'자세히 보기','width=400,height=500,resizable=yes,scrollbars=yes,status=yes');
}
/*-----------------구매후기 상단탭 전환기능 끝-------------------*/

function init() {
    initialize();
    setWriteReviewButtons();
    setReviewTab();
}
init();