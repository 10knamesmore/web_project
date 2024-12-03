const introBackButton = document.getElementById("intro_back_button")
const introContainer = document.getElementById("intro_container")
const matchInfoTypeSelector = document.getElementById("match_info_type_selector")
const matchInfoInput = document.getElementById("match_info_input")
const matchInfoSubmitButton = document.getElementById("match_info_submit_button")

introBackButton.addEventListener("click", (event) => {
    setTimeout(() => {
        window.location.href = targetUrl
    }, 300)

    introContainer.classList.remove("fade_in")
    introContainer.classList.add("fade_out")

    const targetUrl = event.target.getAttribute("data-url")
})

matchInfoSubmitButton.addEventListener("click", async function () {
    const datatype = matchInfoTypeSelector.value
    const datavalue = matchInfoInput.value
    const response = await find_match_by(datatype, datavalue)
    console.log(response.json())
    // TODO: handle response
})

matchInfoTypeSelector.addEventListener("change", function () {
    adapt_to_info_type()
})

async function find_match_by(datatype, datavalue) {
    return await fetch(`/api/findmatchby?datatype=${datatype}&datavalue=${datavalue}`)
}

function adapt_to_info_type() {
    const selectedType = matchInfoTypeSelector.value;
    switch (selectedType) {
        case "matchDate":
        default:
            matchInfoInput.type = "date"
            break;
        case "teamName":
        case "matchType":
            matchInfoInput.type = "text"
            break;
    }
    document.getElementById(
        "match_info_input"
    ).value = "";
}