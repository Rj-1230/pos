
function getInventoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventory";
}

//BUTTON ACTIONS
function addInventory(event){
	var $form = $("#inventory-form");
	var json = toJson($form);
//	Form converted to JSON format
    console.log(json);

	var url = getInventoryUrl();
	console.log(url);
//	url = localhost:9000/pos/api/inventory

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
	   		console.log("this is response" + response);
            document.getElementById('logg').innerHTML=response;
	   		getInventoryList();     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});

	return false;
}

//This function is called on clicking of update button in the edit modal form
function addToInventory(event){
	$('#edit-inventory-modal').modal('toggle');
	//edit-inventory-model is the id given to the entire block which gets toggled up when we click on Update button

	var id = $("#inventory-edit-form input[name=productId]").val();
	//edit-inventory-form is the id given to the form inside the above mentioned block which opens for editing
//	That inventory's id (not CSS ID )value is fetched and given to the variable id

	var url = getInventoryUrl() + "Add/" + id;
//	url = = localhost:9000/inventory/api/inventory/{id}

	//Set the values to update
	var $form = $("#inventory-edit-form");
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
	   		console.log("Inventory details are updated");
	   		getInventoryList();     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});

	return false;
}



function subFromInventory(event){
	$('#sub-inventory-modal').modal('toggle');
	//edit-inventory-model is the id given to the entire block which gets toggled up when we click on Update button

	var id = $("#sub-edit-form input[name=productId]").val();
	//edit-inventory-form is the id given to the form inside the above mentioned block which opens for editing
//	That inventory's id (not CSS ID )value is fetched and given to the variable id

	var url = getInventoryUrl() + "Sub/" + id;
//	url = = localhost:9000/inventory/api/inventory/{id}

	//Set the values to update
	var $form = $("#sub-edit-form");
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
	   		console.log("Inventory details are updated");
	   		getInventoryList();     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});

	return false;
}





function getInventoryList(){
	var url = getInventoryUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		console.log("Inventory-category data fetched");
	   		console.log(data);
	   		displayInventoryList(data);     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}

function deleteInventory(id){
	var url = getInventoryUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		console.log("inventory deleted");
	   		getInventoryList();     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}

////UI DISPLAY METHODS

//All the inventorys are listed with the delete and edit button having event handlers upon click
function displayInventoryList(data){
	console.log('Printing inventory-category data');
	var $tbody = $('#inventory-table').find('tbody');
	$tbody.empty();
//	ello
	for(var i in data){
		var e = data[i];
		var buttonHtml ='<button class="btn btn-warning" onclick="displayEditInventory(' + e.productId + ')" >Add Items </button>'
		buttonHtml +='&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-warning" onclick="displayEditInventorys(' + e.productId + ')" >Remove Items</button>'
		buttonHtml += '&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-danger" onclick="deleteInventory(' + e.productId + ')" disabled>Delete</button>'
		var row = '<tr>'
		+ '<td>' + e.productId + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}


//As the edit button is clicked in the table , its id is passed to this method and the method is called
function displayEditInventory(id){
	var url = getInventoryUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		console.log("Inventory data is fetched");
	   		console.log(data);
//	   		Till here it was a normal GET reuqest
//            Now the function displayInventory is called
//        This will edit the inventory's data and feed into the table
	   		displayInventory(data);     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}

function displayEditInventorys(id){
	var url = getInventoryUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		console.log("Inventory data is fetched");
	   		console.log(data);
//	   		Till here it was a normal GET reuqest
//            Now the function displayInventory is called
//        This will edit the inventory's data and feed into the table
	   		displaySubInventory(data);     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}
function displaySubInventory(data){
	$("#sub-edit-form input[name=productId]").val(data.productId);
	$("#sub-edit-form input[name=quantity]").val(data.quantity);
//	$("#inventory-edit-form input[name=id]").val(data.id);
	$('#sub-inventory-modal').modal('toggle');
}

function displayInventory(data){
	$("#inventory-edit-form input[name=productId]").val(data.productId);
	$("#inventory-edit-form input[name=quantity]").val(data.quantity);
//	$("#inventory-edit-form input[name=id]").val(data.id);
	$('#edit-inventory-modal').modal('toggle');
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
	var file = $('#inventoryFile')[0].files[0];
	readFileData(file, readFileDataCallback);
	document.getElementById('logg').innerHTML = "The list has been updated";
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
	var url = getInventoryUrl();

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
	   		getInventoryList();

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
	var $file = $('#inventoryFile');
	$file.val('');
	$('#inventoryFileName').html("Choose File");
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
	var $file = $('#inventoryFile');
	var fileName = $file.val();
	$('#inventoryFileName').html(fileName);
}

function displayUploadData(){
 	resetUploadDialog();
	$('#upload-inventory-modal').modal('toggle');
}





//INITIALIZATION CODE
function init(){
	$('#add-inventory').click(addInventory);

//	On clicking the button update, updateinventory is called
	$('#addInventory').click(addToInventory);
	$('#subInventory').click(subFromInventory);
	$('#refresh-data').click(getInventoryList);

	$('#upload-data').click(displayUploadData);
        	$('#process-data').click(processData);
        	$('#download-errors').click(downloadErrors);
            $('#inventoryFile').on('change', updateFileName)
}

$(document).ready(init);
$(document).ready(getInventoryList);
