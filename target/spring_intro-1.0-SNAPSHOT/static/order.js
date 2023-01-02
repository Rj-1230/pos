
function getOrderUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/order";
}

//BUTTON ACTIONS
function addOrder(event){
	//Set the values to update
	var $form = $("#order-form");
	var json = toJsons($form);
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
	   		getOrderList();     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});

	return false;
}

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


function getOrderList(){
	var url = getOrderUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		console.log("Order-category data fetched");
	   		console.log(data);
	   		displayOrderList(data);     //...
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
	console.log('Printing order-category data');
	var $tbody = $('#order-table').find('tbody');
	$tbody.empty();
//	ello
	for(var i in data){
		var e = data[i];
		console.log(e.orderId)
		var buttonHtml ='<button class="btn btn-warning"> <a href="orderItem/'+e.orderId+'" class="link link-dark">Edit</a></button>'
		var row = '<tr>'
		+ '<td>' + e.orderId + '</td>'
		+ '<td>' + e.customerName + '</td>'
		+ '<td>' + e.orderTime + '</td>'
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
	$('#add-order').click(addOrder);

//	On clicking the button update, updateorder is called
	$('#refresh-data').click(getOrderList);
}

$(document).ready(init);
$(document).ready(getOrderList);
