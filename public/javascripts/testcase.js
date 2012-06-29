var TestCaseMain = Spine.Controller.sub({
	init : function() {
		this.getListTestCase();

	},

	elements : {
		"#testcase_list" : "testcase_list",
		"#add_testcase_bt" : "add_testcase_bt",
		"#add_testcase_form" : "add_testcase_form",
//		"#testcase_list #resources" : "testcase_list",

	},

	events : {
		"click .add_ope_item"	 : ""
		"click .add_ops"         : "openPopup",
		"click #add_testcase_bt" : "addTestCase",

	},
	
	
	openPopup : function(){
		$('.thickbox').trigger('click');
	},
	getListTestCase : function(){
//		var url = $("#input_baseUrl").val().trim();
		var url = "http://api.sgcharo.com/mobion";
		this.testcase_list.empty();
		this.testcase_list.load("/testcases", null, function() {	
			
		});
	},
	
	addTestCase : function() {
		var formData = form2js("add_testcase_form", '#', true, function(node) {
			if (node.id && node.id.match(/callbackTest/)) {
				return {
					name : node.id,
					value : node.innerHTML
				};
			}
		});

		var json = JSON.stringify(formData, null, '\t');

//		$('.thickbox').trigger('click');
		postJson("/add_test_case", json, function(res) {
			$("#testcase_list #resources").prepend(res.responseText);
		});
		
	},
});

var TestCase = Spine.Controller.sub({

	events : {
		"click #delete_testcase_bt" : "deleteTestCase",

	},

	deleteTestCase : function() {
		var controller = this;
		
		$.post("/delete_test_case/" + this.id, "",function(res){
			controller.el.remove();
		});
	},

});