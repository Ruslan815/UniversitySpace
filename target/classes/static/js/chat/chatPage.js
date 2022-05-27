var chatId = parseInt(getUrlParam('chatId'), 10);
var chatName = getUrlParam('chatName');
var userId;
var isListening = false;

function getAllChatMessages() {
    var userIdElement = document.getElementById('divUserId');
    userId = parseInt(userIdElement.innerHTML, 10);
    userIdElement.remove(); // for user security
    // console.log("CHAT_ID: " + chatId + " : " + typeof chatId);
    // console.log("USER_ID: " + userId + " : " + typeof userId);
    document.getElementById("chatNameElem").innerHTML = chatName;

    var url = "http://localhost:8080/api/messages?chatId=" + chatId + "&userId=" + userId;

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.send(null);
    let allChatMessages = xmlHttp.responseText;

    var obj = JSON.parse(allChatMessages);
    obj.reverse();
    for (var x of obj) {
        addNewMessageToMessagesList(x);
    }
    scrollMessagesList();

    startListenChat();
}

function sendMessage() {
    var messageText = document.getElementById("sendMessageContent").value;
    if (messageText == null || messageText == "") {
        return;
    }
    document.getElementById("sendMessageContent").value = "";

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

function startListenChat() {
    if (isListening) {
        alert("chat already listening!");
        return;
    }

    isListening = true;
    var chatSubscribeUrl = 'http://localhost:8080/api/messages/unread?userId=' + userId + '&chatId=' + chatId;

    subscribe();

    async function subscribe() {
        let response = await fetch(chatSubscribeUrl);

        if (response.status == 408) { // Request timeout
            await subscribe();
        } else if (response.status != 200) { // Some error
            alert("Error: " + response.statusText + " while getting message! Trying again...");
            await new Promise(resolve => setTimeout(resolve, 1000)); // Wait 1 second
            await subscribe(); // Try again
        } else { // Success
            let unreadMessages = await response.text();

            var obj = JSON.parse(unreadMessages);
            for (var x of obj) {
                addNewMessageToMessagesList(x);
            }
            scrollMessagesList();
            await subscribe();
        }
    }
}

function tempAlert(msg, duration) {
    var el = document.createElement("div");
    el.setAttribute("style", "font-size: 25px; position:absolute; top:40%;left:20%; background-color: orange; color: white;");
    el.innerHTML = msg;
    document.getElementById("sendMessageContent").readOnly = true;
    setTimeout(function () {
        el.parentNode.removeChild(el);
        document.getElementById("sendMessageContent").readOnly = false;
        document.location.reload();
    }, duration);
    document.body.appendChild(el);
}

function leaveChat() {
    var xhr = new XMLHttpRequest();
    var url = "http://localhost:8080/api/chat/leave";
    var data = JSON.stringify({"userId": userId, "chatId": chatId});
    xhr.open("POST", url, false); // false - synchronous
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert("You successfully left the chat!");
            sendLeaveMessage(7, chatId, "Пользователь " + xhr.responseText + " вышел из чата!");
        } else if (xhr.status === 500) {
            alert("Error while sending: " + xhr.responseText);
        }
    };

    xhr.send(data);
    window.location.replace("http://localhost:8080/chats");
}

function sendLeaveMessage(userId, chatId, messageText) {
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

function getUrlParam(paramName) {
    var urlParamsText = window.location.search;
    var urlParams = new URLSearchParams(urlParamsText);
    return urlParams.get(paramName);
}

function addNewMessageToMessagesList(message) {
    var messagesListElement = document.getElementById('messages');
    var messageElement = document.createElement("li");

    var timeElement = document.createElement("div");
    timeElement.style.color = 'grey';
    timeElement.style.fontSize = "15px";
    timeElement.innerHTML = message.sendTime.substring(0, message.sendTime.length - 3);

    var messageTextElement = document.createElement("div");
    messageTextElement.style.color = 'black';
    messageTextElement.style.fontSize = "20px";
    if (message.username.length + message.text.length > 65) {
        var newMessageContent = "<p>";
        for (let tempStr of message.text.match(/.{1,65}/g)) {
            newMessageContent += tempStr + "<br>";
        }
        messageTextElement.innerHTML = message.username + ":<br>" + newMessageContent + "</p>";
    } else {
        messageTextElement.innerHTML = message.username + ": " + message.text;
    }

    messageElement.appendChild(timeElement);
    messageElement.appendChild(messageTextElement);

    messagesListElement.append(messageElement);
}

function scrollMessagesList() {
    var messagesListElement = document.getElementById('messages');
    messagesListElement.scrollTop = messagesListElement.scrollHeight;
}