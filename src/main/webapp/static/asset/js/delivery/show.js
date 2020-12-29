import {checkCart, setGoButtons} from '../component/searchHeader.js';
import {makeCommas, deleteCommas, setTel} from '../component/price_quantity.js';

const prices = document.querySelectorAll(".js-price");

const setComma = () => {
    [...prices].forEach(el => el.innerText = makeCommas(el.innerText));
}
function init() {
    checkCart();
    setGoButtons();
    setComma();
    setTel();
}
init();