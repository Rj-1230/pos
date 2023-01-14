function getInventoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventory";
}

//BUTTON ACTIONS
function addInventory(event){
	var $form = $("#inventory-form");
	var json = toJson($form);
	var url = getInventoryUrl()+"AddSub";

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
//	   Header is added mandatorily
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		getInventoryList();
	   		document.getElementById("inventory-form").reset();
            $('#create-inventory-modal').modal('hide');
            document.getElementById('toast-container').classList.remove('bg-warning','bg-danger','bg-success');
            document.getElementById('toast-container').classList.add('bg-success');
            document.getElementById('my-message').innerHTML="The items were added successfully to the inventory";
            $(".toast").toast('show');
	   },
	   error: function(response){
                  handleAjaxError(response);
       	   }
	});

	return false;
}

//This function is called on clicking of update button in the edit modal form
function addToInventory(event){
	$('#add-inventory-modal').modal('toggle');
//	var id = $("#add-edit-form input[name=productId]").val();
	var url = getInventoryUrl() + "AddSub";
    var $form = $("#add-edit-form");
	var json = toJson($form);
	console.log(json);
//    The form is converted to JSON and a PUT request (update) is called and thus details are updated
	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
       	'Content-Type': 'application/json'
       },
	   success: function(response) {
	   		getInventoryList();
	   		$('#add-inventory-modal').modal('hide');
            document.getElementById('toast-container').classList.remove('bg-warning','bg-danger','bg-success');
            document.getElementById('toast-container').classList.add('bg-success');
            document.getElementById('my-message').innerHTML="The inventory was updated successfully";
            $(".toast").toast('show');
	   },
	   error: function(response){
                  handleAjaxError(response);
       	   }
	});

	return false;
}



function subFromInventory(event){

	$('#sub-inventory-modal').modal('toggle');
	var url = getInventoryUrl() + "AddSub";
	var $form = $("#sub-edit-form");

	var json = toJson($form);
	const objJson = JSON.parse(json);
    objJson.quantity=-objJson.quantity;
    json = JSON.stringify (objJson);
//    The form is converted to JSON and a PUT request (update) is called and thus details are updated
	$.ajax({
	   url: url,
	   type: 'POST',
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
	   		displayInventoryList(data);
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
		console.log(typeof(e))
		var buttonHtml ='<button class="btn btn-dark" onclick="displayEditInventory(' +e.productId + ')" ><i class="bi bi-plus"></i> </button>'
		buttonHtml +='&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-dark" onclick="displayEditInventorys(' + e.productId + ')" ><i class="bi bi-dash"></i></button>'
		buttonHtml += '&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-danger" onclick="deleteInventory(' + e.productId + ')" disabled><i class="bi bi-trash"></i></button>'
		var row = '<tr>'
		+ '<td>' + e.productId + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function displayEditInventory(id){
	var url = getInventoryUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		console.log("Inventory data is fetched");
	   		console.log(typeof(data))
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
	$("#sub-edit-form input[name=barcode]").val(data.barcode);
	$("#sub-edit-form input[name=quantity]").val(0);
	$('#sub-inventory-modal').modal('toggle');
}

function displayInventory(data){
	$("#add-edit-form input[name=barcode]").val(data.barcode);
	$("#add-edit-form input[name=quantity]").val(0);
	$('#add-inventory-modal').modal('toggle');
}


function readFileDataCallback(results){
	fileData = results.data;
	var url = getInventoryUrl();
	uploadRows(url);
}
function uploadData(){
//sample file dynamic add krege
//document.getElementById('sample-file').innerHTML='<a th:href="[[@{/sample/inventory.tsv}]]" target="_blank">Download Sample</a>';
displayUploadData();
}

function getList(){
getInventoryList();
}




//INITIALIZATION CODE
function init(){
	$('#add-inventory').click(addInventory);
	$('#addInventory').click(addToInventory);
	$('#subInventory').click(subFromInventory);
	$('#refresh-data').click(getInventoryList);
	$('#upload-data').click(displayUploadData);
        	$('#process-data').click(processData);
        	$('#download-errors').click(downloadErrors);
            $('#myFile').on('change', updateFileName)
}

$(document).ready(init);
$(document).ready(getInventoryList);
