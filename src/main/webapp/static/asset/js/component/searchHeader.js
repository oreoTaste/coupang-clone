// import해서 사용하는 경우
// 장바구니 카운트 표기
export const setCart = () => {
    fetch("/cart/count").then((response) => {
        return response.json();
    }).then((json) => {
        document.querySelector(".my-coupang-title").innerText = json;
    });
}
