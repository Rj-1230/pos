var newBrands = {};
var brandReportData;
//function getInventoryReportUrl(){
// 	var baseUrl = $("meta[name=baseUrl]").attr("content")
// 	return baseUrl + "/api/inventoryReport";
// }

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

function displayBrandReportList(data)
{
    var $tbody = $('#brand-report-table').find('tbody');
        $tbody.empty();
        for(var i in data){
        		var e = data[i];
        		var row = '<tr>'
        		+ '<td>' + e.brand + '</td>'
        		+ '<td>'  + e.category + '</td>'
        		+ '</tr>';
                $tbody.append(row);

        	}

}


function getBrandsList()
{
    var url = getBrandUrl();
    $.ajax({
    	   url: url,
    	   type: 'GET',
    	   success: function(data) {
    	   		console.log(data);
    	   		displayBrandsList(data);
    	   		brandReportData=data;
    	   		displayBrandReportList(data);
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
    for(var i = 0; i<brandReportData.length; i++){
        if(check(brandReportData[i].brand, brandFilter) && check(brandReportData[i].category, categoryFilter))
            data.push(brandReportData[i]);
    }
    displayBrandReportList(data);
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
$(document).ready(getBrandsList);