// DATA TO LOAD
var hws;
var daysOfWeek;
var redInc;
var greenInc;
var blueInc;

function HW(hDate, hTime, hTitle, hTopic, hLink, hCriteria) {
    this.date = hDate;
    this.time = hTime;
    this.title = hTitle;
    this.topic = hTopic;
    this.link = hLink;
    this.criteria = hCriteria;
}
function ScheduleDate(sMonth, sDay) {
    this.month = sMonth;
    this.day = sDay;
}
function initHWs() {
    redInc = 10;
    greenInc = 10;
    blueInc = 5;
    
    daysOfWeek = new Array(7);
    daysOfWeek[0]=  "Sunday";
    daysOfWeek[1] = "Monday";
    daysOfWeek[2] = "Tuesday";
    daysOfWeek[3] = "Wednesday";
    daysOfWeek[4] = "Thursday";
    daysOfWeek[5] = "Friday";
    daysOfWeek[6] = "Saturday";
    
    var dataFile = "./js/ScheduleData.json";
    loadData(dataFile);
}
function loadData(jsonFile) {
    $.getJSON(jsonFile, function (json) {
	loadJSONData(json);
        initTitle(json);
        addHWs();
        initNavbar(json);
    initFooters(json);
    initInstructorInfo(json);
    });
}
function loadJSONData(data) {    
    // LOAD HWs DATA
    hws = new Array();
    for (var i = 0; i < data.hws.length; i++) {
        var hwData = data.hws[i];
        var hwDate = new ScheduleDate(hwData.month, hwData.day);
        var hw = new HW(hwDate, hwData.time, hwData.title, hwData.topic, hwData.link, hwData.criteria);
        hws[i] = hw;
    }
}

function addHWs() {
    var tBody = $("#hws");
    var red = 240;
    var green = 240;
    var blue = 255;
    for (var i = 0; i < hws.length; i++) {
        var hw = hws[i];
        var day = hw.date.day;
        var month = hw.date.month;
        var dayOfWeek = getDayOfWeek(day,month);
        
        // THE FIRST CELL
        var textToAppend = "<tr class=\"hw\" style=\"background-color:rgb(" + red + "," + green + "," + blue + ")\">"
                            + "<td class=\"hw\" style=\"padding-right: 60px\">"
                                + "<br />";
        if (hw.link.valueOf() === "none".valueOf()) {
            textToAppend += hw.title;
        }
        else {
            textToAppend += "<a href=\"" + hw.link + "\">" + hw.title + "</a>";
        }
        textToAppend += " - " + hw.topic + "<br /><br /></td>";
        
        // THE SECOND CELL
        textToAppend += "<td class=\"hw\" style=\"padding-right: 60px\">"
                        + "<br />" + dayOfWeek + ", " + month + "/" + day
                        + "<br /><br /><br />"
                        + "</td>";
                       
        textToAppend += "</tr>";
        tBody.append(textToAppend);
        red -= redInc;
        green -= greenInc;
        blue -= blueInc;
    }
}
function getDayOfWeek(gDay, gMonth) {
    var date = new Date();
    date.setDate(gDay);
    date.setMonth(gMonth-1);
    return daysOfWeek[date.getDay()];
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
            text = '<a id="home_link" class="nav" href="' + fileName + '">' + navBarTitle + '</a>';
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
            text = '<a id="hws_link" class="open_nav" href="' + fileName + '">' + navBarTitle + '</a>';
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