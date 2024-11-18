const intro_back_button = document.getElementById("intro_back_button")
const intro_container = document.getElementById("intro_container")
const by_team_container = document.getElementById("by_team_container")
const match_info_type_selector = document.getElementById("match_info_type_selector")
const match_info_input = document.getElementById("match_info_input")

intro_back_button.addEventListener("click", (event) => {
    intro_container.classList.remove("fade_in")
    intro_container.classList.add("fade_out")

    const targetUrl = event.target.getAttribute("data-url")

    setTimeout(() => {
        window.location.href = targetUrl
    }, 200)
})

match_info_type_selector.addEventListener("change", function () {
    adapt_to_info_type()
})
function choose_search_team() {
    intro_container.classList.remove("fade_in")
    intro_container.classList.add("fade_out")

    by_team_container.classList.remove("fade_out")
    by_team_container.classList.add("fade_in")

    setTimeout(() => {
        by_team_container.classList.remove("hidden")
        intro_container.classList.add("hidden")
    }, 300)
}
function choose_search_match() {
    intro_container.classList.remove("fade_in")
    intro_container.classList.add("fade_out")

    by_match_container.classList.remove("fade_out")
    by_match_container.classList.add("fade_in")

    setTimeout(() => {
        by_match_container.classList.remove("hidden")
        intro_container.classList.add("hidden")
    }, 300)
}

function input_teamname_back() {
    by_team_container.classList.remove("fade_in")
    by_team_container.classList.add("fade_out")

    intro_container.classList.remove("fade_out")
    intro_container.classList.add("fade_in")

    setTimeout(() => {
        intro_container.classList.remove("hidden")
        by_team_container.classList.add("hidden")
    }, 300)
}
function input_match_back() {
    by_match_container.classList.remove("fade_in")
    by_match_container.classList.add("fade_out")

    intro_container.classList.remove("fade_out")
    intro_container.classList.add("fade_in")

    setTimeout(() => {
        intro_container.classList.remove("hidden")
        by_match_container.classList.add("hidden")
    }, 300)

}

function adapt_to_info_type() {
    const selectedType = match_info_type_selector.value;
    switch (selectedType) {
        case "matchDate":
        default:
            match_info_input.type = "date"
            break;
        case "homeTeamName":
        case "guestTeamName":
        case "matchType":
            match_info_input.type = "text"
            break;
    }
}