var taskId = parseInt(getUrlParam('taskId'), 10);
var userId;
var taskObject;

function getUrlParam(paramName) {
    var urlParamsText = window.location.search;
    var urlParams = new URLSearchParams(urlParamsText);
    return urlParams.get(paramName);
}

function getTask() {
    userId = parseInt(document.getElementById('divUserId').innerHTML, 10);
    var url = "http://localhost:8080/api/task?taskId=" + taskId;

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.send(null);
    let task = xmlHttp.responseText;

    taskObject = JSON.parse(task);
    document.getElementById("taskTitle").innerHTML = taskObject.title;
    document.getElementById("taskOwner").innerHTML = taskObject.ownerId;
    document.getElementById("taskDescription").value = taskObject.description;
    taskObject.description = ""; // for memory economy
    document.getElementById("taskCost").innerHTML = taskObject.cost;
    document.getElementById("creationTaskTime").innerHTML = taskObject.creationTime;
    document.getElementById("deadline").innerHTML = taskObject.deadline;
    document.getElementById("taskStatus").innerHTML = taskObject.status;

    if (taskObject.status == "Unresolved") {
        loadComments(false, null);
    } else {
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
            authorIdElement.innerText = x.authorId;
            var creationTimeElement = document.createElement('p');
            creationTimeElement.innerText = x.creationTime;
            var commentTextElement = document.createElement('p');
            commentTextElement.innerText = x.text;
            var solutionButton = document.createElement('input');
            solutionButton.setAttribute("type", "button");
            solutionButton.setAttribute("value", "Mark as solution");
            solutionButton.setAttribute("onclick", "markCommentAsSolution(" + x.commentId + ")");
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
            authorIdElement.innerText = x.authorId;
            var creationTimeElement = document.createElement('p');
            creationTimeElement.innerText = x.creationTime;
            var commentTextElement = document.createElement('p');
            commentTextElement.innerText = x.text;
            var horizontalLineElement = document.createElement('hr');
    
            var someCommentDiv = document.createElement('div');
            someCommentDiv.append(horizontalLineElement);
            someCommentDiv.append(authorIdElement);
            someCommentDiv.append(creationTimeElement);
            someCommentDiv.append(commentTextElement);

            if (x.commentId == taskCommentId) { // highlight an ANSWER 
                someCommentDiv.append(document.createElement('p').innerText = "ANSWER");
            }
    
            elem.append(someCommentDiv);
        }
    }  
}

function markCommentAsSolution(someCommentId) {
    var xhr = new XMLHttpRequest();
    var url = "http://localhost:8080/api/task/resolve?taskId=" + taskId + "&taskCommentId=" + someCommentId;
    xhr.open("POST", url, false); // false - Synchronous request
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status != 200) {
            alert("Error while send!");
        }
    };

    xhr.send();
    window.location.replace("http://localhost:8080/task?taskId=" + taskId);
}

function createTaskComment() {
    var commentText = document.getElementById("newCommentText").value;
                                                                                    // GET USER ID through Thymeleaf
    var data = JSON.stringify({"authorId": userId, "text": commentText, "taskId": taskId});
    var xhr = new XMLHttpRequest();
    var url = "http://localhost:8080/api/task/comment";
    xhr.open("POST", url, false); // false - Synchronous request
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status != 200) {
            alert("Error while send!");
        }
    };

    xhr.send(data);
    window.location.replace("http://localhost:8080/task?taskId=" + taskId);
}