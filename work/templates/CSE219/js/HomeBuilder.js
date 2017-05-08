function initHomePage() {
    var dataFile = "./js/TAsData.json";
    loadData(dataFile);
}

function loadData(jsonFile) {
    $.getJSON(jsonFile, function (json) {
        initTitle(json);
        initNavbar(json);
    	initFooters(json);
    	initInstructorInfo(json);
    });
}

function initTitle(data) {
    var banner = $("#banner");
    var subject = data.subject;
    var number = data.number;
    var semester = data.semester;
    var year = data.year;
    var title = data.courseTitle;
    banner.append(subject + " " + number + " - " + semester + " " + year + "<br>" + title);
}

function initNavbar(data) {
	var navbar = $("#navbar");
	var image = data.imagePath;
	
	navbar.append('<a href="http://www.stonybrook.edu/"><img class="sbu_navbar" src="' + image + '"alt="SBU"></a>');
            
     initHomeLink(data);
	 initSyllabusLink(data);
     initScheduleLink(data);
	 initHWsLink(data);
	 initProjectsLink(data);        
}

function initHomeLink(data) {
	var navbar = $("#navbar");
	for (var i = 0; i < data.site_pages.length; i++) {
		var page = data.site_pages[i];
		var fileName = page.fileName;
		var navBarTitle = page.navBarTitle;
        var text = "";
        
        if (page.navBarTitle === "Home") {
            text = '<a id="home_link" class="open_nav" href="' + fileName + '">' + navBarTitle + '</a>';
        }
        navbar.append(text);
    }
}

function initSyllabusLink(data) {
	var navbar = $("#navbar");
	for (var i = 0; i < data.site_pages.length; i++) {
		var page = data.site_pages[i];
		var fileName = page.fileName;
		var navBarTitle = page.navBarTitle;
        var text = "";
        
        if (page.navBarTitle === "Syllabus") {
           text = '<a id="syllabus_link" class="nav" href="' + fileName + '">' + navBarTitle + '</a>';

        }
    	navbar.append(text);
    }
}

function initScheduleLink(data) {
	var navbar = $("#navbar");
	for (var i = 0; i < data.site_pages.length; i++) {
		var page = data.site_pages[i];
		var fileName = page.fileName;
		var navBarTitle = page.navBarTitle;
        var text = "";
        
        if (page.navBarTitle === "Schedule") {
           text = '<a id="schedule_link" class="nav" href="' + fileName + '">' + navBarTitle + '</a>';
        }
    
    	navbar.append(text);
    }
}

function initHWsLink(data) {
	var navbar = $("#navbar");
	for (var i = 0; i < data.site_pages.length; i++) {
		var page = data.site_pages[i];
		var fileName = page.fileName;
		var navBarTitle = page.navBarTitle;
        var text = "";
        
        if (page.navBarTitle === "HWs") {
            text = '<a id="hws_link" class="nav" href="' + fileName + '">' + navBarTitle + '</a>';
        }
    
    	navbar.append(text);
    }
}

function initProjectsLink(data) {
	var navbar = $("#navbar");
	for (var i = 0; i < data.site_pages.length; i++) {
		var page = data.site_pages[i];
		var fileName = page.fileName;
		var navBarTitle = page.navBarTitle;
        var text = "";
        
        if (page.navBarTitle === "Projects") {
            text = '<a id="projects_link" class="nav" href="' + fileName + '">' + navBarTitle + '</a>';

        }
    
    	navbar.append(text);
    }
}

function initFooters(data) {
	var footers = $("#footers");
	var leftFooter = data.leftFooterPath;
	var rightFooter = data.rightFooterPath;
	
	footers.append('<a href="http://www.stonybrook.edu/"><img class="sunysb" style="float:left" src="' + leftFooter + '"alt="SBU"></a>');
	footers.append('<a href="http://www.stonybrook.edu/"><img class="sunysb" style="float:right" src="' + rightFooter + '"alt="SBU"></a>');
}

function initInstructorInfo(data) {
	var info = $("#instructor_info");
	var name = data.instructorName;
	var home = data.instructorHome;
	
	info.append('<p style="font-size:9pt; text-align:center;">Web page created and maintained<br>by <a href="' + home + '">' + name + '</a><br><br><br><br></p>');
}
