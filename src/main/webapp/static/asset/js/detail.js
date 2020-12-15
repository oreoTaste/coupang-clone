const originalPrice = document.querySelector(".js-original-price"),
      price = document.querySelector(".js-price"),
      productQuantity = document.querySelector(".js-quantity"),
      upButton = document.querySelector(".js-quantity-up"),
      downButton = document.querySelector(".js-quantity-down"),
      thumbnailPic = document.querySelector(".product-detail-header__pic"),
      magnifiedPic = document.querySelector(".magnified-product"),
      detailBody = document.querySelector(".product-detail-body");
      spreader = document.querySelector(".product-detail-content__spreader"),
      id = document.querySelector(".product-detail-header__product-id"),
      directOrder = document.querySelector(".directOrder");

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

const handlePosition = (e) => {
    magnifiedPic.firstElementChild.setAttribute("style", "object-position: -" + e.offsetX + "px -" + e.offsetY + "px");
}

const showBlock = (e) => {
    e.preventDefault();
    console.log(e.target);
    magnifiedPic.classList.remove("hidden");
}

const hideBlock = (e) => {
    magnifiedPic.classList.add("hidden");
}

const handlerSpreader = (e) => {
    e.target.setAttribute("style", "display: none");
    detailBody.setAttribute("style", "height: auto");
}
const handleOrder = (e) => {
    if(!sessionStorage.getItem("id")) {
        e.preventDefault();
        const items = sessionStorage.getItem("products");
        if(!items) {
            sessionStorage.setItem("products", id.value);
        }
        window.location = "/";
    }
}
function init() {
    price.innerText = makeCommas(originalPrice.innerText);
    console.log(originalPrice.innerText);
    upButton.addEventListener("click", handleUp);
    downButton.addEventListener("click", handleDown);
    thumbnailPic.firstElementChild.addEventListener("mouseover", showBlock);
    thumbnailPic.firstElementChild.addEventListener("mouseleave", hideBlock);
    thumbnailPic.firstElementChild.addEventListener("mousemove", handlePosition);
    spreader.addEventListener("click", handlerSpreader);
    directOrder.addEventListener("click", handleOrder);

}

init();