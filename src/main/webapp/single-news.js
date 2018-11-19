
// Display a single news
function onSingleNewsBuild(singleNews) {
    const singleNewsDiv = document.createElement('div');
    // Labels
    const labelsDivEl = document.createElement('div');
    labelsDivEl.id = 'labels-content';
    for (let i = 0; i < singleNews.labels.length; i++) {
        const labelpEl = document.createElement('p');
        labelpEl.id = 'single-label-p';
        labelpEl.textContent = singleNews.labels[i].name;
        labelsDivEl.appendChild(labelpEl);
    }
    singleNewsDiv.appendChild(labelsDivEl);
    // Title
    const title = document.createElement("strong");
    title.id = 'title-strong';
    title.textContent = singleNews.title;
    singleNewsDiv.appendChild(title);
    // Date
    const postDate = document.createElement('p');
    postDate.textContent = dateConverter(singleNews.postDate);
    singleNewsDiv.appendChild(postDate);
    // Content
    const content = document.createElement('p');
    content.id = "pTextBox";
    content.textContent = singleNews.content;
    singleNewsDiv.appendChild(content);

    singleNewsDiv.appendChild(turn(singleNews));

    return singleNewsDiv;
}

// Turning
function turn(singleNews) {
    const turnDiv = document.createElement('div');
    turnDiv.id = 'turn-content';
    // Backward
    const leftDivEl = document.createElement('div');
    leftDivEl.id = 'turn-left'
    const leftButton = document.createElement('button');
    leftButton.textContent = '<--'
    leftButton.addEventListener('click', () => {
        onSingleNewsLoad(singleNews.id - 1);
    })
    leftDivEl.appendChild(leftButton);
    // Forward
    const rightDivEl = document.createElement('div');
    rightDivEl.id = 'turn-right';
    const rightButton = document.createElement('button');
    rightButton.textContent = '-->'
    rightButton.addEventListener('click', () => {
        onSingleNewsLoad(singleNews.id + 1);
    })
    rightDivEl.appendChild(rightButton);

    turnDiv.appendChild(leftDivEl);
    turnDiv.appendChild(rightDivEl);
    return turnDiv;
}

function onSingleNewsResponse() {
    if (this.status === OK) {
        clearMessages();
        const singleNews = JSON.parse(this.responseText);
        const orderDivEl = document.getElementById('order-content');
        removeAllChildren(orderDivEl);
        removeAllChildren(newsDivEl);
        newsDivEl.appendChild(onSingleNewsBuild(singleNews));
    } else {
        onOtherResponse(newsDivEl, this);
    }
}

function onSingleNewsLoad(id) {
    const xhr = new XMLHttpRequest();
    const params = new URLSearchParams();
    params.append('id', id);
    xhr.addEventListener('load', onSingleNewsResponse);
    xhr.open('POST', 'single-news');
    xhr.send(params);
}
