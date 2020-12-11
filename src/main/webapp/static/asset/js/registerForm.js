const check_for_all = document.querySelector("#check-all"),
    check_agreement = document.querySelectorAll(".check_agreement"),
    all = [...check_agreement],
    agree = document.querySelector("#agree");
    

const handleCheckAll = (e) => {
    if(e.target.checked) {
        all.forEach(el => el.checked = true);
    } else {
        all.forEach(el => el.checked = false);
    }
}

const checkAll = (e) => {
    if(all.filter(el => el.checked).length >= 5) {
        check_for_all.checked = true;
    } else {
        check_for_all.checked = false;
    }
}

const handleAgree = (e) => {
    const val1 = document.querySelector("#password");
    const val2 = document.querySelector("#password-2");
    if(val1.value !== val2.value) {
        alert("비밀번호가 일치하지 않습니다");
        e.preventDefault();
    }
}
function init() {
    all.forEach(el => 
        el.addEventListener("click", checkAll)
    );
    
    check_for_all.addEventListener("click", handleCheckAll);
    agree.addEventListener("click", handleAgree);
}
init();
