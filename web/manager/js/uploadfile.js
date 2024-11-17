// TODO 
const button = document.getElementById("back_button");
back_button.addEventListener("click", (event) => {
    const container = document.getElementById("upload-container");

    container.classList.remove("fade_in")
    container.classList.add("fade_out");
    container.style.transform = ""

    const targetUrl = event.target.getAttribute("data-url");

    setTimeout(() => {
        window.location.href = targetUrl;
    }, 200);
});

