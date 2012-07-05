var ApiVersionMain = Spine.Controller.sub({
	
	
	
	init : function(){
		this.loadListVersion();
	}, 
	
	
	
	loadListVersion : function(){
		$("#framecontent  .innercontent").load("/get_list_version");
	},
	
	
	
	
})



var ApiVersion = Spine.Controller.sub({
	
	events : {
		"click  a" : "getApiByVersion",
	},
	
	getApiByVersion : function(){
		alert("Fds");
	},
})