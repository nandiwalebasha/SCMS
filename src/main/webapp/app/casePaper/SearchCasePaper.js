function getSearchCasePaperPanel() {

	var searchButton = new Ext.Button({
		text: 'Search',
		handler: function() {
			var casePaperValue = casePaperNumber.getValue();
			var firstNameValue = firstName.getValue();
			var lastNameValue = lastName.getValue();

			var lastOptions = myStore.lastOptions;
			Ext.apply(lastOptions.params, {
				casePaperNumber: casePaperValue,
				firstName: firstNameValue,
				lastName: lastNameValue
			});
			myStore.reload(lastOptions);
		}
	});

	var myReader = new Ext.data.JsonReader({
		idProperty: 'sysCasePaperId',
		root: 'data',
		totalProperty: 'count',
		fields: [
			{ name: 'sysCasePaperId', mapping: 'sysCasePaperId' },
			{ name: 'casePaperNumber', mapping: 'casePaperNumber' },
			{ name: 'firstName', mapping: 'firstName' },
			{ name: 'lastName', mapping: 'lastName' },
			{ name: 'gender', mapping: 'gender' },
			{ name: 'occupation', mapping: 'occupation' },
			{ name: 'age', mapping: 'age' },
			{ name: 'contactNo', mapping: 'contactNo' },
			{ name: 'address', mapping: 'address' },
			{ name: 'createdOn', mapping: 'createdOn' },
			{ name: 'updatedOn', mapping: 'updatedOn' }
		]
	});

	var myStore = new Ext.data.Store({
		url: '/casepaper/getallcasepapers',
		autoLoad: {params:{start: 0, limit: 25}},
		listeners :{
			"beforeload" : function(store,options){
				if(options.params.searchParams){
					delete options.params["searchParams"];
				}
				options.params.searchParams = JSON.stringify(options.params); 
			}
		},
		reader: myReader
	});
	
	

	var columns = [];

	columns.push({
		header: 'Case Paper Number', dataIndex: 'casePaperNumber',
		renderer: function(value, metaData, record) {
			if (value) {
				value = '<a href="javascript:openCasePaper({sysCasePaperId:' + record.get('sysCasePaperId') +',casePaperNumber:\'' + record.get('casePaperNumber') +'\'});">' + value + '</a>';
			}
			return value;
		}
	});
	columns.push({ header: 'First Name', dataIndex: 'firstName' });
	columns.push({ header: 'Last Name', dataIndex: 'lastName' });
	columns.push({ header: 'Gender', dataIndex: 'gender' });
	columns.push({ header: 'Occupation', dataIndex: 'occupation' });
	columns.push({ header: 'Age', dataIndex: 'age' });
	columns.push({ header: 'Contact No', dataIndex: 'contactNo' });
	columns.push({ header: 'Address', dataIndex: 'address' });
	columns.push({ header: 'Created On', dataIndex: 'createdOn',xtype:'datecolumn',format:'Y-m-d H:i' });
	columns.push({ header: 'Updated On', dataIndex: 'updatedOn',xtype:'datecolumn',format:'Y-m-d H:i' });

	var casePaperNumber = new Ext.form.TextField({
		name: 'casePaperNumber'
	});

	var firstName = new Ext.form.TextField({
		name: 'firstName'
	});

	var lastName = new Ext.form.TextField({
		name: 'lastName'
	});

	var grid = new Ext.grid.GridPanel({
		store: myStore,
		region: 'center',
		loadMask: true,
		//itemId: 'searchCasePapers',
		tbar: ['Case paper number', casePaperNumber, '-', 'First Name', firstName, '-', 'Last Name', lastName,  '-', searchButton],
		title: 'Search Case Paper',
		closable: true,
		colModel: new Ext.grid.ColumnModel({
			defaults: {
				width: 160,
				sortable: true
			},
			columns: columns
		}),
		viewConfig: {
			forceFit: true,
			getRowClass: function(record, index) {
				return 'row-class';
			}
		},
		sm: new Ext.grid.RowSelectionModel({ singleSelect: true }),
		border: false,
		buttons: ['.'],
		bbar: new Ext.PagingToolbar({
			store: myStore,       // grid and PagingToolbar using same store
			displayInfo: true,
			pageSize: 25,
			prependButtons: true,
			items: ['->']
		})
	});
	return grid;
}


function openCasePaper(jsonObject){
	addPanelToTabsPanel(getCreateCasePaperPanel(jsonObject));
}