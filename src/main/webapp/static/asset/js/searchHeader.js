const logoBtn = document.querySelector(".main-logo-btn");

const goToHome = (e) => {
    window.location = "/login";
}

function init () {
    logoBtn.addEventListener("click", goToHome);
    logoBtn.setAttribute("style", "cursor: pointer");
}
init();

