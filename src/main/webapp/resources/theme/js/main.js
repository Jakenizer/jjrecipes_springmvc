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
    var token = $("meta[name='_csrf']").attr("content");
	var header = $("meta[name='_csrf_header']").attr("content");
	var headers = {};
	headers[header] = token;
	$.ajax({
         type : 'POST',
         url : '/JJRecipes/removeTag',
         data: {tagId : id},
         headers: headers
	})
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