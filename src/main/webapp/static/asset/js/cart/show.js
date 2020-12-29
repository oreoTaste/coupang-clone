import {makeCommas, deleteCommas} from '../component/price_quantity.js';
import {setJsHome} from '../component/buttons.js';

const prices = document.querySelectorAll(".js-price"),
      items = document.querySelectorAll(".cart-content__item"),
      contents = document.querySelectorAll(".cart-content"),
      counter = document.querySelectorAll(".js-count"),
      checkToBuy = document.querySelectorAll(".check-to-buy"),
      all = [...checkToBuy],
      checkAll = document.querySelector(".check-all"),
      contentSummary = document.querySelectorAll(".cart-content__summary");

const setCommas = () => {
    prices.forEach(el => el.innerText = makeCommas(el.innerText));
}
/*---------------------------- 계산관련 핸들러시작 ----------------------------*/
function setCalculator(){
    // 상품별 총액 계산
    calculateEachItems();
    // 상품군별 총액 계산 (로켓상품, 판매자배송상품)
    calculateEachContents();
    // 총액 계산 (grand total)
    calculateAll();
    // 계산후 콤마 찍기
    setCommas();
}
const calculateEachItems = () => {
    for(let item of items) {
        const cartQuantity = item.querySelector(".cart-quantity").innerText;
        const cartPrice = item.querySelector(".cart-price").innerText;
        const totalCost = item.querySelector(".total-cost");
        totalCost.innerText = Number(cartQuantity) * Number(deleteCommas(cartPrice));
    }
}
const calculateEachContentsChecked = (content) => {
    const items = content.querySelectorAll(".cart-content__item");

    let totalCostsBucket = 0;
    let deliveryCostsBucket = 0;

    items.forEach(el => {
        if(el.querySelector(".check-to-buy").checked) {
            totalCostsBucket += Number(deleteCommas(el.querySelector(".total-cost").innerText));
            deliveryCostsBucket += Number(deleteCommas(el.querySelector(".delivery-cost").innerText));
        }
    });
    return {totalCostsBucket, deliveryCostsBucket};
}

const calculateEachContents = () => {
    for(let content of contents) {
        const {totalCostsBucket, deliveryCostsBucket} = calculateEachContentsChecked(content);
        content.querySelector(".summary__item-cost").innerText = totalCostsBucket;
        content.querySelector(".summary__delivery-cost").innerText = deliveryCostsBucket;
        content.querySelector(".summary__total-cost").innerText = totalCostsBucket + deliveryCostsBucket;
    }
}
const calculateAll = () => {
    let itemCost = 0;
    let deliveryCost = 0;
    let totalCost = 0;

    contentSummary.forEach(el => {
        itemCost += Number(deleteCommas(el.querySelector(".summary__item-cost").innerText));
        deliveryCost += Number(deleteCommas(el.querySelector(".summary__delivery-cost").innerText));
        totalCost += Number(deleteCommas(el.querySelector(".summary__total-cost").innerText));
        }
    );

    document.querySelector(".grand-item-cost").innerText = itemCost;
    document.querySelector(".grand-delivery-cost").innerText = deliveryCost;
    document.querySelector(".grand-total-cost").innerText = totalCost;
}
/*---------------------------- 계산관련 핸들러끝 ----------------------------*/

/*---------------------------- 체크박스 핸들러시작 ----------------------------*/
function setCheckButtons() {
    checkAll.addEventListener("click", handleCheckAll);
    checkToBuy.forEach(el => el.addEventListener("click", handleCheckToBuy));
}
const handleCheckAll = (e) => {
    if(e.target.checked) {
        checkToBuy.forEach(el => el.checked = true);
    } else {
        checkToBuy.forEach(el => el.checked = false);
    }
    setCalculator();
}
const handleCheckToBuy = (e) => {
    if(all.filter(el => el.checked).length >= checkToBuy.length) {
        checkAll.checked = true;
    } else {
        checkAll.checked = false;
    }
    setCalculator();
}
/*---------------------------- 체크박스 핸들러끝 ----------------------------*/

/*---------------------------- +- 버튼 시작 ----------------------------*/
function setPlusMinusButtons() {
    counter.forEach(el => {
       const minus = el.querySelector(".minus");
       const plus = el.querySelector(".plus");
       minus.addEventListener("click", setMinusBtn);
       plus.addEventListener("click", setPlusBtn);
    })
}
const adjustCart = (grandParent) => {
    const id = grandParent.querySelector(".product-info").getAttribute("value");
    const quantity = grandParent.querySelector(".cart-quantity").innerText;

    fetch("/cart/adjust/id=" + id + "&quantity=" + quantity, {
            method: 'POST',
            mode: 'cors',
            cache: 'no-cache',
            credentials: 'same-origin',
            headers: {
                'Content-Type': 'application/x-www-form-urlencoded',
            }
        }
    );
}
const setMinusBtn = (e) => {
    const originalValue = Number(e.target.parentNode.querySelector(".cart-quantity").innerText);
    if(originalValue > 1) {
        e.target.parentNode.querySelector(".cart-quantity").innerText = originalValue - 1;
    }
    adjustCart(e.target.parentNode.parentNode.parentNode);
    setCalculator();
}
const setPlusBtn = (e) => {
    const originalValue = Number(e.target.parentNode.querySelector(".cart-quantity").innerText);
    e.target.parentNode.querySelector(".cart-quantity").innerText = originalValue + 1;
    adjustCart(e.target.parentNode.parentNode.parentNode);
    setCalculator();
}
/*---------------------------- +- 버튼 끝 ----------------------------*/

/*---------------------------- 구매버튼 시작 ----------------------------*/
function setPurchaseButtons() {
    // 홈으로 가기 버튼
    setJsHome();
    // 구매하기 버튼
    setPurchase();
}
const setPurchase = () => {
    document.querySelector(".js-purchase").addEventListener("click", purchase);
}
const purchase = (e) => {
    const checkedItems = [...items].filter(el => el.querySelector(".check-to-buy").checked);
    let productId = "";
    let count = "";
    for(let item of checkedItems) {
        productId += item.querySelector(".product-info").getAttribute("value") + ",";
        count += item.querySelector(".cart-quantity").innerText + ",";
    }
    window.location.href="/product/checkout/" + "productId=" + productId + "&count=" + count;
}
/*---------------------------- 구매버튼 끝 ----------------------------*/

/*---------------------------- 삭제버튼 시작 ----------------------------*/
function setDeleteButton() {
    setDeletion();
}
const setDeletion = () => {
    document.querySelector(".js-delete").addEventListener("click", deletion);
}
const deletion = () => {
    const checkedItems = [...items].filter(el => el.querySelector(".check-to-buy").checked);
    let productId = "";
    for(let item of checkedItems) {
        productId += item.querySelector(".product-info").getAttribute("value") + ",";
    }

    fetch(
        "/cart/delete/" + productId
    ).then((resp) => {
        console.log(resp);
        return resp.json();
    }).then((json) => {
        console.log(json);
    })
}
/*---------------------------- 삭제버튼 끝 ----------------------------*/

function init() {
    setPurchaseButtons();
    setDeleteButton();
    setCalculator();
    setPlusMinusButtons();
    setCheckButtons();
}
init();