var userId;
var userRole;
$(document).ready(function() {
	userId = sessionStorage.getItem("id");
	userRole = sessionStorage.getItem("userRole");
	if(userId == "null" || userRole !="ADMIN"){
		loadCategorySub();
	}else{
		loadCategory();
	}
});
function tableHeader(){
	var table =  $('#myTableCategory');
	table.empty();
	table.append('<tr>'+
					'<th>Name</th>'+
					'<th>Edit</th>'+
					'<th>Delete</th>'+
					'<th><a class="btn btn-danger" data-toggle="modal" id="addBtn" href="#addCategory">Add</a></th>'+
				'</tr>');
}

function loadCategory(){
	var token = localStorage.getItem("token");
	tableHeader();
	$.ajax({
		url:'https://localhost:8080/ebook/category',
		headers:{Authorization:"Bearer " + token},
		type: 'GET',
		dataType:'json',
		crossDomain: true,
		success:function(response){
			if(response.length == 0){
				var table =  $('#myTableCategory');
				table.empty();
				alert("Table category are empty.")
				return;
			}
			for(var i=0; i<response.length; i++) {
				var table =  $('#myTableCategory');
				category = response[i];
				console.log(category.name);
				if(category.name != "NONE"){
					table.append('<tr>'+
								'<td> <a href="EbookPage.html?id='+ category.name +'">'+category.name +'</a> </td>'+
								
								'<td><a class="btn btn-danger" data-toggle="modal" id="editBtn" onclick="editCategoryID('+category.id+')" href="#editCategory">Edit</a></td>'+
								'<td><a class="btn btn-danger" data-toggle="modal" id="deleteBtn" onclick="deleteCategoryID('+category.id+')" href="#deleteCategory">Delete</a></td>'+
							'</tr>');
				}else {
					table.append('<tr>'+
							'<td> '+category.name +'</td>'+
							
							'<td><a class="btn btn-danger" data-toggle="modal" id="editBtn" onclick="editCategoryID('+category.id+')" href="#editCategory">Edit</a></td>'+
							'<td><a class="btn btn-danger" data-toggle="modal" id="deleteBtn" onclick="deleteCategoryID('+category.id+')" href="#deleteCategory">Delete</a></td>'+
						'</tr>');
				}
			}
		},
		error: function (jqXHR, textStatus, errorThrown) {  
			if(jqXHR.status=="404"){
				alert("Error.");
			}
		}
	});
}
function tableHeaderSub(){
	var table =  $('#myTableCategory');
	table.empty();
	table.append('<tr>'+
					'<th>Name</th>'+
				'</tr>');
}
function loadCategorySub(){
	var token = localStorage.getItem("token");
	tableHeaderSub();
	$.ajax({
		url:'https://localhost:8080/ebook/category',
		headers:{Authorization:"Bearer " + token},
		type: 'GET',
		dataType:'json',
		crossDomain: true,
		success:function(response){
			if(response.length == 0){
				var table =  $('#myTableCategory');
				table.empty();
				alert("Table category are empty.")
				return;
			}
			
			for(var i=0; i<response.length; i++) {
				var table =  $('#myTableCategory');
				category = response[i];
				console.log(category.name);
				if(category.name != "NONE"){
					table.append('<tr>'+
								'<td> <a href="EbookPage.html?id='+ category.name +'">'+category.name +'</a> </td>'+
									'</tr>');
				}else {
					table.append('<tr>'+
							'<td> '+category.name +' </td>'+
								'</tr>');
				}
			}
			
		},
		error: function (jqXHR, textStatus, errorThrown) {  
			if(jqXHR.status=="404"){
				alert("Error.");
			}
		}
	});
}

function addCategory() {
	var token = localStorage.getItem("token");
	var name= $("#nameCategoryAdd").val().trim();
	
	if(name == ""){
		alert("All fields must be filled");
		return;
	}
	var data={
			'name':name,
			
	}

	console.log(data);
	$.ajax({
		type: 'POST',
        contentType: 'application/json',
        url: 'https://localhost:8080/ebook/category/add/',
        headers:{Authorization:"Bearer " + token},
        data: JSON.stringify(data),
        dataType: 'json',
		cache: false,
		processData: false,
        success: function (response) {
        	$('#addCategory').modal('toggle');
        	loadCategory();
        },
		error: function (jqXHR, textStatus, errorThrown) {  
			if(jqXHR.status=="403"){
				alert("Error.");
			}
			$('#addCategory').modal('toggle');
		}
    });
}
var uid;
function editCategoryID(id){
	uid=id;
	var name= $("#nameCategory").val().trim();
	name = "";
}
function editCategory() {
	var token = localStorage.getItem("token");
	var name= $("#nameCategory").val().trim();
	
	if(name == ""){
		alert("All fields must be filled");
		return;
	}
	var data={
			'name':name,
			
	}

	console.log(data);
	console.log(uid);
	$.ajax({
		type: 'PUT',
        contentType: 'application/json',
        url: 'https://localhost:8080/ebook/category/edit/'+uid,
        headers:{Authorization:"Bearer " + token},
        data: JSON.stringify(data),
        dataType: 'json',
		cache: false,
		processData: false,
        success: function (response) {
        	console.log(response);
        	$('#editCategory').modal('toggle');
        	loadCategory();
        },
		error: function (jqXHR, textStatus, errorThrown) {  
			if(jqXHR.status=="403"){
				alert("Error.");
			}
			$('#editCategory').modal('toggle');
		}
    });
}
function deleteCategoryID(id){
	uid=id;
}
function deleteCategory() {
	var token = localStorage.getItem("token");
	$.ajax({
        url: 'https://localhost:8080/ebook/category/delete/'+uid,
        headers:{Authorization:"Bearer " + token},
        contentType: "application/json",
		type: 'DELETE',
        success: function () {
        	console.log("category delete success: ");
        	
        	$('#deleteCategory').modal('toggle');
        	loadCategory();
        },
		error: function (jqXHR, textStatus, errorThrown) {  
			alert(textStatus);
		}
    });
}