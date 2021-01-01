/*---------------------------- 장바구니 관련 설정 시작 ----------------------------*/
export const checkCart = () => {
    if(sessionStorage.getItem("login") == "true") { // 로그인 상태에서 장바구니 상품 수 받아오기
        setCart();
    } else { // 로그아웃 상태에서 장바구니 상품 수 처리하기
        const {bm_sv} = getCookieTempInfo();
        // 로그아웃 상태에서 장바구니가 비어있다면 0으로 세팅
        if(bm_sv == undefined) {
            setCart(0);
        } else {
            setCart();
        }
    }
}
export const setCart = (num) => {
    if(num == null) {
        fetch("/cart/count").then((response) => {
            return response.json();
        }).then((json) => {
            document.querySelector(".my-coupang-title").innerText = json;
        });
    } else {
        document.querySelector(".my-coupang-title").innerText = num;
    }
}
/*---------------------------- 장바구니 관련 설정 끝 ----------------------------*/

/*-------------------- 버튼 설정 시작 --------------------*/
export const setGoButtons = () => {
    setGoToLogin();
    setGoToLogout();
    setGoToMyCoupang();
    setGoToRegister();
}
export const setGoToLogout = () => {
    if(document.querySelector(".go-to-logout")) {
        document.querySelectorAll(".go-to-logout")
            .forEach(el => el.addEventListener("click", () => {
                window.location.href="/logout";
            }));
    }
}
export const setGoToLogin = () => {
    if(document.querySelector(".go-to-login")) {
        document.querySelectorAll(".go-to-login")
            .forEach(el => el.addEventListener("click", () => {
                window.location.href="/";
            }));
    }
}
export const setGoToMyCoupang = () => {
    if(document.querySelector(".go-to-mycoupang")) {
        document.querySelectorAll(".go-to-mycoupang")
            .forEach(el => el.addEventListener("click", () => {
                window.location.href="/mycoupang";
            }));
    }
}
export const setGoToRegister = () => {
    if(document.querySelector(".go-to-register")) {
        document.querySelectorAll(".go-to-register")
            .forEach(el => el.addEventListener("click", () => {
                window.location.href="/register";
            }));
    }
}
/*-------------------- 버튼 설정 끝 --------------------*/

/*-------------------- 쿠키 설정 읽기 시작 --------------------*/
export const getCookieTempInfo = () => {
    let tempInfo = {};
    const pieces = document.cookie.split(";");
    for(let piece of pieces) {
        const [key, value] = piece.split("=");
        if(key.trim() == "tempId") {
            tempInfo.tempId = value.trim();
        }
        if(key.trim() == "bm_sv") {
            tempInfo.bm_sv = value.trim();
        }
    }
    return tempInfo;
}
/*-------------------- 쿠키 설정 읽기 끝 --------------------*/
