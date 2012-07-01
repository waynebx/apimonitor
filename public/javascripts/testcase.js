var TestCaseMain = Spine.Controller.sub({
	init : function() {
		this.getListTestCase();

	},

	elements : {
		"#testcase_list" : "testcase_list",
		"#add_testcase_bt" : "add_testcase_bt",
		"#add_testcase_form" : "add_testcase_form",
		"#add_api_2_testcase_form" : "add_api_2_testcase_form"
//		"#testcase_list #resources" : "testcase_list",

	},

	events : {
		"click  .save_list_function" : "addAPI2TestCase",
		"click .add_ope_item"	 : "addOpeItem",
		"click #add_testcase_bt" : "addTestCase",
		"click .remove_ope_item" : "removeOpeItem"

	},
	
	removeOpeItem : function(e){
		$(e.target).html("Add").css("color","#0F6AB4").removeClass().addClass("add_ope_item");
		
		var id = "#api_" + $(e.target).attr("id");
		$(id).remove();
	},
	
	addOpeItem : function(e){
		var apiId = "apiId";
		$(e.target).html("Added").css("color","red").removeClass().addClass("remove_ope_item").attr("id",apiId);
		var countApiConfigs = $('#add_api_2_testcase_form  .api_configs').size();
		
		var renderString = "<dd class='api_configs' id='api_" + apiId + "'>";
		renderString += "<input type='text' value='" + apiId + "'  name='apiConfigs[" + countApiConfigs + "].apiId'  />";
		$(e.target).parents('.operations').find(".content table tbody tr").each(function(){
			var paramName = $(this).find('.code').html();
			var paramValue = $(this).find('input').val();
			renderString += "<input type='text' name='apiConfigs[" +countApiConfigs + "].params." + paramName + "' value='"+ paramValue +"'  />";
			
		});
		$('#add_api_2_testcase_form').append(renderString + "</dd>");
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
		});
		var testcaseId = $('#testcase_list #resources li.resource:first').attr("id").split("_")[1];
		$("#add_api_2_testcase_form").append("<dd><input type='text' name='id' value='"+ testcaseId+ "'/></dd>");
		$('.thickbox').trigger('click');
	},
	
	addAPI2TestCase : function() {
//		$('#resources_list').find('.item_added').each(function(){
//			//reset btn
//			$(this).html("Add").removeClass().addClass("add_ope_item").css("color","#0F6AB4");
//		});
		
		
//		
		var formData = form2js("add_api_2_testcase_form", '.', true);
		var json = JSON.stringify(formData, null, '\t');
		alert(json);
//
//		postJson("/add_api_to_testcase", json, function(res) {
//			$("#testcase_list #resources").append(res.responseText);
//		});
//		
		
		$('#add_api_2_testcase_form').html("");
		$("#TB_closeWindowButton").trigger('click');
	}
	
	
});

var TestCase = Spine.Controller.sub({

	elements : {
		".endpoints" : "endpoints",
		"#add_api_2_testcase_form" : "add_api_2_testcase_form"
	},
	
	
	events : {
		"click #delete_testcase_bt" : "deleteTestCase",
		"click #get_detail_bt" : "getDetails",
		"click .add_ops"         : "openPopup",

	},

	
	openPopup : function(){
		$('.thickbox').trigger('click');
		$("#add_api_2_testcase_form").append("<dd><input type='text' name='id' value='"+ this.id+ "'/></dd>");
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
			controller.endpoints.append(res);
		});
	}

});