const introBackButton = document.getElementById("intro_back_button")
const introContainer = document.getElementById("intro_container")
const byTeamContainer = document.getElementById("by_team_container")
const byMatchContainer = document.getElementById("by_match_container")
const matchInfoTypeSelector = document.getElementById("match_info_type_selector")
const matchInfoInput = document.getElementById("match_info_input")

introBackButton.addEventListener("click", (event) => {
    setTimeout(() => {
        window.location.href = targetUrl
    }, 300)

    introContainer.classList.remove("fade_in")
    introContainer.classList.add("fade_out")

    const targetUrl = event.target.getAttribute("data-url")

})

matchInfoTypeSelector.addEventListener("change", function () {
    adapt_to_info_type()
})
function choose_search_team() {
    setTimeout(() => {
        byTeamContainer.classList.remove("hidden")
        introContainer.classList.add("hidden")
    }, 300)
    introContainer.classList.remove("fade_in")
    introContainer.classList.add("fade_out")

    byTeamContainer.classList.remove("fade_out")
    byTeamContainer.classList.add("fade_in")

}
function choose_search_match() {
    setTimeout(() => {
        byMatchContainer.classList.remove("hidden")
        introContainer.classList.add("hidden")
    }, 300)
    introContainer.classList.remove("fade_in")
    introContainer.classList.add("fade_out")

    byMatchContainer.classList.remove("fade_out")
    byMatchContainer.classList.add("fade_in")

}

function input_teamname_back() {
    setTimeout(() => {
        introContainer.classList.remove("hidden")
        byTeamContainer.classList.add("hidden")
    }, 300)
    byTeamContainer.classList.remove("fade_in")
    byTeamContainer.classList.add("fade_out")

    introContainer.classList.remove("fade_out")
    introContainer.classList.add("fade_in")

}
function input_match_back() {
    setTimeout(() => {
        introContainer.classList.remove("hidden")
        byMatchContainer.classList.add("hidden")
    }, 300)
    byMatchContainer.classList.remove("fade_in")
    byMatchContainer.classList.add("fade_out")

    introContainer.classList.remove("fade_out")
    introContainer.classList.add("fade_in")
}

function adapt_to_info_type() {
    const selectedType = matchInfoTypeSelector.value;
    switch (selectedType) {
        case "matchDate":
        default:
            matchInfoInput.type = "date"
            break;
        case "homeTeamName":
        case "guestTeamName":
        case "matchType":
            matchInfoInput.type = "text"
            break;
    }
}