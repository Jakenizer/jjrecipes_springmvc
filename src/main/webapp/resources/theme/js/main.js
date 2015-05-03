/**
 * 
 */

function addItemToList(ul_id, link, text) {
	$(ul_id).append('<li><a href="' + link + '">' + text + '</a></li>');
}

function logout() {
	document.logoutForm.submit();
}

