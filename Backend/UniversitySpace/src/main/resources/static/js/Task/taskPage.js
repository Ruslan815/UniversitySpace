var taskId = parseInt(getUrlParam('taskId'), 10);
var taskObject;

function getUrlParam(paramName) {
    var urlParamsText = window.location.search;
    var urlParams = new URLSearchParams(urlParamsText);
    return urlParams.get(paramName);
}

function getTask() {
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
}
