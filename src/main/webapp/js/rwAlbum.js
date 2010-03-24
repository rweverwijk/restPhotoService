var photoes = new Array();
var currentPhoto;
$.getJSON("http://localhost:8080/restPhotoService/rest/photo/",
        function(data){
          $.each(data.Photo, function(i,item){
        	photoes.push(item);
            thisDiv = $("<div/>").attr("class", "image").appendTo("#images");
            thisImg = $("<img/>").attr("src", item.thumbnailUrl).attr("style", "opacity: 0.85").appendTo(thisDiv);
            thisImg.click(function() {showImage(i);});
          });
          $("img").mouseover(function() {$(this).animate({"opacity": "1"});});
		  $("img").mouseout(function() {$(this).animate({"opacity": "0.85"});});
	
				$(document).keyup(function(event) {
					documentKeyupEvent(event);
			    });

			function documentKeyupEvent(event) {
				// esc
				if (event.keyCode == 27) {
					$("div#overlay").fadeOut();
					$("div#outerImageContainer").attr("style", "display: none;");
				} 
				// left
				else if (event.keyCode == 37) {
					showImage(currentPhoto - 1);
				}
				// left
				else if (event.keyCode == 39) {
					showImage(currentPhoto + 1);
				}
			}
				
			function showImage(imageId) {
				currentPhoto = imageId;
				if($('#overlay').length == 0) {
					$("<div/>").attr("id", "overlay").attr("style", "height: " + getPageSize()[1] +"px").appendTo("body");
					$("<div/>").attr("id", "outerImageContainer").appendTo("body");
					$("<div/>").attr("id", "imageContainer").appendTo("#outerImageContainer");
					$("<div/>").attr("id", "imageTitle").appendTo("#imageContainer");
					$("<div/>").attr("id", "imageNav").appendTo("#imageContainer");
					$('<a href="#" border="0"/>').attr("id", "imageNavPrev").appendTo("#imageNav");
					$('<a href="#" border="0"/>').attr("id", "imageNavNext").appendTo("#imageNav");
					$("#imageNavPrev").click(function(){showImage(currentPhoto - 1); $("#image").focus()});
					$("#imageNavNext").click(function(){showImage(currentPhoto + 1);});
					$("#imageTitle").click(function(){showEditImageTitle();});
					$("<img/>").attr("id", "image").appendTo("#imageContainer");
				}
				$("#imageTitle").hide();
				$("#image").hide();
				$("#imageContainer").hide();
				$("#outerImageContainer").hide();
				$("#imageTitle").text(photoes[currentPhoto].name);
				preloadNextImage = new Image();
				preloadNextImage.onload=function(){
					resizeImageContainer(preloadNextImage);
				} 
				preloadNextImage.src = photoes[imageId].originalUrl;
				$("#image").attr("src", photoes[imageId].originalUrl);				
			}

			function resizeImageContainer(image) {
				$("#outerImageContainer").show();
				$("#imageContainer").animate({width: image.width + "px"}, 10);
				$("#imageNav").css({height: image.height + "px"});
				$("#image").fadeIn("fast");
				$("#imageTitle").fadeIn(500);
				
			}
			
			function showEditImageTitle() {
				$("<input />").attr("type", "text").attr("id", "imageTitleInput").attr("value", $("#imageTitle").text()).insertBefore("#imageTitle");
				$("#imageTitle").hide();
				$("#imageTitleInput").focus();
				
				$("#imageTitleInput").focusout(function(){
					editImageTitle();
				});
				
				$("#imageTitleInput").keyup(function(event) {
					if (event.keyCode == 13) {
						editImageTitle();	
					}
			    });
			}
			
			function editImageTitle() {
				$("#imageTitle").text($("#imageTitleInput").val());
				$("#imageTitleInput").remove();
				$("#imageTitle").show();
				photoes[currentPhoto].name = $("#imageTitle").text();
//curl -v -X POST --data-binary "{\"id\":\"IMG_2986.jpg\",\"name\":\"IMG_2986.jpg\",\"description\":\"pietjeIMG_2986.jpg\",\"originalUrl\":\"images/IMG_2986.jpg\",\"thumbnailUrl\":\"images/small/IMG_2986.jpg\"}" -H"Content-Type: application/json" -H"Accept: application/json" http://localhost:8080/restPhotoService/rest/photo
				
				$.ajax({
					url:'http://localhost:8080/restPhotoService/rest/photo/',
					type:'POST',
					data: '{"id":"' + photoes[currentPhoto].id + '","name":"' + $("#imageTitle").text() + '"}',
					dataType: 'json',
					contentType: "application/json; charset=utf-8",
					success:function(res){
					//alert("it works!");
					},
					error:function(res){
						//alert("Bad thing happend! " + res.statusText);
					}
				});
			}
        });


//
// getPageSize()
// Returns array with page width, height and window width, height
// Core code from - quirksmode.org
// Edit for Firefox by pHaez
//
function getPageSize() {

	var xScroll, yScroll;

	if (window.innerHeight && window.scrollMaxY) {
		xScroll = document.body.scrollWidth;
		yScroll = window.innerHeight + window.scrollMaxY;
	} else if (document.body.scrollHeight > document.body.offsetHeight) { // all
																			// but
																			// Explorer
																			// Mac
		xScroll = document.body.scrollWidth;
		yScroll = document.body.scrollHeight;
	} else { // Explorer Mac...would also work in Explorer 6 Strict, Mozilla
				// and Safari
		xScroll = document.body.offsetWidth;
		yScroll = document.body.offsetHeight;
	}

	var windowWidth, windowHeight;
	if (self.innerHeight) { // all except Explorer
		windowWidth = self.innerWidth;
		windowHeight = self.innerHeight;
	} else if (document.documentElement
			&& document.documentElement.clientHeight) { // Explorer 6 Strict
														// Mode
		windowWidth = document.documentElement.clientWidth;
		windowHeight = document.documentElement.clientHeight;
	} else if (document.body) { // other Explorers
		windowWidth = document.body.clientWidth;
		windowHeight = document.body.clientHeight;
	}

	// for small pages with total height less then height of the viewport
	if (yScroll < windowHeight) {
		pageHeight = windowHeight;
	} else {
		pageHeight = yScroll;
	}

	// for small pages with total width less then width of the viewport
	if (xScroll < windowWidth) {
		pageWidth = windowWidth;
	} else {
		pageWidth = xScroll;
	}

	arrayPageSize = new Array(pageWidth, pageHeight, windowWidth, windowHeight)
	return arrayPageSize;
}