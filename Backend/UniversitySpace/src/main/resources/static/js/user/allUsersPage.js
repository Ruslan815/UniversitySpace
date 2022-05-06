function getAllUsersList(elem) {
    var url = "http://localhost:8080/api/users";

    var xmlHttp = new XMLHttpRequest();
    xmlHttp.open("GET", url, false); // false - Synchronous request
    xmlHttp.send(null);
    let users = xmlHttp.responseText;

    var obj = JSON.parse(users);
    for (var x of obj) {
      var a = document.createElement('a');
      var linkText = document.createTextNode(x.username);
      a.appendChild(linkText);
      a.title = x.username;
      a.href = "http://localhost:8080/user?userId=" + x.username;

      let userElem = document.createElement('li');
      userElem.append(a);
      elem.append(userElem);
    }
}