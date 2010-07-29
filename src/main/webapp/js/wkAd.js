$(document).ready(function() {
	createFlyer();
});
$(document).keyup(function(event) {
	documentKeyupEvent(event);
});

//directory
function createFlyer() {
	// only show ad the first time
	//if (readCookie("wk_shown") == null) {
		// overlay
		$("<div/>").attr("id", "overlayO").attr("style", "height: " + getPageSize()[1] +"px; background-color: #F87217; left: 0; top: 0; position: absolute; width: 50%; z-index: 40;").appendTo("body").delay(5000).fadeOut();
		$("<div/>").attr("id", "overlayW").attr("style", "height: " + getPageSize()[1] +"px; background-color: white; right: 0; top: 0; position: absolute; width: 50%; z-index: 40;").appendTo("body").delay(5000).fadeOut();
		
		// oranje
		var div = $("<div/>").attr("id", "outerImageContainerO").attr("style", "z-index: 100; position: absolute; left: 0px; top: 0; color: White; font-size: 30px; width: 50%; text-align: right;");
		div.appendTo("body").delay(5000).fadeOut();
		$("<h1/>").text("KOM").appendTo("#outerImageContainerO").attr("style", "color: White; font-size: 50px; text-align: right;");
		$("<h1/>").text("BELEEF").appendTo("#outerImageContainerO").attr("style", "color: White; font-size: 50px; text-align: right;");
		$("<h1/>").text("VIER").appendTo("#outerImageContainerO").attr("style", "color: White; font-size: 50px; text-align: right;");
		
		//wit
		var divW = $("<div/>").attr("id", "outerImageContainerW").attr("style", "z-index: 100; position: absolute; right: 0px; top: 0; color: #F87217; font-size: 30px; width: 50%; text-align: left;");
		divW.appendTo("body").delay(5000).fadeOut();
		$("<h1/>").text("Oranje").appendTo("#outerImageContainerW").attr("style", "color: #F87217; font-size: 50px; text-align: left;");
		$("<h1/>").text("Oranje").appendTo("#outerImageContainerW").attr("style", "color: #F87217; font-size: 50px; text-align: left;");
		$("<h1/>").text("Oranje").appendTo("#outerImageContainerW").attr("style", "color: #F87217; font-size: 50px; text-align: left;");
		
		//Detail
		var divD = $("<div/>").attr("id", "outerImageContainerD").attr("style", "z-index: 100; position: absolute; top: " + (getPageSize()[3] - 100) + "px; right: 0px; font-size: 10px; width: 100%; text-align: center;");
		divD.appendTo("body").delay(5000).fadeOut();
		$("<h1/>").text("Trap het WK af met het Fortissimo Oranjefeest. Kom direct in de").appendTo("#outerImageContainerD");
		$("<h1/>").text("stemming voor 4 onvergetelijke weken! 12 juni 2010 vanaf 21:00").appendTo("#outerImageContainerD");
		
		// set cookie to session
		document.cookie="wk_shown=true";
	//}
}

function readCookie(name) {
	var nameEQ = name + "=";
	var ca = document.cookie.split(';');
	for(var i=0;i < ca.length;i++) {
		var c = ca[i];
		while (c.charAt(0)==' ') c = c.substring(1,c.length);
		if (c.indexOf(nameEQ) == 0) return c.substring(nameEQ.length,c.length);
	}
	return null;
}


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