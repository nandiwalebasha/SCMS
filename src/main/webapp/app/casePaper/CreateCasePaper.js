var fieldWidth = 200;
var labelWidth = 150;
function getCreateCasePaperPanel(casePaperDetails) {
	var sysCasePaperId;
	var panelTitle = 'Create new case paper';
	if (casePaperDetails) {
		if (casePaperDetails.casePaperNumber) {
			panelTitle = casePaperDetails.casePaperNumber;
			sysCasePaperId = casePaperDetails.sysCasePaperId;
		}
	}

	var grid = getGridPanel();

	var saveButton = new Ext.Button({
		text: 'Save',
		handler: function() {
			if (form.getForm().isValid()) {
				var formValues = form.getForm().getValues();
				var gridRecord = this.store.getAt(0);
				if (gridRecord) {
					formValues.followUps = [gridRecord.data];
				}
				if (panel.sysCasePaperId) {
					formValues.sysCasePaperId = panel.sysCasePaperId;
				}
				console.log(formValues);

				Ext.Ajax.request({
					url: '/casepaper/savecasepaper',
					method:'POST',
					headers: {
						'Content-Type': 'application/json'
					},
					params: JSON.stringify(formValues),
					success: function(response, request) {
						Ext.Msg.show({
							title: 'Success',
							minWidth: 400,
							msg: 'Case paper saved successfully',
							buttons: Ext.MessageBox.OK,
							icon: Ext.MessageBox.INFO
						});

						var responseJson = JSON.parse(response.responseText);
						panel.sysCasePaperId = responseJson.sysCasePaperId;

						if(responseJson.casePaperNumber){
							panel.setTitle(responseJson.casePaperNumber);
						}

						if (form.getForm().items) {
							form.getForm().items.map.casePaperNumber.disable();
						}

						if(grid.store.getAt(0)){
							grid.store.getAt(0).phantom = false;
						}
						grid.getView().refresh();
						printButton.show();

					},
					failure: function() {
						Ext.Msg.show({
							title: 'Failure',
							minWidth: 400,
							msg: 'Failed to save Case paper',
							buttons: Ext.MessageBox.OK,
							icon: Ext.MessageBox.ERROR
						});
					}
				});
			}
		}.bind(grid)
	});


	var printButton = new Ext.Button({
		text: 'Print',
		hidden: !sysCasePaperId,
		handler: function() {
			Ext.Ajax.request({
				url: '/casepaper/downloadcasepaperbyid/'+panel.sysCasePaperId,
				method:'POST',
				headers: {
					'Content-Type': 'application/json'
				},
				success: function(response, request) {
					Ext.Msg.show({
						title: 'Success',
						minWidth: 400,
						msg: 'Downloaded file..!! Will open in few seconds..',
						buttons: Ext.MessageBox.OK,
						icon: Ext.MessageBox.INFO
					});
				},
				failure: function() {
					Ext.Msg.show({
						title: 'Failure',
						minWidth: 400,
						msg: 'Failed to download Case paper',
						buttons: Ext.MessageBox.OK,
						icon: Ext.MessageBox.ERROR
					});
				}
			});
		}
	});


	var column1Items = getCpColumn1Items();
	var column2Items = getCpColumn2Items();
	var column3Items = getCpColumn3Items();

	var form = new Ext.form.FormPanel({
		padding: 20,
		labelWidth: labelWidth,
		itemId:'casePaperForm',
		region: 'north',
		layout: 'column',
		height: 130,
		items: [
			{
				layout: 'form',
				border: false,
				columnWidth: 0.3,
				items: column1Items
			},
			{
				layout: 'form',
				border: false,
				columnWidth: 0.3,
				items: column2Items
			},
			{
				layout: 'form',
				border: false,
				columnWidth: 0.4,
				items: column3Items
			}
		]
	});

	var panel = new Ext.Panel({
		title: panelTitle,
		closable: true,
		sysCasePaperId: sysCasePaperId,
		layout: 'border',
		items: [form, grid],
		buttons: [printButton,saveButton]
	});

	panel.on('render', function() {
		var scope = this;
		if (scope.sysCasePaperId) {
			Ext.Ajax.request({
				url: '/casepaper/getcasepaperbyid/'+scope.sysCasePaperId,
				headers: {
					'Content-Type': 'application/json'
				},
				method:'POST',
				success: function(response, request) {

					var responseJson = JSON.parse(response.responseText);
					panel.items.map.casePaperForm.getForm().setValues(responseJson);

					if (panel.items.map.casePaperForm) {
						panel.items.map.casePaperForm.getForm().items.map.casePaperNumber.disable();
					}
					panel.items.map.casePaperGrid.getStore().loadData({rows:responseJson.followUps});

				},
				failure: function() {
					Ext.Msg.show({
						title: 'Failure',
						minWidth: 400,
						msg: 'Failed to load the Case paper',
						buttons: Ext.MessageBox.OK,
						icon: Ext.MessageBox.ERROR
					});
				}
			});
		}
	}, panel);

	return panel;
}


function getCpColumn1Items() {
	var columnItems = [];

	columnItems.push({
		xtype: 'textfield',
		name: 'casePaperNumber',
		itemId: 'casePaperNumber',
		allowBlank: false,
		width: fieldWidth,
		fieldLabel: '* Case paper number'
	});

	columnItems.push({
		xtype: 'textfield',
		name: 'firstName',
		itemId: 'firstName',
		allowBlank: false,
		width: fieldWidth,
		fieldLabel: '* First Name'
	});

	columnItems.push({
		xtype: 'textfield',
		name: 'lastName',
		itemId: 'lastName',
		width: fieldWidth,
		allowBlank: false,
		fieldLabel: '* Last Name'
	});


	return columnItems;
}


function getCpColumn2Items() {
	var columnItems = [];

	columnItems.push({
		xtype: 'combo',
		typeAhead: true,
		triggerAction: 'all',
		lazyRender: true,
		mode: 'local',
		store: new Ext.data.ArrayStore({
			fields: [
				'myId',
				'displayText'
			],
			data: [['Male', 'Male'], ['Female', 'Female'], ['Other', 'Other']]
		}),
		valueField: 'myId',
		displayField: 'displayText',
		name: 'gender',
		itemId: 'gender',
		allowBlank: false,
		width: fieldWidth,
		fieldLabel: '* Gender'
	});

	columnItems.push({
		xtype: 'textfield',
		name: 'occupation',
		itemId: 'occupation',
		width: fieldWidth,
		fieldLabel: 'Occupation'
	});

	columnItems.push({
		xtype: 'numberfield',
		name: 'age',
		itemId: 'age',
		allowBlank: false,
		width: fieldWidth,
		fieldLabel: '* Age'
	});

	return columnItems;
}

function getCpColumn3Items() {
	var columnItems = [];

	columnItems.push({
		xtype: 'numberfield',
		name: 'contactNo',
		itemId: 'contactNo',
		maxLength: 10,
		minLength: 10,
		maxLengthText: 'Contact number should be 10 digits',
		minLengthText: 'Contact number should be 10 digits',
		width: fieldWidth,
		fieldLabel: 'Contact No'
	});

	columnItems.push({
		xtype: 'textarea',
		name: 'address',
		itemId: 'address',
		width: fieldWidth,
		fieldLabel: 'Address'
	});

	return columnItems;
}

function getGridPanel() {
	var followUpButton = new Ext.Button({
		text: 'Add followup',
		handler: function() {
			showFollowupWindow(grid);
		}
	});

	var myReader = new Ext.data.JsonReader({
		idProperty: 'followUpID',
		root: 'rows',
		fields: [
			{ name: 'followUpID', mapping: 'followUpID' },
			{ name: 'followUpDate', mapping: 'followUpDate' },
			{ name: 'complaint', mapping: 'complaint' },
			{ name: 'diagnosis', mapping: 'diagnosis' },
			{ name: 'medicine', mapping: 'medicine' },
			{ name: 'weight', mapping: 'weight' },
			{ name: 'mind', mapping: 'mind' },
			{ name: 'head', mapping: 'head' },
			{ name: 'hairs', mapping: 'hairs' },
			{ name: 'sleep', mapping: 'sleep' },
			{ name: 'dreams', mapping: 'dreams' },
			{ name: 'eyes', mapping: 'eyes' },
			{ name: 'ears', mapping: 'ears' },
			{ name: 'nose', mapping: 'nose' },
			{ name: 'mouth', mapping: 'mouth' },
			{ name: 'throat', mapping: 'throat' },
			{ name: 'chest', mapping: 'chest' },
			{ name: 'stomach', mapping: 'stomach' },
			{ name: 'back', mapping: 'back' },
			{ name: 'urine', mapping: 'urine' },
			{ name: 'stool', mapping: 'stool' },
			{ name: 'extremities', mapping: 'extremities' },
			{ name: 'skin', mapping: 'skin' },
			{ name: 'bloodReport', mapping: 'bloodReport' },
			{ name: 'thermal', mapping: 'thermal' }
		]
	});

	var myStore = new Ext.data.Store({
		reader: myReader
	});

	var columns = [];

	columns.push({ header: 'Followup Date', dataIndex: 'followUpDate' });
	columns.push({ header: 'Complaint', dataIndex: 'complaint' });
	columns.push({ header: 'Diagnosis', dataIndex: 'diagnosis' });
	columns.push({ header: 'Medicine', dataIndex: 'medicine' });
	columns.push({ header: 'Weight', dataIndex: 'weight' });
	columns.push({ header: 'Mind', dataIndex: 'mind' });
	columns.push({ header: 'Head', dataIndex: 'head' });
	columns.push({ header: 'Hairs', dataIndex: 'hairs' });
	columns.push({ header: 'Sleep', dataIndex: 'sleep' });
	columns.push({ header: 'Dreams', dataIndex: 'dreams' });
	columns.push({ header: 'Eyes', dataIndex: 'eyes' });
	columns.push({ header: 'Ears', dataIndex: 'ears' });
	columns.push({ header: 'Nose', dataIndex: 'nose' });
	columns.push({ header: 'Mouth', dataIndex: 'mouth' });
	columns.push({ header: 'Throat', dataIndex: 'throat' });
	columns.push({ header: 'Chest', dataIndex: 'chest' });
	columns.push({ header: 'Stomach', dataIndex: 'stomach' });
	columns.push({ header: 'Back', dataIndex: 'back' });
	columns.push({ header: 'Urine', dataIndex: 'urine' });
	columns.push({ header: 'Stool', dataIndex: 'stool' });
	columns.push({ header: 'Extremities', dataIndex: 'extremities' });
	columns.push({ header: 'Skin', dataIndex: 'skin' });
	columns.push({ header: 'Thermal', dataIndex: 'thermal' });
	columns.push({ header: 'Blood Report', dataIndex: 'bloodReport' });

	var grid = new Ext.grid.GridPanel({
		store: myStore,
		region: 'center',
		itemId:'casePaperGrid',
		tbar: [followUpButton],
		colModel: new Ext.grid.ColumnModel({
			defaults: {
				width: 160,
				sortable: false
			},
			columns: columns
		}),
		sm: new Ext.grid.RowSelectionModel({ singleSelect: true }),
		border: false,
		viewConfig: {
			getRowClass: function(record, rowIndex, rp, ds) { // rp = rowParams
				if (record.phantom) {
					return 'new-row-background';
				}
				return 'row-class';
			}
		},
		title: 'Health details'
	});

	return grid;
}