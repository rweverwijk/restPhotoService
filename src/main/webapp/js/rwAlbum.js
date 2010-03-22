var photoes = new Array();
var currentPhoto;
$.getJSON("http://localhost:8080/restPhotoService/rest/photo/",
        function(data){
          $.each(data.Photo, function(i,item){
        	photoes.push(item);
            thisDiv = $("<div/>").attr("class", "image").appendTo("#images");
            thisImg = $("<img/>").attr("src", item.thumbnailUrl).attr("style", "opacity: 0.50").appendTo(thisDiv);
            thisImg.click(function() {showImage(i);});
          });
          $("img").mouseover(function() {$(this).animate({"opacity": "1"});});
		  $("img").mouseout(function() {$(this).animate({"opacity": "0.50"});});
	
				$(document).keyup(function(event) {
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
			);

			function showImage(imageId) {
				currentPhoto = imageId;
				if($('#overlay').length == 0) {
					$("<div/>").attr("id", "overlay").attr("style", "height: 1000px").appendTo("body");
					$("<div/>").attr("id", "outerImageContainer").appendTo("body");
					$("<div/>").attr("id", "imageContainer").appendTo("#outerImageContainer");
					$("<span/>").attr("id", "imageTitle").appendTo("#imageContainer");
					$("<img/>").attr("id", "image").appendTo("#imageContainer");
				}
				$("#imageTitle").text(photoes[imageId].name);
				preloadNextImage = new Image();
				preloadNextImage.onload=function(){
					enlargeImageContainer(preloadNextImage);
				} 
				preloadNextImage.src = photoes[imageId].originalUrl;
				$("#image").attr("src", photoes[imageId].originalUrl);
				
			}

			function enlargeImageContainer(image) {
				$("#imageContainer").attr("style", "width: " + image.width + "px");
				$("div#overlay").fadeIn(50);
				$("#outerImageContainer").fadeIn(50);
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