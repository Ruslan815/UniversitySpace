function createNewTask() {
    var taskTitle = document.getElementById("taskTitle").value;
    var taskDescription = document.getElementById("taskDescription").value;
    var taskDeadline = document.getElementById("taskDeadline").value;
    var taskCost = document.getElementById("taskCost").value;
                                                                                    // GET USER ID through Thymeleaf
    var data = JSON.stringify({"title": taskTitle, "description": taskDescription, "ownerId": 1, "deadline": taskDeadline.replace("T", " "), "cost": taskCost});
    var xhr = new XMLHttpRequest();
    var url = "http://localhost:8080/api/task";
    xhr.open("POST", url, false); // false - Synchronous request
    xhr.setRequestHeader("Content-Type", "application/json");
    xhr.onreadystatechange = function () {
        if (xhr.readyState === 4 && xhr.status != 200) {
            alert("Error while send!");
        }
    };

    xhr.send(data);
    window.location.replace("http://localhost:8080/tasks");
}