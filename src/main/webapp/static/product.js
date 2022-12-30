
function getProductUrl(){
	var baseUrl = $("meta[name=baseUrl]").attr("content")
	return baseUrl + "/api/product";
}

//BUTTON ACTIONS
function addProduct(event){
	//Set the values to update
	var $form = $("#product-form");
	var json = toJson($form);
//	Form converted to JSON format

	var url = getProductUrl();
	console.log("Here");
//	url = localhost:9000/pos/api/product

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
//	   		console.log("Product created");
	   		getProductList();     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});

	return false;
}

//This function is called on clicking of update button in the edit modal form
function updateProduct(event){
	$('#edit-product-modal').modal('toggle');
	//edit-product-model is the id given to the entire block which gets toggled up when we click on Update button

	var id = $("#product-edit-form input[name=productId]").val();
	//edit-product-form is the id given to the form inside the above mentioned block which opens for editing
//	That product's id (not CSS ID )value is fetched and given to the variable id

	var url = getProductUrl() + "/" + id;
//	url = = localhost:9000/product/api/product/{id}

	//Set the values to update
	var $form = $("#product-edit-form");
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
	   		console.log("Product details are updated");
	   		getProductList();     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});

	return false;
}


function getProductList(){
	var url = getProductUrl();
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		console.log("All products fetched");
	   		console.log(data);
	   		displayProductList(data);     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}

function deleteProduct(id){
	var url = getProductUrl() + "/" + id;

	$.ajax({
	   url: url,
	   type: 'DELETE',
	   success: function(data) {
	   		console.log("product deleted");
	   		getProductList();     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}

////UI DISPLAY METHODS

//All the products are listed with the delete and edit button having event handlers upon click
function displayProductList(data){
	console.log('Printing product data');
	var $tbody = $('#product-table').find('tbody');
	$tbody.empty();
	for(var i in data){
		var e = data[i];
		var buttonHtml ='<button class="btn btn-warning" onclick="displayEditProduct(' + e.productId + ')">Edit</button>'
		buttonHtml += '&nbsp;&nbsp;&nbsp;&nbsp;<button class="btn btn-danger" onclick="deleteProduct(' + e.productId+ ')">Delete</button>'
		var row = '<tr>'
		+ '<td>' + e.productId + '</td>'
		+ '<td>' + e.barcode + '</td>'
		+ '<td>'  + e.brandId + '</td>'
		+ '<td>'  + e.name + '</td>'
		+ '<td>'  + e.mrp + '</td>'
		+ '<td>' + buttonHtml + '</td>'
		+ '</tr>';
        $tbody.append(row);
	}
}


//As the edit button is clicked in the table , its id is passed to this method and the method is called
function displayEditProduct(id){
	var url = getProductUrl() + "/" + id;
	$.ajax({
	   url: url,
	   type: 'GET',
	   success: function(data) {
	   		console.log("Product data is fetched");
	   		console.log(data);
//	   		Till here it was a normal GET reuqest
//            Now the function displayProduct is called
//        This will edit the product's data and feed into the table
	   		displayProduct(data);     //...
	   },
	   error: function(){
	   		alert("An error has occurred");
	   }
	});
}

function displayProduct(data){
	$("#product-edit-form input[name=barcode]").val(data.barcode);
	$("#product-edit-form input[name=brandId]").val(data.brandId);
	$("#product-edit-form input[name=name]").val(data.name);
	$("#product-edit-form input[name=mrp]").val(data.mrp);
	$("#product-edit-form input[name=productId]").val(data.productId);
	$('#edit-product-modal').modal('toggle');
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
	$('#add-product').click(addProduct);

////	On clicking the button update, updateproduct is called
	$('#update-product').click(updateProduct);
	$('#refresh-data').click(getProductList);
}

$(document).ready(init);
$(document).ready(getProductList);
