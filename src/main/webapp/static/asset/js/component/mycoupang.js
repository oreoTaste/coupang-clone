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
