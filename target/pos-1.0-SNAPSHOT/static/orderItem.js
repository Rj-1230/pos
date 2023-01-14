var orderId;
var customerName;
function getOrderItemUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/orderItem";
}

function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}

//BUTTON ACTIONS
function addOrderItem(event){
	//Set the values to update
	var $form = $("#orderItem-form");
	var json = toJson($form);
//	Form converted to JSON format
    console.log(json);

	var url = getOrderItemUrl();
	console.log(url);
//	url = localhost:9000/pos/api/orderItem

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
	   		getOrderItemList();     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});

	return false;
}

//This function is called on clicking of update button in the edit modal form
//function updateOrderItem(event){
//	$('#edit-orderItem-modal').modal('toggle');
//	//edit-orderItem-model is the id given to the entire block which gets toggled up when we click on Update button
//
//	var id = $("#orderItem-edit-form input[name=id]").val();
//	//edit-orderItem-form is the id given to the form inside the above mentioned block which opens for editing
////	That orderItem's id (not CSS ID )value is fetched and given to the variable id
//
//	var url = getOrderItemUrl() + "/" + id;
////	url = = localhost:9000/orderItem/api/orderItem/{id}
//
//	//Set the values to update
//	var $form = $("#orderItem-edit-form");
//	var json = toJson($form);
////    The form is converted to JSON and a PUT request (update) is called and thus details are updated
//	$.ajax({
//	   url: url,
//	   type: 'PUT',
//	   data: json,
//	   headers: {
//       	'Content-Type': 'application/json'
//       },
//	   success: function(response) {
//	   		console.log("OrderItem details are updated");
//	   		getOrderItemList();     //...
//	   },
//	   error: function(){
//	   		alert("An error has occurred");
//	   }
//	});
//
//	return false;
//}


function getOrderItemList(){
//    customerName[0].toUpperCase();
	var url = getOrderItemUrl() + "s/" + orderId;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		console.log("OrderItem-category data fetched");
	   		console.log(data);
	   		displayOrderItemList(data);     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}

function deleteCartItem(id){
	var url = getOrderItemUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		console.log("orderItem deleted");
	   		getOrderItemList();     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}

////UI DISPLAY METHODS

//All the orderItems are listed with the delete and edit button having event handlers upon click
function displayOrderItemList(data){

console.log('Printing customer name');
	console.log(customerName);


	console.log('Printing orderItems data');
	console.log(data);
	var $tbody = $('#orderItem-table').find('tbody');
	$tbody.empty();
//	ello
	for(var i in data){
		var e = data[i];
		if(e.quantity==0){
        		continue;
        		}
//		var buttonHtml ='<button class="btn btn-warning" onclick="displayEditorderItem(' + e.orderItemId + ')" >Edit Order</button>'
//		buttonHtml += '&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-danger" onclick="deleteOrderItem(' + e.orderItemId + ')" disabled>Delete</button>'
		var buttonHtml ='<button class="btn btn-warning" onclick="displayEditCartItem(' + e.orderItemId + ')" >Edit </button>'
        buttonHtml += '&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-danger" onclick="deleteCartItem(' + e.orderItemId + ')">Delete</button>'
		var row = '<tr>'
		+ '<td>' + e.orderItemId + '</td>'
		+ '<td>' + e.orderId + '</td>'
		+ '<td>' + e.productName + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + e.sellingPrice+ '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}

function displayEditCartItem(id){
	var url = getOrderItemUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		console.log("cart data is fetched");
	   		console.log(data);
//	   		Till here it was a normal GET reuqest
//            Now the function displayOrderItem is called
//        This will edit the orderItem's data and feed into the table
	   		displayCartItem(data);     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}

function updateCustomerDetails(){
	var url = getOrderUrl() + "/" + orderId;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   console.log(data);
	   		displayCustomerDetails(data);     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}


function displayCustomerDetails(data){
	$("#customer-edit-form input[name=customerName]").val(data.customerName);
	$("#customer-edit-form input[name=customerPhone]").val(data.customerPhone);
//	$("#cart-edit-form input[name=orderItemId]").val(data.orderItemId);
	$('#edit-customer-modal').modal('toggle');
}


function displayCartItem(data){
	$("#cart-edit-form input[name=sellingPrice]").val(data.sellingPrice);
	$("#cart-edit-form input[name=quantity]").val(data.quantity);
	$("#cart-edit-form input[name=orderItemId]").val(data.orderItemId);
	$('#edit-cart-modal').modal('toggle');
}

function updateCart(event){
	$('#edit-cart-modal').modal('toggle');
	//edit-orderItem-model is the id given to the entire block which gets toggled up when we click on Update button

	var id = $("#cart-edit-form input[name=orderItemId]").val();
	//edit-orderItem-form is the id given to the form inside the above mentioned block which opens for editing
//	That orderItem's id (not CSS ID )value is fetched and given to the variable id

	var url = getOrderItemUrl() + "/" + id;
//	url = = localhost:9000/orderItem/api/orderItem/{id}

	//Set the values to update
	var $form = $("#cart-edit-form");
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
	   		console.log("Cart details are updated");
	   		getOrderItemList();     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});

	return false;
}

function updateCustomer (event) {
	$('#edit-customer-modal').modal('toggle');
	//edit-orderItem-model is the id given to the entire block which gets toggled up when we click on Update button

//	var id = $("#customer-edit-form input[name=orderId]").val();
	//edit-orderItem-form is the id given to the form inside the above mentioned block which opens for editing
//	That orderItem's id (not CSS ID )value is fetched and given to the variable id

	var url = getOrderUrl() + "/" + orderId;
//	url = = localhost:9000/orderItem/api/orderItem/{id}

	//Set the values to update
	var $form = $("#customer-edit-form");
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
	   location.reload()
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});

	return false;
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



//INITIALIZATION CODE
function init(){
	$('#add-orderItem').click(addOrderItem);

	orderId= $("meta[name=orderId]").attr("content");
	customerName= $("meta[name=customerName]").attr("content");
	customerName[0].toUpperCase();
	console.log("Hieee");
	console.log(customerName);
	document.getElementById('inputOrderId').value=orderId;
    $('#updateCustomerDetails').click(updateCustomerDetails)
	$('#update-customer').click(updateCustomer)
    $('#update-cart').click(updateCart);
//	On clicking the button update, updateorderItem is called
	$('#refresh-data').click(getOrderItemList);
}
$(document).ready(init);
$(document).ready(getOrderItemList);
