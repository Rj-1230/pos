var newBrands = {};
var inventoryReportData;
function getInventoryReportUrl(){
 	var baseUrl = $("meta[name=baseUrl]").attr("content")
 	return baseUrl + "/api/inventoryReport";
 }

function getBrandUrl()
{
    var baseUrl = $("meta[name=baseUrl]").attr("content")
     	return baseUrl + "/api/brand";
}

function getBrandOption() {
        selectElement = document.querySelector('#inputFilterBrand');
        output = selectElement.options[selectElement.selectedIndex].value;
        return output;
}

function getCategoryOption() {
        selectElement = document.querySelector('#inputFilterCategory');
        output = selectElement.options[selectElement.selectedIndex].value;
        return output;
}

function getAllList()
{
    getInventoryList();
    getBrandsList();
}


//function displayRevenueProductList(data)
//{
//    var $tbody = $('#product-revenue-list-table').find('tbody');
//    $tbody.empty();
//    var total_prod_quantity =0;
//    var total_prod_revenue=0;
//    for(var i in data){
//    		var e = data[i];
//    		console.log(e);
//    		var row = '<tr>'
//    		+ '<td>' + e.productId + '</td>'
//    		+ '<td>' + e.barcode + '</td>'
//    		+ '<td>'  + e.name + '</td>'
//    		+ '<td>'  + e.mrp + '</td>'
//    		+ '<td>'  + e.quantity + '</td>'
//    		+ '<td>'  + e.total + '</td>'
//    		+ '</tr>';
//            $tbody.append(row);
//            total_prod_quantity+=e.quantity;
//            total_prod_revenue+=e.total;
//    	}
//    	var row = '<tr>'
//            		+ '<td>' + "<font size='+2'><b>Total</b></font>"+ '</td>'
//            		+ '<td>' +"" + '</td>'
//            		+ '<td>'  + "" + '</td>'
//            		+ '<td>'  + "" + '</td>'
//            		+ '<td>'  +  "<b><font size='+2'>"+total_prod_quantity +  "</b></font>"+'</td>'
//            		+ '<td>'  + "<b><font size='+2'>&#8377;"+total_prod_revenue +"</b></font>"+ '</td>'
//            		+ '</tr>';
//            		$tbody.append(row);
//    	console.log(total_prod_quantity);
//    	console.log(total_prod_revenue);
//
//}
//
//function displayRevenueBrandList(data)
//{
//    var $tbody = $('#brand-revenue-list-table').find('tbody');
//        $tbody.empty();
//        var total_brand_quantity =0;
//        var total_brand_revenue=0;
//        for(var i in data){
//        		var e = data[i];
//        		var brandName = e.brand;
//        		var row = '<tr onclick=displayBrandRevenue()>'
//        		+ '<td>' + e.brand + '</td>'
//        		+ '<td>'  + e.quantity + '</td>'
//        		+ '<td>'  + e.totalBrandRevenue + '</td>'
//        		+ '</tr>';
//                $tbody.append(row);
//                total_brand_quantity+=e.quantity;
//                total_brand_revenue+=e.totalBrandRevenue;
////                console.log(e.brand);
//        	}
//        	var row = '<tr>'
//                        		+ '<td>' + "<font size='+2'><b>Total</b></font>"+ '</td>'
//
//                        		+ '<td>'  +  "<b><font size='+2'>"+total_brand_quantity +  "</b></font>"+'</td>'
//                        		+ '<td>'  + "<b><font size='+2'>&#8377;"+total_brand_revenue +"</b></font>"+ '</td>'
//                        		+ '</tr>';
//                        		$tbody.append(row);
//}
//


//function displayRevenueCategoryList(data)
//{
//    var $tbody = $('#category-revenue-list-table').find('tbody');
//        $tbody.empty();
//        var total_category_quantity =0;
//        var total_category_revenue=0;
//        for(var i in data){
//        		var e = data[i];
//        		var row = '<tr onclick = "displayCategory()">'
//        		+ '<td>' + e.category + '</td>'
//        		+ '<td>'  + e.quantity + '</td>'
//        		+ '<td>'  + e.totalCategoryRevenue + '</td>'
//        		+ '</tr>';
//                $tbody.append(row);
//                total_category_quantity+=e.quantity;
//                                total_category_revenue+=e.totalCategoryRevenue;
//        	}
//        	var row = '<tr>'
//                                    		+ '<td>' + "<font size='+2'><b>Total</b></font>"+ '</td>'
//
//                                    		+ '<td>'  +  "<b><font size='+2'>"+total_category_quantity +  "</b></font>"+'</td>'
//                                    		+ '<td>'  + "<b><font size='+2'>&#8377;"+total_category_revenue +"</b></font>"+ '</td>'
//                                    		+ '</tr>';
//                                    		$tbody.append(row);
//}
//




function displayInventoryReportList(data)
{
    var $tbody = $('#inventory-report-table').find('tbody');
        $tbody.empty();
        var total_quantity =0;
        for(var i in data){
        		var e = data[i];
        		var row = '<tr>'
        		+ '<td>' + e.brand + '</td>'
        		+ '<td>'  + e.category + '</td>'
        		+ '<td>'  + e.quantity + '</td>'
        		+ '</tr>';
                $tbody.append(row);
                total_quantity+=e.quantity;

        	}
        	var row = '<tr>'
                                    		+ '<td colspan=2>' + "<font size='+2'><b>Total</b></font>"+ '</td>'

                                    		+ '<td>'  +  "<b><font size='+2'>"+total_quantity +  "</b></font>"+'</td>'

                                    		+ '</tr>';
                                    		$tbody.append(row);
}



//function displayBrandRevenue()
//{
//console.log("Hi");
//    console.log("this will display revenue of brand: ");
//}
//
//function displayCategory()
//{
//    console.log("this will display revenue of category: ");
//}
//
//function showBrandView()
//{
//    document.getElementById("category-div").style.display = "none";
//    document.getElementById("product-div").style.display = "none";
//    document.getElementById("brand-div").style.display = "block";
//}
//
//function showCategoryView()
//{
//    document.getElementById("category-div").style.display = "block";
//    document.getElementById("product-div").style.display = "none";
//    document.getElementById("brand-div").style.display = "none";
//}
//
//function showProductView()
//{
//    document.getElementById("category-div").style.display = "none";
//    document.getElementById("product-div").style.display = "block";
//    document.getElementById("brand-div").style.display = "none";
//}
//
function getBrandsList()
{
    var url = getBrandUrl();
    $.ajax({
    	   url: url,
    	   type: 'GET',
    	   success: function(data) {
    	   		console.log(data);
    	   		displayBrandsList(data);
    	   }
    	});
   }

function getInventoryList()
{
    var url = getInventoryReportUrl();
    $.ajax({
    	   url: url,
    	   type: 'GET',
    	   success: function(data) {
    	   		console.log(data);
    	   		inventoryReportData=data;
    	   		displayInventoryReportList(data);
    	   }
    	});
   }



   function displayBrandsList(data)
   {
       Object.assign(newBrands, {"All":[]});
       for(var i in data)
       {
           var a = data[i].brand;
           var b = data[i].category;
           if(!newBrands.hasOwnProperty(a))
               Object.assign(newBrands, {[a]:[]});
           newBrands[a].push(b);
           newBrands["All"].push(b);
       }

       newBrands["All"] = removeDuplicates(newBrands["All"]);
       console.log(newBrands);

       var $elB = $("#inputFilterBrand");

       $elB.empty();

       $.each(newBrands, function(key,value) {
             $elB.append($("<option></option>")
                .attr("value", key).text(key));
           });

       displayCategoryList();
   }

function displayCategoryList()
{
    var $elC = $("#inputFilterCategory");

    $elC.empty();

    console.log("this is it");
    var a = getBrandOption();

    console.log(newBrands[a]);

    $elC.append($("<option></option>")
                    .attr("value", "All").text("All"));

    for(var i=0; i<newBrands[a].length; i++)
    {
        $elC.append($("<option></option>")
            .attr("value", newBrands[a][i]).text(newBrands[a][i]));
    }
}

function applyBrandCategoryFilter()
{
console.log("this is it");
    var brandFilter = getBrandOption();
    var categoryFilter = getCategoryOption();
    console.log(brandFilter);
    console.log(categoryFilter);
    var data = [];
    for(var i = 0; i<inventoryReportData.length; i++){
        if(check(inventoryReportData[i].brand, brandFilter) && check(inventoryReportData[i].category, categoryFilter))
            data.push(inventoryReportData[i]);
    }
    displayInventoryReportList(data);
}

// helpers

function check(a, b)
{
    if(b=="All" || a==b)
        return true;
    return false;
}

function removeDuplicates(arr) {
        return arr.filter((item,
            index) => arr.indexOf(item) === index);
}

function init()
{
//    $('#show-revenue').click(getRevenueList);
//    $('#product-view').click(showProductView);
//    $('#brand-view').click(showBrandView);
//    $('#category-view').click(showCategoryView);
//    document.getElementById('inputEndDate').value = new Date().toISOString().substring(0, 10);
//    document.getElementById("category-div").style.display = "none";
//    document.getElementById("product-div").style.display = "none";
//    document.getElementById("brand-div").style.display = "none";
//
    $('#inputFilterBrand').change(displayCategoryList);
    $('#apply-brand-category-filter').click(applyBrandCategoryFilter);
}

$(document).ready(init);
$(document).ready(getAllList);