var publicationId = parseInt(getUrlParam('publicationId'), 10);
var publicationObject;

function getUrlParam(paramName) {
    var urlParamsText = window.location.search;
    var urlParams = new URLSearchParams(urlParamsText);
    return urlParams.get(paramName);
}

function getPublication() {
    var url = "http://localhost:8080/publication?publicationId=" + publicationId;

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.send(null);
    let publication = xmlHttp.responseText;

    publicationObject = JSON.parse(publication);
    document.getElementById("publicationTitle").innerHTML = publicationObject.title;
    document.getElementById("publicationAuthor").innerHTML = publicationObject.authorId;
    document.getElementById("publicationContent").value = publicationObject.content;
    publicationObject.content = ""; // for memory economy
}

function enableEditMode() {
    document.getElementById("saveButton").removeAttribute("hidden");
    document.getElementById("publicationContent").removeAttribute("readonly");
}

function updatePublication() {
    var publicationContent = document.getElementById("publicationContent").value;
    var data = JSON.stringify({"publicationId": publicationId, "title": publicationObject.title, "content": publicationContent, 
                                "authorId": publicationObject.authorId, "creationTime": publicationObject.creationTime});
    var xhr = new XMLHttpRequest();
    var url = "http://localhost:8080/publication/update";
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status != 200) {
            alert("Error while send!");
        }
    };

    xhr.send(data);
}

function deletePublication() {
    var data = JSON.stringify({});
    var xhr = new XMLHttpRequest();
    var url = "http://localhost:8080/publication/delete?publicationId=" + publicationId;
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status != 200) {
            alert("Error while send!");
        }
    };

    xhr.send(data);
}

