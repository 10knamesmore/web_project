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
    return (await fetch(getMatchesDataApiUrl)).json()
}

async function getAllTeams() {
    return await fetch("/api/getallteams")
        .then((response) => response.json())
}

async function getMatchesByTeamId(teamId) {
    return (await fetch((`/api/getmatchesbyteamid?teamId=${teamId}`))).json()
}

async function chooseCheckTeams() {
    const teams = await getAllTeams()

    setTimeout(() => {
        introContainer.classList.add("hidden")
        checkTeamsContainer.classList.remove("hidden")
    }, 300)

    introContainer.classList.remove("fade_in")
    introContainer.classList.add("fade_out")

    checkTeamsContainer.classList.remove("fade_out")
    checkTeamsContainer.classList.add("fade_in")

    const teamsList = []
    for (let i = 0; i < teams.length; i++) {
        teamsList.push({id: teams[i].id, name: teams[i].name})
    }
    const teamList = document.getElementById("team_list");

    // 初始化左侧队伍列表
    teamsList.forEach((team) => {
        const li = document.createElement("li");
        li.textContent = team.name;
        li.setAttribute("team_id", team.id);
        li.addEventListener("click", () => loadTeamData(team.id, li));
        teamList.appendChild(li);
    });
}

function checkTeamsBack() {
    setTimeout(() => {
        introContainer.classList.remove("hidden")
        checkTeamsContainer.classList.add("hidden")
    }, 300)
    introContainer.classList.remove("fade_out")
    introContainer.classList.add("fade_in")

    checkTeamsContainer.classList.remove("fade_in")
    checkTeamsContainer.classList.add("fade_out")

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
    setTimeout(() => {
        introContainer.classList.add("hidden")
        checkMatchesContainer.classList.remove("hidden")
    }, 300)

    // Hide other containers and show the matches container
    introContainer.classList.remove("fade_in")
    introContainer.classList.add("fade_out")

    checkMatchesContainer.classList.remove("fade_out")
    checkMatchesContainer.classList.add("fade_in")


}

// Populate the table with data

function checkMatchesBack() {
    setTimeout(() => {
        introContainer.classList.remove("hidden")
        checkMatchesContainer.classList.add("hidden")
    }, 300)
    // Hide the matches container and go back to intro
    checkMatchesContainer.classList.remove("fade_in")
    checkMatchesContainer.classList.add("fade_out")

    introContainer.classList.remove("fade_out")
    introContainer.classList.add("fade_in")

}

async function loadTeamData(teamId, li) {
    document.getElementById("matches_data_tablebody").innerHTML = ""; // 清空之前的内容

    // 高亮当前选中的队伍
    document.querySelectorAll(".sidebar li")
        .forEach(
            (el) => el.classList.remove("active"));
    li.classList.add("active");

    // 获取对应队伍的数据
    const matches = await getMatchesByTeamId(teamId);


    const matchData = document.getElementById("matches_data_tablebody");
    // 更新比赛数据表
    for (let i = 0; i < matches.length; i++) {
        const match = matches[i]
        const html =
            `<tr>
              <td>${match.matchType}</td>
              <td>${match.matchDate}</td>
              <td>${match.enemyTeamName}</td>
              <td>${match.Scores}</td>
            </tr>`
        matchData.innerHTML += html
    }

    // 更新折线图
    updateChart(matches);
}

function updateChart(matches) {
    const chart = document.getElementById("chart");
    chart.innerHTML = ""; // 清空之前的内容

    const canvas = document.createElement("canvas");
    canvas.width = chart.offsetWidth;
    canvas.height = chart.offsetHeight;
    chart.appendChild(canvas);

    const ctx = canvas.getContext("2d");
    const padding = 40;
    const width = canvas.width - 2 * padding;
    const height = canvas.height - 2 * padding;

    // 数据
    const goals = matches.map((m) => m.scores);
    const maxGoals = Math.max(...goals, 5);
    const step = width / (goals.length - 1);

    // 绘制坐标轴
    ctx.beginPath();
    ctx.moveTo(padding, padding);
    ctx.lineTo(padding, padding + height);
    ctx.lineTo(padding + width, padding + height);
    ctx.stroke();

    // 绘制折线图
    ctx.beginPath();
    goals.forEach((goal, i) => {
        const x = padding + i * step;
        const y = padding + height - (goal / maxGoals) * height;
        if (i === 0) ctx.moveTo(x, y);
        else ctx.lineTo(x, y);
        ctx.arc(x, y, 3, 0, 2 * Math.PI); // 节点圆点
    });
    ctx.stroke();
}