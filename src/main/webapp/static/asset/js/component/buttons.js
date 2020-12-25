export function setJsHome() {
    document.querySelector(".js-home").addEventListener("click",
        (e) => {
            e.preventDefault();
            window.location.href="/main";
        });
}

export function setAddressRegistration() {
    if(document.querySelector(".register-address")) {
        document.querySelectorAll(".register-address").forEach(el =>
            el.addEventListener("click", (e) => {
                window.open('/register/address/','주소등록','width=400,height=500,location=no,status=no,scrollbars=no');
            })
        )
    }
}

export function setAddressChange() {
    if(document.querySelector(".change-address")) {
        document.querySelectorAll(".change-address").forEach(el =>
            el.addEventListener("click", (e) => {
                const id = e.target.parentNode.parentNode.querySelector(".address-id").innerText;
                window.open('/change/address/' + id, '주소수정', 'width=400,height=500,location=no,status=no,scrollbars=no');
            })
        )
    }
}

export function setAddressDelete() {
    if(document.querySelector(".delete-address")) {
        document.querySelectorAll(".delete-address").forEach(el =>
            el.addEventListener("click", (e) => {
                const id = e.target.parentNode.parentNode.querySelector(".address-id").innerText;
                window.open('/delete/address/' + id, '주소수정', 'width=400,height=500,location=no,status=no,scrollbars=no');
            })
        )
    }
}