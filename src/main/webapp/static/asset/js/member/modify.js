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
        return null;
    }).catch((err) => {
        alert("사용자 정보가 올바르지 않습니다")
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
        modifyPasswordBtn.addEventListener("click", setModifyPasswordBtn);
    }
}
const setModifyPasswordBtn = (e) => {
    const parent = e.target.parentNode.parentNode;
    const curPas = parent.querySelector(".cur-password").value;
    const newPas = parent.querySelector(".new-password").value;
    const newPasRepeat = parent.querySelector(".new-password-repeat").value;

    (async () => {
        let bool = false;
        const answer = await getVerification({"password": curPas});
        bool = answer.answer == "OK";

        if(bool == true) {
            bool = checkPassword(newPas, newPasRepeat);
        }
        if(bool == true) {
            modify({"curPas": curPas, "newPas": newPas});
        } else {
            alert("새 비밀번호를 다시 확인해주세요 (8자이상이어야합니다)")
        }

    })();
}
const getVerification = async (data) => await fetch (
    "/mycoupang/checkPassword", {
        method: 'POST',
        body: JSON.stringify(data)
    }).then((resp) => {
        return resp.json();
});

const checkPassword = (a, b) => {
    if(a.toString().length >= 8) {
        if(a == b) {
            return true;
        }
    }
    return false;
}
const modify = (data) => {
    fetch(
        "/mycoupang/modify", {
        method: 'POST',
        body: JSON.stringify(data)
    }).then((resp) => {
        return resp.json();
    }).then((json) => {
        if(json.answer == "OK") {
            window.location.href="/?changePassword=Y";
        }
    }).catch((err) => {
        alert("사용자 정보가 올바르지 않습니다")
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