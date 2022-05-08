function getUserIdByUsername() {
    var username = document.getElementById("usernameInputField").value;
    var url = "http://localhost:8080/api/user/idByUsername?username=" + username;

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status != 200) {
            document.getElementById("hiddenUserId").setAttribute("hidden", "hidden");
            alert("Error while send: " + xmlHttp.responseText);
            return;
        } else {
            let userId = xmlHttp.responseText;
            document.getElementById("hiddenUserId").innerText = "User: " + username + ", have id: " + userId;
            document.getElementById("hiddenUserId").removeAttribute("hidden");
        }
    };

    xmlHttp.send(null);
}

function giveAdminRoleById() {
    var userId = document.getElementById("idInputField").value;
    var url = "http://localhost:8080/admin/user/give/admin?userId=" + userId;
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", url, false); // false - Synchronous request
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status != 200) {
            alert("Error while send: " + xmlHttp.responseText);
        } else {
            alert(xmlHttp.responseText);
        }
    };

    xmlHttp.send(null);
}

function deleteUserById() {
    var userId = document.getElementById("idInputField").value;
    var url = "http://localhost:8080/admin/user/delete?userId=" + userId;
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", url, false); // false - Synchronous request
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status != 200) {
            alert("Error while send: " + xmlHttp.responseText);
        } else {
            alert(xmlHttp.responseText);
        }
    };

    xmlHttp.send(null);
}

function deletePublicationById() {
    var id = document.getElementById("idInputField").value;
    alert(id);
}

function deleteTaskById() {
    var taskId = document.getElementById("idInputField").value;
    var url = "http://localhost:8080/api/task/delete?taskId=" + taskId;
    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("POST", url, false); // false - Synchronous request
    xmlHttp.onreadystatechange = function () {
        if (xmlHttp.readyState === 4 && xmlHttp.status != 200) {
            alert("Error while send: " + xmlHttp.responseText);
        } else {
            alert(xmlHttp.responseText);
        }
    };

    xmlHttp.send(null);
}