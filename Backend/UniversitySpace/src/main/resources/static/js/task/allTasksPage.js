function getAllTasksList(elem) {
    var url = "http://localhost:8080/api/tasks";

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.send(null);
    let message = xmlHttp.responseText;

    var obj = JSON.parse(message);
    for (var x of obj) {
        var a = document.createElement('a');
        var linkText = document.createTextNode(x.title);
        a.appendChild(linkText);
        a.title = x.title;
        a.href = "http://localhost:8080/task?taskId=" + x.taskId;

        let taskElem = document.createElement('li');
        taskElem.append(a);
        elem.append(taskElem);
    }
}