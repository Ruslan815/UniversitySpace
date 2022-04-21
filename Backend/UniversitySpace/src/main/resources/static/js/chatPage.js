function getAllChatMessages() {
    var url = "http://localhost:8080/messages?chatId=" + chatId;

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
}

function sendMessage() {
    var messageText = document.getElementById("sendMessageContent").value;
    var data = JSON.stringify({"userId": userId, "chatId": chatId, "text": messageText});
    var xhr = new XMLHttpRequest();
    var url = "http://localhost:8080/message";
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status != 200) {
            alert("Error while send!");
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
    var chatSubscribeUrl = 'http://localhost:8080/messages/unread?userId=' + userId + '&chatId=' + chatId;
    
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

function getUrlParam(paramName) {
    var urlParamsText = window.location.search;
    var urlParams = new URLSearchParams(urlParamsText);
    return urlParams.get(paramName);
}

function addNewMessageToMessagesList(message) {
    var messagesListElement = document.getElementById('messages');
    var messageElement = document.createElement("li");
    messageElement.innerHTML = "USER: " + message.userId + "; TEXT: " + message.text + "; TIME: " + message.sendTime;
    messagesListElement.append(messageElement);
}

function scrollMessagesList() {
    var messagesListElement = document.getElementById('messages');
    messagesListElement.scrollTop = messagesListElement.scrollHeight;
}


var chatId = parseInt(getUrlParam('chatId'), 10);
var userId = parseInt(getUrlParam('userId'), 10);
console.log("CHAT_ID: " + chatId + " : " + typeof chatId);
console.log("USER_ID: " + userId + " : " + typeof userId);
var isListening = false;