var TestCaseMain = Spine.Controller.sub({
	init : function() {
		
		loadConfig();
		
		this.getListTestCase();

	},

	elements : {
		"#testcase_list" : "testcase_list",
		"#add_testcase_bt" : "add_testcase_bt",
		"#add_testcase_form" : "add_testcase_form",
		"#add_api_2_testcase_form" : "add_api_2_testcase_form"

	},

	events : {
		"click  .save_list_function" : "addAPI2TestCase",
		"click .add_ope_item" : "addOpeItem",
		"click #add_testcase_bt" : "addTestCase",
		"click .remove_ope_item" : "removeOpeItem",
		"click .run_testcase"         : "runTestcase"

	},

	runTestcase : function(e){
		if(!$(e.target).parents('.resource').find(".endpoints").is(":visible")){
			$(e.target).parents('.resource').find("#get_detail_bt").trigger("click");
		}
		setTimeout(function(){
			$(e.target).parents(".resource").find(".endpoint").each(function(){
				$(this).find("form input[name=commit]").trigger("click");
			});	
		}, 100);
		
	},

	removeOpeItem : function(e) {
		$(e.target).html("Add").css("color", "#0F6AB4").removeClass().addClass(
				"add_ope_item");

		var id = e.target.id.split("id_")[1].split('/').join('_');
		$("#new_added_api_" + id).remove();
	},

	addOpeItem : function(e) {

		$(e.target).html("Added").css("color", "red").removeClass().addClass(
				"remove_ope_item");
		var countApiConfigs = $('#add_api_2_testcase_form  .new_added_apis')
				.size();

		var idform = $(e.target).parents(".endpoint").find(".content form").attr("id");
		var formData = form2js(idform, '.', true);
		var json = JSON.stringify(formData, null, '\t');
		var obj = new Object();
		obj.index = countApiConfigs;
		
		obj.params =json;
		obj.apiId = e.target.id.split("id_")[1];
		obj.id = e.target.id.split("id_")[1].split('/').join('_');

		$("#apiConfigs_template").tmpl(obj).appendTo(
				"#add_api_2_testcase_form dl");

	},

	getListTestCase : function() {
		// var url = $("#input_baseUrl").val().trim();
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
			var testcaseId = $('#testcase_list #resources li.resource:first')
					.attr("id").split("_")[1];
			$("#add_api_2_testcase_form #testcase_id").val(testcaseId);
		});

		$('.thickbox').trigger('click');
		Main.getAPI();
	},

	addAPI2TestCase : function() {
		// $('#resources_list').find('.item_added').each(function(){
		// //reset btn
		// $(this).html("Add").removeClass().addClass("add_ope_item").css("color","#0F6AB4");
		// });

		//		
		var formData = form2js("add_api_2_testcase_form", '.', true);
		var json = JSON.stringify(formData, null, '\t');
		var testcaseId = $('#add_api_2_testcase_form').find("input[name=id]").val();
		postJson("/add_api_to_testcase", json, function(res) {
			$("#testcase_list #resources #" + testcaseId + "_endpoint_list")
					.append(res.responseText);
		});
		//		
		$('#resources_list').empty();
		$('#add_api_2_testcase_form dl').empty();
		$("#TB_closeWindowButton").trigger('click');

	},
	

	
});

var TestCase = Spine.Controller.sub({

	elements : {
		".endpoints" : "endpoints",
		"#add_api_2_testcase_form" : "add_api_2_testcase_form",
		"#add_api_2_testcase_form #resources_list" : "resources_list"
	},

	events : {
		"click #delete_testcase_bt" : "deleteTestCase",
		"click #get_detail_bt" : "getDetails",
		"click .add_ops" : "openPopup",
		"click .remove_api_item" : "removeAPIfromTestCase"

	},

	removeAPIfromTestCase : function(e) {
		var target = e.target;
		var testcaseId = this.id;
		var apiId = $(target).parents(".heading").find("input[name=apiConfigId]").val();
		var json = {"id" : testcaseId, "apiConfigIds" : [apiId]};
		postJson("/remove_api_from_testcase", JSON.stringify(json), function(res) {
			$(target).parents(".endpoint").remove();
		});
		
		
	},
	openPopup : function() {
		this.resources_list.empty();
		$('.thickbox').trigger('click');

		$('#add_api_2_testcase_form #testcase_id').val(this.id);
		Main.getAPI();
		// $("#add_api_2_testcase_form").append("<dd><input type='text'
		// name='id' value='"+ this.id+ "'/></dd>");
	},
	deleteTestCase : function() {
		var controller = this;

		$.post("/delete_test_case/" + this.id, "", function(res) {
			controller.el.remove();
		});
	},

	getDetails : function() {
		if ($("#testcase_" + this.id + " .endpoints .endpoint").size() == 0) {
			var controller = this;
			$.get("/api_in_testcase/" + this.id, null, function(res) {
				controller.endpoints.empty();
				controller.endpoints.append(res);
				controller.toggleEndpoints();

			});
		} else {
			this.toggleEndpoints();
		}
	},

	toggleEndpoints : function() {
		if (this.endpoints.is(":visible")) {
			this.endpoints.hide();
		} else {
			this.endpoints.show();
		}
	}

});
