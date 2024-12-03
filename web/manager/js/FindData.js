const introBackButton = document.getElementById("intro_back_button")
const introContainer = document.getElementById("intro_container")
const dataContainer = document.getElementById("data_container")
const matchInfoTypeSelector = document.getElementById("match_info_type_selector")
const matchInfoInput = document.getElementById("match_info_input")
const matchInfoSubmitButton = document.getElementById("match_info_submit_button")
const dataBackButton = document.getElementById("data_back_button")

dataBackButton.addEventListener("click", (event) => {
    setTimeout(() => {
        dataContainer.classList.add("hidden")
        introContainer.classList.remove("hidden")
    }, 300)

    dataContainer.classList.remove("fade_in")
    dataContainer.classList.add("fade_out")

    introContainer.classList.remove("fade_out")
    introContainer.classList.add("fade_in")
})

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
    draw_data(response)
})

matchInfoTypeSelector.addEventListener("change", function () {
    adapt_to_info_type()
})

async function find_match_by(datatype, datavalue) {
    return await fetch(`/api/findmatchby?datatype=${datatype}&datavalue=${datavalue}`).then(response => response.json())
}

function draw_data(data) {
    const tableBody = document.getElementById("matches_table").querySelector("tbody");
    tableBody.innerHTML = ''
    data.forEach((match) => {
        const row = document.createElement("tr");
        const id = match.id
        row.innerHTML = `
            <td>${match.matchType}</td>
            <td>${match.matchDate}</td>
            <td>${match.homeTeamName}</td>
            <td>${match.homeTeamScore}</td>
            <td>${match.awayTeamScore}</td>
            <td>${match.awayTeamName}</td>
            <td><button class="delete_match_button" data_id="${id}">删除</button></td>
            `;
        tableBody.appendChild(row);
    });
    setTimeout(() => {
        introContainer.classList.add("hidden")
        dataContainer.classList.remove("hidden")
    }, 300)

    const deleteButtons = document.querySelectorAll(".delete_match_button");
    deleteButtons.forEach(button => {
        button.addEventListener("click", function () {
            const matchId = button.getAttribute("data_id");
            deleteMatch(matchId);
        });
    });
    // Hide other containers and show the matches container
    introContainer.classList.remove("fade_in")
    introContainer.classList.add("fade_out")

    dataContainer.classList.remove("fade_out")
    dataContainer.classList.add("fade_in")
}

function deleteMatch(matchId) {
    fetch(`/api/deletematch?id=${matchId}`)
        .then(
            async () => {
                alert("删除成功！");
                // 刷新页面
                matchInfoSubmitButton.click()
            }
        )
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