var Main = Spine.Controller.sub({
	init : function(){
		if (this.supportsLocalStorage()) {
			var url = localStorage.getItem("com.mobion.url", url);
			$("#input_baseUrl").val(url);
		}
	},
	events : {
		"click #explore" : "getAPI"
	},
	
	getAPI : function()
	{
		var url = $("#input_baseUrl").val().trim();
		$("#content_message").html("Loading...");
		$("#resources_list").slideUp();
		$("#content_message").slideDown();

		$('#resources_list').load('/getapi?url=' + encodeURIComponent(url), null,
				function() {
					$("#content_message").slideUp();
					$("#resources_list").slideDown();
					if (supportsLocalStorage) {
						localStorage.setItem("com.mobion.url", url);
					}
				}
		);
	},
	
	supportsLocalStorage :  function()
	{
		try {
			return 'localStorage' in window && window['localStorage'] !== null;
		} catch (e) {
			return false;
		}
	}
});

var Resource = Spine.Controller.sub({

	events : {
		"click h2" : "toggleEndpoint",
		"click .show_hide" : "toggleEndpoint",
		"click .expand_ops" : "expandOperations",
		"click .list_ops" : "collapseOperations"
	
	},

	toggleEndpoint: function(){
		Docs.toggleEndpointListForResource(this.id)
	},
	
	collapseOperations: function(){
		Docs.collapseOperationsForResource(this.id)
	},
	
	expandOperations: function(){
		Docs.expandOperationsForResource(this.id)
	}
});

var Operation = Spine.Controller.sub({
	tag : "li",
	
	events : {"click .heading" : "click"},

	click: function(){
		Docs.toggleOperationContent(this.id + '_content');
	},
	 
});


  
// GUi Effect utils
var Docs = {


		showResponse: function(response, elementScope) {
			// log(response);
			var prettyJson = JSON.stringify(response, null, "\t").replace(/\n/g, "<br>");
			// log(prettyJson);
			$(".response_body", elementScope + "_content_sandbox_response").html(prettyJson);

			$(elementScope + "_content_sandbox_response").slideDown();
		},

		showErrorStatus: function(data,elementScope) {
			// log("error " + data.status);
			this.showStatus(data,elementScope);
			$(elementScope + "_content_sandbox_response").slideDown();
		},

		showCompleteStatus: function(data,elementScope) {
			// log("complete " + data.status);
			this.showStatus(data,elementScope);
		},

		showStatus: function(data,elementScope) {
			// log(data);
			// log(data.getAllResponseHeaders());
			var jsonData = JSON.parse(data.responseText);
			var response_body = "<pre>" + JSON.stringify(jsonData, null, 2).replace(/\n/g, "<br>") + "</pre>";
			if(jsonData.status == "success"){
				$(".response_code", elementScope + "_content_sandbox_response").html("<pre>" + "OK" + "</pre>");
			}else{
				$(".response_code", elementScope + "_content_sandbox_response").html("<pre>" + jsonData.error_code + "</pre>");  
			}
			$(".response_body", elementScope + "_content_sandbox_response").html(response_body);
			$(".response_headers", elementScope + "_content_sandbox_response").html("<pre>" + data.getAllResponseHeaders() + "</pre>");
		},

		shebang: function() {

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
				$("#"+dom_id).slideto({highlight: false});
				break;
			case 2:
				// Refer to the endpoint DOM element, e.g. #words_get_search
				log('shebang endpoint: ' + fragments.join('_'));

				// Expand Resource
				Docs.expandEndpointListForResource(fragments[0]);
				$("#"+dom_id).slideto({highlight: false});

				// Expand operation
				var li_dom_id = fragments.join('_');
				var li_content_dom_id = li_dom_id + "_content";

				log("li_dom_id " + li_dom_id);
				log("li_content_dom_id " + li_content_dom_id);

				Docs.expandOperation($('#'+li_content_dom_id));
				$('#'+li_dom_id).slideto({highlight: false});
				break;
			}

		},

		toggleEndpointListForResource: function(resource) {
			var elem = $('li#resource_' + resource + ' ul.endpoints');
			if (elem.is(':visible')) {
				Docs.collapseEndpointListForResource(resource);
			} else {
				Docs.expandEndpointListForResource(resource);
			}
		},

		// Expand resource
		expandEndpointListForResource: function(resource) {
			$('#resource_' + resource).addClass('active');

			var elem = $('li#resource_' + resource + ' ul.endpoints');
			elem.slideDown();
		},

		// Collapse resource and mark as explicitly closed
		collapseEndpointListForResource: function(resource) {
			$('#resource_' + resource).removeClass('active');

			var elem = $('li#resource_' + resource + ' ul.endpoints');
			elem.slideUp();
		},

		expandOperationsForResource: function(resource) {
			// Make sure the resource container is open..
			Docs.expandEndpointListForResource(resource);
			$('li#resource_' + resource + ' li.operation div.content').each(function() {
				Docs.expandOperation($(this));
			});
		},

		collapseOperationsForResource: function(resource) {
			// Make sure the resource container is open..
			Docs.expandEndpointListForResource(resource);
			$('li#resource_' + resource + ' li.operation div.content').each(function() {
				Docs.collapseOperation($(this));
			});
		},

		expandOperation: function(elem) {
			elem.slideDown();
		},

		collapseOperation: function(elem) {
			elem.slideUp();
		},

		toggleOperationContent: function(dom_id) {
			var elem = $('#' + dom_id);
			(elem.is(':visible')) ? Docs.collapseOperation(elem) : Docs.expandOperation(elem);
		}

};
