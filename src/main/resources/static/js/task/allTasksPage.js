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
        statusElem.style.color = "red";
    } else {
        statusElem.innerHTML = "Решена";
        statusElem.style.color = "green";
    }
    frontCardElem.append(statusElem);

    var headerElem = document.createElement("h2");
    if (x.title.length > 60) {
        var headerElem1 = document.createElement("h2");
        var headerElem2 = document.createElement("h2");
        var headerElem3 = document.createElement("h2");
        headerElem.innerHTML = x.title.substring(0,20);
        headerElem1.innerHTML = x.title.substring(20,40);
        headerElem2.innerHTML = x.title.substring(40,60);
        headerElem3.innerHTML = x.title.substring(60,x.title.length) + "...";
        frontCardElem.append(headerElem);
        frontCardElem.append(headerElem1);
        frontCardElem.append(headerElem2);
        frontCardElem.append(headerElem3);
    } else if (x.title.length > 40) {
        var headerElem1 = document.createElement("h2");
        var headerElem2 = document.createElement("h2");
        headerElem.innerHTML = x.title.substring(0,20);
        headerElem1.innerHTML = x.title.substring(20,40);
        headerElem2.innerHTML = x.title.substring(40,x.title.length);
        frontCardElem.append(headerElem);
        frontCardElem.append(headerElem1);
        frontCardElem.append(headerElem2);
    } else if (x.title.length > 20) {
        var headerElem1 = document.createElement("h2");
        headerElem.innerHTML = x.title.substring(0,20);
        headerElem1.innerHTML = x.title.substring(20,x.title.length);
        frontCardElem.append(headerElem);
        frontCardElem.append(headerElem1);
    } else {
        headerElem.innerHTML = x.title;
        frontCardElem.append(headerElem);
    }

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