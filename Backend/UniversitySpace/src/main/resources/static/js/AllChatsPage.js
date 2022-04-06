function printText(someText) {
    alert(someText);
}

function getAllChatsList(elem) {
    var url = "http://localhost:8080/chats";

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.send(null);
    let message = xmlHttp.responseText;

    var obj = JSON.parse(message);
    for (var x of obj) {
        var a = document.createElement('a');
        var linkText = document.createTextNode(x.name);
        a.appendChild(linkText);
        a.title = x.name;
        a.href = "http://localhost:8080/messages?chatId=" + x.chatId;

        let messageElem = document.createElement('li');
        messageElem.append(a);
        elem.append(messageElem);
    }
}

function createNewChat(name) {
    var xhr = new XMLHttpRequest();
    var url = "http://localhost:8080/chat";
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert("Chat named: " + name + ", was created!");
        } else if (xhr.status === 500) {
            alert("Chat can't be named: " + name + "!");
        }
    };
    var data = JSON.stringify({"name": name});
    xhr.send(data);
}