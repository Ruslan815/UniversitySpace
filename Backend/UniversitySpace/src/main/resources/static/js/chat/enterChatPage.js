function loadChatName(elem) {
    var chatName = getUrlParam('chatName');
    elem.innerHTML = "Вы хотите войти в чат: " + chatName + "?";
}

function backToAllChats() {
    window.location.replace("http://localhost:8080/chats");
}

function enterChat() {
    var userIdElement = document.getElementById('divUserId');
    var userId = parseInt(userIdElement.innerHTML, 10);
    userIdElement.remove(); // for user security

    var chatId = parseInt(getUrlParam('chatId'), 10);

    var xhr = new XMLHttpRequest();
    var url = "http://localhost:8080/api/chat/enter";
    var data = JSON.stringify({"userId": userId, "chatId": chatId});
    xhr.open("POST", url, false); // false - synchronous
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert("You successfully entered the chat!");
            sendMessage(7, chatId, "Пользователь " + xhr.responseText + " вошёл в чат!");
        } else if (xhr.status === 500) {
            alert("Error while sending: " + xhr.responseText);
        }
    };
    
    xhr.send(data);
    window.location.replace("http://localhost:8080/chats");
}

function getUrlParam(paramName) {
    var urlParamsText = window.location.search;
    var urlParams = new URLSearchParams(urlParamsText);
    return urlParams.get(paramName);
}

function sendMessage(userId, chatId, messageText) {
    var data = JSON.stringify({"userId": userId, "chatId": chatId, "text": messageText});
    var xhr = new XMLHttpRequest();
    var url = "http://localhost:8080/api/message";
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status != 200) {
            alert("Error while send: " + xhr.responseText);
        }
    };

    xhr.send(data);
}