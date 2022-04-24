function createNewPublication() {
    var publicationTitle = document.getElementById("publicationTitle").value;
    var publicationContent = document.getElementById("publicationContent").value;
                                                                                    // GET USER ID through Thymeleaf
    var data = JSON.stringify({"title": publicationTitle, "content": publicationContent, "authorId": 1});
    var xhr = new XMLHttpRequest();
    var url = "http://localhost:8080/publication";
    xhr.open("POST", url, true);
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status != 200) {
            alert("Error while send!");
        }
    };

    xhr.send(data);
    window.location.replace("AllPublicationsPage.html");
}