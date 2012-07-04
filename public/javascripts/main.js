function postJson(url, data, success) {
	$.ajax({
		url : url,
		type : "POST",
		data : data,
		headers : {
			"Content-Type" : "application/json",
			"Accept" : "application/json, text/plain"
		},
		dataType : "json",
		complete : success
	})
};

function supportsLocalStorage() {
	try {
		return 'localStorage' in window && window['localStorage'] !== null;
	} catch (e) {
		return false;
	}
}

var Main = Spine.Controller.sub({
	init : function() {
		if (supportsLocalStorage()) {
			var url = localStorage.getItem("com.mobion.url", url);
			$("#input_baseUrl").val(url);
			Main.base_url = url;
			Main.getAPI();
		}
		
		$('#explore').click(function(){
			this.show_api();
		});
	},

	elements : {
		"#testcase_tab" : "testcase_tab",
		"#testcase_tab #testcase_list" : "testcase_list",
		"#api_tab" : "api_tab",
		"#api_tab #resources_list" : "resources_list",
	},

	events : {
		"click #api_bt" : "show_api",
		"click #testcase_bt" : "show_testcase",

	},

	show_api : function() {
		this.resources_list.empty();
		this.testcase_tab.empty();
		Main.getAPI();
	},

	show_testcase : function() {
		this.resources_list.empty();
		this.testcase_tab.empty();
		this.testcase_tab.load("/testcase");

	},

	// getTestcase : function() {
	// var url = $("#input_baseUrl").val().trim();
	//
	// var controller = this;
	// this.testcase_list.empty();
	// this.testcase_list.load("/testcases", null, function() {
	//
	// });
	//
	// },

	

});

Main.extend({

	form2Json : function(form) {

		var o = {};
		var a = form.serializeArray();
		$.each(a, function() {
			if (o[this.name] !== undefined) {
				if (!o[this.name].push) {
					o[this.name] = [ o[this.name] ];
				}
				o[this.name].push(this.value || '');
			} else {
				o[this.name] = this.value || '';
			}
		});
		return o;
	},

	getAPI : function() {
		var url = $("#input_baseUrl").val().trim();
		$("#content_message").html("Loading...");
		$("#resources_list").slideUp();
		$("#content_message").slideDown();
	
		var controller = this;
		$('#resources_list').load('/getapi?url=' + encodeURIComponent(url),
				null, function() {
					$("#content_message").slideUp();
					$("#resources_list").slideDown();
					if (supportsLocalStorage()) {
						localStorage.setItem("com.mobion.url", url);
					}
				});
	}
});

var Resource = Spine.Controller.sub({

	events : {
		"click h2" : "toggleEndpoint",
		"click .show_hide" : "toggleEndpoint",
		"click .expand_ops" : "expandOperations",
		"click .list_ops" : "collapseOperations"

	},

	toggleEndpoint : function() {
		Docs.toggleEndpointListForResource(this.id)
	},

	collapseOperations : function() {
		Docs.collapseOperationsForResource(this.id)
	},

	expandOperations : function() {
		Docs.expandOperationsForResource(this.id)
	}
});

var Operation = Spine.Controller.sub({
	tag : "li",
	target : "",
	init : function(){
		if(this.testcase_id == ""){
			this.target = "#" + this.id;
		}else{
			this.target = "#testcase_" + this.testcase_id + " #" + this.id;
		}
	},
	events : {
		"click h3" : "click",
		"click .sandbox_header input.submit" : "call_api"
	},

	click : function() {
		if(this.testcase_id == ""){
			Docs.toggleOperationContent(this.id + '_content');	
		}else{
			Docs.toggleOperationContent("testcase_" + this.testcase_id + " #" + this.id + '_content');
		}
		
	},

	call_api : function() {
		var form = $("#" + this.id + "_form");
		var error_free = true;
		var missing_input = null;
		var controller = this;
		// Cycle through the form's required inputs
		form.find("input.required").each(function() {

			// Remove any existing error styles from the input
			$(this).removeClass('error');

			// Tack the error style on if the input is empty..
			if ($(this).val() == '') {
				if (missing_input == null)
					missing_input = $(this);
				$(this).addClass('error');
//				$(this).wiggle();
				 $(controller.target + " .options .run_status").html("Require").css("color", "red");
				error_free = false;
			}

		});

		if (error_free) {
			var invocationUrl = this.invocationUrl(form.serializeArray());
			$(".request_url", this.target + "_content_sandbox_response")
					.html("<pre>" + invocationUrl + "</pre>");

			if (this.http_method == "get") {
				$.getJSON(invocationUrl, this.proxy(this.showResponse))
						.complete(this.proxy(this.showCompleteStatus)).error(
								this.proxy(this.showErrorStatus));
			} else {
				var postParams = this.operation.invocationPostParam(form
						.serializeArray());
				$.post(invocationUrl, $.parseJSON(postParams),
						this.showResponse).complete(this.showCompleteStatus)
						.error(this.showErrorStatus);
			}

		}

	},

	showResponse : function(response, elementScope) {
		// log(response);
		var prettyJson = JSON.stringify(response, null, "\t").replace(/\n/g,
				"<br>");
		// log(prettyJson);
		$(".response_body",this.target + "_content_sandbox_response").html(
				prettyJson);

		$("#testcase_" + this.target + "_content_sandbox_response").slideDown();
	},

	showErrorStatus : function(data, elementScope) {
		// log("error " + data.status);
		this.showStatus(data, elementScope);
		$(this.target + "_content_sandbox_response").slideDown();
	},

	showCompleteStatus : function(data, elementScope) {
		// log("complete " + data.status);
		this.showStatus(data, elementScope);
	},

	showStatus : function(data, elementScope) {
		// log(data);
		// log(data.getAllResponseHeaders());
		var jsonData = JSON.parse(data.responseText);
		var response_body = "<pre>"
				+ JSON.stringify(jsonData, null, 2).replace(/\n/g, "<br>")
				+ "</pre>";
		if (jsonData.status == "success") {
			 $(this.target + " .options .run_status").html("Success").css("color", "blue");
			$(".response_code", this.target + "_content_sandbox_response")
					.html("<pre>" + "OK" + "</pre>");
		} else {
			$(this.target + " .options .run_status").html("Fail").css("color","red");
			$(".response_code", this.target + "_content_sandbox_response")
					.html("<pre>" + jsonData.error_code + "</pre>");
		}
		$(".response_body", this.target + "_content_sandbox_response").html(
				response_body);
		$(".response_headers", this.target + "_content_sandbox_response")
				.html("<pre>" + data.getAllResponseHeaders() + "</pre>");
		$(this.target + "_content_sandbox_response").slideDown();
	},

	invocationUrl : function(formValues) {
		var formValuesMap = new Object();
		for ( var i = 0; i < formValues.length; i++) {
			var formValue = formValues[i];
			if (formValue.value && jQuery.trim(formValue.value).length > 0)
				formValuesMap[formValue.name] = formValue.value;
		}

		var urlTemplateText = this.path.split("{").join("${");
		// log("url template = " + urlTemplateText);
		var urlTemplate = $.template(null, urlTemplateText);
		var url = $.tmpl(urlTemplate, formValuesMap)[0].data;
		// log("url with path params = " + url);

		var queryParams = "";
		var apiKey = $("#input_apiKey").val();
		if (apiKey) {
			apiKey = jQuery.trim(apiKey);
			if (apiKey.length > 0)
				queryParams = "?api_key=" + apiKey;
		}

		// var names = Object.keys(formValuesMap);
		for ( var name in formValuesMap) {
			var value = formValuesMap[name];
			if (value.length > 0) {
				queryParams += queryParams.length > 0 ? "&" : "?";
				queryParams += name;
				queryParams += "=";
				queryParams += value;
			}
		}
		;

		url = Main.base_url + url + queryParams;
		// log("final url with query params and base url = " + url);

		return url;
	},

	invocationPostParam : function(formValues) {
		var formValuesMap = new Object();
		for ( var i = 0; i < formValues.length; i++) {
			var formValue = formValues[i];
			if (formValue.value && jQuery.trim(formValue.value).length > 0)
				formValuesMap[formValue.name] = formValue.value;
		}

		var postParam = "";
		this.parameters.each(function(param) {
			var paramValue = jQuery.trim(formValuesMap[param.name]);
			if (param.paramType == "body" && paramValue.length > 0) {
				postParam = postParam.length > 0 ? postParam : "{";
				postParam += "\"" + param.name + "\"";
				postParam += ":";
				postParam += "\"" + formValuesMap[param.name] + "\",";
			}
		});

		if (postParam.length > 0) {
			postParam = postParam.substring(0, postParam.length - 1) + "}";
		}

		return postParam;
	}

});

// GUi Effect utils
var Docs = {

	shebang : function() {

		// If shebang has an operation nickname in it..
		// e.g. /docs/#!/words/get_search
		var fragments = $.param.fragment().split('/');
		fragments.shift(); // get rid of the bang

		switch (fragments.length) {
		case 1:
			// Expand all operations for the resource and scroll to it
			log('shebang resource:' + fragments[0]);
			var dom_id = 'resource_' + fragments[0];

			Docs.expandEndpointListForResource(fragments[0]);
			$("#" + dom_id).slideto({
				highlight : false
			});
			break;
		case 2:
			// Refer to the endpoint DOM element, e.g. #words_get_search
			log('shebang endpoint: ' + fragments.join('_'));

			// Expand Resource
			Docs.expandEndpointListForResource(fragments[0]);
			$("#" + dom_id).slideto({
				highlight : false
			});

			// Expand operation
			var li_dom_id = fragments.join('_');
			var li_content_dom_id = li_dom_id + "_content";

			log("li_dom_id " + li_dom_id);
			log("li_content_dom_id " + li_content_dom_id);

			Docs.expandOperation($('#' + li_content_dom_id));
			$('#' + li_dom_id).slideto({
				highlight : false
			});
			break;
		}

	},

	toggleEndpointListForResource : function(resource) {
		var elem = $('li#resource_' + resource + ' ul.endpoints');
		if (elem.is(':visible')) {
			Docs.collapseEndpointListForResource(resource);
		} else {
			Docs.expandEndpointListForResource(resource);
		}
	},

	// Expand resource
	expandEndpointListForResource : function(resource) {
		$('#resource_' + resource).addClass('active');

		var elem = $('li#resource_' + resource + ' ul.endpoints');
		elem.slideDown();
	},

	// Collapse resource and mark as explicitly closed
	collapseEndpointListForResource : function(resource) {
		$('#resource_' + resource).removeClass('active');

		var elem = $('li#resource_' + resource + ' ul.endpoints');
		elem.slideUp();
	},

	expandOperationsForResource : function(resource) {
		// Make sure the resource container is open..
		Docs.expandEndpointListForResource(resource);
		$('li#resource_' + resource + ' li.operation div.content').each(
				function() {
					Docs.expandOperation($(this));
				});
	},

	collapseOperationsForResource : function(resource) {
		// Make sure the resource container is open..
		Docs.expandEndpointListForResource(resource);
		$('li#resource_' + resource + ' li.operation div.content').each(
				function() {
					Docs.collapseOperation($(this));
				});
	},

	expandOperation : function(elem) {
		elem.slideDown();
	},

	collapseOperation : function(elem) {
		elem.slideUp();
	},

	toggleOperationContent : function(dom_id) {
		var elem = $('#' + dom_id);
		(elem.is(':visible')) ? Docs.collapseOperation(elem) : Docs
				.expandOperation(elem);
	}

};
