var userId;
var userRole;
$(document).ready(function() {
	userId = sessionStorage.getItem("id");
	userRole = sessionStorage.getItem("userRole");
	
});


function downloadBre(location){
	var token = localStorage.getItem("token");
	console.log('lokacija : '+location);
	var split1 = filename.split('files\\');
	console.log(split1);
	var split2= split1[1].split(".");
	var filename = split2[0];
	console.log('filename : '+filename);
	$.ajax({
        type: "POST",
        url: "https://localhost:8080/ebook/books/downloadPdf" + filename ,
        headers:{Authorization:"Bearer " + token},
        contentType: 'application/json',
        success: function (data) {
        	console.log("Bravo");
        },
        error: function (jqXHR, textStatus, errorThrown) {
        	
        	alert(jqXHR+ textStatus+ errorThrown);

        }
    });

}

function searchBre(){
	var token = localStorage.getItem("token");
	console.log("usao u preptegu")
	var tipQue= $("#tipquerya").val();
	var tipField= $("#tipField").val();
	var value=$("#value").val().trim();
	if(value == ""){
		alert("All fields must be filled.");
		return;
	}
	console.log("Pretraga :" + tipQue +  tipField + value)
	var data = JSON.stringify({"field":tipField, "value":value});
	var table =  $('#searchTable');
	table.empty();
	table.append('<tr>'+
					'<th>Title</th>'+
					'<th>Author</th>'+
					'<th>Keywords</th>'+
					'<th>Highlight</th>'+
					'<th>Location</th>'+
					'<th>Download</th>'+
					
				'</tr>'); 
	
	$.ajax({
	        type: "POST",
	        url: "https://localhost:8080/ebook/search/" + tipQue,
	        headers:{Authorization:"Bearer " + token},
	        data: data,
	        contentType: 'application/json',
	        success: function (data) {
	        	var table =  $('#searchTable');
	            for(index = 0; index < data.length; index++){
	                var result = data[index]
	                table.append('<tr>'+
							'<td>'+result.title+'</td>'+
							'<td>'+result.author+'</td>'+
							'<td>'+result.keywords+'</td>'+
							'<td>'+result.highlight+'</td>'+
							'<td>'+result.location+'</td>'+
							'<td><a class="btn btn-primary"  id="downloadBtn" onclick="downloadBre('+result.location +')" >Download</a></td>'+
							
						'</tr>');
	                
	            }
	            
	            console.log("SUCCESS : ", data);
	        },
	        error: function (jqXHR, textStatus, errorThrown) {
	        	
	        	alert(jqXHR+ textStatus+ errorThrown);

	        }
	    });
	
	
}

function booleanSearch(){
	var token = localStorage.getItem("token");
	var field1= $("#field1").val();
	var value1=$("#value1").val().trim();
	var field2= $("#field2").val();
	var value2=$("#value2").val().trim();
	var operation=$("#operation").val();
	console.log(value1 + value2);
	if(value1 == "" || value2 == "" ){
		alert("All fields must be filled.");
		return;
	}
	var data = JSON.stringify({"field1":field1, "value1":value1, "field2":field2, "value2":value2 , "operation":operation});
	var table =  $('#searchTable');
	table.empty();
	table.append('<tr>'+
					'<th>Title</th>'+
					'<th>Author</th>'+
					'<th>Keywords</th>'+
					'<th>Highlight</th>'+
					'<th>Location</th>'+
					'<th>Download</th>'+
					
				'</tr>');  
	$.ajax({
	        type: "POST",
	        url: "https://localhost:8080/ebook/search/boolean" ,
	        headers:{Authorization:"Bearer " + token},
	        data: data,
	        contentType: 'application/json',
	        success: function (data) {
	        	$('#result').empty();
	            for(index = 0; index < data.length; index++){
	            	var table =  $('#searchTable');
	            	var result = data[index]
	                table.append('<tr>'+
							'<td>'+result.title+'</td>'+
							'<td>'+result.author+'</td>'+
							'<td>'+result.keywords+'</td>'+
							'<td>'+result.highlight+'</td>'+
							'<td>'+result.location+'</td>'+
							'<td><a class="btn btn-primary" onclick = "downloadBre('+ result.location +')" >Download </a></td>'+
							
						'</tr>');
	                
	            }
	            console.log("SUCCESS : ", data);
	        },
	        error: function (e) {
	        	
	        	alert(e);

	        }
	    });
}