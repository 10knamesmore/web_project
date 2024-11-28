const backButton = document.getElementById("check_back_button")
const introContainer = document.getElementById("intro_container")
const checkTeamsContainer = document.getElementById("check_team_container")
const checkMatchesContainer = document.getElementById("check_matches_container")

const baseUrl = window.location.protocol + "//" + window.location.host
const getMatchesDataApiUrl = baseUrl + "/api/matchesdata"

function getMatchesData() {
    fetch(getMatchesDataApiUrl)
        .then(response => response.json())
        .then(data => {
            console.log(data)
        })
}

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
    getMatchesData()
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
// Simulated JSON data from backend
const matchesData = [
    {
        event: "世外亚洲",
        date: "2024/11/14",
        homeTeam: "澳大利亚",
        score: "0:0",
        awayTeam: "沙特",
        result: "平",
    },
    {
        event: "世外亚洲",
        date: "2024/10/15",
        homeTeam: "日本",
        score: "1:1",
        awayTeam: "澳大利亚",
        result: "平",
    },
    {
        event: "世外亚洲",
        date: "2024/11/14",
        homeTeam: "澳大利亚",
        score: "0:0",
        awayTeam: "沙特",
        result: "平",
    },
    {
        event: "世外亚洲",
        date: "2024/11/14",
        homeTeam: "澳大利亚",
        score: "0:0",
        awayTeam: "沙特",
        result: "平",
    },
    {
        event: "世外亚洲",
        date: "2024/11/14",
        homeTeam: "澳大利亚",
        score: "0:0",
        awayTeam: "沙特",
        result: "平",
    },
    {
        event: "世外亚洲",
        date: "2024/11/14",
        homeTeam: "澳大利亚",
        score: "0:0",
        awayTeam: "沙特",
        result: "平",
    },
    {
        event: "世外亚洲",
        date: "2024/11/14",
        homeTeam: "澳大利亚",
        score: "0:0",
        awayTeam: "沙特",
        result: "平",
    },
    {
        event: "世外亚洲",
        date: "2024/11/14",
        homeTeam: "澳大利亚",
        score: "0:0",
        awayTeam: "沙特",
        result: "平",
    },
    {
        event: "世外亚洲",
        date: "2024/11/14",
        homeTeam: "澳大利亚",
        score: "0:0",
        awayTeam: "沙特",
        result: "平",
    },
    {
        event: "世外亚洲",
        date: "2024/11/14",
        homeTeam: "澳大利亚",
        score: "0:0",
        awayTeam: "沙特",
        result: "平",
    },
    {
        event: "世外亚洲",
        date: "2024/11/14",
        homeTeam: "澳大利亚",
        score: "0:0",
        awayTeam: "沙特",
        result: "平",
    },
    {
        event: "世外亚洲",
        date: "2024/11/14",
        homeTeam: "澳大利亚",
        score: "0:0",
        awayTeam: "沙特",
        result: "平",
    },
    {
        event: "世外亚洲",
        date: "2024/11/14",
        homeTeam: "澳大利亚",
        score: "0:0",
        awayTeam: "沙特",
        result: "平",
    },
];
// Populate the table with data
const tableBody = document.getElementById("matches_table").querySelector("tbody");
tableBody.innerHTML = ""; // Clear any existing rows

matchesData.forEach((match) => {
    const row = document.createElement("tr");
    row.innerHTML = `
  <td>${match.event}</td>
  <td>${match.date}</td>
  <td>${match.homeTeam}</td>
  <td>${match.score}</td>
  <td>${match.awayTeam}</td>
  <td>${match.result}</td>
  <td><button>删除</button></td>
`;
    tableBody.appendChild(row);
});

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
