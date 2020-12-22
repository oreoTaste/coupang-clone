export function setJsHome() {
    document.querySelector(".js-home").addEventListener("click",
        (e) => {
            e.preventDefault();
            window.location.href="/main";
        });
}
