
function getBrandUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/brand";
}

//BUTTON ACTIONS
function addBrand(event){
	//Set the values to update
	var $form = $("#brand-form");
	var json = toJson($form);
//	Form converted to JSON format

	var url = getBrandUrl();
//	url = localhost:9000/pos/api/brand

//    AJAX call is done
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
//	   Header is added mandatorily
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		console.log("This is my response" + response);
	   		document.getElementById('logger').innerHTML=response;
	   		getBrandList();     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});

	return false;
}

//This function is called on clicking of update button in the edit modal form
function updateBrand(event){
	$('#edit-brand-modal').modal('toggle');
	//edit-brand-model is the id given to the entire block which gets toggled up when we click on Update button

	var id = $("#brand-edit-form input[name=id]").val();
	//edit-brand-form is the id given to the form inside the above mentioned block which opens for editing
//	That brand's id (not CSS ID )value is fetched and given to the variable id

	var url = getBrandUrl() + "/" + id;
//	url = = localhost:9000/brand/api/brand/{id}

	//Set the values to update
	var $form = $("#brand-edit-form");
	var json = toJson($form);
//    The form is converted to JSON and a PUT request (update) is called and thus details are updated
	$.ajax({
	   url: url,
	   type: 'PUT',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		console.log("Brand details are updated");
	   		getBrandList();     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});

	return false;
}


function getBrandList(){
	var url = getBrandUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		console.log("Brand-category data fetched");
	   		console.log(data);
	   		displayBrandList(data);     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}

function deleteBrand(id){
	var url = getBrandUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		console.log("brand deleted");
	   		getBrandList();     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}

////UI DISPLAY METHODS

//All the brands are listed with the delete and edit button having event handlers upon click
function displayBrandList(data){
	console.log('Printing brand-category data');
	var $tbody = $('#brand-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml ='<button class="btn btn-warning" onclick="displayEditbrand(' + e.id + ')">Edit</button>'
		buttonHtml += '&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-danger" onclick="deleteBrand(' + e.id + ')" disabled>Delete</button>'
		var row = '<tr>'
		+ '<td>' + e.id + '</td>'
		+ '<td>' + e.brand + '</td>'
		+ '<td>'  + e.category + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}


//As the edit button is clicked in the table , its id is passed to this method and the method is called
function displayEditbrand(id){
	var url = getBrandUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		console.log("Brand data is fetched");
	   		console.log(data);
//	   		Till here it was a normal GET reuqest
//            Now the function displayBrand is called
//        This will edit the brand's data and feed into the table
	   		displayBrand(data);     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}

function displayBrand(data){
	$("#brand-edit-form input[name=brand]").val(data.brand);
	$("#brand-edit-form input[name=category]").val(data.category);
	$("#brand-edit-form input[name=id]").val(data.id);
	$('#edit-brand-modal').modal('toggle');
}


//    HELPER METHOD
    function toJson($form){
        var serialized = $form.serializeArray();
        console.log(serialized);
        var s = '';
        var data = {};
        for(s in serialized){
            data[serialized[s]['name']] = serialized[s]['value']
        }
        var json = JSON.stringify(data);
        console.log(json);
        return json;
    }

// FILE UPLOAD METHODS
var fileData = [];
var errorData = [];
var processCount = 0;


function processData(){
	var file = $('#brandFile')[0].files[0];
	readFileData(file, readFileDataCallback);
}

function readFileDataCallback(results){
	fileData = results.data;
	uploadRows();
}

function uploadRows(){
	//Update progress
	updateUploadDialog();
	//If everything processed then return
	if(processCount==fileData.length){
		return;
	}

	//Process next row
	var row = fileData[processCount];
	processCount++;

	var json = JSON.stringify(row);
	var url = getBrandUrl();

	//Make ajax call
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		uploadRows();
	   		getBrandList();

	   },
	   error: function(response){
	   		row.error=response.responseText
	   		errorData.push(row);
	   		uploadRows();
	   }
	});

	console.log("Finallllyy uploaded");

}

function downloadErrors(){
	writeFileData(errorData);
}


function resetUploadDialog(){
	//Reset file name
	var $file = $('#brandFile');
	$file.val('');
	$('#brandFileName').html("Choose File");
	//Reset various counts
	processCount = 0;
	fileData = [];
	errorData = [];
	//Update counts	
	updateUploadDialog();
}

function updateUploadDialog(){
	$('#rowCount').html("" + fileData.length);
	$('#processCount').html("" + processCount);
	$('#errorCount').html("" + errorData.length);
}

function updateFileName(){
	var $file = $('#brandFile');
	var fileName = $file.val();
	$('#brandFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog(); 	
	$('#upload-brand-modal').modal('toggle');
}


//INITIALIZATION CODE
function init(){
	$('#add-brand').click(addBrand);

//	On clicking the button update, updatebrand is called
	$('#update-brand').click(updateBrand);
	$('#refresh-data').click(getBrandList);
	$('#upload-data').click(displayUploadData);
    	$('#process-data').click(processData);
    	$('#download-errors').click(downloadErrors);
        $('#brandFile').on('change', updateFileName)
}

$(document).ready(init);
$(document).ready(getBrandList);
