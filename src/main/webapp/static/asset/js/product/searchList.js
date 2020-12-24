import {setCart, setGoToLogin, setGoToLogout, checkCart} from '../component/searchHeader.js';
import {makeCommas, deleteCommas} from "../component/price_quantity.js";

const prices = document.querySelectorAll(".js-price");

function init(){
    checkCart();
    setGoToLogin();
    setGoToLogout();
    [...prices].forEach(el => el.innerText = makeCommas(el.innerText));
}
init();