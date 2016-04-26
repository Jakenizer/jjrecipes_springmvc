/**
 * 
 */

function addItemToList(ul_id, link, text) {
	$(ul_id).append('<li><a href="' + link + '">' + text + '</a></li>');
}

function logout() {
	document.logoutForm.submit();
}

function removeTag(id) {
	$("#tagId").val(id);
	$("#removeTag").submit();
}

function searchByTags() {
	var selectedTags = $('.active.list-group-item');
	var vals = [];
	$.each(selectedTags, function(i, tag) {
		vals.push(tag.innerText);
	});
	
	document.tagForm.selectedValues.value = vals;
	document.tagForm.submit();
}