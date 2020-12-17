const jusoValue = document.querySelector("#js-juso"),
      addressBtn = document.querySelector("#js-getAddressBtn"),
      contentResults = document.querySelector(".register-address-content__search-result"),
      contentTitle = document.querySelector(".register-address-content__title"),
      previousBtn = document.querySelector(".js-juso-previous"),
      nextBtn = document.querySelector(".js-juso-next"),
      curPage = document.querySelector(".js-curPage"),
      totalCnt = document.querySelector(".js-totalCount");

// 결과 만들기
const cleanResults = () => {
    totalCnt.innerText = "";
    curPage.innerText = "";
    if(contentResults.hasChildNodes) {
        contentResults.innerText = "";
    }
}
const createTitle = (count, pg, totalPage) => {
    totalCnt.innerText = count;
    curPage.setAttribute("value", pg);
    curPage.innerText = "(" + pg + "/" + totalPage + ")";
}
const createContent = (roadAddr, jibunAddr, zipNo) => {
    const wrapperBox = document.createElement("div");
    wrapperBox.classList.add("search-result");
    const zipBox = document.createElement("div");
    const roadBox = document.createElement("div");
    const jibunBox = document.createElement("div");
    zipBox.innerText = zipNo;
    roadBox.innerText = roadAddr;
    jibunBox.innerText = jibunAddr;
    wrapperBox.appendChild(zipBox);
    wrapperBox.appendChild(roadBox);
    wrapperBox.appendChild(jibunBox);
    contentResults.appendChild(wrapperBox);
}
// 주소 개수 받기 (너무 검색결과가 많은 경우도 있어서 배제)
/*
function getResult() {
    const value = jusoValue.value;
    const query = "http://www.juso.go.kr/addrlink/addrLinkApi.do?"
                    + "currentPage=1&"
                    + "confmKey=devU01TX0FVVEgyMDIwMTIxNzE3MTY1MDExMDU2NTE=&"
                    + "resultType=json&"
                    + "keyword=" + value;
    fetch(query).then((response) => {
        return response.json();
    }).then((json) => {
        const { results: { common: { totalCount } } } = json;
        const totalPg = 1 + Math.floor(totalCount / 10);
        return {totalCount, totalPg};
    }).then(({totalCount, totalPg}) => {
         cleanResults();
         createTitle(totalCount);
        for(let i = 1; i <= totalPg; i++) {
            printEachResult(i, value);
        }
    })
}
*/
// 페이지별로 주소 결과 받기
const printEachResult = (pg) => {
    const value = jusoValue.value;
    const query = "http://www.juso.go.kr/addrlink/addrLinkApi.do?"
                     + "currentPage=" + pg + "&"
                     + "confmKey=devU01TX0FVVEgyMDIwMTIxNzE3MTY1MDExMDU2NTE=&"
                     + "resultType=json&"
                     + "keyword=" + value;

    console.log(query);

    fetch(query).then((response) => {
        return response.json();
    }).then((json) => {
        const { results: { common: { totalCount }, juso } } = json;

        const totalPg = 1 + Math.floor(totalCount / 10);
        maxPage = totalPg;
        cleanResults();
        createTitle(totalCount, pg, maxPage);

        juso.forEach(el => {
            createContent(el.roadAddr, el.jibunAddr, el.zipNo);
        });
    })
}
// 주소 결과 받기
function printResult() {
    printEachResult(1);
}
function printPreviousResult() {
    let prev = (Number)(curPage.getAttribute("value"));
    if(prev > 1) {
        prev -= 1;
    }
    console.log(prev);
    printEachResult(prev);
}
function printNextResult() {
    let next = (Number)(curPage.getAttribute("value"));
    if(next < maxPage) {
        next += 1;
    }
    console.log(next);
    printEachResult(next);
}
const setAddressBtn = () => {
    addressBtn.addEventListener("click", printResult);
}
const setPreviousBtn = () => {
    previousBtn.addEventListener("click", printPreviousResult);
}
const setNextBtn = () => {
    nextBtn.addEventListener("click", printNextResult);
}

function init() {
    setAddressBtn();
    setPreviousBtn();
    setNextBtn();
}

init();