function getAllPublicationsList(elem) {
    var url = "http://localhost:8080/api/publications";

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
        a.href = "http://localhost:8080/publication?publicationId=" + x.publicationId;

        let publicationElem = document.createElement('li');
        publicationElem.append(a);
        elem.append(publicationElem);
    }
}