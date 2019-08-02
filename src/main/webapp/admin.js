window.onload = loadManager();
	
function loadManager(){
	let xhr = new XMLHttpRequest();
	xhr.open('POST', "http://localhost:8080/project-1-JobDiangkinay/AdminServlet", true);
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
			employeeInfo.innerHTML = empDetails(JSONObject);
		}
	};
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send(encodeURI("methodname=loademp"));
}

function empDetails(JSONObject){
	 return `<div class="secondary-block">
     <h3>Name: ${JSONObject.firstName} ${JSONObject.lastName}</h3>
     <h3>Phone Number: ${JSONObject.phoneNumber}</h3>
     <h3>Email Address: ${JSONObject.eMail}</h3>
     <button onClick="showUpdateInfo()">Update Info</button>
     </div>
`
}

function loadAllReim(){
	let xhr = new XMLHttpRequest();
	xhr.open('POST', "http://localhost:8080/project-1-JobDiangkinay/AdminServlet", true);
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			var JSONObject = JSON.parse(xhr.responseText);
			var html = '<table id="reimbursementTable" class="table table-striped table-dark table-bordered">'; // string
			html += '<thead class="thead-dark"><tr><td>Reimbursement ID</td>';
			html += '<td>Username</td>';
			html += '<td>Type</td>';
			html += '<td>Description</td>';
			html += '<td>Status</td>';
			html += '<td>Amount</td>';
			html += '<td>Manager</td>';
			html += '<td>Date Created</td></tr></thead><tbody>';
		    for (var i = 0, len = JSONObject.length; i < len; i++) {
		        html += '<tr onclick="tdClick('+(i+1)+')">';
		        html += '<td id="reimId'+(i+1)+'">' + JSONObject[i].ReimbursementId + '</td>';
		        html += '<td id="reimUser'+(i+1)+'">' + JSONObject[i].userId + '</td>';
		        html += '<td id="reimType'+(i+1)+'">' + JSONObject[i].ReimbursementType + '</td>';
		        html += '<td id="reimDesc'+(i+1)+'">' + JSONObject[i].ReimbursementDesc + '</td>';
		        html += '<td id="reimStat'+(i+1)+'">' + JSONObject[i].Status + '</td>';
		        html += '<td id="reimAmount'+(i+1)+'">$' + JSONObject[i].Amount + '</td>';
		        if(typeof JSONObject[i].adminName != 'undefined'){
		        	 html += '<td id="reimAdminName'+(i+1)+'">' + JSONObject[i].adminName + '</td>';
		        }else{
		        	 html += '<td id="reimAdminName'+(i+1)+'">N/A</td>';
		        }
		        html += '<td id="reimDate'+(i+1)+'">' + JSONObject[i].DateCreated + '</td>';
		        html += '</tr>';
		    }
		    html += '</tbody></table>';
		    var div = document.getElementById('employeeInfoTwo');
		    div.innerHTML = html;
		    dataReimTableRun();
			
		}
	};
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send(encodeURI("methodname=loadAllReim"));
}

function loadAllReimPic(i){
	let xhr = new XMLHttpRequest();
	xhr.open('POST', "http://localhost:8080/project-1-JobDiangkinay/AdminServlet", true);
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			var JSONObject = JSON.parse(xhr.responseText);
			var num = i - 1;
			var srcData = JSONObject[num].imageBase64;
			if (typeof srcData != 'undefined'){
			var newImage = document.createElement('img');
				// var newImage = new Image();
			newImage.src = srcData;
			document.getElementById("imgTest").innerHTML = newImage.outerHTML;
			}
		}
	};
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send(encodeURI("methodname=loadAllReim"));
}


function loadAllEmployees(){
	let xhr = new XMLHttpRequest();
	xhr.open('POST', "http://localhost:8080/project-1-JobDiangkinay/AdminServlet", true);
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
		var employeeList = JSON.parse(xhr.responseText);
		var html = '<table id="employeeTable" class="table table-striped table-dark">';
		html += '<thead class="thead-dark"><tr>';
		html += '<td>Username</td>';
		html += '<td>Employee Name</td>';
		html += '<td>Email Address</td>';
		html += '<td>Phone Number</td></tr></thead><tbody>';
		for (var i = 0, len = employeeList.length; i < len; i++) {
			
	        html += '<tr>';
	        html += '<td id="empUser'+(i+1)+'">' + employeeList[i].userName + '</td>';
	        html += '<td id="empName'+(i+1)+'">' + employeeList[i].firstName +" "+ employeeList[i].lastName + '</td>';
	        html += '<td id="empEmail'+(i+1)+'">' + employeeList[i].eMail + '</td>';
	        html += '<td id="empPhone'+(i+1)+'">' + employeeList[i].phoneNumber + '</td>';
	        html += '</tr>';
		}
	        var div = document.getElementById('employeeList');
		    div.innerHTML = html;
		    html += '</tbody></table>';
		    dataEmpTableRun();
		}
	};
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send(encodeURI("methodname=loadAllEmp"));
}

function tdClick(i){
	let reimId = document.getElementById('reimId'+i).innerText;
	let reimUser = document.getElementById('reimUser'+i).innerText;
	let reimType = document.getElementById('reimType'+i).innerText;
	let reimDesc = document.getElementById('reimDesc'+i).innerText;
	let reimAmount = document.getElementById('reimAmount'+i).innerText;
	let reimDate = document.getElementById('reimDate'+i).innerText;
	let reimStatus = document.getElementById('reimStat'+i).innerText;
	let reimAdminName = document.getElementById('reimAdminName'+i).innerText;
	let div = document.getElementById('selectedReim');
	if (reimStatus == "Approved" || reimStatus == "Denied"){
    div.innerHTML = `<div  class="secondary-block">
    <div>
				<button onClick="closeSelectAndImage()" type="button" class="close" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div><div>
    	<h4>Reimbursement Details</h4>
        <h5>ID: ${reimId} Type:${reimType}</h5>
        <h5>Submitted By: ${reimUser}</h5>
        <h5>Description: ${reimDesc}</h5>
        <h5>Amount: ${reimAmount}</h5>
        <h5>Date Submitted: ${reimDate}</h5>
        <h5>Status: ${reimStatus}</h5></div>
        <h5>Managed by: ${reimAdminName}</h5></div>
        </div>`
	}else {
		div.innerHTML = `<div class="secondary-block">
		<div>
				<button onClick="closeSelectAndImage()" type="button" class="close" aria-label="Close">
					<span aria-hidden="true">&times;</span>
				</button>
			</div
		<div>
			<h4>Reimbursement Details</h4>
	        <h4>ID: ${reimId} Type:${reimType}</h4>
	        <h5>Submitted By: ${reimUser}</h5>
	        <h4>Description: ${reimDesc}</h4>
	        <h4>Amount: ${reimAmount}</h4>
	        <h4>Date Submitted: ${reimDate}</h4>
	        <h4>Status: ${reimStatus}</h4>
	        <button onClick="handleReim(${reimId},'ACCEPT')">Accept</button>
	        <button onClick="handleReim(${reimId},'DENY')">Deny</button>
	        <br/>
	        </div>
	        </div>`
	}
	closePictureWindow()
	loadAllReimPic(i)
}

function handleReim(reimId,reimCode){
	let xhr = new XMLHttpRequest();
	xhr.open('POST', "http://localhost:8080/project-1-JobDiangkinay/AdminServlet", true);
	var param = "methodname=handleReim&reimCode="+reimCode+"&reimId="+reimId;
	xhr.onreadystatechange = function(){
		if(xhr.readyState == 4 && xhr.status == 200){
			console.log(xhr.response)
			var JSONObject = JSON.parse(xhr.responseText);
			if(JSONObject == "true"){
				alert("Reimbursement Accepted!");
			}if(JSONObject == "false"){
				alert("Reimbursement Denied!");
			}
			loadAllReim();
			closeSelectWindow();
			closePictureWindow();
		}
	};
	xhr.setRequestHeader("Content-type", "application/x-www-form-urlencoded");
    xhr.send(encodeURI(param));
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
		  document.getElementById('div1').innerHTML='Value: <input type="email" id="editField" name="editValue" size="20" required />';
		  document.getElementById('button1').innerHTML='<button onClick="updateEmployee()">Confirm</button>';
	  }
	  else if(name=='phoneNumber'){
		  document.getElementById('div1').innerHTML='Value: <input type="number" id="editField" name="editValue" size="20" required />';
		  document.getElementById('button1').innerHTML='<button onClick="updateEmployee()">Confirm</button>';
	  }
	  else {
		  document.getElementById('div1').innerHTML='';
		  document.getElementById('button1').innerHTML='';
	  }
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
	xhr.open('POST', "http://localhost:8080/project-1-JobDiangkinay/AdminServlet", true);
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

function closeSelectAndImage(){
	closeSelectWindow();
	closePictureWindow();
}

function closeUpdateInfo(){
	let div2 = document.getElementById('updateInfo');
	div2.innerHTML="";
}

function closeSelectWindow(){
	let div = document.getElementById('selectedReim');
	div.innerHTML = "";
}

function closePictureWindow(){
	let div = document.getElementById('imgTest');
	div.innerHTML = "";
}

function closePersonHeader(){
	let div = document.getElementById('employeeInfo');
	div.innerHTML="";
}

function closeReimList(){
	let div = document.getElementById('employeeInfoTwo');
	div.innerHTML="";
}

function closeEmpList(){
	let div = document.getElementById('employeeList');
	div.innerHTML="";
}

function clickEmployeeHeader(){
	closeReimList();
	closeSelectWindow();
	closePictureWindow();
	closePersonHeader();
	loadAllEmployees();
	closeUpdateInfo();
}

function clickReimbursementHeader(){
	closeSelectWindow();
	closePictureWindow();
	closeEmpList();
	closePersonHeader();
	loadAllReim();
	closeUpdateInfo();
}

function clickPersonalInfo(){
	closeSelectWindow();
	closePictureWindow();
	closeEmpList();
	closeReimList();
	loadManager();
}

function dataReimTableRun(){
    $('#reimbursementTable').DataTable();
}

function dataEmpTableRun(){
    $('#employeeTable').DataTable();
}
