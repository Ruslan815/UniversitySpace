var allPublicationsList;

function getAllPublicationsList(elem) {
    var url = "http://localhost:8080/api/publications";

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.send(null);
    let message = xmlHttp.responseText;

    var obj = JSON.parse(message);
    allPublicationsList = obj;
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

function searchByInput() {
    var searchString = document.getElementById('searchInput').value.toLowerCase();
    if (searchString == null || searchString == "") {
        return;
    }

    var elem = document.getElementById("searchResult");
    elem.removeAttribute("hidden");
    elem.innerHTML = "";
    document.getElementById("publicationsList").setAttribute("hidden", "hidden");

    for (var x of allPublicationsList) {
        if (x.title.toLowerCase().includes(searchString)) {
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
}

function clearSearchResult() {
    document.getElementById("searchResult").setAttribute("hidden", "hidden");
    document.getElementById("searchResult").innerHTML = "";
    document.getElementById("publicationsList").removeAttribute("hidden");
}