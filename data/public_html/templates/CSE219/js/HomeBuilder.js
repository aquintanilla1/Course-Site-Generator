function initHomePage() {
    var dataFile = "./js/TAsData.json";
    loadData(dataFile);
}

function loadData(jsonFile) {
    $.getJSON(jsonFile, function (json) {
        initTitle(json);
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