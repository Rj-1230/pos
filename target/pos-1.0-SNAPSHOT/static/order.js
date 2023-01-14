
function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}

function getCartUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/cart";
}

function addOrder(event){
	$('#customerModal').modal('toggle');
	//Set the values to update
	var $form = $("#customer-form");

	var json = toJson($form);

	var url = getCartUrl();
	url += 'Flush';
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
            getCartItemList();
            var baseUrl = $("meta[name=baseUrl]").attr("content");
            window.location.href = baseUrl + "/ui/orders";
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});

	return false;
}

////BUTTON ACTIONS
//function addOrder(event){
//	//Set the values to update
//	var $form = $("#order-form");
//	var json = toJsons($form);
////	Form converted to JSON format
//    console.log(json);
//
//	var url = getOrderUrl();
//	console.log(url);
////	url = localhost:9000/pos/api/order
//
////    AJAX call is done
//	$.ajax({
//	   url: url,
//	   type: 'POST',
//	   data: json,
//	   headers: {
////	   Header is added mandatorily
//       	'Content-Type': 'application/json'
//       },
//	   success: function(response) {
//	   		console.log("this is response" + response);
//            document.getElementById('logg').innerHTML=response;
//	   		getOrderList();     //...
//	   },
//	   error: function(){
//	   		alert("An error has occurred");
//	   }
//	});
//
//	return false;
//}

//This function is called on clicking of update button in the edit modal form
//function updateOrder(event){
//	$('#edit-order-modal').modal('toggle');
//	//edit-order-model is the id given to the entire block which gets toggled up when we click on Update button
//
//	var id = $("#order-edit-form input[name=id]").val();
//	//edit-order-form is the id given to the form inside the above mentioned block which opens for editing
////	That order's id (not CSS ID )value is fetched and given to the variable id
//
//	var url = getOrderUrl() + "/" + id;
////	url = = localhost:9000/order/api/order/{id}
//
//	//Set the values to update
//	var $form = $("#order-edit-form");
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
//	   		console.log("Order details are updated");
//	   		getOrderList();     //...
//	   },
//	   error: function(){
//	   		alert("An error has occurred");
//	   }
//	});
//
//	return false;
//}

function placeOrder (orderId) {
	var url = getOrderUrl() + "/place/" + orderId;
    $.ajax({
    	   url: url,
    	   type: 'PUT',
    	   headers: {
           	'Content-Type': 'application/json'
           },
    	   success: function(response) {
    	   getOrderList();
    	   },
    	   error: function(){
    	   		alert("An error has occurred");
    	   }
    	});

    	return false;

}


function getOrderList(){
	var url = getOrderUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayOrderList(data);
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}

//function deleteOrder(id){
//	var url = getOrderUrl() + "/" + id;
//
//	$.ajax({
//	   url: url,
//	   type: 'DELETE',
//	   success: function(data) {
//	   		console.log("order deleted");
//	   		getOrderList();     //...
//	   },
//	   error: function(){
//	   		alert("An error has occurred");
//	   }
//	});
//}

////UI DISPLAY METHODS

//All the orders are listed with the delete and edit button having event handlers upon click
function displayOrderList(data){
	var $tbody = $('#order-table').find('tbody');
	$tbody.empty();
//	ello
	for(var i in data){
		var e = data[i];
		if(e.status=="invoiced"){
		var buttonHtml ='<button class="btn btn-dark" disabled> <i class="bi bi-pen"></i></button>'
		buttonHtml+='&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-success" id="generate-invoice">Invoice <i class="bi bi-receipt-cutoff"></i></a></button>'
		}
		else{
		var buttonHtml ='<button class="btn btn-dark"> <a href="orderItem/'+e.orderId+'" style="text-decoration:none; color:white;"><i class="bi bi-pen"></i></a></button>'
		buttonHtml+='&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-primary" onclick="placeOrder('+e.orderId + ')"> Place Order </a></button>'
		}
		var row = '<tr>'
		+ '<td>' + e.orderId + '</td>'
		+ '<td>' + e.customerName + '</td>'
		+ '<td>' + e.customerPhone + '</td>'
		+ '<td>' + e.orderCreateTime + '</td>'
		+ '<td>' + e.orderPlaceTime + '</td>'
		+ '<td>' + e.status + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}


//As the edit button is clicked in the table , its id is passed to this method and the method is called
//function displayEditorder(id){
//	var url = getOrderUrl() + "/" + id;
//	$.ajax({
//	   url: url,
//	   type: 'GET',
//	   success: function(data) {
//	   		console.log("Order data is fetched");
//	   		console.log(data);
////	   		Till here it was a normal GET reuqest
////            Now the function displayOrder is called
////        This will edit the order's data and feed into the table
//	   		displayOrder(data);     //...
//	   },
//	   error: function(){
//	   		alert("An error has occurred");
//	   }
//	});
//}

//function displayOrder(data){
//	$("#order-edit-form input[name=order]").val(data.order);
//	$("#order-edit-form input[name=category]").val(data.category);
//	$("#order-edit-form input[name=id]").val(data.id);
//	$('#edit-order-modal').modal('toggle');
//}


function getCartItemList(){
	var url = getCartUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayCartItemList(data);
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}

function displayCartItemList(data){
	if(data.length==0){
	    document.getElementById("create-new-order").style.display = "none";
	}
	else{
	document.getElementById("create-new-order").style.display = "block";
	}
	var $tbody = $('#cartItem-table').find('tbody');
	$tbody.empty();

	for(var i in data){
		var e = data[i];
		if(e.quantity==0){
		continue;
		}
		var buttonHtml ='<button class="btn btn-warning" onclick="displayEditCartItem(' + e.cartItemId + ')" >Edit </button>'
		buttonHtml += '&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-danger" onclick="deleteCartItem(' + e.cartItemId + ')">Delete</button>'
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

	function addItemToCart(event){
    	//Set the values to update
    	var $form = $("#cart-form");
    	var json = toJson($form);
    	var url = getCartUrl();
    	$.ajax({
    	   url: url,
    	   type: 'POST',
    	   data: json,
    	   headers: {
    //	   Header is added mandatorily
           	'Content-Type': 'application/json'
           },
    	   success: function(response) {
    	   		getCartItemList();     //...
    	   },
    	   error: function(){
    	   		alert("An error has occurred");
    	   }
    	});

    	return false;
    }

    function emptyCart(event){

    	var url = getCartUrl() + "Flushs";
    	$.ajax({
    	   url: url,
    	   type: 'DELETE',
    	   success: function(data) {
    	   		getCartItemList();     //...
    	   },
    	   error: function(){
    	   		alert("An error has occurred");
    	   }
    	});

    	return false;
    }


    function updateCart(event){
$('#edit-cart-modal').modal('toggle');
    	var id = $("#cart-edit-form input[name=cartItemId]").val();
    	var url = getCartUrl() + "/" + id;
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
    	   		getCartItemList();     //...
    	   },
    	   error: function(){
    	   		alert("An error has occurred");
    	   }
    	});

    	return false;
    }



function deleteCartItem(id){
	var url = getCartUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		getCartItemList();     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}

function displayEditCartItem(id){
	var url = getCartUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		displayCartItem(data);
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}

function displayCartItem(data){
	$("#cart-edit-form input[name=sellingPrice]").val(data.sellingPrice);
	$("#cart-edit-form input[name=quantity]").val(data.quantity);
	$("#cart-edit-form input[name=cartItemId]").val(data.cartItemId);
	document.getElementById("edit-cart-modal").style.zIndex = "9999";
	document.getElementById("edit-cart-modal").style.backgroundColor = "rgba(0, 0, 0, 0.4)";
	$('#edit-cart-modal').modal('toggle');
}

function createNewOrder(){
document.getElementById("customerModal").style.zIndex = "9999";
	document.getElementById("customerModal").style.backgroundColor = "rgba(0, 0, 0, 0.4)";
	$('#customerModal').modal('toggle');
}



//INITIALIZATION CODE
function init(){

$('#update-cart').click(updateCart);
	$('#create-order').click(addOrder);
	$('#add-cartItem').click(addItemToCart);
	$('#empty-cart').click(emptyCart);
    $('#create-new-order').click(createNewOrder);
//	On clicking the button update, updateorder is called
	$('#refresh-data').click(getOrderList);
}

$(document).ready(init);
$(document).ready(getOrderList);
$(document).ready(getCartItemList);