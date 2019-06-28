var userId;
var userRole;
$(document).ready(function() {
	userId = sessionStorage.getItem("id");
	userRole = sessionStorage.getItem("userRole");
	console.log("user id i role: "+userId + " " +userRole);
	if(userId == "null" || userRole !="ADMIN"){
		//window.location.href = "EbookPage.html";
	}
	loadUsers();
});
function tableHeader(){
	var table =  $('#myTableAdmin');
	table.empty();
	table.append('<tr>'+
					'<th>First name</th>'+
					'<th>Last name</th>'+
					'<th>Username</th>'+
					'<th>Role</th>'+
					'<th>Category</th>'+
					'<th><a class="btn btn-danger" data-toggle="modal" id="addBtn" onclick="add()" href="#AddNewUserByAdmin">Add</a></th>'+
				'</tr>');
}

function loadUsers(){
	var token = localStorage.getItem("token");
	tableHeader();
	$.ajax({
		url:'https://localhost:8080/ebook/users',
		headers:{Authorization:"Bearer " + token},
		type: 'GET',
		dataType:'json',
		crossDomain: true,
		success:function(response){
			if(response.length == 0){
				var table =  $('#myTable');
				table.empty();
				alert("Table users are empty.")
				return;
			}
			for(var i=0; i<response.length; i++) {
				var table =  $('#myTableAdmin');
				user = response[i];
				console.log(user)
				table.append('<tr>'+
								'<td>'+user.firstname+'</td>'+
								'<td>'+user.lastname+'</td>'+
								'<td>'+user.username+'</td>'+
								'<td>'+user.autority+'</td>'+
								'<td>'+user.category+'</td>'+
								'<td><a class="btn btn-danger" data-toggle="modal" id="editBtn" onclick="editUserByAdminID('+user.id+')" href="#editUserByAdmin">Edit</a></td>'+
								'<td><a class="btn btn-danger" data-toggle="modal" id="deleteBtn" onclick="deleteUserID('+user.id+')" href="#deleteUserModal">Delete</a></td>'+
							'</tr>');
			}
		},
		error: function (jqXHR, textStatus, errorThrown) {  
			if(jqXHR.status=="404"){
				alert("Error.");
			}
		}
	});

}



var uid;
function editUserByAdminID(id){
	var token = localStorage.getItem("token");
	uid=id;
	console.log('id usera za edit'+id);
	select = document.getElementById('categorySelectEDIT');
	$.ajax({
		url:'https://localhost:8080/ebook/category',
		headers:{Authorization:"Bearer " + token},
		type: 'GET',
		dataType:'json',
		crossDomain: true,
		success:function(response){
			
			for(var i=0; i<response.length; i++) {
				category = response[i];
				var opt = document.createElement('option');
			    opt.value = category.name;
			    opt.innerHTML = category.name;
			    select.appendChild(opt);
				
			}
			
			
		},
		error: function (jqXHR, textStatus, errorThrown) {  
			if(jqXHR.status=="404"){
				alert("Error.");
			}
		}
	});
	$("#fnameEditUserByAdmin").val('');
	$("#lnameEditUserByAdmin").val('');
	$("#passwordEditUserByAdmin").val('');
}
function editUserByAdmin(){
	var token = localStorage.getItem("token");
	var fname= $("#fnameEditUserByAdmin").val().trim();
	var lname= $("#lnameEditUserByAdmin").val().trim();
	var password= $("#passwordEditUserByAdmin").val().trim();
	var userType = $('#roleSelectEdit').val();
	var categoryName = $('#categorySelectEDIT').val();
	if(password=="" || fname=="" || lname==""){
		alert("All fields must be filled");
		return;
	}
	
	var data={
			'password':password,
			'firstname':fname,
			'lastname':fname,
			'autority':userType,
			'category':categoryName
	}
	
	console.log(data);
	console.log('id usera za edit'+uid);
	$.ajax({
		type: 'PUT',
        contentType: 'application/json',
        url: 'https://localhost:8080/ebook/users/update/'+uid,
        headers:{Authorization:"Bearer " + token},
        data: JSON.stringify(data),
        dataType: 'json',
		cache: false,
		processData: false,
        success: function (response) {
        	$('#editUserByAdmin').modal('toggle');
        	loadUsers();
        },
		error: function (jqXHR, textStatus, errorThrown) {  
			if(jqXHR.status=="403"){
				alert("Error.");
			}
			$('#editUserByAdmin').modal('toggle');
		}
    });

}

function add(){
	var token = localStorage.getItem("token");
	select = document.getElementById('categorySelectADD');
	$.ajax({
		url:'https://localhost:8080/ebook/category',
		headers:{Authorization:"Bearer " + token},
		type: 'GET',
		dataType:'json',
		crossDomain: true,
		success:function(response){
			
			for(var i=0; i<response.length; i++) {
				category = response[i];
				var opt = document.createElement('option');
			    opt.value = category.name;
			    opt.innerHTML = category.name;
			    select.appendChild(opt);
				
			}
		},
		error: function (jqXHR, textStatus, errorThrown) {  
			if(jqXHR.status=="404"){
				alert("Error.");
			}
		}
	});
	
	$("#usernameAddNewUserByAdmin").val('');
	$("#fnameAddUserByAdmin").val('');
	$("#lnameAddUserByAdmin").val('');
	$("#passwordAddNewUserByAdmin").val('');
	
}


function addNewuserbyAdmin(){
	var token = localStorage.getItem("token");
	var username= $("#usernameAddNewUserByAdmin").val().trim();
	var fname= $("#fnameAddUserByAdmin").val().trim();
	var lname= $("#lnameAddUserByAdmin").val().trim();
	var password= $("#passwordAddNewUserByAdmin").val().trim();
	var userType = $('#roleSelectAdd').val();
	var categoryName = $('#categorySelectADD').val();
	if(password=="" || fname=="" || lname=="" || username == ""){
		alert("All fields must be filled");
		return;
	}
	var category;
	
	var data={
			'username':username,
			'password':password,
			'firstname':fname,
			'lastname':lname,
			'autority':userType,
			'category':categoryName
	}
	console.log(data);
	$.ajax({
		type: 'POST',
        contentType: 'application/json',
        url: 'https://localhost:8080/ebook/users/add/',
        headers:{Authorization:"Bearer " + token},
        data: JSON.stringify(data),
        dataType: 'json',
		cache: false,
		processData: false,
        success: function (response) {
        	$('#AddNewUserByAdmin').modal('toggle');
        	loadUsers();
        },
		error: function (jqXHR, textStatus, errorThrown) {  
			if(jqXHR.status=="403"){
				alert("Error.");
			}
			$('#AddNewUserByAdmin').modal('toggle');
		}
    });
}
function deleteUserID(id){
	uid=id;
}
function deleteUser(){
	var token = localStorage.getItem("token");
	$.ajax({
        url: 'https://localhost:8080/ebook/users/delete/'+uid,
        headers:{Authorization:"Bearer " + token},
        contentType: "application/json",
		type: 'DELETE',
        success: function (response) {
        	console.log("user delete success: ");
        	
        	$('#deleteUserModal').modal('toggle');
        	loadUsers();
        },
		error: function (jqXHR, textStatus, errorThrown) {  
			alert(textStatus);
		}
    });
}