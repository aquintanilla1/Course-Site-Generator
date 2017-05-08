// DATA TO LOAD
var startHour;
var endHour;
var daysOfWeek;
var officeHours;
var undergradTAs;

function buildOfficeHoursGrid() {
    var dataFile = "./js/TAsData.json";
    loadData(dataFile);
}

function loadData(jsonFile) {
    $.getJSON(jsonFile, function (json) {
	initDays(json);
        addUndergradTAs(json);
        addOfficeHours(json);
        initTitle(json);
        initNavbar(json);
    	initFooters(json);
    	initInstructorInfo(json);
    });
}

function initDays(data) {
    // GET THE START AND END HOURS
    startHour = parseInt(data.startHour);
    endHour = parseInt(data.endHour);

    // THEN MAKE THE TIMES
    daysOfWeek = new Array();
    daysOfWeek[0] = "MONDAY";
    daysOfWeek[1] = "TUESDAY";
    daysOfWeek[2] = "WEDNESDAY";
    daysOfWeek[3] = "THURSDAY";
    daysOfWeek[4] = "FRIDAY";    
}

function addUndergradTAs(data) {
    var tas = $("#undergrad_tas");
    var tasPerRow = 4;
    var numTAs = data.undergrad_tas.length;
    for (var i = 0; i < data.undergrad_tas.length; ) {
        var text = "";
        text = "<tr>";
        for (var j = 0; j < tasPerRow; j++) {
            text += buildTACell(i, numTAs, data.undergrad_tas[i]);
            i++;
        }
        text += "</tr>";
        tas.append(text);
    }
}
function buildTACell(counter, numTAs, ta) {
    if (counter >= numTAs)
        return "<td></td>";

    var name = ta.name;
    var abbrName = name.replace(/\s/g, '');
    var email = ta.email;
    var text = "<td class='tas'><img width='100' height='100'"
                + " src='./images/tas/" + abbrName + ".JPG' "
                + " alt='" + name + "' /><br />"
                + "<strong>" + name + "</strong><br />"
                + "<span class='email'>" + email + "</span><br />"
                + "<br /><br /></td>";
    return text;
}
function addOfficeHours(data) {
    for (var i = startHour; i < endHour; i++) {
        // ON THE HOUR
        var textToAppend = "<tr>";
        var amPm = getAMorPM(i);
        var displayNum = i;
        if (i > 12)
            displayNum = displayNum-12;
        textToAppend += "<td>" + displayNum + ":00" + amPm + "</td>"
                    + "<td>" + displayNum + ":30" + amPm + "</td>";
        for (var j = 0; j < 5; j++) {
            textToAppend += "<td id=\"" + daysOfWeek[j]
                                + "_" + displayNum
                                + "_00" + amPm
                                + "\" class=\"open\"></td>";
        }
        textToAppend += "</tr>"; 
        
        // ON THE HALF HOUR
        var altAmPm = amPm;
        if (displayNum === 11)
            altAmPm = "pm";
        var altDisplayNum = displayNum + 1;
        if (altDisplayNum > 12)
            altDisplayNum = 1;
                    
        textToAppend += "<tr>";
        textToAppend += "<td>" + displayNum + ":30" + amPm + "</td>"
                    + "<td>" + altDisplayNum + ":00" + altAmPm + "</td>";
            
        for (var j = 0; j < 5; j++) {
            textToAppend += "<td id=\"" + daysOfWeek[j]
                                + "_" + displayNum
                                + "_30" + amPm
                                + "\" class=\"open\"></td>";
        }
        
        textToAppend += "</tr>";
        var cell = $("#office_hours_table");
        cell.append(textToAppend);
    }
    
    // NOW SET THE OFFICE HOURS
    for (var i = 0; i < data.officeHours.length; i++) {
	var id = data.officeHours[i].day + "_" + data.officeHours[i].time;
	var name = data.officeHours[i].name;
	var cell = $("#" + id);
	if (name === "Lecture") {
	    cell.removeClass("open");
	    cell.addClass("lecture");
	    cell.html("Lecture");
	}
	else {
	    cell.removeClass("open");
	    cell.addClass("time");
	    cell.append(name);
	}
    }
}
function getAMorPM(testTime) {
    if (testTime >= 12)
        return "pm";
    else
        return "am";
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
           text = '<a id="syllabus_link" class="open_nav" href="' + fileName + '">' + navBarTitle + '</a>';

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
