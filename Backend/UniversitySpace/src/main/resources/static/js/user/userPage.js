var username = getUrlParam('username');
var userObj;

function getUserInfo() {
    var url = "http://localhost:8080/api/user?username=" + username;

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.send(null);
    let user = xmlHttp.responseText;

    var userObj = JSON.parse(user);
    /*for (var x of obj) {
      var a = document.createElement('a');
      var linkText = document.createTextNode(x.username);
      a.appendChild(linkText);
      a.title = x.username;
      a.href = "http://localhost:8080/user?userId=" + x.username;

      let userElem = document.createElement('li');
      userElem.append(a);
      elem.append(userElem);
    }*/

    displayUsername();
    displayOnline();
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

function getUrlParam(paramName) {
    var urlParamsText = window.location.search;
    var urlParams = new URLSearchParams(urlParamsText);
    return urlParams.get(paramName);
}