
// Display all news
function onNewsBuild(news) {
    const ulEl = document.createElement("ul");

    for (let i = 0; i < news.length; i++) {
        const singleNews = news[i];

        const liEl = document.createElement('li');
        liEl.id = singleNews.id;
        // Title
        const strongEl = document.createElement('strong');
        strongEl.textContent = singleNews.title;
        liEl.appendChild(strongEl);
        // Date
        const pDate = document.createElement('p');
        pDate.textContent = dateConverter(singleNews.postDate);
        liEl.appendChild(pDate);
        // Content
        const pContent = document.createElement('p');
        pContent.textContent = setCharLimit(singleNews.content);
        liEl.appendChild(pContent);
        // Continue
        const continueButtonEl = document.createElement('button');
        continueButtonEl.textContent = 'Continue';

        continueButtonEl.addEventListener('click', () => {
            onSingleNewsLoad(singleNews.id);
        });
        liEl.appendChild(continueButtonEl);

        liEl.setAttribute('class', 'single-news-li');
        ulEl.appendChild(liEl);
    }
    return ulEl;
}

function onNewsResponse() {
    if (this.status === OK) {
        clearMessages();
        const news = JSON.parse(this.responseText);
        removeAllChildren(newsDivEl);
        newsDivEl.appendChild(onNewsBuild(news));
    } else {
        onOtherResponse(newsDivEl, this);
    }
}

function onNewsLoad() {
    const xhr = new XMLHttpRequest();
    xhr.addEventListener('load', onNewsResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('GET', 'all-news');
    xhr.send();
}

// Asc or Desc ordering
function onOrderedNewsLoad() {
    const orderSelectEl = document.getElementById('order-select');
    const order = orderSelectEl.value;
    const xhr = new XMLHttpRequest();
    const params = new URLSearchParams();
    xhr.addEventListener('load', onNewsResponse);
    xhr.addEventListener('error', onNetworkError);
    xhr.open('POST', 'all-news');
    params.append('order', order);
    xhr.send(params)
}

function setCharLimit(str) {
    return str.substring(0, 200) + '...';
}
