import {makeCommas, deleteCommas} from '../component/price_quantity.js';

const prices = document.querySelectorAll(".js-price"),
      items = document.querySelectorAll(".cart-content__item"),
      contents = document.querySelectorAll(".cart-content"),
      counter = document.querySelectorAll(".js-count");

const setCommas = () => {
    prices.forEach(el => el.innerText = makeCommas(el.innerText));
}

const calculateEachItems = () => {
    for(let item of items) {
        const cartQuantity = item.querySelector(".cart-quantity").innerText;
        const cartPrice = item.querySelector(".cart-price").innerText;
        const totalCost = item.querySelector(".total-cost");
        totalCost.innerText = Number(cartQuantity) * Number(deleteCommas(cartPrice));
    }
}

const getSummary = () => {
    for(let content of contents) {
        const totalCosts = content.querySelectorAll(".total-cost");
        const deliveryCosts = content.querySelectorAll(".delivery-cost");
        let totalCostsBucket = 0;
        let deliveryCostsBucket = 0;
        totalCosts.forEach(el => totalCostsBucket += Number(el.innerText));
        deliveryCosts.forEach(el => deliveryCostsBucket += Number(deleteCommas(el.innerText)));

        content.querySelector(".summary__item-cost").innerText = totalCostsBucket;
        content.querySelector(".summary__delivery-cost").innerText = deliveryCostsBucket;
        content.querySelector(".summary__total-cost").innerText = totalCostsBucket + deliveryCostsBucket;
    }
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
/* set counter(Plus, minus buttons) */
const setMinusBtn = (e) => {
    const originalValue = Number(e.target.parentNode.querySelector(".cart-quantity").innerText);
    if(originalValue > 1) {
        e.target.parentNode.querySelector(".cart-quantity").innerText = originalValue - 1;
    }
    adjustCart(e.target.parentNode.parentNode.parentNode);
    calculateEachItems();
    getSummary();
    setCommas();
}
const setPlusBtn = (e) => {
    const originalValue = Number(e.target.parentNode.querySelector(".cart-quantity").innerText);
    e.target.parentNode.querySelector(".cart-quantity").innerText = originalValue + 1;
    adjustCart(e.target.parentNode.parentNode.parentNode);

    calculateEachItems();
    getSummary();
    setCommas();
}
const setCounter = () => {
    counter.forEach(el => {
       const minus = el.querySelector(".minus");
       const plus = el.querySelector(".plus");
       minus.addEventListener("click", setMinusBtn);
       plus.addEventListener("click", setPlusBtn);
    })
}
function init() {
    calculateEachItems();
    getSummary();
    setCounter();
    setCommas();
}
init();