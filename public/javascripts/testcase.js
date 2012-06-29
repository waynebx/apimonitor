var TestCaseMain = Spine.Controller.sub({
	init : function() {
		
	},

	elements : {
		"#add_testcase_bt" : "add_testcase_bt",
		"#add_testcase_form" : "add_testcase_form",
//		"#testcase_list #resources" : "testcase_list",

	},

	events : {
		"click #add_testcase_bt" : "addTestCase",

	},

	addTestCase : function() {
		var formData = form2js("add_testcase_form", '.', true);
		var json = JSON.stringify(formData, null, '\t');

		
		var controller = this;

		postJson("/add_test_case", json, function(res) {
			$("#testcase_list #resources").append(res.responseText);
		});

	},
	
	addAPI2Testcase : function() {
		var formData = form2js("add_testcase_form", '.', true);
		var json = JSON.stringify(formData, null, '\t');

		
		var controller = this;

		postJson("/add_api_to_testcase", json, function(res) {
			$("#testcase_list #resources").append(res.responseText);
		});

	},
	
	
});

var TestCase = Spine.Controller.sub({

	elements : {
		".endpoints" : "endpoints"
	},
	
	
	events : {
		"click #delete_testcase_bt" : "deleteTestCase",
		"click #get_detail_bt" : "getDetails",

	},

	deleteTestCase : function() {
		var controller = this;
		
		$.post("/delete_test_case/" + this.id, "",function(res){
			controller.el.remove();
		});
	},
	
	getDetails : function() {
		var controller = this;
		$.get("/api_in_testcase/" + this.id, null, function(res){
			controller.endpoints.append(res)
		});
	},

});