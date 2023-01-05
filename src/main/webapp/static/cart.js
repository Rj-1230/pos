function getCartUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/cart";
}

function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/cartFlush";
}


//BUTTON ACTIONS
function addItemToCart(event){
	//Set the values to update
	var $form = $("#cart-form");
	var json = toJson($form);
    console.log(json);

	var url = getCartUrl();
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
	   		getCartItemList();     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});

	return false;
}


function addOrder(event){
	//Set the values to update
	var $form = $("#customer-form");
	var json = toJson($form);
//	Form converted to JSON format
    console.log(json);

	var url = getOrderUrl();
	console.log(url);
//	url = localhost:9000/pos/api/order

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


function getCartItemList(){
//    customerName[0].toUpperCase();
	var url = getCartUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		console.log("OrderItem-category data fetched");
	   		console.log(data);
	   		displayCartItemList(data);     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}

//function deleteOrderItem(id){
//	var url = getOrderItemUrl() + "/" + id;
//
//	$.ajax({
//	   url: url,
//	   type: 'DELETE',
//	   success: function(data) {
//	   		console.log("orderItem deleted");
//	   		getOrderItemList();     //...
//	   },
//	   error: function(){
//	   		alert("An error has occurred");
//	   }
//	});
//}

////UI DISPLAY METHODS

//All the orderItems are listed with the delete and edit button having event handlers upon click
function displayCartItemList(data){

//console.log('Printing customer name');
//	console.log(customerName);


	console.log('Printing orderItems data');
	console.log(data);
	var $tbody = $('#cartItem-table').find('tbody');
	$tbody.empty();
//	ello
	for(var i in data){
		var e = data[i];
		var buttonHtml ='<button class="btn btn-warning" onclick="displayEditCartItem(' + e.cartItemId + ')" >Edit </button>'
		buttonHtml += '&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-danger" onclick="deleteCartItem(' + e.cartItemId + ')" disabled>Delete</button>'
		var row = '<tr>'
		+ '<td>' + e.cartItemId + '</td>'
		+ '<td>' + e.productName + '</td>'
		+ '<td>' + e.quantity + '</td>'
		+ '<td>' + e.sellingPrice+ '</td>'
		+ '<td>' + e.counterId + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}


//As the edit button is clicked in the table , its id is passed to this method and the method is called
//function displayEditorderItem(id){
//	var url = getOrderItemUrl() + "/" + id;
//	$.ajax({
//	   url: url,
//	   type: 'GET',
//	   success: function(data) {
//	   		console.log("OrderItem data is fetched");
//	   		console.log(data);
////	   		Till here it was a normal GET reuqest
////            Now the function displayOrderItem is called
////        This will edit the orderItem's data and feed into the table
//	   		displayOrderItem(data);     //...
//	   },
//	   error: function(){
//	   		alert("An error has occurred");
//	   }
//	});
//}

//function displayOrderItem(data){
//	$("#orderItem-edit-form input[name=orderItem]").val(data.orderItem);
//	$("#orderItem-edit-form input[name=category]").val(data.category);
//	$("#orderItem-edit-form input[name=id]").val(data.id);
//	$('#edit-orderItem-modal').modal('toggle');
//}


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
	$('#add-cartItem').click(addItemToCart);
	$('#create-order').click(addOrder);
	$('#refresh-data').click(getCartItemList);
}
$(document).ready(init);
$(document).ready(getCartItemList);
