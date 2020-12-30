import {checkCart, setGoButtons} from '../component/searchHeader.js';
import {makeCommas, deleteCommas} from '../component/price_quantity.js';
import {setJsHome} from '../component/buttons.js';

const setModifyButton = () => {
    const modifyBtn = document.querySelector(".js-modify");
    if(modifyBtn) {
        modifyBtn.addEventListener("click", modifyRequest);
    }
}
const modifyRequest = () => {
    const password = document.querySelector(".password-input").value;
    fetch(
        "/mycoupang/checkPassword", {
        method: 'POST',
        body: JSON.stringify({"password": password})
    }).then((resp) => {
        return resp.json();
    }).then((json) => {
        if(json.answer == "OK") {
            moveToModification();
        }
    });
}
const moveToModification = () => {
    let form = document.createElement("form");
    form.action = "/mycoupang/userModify";
    form.method = "post";
    document.body.appendChild(form);
    form.submit();
}
function executeModification() {
    if(document.querySelector(".modify-password")) {
        const modifyPasswordBtn = document.querySelector(".modify-password");
        modifyPasswordBtn.addEventListener("click", execute);
    }
}
const execute = (e) => {
    const parent = e.target.parentNode.parentNode;
    const curPas = parent.querySelector(".cur-password").value;
    const newPas = parent.querySelector(".new-password").value;
    const curPasRepeat = parent.querySelector(".new-password-repeat").value;
    fetch(
        "/mycoupang/checkPassword", {
        method: 'POST',
        body: JSON.stringify({"password": curPas})
    }).then((resp) => {
        return resp.json();
    }).then((json) => {
        if(json.answer == "OK") {
            // 성공시 수행할 내용
        }
    });
}
function init() {
    checkCart();
    setGoButtons();
    setJsHome();
    setModifyButton();
    executeModification();
}
init();