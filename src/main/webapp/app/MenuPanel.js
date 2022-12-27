function getMenus(){
	var casePaperButton = new Ext.Button({
		text: 'Create new case paper',
		handler: function() {
			addPanelToTabsPanel(getCreateCasePaperPanel());
		}
	});
	var searchCasePaperButton = new Ext.Button({
		text: 'Search case paper',
		handler: function() {
			addPanelToTabsPanel(getSearchCasePaperPanel());
		}
	});
	return ["-",casePaperButton,"-",searchCasePaperButton,"-"];	
}