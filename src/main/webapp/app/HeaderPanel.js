function getHeaderPanel() {
	var logoutButton = new Ext.Button({
		icon: '../images/icon-logoff.png',
		border: false,
		tooltip: 'Logout',
		height: 40,
		width: 40,
		handler: function() {
			Ext.Ajax.request({
				url: '/perform_logout',
				success: function(){
					console.log("success");
					 document.location.reload()
				},
				failure: function(){
					console.log("failure");
					Ext.msg.alert("Failure","Failed to logout");
				}
			});
		}
	});

	var headerPanel = new Ext.Panel({
		layout: 'column',
		height: 70,
		border: false,
		items: [
			{
				xtype: 'panel',
				columnWidth: 0.07,
				height: 70,
				bodyCssClass:'header',
				border: false,
				html: '<img src="../images/Logo.jpg" style="max-width:100%;max-height:100%;"/>'
			},
			{
				xtype: 'panel',
				columnWidth: 0.78,
				height: 70,
				bodyCssClass:'header-title',
				html: 'Systematic Case Paper Management',
				border: false
			},
			{
				xtype: 'panel',
				columnWidth: 0.1,
				height: 70,
				bodyCssClass:'header',
				border: false
			},
			{
				xtype: 'panel',
				columnWidth: 0.05,
				height: 70,
				bodyCssClass:'header',
				border: false,
				buttonAlign: 'center',
				buttons: [logoutButton]
			}
		]
	});
	return headerPanel;
}