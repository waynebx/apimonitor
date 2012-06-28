var TestCaseMain = Spine.Controller.sub({
	init : function() {
		
	},

	elements : {
		"#add_testcase_bt" : "add_testcase_bt",
		"#add_testcase_form" : "add_testcase_form",
		"#testcase_list #resources" : "testcase_list",
		
	},

	events : {
		"click #add_testcase_bt" : "addTestCase",
		
	},

	addTestCase : function() {
		var formData = form2js("add_testcase_form", '#', true,
				function(node)
				{
					if (node.id && node.id.match(/callbackTest/))
					{
						return { name: node.id, value: node.innerHTML };
					}
				});
		
		var json = JSON.stringify(formData, null, '\t');
		
		var controller = this;
		
		$.ajax({
			  url:"/add_test_case",
			  type:"POST",
			  data: json,
			  headers: { "Content-Type": "application/json", "Accept": "application/json, text/plain" },
			  dataType:"json",
			  complete: function(res) {
				  $('#testcase_list #resources').append(res.responseText)
			  }
			});
			
// postJson("/add_test_case", json, function(res){
// controller.testcase_list.append(res);
// });
	
	},
});