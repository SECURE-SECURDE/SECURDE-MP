var req;

function redirectGroup(groupID) {
	try {
		if (window.XMLHttpRequest) {// code for IE7+, Firefox, Chrome, Opera, Safari
		 	req = new XMLHttpRequest();
		} else {// code for IE6, IE5
		  	req = new ActiveXObject("Microsoft.XMLHTTP");
		}
		req.onreadystatechange = handleRedirect;
		req.open("GET", "GroupPage.jsp?groupID="+groupID , true);
		req.send();
	} catch(e) {
		alert("Your browser is not AJAX enabled");
	}
}

function handleRedirect() {
	if(req.readyState === 4) {
		if(req.status === 200) {
			document.getElementById("content").innerHTML = req.responseText;
		}
	} 
}