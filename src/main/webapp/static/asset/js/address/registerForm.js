const showingPart = document.querySelector(".showingPart"),
      trigger = document.querySelector("#rough-address"),
      showingAddrDetail = document.querySelector(".register-address-profile__address"),
      searchPart = document.querySelector(".searchPart"),
      jusoValue = document.querySelector("#js-juso"),
      addressBtn = document.querySelector("#js-getAddressBtn"),
      contentResults = document.querySelector(".register-address-content__search-result"),
      contentTitle = document.querySelector(".register-address-content__title"),
      previousBtn = document.querySelector(".js-juso-previous"),
      nextBtn = document.querySelector(".js-juso-next"),
      curPage = document.querySelector(".js-curPage"),
      totalCnt = document.querySelector(".js-totalCount"),
      searchResult = document.querySelector(".search-result"),
      showingZipCode = document.querySelector("#zipcode"),
      showingCity = document.querySelector("#city");

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
    wrapperBox.addEventListener("click", addResult);

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

// 페이지별로 주소 결과 받기
const printEachResult = (pg) => {
    const value = jusoValue.value;
    const query = "http://www.juso.go.kr/addrlink/addrLinkApi.do?"
                     + "currentPage=" + pg + "&"
                     + "confmKey=devU01TX0FVVEgyMDIwMTIxNzE3MTY1MDExMDU2NTE=&"
                     + "resultType=json&"
                     + "keyword=" + value;

    fetch(query).then((response) => {
        return response.json();
    }).then((json) => {
        const { results: { common: { totalCount }, juso } } = json;

        const totalPg = 1 + Math.floor(totalCount / 10);
        maxPage = totalPg;
        cleanResults();
        createTitle(totalCount, pg, maxPage);
        if(!juso) {
            alert("해당하는 주소가 없습니다");
        } else {
            juso.forEach(el => {
                createContent(el.roadAddr, el.jibunAddr, el.zipNo);
            });
        }
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

// 결과창 추가하기
const addResult = (e) => {
    const parent = e.target.parentNode;
    const [zip, rd, jbn] = parent.children;

    totalCnt.innerText = "0";
    curPage.innerText = "";
    contentResults.innerText = "";

    showingZipCode.value = zip.innerText;
    showingCity.value = jbn.innerText.split(' ')[0];

    showingAddrDetail.classList.remove("double-line");
    trigger.value = rd.innerText;
    HideAndShow();
}
// 창 숨기기
const HideAndShow = () => {
    searchPart.classList.add("hidden");
    showingPart.classList.remove("hidden");
}
// 창 띄우기
const showAndHide = () => {
    showingPart.classList.add("hidden");
    searchPart.classList.remove("hidden");
}
const setTrigger = () => {
    trigger.addEventListener("click", showAndHide);
}
function init() {
    setAddressBtn();
    setPreviousBtn();
    setNextBtn();
    setTrigger();
}

init();


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