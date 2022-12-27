function getDashboardPanel() {
	var panel = new Ext.Panel({
		title: 'Dashboard',
		layout: 'column',
		bodyCssClass: 'dashboard-background',
		padding: 10,
		border: false,
		buttonAlign: 'center',
		items: getDashboardItems(),
		buttons: ['<span style="color:whitesmoke">Welcome to Systematic Case Paper Management</span>']
	});
	
	panel.on('render',function(){
		Ext.Ajax.request({
				url: '/casepaper/getdashboardvalues',
				params:{
					user:'user'
				},
				method:'POST',
				scope :this,
				success: function(response, request) {
					var responseJson = JSON.parse(response.responseText);
					//var items = this.items.items;
					var data = responseJson.data;
					
					/*for(var i=0;i<items.length;i++){
						var item = items[i];
						for (var key in data) {
							if (item.items.map.hasOwnProperty(key)) {
								var panel = item.items.map[key];
								if(panel){
									panel.body.dom.innerHTML = data[key];	
								}
							}
						}
					}*/
					
					var totalCasePapers = this.items.map.column1.items.map.totalCasePapers;
					totalCasePapers.body.dom.innerHTML = data["totalCasePapers"] || '0';
					
					var monthlyCasePaperChart = this.items.map.column1.items.map.monthlyCasePaperChart;
					var monthlyData = data["monthlyCasePapers"];
					for (var key in monthlyData) {
						if (monthlyData.hasOwnProperty(key)) {
							monthlyCasePaperChart.dataPoints.push({ 
								x: stringToDate(key,"mm-dd-yyyy","-"), 
								y: monthlyData[key] 
							});		
						}
					}
					monthlyCasePaperChart.chart.render();
					
					var casePapersForLastMonth = this.items.map.column2.items.map.casePapersForLastMonth;
					casePapersForLastMonth.body.dom.innerHTML = data["casePapersForLastMonth"]|| '0';
					
					var weeklyCasePapers = this.items.map.column2.items.map.weeklyCasePapers;
					
					var weeklyData = data["weeklyCasePapers"];
					for (var key in weeklyData) {
						if (weeklyData.hasOwnProperty(key)) {
							weeklyCasePapers.dataPoints.push({ 
								x: stringToDate(key,"mm-dd-yyyy","-"), 
								y: weeklyData[key] 
							});		
						}
					}
					/*
					weeklyCasePapers.dataPoints.push({ x: new Date(2016, 00, 01), y: 10 });
					weeklyCasePapers.dataPoints.push({ x: new Date(2016, 01, 02), y: 45 });
					weeklyCasePapers.dataPoints.push({ x: new Date(2016, 02, 03), y: 30 });
					weeklyCasePapers.dataPoints.push({ x: new Date(2016, 03, 04), y: 54 });
					*/
					weeklyCasePapers.chart.render();
					
					var casePapersForCurrentMonth = this.items.map.column3.items.map.casePapersForCurrentMonth;
					casePapersForCurrentMonth.body.dom.innerHTML = data["casePapersForCurrentMonth"]|| '0';
					
					var casePapersByAge = this.items.map.column3.items.map.casePapersByAge;
					var casePaperByAgeData = data["casePapersByAge"];
					for (var key in casePaperByAgeData) {
						if (casePaperByAgeData.hasOwnProperty(key)) {
							casePapersByAge.dataPoints.push({ 
								label: key, 
								y: casePaperByAgeData[key] 
							});		
						}
					}
					/*
					casePapersByAge.dataPoints.push({y: 79.45, label: "Google"});
					casePapersByAge.dataPoints.push({y: 7.31, label: "Bing"});
					casePapersByAge.dataPoints.push({y: 7.06, label: "Baidu"});
					casePapersByAge.dataPoints.push({y: 4.91, label: "Yahoo"});
					casePapersByAge.dataPoints.push({y: 1.26, label: "Others"});
					*/
					casePapersByAge.chart.render();
					
				},
				failure: function() {
					
				}
			});
	},panel);
	
	return panel;

}

function stringToDate(_date, _format, _delimiter) {
	var formatLowerCase = _format.toLowerCase();
	var formatItems = formatLowerCase.split(_delimiter);
	var dateItems = _date.split(_delimiter);
	var monthIndex = formatItems.indexOf("mm");
	var dayIndex = formatItems.indexOf("dd");
	var yearIndex = formatItems.indexOf("yyyy");
	var month = parseInt(dateItems[monthIndex]);
	month -= 1;
	var formatedDate = new Date(dateItems[yearIndex], month, dateItems[dayIndex]);
	return formatedDate;
}

function getDashboardItems(){
	var items=[];
	
	items.push({
		xtype: 'panel',
		columnWidth: 0.33,
		bodyCssClass:'dashboard-background',
		itemId:'column1',
		padding:5,
		items:getDashboardColumn1(),
		border:false
	});
	items.push({
		xtype: 'panel',
		columnWidth: 0.33,
		bodyCssClass:'dashboard-background',
		itemId:'column2',
		padding:5,
		items:getDashboardColumn2(),
		border:false
	});
	items.push({
		xtype: 'panel',
		columnWidth: 0.33,
		bodyCssClass:'dashboard-background',
		itemId:'column3',
		padding:5,
		items:getDashboardColumn3(),
		border:false
	});
	
	return items;
}


function getDashboardColumn1(){
	var items = [];
	
	items.push({
		xtype: 'panel',
		title:'Total case papers',
		itemId:'totalCasePapers',
		headerCfg:{
			cls: 'portlet-header'
		},
		bodyCssClass:'portlet-body',
		padding:5,
		height:200
	});
	
	var chartPanel = new Ext.Panel({
		xtype: 'panel',
		title:'Monthly case papers',
		//id:'monthlyCasePaperChart',
		itemId:'monthlyCasePaperChart',
		//autoScroll: true,
		headerCfg:{
			cls: 'portlet-header'
		},
		bodyCssClass:'portlet-body',
		padding:5,
		height:250
	});

	chartPanel.on('afterrender', function() {
		try {

			this.dataPoints = [];
			
			this.chart = new CanvasJS.Chart(this.body.id, {
				animationEnabled: true,
				theme: "light2",
				axisY: {
					title: "Case Papers",
					titleFontSize: 10,
					includeZero: true
				},
				data: [{
					type: "column",
					xValueFormatString: "MMM YYYY",
					yValueFormatString: "#,### case papers",
					dataPoints: this.dataPoints
				}]
			});
			
		} catch (e) {
			console.log(e);
		}
	}, chartPanel);

	items.push(chartPanel);
	return items;
}

function getDashboardColumn2(){
	var items = [];
	
	items.push({
		xtype: 'panel',
		title:'Case papers for last month',
		itemId:'casePapersForLastMonth',
		headerCfg:{
			cls: 'portlet-header'
		},
		bodyCssClass:'portlet-body',
		padding:5,
		height:200
	});

	
	
	
	var chartPanel = new Ext.Panel({
		xtype: 'panel',
		title: 'Weekly case papers',
		itemId:'weeklyCasePapers',
		headerCfg:{
			cls: 'portlet-header'
		},
		bodyCssClass:'portlet-body',
		padding: 5,
		height: 250
	});
	
	chartPanel.on('afterrender', function() {
		try {
			this.dataPoints=[];
			this.chart = new CanvasJS.Chart(this.body.id, {
				animationEnabled: true,
				axisY: {
					title: "Case Papers"
				},
				data: [{
					xValueFormatString: "DD MMM",
					type: "spline",
					dataPoints: this.dataPoints
				}]
			});
			
		} catch (e) {
			console.log(e);
		}
	}, chartPanel);
	items.push(chartPanel);
	return items;
}

function getDashboardColumn3(){
	var items = [];
	
	items.push({
		xtype: 'panel',
		title:'Case papers for current month',
		itemId:'casePapersForCurrentMonth',
		headerCfg:{
			cls: 'portlet-header'
		},
		bodyCssClass:'portlet-body',
		padding:5,
		height:200
	});



	var chartPanel = new Ext.Panel({
		xtype: 'panel',
		title: 'Case papers by age',
		itemId: 'casePapersByAge',
		headerCfg: {
			cls: 'portlet-header'
		},
		bodyCssClass: 'portlet-body',
		padding: 5,
		height: 250
	});

	chartPanel.on('afterrender', function() {
		try {
			this.dataPoints = [];
			this.chart = new CanvasJS.Chart(this.body.id, {
				animationEnabled: true,
				data: [{
					type: "pie",
					startAngle: 240,
					yValueFormatString: "##0.00\"%\"",
					indexLabel: "{label} {y}",
					dataPoints: this.dataPoints
				}]
			});

		} catch (e) {
			console.log(e);
		}
	}, chartPanel);

	items.push(chartPanel);

	return items;
}