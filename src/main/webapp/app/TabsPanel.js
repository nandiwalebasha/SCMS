function getTabsPanel(){
	var tabs = new Ext.TabPanel({
		activeTab: 0,
		id:'mainTabPanel',
		region: 'center',
		tabWidth:200,
		items: [getDashboardPanel()]
	});
	return tabs;	
}

function addPanelToTabsPanel(panel){
	var tabs = Ext.getCmp('mainTabPanel');
	//if(tabs.find('itemId',panel.itemId).length == 0){
		tabs.add(panel);	
	//}
	//tabs.setActiveTab(panel.getItemId());
	tabs.setActiveTab(panel.getItemId());
	
}
