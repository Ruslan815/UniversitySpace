var userId;
var allChatsList;

function getAllChatsList(elem) {
    getUserChatsList(document.getElementById('userChatsList'));

    var url = "http://localhost:8080/api/chats";

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.send(null);
    let chats = xmlHttp.responseText;

    var obj = JSON.parse(chats);
    allChatsList = obj;
    for (var x of obj) {
        elem.append(createChatElement(x));
    }
}

function getUserChatsList(elem) {
    var userIdElement = document.getElementById('divUserId');
    userId = parseInt(userIdElement.innerHTML, 10);
    userIdElement.remove(); // for user security

    var url = "http://localhost:8080/api/user/chats?userId=" + userId;

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.send(null);
    let chats = xmlHttp.responseText;

    var obj = JSON.parse(chats);
    for (var x of obj) {
        elem.append(createChatElement(x));
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
    document.getElementById("chatsListDiv").style.display = 'none';

    for (var x of allChatsList) {
        if (x.name.toLowerCase().includes(searchString)) {
            elem.append(createChatElement(x));
        }
    }
}

function clearSearchResult() {
    document.getElementById('searchInput').value = "";
    document.getElementById("searchResult").innerHTML = "";
    document.getElementById("searchResult").style.display = 'none';
    document.getElementById("chatsListDiv").style.display = 'grid';
}

function createNewChat(name) {
    var xhr = new XMLHttpRequest();
    var url = "http://localhost:8080/api/chat";
    var data = JSON.stringify({ "name": name });
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert("Chat named: " + name + ", was created!");
            enterChat(JSON.parse(xhr.responseText).chatId);
        } else if (xhr.status === 500) {
            alert("Chat can't be named: " + name + "!");
        }
    };

    xhr.send(data);
}

function enterChat(chatId) {
    var xhr = new XMLHttpRequest();
    var url = "http://localhost:8080/api/chat/enter";
    var data = JSON.stringify({ "userId": userId, "chatId": chatId });
    xhr.open("POST", url, false); // false - synchronous
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert("You successfully entered the chat!");
            sendMessage(7, chatId, "User " + xhr.responseText + " joined to this chat!");
        } else if (xhr.status === 500) {
            alert("Error while sending: " + xhr.responseText);
        }
    };

    xhr.send(data);
    window.location.replace("http://localhost:8080/chats");
}

function createChatElement(x) {
    var articleElem = document.createElement("article");
    articleElem.setAttribute("class", "text");

    var headerElem = document.createElement("h3");
    headerElem.innerHTML = x.name;

    var textElem = document.createElement("p");
    if (x.membersCount == null || x.membersCount == 0) {
        textElem.innerHTML = "Пока ещё нет участников";
    } else {
        textElem.innerHTML = "Количество участников: " + x.membersCount;
    }

    var linkElem = document.createElement("a");
    var linkText = document.createTextNode("Войти в чат");
    linkElem.appendChild(linkText);
    linkElem.title = "Войти в чат";
    linkElem.href = "http://localhost:8080/chat?chatId=" + x.chatId + "&chatName=" + x.name;

    articleElem.append(headerElem);
    articleElem.append(textElem);
    articleElem.append(linkElem);

    return articleElem;
}