function logInAjax() {
	let userName = document.getElementById("userName").value;
	let password = document.getElementById("password").value;
	var param = "userName=" + userName + "&password=" + password;
	let xhr = new XMLHttpRequest();
	xhr.open('POST',
			"http://localhost:8080/project-1-JobDiangkinay/LogInServlet", true);
	xhr.onreadystatechange = function() {
		if (xhr.readyState == 4 && xhr.status == 200) {
			var JSONObject = xhr.responseText;
			if (JSONObject == "false") {
				alert("Check UserName/Password!");
			}
			if (JSONObject == "EMPLOYEE") {
				window.location ="http://localhost:8080/project-1-JobDiangkinay/Employee.html";
			}
			if (JSONObject == "ADMIN") {
				window.location ="http://localhost:8080/project-1-JobDiangkinay/Admin.html";
			}
		}
	};
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(encodeURI(param));
}