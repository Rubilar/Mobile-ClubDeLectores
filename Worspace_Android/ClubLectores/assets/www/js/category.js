		$.getJSON("http://club.mersap.com/mobile/ClubDeLectores/Json/json.php",
			function(data){
				$.each(data.category, function(i,category){
				var subCategory = category.sub.m;
				var subCategoryArray = subCategory.split('-');
				var div ='<div data-role="collapsible" data-theme="a"><h2>'+category.title+'</h2><ul data-role="listview" data-theme="d" data-divider-theme="d" class="jqm-list">';
				
					div = div+'<li><a href="index.html">'+subCategoryArray[0]+'</a></li>';
				
				div = div+'</ul></div>';
				$('#category').append(div);
				
				if ( i == 2 ) return false;
			  });
			})
			.error(function() { alert("error"); })
		$('#category').load();