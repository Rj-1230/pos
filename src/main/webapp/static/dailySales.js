function getReportUrl()
{
    var baseUrl = $("meta[name=baseUrl]").attr("content")
     	return baseUrl + "/api/dailyReport";
}

function displayDailySalesReportList(data)
{
    var $tbody = $('#dailySales-report-table').find('tbody');
        $tbody.empty();
        for(var i in data){
        		var e = data[i];
        		var invoiceDate = e.date.dayOfMonth +"-"+e.date.monthValue+"-"+e.date.year;
        		var row = '<tr>'
        		+ '<td>' + invoiceDate + '</td>'
        		+ '<td>'  + e.invoicedOrderCount + '</td>'
        		+ '<td>'  + e.invoicedItemsCount + '</td>'
        		+ '<td>'  + e.totalRevenue + '</td>'
        		+ '</tr>';
                $tbody.append(row);

        	}

}


function getDailySalesList()
{
    var url = getReportUrl();
    $.ajax({
    	   url: url,
    	   type: 'GET',
    	   success: function(data) {
    	   		displayDailySalesReportList(data);
    	   }
    	});
   }


$(document).ready(getDailySalesList);