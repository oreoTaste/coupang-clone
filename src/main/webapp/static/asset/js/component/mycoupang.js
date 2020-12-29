export const setDeliveryStatus = (num) => {
    if(num == null) {
        fetch("/delivery/count").then((response) => {
            return response.json();
        }).then((json) => {
            document.querySelector(".js-delivery-status").innerText = json;
        });
    } else {
        document.querySelector(".js-delivery-status").innerText = num;
    }
}
export const setDeliveryStatusShowButton = (num) => {
    if(document.querySelector(".js-show-delivery")) {
        document.querySelectorAll(".js-show-delivery").forEach(el => el.addEventListener("click", e =>
            window.location.href = '/deliveryStatus/' + e.target.parentNode.querySelector(".orderId").value
        ));
    }
}

