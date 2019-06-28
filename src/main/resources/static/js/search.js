var userId = "";
var userRole = "";
var token = "";
$(document).ready(function(e) {
	loginStatus();
});
function loginStatus(){
	userId = sessionStorage.getItem("id");
	userRole = sessionStorage.getItem("userRole");
	token = sessionStorage.getItem("token");
	console.log("current user id: "+ userId);
	console.log("current userrole: "+ userRole);
	
	if(userId != 'null' && userId != null){
		console.log("Nije null");
		
		$('#loggeduser').text(userRole);
		var y = document.getElementById("navBarNotLogIn");
		y.style.display = "none";
		var y = document.getElementById("navBarLogIn");
		y.style.display = "block";
		$('#userRoleE').val(userRole);
		if(userRole != "ADMIN"){
			$("#adminPageId").hide();
			
			if(userRole !="USER"){
				
			}
		}
	}else{
		console.log("jeste null");
		var y = document.getElementById("navBarNotLogIn");
		y.style.display = "block";
		var y = document.getElementById("navBarLogIn");
		y.style.display = "none";
		
	}
	
}


function login(){
	var username =  $('#loginUsername').val().trim();
	var password = $('#loginPassword').val().trim();
	if(username=="" || password==""){
		alert("All fields must be filled.")
		return;
	}
	
	var data = {
			'username':username,
			'password':password
	}
	console.log(data);
	console.log("usao u login()");

	$.ajax({
		type: 'POST',
		contentType: 'application/json',
        url: 'https://localhost:8080/api/auth/login',
        data: JSON.stringify(data),
        dataType: 'json',
        crossDomain: true,
		cache: false,
		processData: false,
		success:function(response){
			if (typeof(Storage) !== "undefined") {
        	    console.log(response);
        	    token = response.access_token;
        	    localStorage.setItem("token",token);
        	    console.log(token);
				sessionStorage.setItem("id", response.userId);
        	    username = response.username;
        	    sessionStorage.setItem("userRole", response.userAutority);
        	    console.log(response.userAutority);
        	    window.location.href = "EbookPage.html";
        	} else {
        	    alert("Sorry, your browser does not support Web Storage...");
        	}
        	//location.reload(); 
        },
		error: function (jqXHR, textStatus, errorThrown) {  
			if(jqXHR.status="404"){
				alert("That user doesnt exist.");
			}
			location.reload(); 
		}
	});

}

function logout(){
	console.log("logout");
    sessionStorage.setItem("id", null);
    sessionStorage.setItem("userRole", null);
    sessionStorage.setItem("token", null);
    window.location.href = "EbookPage.html";
}

function eu(){
	$('#fnameEditUser').val('');
	$('#lnameEditUser').val('');
}

function editUser(){
	var token = localStorage.getItem("token");
	var fname=$('#fnameEditUser').val().trim();
	var lname=$('#lnameEditUser').val().trim();
	var data ={
			'firstname':fname,
			'lastname':lname
	}
	$.ajax({
		type: 'PUT',
        contentType: 'application/json',
        url: 'https://localhost:8080/ebook/users/edit/'+userId,
        headers:{Authorization:"Bearer " + token},
        data: JSON.stringify(data),
        dataType: 'json',
		cache: false,
		processData: false,
        success: function (response) {
        	$('#editUser').modal('toggle');
        	location.reload();
        },
		error: function (jqXHR, textStatus, errorThrown) {  
			if(jqXHR.status=="403"){
				alert("Error.");
			}
			$('#editUser').modal('toggle');
		}
    });
}

function cp(){
	$('#passwordCP').val('');
	$('#cpasswordCP').val('');
}

function changePassword(){
	var token = localStorage.getItem("token");
	var opass=$('#passworOP').val().trim();
	var pass=$('#passwordP').val().trim();
	var cpass=$('#cpasswordCP').val().trim();
	var token = localStorage.getItem("token");
	if(pass != cpass){
		alert("sifre moraju biti iste!")
	}
	var data ={
			'oldPassword':opass,
			'newPassword':pass
	}
	$.ajax({
		type: 'POST',
        contentType: 'application/json',
        url: 'https://localhost:8080/api/auth/change-password',
        headers:{Authorization:"Bearer " + token},
        data: JSON.stringify(data),
        dataType: 'json',
		cache: false,
		processData: false,
        success: function (response) {
        	$('#changePassword').modal('toggle');
        	location.reload();
        },
		error: function (jqXHR, textStatus, errorThrown) {  
			if(jqXHR.status=="403"){
				alert("Error.");
			}
			$('#changePassword').modal('toggle');
		}
    })
	
	/*$.ajax({
		type: 'PUT',
        contentType: 'application/json',
        url: 'https://localhost:8080/ebook/users/cp/'+userId,
        data: JSON.stringify(data),
        dataType: 'json',
		cache: false,
		processData: false,
        success: function (response) {
        	$('#changePassword').modal('toggle');
        	location.reload();
        },
		error: function (jqXHR, textStatus, errorThrown) {  
			if(jqXHR.status=="403"){
				alert("Error.");
			}
			$('#changePassword').modal('toggle');
		}
    });*/
	
}