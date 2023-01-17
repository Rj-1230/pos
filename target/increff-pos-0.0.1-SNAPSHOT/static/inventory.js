var role;
function getInventoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventory";
}

function getSupervisorInventoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/supervisor/inventory";
}

//BUTTON ACTIONS
function addInventory(event){
	var $form = $("#inventory-form");
	var json = toJson($form);
	var url = getSupervisorInventoryUrl()+"AddSub";

	$.ajax({
	   url: url,
	   type: 'POST',
	   data: json,
	   headers: {
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

function addToInventory(event){
	$('#add-inventory-modal').modal('toggle');
//	var id = $("#add-edit-form input[name=productId]").val();
	var url = getSupervisorInventoryUrl() + "AddSub";
    var $form = $("#add-edit-form");
	var json = toJson($form);

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
            document.getElementById('my-message').innerHTML="The items were added successfully to the inventory";
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
	var url = getSupervisorInventoryUrl() + "AddSub";
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
	   		getInventoryList();
            $('#add-inventory-modal').modal('hide');
            document.getElementById('toast-container').classList.remove('bg-warning','bg-danger','bg-success');
            document.getElementById('toast-container').classList.add('bg-success');
            document.getElementById('my-message').innerHTML="The items were removed successfully from the inventory";
            $(".toast").toast('show');
	   },
	   error: function(response){
                         handleAjaxError(response);
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
	   error: function(response){
                         handleAjaxError(response);
              	   }
	});
}

function deleteInventory(id){
	var url = getSupervisorInventoryUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getInventoryList();
	   },
	  error: function(response){
                        handleAjaxError(response);
             	   }
	});
}


function displayInventoryList(data){
	var $tbody = $('#inventory-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml=''
		if(role=='supervisor'){
	     buttonHtml +='<button class="btn btn-dark" onclick="displayEditInventory(' +e.productId + ')" ><i class="bi bi-plus"></i> </button>'
		buttonHtml +='&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-dark" onclick="displayEditInventorys(' + e.productId + ')" ><i class="bi bi-dash"></i></button>'
		}
		else{
		buttonHtml +='<button class="btn btn-dark" onclick="displayEditInventory(' +e.productId + ')" disabled><i class="bi bi-plus"></i> </button>'
        		buttonHtml +='&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-dark" onclick="displayEditInventorys(' + e.productId + ')" disabled><i class="bi bi-dash"></i></button>'
		}
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
	   		displayInventory(data);
	   },
	   error: function(response){
                         handleAjaxError(response);
              	   }
	});


}



function displayEditInventorys(id){
	var url = getInventoryUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displaySubInventory(data);
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
    role= $("meta[name=role]").attr("content");
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
