/* 바로 html에서 사용하는 경우
const prices = document.querySelectorAll(".js-price");
function init() {
    prices.forEach(el => el.innerText = makeCommas(el.innerText));
}
init();
*/

// import해서 사용하는 경우
// 1000단위 콤마 관련
const makeCommas = (x) => {
    return x.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",").split(".")[0];
}
const deleteCommas = (x) => {
    return x.replace(',', '');
}
export {makeCommas, deleteCommas};


