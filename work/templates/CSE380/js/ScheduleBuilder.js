// DATA TO LOAD
var holidays;
var lectures;
var references;
var hws;
var startMondayDate;
var endFridayDate;
var daysInMonth;
var isLeapYear;

function ScheduleItem(sDate, sTitle, sTopic, sLink) {
    this.date = sDate;
    this.title = sTitle;
    this.topic = sTopic;
    this.link = sLink;
}
function ScheduleDate(sMonth, sDay) {
    this.month = sMonth;
    this.day = sDay;
}
function initSchedule() {
    initDateData();
    var dataFile = "./js/ScheduleData.json";
    loadData(dataFile);
}
function loadData(jsonFile) {
    $.getJSON(jsonFile, function (json) {
	loadJSONData(json);
        buildScheduleTable();
        addHolidays();
        addLectures();
        addReferences();
        addHWs();
        initTitle(json);
        initNavbar(json);
    	initFooters(json);
    	initInstructorInfo(json);
    });
}
function initDateData() {
    var currentYear = new Date().getFullYear();
    daysInMonth = new Array();
    if ((currentYear %4) == 0) {
        isLeapYear = true;
        daysInMonth[2] = 29;
    }
    else {
        isLeapYear = false;
        daysInMonth[2] = 28;
    }
    daysInMonth[1] = 31;
    daysInMonth[3] = 31;
    daysInMonth[4] = 30;
    daysInMonth[5] = 31;
    daysInMonth[6] = 30;
    daysInMonth[7] = 31;
    daysInMonth[8] = 31;
    daysInMonth[9] = 30;
    daysInMonth[10] = 31;
    daysInMonth[11] = 30;
    daysInMonth[12] = 31;
}
function loadJSONData(data) {
    // FIRST GET THE STARTING AND ENDING DATES
    var startingMondayMonth = parseInt(data.startingMondayMonth, 10);
    var startingMondayDay = parseInt(data.startingMondayDay, 10);
    startingMondayDate = new ScheduleDate(startingMondayMonth, startingMondayDay);
    var endingFridayMonth = parseInt(data.endingFridayMonth, 10);
    var endingFridayDay = parseInt(data.endingFridayDay, 10);
    endingFridayDate = new ScheduleDate(endingFridayMonth, endingFridayDay);
    
    // THEN GET THE HOLIDAYS
    holidays = new Array();
    for (var i = 0; i < data.holidays.length; i++) {
        var holidayData = data.holidays[i];
        var holidayDate = new ScheduleDate(holidayData.month, holidayData.day);
        var holiday = new ScheduleItem(holidayDate, holidayData.title, "none", holidayData.link);
        holidays[i] = holiday;
    }
    
    // AND THEN THE LECTURES
    lectures = new Array();
    for (var i = 0; i < data.lectures.length; i++) {
        var lectureData = data.lectures[i];
        var lectureDate = new ScheduleDate(lectureData.month, lectureData.day);
        var lecture = new ScheduleItem(lectureDate, lectureData.title, lectureData.topic, lectureData.link);
        lectures[i] = lecture;
    }
    
    // AND THEN THE REFERENCES
    references = new Array();
    for (var i = 0; i < data.references.length; i++) {
        var refData = data.references[i];
        var refDate = new ScheduleDate(refData.month, refData.day);
        var ref = new ScheduleItem(refDate, refData.title, refData.topic, refData.link);
        references[i] = ref;
    }
    
    // AND THEN THE HWs
    hws = new Array();
    for (var i = 0; i < data.hws.length; i++) {
        var hwData = data.hws[i];
        var hwDate = new ScheduleDate(hwData.month, hwData.day);
        var hw = new ScheduleItem(hwDate, hwData.title, hwData.topic, hwData.link);
        hws[i] = hw;
    }
}

function buildScheduleTable() {
    var countMonth = startingMondayDate.month;
    var countDay = startingMondayDate.day;
    var countDate = new ScheduleDate(countMonth, countDay);
    var table = $("#schedule_table");
    while (firstDateIsBeforeSecond(countDate, endingFridayDate)) {
        table.append(
                  "<tr>"
                + "<th class=\"sch\">MONDAY</th>"
                + "<th class=\"sch\">TUESDAY</th>"
                + "<th class=\"sch\">WEDNESDAY</th>"
                + "<th class=\"sch\">THURSDAY</th>"
                + "<th class=\"sch\">FRIDAY</th>"
                + "</tr>");
        table.append("<tr>");
        for (var i = 0; i < 5; i++) {
            table.append(
                    "<td class=\"sch\" id=\"" + countDate.month + "_" + countDate.day + "\"><strong>" + countDate.month + "/" + countDate.day + "</strong><br /></td>");
            incDate(countDate);
        }
        table.append("</tr>");
        incDate(countDate);
        incDate(countDate);
    }
}

function incDate(dateToInc) {
    dateToInc.day++;
    var maxDays = daysInMonth[dateToInc.month];
    if (dateToInc.day > maxDays) {
        dateToInc.day = 1;
        dateToInc.month++;
    }
}

function firstDateIsBeforeSecond(firstDate, secondDate) {
    if (firstDate.month < secondDate.month)
        return true;
    if ((firstDate.month === secondDate.month)
        && (firstDate.day < secondDate.day))
        return true;
    return false;
}

function addHolidays() {
    for (var i = 0; i < holidays.length; i++) {
        var holiday = holidays[i];
        var cell = $("#" + holiday.date.month + "_" + holiday.date.day);
        cell.addClass("holiday");
        cell.append(
                "<a href=\"" + holiday.link + "\">" + holiday.title + "</a>"
                );
    }
}

function addLectures() {
    for (var i = 0; i < lectures.length; i++) {
        var lecture = lectures[i];
        var textToAppend = "" + lecture.topic;
        if (lecture.link.valueOf() != "none".valueOf()) {
            textToAppend = "<a href=\"" + lecture.link + "\">"
                          + textToAppend
                          + "</a>";
        }
        textToAppend = "<span class=\"lecture\">"
                       + lecture.title + "<br />"
                       + "</span>"
                       + textToAppend;
        var cell = $("#" + lecture.date.month + "_" + lecture.date.day);
        cell.append(textToAppend);
    }    
}
function addReferences() {
    for (var i = 0; i < references.length; i++) {
        var ref = references[i];
        var textToAppend = "<span class=\"tutorial\">" + ref.title + "</span><br />";

        if (ref.link.valueOf() != "none".valueOf()) {
            textToAppend += "<a href=\"" + ref.link + "\">"
                            + ref.topic + "</a>";
        }
        else
            textToAppend += ref.topic;

        textToAppend += "<br /><br />";
        var cell = $("#" + ref.date.month + "_" + ref.date.day);
        cell.append(textToAppend);
    }       
}

function addHWs() {
    for (var i = 0; i < hws.length; i++) {
        var hw = hws[i];
        var textToAppend = hw.title;
        if (hw.link.valueOf() == "none".valueOf()) {
            textToAppend = 
                "<span class=\"hw\">"
                + textToAppend
                + "</span><br />";
        }
        else {
            textToAppend =
                "<a href=\"" + hw.link + "\">"
                + textToAppend
                + "</a><br />";
        }
        textToAppend += hw.topic + "<br /><br />";
        var cell = $("#" + hw.date.month + "_" + hw.date.day);
        cell.append(textToAppend);
    }
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
           text = '<a id="schedule_link" class="open_nav" href="' + fileName + '">' + navBarTitle + '</a>';
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
