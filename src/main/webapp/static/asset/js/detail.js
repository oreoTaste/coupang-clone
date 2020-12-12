const originalPrice = document.querySelector(".js-original-price");
      price = document.querySelector(".js-price"),
      productQuantity = document.querySelector(".js-quantity"),
      upButton = document.querySelector(".js-quantity-up"),
      downButton = document.querySelector(".js-quantity-down");

const makeCommas = (x) => {
    return x.toString().replace(/\B(?<!\.\d*)(?=(\d{3})+(?!\d))/g, ",");
}

const handleUp = () => {
    const originalQuantity = Number(productQuantity.value);
    if(productQuantity.value < 100) {
        productQuantity.value = originalQuantity + 1;
        price.innerText = makeCommas(Number(originalPrice.innerText) * productQuantity.value)
    }
}

const handleDown = () => {
    if(productQuantity.value > 1) {
        productQuantity.value -= 1;
        price.innerText = makeCommas(Number(originalPrice.innerText) * productQuantity.value)
    }
}

function init() {
    price.innerText = makeCommas(originalPrice.innerText);
    upButton.addEventListener("click", handleUp);
    downButton.addEventListener("click", handleDown);

}

init();