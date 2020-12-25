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

const makeHyphen = (x) => {
    switch(x.toString().length) {
        case 8:
            // 1588-1234
            return x.toString().replace(/(\d{4})(\d{4})/, '$1-$2');
        case 9:
            // 02-123-1234
            return x.toString().replace(/(\d{2})(\d{3})(\d{4})/, '$1-$2-$3');
        case 10:
            // 010-123-1234
            return x.toString().replace(/(\d{3})(\d{3})(\d{4})/, '$1-$2-$3');
        case 11:
            // 010-1234-1234
            return x.toString().replace(/(\d{3})(\d{4})(\d{4})/, '$1-$2-$3');
        default:
            return x.toString();
    }
}

const setTel = () => {
    if(document.querySelector(".js-tel")) {
        document.querySelectorAll(".js-tel").forEach(el =>
            el.innerText = makeHyphen(el.innerText)
        );
    }
}
export {makeCommas, deleteCommas, setTel};