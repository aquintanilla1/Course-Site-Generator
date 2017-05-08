// DATA TO LOAD
var work;
var daysOfWeek;
var redInc;
var greenInc;
var blueInc;

function Work(hSemester, hProjects) {
    this.semester = hSemester;
    this.projects = hProjects;
}
function Project(hName, hStudents, hLink) {
    this.name = hName;
    this.students = hStudents;
    this.link = hLink;
}
function initProjects() {
    var dataFile = "./js/ProjectsData.json";
    loadData(dataFile);
    
}
function loadData(jsonFile) {
    $.getJSON(jsonFile, function (json) {
        loadJSONData(json);
        addProjects();
        initTitle(json);
        initNavbar(json);
        initFooters(json);
    	initInstructorInfo(json);
    });
}
function loadJSONData(data) {
    // LOAD Projects DATA
    work = new Array();
    for (var i = 0; i < data.work.length; i++) {
        var workData = data.work[i];
        var wProjects = new Array();
        for (var j = 0; j < workData.projects.length; j++) {
            var projectData = workData.projects[j];
            var pStudents = new Array();
            for (var k = 0; k < projectData.students.length; k++) {
                pStudents[k] = projectData.students[k];
            }
            var project = new Project(projectData.name, pStudents, projectData.link);
            wProjects[j] = project;
        }
        wWork = new Work(data.work[i].semester, wProjects);
        work[i] = wWork;
    }
}

function addProjects() {
    var div = $("#project_tables");
    for (var i = 0; i < work.length; i++) {
        var wWork = work[i];
        var text = "<h3>" + wWork.semester + " Projects</h3>"
                + "<table><tbody>";        
        var projects = wWork.projects;
        for (var j = 0; j < projects.length; j+=2) {
            var project = projects[j];
            text += "<tr>";
            text += getProjectCell(project);
            if ((j + 1) < projects.length) {
                project = projects[j + 1];
                text += getProjectCell(project);
            }
            text += "</tr>";        
        }
        text += "</tbody></table><br /><br />";
        div.append(text);
    }
}
function getProjectCell(project) {
    var text = "<td><a href=\""
            + project.link
            + "\"><img src=\"./images/projects/"
            + project.name.replace(/\s/g, '')
            + ".png\" /></a><br />"
            + "<a href=\""
            + project.link
            + "\">" + project.name + "</a><br />"
            + "by ";
    for (var k = 0; k < project.students.length; k++) {
        text += project.students[k];
        if ((k + 1) < project.students.length)
            text += ", ";
    }
    text += "<br /><br /></td>";
    return text;
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
            text = '<a id="projects_link" class="open_nav" href="' + fileName + '">' + navBarTitle + '</a>';
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