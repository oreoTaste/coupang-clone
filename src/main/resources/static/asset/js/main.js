const joysticks = document.querySelectorAll(".joystick"),
      bannerImg = document.querySelector(".main-banner__banners");

const changeImg = (e) => {
    const text = e.target.alt.slice(0, e.target.alt.indexOf("_")) + ".jpg";
    bannerImg.style.background = "url(../static/asset/img/banner/" + text + ")";
}
function init() {
    [...joysticks].forEach(el => el.addEventListener("mouseover", changeImg));

}

init();