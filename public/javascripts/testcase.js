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
		var formData = form2js("add_testcase_form", '.', true);
		var json = JSON.stringify(formData, null, '\t');

		
		var controller = this;

		postJson("/add_test_case", json, function(res) {
			$("#testcase_list #resources").prepend(res.responseText);
		}

	},
	
	addAPI2Testcase : function() {
		var formData = form2js("add_testcase_form", '.', true);
		var json = JSON.stringify(formData, null, '\t');

//		postJson("/add_api_to_testcase", json, function(res) {
//			$("#testcase_list #resources").append(res.responseText);
//		});
		
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