function printText(someText) {
    alert(someText);
}

function getAllChatsList(elem) {
    getUserChatsList(document.getElementById('userChatsList'));

    var url = "http://localhost:8080/api/chats";

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.send(null);
    let chats = xmlHttp.responseText;

    var obj = JSON.parse(chats);
    for (var x of obj) {
      var a = document.createElement('a');
      var linkText = document.createTextNode(x.name);
      a.appendChild(linkText);
      a.title = x.name;
      a.href = "http://localhost:8080/chat?chatId=" + x.chatId;

      let chatElem = document.createElement('li');
      chatElem.append(a);
      elem.append(chatElem);
    }
}
 
function getUserChatsList(elem) {
    var userIdElement = document.getElementById('divUserId');
    var userId = parseInt(userIdElement.innerHTML, 10);
    userIdElement.remove(); // for user security

    var url = "http://localhost:8080/api/user/chats?userId=" + userId;

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.send(null);
    let chats = xmlHttp.responseText;

    var obj = JSON.parse(chats);
    for (var x of obj) {
      var a = document.createElement('a');
      var linkText = document.createTextNode(x.name);
      a.appendChild(linkText);
      a.title = x.name;
      a.href = "http://localhost:8080/chat?chatId=" + x.chatId;

      let chatElem = document.createElement('li');
      chatElem.append(a);
      elem.append(chatElem);
    }
}

function createNewChat(name) {
    var xhr = new XMLHttpRequest();
    var url = "http://localhost:8080/api/chat";
    var data = JSON.stringify({"name": name});
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status === 200) {
            alert("Chat named: " + name + ", was created!");
        } else if (xhr.status === 500) {
            alert("Chat can't be named: " + name + "!");
        }
    };
    
    xhr.send(data);
}