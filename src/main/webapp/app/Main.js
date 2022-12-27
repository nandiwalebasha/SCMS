Ext.onReady(function() {

	var headerPanel = getHeaderPanel();	
	var tabs = getTabsPanel();
	var menus = getMenus();
	new Ext.Viewport({
		renderTo: 'app-location',
		layout: 'border',
		items: [
			{
				xtype: 'panel',
				items: [headerPanel],
				height: 95,
				border: false,
				region: 'north',
				bbar:menus
			},
			tabs
		]
	});
});