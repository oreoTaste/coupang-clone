// import해서 사용하는 경우
// 장바구니 카운트 표기
export const setCart = (num) => {
    if(num == null) {
//        $.when($.ajax({
//            type: "get",
//            url: "/cart/count",
//        })).done((resp) => {
//            console.log(resp);
//            document.querySelector(".my-coupang-title").innerText = resp;
//        });

        fetch("/cart/count").then((response) => {
            return response.json();
        }).then((json) => {
            document.querySelector(".my-coupang-title").innerText = json;
        });

    } else {
        document.querySelector(".my-coupang-title").innerText = num;
    }
}
export const setGoToLogin = () => {
    if(document.querySelector(".goToLogout")) {
        document.querySelector(".goToLogout").addEventListener("click", () => {
            window.location.href="/logout";
        });
    }
}
export const setGoToLogout = () => {
    if(document.querySelector(".goToLogin")) {
        document.querySelector(".goToLogin").addEventListener("click", () => {
            window.location.href="/";
        });
    }
}
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