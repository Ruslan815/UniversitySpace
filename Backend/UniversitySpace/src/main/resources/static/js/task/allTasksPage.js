var allTasksList;

function getAllTasksList(elem) {
    var url = "http://localhost:8080/api/tasks";

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.send(null);
    let message = xmlHttp.responseText;

    var obj = JSON.parse(message);
    allTasksList = obj;
    for (var x of obj) {
        elem.append(createCardElement(x));
    }
}

function searchByInput() {
    var searchString = document.getElementById('searchInput').value.toLowerCase();
    if (searchString == null || searchString == "") {
        return;
    }

    var elem = document.getElementById("searchResult");
    elem.style.display = 'grid';
    elem.innerHTML = "";
    document.getElementById("tasksList").style.display = 'none'

    for (var x of allTasksList) {
        if (x.title.toLowerCase().includes(searchString)) {
            elem.append(createCardElement(x));
        }
    }
}

function clearSearchResult() {
    document.getElementById('searchInput').value = "";
    document.getElementById("searchResult").style.display = 'none';
    document.getElementById("searchResult").innerHTML = "";
    document.getElementById("tasksList").style.display = 'grid';
}

function createCardElement(x) {
    var cardElem = document.createElement("div");
    cardElem.setAttribute("class", "flip-card");

    var cardInnerElem = document.createElement("div");
    cardInnerElem.setAttribute("class", "flip-card-inner");

///////////////////////////////////////////////////////////////////

    var frontCardElem = document.createElement("div");
    frontCardElem.setAttribute("class", "flip-card-front");

    var statusElem = document.createElement("h3");
    if (x.status == "Unresolved") {
        statusElem.innerHTML = "Не решена";
    } else {
        statusElem.innerHTML = "Решена";
    }

    var headerElem = document.createElement("h2");
    if (x.title.length > 100) {
        headerElem.innerHTML = x.title.substring(0,100) + "...";
    } else {
        headerElem.innerHTML = x.title;
    }

    frontCardElem.append(statusElem);
    frontCardElem.append(headerElem);

/////////////////////////////////////////////////////////////

    var backCardElem = document.createElement("div");
    backCardElem.setAttribute("class", "flip-card-back");

    var costElem = document.createElement("h3");
    costElem.innerHTML = "Стоимость: " + x.cost;

    var linkElem = document.createElement("a");
    var linkText = document.createTextNode("Посмотреть");
    linkElem.appendChild(linkText);
    linkElem.title = "Посмотреть";
    linkElem.href = "http://localhost:8080/task?taskId=" + x.taskId;

    backCardElem.append(costElem);
    backCardElem.append(linkElem);

///////////////////////////////////////////////////////////////

    cardInnerElem.append(frontCardElem);
    cardInnerElem.append(backCardElem);

    cardElem.append(cardInnerElem);

    return cardElem;
}