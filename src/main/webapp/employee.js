
function empDetails(JSONObject){
    return `<div class="secondary-block">
            <h3>Name: ${JSONObject.firstName} ${JSONObject.lastName}</h3>
            <h3>Phone Number: ${JSONObject.phoneNumber}</h3>
            <h3>Email Address: ${JSONObject.eMail}</h3>
            <button onClick="showUpdateInfo()">Update Info</button>
            </div>
    `
}

window.onload = firstLoad();
	
function firstLoad(){
	let xhr = new XMLHttpRequest();
	xhr.open('POST', "http://localhost:8080/project-1-JobDiangkinay/EmployeeServlet", true);
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			try{
			var JSONObject = JSON.parse(xhr.responseText);
			}catch(e){
				window.location ="http://localhost:8080/project-1-JobDiangkinay/";
			}
			var employeeInfo = document.getElementById("employeeInfo");
			employeeInfo.innerHTML = "";
			var resultfirstName = JSONObject.firstName;
			var resultlastName = JSONObject.lastName;
			var resultphoneNumber = JSONObject.phoneNumber;
			var resultEmail = JSONObject.eMail;
			employeeInfo.innerHTML = empDetails(JSONObject);
			// loadAllReim();
		}
	};
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send(encodeURI("methodname=loademp"));
}

function submitNewRequest(){
	let newAmount = document.getElementById("newAmount").value;
	let newDesc = document.getElementById("newDesc").value;
	let newType = document.getElementById("newType").value;
	var countDecimals = function (value) {
	    if(Math.floor(value) == value) return 0;
	    return value.toString().split(".")[1].length || 0; 
	}
	let num = false;
	try{
		num = newAmount.match(/[.]/g).length >= 2; 
	}catch(Exception){
		
	}
	if(newAmount < 0 || countDecimals(newAmount) > 2 || newAmount == "" || num){
		alert("Invalid Amount!");
		return;
	}
	if(newDesc == ""){
		alert("Invalid Description!");
		return;
	}
	var param = "methodname=newReim&newAmount="+newAmount+"&newDesc="+newDesc+"&newType="+newType;
	let xhr = new XMLHttpRequest();
	xhr.open('POST', "http://localhost:8080/project-1-JobDiangkinay/EmployeeServlet", true);
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			console.log(xhr.response);
			closeCreateForm();
			loadAllReim();
			var JSONObject = JSON.parse(xhr.responseText);
			if(JSONObject){
				alert("Submitted Successfully");
			}else{
				alert("Operation Failed");
			}	
		}
	};
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send(encodeURI(param));
}

function loadAllReim(){
	let xhr = new XMLHttpRequest();
	xhr.open('POST', "http://localhost:8080/project-1-JobDiangkinay/EmployeeServlet", true);
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			var JSONObject = JSON.parse(xhr.responseText);
			var html = '<table id="reimbursementTable" class="table table-striped table-dark table-bordered">'; // string
			html += '<thead class="thead-dark"><tr><td>Reimbursement ID</td>';
			html += '<td>Type</td>';
			html += '<td>Description</td>';
			html += '<td>Status</td>';
			html += '<td>Amount</td>';
			html += '<td>Date Created</td></tr></thead><tbody>';
		    for (var i = 0, len = JSONObject.length; i < len; i++) {
		        html += '<tr>';
		        html += '<td>' + JSONObject[i].ReimbursementId + '</td>';
		        html += '<td>' + JSONObject[i].ReimbursementType + '</td>';
		        html += '<td>' + JSONObject[i].ReimbursementDesc + '</td>';
		        html += '<td>' + JSONObject[i].Status + '</td>';
		        html += '<td>$' + JSONObject[i].Amount + '</td>';
		        html += '<td>' + JSONObject[i].DateCreated + '</td>';
		        html += '</tr>';
		    }
		    html += '</tbody></table>';
		    var div = document.getElementById('employeeInfoTwo');
		    div.innerHTML = html;
		    dataTableRun();
		  
			
		}
	};
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send(encodeURI("methodname=loadAllReim"));
}

function dataTableRun(){
     $('#reimbursementTable').DataTable();
}

function updateEmployee(){
	let editType = document.getElementById("editType").value;
	let editField = document.getElementById("editField").value;
	function validateEmail(email) 
	{
	    var re = /\S+@\S+\.\S+/;
	    return re.test(email);
	}
	let emailCheck;
	if (editType == "eMail"){
		emailCheck = validateEmail(editField); 
		if(!emailCheck){
			document.getElementById("editField").value = "";
			alert("Invalid Email");
			return;
		}
	}
	var param = "methodname=updateInfo&editType="+editType+"&editField="+editField;
	let xhr = new XMLHttpRequest();
	if(editField != ""){
	xhr.open('POST', "http://localhost:8080/project-1-JobDiangkinay/EmployeeServlet", true);
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			console.log(xhr.response);
			//loadAllReim();
			// var JSONObject = JSON.parse(xhr.responseText);
			
		}
	};
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send(encodeURI(param));
	}
}

function showUpdateInfo(){
	let div2 = document.getElementById('updateInfo');
	div2.innerHTML=`
	<div class="secondary-block">
	<div>
				<button onClick="closeUpdateInfo()" type="button" class="close" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div>
			<div>
				<form method="POST">
					<h3>Update Information</h3>
					<select name="editCode" id="editType" onchange="showfield(this.options[this.selectedIndex].value)">
						<option selected="selected">Please select ...</option>
						<option value="eMail">Email</option>
						<option value="phoneNumber">Phone Number</option>
					</select> 
					<label id="div1"></label>
					<label id="button1"></label>
				</form>
			</div></div>`;
}

function showfield(name){
	  if(name=='eMail'){
		  document.getElementById('div1').innerHTML= '';
		  document.getElementById('div1').innerHTML='Value: <input type="email" id="editField" name="editValue" size="20" required />';
		  document.getElementById('button1').innerHTML='<button onClick="updateEmployee()">Confirm</button>';
	  }
	  else if(name=='phoneNumber'){
		  document.getElementById('div1').innerHTML= '';
		  document.getElementById('div1').innerHTML='Value: <input type="number" id="editField" name="editValue" size="20" required />';
		  document.getElementById('button1').innerHTML='<button onClick="updateEmployee()">Confirm</button>';
	  }
	  else{
		  document.getElementById('div1').innerHTML='';
		  document.getElementById('button1').innerHTML='';
	  }
	}

function showCreateForm(){
	let div = document.getElementById('createForm');
	div.innerHTML=`
	<div  class="secondary-block">
			<form id="createImgForm">
				<h3>Create new Reimbursement</h3>
				<br /> <label>Reimbursement Type</label> <select name="reimType"
					id="newType">
					<option value="Relocation">Relocation</option>
					<option value="Certification">Certification</option>
					<option value="Others">Others</option>
				</select> <br /> 
				<table>
				<tr><td>Amount:</td><td><input type="text" oninput="numberOnly(this.id);" id="newAmount" name="amount" step="0.01" min="0" maxlength="7"/></td></tr> 
				<tr><td>Description:</td> <td><input type="text" id="newDesc" name="reimDesc" maxlength="30" /></td></tr>
				</table>
					<input id="inputFileToLoad" type="file" name="pic" style="margin-top: 8px;" accept="image/*"><br/>
					<input
					type="button" style="margin-top: 8px;" onClick="tryImgAJAX()"
					value="Submit Reimbursement" />
			</form>
			</div>`;
}

function numberOnly(id) {
    // Get element by id which passed as parameter within HTML element event
    var element = document.getElementById(id);
    // Use numbers only pattern, from 0 to 9
    var regex = /[^0-9.]/gi;
    // This removes any other character but numbers as entered by user
    element.value = element.value.replace(regex, "");
}

function tryImgAJAX(){
	var filesSelected = document.getElementById('inputFileToLoad').files;
	var srcData;
	if (filesSelected.length > 0) {
		var fileToLoad = filesSelected[0];

		var fileReader = new FileReader();

		fileReader.onload = function(fileLoadedEvent) {
		srcData = fileLoadedEvent.target.result; // <--- data: base64
		submitNewRequestWithImg(srcData);
		}
		fileReader.readAsDataURL(fileToLoad);
	}else{
		submitNewRequest();
	}
}

function submitNewRequestWithImg(img64){
	let newImg = img64;
	let newAmount = document.getElementById("newAmount").value;
	let newDesc = document.getElementById("newDesc").value;
	let newType = document.getElementById("newType").value;
	if(newAmount < 0){
		alert("Invalid Amount!");
		return;
	}
	const escapeRegExp = (string) => {
		 // return string.replace(/[.*+?^${}()|[\]\\]/g, '%2B')
		return string.replace(/[+]/g, '%2B')
		}
	var resImg = escapeRegExp(img64);
	var param = "methodname=newReim&newAmount="+newAmount+"&newDesc="+newDesc+"&newType="+newType+"&newImg="+resImg;
	let xhr = new XMLHttpRequest();
	
	xhr.open('POST', "http://localhost:8080/project-1-JobDiangkinay/EmployeeServlet", true);
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			console.log(xhr.response);
			closeCreateForm();
			loadAllReim();
			var JSONObject = JSON.parse(xhr.responseText);
			if(JSONObject){
				alert("Submitted Successfully");
			}else{
				alert("Operation Failed");
			}
		}
	};
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
	xhr.send(encodeURI(param));
}

function closeCreateForm(){
	let div = document.getElementById('createForm');
	div.innerHTML="";
}

function closeUpdateInfo(){
	let div2 = document.getElementById('updateInfo');
	div2.innerHTML="";
}

function clickReimbursementHeader(){
	closePersonHeader();
	closeUpdateInfo();
	closeCreateForm();
	loadAllReim();
}

function clickPersonHeader(){
	firstLoad();
	closeCreateForm();
	closeReimbursementList();
	closeUpdateInfo();
}

function clickCreateForm(){
	showCreateForm();
	closePersonHeader()
	closeReimbursementList();
	closeUpdateInfo();
}

function closeReimbursementList(){
	let div = document.getElementById('employeeInfoTwo');
    div.innerHTML = "";
}

function closePersonHeader(){
	let div = document.getElementById('employeeInfo');
	div.innerHTML="";
}
