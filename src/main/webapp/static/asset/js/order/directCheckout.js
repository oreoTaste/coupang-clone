import {makeCommas, deleteCommas} from "../component/price_quantity.js";
const prices = document.querySelectorAll(".js-price"),
      totalCost = document.querySelector(".js-total-cost"),
      productPrices = document.querySelectorAll(".product-price"),
      productPrice = document.querySelector(".js-product-price"),
      deliveryPrices = document.querySelectorAll(".delivery-price"),
      deliveryPrice = document.querySelector(".js-delivery-price"),
      registerAddress = document.querySelector("#registerAddress"),
      productIds = document.querySelectorAll(".productId"),
      addressId = document.querySelector(".addressId"),
      payment = document.querySelector("#payment");

// 천단위 콤마
const setComma = () => {
    prices.forEach(el => el.innerText = makeCommas(el.innerText));
}

// 총결제금액 계산
const setTotalCost = () => {
    let pp = 0;
    productPrices.forEach(el => pp += Number(deleteCommas(el.innerText)));
    productPrice.innerText = makeCommas(pp);

    let dp = 0;
    deliveryPrices.forEach(el => dp += Number(deleteCommas(el.innerText)));
    deliveryPrice.innerText = makeCommas(dp);

    totalCost.innerText = makeCommas(dp + pp);
}
// 주소 등록하기
const setAddressRegisteration = () => {
    if(registerAddress) {
        registerAddress.addEventListener("click", () => {
            window.open('/register/address/','주소등록','width=400,height=500,location=no,status=no,scrollbars=no');
        });
    }
}
const goToCheckout = () => {
    const exAddressId = addressId.getAttribute("data-id");
    let exProductId = [];
    [...productIds].forEach(el => {
        exProductId.push(el.getAttribute("data-id") + ":" + el.getAttribute("data-count"));
    })
    // 결제에 대한 주문처리
    $.when($.ajax({
        type: "post",
        url: "/checkout/",
        data: {
            "exAddressId" : exAddressId,
            "exProductId" : exProductId
        },
        // 주문처리 후, 결제완료 페이지
    })).done((resp) => {
        if(resp == "OK") {
            window.location.href = "/checkout/after/" + exAddressId + "/" + exProductId;
        }
    });
}
const triggerPayment = () => {
    IMP.request_pay({
        pg : 'kakaopay',
        pay_method : 'card',
        merchant_uid : 'merchant_' + new Date().getTime(),
        name : '주문명:결제테스트(quick-spring)',
        amount : totalCost.innerText,
        buyer_email : 'iamport@siot.do',
        buyer_name : '구매자이름',
        buyer_tel : '010-1234-5678',
        buyer_addr : '서울특별시 강남구 삼성동',
        buyer_postcode : '123-456',
        m_redirect_url : '/main'
    }, function(rsp) {
        if ( rsp.success ) {
            var msg = '결제가 완료되었습니다.';
            msg += '고유ID : ' + rsp.imp_uid;
            msg += '상점 거래ID : ' + rsp.merchant_uid;
            msg += '결제 금액 : ' + rsp.paid_amount;
            msg += '카드 승인번호 : ' + rsp.apply_num;
        } else {
            var msg = '결제에 실패하였습니다.';
            msg += '에러내용 : ' + rsp.error_msg;
        }
        alert(msg);
        goToCheckout();
    });
}
const setPayment = () => {
    payment.addEventListener("click", triggerPayment);
}
function init() {
    var IMP = window.IMP; // 생략가능
    IMP.init("imp94521535");
    setComma();
    setTotalCost();
    setAddressRegisteration();
    setPayment();
}

init();