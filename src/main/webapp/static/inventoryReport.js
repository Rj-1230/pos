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


function getBrandsList()
{
    var url = getBrandUrl();
    $.ajax({
    	   url: url,
    	   type: 'GET',
    	   success: function(data) {
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
    var a = getBrandOption();

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
    var brandFilter = getBrandOption();
    var categoryFilter = getCategoryOption();
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
    $('#inputFilterBrand').change(displayCategoryList);
    $('#apply-brand-category-filter').click(applyBrandCategoryFilter);
}

$(document).ready(init);
$(document).ready(getAllList);