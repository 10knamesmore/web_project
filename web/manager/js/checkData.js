const backButton = document.getElementById("check_back_button")
const introContainer = document.getElementById("intro_container")
const checkTeamsContainer = document.getElementById("check_teams_container")
const checkMatchesContainer = document.getElementById("check_matches_container")

const baseUrl = window.location.protocol + "//" + window.location.host
const getMatchesDataApiUrl = baseUrl + "/api/matchesdata"


backButton.addEventListener("click", (event) => {
    introContainer.classList.remove("fade_in")
    introContainer.classList.add("fade_out")

    const targetUrl = event.target.getAttribute("data-url")

    setTimeout(() => {
        window.location.href = targetUrl
    }, 300)
})

async function getMatchesData() {
    const response = await fetch(getMatchesDataApiUrl)
    return await response.json()
}

function chooseCheckTeams() {
    introContainer.classList.remove("fade_in")
    introContainer.classList.add("fade_out")

    checkTeamsContainer.classList.remove("fade_out")
    checkTeamsContainer.classList.add("fade_in")

    setTimeout(() => {
        introContainer.classList.add("hidden")
        checkTeamsContainer.classList.remove("hidden")
    }, 300)
}

function checkTeamsBack() {
    introContainer.classList.remove("fade_out")
    introContainer.classList.add("fade_in")

    checkTeamsContainer.classList.remove("fade_in")
    checkTeamsContainer.classList.add("fade_out")

    setTimeout(() => {
        introContainer.classList.remove("hidden")
        checkTeamsContainer.classList.add("hidden")
    }, 300)
}


function choseCheckMatches() {
    const tableBody = document.getElementById("matches_table").querySelector("tbody");
    tableBody.innerHTML = ""; // Clear any existing rows

    getMatchesData()
        .then(data => {
            data.forEach((match) => {
                const row = document.createElement("tr");
                row.innerHTML = `
                <td>${match.matchType}</td>
                <td>${match.matchDate}</td>
                <td>${match.homeTeamName}</td>
              <td>${match.homeTeamScore}</td>
              <td>${match.awayTeamScore}</td>
              <td>${match.awayTeamName}</td>
              <td><button>删除</button></td>
              `;
                tableBody.appendChild(row);
            });
        })

    // Hide other containers and show the matches container
    introContainer.classList.remove("fade_in")
    introContainer.classList.add("fade_out")

    checkMatchesContainer.classList.remove("fade_out")
    checkMatchesContainer.classList.add("fade_in")

    setTimeout(() => {
        introContainer.classList.add("hidden")
        checkMatchesContainer.classList.remove("hidden")
    }, 300)


}

// Populate the table with data

function checkMatchesBack() {
    // Hide the matches container and go back to intro
    checkMatchesContainer.classList.remove("fade_in")
    checkMatchesContainer.classList.add("fade_out")

    introContainer.classList.remove("fade_out")
    introContainer.classList.add("fade_in")

    setTimeout(() => {
        introContainer.classList.remove("hidden")
        checkMatchesContainer.classList.add("hidden")
    }, 300)
}
