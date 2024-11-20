const backButton = document.getElementById("check_back_button")
const introContainer = document.getElementById("intro_container")
const checkTeamContainer = document.getElementById("check_team_container")

backButton.addEventListener("click", (event) => {
    introContainer.classList.remove("fade_in")
    introContainer.classList.add("fade_out")

    const targetUrl = event.target.getAttribute("data-url")

    setTimeout(() => {
        window.location.href = targetUrl
    }, 300)
})

function chooseCheckTeams() {
    introContainer.classList.remove("fade_in")
    introContainer.classList.add("fade_out")

    checkTeamContainer.classList.remove("fade_out")
    checkTeamContainer.classList.add("fade_in")

    setTimeout(() => {
        introContainer.classList.add("hidden")
        checkTeamContainer.classList.remove("hidden")
    }, 300)
}

function checkTeamsBack() {
    introContainer.classList.remove("fade_out")
    introContainer.classList.add("fade_in")

    checkTeamContainer.classList.remove("fade_in")
    checkTeamContainer.classList.add("fade_out")

    setTimeout(() => {
        introContainer.classList.remove("hidden")
        checkTeamContainer.classList.add("hidden")
    }, 300)
}