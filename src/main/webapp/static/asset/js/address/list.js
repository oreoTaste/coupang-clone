import {checkCart, setGoButtons} from '../component/searchHeader.js';
import {makeCommas, deleteCommas, setTel} from "../component/price_quantity.js";
import {setAddressRegistration, setAddressChange, setAddressDelete} from "../component/buttons.js";

function init() {
    setTel();
    checkCart();
    setGoButtons();
    setAddressRegistration();
    setAddressChange();
    setAddressDelete();
}
init();