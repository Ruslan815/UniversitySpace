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
        elem.append(createPublicationElement(x));
    }
}

function searchByInput() {
    var searchString = document.getElementById('searchInput').value.toLowerCase();
    if (searchString == null || searchString == "") {
        return;
    }

    var elem = document.getElementById("searchResult");
    elem.style.display = 'grid';
    elem.innerHTML = "";
    document.getElementById("publicationsList").style.display = 'none'

    for (var x of allPublicationsList) {
        if (x.title.toLowerCase().includes(searchString)) {
            elem.append(createPublicationElement(x));
        }
    }
}

function clearSearchResult() {
    document.getElementById('searchInput').value = "";
    document.getElementById("searchResult").style.display = 'none';
    document.getElementById("searchResult").innerHTML = "";
    document.getElementById("publicationsList").style.display = 'grid'
}

function createPublicationElement(x) {
    var articleElem = document.createElement("article");
    articleElem.setAttribute("class", "text");

    var headerElem = document.createElement("h3");
    if (x.title.length > 30) {
        headerElem.innerHTML = x.title.substring(0,30) + "...";
    } else {
        headerElem.innerHTML = x.title;
    }
    
    var textElem = document.createElement("p");
    if (x.content.length > 150) {
        textElem.innerHTML = x.content.substring(0,150) + "...";
    } else {
        textElem.innerHTML = x.content;
    }
    
    var linkElem = document.createElement("a");
    var linkText = document.createTextNode("Читать");
    linkElem.appendChild(linkText);
    linkElem.title = "Читать";
    linkElem.href = "http://localhost:8080/publication?publicationId=" + x.publicationId;

    articleElem.append(headerElem);
    articleElem.append(textElem);
    articleElem.append(linkElem);

    return articleElem;
}