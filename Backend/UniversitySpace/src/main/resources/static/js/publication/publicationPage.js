var publicationId = parseInt(getUrlParam('publicationId'), 10);
var userId;
var publicationObject;

function getUrlParam(paramName) {
    var urlParamsText = window.location.search;
    var urlParams = new URLSearchParams(urlParamsText);
    return urlParams.get(paramName);
}

function getPublication() {
    userId = parseInt(document.getElementById('divUserId').innerHTML, 10);
    var url = "http://localhost:8080/api/publication?publicationId=" + publicationId;

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.send(null);
    let publication = xmlHttp.responseText;

    publicationObject = JSON.parse(publication);
    document.getElementById("publicationTitle").innerHTML = publicationObject.title;
    document.getElementById("publicationAuthor").innerHTML = publicationObject.authorId;
    document.getElementById("publicationContent").value = publicationObject.content;
    publicationObject.content = ""; // for memory economy
    loadComments();
}

function loadComments() {
    var url = "http://localhost:8080/api/publication/comments?publicationId=" + publicationId;
    var elem = document.getElementById("commentsDiv");

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.send(null);
    let comments = xmlHttp.responseText;

    var obj = JSON.parse(comments);
    for (var x of obj) { // creating comments list
        var authorIdElement = document.createElement('p');
        authorIdElement.innerText = userId;
        var creationTimeElement = document.createElement('p');
        creationTimeElement.innerText = x.creationTime;
        var commentTextElement = document.createElement('p');
        commentTextElement.innerText = x.text;

        var someCommentDiv = document.createElement('div');
        someCommentDiv.append(authorIdElement);
        someCommentDiv.append(creationTimeElement);
        someCommentDiv.append(commentTextElement);

        elem.append(someCommentDiv);
    }
}

function createPublicationComment() {
    var commentText = document.getElementById("newCommentText").value;
                                                                                    // GET USER ID through Thymeleaf
    var data = JSON.stringify({"authorId": userId, "text": commentText, "publicationId": publicationId});
    var xhr = new XMLHttpRequest();
    var url = "http://localhost:8080/api/publication/comment";
    xhr.open("POST", url, false); // false - Synchronous request
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status != 200) {
            alert("Error while send!");
        }
    };

    xhr.send(data);
    window.location.replace("http://localhost:8080/publication?publicationId=" + publicationId);
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
    var url = "http://localhost:8080/api/publication/update";
    xhr.open("POST", url, false); // false - Synchronous request
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status != 200) {
            alert("Error while send!");
        }
    };

    xhr.send(data);
    window.location.replace("http://localhost:8080/publications");
}

function deletePublication() {
    var data = JSON.stringify({});
    var xhr = new XMLHttpRequest();
    var url = "http://localhost:8080/api/publication/delete?publicationId=" + publicationId;
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status != 200) {
            alert("Error while send!");
        }
    };

    xhr.send(data);
    window.location.replace("http://localhost:8080/publications");
}

