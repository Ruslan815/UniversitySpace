function createNewPublication() {
    var publicationTitle = document.getElementById("publicationTitle").value;
    var publicationContent = document.getElementById("publicationContent").value;
    var data = JSON.stringify({"title": publicationTitle, "content": publicationContent});
    var xhr = new XMLHttpRequest();
    var url = "http://localhost:8080/api/publication";
    xhr.open("POST", url, false); // false - Synchronous request
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status != 200) {
            alert("Error while send: " + xhr.responseText);
        }
    };

    xhr.send(data);
    window.location.replace("http://localhost:8080/publications");
}