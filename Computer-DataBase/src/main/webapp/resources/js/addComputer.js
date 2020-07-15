$("#introduced").change(function(){
	$("#discontinued").attr("min", this.value);
});

$("#discontinued").change(function(){
	$("#introduced").attr("max", this.value);
});

function checkDate(){
	var test = true;
	if($("#introduced").value != '' && $("#discontinued").value != ''){
		var introduced = new Date($("#introduced").value);
		var discontinued = new Date($("#discontinued").value);
		if(introduced > discontinued){
			test = false;
			alert("Discontinued date must be greater than introduced");
		}
	}
	if($("#computerName").val().trim() == ''){
		test = false;
		alert("Computer Name must not be empty");
	}
	return test;
}