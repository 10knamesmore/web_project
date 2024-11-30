

const buttons = document.querySelectorAll(".redir_but");

buttons.forEach(button => {
    button.addEventListener("click", (event) => {
        setTimeout(() => {
            window.location.href = targetUrl
        }, 300)

        const container = document.getElementById("container");

        container.classList.remove("fade_in")
        container.classList.add("fade_out")

        const targetUrl = event.target.getAttribute("data-url")

    })
})

