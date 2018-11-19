
let statisticsSelectEl;
let statisticsDivEl;
let tableTopEl;

// Display a selected statistics
function onStatisticsBuild(statistics) {
    switch (statisticsSelectEl.value) {
        case 'top':
            return onTopLabelsBuild(statistics);
        case 'most':
            return onMostWordBuild(statistics);
        case 'avg':
            return onCharacterAveragePerDay(statistics);
        case 'day':
            return onNumberOfNewsPerDay(statistics);
        default:
            break;
    }
}

// The number of news / days
function onNumberOfNewsPerDay(statistics) {
    tableTopEl = createHeader('Day', 'News');
    for (let i = 0; i < statistics.length; i++) {
        createTableContent(tableTopEl, dateConverter(statistics[i].date), statistics[i].amount);
    }
    return tableTopEl;
}

// The average of characters / day
function onCharacterAveragePerDay(statistics) {
    tableTopEl = createHeader('Day', 'Characters');
    for (let i = 0; i < statistics.length; i++) {
        createTableContent(tableTopEl, dateConverter(statistics[i].date), statistics[i].amount);;
    }
    return tableTopEl;
}

// The news which contains the most words
function onMostWordBuild(statistics) {
    tableTopEl = createHeader('News', 'Words');
    createTableContent(tableTopEl, statistics.title, statistics.numberOfWords);
    return tableTopEl;

}

// Top 10 label in use
function onTopLabelsBuild(statistics) {
    tableTopEl = createHeader('Label', 'Occurrences');
    for (let i = 0; i < statistics.length; i++) {
        createTableContent(tableTopEl, statistics[i].labelName, statistics[i].occurrences);
    }
    return tableTopEl;
}

function createHeader(header1, header2) {
    const tableEl = document.createElement('table');
    tableEl.id = 'statistics-table'

    const trHeadEl = document.createElement('tr');
    // Create first column
    const thFirstHeaderEl = document.createElement('th')
    thFirstHeaderEl.textContent = header1;
    trHeadEl.appendChild(thFirstHeaderEl);
    // Create second column
    const thSecondHeaderEl = document.createElement('th');
    thSecondHeaderEl.textContent = header2;
    trHeadEl.appendChild(thSecondHeaderEl);
    tableEl.appendChild(trHeadEl);
    return tableEl;
}

function createTableContent(tableTopEl, param1, param2) {
    // Create row for data
    const trEl = document.createElement('tr');
    // first data
    const tdFirstEl = document.createElement('td');
    tdFirstEl.appendChild(document.createTextNode(`${param1}`));
    trEl.appendChild(tdFirstEl);
    // second data
    const tdSecondEl = document.createElement('td');
    tdSecondEl.appendChild(document.createTextNode(`${param2}`));
    trEl.appendChild(tdSecondEl);
    // add row to table
    tableTopEl.appendChild(trEl);
}

function onStatisticsResponse() {
    if (this.status === OK) {
        clearMessages();
        const statistics = JSON.parse(this.responseText);
        removeAllChildren(statisticsDivEl);
        statisticsDivEl.appendChild(onStatisticsBuild(statistics));
    } else {
        onOtherResponse(statisticsDivEl, this);
    }
}

function onStatisticsSelect() {
    const option = statisticsSelectEl.value;
    const xhr = new XMLHttpRequest();
    const params = new URLSearchParams();
    xhr.addEventListener('load', onStatisticsResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'statistics');
    if (option !== null) {
        params.append('option', option);
    }
    xhr.send(params)
}

function onDefaultStatisticsResponse() {
    if (this.status === OK) {
        clearMessages();
        const statistics = JSON.parse(this.responseText);
        removeAllChildren(statisticsDivEl);
        statisticsDivEl.appendChild(onTopLabelsBuild(statistics));
    } else {
        onOtherResponse(statisticsSelectEl, this);
    }
}

// 'Top 10 label in use' statistics is the default
function onDefaultStatisticsLoad() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onDefaultStatisticsResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'statistics');
    xhr.send();
}

function onStatisticsLoad() {
    menuContent = document.getElementById('menu-content');
    statisticsSelectEl = document.getElementById('statistics-select');
    statisticsDivEl = document.getElementById('statistics-content');
    window.addEventListener('load', onDefaultStatisticsLoad);
}

document.addEventListener('DOMContentLoaded', onStatisticsLoad);
