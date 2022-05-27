var taskId = parseInt(getUrlParam('taskId'), 10);
var userId;
var taskObject;

function getUrlParam(paramName) {
    var urlParamsText = window.location.search;
    var urlParams = new URLSearchParams(urlParamsText);
    return urlParams.get(paramName);
}

function getTask() {
    var userIdElement = document.getElementById('divUserId');
    userId = parseInt(userIdElement.innerHTML, 10);
    userIdElement.remove(); // for user security

    var url = "http://localhost:8080/api/task?taskId=" + taskId;

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.send(null);
    let task = xmlHttp.responseText;

    taskObject = JSON.parse(task);
    document.getElementById("taskTitle").innerHTML = taskObject.title;
    document.getElementById("taskOwner").innerHTML = "Автор: " + taskObject.ownerUsername;
    document.getElementById("taskDescription").value = taskObject.description;
    taskObject.description = ""; // for memory economy
    document.getElementById("taskCost").innerHTML = "Стоимость: " + taskObject.cost;
    document.getElementById("creationTaskTime").innerHTML = "Дата создания: " + taskObject.creationTime.substring(0, taskObject.creationTime.length - 3);
    document.getElementById("deadline").innerHTML = "Крайний срок сдачи: " + taskObject.deadline;
    
    if (taskObject.status == "Unresolved") {
        document.getElementById("taskStatus").innerHTML = "Статус: Не решена";
        document.getElementById("taskStatus").style.color = "red";
        loadComments(false, null);
    } else {
        document.getElementById("taskStatus").innerHTML = "Статус: Решена";
        document.getElementById("taskStatus").style.color = "green";
        loadComments(true, taskObject.taskCommentSolutionId);
    }
}

function loadComments(isResolved, taskCommentId) {
    var url = "http://localhost:8080/api/task/comments?taskId=" + taskId;
    var elem = document.getElementById("commentsDiv");

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.send(null);
    let comments = xmlHttp.responseText;
    var obj = JSON.parse(comments);
 
    if (!isResolved && userId == taskObject.ownerId) { // mark as resolve
        for (var x of obj) { // creating comments list
            var authorIdElement = document.createElement('p');
            authorIdElement.innerText = x.authorUsername;
            var creationTimeElement = document.createElement('p');
            creationTimeElement.innerText = x.creationTime;

            var commentTextElement = document.createElement('p');
            if (x.text.length > 50) {
                var newCommentContent = "<p>";
                for (let tempStr of x.text.match(/.{1,50}/g)) {
                    newCommentContent += tempStr + "<br>";
                }
                commentTextElement.innerHTML = newCommentContent + "</p>";
            } else {
                commentTextElement.innerHTML = x.text;
            }

            var solutionButton = document.createElement('input');
            solutionButton.setAttribute("type", "button");
            solutionButton.setAttribute("value", "Пометить как решение");
            solutionButton.setAttribute("onclick", "markCommentAsSolution(" + x.commentId + ")");
            solutionButton.style.color = "white";
            solutionButton.style.background = "green";
            var horizontalLineElement = document.createElement('hr');
    
            var someCommentDiv = document.createElement('div');
            someCommentDiv.append(horizontalLineElement);
            someCommentDiv.append(authorIdElement);
            someCommentDiv.append(creationTimeElement);
            someCommentDiv.append(commentTextElement);
            someCommentDiv.append(solutionButton);
    
            elem.append(someCommentDiv);
        }
    } else {
        for (var x of obj) { // creating comments list
            var authorIdElement = document.createElement('p');
            authorIdElement.innerText = x.authorUsername;
            var creationTimeElement = document.createElement('p');
            creationTimeElement.innerText = x.creationTime;

            var commentTextElement = document.createElement('p');
            if (x.text.length > 50) {
                var newCommentContent = "<p>";
                for (let tempStr of x.text.match(/.{1,50}/g)) {
                    newCommentContent += tempStr + "<br>";
                }
                commentTextElement.innerHTML = newCommentContent + "</p>";
            } else {
                commentTextElement.innerHTML = x.text;
            }
            var horizontalLineElement = document.createElement('hr');
    
            var someCommentDiv = document.createElement('div');
            someCommentDiv.append(horizontalLineElement);
            someCommentDiv.append(authorIdElement);
            someCommentDiv.append(creationTimeElement);
            someCommentDiv.append(commentTextElement);

            if (x.commentId == taskCommentId) { // highlight an ANSWER 
                var answerHighlight = document.createElement('p');
                answerHighlight.innerText = "ОТВЕТ"
                answerHighlight.style.color = "green";
                someCommentDiv.append(answerHighlight);
            }
    
            elem.append(someCommentDiv);
        }
    }  
    userId = null; // for user security
}

function markCommentAsSolution(someCommentId) {
    var xhr = new XMLHttpRequest();
    var url = "http://localhost:8080/api/task/resolve?taskId=" + taskId + "&taskCommentId=" + someCommentId;
    xhr.open("POST", url, false); // false - Synchronous request
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status != 200) {
            alert("Error while send: " + xhr.responseText);
        }
    };

    xhr.send();
    window.location.replace("http://localhost:8080/task?taskId=" + taskId);
}

function createTaskComment() {
    var commentText = document.getElementById("newCommentText").value;
    var data = JSON.stringify({"text": commentText, "taskId": taskId});
    var xhr = new XMLHttpRequest();
    var url = "http://localhost:8080/api/task/comment";
    xhr.open("POST", url, false); // false - Synchronous request
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status != 200) {
            alert("Error while send: " + xhr.responseText);
        }
    };

    xhr.send(data);
    window.location.replace("http://localhost:8080/task?taskId=" + taskId);
} 