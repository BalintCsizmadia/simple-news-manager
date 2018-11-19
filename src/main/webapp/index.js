const OK = 200;
const BAD_REQUEST = 400;
const UNAUTHORIZED = 401;
const NOT_FOUND = 404;
const INTERNAL_SERVER_ERROR = 500;

let menuContent;
let newsDivEl;

function newInfo(targetEl, message) {
    newMessage(targetEl, 'info', message);
}

function newError(targetEl, message) {
    newMessage(targetEl, 'error', message);
}

function newMessage(targetEl, cssClass, message) {
    clearMessages();

    const pEl = document.createElement('p');
    pEl.classList.add('message');
    pEl.classList.add(cssClass);
    pEl.textContent = message;

    targetEl.appendChild(pEl);
}

function clearMessages() {
    const messageEls = document.getElementsByClassName('message');
    for (let i = 0; i < messageEls.length; i++) {
        const messageEl = messageEls[i];
        messageEl.remove();
    }
}

function removeAllChildren(el) {
    while (el.firstChild) {
        el.removeChild(el.firstChild);
    }
}

function onNetworkError(response) {
    document.body.remove();
    const bodyEl = document.createElement('body');
    document.appendChild(bodyEl);
    newError(bodyEl, 'Network error, please try reloading the page');
}

function onOtherResponse(targetEl, xhr) {
    if (xhr.status === NOT_FOUND && xhr.responseText.startsWith('"Y')) {
        newInfo(targetEl, xhr.responseText.substring(1, xhr.responseText.length - 1));
    } else if (xhr.status === NOT_FOUND) {
        newError(targetEl, 'Not found');
        console.error(xhr);
    } else {
        const json = JSON.parse(xhr.responseText);
        if (xhr.status === INTERNAL_SERVER_ERROR) {
            newError(targetEl, `Server error: ${json.message}`);
        } else if (xhr.status === UNAUTHORIZED || xhr.status === BAD_REQUEST) {
            newError(targetEl, json.message);
        } else {
            newError(targetEl, `Unknown error: ${json.message}`);
        }
    }
}

function dateConverter(date) {
    return new Date(date).toLocaleDateString('zh-Hans-CN');
}

function onLoad() {
    menuContent = document.getElementById('menu-content');
    newsDivEl = document.getElementById('news-content');
    if (newsDivEl != null) {
        window.addEventListener('load', onNewsLoad);
    }
}

document.addEventListener('DOMContentLoaded', onLoad);
