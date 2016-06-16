var nl2br = function (text) {
	return text.split('\n').join("<br/>");
};

var br2nl = function (text) {
	return text.split('<br/>').join("\n");
};


var passiveMeBox = function(activeBox, arr) {
	var who = arr[0];
	var what = arr[1];
	activeBox.parent().before(
		'<div class="passiveMeBox"><p>'
		+ nl2br(who) + " : " + nl2br(what)
		+ '</p></div>'
	);
};

window.onload = function(){

	setInterval(function(){
		jQuery.ajax({
	        type: "GET",
	        url: "/message",
	        async: "true",
	        success: function (data) {
				console.log(data);
	        	$('.passiveMeBox').remove();
	        	JSON.parse(data).forEach(function(message, i, arr){
	        		passiveMeBox($("#activeBox textarea"), /*br2nl(*/message/*)*/);
	        	})
	        }
	    });
	}, 500);

	$("#activeBox textarea").focus(function(){
		if ($(this).val() == "Enter message...") {
			$(this).val("");
		}
	});

	ctrl = false;
	myNode = $("#activeBox");

	$("#activeBox textarea").keydown(function(event) {

		if(event.keyCode == 17) {
			ctrl = true;
		}

		if(event.keyCode == 13) {
			if(ctrl) {
				$(this).val($(this).val() + "\n");
			}
			else {
				if ($(this).val() != ""
					&& !/^\s+$/.test($(this).val())
					&& $(this).val() != "Enter message...") {
					jQuery.ajax({
	                    type: "POST",
	                    data: "message=" + nl2br($(this).val()),
	                    url: "/message"
	                });
					passiveMeBox($(this), $(this).val());
					$(this).val("");
				}
			}
		}
	});

	$("#activeBox textarea").keyup(function(event){

		if(event.keyCode == 17) {
			ctrl = false;
		}

		if(!ctrl && event.keyCode == 13 && /^\s+$/.test($(this).val()))
			$(this).val("");
	});

};