
const RED_BORDER = '2px solid red';
const BLACK_BORDER = '1px solid black';
const MINIMUM_CHARACTERS = 2;

let titleInputEl;
let labelInputEl;
let contentInputEl;
let sendNewsButtonEl;
let message;

function onSendButtonClicked() {
    const loginFormEl = document.forms['add-news-form'];

    titleInputEl = loginFormEl.querySelector('input[name="title"]');
    labelInputEl = loginFormEl.querySelector('input[name="labels"]');
    contentInputEl = loginFormEl.querySelector('textarea[name="content"]');

    const title = titleInputEl.value;
    const labels = unique(labelInputEl.value.split(/\s+/g));
    const content = contentInputEl.value;

    if (isValidInput(title, labels, content)) {
        const params = new URLSearchParams();
        params.append('title', title);
        params.append('labels', labels);
        params.append('content', content);

        const xhr = new XMLHttpRequest();
        xhr.addEventListener('error', onNetworkError);
        xhr.open('POST', 'add-news');
        xhr.send(params);
        xhr.addEventListener('load', () => {
            handleAddNewsResponse(newsContentDivEl, xhr);
        });
    } else {
        newError(newsContentDivEl, message);
    }
}

function unique(arr) {
    return Array.from(new Set(arr));
}

function goToAllNews() {
    window.location = "index.html";
}

function isValidInput(title, labels, content) {
    clearMessages();
    inputOK([titleInputEl, labelInputEl, contentInputEl]);
    if (title === '' || title.length <= MINIMUM_CHARACTERS) {
        inputWrong(titleInputEl);
        message = 'Incorrect or empty title!'
        return false;
    }
    if (labels.length < 2) {
        if (labels[0] === '') {
            inputWrong(labelInputEl);
            message = 'Incorrect or empty label!'
            return false;
        }
    }
    if (content === '' || content.length <= MINIMUM_CHARACTERS) {
        inputWrong(contentInputEl);
        message = 'Incorrect or empty content!'
        return false;
    }
    return true;
}

function inputWrong(inputEl) {
    inputEl.style.border = RED_BORDER;
}

function inputOK(inputs) {
    for (let i = 0; i < inputs.length; i++) {
        inputs[i].style.border = BLACK_BORDER;
    }
}

function handleAddNewsResponse(targetEl, xhr) {
    if (xhr.status === OK) {
        newInfo(targetEl, 'News sent');
        goToAllNews();
    } else if (xhr.status === BAD_REQUEST) {
        newError(targetEl, xhr.responseText.substring(1, xhr.responseText.length - 1));
    } else {
        onOtherResponse(targetEl, xhr);
    }
}

function onAddNewsLoad() {
    menuContent = document.getElementById('menu-content');
    newsContentDivEl = document.getElementById('add-news-content');

    sendNewsButtonEl = document.getElementById('send-button');
    sendNewsButtonEl.addEventListener('click', onSendButtonClicked);

}

document.addEventListener('DOMContentLoaded', onAddNewsLoad);
