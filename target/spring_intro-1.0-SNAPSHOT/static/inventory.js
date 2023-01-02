
function getInventoryUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/inventory";
}

//BUTTON ACTIONS
function addInventory(event){
	//Set the values to update
	var $form = $("#inventory-form");
	var json = toJsons($form);
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
function updateInventory(event){
	$('#edit-inventory-modal').modal('toggle');
	//edit-inventory-model is the id given to the entire block which gets toggled up when we click on Update button

	var id = $("#inventory-edit-form input[name=id]").val();
	//edit-inventory-form is the id given to the form inside the above mentioned block which opens for editing
//	That inventory's id (not CSS ID )value is fetched and given to the variable id

	var url = getInventoryUrl() + "/" + id;
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
		var buttonHtml ='<button class="btn btn-warning" onclick="displayEditinventory(' + e.productId + ')" >Edit</button>'
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
function displayEditinventory(id){
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

function displayInventory(data){
	$("#inventory-edit-form input[name=inventory]").val(data.inventory);
	$("#inventory-edit-form input[name=category]").val(data.category);
	$("#inventory-edit-form input[name=id]").val(data.id);
	$('#edit-inventory-modal').modal('toggle');
}


//    HELPER METHOD
    function toJsons($form){
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


//INITIALIZATION CODE
function init(){
	$('#add-inventory').click(addInventory);

//	On clicking the button update, updateinventory is called
	$('#update-inventory').click(updateInventory);
	$('#refresh-data').click(getInventoryList);
}

$(document).ready(init);
$(document).ready(getInventoryList);
