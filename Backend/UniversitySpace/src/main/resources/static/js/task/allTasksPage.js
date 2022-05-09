var allTasksList;

function getAllTasksList(elem) {
    var url = "http://localhost:8080/api/tasks";

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.send(null);
    let message = xmlHttp.responseText;

    var obj = JSON.parse(message);
    allTasksList = obj;
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

function searchByInput() {
    var searchString = document.getElementById('searchInput').value.toLowerCase();
    if (searchString == null || searchString == "") {
        return;
    }

    var elem = document.getElementById("searchResult");
    elem.removeAttribute("hidden");
    elem.innerHTML = "";
    document.getElementById("tasksList").setAttribute("hidden", "hidden");

    for (var x of allTasksList) {
        if (x.title.toLowerCase().includes(searchString)) {
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
}

function clearSearchResult() {
    document.getElementById('searchInput').value = "";
    document.getElementById("searchResult").setAttribute("hidden", "hidden");
    document.getElementById("searchResult").innerHTML = "";
    document.getElementById("tasksList").removeAttribute("hidden");
}