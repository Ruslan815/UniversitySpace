var username = getUrlParam('username');
var userObj;

function getUserInfo() {
    var url = "http://localhost:8080/api/user?username=" + username;

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.send(null);
    let user = xmlHttp.responseText;
    userObj = JSON.parse(user);

    displayUsername();
    displayOnline();
    displayAllUserPublicationsList();
    displayAllUserTasksList();
    displayUserSolvedTaskCount();
}

function displayUsername() {
    document.getElementById('usernameText').innerText = username;
}

function displayOnline() {
    var url = "http://localhost:8080/api/user/online?username=" + username;

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.send(null);
    let isUserOnline = xmlHttp.responseText;

    if (isUserOnline == "true") {
        document.getElementById('isOnlineText').innerText = "Online";
    } else {
        document.getElementById('isOnlineText').innerText = "Offline";
    }
}

function displayAllUserPublicationsList() {
    var elem = document.getElementById('publicationsList');
    var url = "http://localhost:8080/api/publications/user?username=" + username;

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.send(null);
    let message = xmlHttp.responseText;

    var obj = JSON.parse(message);
    for (var x of obj) {
        var a = document.createElement('a');
        var linkText = document.createTextNode(x.title);
        a.appendChild(linkText);
        a.title = x.title;
        a.href = "http://localhost:8080/publication?publicationId=" + x.publicationId;

        let publicationElem = document.createElement('li');
        publicationElem.append(a);
        elem.append(publicationElem);
    }
}

function displayAllUserTasksList() {
    var elem = document.getElementById('tasksList');
    var url = "http://localhost:8080/api/tasks/user?username=" + username;

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.send(null);
    let message = xmlHttp.responseText;

    var obj = JSON.parse(message);
    for (var x of obj) {
        var a = document.createElement('a');
        var linkText = document.createTextNode(x.title);
        a.appendChild(linkText);
        a.title = x.title;
        a.href = "http://localhost:8080/task?taskId=" + x.taskId;

        let taskElem = document.createElement('li');
        taskElem.append(a);
        elem.append(taskElem);
    }
} 

function displayUserSolvedTaskCount() {
    document.getElementById('solvedTaskCountText').innerText = userObj.solvedTaskCount;
}

function getUrlParam(paramName) {
    var urlParamsText = window.location.search;
    var urlParams = new URLSearchParams(urlParamsText);
    return urlParams.get(paramName);
}