var allUsersList;

function getAllUsersList(elem) {
    var url = "http://localhost:8080/api/users";

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.send(null);
    let users = xmlHttp.responseText;

    var obj = JSON.parse(users);
    allUsersList = obj;
    for (var x of obj) {
        elem.append(createUserElement(x));
    }
}

function showOnlineUsersList(elem) {
    showAllUsersList();
    var url = "http://localhost:8080/api/users/online";

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.send(null);
    let users = xmlHttp.responseText;

    var obj = JSON.parse(users);
    for (var x of obj) {
        elem.append(createUserElement(x));
    }

    document.getElementById("usersList").style.display = 'none';
    document.getElementById("onlineUsersList").style.display = 'grid';
    document.getElementById("showOnlineButton").setAttribute("hidden", "hidden");
    document.getElementById("showAllButton").removeAttribute("hidden");
}

function searchByInput() {
    var searchString = document.getElementById('searchInput').value.toLowerCase();
    if (searchString == null || searchString == "") {
        return;
    }
    showAllUsersList();

    var elem = document.getElementById("searchResult");
    elem.style.display = 'grid';
    elem.innerHTML = "";
    document.getElementById("usersList").style.display = 'none';

    for (var x of allUsersList) {
        if (x.username.toLowerCase().includes(searchString)) {
            elem.append(createUserElement(x));
        }
    }
}

function showAllUsersList() {
    document.getElementById('searchInput').value = "";
    document.getElementById("searchResult").innerHTML = "";
    document.getElementById("searchResult").style.display = 'none';
    document.getElementById("onlineUsersList").innerHTML = "";
    document.getElementById("onlineUsersList").style.display = 'none';
    document.getElementById("usersList").style.display = 'grid';
    document.getElementById("showOnlineButton").removeAttribute("hidden");
    document.getElementById("showAllButton").setAttribute("hidden", "hidden");
}

function createUserElement(x) {
    var articleElem = document.createElement("article");
    articleElem.setAttribute("class", "text");

    var headerElem = document.createElement("h3");
    headerElem.innerHTML = x.username + " " + x.roles;
    
    var textElem = document.createElement("p");
    if (x.solvedTaskCount == null || x.solvedTaskCount == 0) {
        textElem.innerHTML = "Пока ещё нет решённых задач";
    } else {
        textElem.innerHTML = "Решено задач: " + x.solvedTaskCount;
    }
    
    var linkElem = document.createElement("a");
    var linkText = document.createTextNode("Открыть профиль");
    linkElem.appendChild(linkText);
    linkElem.title = "Открыть профиль";
    linkElem.href = "http://localhost:8080/user?username=" + x.username;

    articleElem.append(headerElem);
    articleElem.append(textElem);
    articleElem.append(linkElem);

    return articleElem;
}