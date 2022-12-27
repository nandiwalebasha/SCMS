var followUpfieldWidth = 200;
function showFollowupWindow(grid) {

	var addButton = new Ext.Button({
		text: 'Add',
		handler: function() {
			if(basicParamsForm.getForm().isValid() && form.getForm().isValid()){
				var basicValues = basicParamsForm.getForm().getValues();
				var otherValues = form.getForm().getValues();
				Ext.apply(basicValues,otherValues);

				console.log(basicValues);
				var record = new this.store.reader.recordType(basicValues);
				if(this.store.getAt(0) && this.store.getAt(0).phantom){
					this.store.removeAt(0);
				}
				this.store.insert(0, record);
				window.close();
			}
		}.bind(grid)
	});

	var cancelButton = new Ext.Button({
		text: 'Cancel',
		handler: function() {
			window.close();
		}
	});

	var basicParams1Column = getBasicParam1Items();
	var basicParams2Column = getBasicParam2Items();

	var basicParamsForm = new Ext.form.FormPanel({
		padding: 10,
		labelWidth: labelWidth,
		layout: 'column',
		autoHeight: true,
		items: [
			{
				layout: 'form',
				border: false,
				columnWidth: 0.5,
				items: basicParams1Column
			},
			{
				layout: 'form',
				border: false,
				columnWidth: 0.5,
				items: basicParams2Column
			}
		]
	});

	var column1Items = getFuColumn1Items();
	var column2Items = getFuColumn2Items();

	var form = new Ext.form.FormPanel({
		padding: 10,
		labelWidth: labelWidth,
		layout: 'column',
		autoHeight: true,
		items: [
			{
				layout: 'form',
				border: false,
				columnWidth: 0.5,
				items: column1Items
			},
			{
				layout: 'form',
				border: false,
				columnWidth: 0.5,
				items: column2Items
			}
		]
	});


	var window = new Ext.Window({
		title: 'Add followup',
		closable: true,
		modal: true,
		width: 900,
		height: 530,
		padding: 10,
		items: [{
			xtype: 'fieldset',
			title: 'Basic Parameters', // title, header, or checkboxToggle creates fieldset header
			autoHeight: true,
			items: [basicParamsForm]
		}, {
			xtype: 'fieldset',
			title: 'Other Parameters', // title, header, or checkboxToggle creates fieldset header
			autoHeight: true,
			items: [form]
		}],
		buttons: [addButton,cancelButton]
	});

	var recordToLoad = grid.getStore().getAt(0);
	if(recordToLoad){
		if(recordToLoad.phantom){
			basicParamsForm.getForm().loadRecord(recordToLoad);
		}
		form.getForm().loadRecord(recordToLoad);
	}



	window.show();
}


function getFuColumn1Items() {
	var columnItems = [];

	columnItems.push({
		xtype: 'numberfield',
		name: 'weight',
		width: followUpfieldWidth,
		fieldLabel: 'Weight'
	});

	columnItems.push({
		xtype: 'textfield',
		name: 'mind',
		width: followUpfieldWidth,
		fieldLabel: 'Mind'
	});

	columnItems.push({
		xtype: 'textfield',
		name: 'head',
		width: followUpfieldWidth,
		fieldLabel: 'head'
	});

	columnItems.push({
		xtype: 'textfield',
		name: 'hairs',
		width: followUpfieldWidth,
		fieldLabel: 'Hairs'
	});

	columnItems.push({
		xtype: 'textfield',
		name: 'sleep',
		width: followUpfieldWidth,
		fieldLabel: 'Sleep'
	});

	columnItems.push({
		xtype: 'textfield',
		name: 'dreams',
		width: followUpfieldWidth,
		fieldLabel: 'Dreams'
	});

	columnItems.push({
		xtype: 'textfield',
		name: 'eyes',
		width: followUpfieldWidth,
		fieldLabel: 'Eyes'
	});

	columnItems.push({
		xtype: 'textfield',
		name: 'ears',
		width: followUpfieldWidth,
		fieldLabel: 'Ears'
	});

	columnItems.push({
		xtype: 'textfield',
		name: 'nose',
		width: followUpfieldWidth,
		fieldLabel: 'Nose'
	});

	columnItems.push({
		xtype: 'textfield',
		name: 'mouth',
		width: followUpfieldWidth,
		fieldLabel: 'Mouth'
	});



	return columnItems;
}


function getFuColumn2Items() {
	var columnItems = [];

	columnItems.push({
		xtype: 'textfield',
		name: 'back',
		width: followUpfieldWidth,
		fieldLabel: 'Back'
	});

	columnItems.push({
		xtype: 'textfield',
		name: 'urine',
		width: followUpfieldWidth,
		fieldLabel: 'Urine'
	});

	columnItems.push({
		xtype: 'textfield',
		name: 'stool',
		width: followUpfieldWidth,
		fieldLabel: 'Stool'
	});

	columnItems.push({
		xtype: 'textfield',
		name: 'extremities',
		width: followUpfieldWidth,
		fieldLabel: 'Extremities'
	});

	columnItems.push({
		xtype: 'textfield',
		name: 'skin',
		width: followUpfieldWidth,
		fieldLabel: 'Skin'
	});

	columnItems.push({
		xtype: 'textfield',
		name: 'thermal',
		width: followUpfieldWidth,
		fieldLabel: 'Thermal'
	});

	columnItems.push({
		xtype: 'textfield',
		name: 'throat',
		width: followUpfieldWidth,
		fieldLabel: 'Throat'
	});

	columnItems.push({
		xtype: 'textfield',
		name: 'chest',
		width: followUpfieldWidth,
		fieldLabel: 'Chest'
	});

	columnItems.push({
		xtype: 'textfield',
		name: 'stomach',
		width: followUpfieldWidth,
		fieldLabel: 'Stomach'
	});


	columnItems.push({
		xtype: 'textfield',
		name: 'bloodReport',
		width: followUpfieldWidth,
		fieldLabel: 'Blood Report'
	});


	return columnItems;
}


function getBasicParam1Items() {
	var columnItems = [];

	columnItems.push({
		xtype: 'datefield',
		name: 'followUpDate',
		format:"Y-m-d",
		allowBlank:false,
		width: followUpfieldWidth,
		value: new Date(),
		fieldLabel: '* Followup Date'
	});

	columnItems.push({
		xtype: 'textfield',
		name: 'diagnosis',
		allowBlank:false,
		width: followUpfieldWidth,
		fieldLabel: '* Diagnosis'
	});

	return columnItems;
}

function getBasicParam2Items() {
	var columnItems = [];
	columnItems.push({
		xtype: 'textfield',
		name: 'complaint',
		allowBlank:false,
		width: followUpfieldWidth,
		fieldLabel: '* Complaint'
	});

	columnItems.push({
		xtype: 'textfield',
		name: 'medicine',
		allowBlank:false,
		width: followUpfieldWidth,
		fieldLabel: '* Medicine'
	});
	return columnItems;
}