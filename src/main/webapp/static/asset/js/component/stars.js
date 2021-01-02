/*-----------------별표 구현 설정 시작-------------------*/
export function setStarRatings() {
    const starRatings = document.querySelectorAll(".js-star-rating");
    [...starRatings].forEach(el => setStar(el, el.querySelector("span").innerText));
}
const setStar = (el, val) => {
    let cnt = parseInt(val);
    let left = 5 - cnt;
    while(cnt > 0) {
        cnt--;
        const star = document.createElement("span");
        star.classList.add("yellow-star");
        el.appendChild(star);
    }
    while(left > 0) {
        left--;
        const star = document.createElement("span");
        star.classList.add("gray-star");
        el.appendChild(star);
    }
    const number = document.createElement("span");
    number.innerText = "(" + parseInt(val) + "/5)";
    el.appendChild(number);
}
/*-----------------별표 구현 설정 끝-------------------*/