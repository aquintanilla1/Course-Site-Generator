/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.files;

import csg.CSGApp;
import csg.CSGProp;
import csg.data.CSGData;
import csg.data.Recitation;
import csg.data.ScheduleItem;
import csg.data.SitePage;
import csg.data.Student;
import csg.data.TeachingAssistant;
import csg.data.Team;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import javafx.collections.ObservableList;
import javax.json.Json;
import javax.json.JsonArray;
import javax.json.JsonArrayBuilder;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.json.JsonWriter;
import javax.json.JsonWriterFactory;
import javax.json.stream.JsonGenerator;
import org.apache.commons.io.FileUtils;
import properties_manager.PropertiesManager;

/**
 *
 * @author Alvaro Quintanilla, ID: 110289649
 */
public class CSGFiles implements AppFileComponent {
    CSGApp app;
    // THESE ARE USED FOR IDENTIFYING JSON TYPES
    static final String JSON_SUBJECT = "subject";
    static final String JSON_NUMBER = "number";
    static final String JSON_SEMESTER = "semester";
    static final String JSON_YEAR = "year";
    static final String JSON_COURSE_TITLE = "courseTitle";
    static final String JSON_INSTRUCTOR_NAME = "instructorName";
    static final String JSON_INSTRUCTOR_HOME = "instructorHome";
    static final String JSON_EXPORT_DIRECTORY = "exportDir";
    static final String JSON_COURSE_INFO = "course_info";
    
    static final String JSON_TEMPLATE_DIRECTORY = "templateDir";
    static final String JSON_PAGE_USED = "isUsed";
    static final String JSON_PAGE_NAVBAR_TITLE = "navBarTitle";
    static final String JSON_PAGE_FILE_NAME = "fileName";
    static final String JSON_PAGE_SCRIPT = "script";
    static final String JSON_SITE_PAGES = "site_pages";
    
    static final String JSON_IMAGE_PATH = "imagePath";
    static final String JSON_LEFT_FOOTER_PATH = "leftFooterPath";
    static final String JSON_RIGHT_FOOTER_PATH = "rightFooterPath";
    static final String JSON_STYLESHEET = "stylesheet";
    static final String JSON_STYLE = "style";
            
    static final String JSON_START_HOUR = "startHour";
    static final String JSON_END_HOUR = "endHour";
    static final String JSON_OFFICE_HOURS = "officeHours";
    static final String JSON_DAY = "day";
    static final String JSON_TIME = "time";
    static final String JSON_NAME = "name";
    static final String JSON_EMAIL = "email";
    static final String JSON_IS_UNDERGRAD = "isUndergrad";
    static final String JSON_UNDERGRAD_TAS = "undergrad_tas";
    static final String JSON_GRAD_TAS = "grad_tas";
    
    static final String JSON_RECITATION_SECTION = "section";
    static final String JSON_RECITATION_INSTRUCTOR = "recitationInstructor";
    static final String JSON_DAY_TIME = "day_time";
    static final String JSON_LOCATION = "location";
    static final String JSON_SUPERVISING_TA_ONE = "ta_1";
    static final String JSON_SUPERVISING_TA_TWO = "ta_2";
    static final String JSON_RECITATIONS = "recitations";
    
    static final String JSON_STARTING_MONDAY = "startingMonday";
    static final String JSON_ENDING_FRIDAY = "endingFriday";
    static final String JSON_STARTING_MONDAY_MONTH = "startingMondayMonth";
    static final String JSON_STARTING_MONDAY_DAY = "startingMondayDay";
    static final String JSON_ENDING_FRIDAY_MONTH = "endingFridayMonth";
    static final String JSON_ENDING_FRIDAY_DAY = "endingFridayDay";
    
    static final String JSON_TYPE = "type";
    static final String JSON_DATE = "date";
    static final String JSON_MONTH = "month";
    static final String JSON_SCHEDULE_TIME = "scheduleTime";
    static final String JSON_SCHEDULE_TITLE = "scheduleTitle";
    static final String JSON_TITLE = "title";
    static final String JSON_TOPIC = "topic";
    static final String JSON_SCHEDULE_LINK = "scheduleLink";
    static final String JSON_LINK = "link";
    static final String JSON_CRITERIA = "criteria";
    static final String JSON_HOLIDAYS = "holidays";
    static final String JSON_LECTURES = "lectures";
    static final String JSON_HWS = "hws";
    static final String JSON_REFERENCES = "references";
    static final String JSON_SCHEDULE_ITEMS = "schedule_items";
    
    static final String JSON_TEAM_NAME = "name";
    static final String JSON_COLOR = "color";
    static final String JSON_RED = "red";
    static final String JSON_GREEN = "green";
    static final String JSON_BLUE = "blue";
    static final String JSON_TEXT_COLOR = "text_color";
    static final String JSON_TEAM_LINK = "link";
    static final String JSON_TEAMS = "teams";
    
    static final String JSON_STUDENT_FIRST_NAME = "firstName";
    static final String JSON_STUDENT_LAST_NAME = "lastName";
    static final String JSON_STUDENT_TEAM = "studentTeam";
    static final String JSON_ROLE = "role";
    static final String JSON_STUDENTS = "students";
    static final String JSON_TEAM = "team";
    
    static final String JSON_WORK = "work";
    static final String JSON_PROJECTS = "projects";
    
    public CSGFiles(CSGApp initApp) {
        app = initApp;
    }
    
    public CSGFiles() {}

    @Override
    public void saveData(AppDataComponent data, String filePath) throws IOException {
        // GET THE DATA
	CSGData dataManager = (CSGData)data;

        JsonArrayBuilder courseInfoArrayBuilder = Json.createArrayBuilder();
	ObservableList<String> courseInfo = dataManager.getCourseInfo();
        JsonObject courseInfoJson = Json.createObjectBuilder()
                .add(JSON_SUBJECT, courseInfo.get(0))
                .add(JSON_NUMBER, courseInfo.get(1))
                .add(JSON_SEMESTER, courseInfo.get(2))
                .add(JSON_YEAR, courseInfo.get(3))
                .add(JSON_COURSE_TITLE, courseInfo.get(4))
                .add(JSON_INSTRUCTOR_NAME, courseInfo.get(5))
                .add(JSON_INSTRUCTOR_HOME, courseInfo.get(6))
                .add(JSON_EXPORT_DIRECTORY, courseInfo.get(7))
                .build();
        courseInfoArrayBuilder.add(courseInfoJson);
        JsonArray courseInfoArray = courseInfoArrayBuilder.build();
        
        JsonArrayBuilder sitePageArrayBuilder = Json.createArrayBuilder();
	ObservableList<SitePage> pages = dataManager.getSitePages();
	for (SitePage page : pages) {	    
	    JsonObject sitePageJson = Json.createObjectBuilder()
		    .add(JSON_PAGE_USED, page.getIsUsed())
                    .add(JSON_PAGE_NAVBAR_TITLE, page.getNavbarTitle())
                    .add(JSON_PAGE_FILE_NAME, page.getFileName())
                    .add(JSON_PAGE_SCRIPT, page.getScript())
                    .build();
	    sitePageArrayBuilder.add(sitePageJson);
	}
	JsonArray sitePageArray = sitePageArrayBuilder.build();
        
        JsonArrayBuilder pageStyleArrayBuilder = Json.createArrayBuilder();
        JsonObject pageStyleJson = Json.createObjectBuilder()
                .add(JSON_IMAGE_PATH, dataManager.getImagePath())
                .add(JSON_LEFT_FOOTER_PATH, dataManager.getLeftFooterImagePath())
                .add(JSON_RIGHT_FOOTER_PATH, dataManager.getRightFooterImagePath())
                .add(JSON_STYLESHEET, dataManager.getStylesheet())
                .build();
        pageStyleArrayBuilder.add(pageStyleJson);
        JsonArray pageStyleArray = pageStyleArrayBuilder.build();
                
	JsonArrayBuilder taArrayBuilder = Json.createArrayBuilder();
	ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
	for (TeachingAssistant ta : tas) {	    
	    JsonObject taJson = Json.createObjectBuilder()
		    .add(JSON_NAME, ta.getName())
                    .add(JSON_EMAIL, ta.getEmail())
                    .add(JSON_IS_UNDERGRAD, ta.getIsUndergrad())
                    .build();
	    taArrayBuilder.add(taJson);
	}
	JsonArray undergradTAsArray = taArrayBuilder.build();

	// NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
	JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
	ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
	for (TimeSlot ts : officeHours) {	    
	    JsonObject tsJson = Json.createObjectBuilder()
		    .add(JSON_DAY, ts.getDay())
		    .add(JSON_TIME, ts.getTime())
		    .add(JSON_NAME, ts.getName()).build();
	    timeSlotArrayBuilder.add(tsJson);
	}
	JsonArray timeSlotsArray = timeSlotArrayBuilder.build();  
        
        JsonArrayBuilder recitationArrayBuilder = Json.createArrayBuilder();
	ObservableList<Recitation> recitations = dataManager.getRecitations();
	for (Recitation recitation : recitations) {	    
	    JsonObject recitationJson = Json.createObjectBuilder()
		    .add(JSON_RECITATION_SECTION, recitation.getSection())
                    .add(JSON_RECITATION_INSTRUCTOR, recitation.getInstructor())
                    .add(JSON_DAY_TIME, recitation.getDayTime())
                    .add(JSON_LOCATION, recitation.getLocation())
                    .add(JSON_SUPERVISING_TA_ONE, recitation.getTa1())
                    .add(JSON_SUPERVISING_TA_TWO, recitation.getTa2())
                    .build();
	    recitationArrayBuilder.add(recitationJson);
	}
	JsonArray recitationsArray = recitationArrayBuilder.build();

        JsonArrayBuilder scheduleItemArrayBuilder = Json.createArrayBuilder();
	ObservableList<ScheduleItem> scheduleItems = dataManager.getScheduleItems();
	for (ScheduleItem item : scheduleItems) {	    
	    JsonObject scheduleItemJson = Json.createObjectBuilder()
		    .add(JSON_TYPE, item.getType())
                    .add(JSON_SCHEDULE_TIME, item.getTime())
                    .add(JSON_DATE, item.getDate())
                    .add(JSON_SCHEDULE_TITLE, item.getTitle())
                    .add(JSON_TOPIC, item.getTopic())
                    .add(JSON_SCHEDULE_LINK, item.getLink())
                    .add(JSON_CRITERIA, item.getCriteria())
                    .build();
	    scheduleItemArrayBuilder.add(scheduleItemJson);
	}
        JsonArray scheduleItemsArray = scheduleItemArrayBuilder.build();

        JsonArrayBuilder teamArrayBuilder = Json.createArrayBuilder();
	ObservableList<Team> teams = dataManager.getTeams();
	for (Team team : teams) {	    
	    JsonObject teamJson = Json.createObjectBuilder()
		    .add(JSON_TEAM_NAME, team.getName())
                    .add(JSON_COLOR, team.getColor())
                    .add(JSON_TEXT_COLOR, team.getTextColor())
                    .add(JSON_TEAM_LINK, team.getLink())
                    .build();
	    teamArrayBuilder.add(teamJson);
	}
        
        JsonArray teamArray = teamArrayBuilder.build();
        
        JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();
	ObservableList<Student> students = dataManager.getStudents();
	for (Student student : students) {	    
	    JsonObject studentJson = Json.createObjectBuilder()
		    .add(JSON_STUDENT_FIRST_NAME, student.getFirstName())
                    .add(JSON_STUDENT_LAST_NAME, student.getLastName())
                    .add(JSON_STUDENT_TEAM, student.getTeam())
                    .add(JSON_ROLE, student.getRole())
                    .build();
	    studentArrayBuilder.add(studentJson);
	}
        
        JsonArray studentArray = studentArrayBuilder.build();
        
	// THEN PUT IT ALL TOGETHER IN A JsonObject
	JsonObject dataManagerJSO = Json.createObjectBuilder()
                .add(JSON_COURSE_INFO, courseInfoArray)
                .add(JSON_TEMPLATE_DIRECTORY, "" + dataManager.getTemplateDirectory())
                .add(JSON_SITE_PAGES, sitePageArray)
                .add(JSON_STYLE, pageStyleArray)
		.add(JSON_START_HOUR, "" + dataManager.getStartHour())
		.add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
                .add(JSON_RECITATIONS, recitationsArray)
                .add(JSON_STARTING_MONDAY, "" + dataManager.getStartMonday())
                .add(JSON_ENDING_FRIDAY, "" + dataManager.getEndFriday())
                .add(JSON_SCHEDULE_ITEMS, scheduleItemsArray)
                .add(JSON_TEAMS, teamArray)
                .add(JSON_STUDENTS, studentArray)
		.build();
	
	// AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
	StringWriter sw = new StringWriter();
	JsonWriter jsonWriter = writerFactory.createWriter(sw);
	jsonWriter.writeObject(dataManagerJSO);
	jsonWriter.close();
        
        System.out.println(filePath);

	// INIT THE WRITER
	OutputStream os = new FileOutputStream(filePath);
	JsonWriter jsonFileWriter = Json.createWriter(os);
	jsonFileWriter.writeObject(dataManagerJSO);
	String prettyPrinted = sw.toString();
	PrintWriter pw = new PrintWriter(filePath);
	pw.write(prettyPrinted);
	pw.close();
    }

    @Override
    public void loadData(AppDataComponent data, String filePath) throws IOException {
        CSGData dataManager = (CSGData)data;
        
	// LOAD THE JSON FILE WITH ALL THE DATA
	JsonObject json = loadJSONFile(filePath);
        
        //Kind of redundant loop since it only has one object in it. May fix later
        JsonArray courseInfoArray = json.getJsonArray(JSON_COURSE_INFO);
        for (int i = 0; i < courseInfoArray.size(); i++) {
            JsonObject jsonCourseInfo = courseInfoArray.getJsonObject(i);
            dataManager.getCourseInfo().set(0, jsonCourseInfo.getString(JSON_SUBJECT));
            dataManager.getCourseInfo().set(1, jsonCourseInfo.getString(JSON_NUMBER));
            dataManager.getCourseInfo().set(2, jsonCourseInfo.getString(JSON_SEMESTER));
            dataManager.getCourseInfo().set(3, jsonCourseInfo.getString(JSON_YEAR));
            dataManager.getCourseInfo().set(4, jsonCourseInfo.getString(JSON_COURSE_TITLE));
            dataManager.getCourseInfo().set(5, jsonCourseInfo.getString(JSON_INSTRUCTOR_NAME));
            dataManager.getCourseInfo().set(6, jsonCourseInfo.getString(JSON_INSTRUCTOR_HOME));
            dataManager.getCourseInfo().set(7, jsonCourseInfo.getString(JSON_EXPORT_DIRECTORY));
        }
        
        dataManager.setTemplateDirectory(json.getString(JSON_TEMPLATE_DIRECTORY));
        
        JsonArray sitePageArray = json.getJsonArray(JSON_SITE_PAGES);
        for (int i = 0; i < sitePageArray.size(); i++) {
            JsonObject jsonSitePage = sitePageArray.getJsonObject(i);
            boolean isUsed = jsonSitePage.getBoolean(JSON_PAGE_USED);
            dataManager.getSitePages().get(i).setUsed(isUsed);
        }
        
        JsonArray pageStyleArray = json.getJsonArray(JSON_STYLE);
        for (int i = 0; i < pageStyleArray.size(); i++) {
            JsonObject jsonPageStyle = pageStyleArray.getJsonObject(i);
            dataManager.setImage(jsonPageStyle.getString(JSON_IMAGE_PATH));
            dataManager.setLeftFooter(jsonPageStyle.getString(JSON_LEFT_FOOTER_PATH));
            dataManager.setRightFooter(jsonPageStyle.getString(JSON_RIGHT_FOOTER_PATH));
            dataManager.setStylesheet(jsonPageStyle.getString(JSON_STYLESHEET));
        }

	// LOAD THE START AND END HOURS
	String startHour = json.getString(JSON_START_HOUR);
        String endHour = json.getString(JSON_END_HOUR);
        dataManager.initHours(startHour, endHour);
        
        dataManager.setStartMonday(json.getString(JSON_STARTING_MONDAY));
        dataManager.setEndFriday(json.getString(JSON_ENDING_FRIDAY));
        
        if (app != null) {
            app.getWorkspaceComponent().reloadWorkspace(app.getDataComponent());
        }

        // NOW LOAD ALL THE UNDERGRAD TAs
        JsonArray jsonTAArray = json.getJsonArray(JSON_UNDERGRAD_TAS);
        for (int i = 0; i < jsonTAArray.size(); i++) {
            JsonObject jsonTA = jsonTAArray.getJsonObject(i);
            String name = jsonTA.getString(JSON_NAME);
            String email = jsonTA.getString(JSON_EMAIL);
            boolean isUndergrad = jsonTA.getBoolean(JSON_IS_UNDERGRAD);
            dataManager.addTA(name, email, isUndergrad ,true);
        }

        // AND THEN ALL THE OFFICE HOURS
        JsonArray jsonOfficeHoursArray = json.getJsonArray(JSON_OFFICE_HOURS);
        for (int i = 0; i < jsonOfficeHoursArray.size(); i++) {
            JsonObject jsonOfficeHours = jsonOfficeHoursArray.getJsonObject(i);
            String day = jsonOfficeHours.getString(JSON_DAY);
            String time = jsonOfficeHours.getString(JSON_TIME);
            String name = jsonOfficeHours.getString(JSON_NAME);
            dataManager.addOfficeHoursReservation(day, time, name);
        }
        
        //Loading Recitations
        JsonArray jsonRecitationArray = json.getJsonArray(JSON_RECITATIONS);
        ArrayList<String> recitationDetails = new ArrayList<>();
        for (int i = 0; i < jsonRecitationArray.size(); i++) {
            JsonObject jsonRecitation = jsonRecitationArray.getJsonObject(i);
            recitationDetails.add(jsonRecitation.getString(JSON_RECITATION_SECTION));
            recitationDetails.add(jsonRecitation.getString(JSON_RECITATION_INSTRUCTOR));
            recitationDetails.add(jsonRecitation.getString(JSON_DAY_TIME));
            recitationDetails.add(jsonRecitation.getString(JSON_LOCATION));
            recitationDetails.add(jsonRecitation.getString(JSON_SUPERVISING_TA_ONE));
            recitationDetails.add(jsonRecitation.getString(JSON_SUPERVISING_TA_TWO));
            dataManager.addRecitation(recitationDetails);
            recitationDetails.clear();
        }
        
        
        JsonArray jsonScheduleItemArray = json.getJsonArray(JSON_SCHEDULE_ITEMS);
        for (int i = 0; i < jsonScheduleItemArray.size(); i++) {
            JsonObject jsonScheduleItem = jsonScheduleItemArray.getJsonObject(i);
            String type = jsonScheduleItem.getString(JSON_TYPE);
            String date = jsonScheduleItem.getString(JSON_DATE);
            String time = jsonScheduleItem.getString(JSON_SCHEDULE_TIME);
            String title = jsonScheduleItem.getString(JSON_SCHEDULE_TITLE);
            String topic = jsonScheduleItem.getString(JSON_TOPIC);
            String scheduleLink = jsonScheduleItem.getString(JSON_SCHEDULE_LINK);
            String criteria = jsonScheduleItem.getString(JSON_CRITERIA);
            dataManager.addScheduleItem(type, date, time, title, topic, scheduleLink, criteria);
        }

        JsonArray jsonTeamArray = json.getJsonArray(JSON_TEAMS);
        for (int i = 0; i < jsonTeamArray.size(); i++) {
            JsonObject jsonTeam = jsonTeamArray.getJsonObject(i);
            String teamName = jsonTeam.getString(JSON_TEAM_NAME);
            String color = jsonTeam.getString(JSON_COLOR);
            String textColor = jsonTeam.getString(JSON_TEXT_COLOR);
            String teamLink = jsonTeam.getString(JSON_TEAM_LINK);
            dataManager.addTeam(teamName, color, textColor, teamLink);
        }

        JsonArray jsonStudentArray = json.getJsonArray(JSON_STUDENTS);
        for (int i = 0; i < jsonStudentArray.size(); i++) {
            JsonObject jsonStudent = jsonStudentArray.getJsonObject(i);
            String firstName = jsonStudent.getString(JSON_STUDENT_FIRST_NAME);
            String lastName = jsonStudent.getString(JSON_STUDENT_LAST_NAME);
            String teamName = jsonStudent.getString(JSON_STUDENT_TEAM);
            String role = jsonStudent.getString(JSON_ROLE);
            dataManager.addStudent(firstName, lastName,teamName, role);
        }
        
        
        
    }
      
    // HELPER METHOD FOR LOADING DATA FROM A JSON FORMAT
    private JsonObject loadJSONFile(String jsonFilePath) throws IOException {
	InputStream is = new FileInputStream(jsonFilePath);
	JsonReader jsonReader = Json.createReader(is);
	JsonObject json = jsonReader.readObject();
	jsonReader.close();
	is.close();
	return json;
    }
    

    @Override
    public void exportData(AppDataComponent data, String filePath) throws IOException {
        CSGData dataManager = (CSGData) data;
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        filePath = dataManager.getCourseInfo().get(7);
        String sourcePath = System.getProperty("user.dir") + "/data/public_html";
        String directoryPath;
        
        if (filePath.equals(props.getProperty(CSGProp.DEFAULT_DIR_TEXT))) {
            return; //will provide dialog message later.
        }
        else {
           directoryPath = filePath;
        }        
        
        //TAs.Json
        JsonArrayBuilder undergradTaArrayBuilder = Json.createArrayBuilder();
	ObservableList<TeachingAssistant> tas = dataManager.getTeachingAssistants();
	for (TeachingAssistant ta : tas) {	   
            if (ta.getIsUndergrad()) {
                JsonObject taJson = Json.createObjectBuilder()
                        .add(JSON_NAME, ta.getName())
                        .add(JSON_EMAIL, ta.getEmail())
                        .build();
                undergradTaArrayBuilder.add(taJson);
            }
	}
	JsonArray undergradTAsArray = undergradTaArrayBuilder.build();
        
        JsonArrayBuilder gradTaArrayBuilder = Json.createArrayBuilder();
	for (TeachingAssistant ta : tas) {	   
            if (!ta.getIsUndergrad()) {
                JsonObject taJson = Json.createObjectBuilder()
                        .add(JSON_NAME, ta.getName())
                        .add(JSON_EMAIL, ta.getEmail())
                        .build();
                gradTaArrayBuilder.add(taJson);
            }
	}
	JsonArray gradTAsArray = gradTaArrayBuilder.build();

	// NOW BUILD THE TIME SLOT JSON OBJCTS TO SAVE
	JsonArrayBuilder timeSlotArrayBuilder = Json.createArrayBuilder();
	ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(dataManager);
	for (TimeSlot ts : officeHours) {	    
	    JsonObject tsJson = Json.createObjectBuilder()
		    .add(JSON_DAY, ts.getDay())
		    .add(JSON_TIME, ts.getTime())
		    .add(JSON_NAME, ts.getName()).build();
	    timeSlotArrayBuilder.add(tsJson);
	}
	JsonArray timeSlotsArray = timeSlotArrayBuilder.build();
        
        JsonObject tasJSO = Json.createObjectBuilder()
                .add(JSON_SUBJECT, dataManager.getCourseInfo().get(0))
                .add(JSON_NUMBER, dataManager.getCourseInfo().get(1))
                .add(JSON_SEMESTER, dataManager.getCourseInfo().get(2))
                .add(JSON_YEAR, dataManager.getCourseInfo().get(3))
                .add(JSON_COURSE_TITLE, dataManager.getCourseInfo().get(4))
		.add(JSON_START_HOUR, "" + dataManager.getStartHour())
		.add(JSON_END_HOUR, "" + dataManager.getEndHour())
                .add(JSON_UNDERGRAD_TAS, undergradTAsArray)
                .add(JSON_GRAD_TAS, gradTAsArray)
                .add(JSON_OFFICE_HOURS, timeSlotsArray)
		.build();
        String taFilePath = sourcePath + "/js/TAsData.json";
        
        //RecitationData.json
        JsonArrayBuilder recitationArrayBuilder = Json.createArrayBuilder();
	ObservableList<Recitation> recitations = dataManager.getRecitations();
	for (Recitation recitation : recitations) {	    
	    JsonObject recitationJson = Json.createObjectBuilder()
		    .add(JSON_RECITATION_SECTION, "<strong>" + recitation.getSection() + "</strong> (" + recitation.getInstructor() + ")")
                    .add(JSON_DAY_TIME, recitation.getDayTime())
                    .add(JSON_LOCATION, recitation.getLocation())
                    .add(JSON_SUPERVISING_TA_ONE, recitation.getTa1())
                    .add(JSON_SUPERVISING_TA_TWO, recitation.getTa2())
                    .build();
	    recitationArrayBuilder.add(recitationJson);
	}
	JsonArray recitationsArray = recitationArrayBuilder.build();
        
        JsonObject recitationsJSO = Json.createObjectBuilder()
                .add(JSON_RECITATIONS, recitationsArray)
                .build();
        String recitationFilePath = sourcePath + "/js/RecitationsData.json";
        
        //Schedule.json
        ObservableList<ScheduleItem> scheduleItems = dataManager.getScheduleItems();
        JsonArrayBuilder holidayArrayBuilder = Json.createArrayBuilder();
        for (ScheduleItem item: scheduleItems) {
            if (item.getType().equals(props.getProperty(CSGProp.HOLIDAY_TEXT))) {
                JsonObject holidayJson = Json.createObjectBuilder()
                        .add(JSON_MONTH, item.getMonth())
                        .add(JSON_DAY, item.getDay())
                        .add(JSON_TITLE, item.getTitle())
                        .add(JSON_LINK, item.getLink())
                        .build();
                holidayArrayBuilder.add(holidayJson);
            }
        }
        JsonArray holidayArray = holidayArrayBuilder.build();
        
        JsonArrayBuilder lectureArrayBuilder = Json.createArrayBuilder();
        for (ScheduleItem item: scheduleItems) {
            if (item.getType().equals(props.getProperty(CSGProp.LECTURE_TEXT))) {
                JsonObject lectureJson = Json.createObjectBuilder()
                        .add(JSON_MONTH, item.getMonth())
                        .add(JSON_DAY, item.getDay())
                        .add(JSON_TITLE, item.getTitle())
                        .add(JSON_TOPIC, item.getTopic())
                        .add(JSON_LINK, item.getLink())
                        .build();
                lectureArrayBuilder.add(lectureJson);
            }
        }
        JsonArray lectureArray = lectureArrayBuilder.build();
        
        JsonArrayBuilder referenceArrayBuilder = Json.createArrayBuilder();
        for (ScheduleItem item: scheduleItems) {
            if (item.getType().equals(props.getProperty(CSGProp.REFERENCE_TEXT))) {
                JsonObject referenceJson = Json.createObjectBuilder()
                        .add(JSON_MONTH, item.getMonth())
                        .add(JSON_DAY, item.getDay())
                        .add(JSON_TITLE, item.getTitle())
                        .add(JSON_TOPIC, item.getTopic())
                        .add(JSON_LINK, item.getLink())
                        .build();
                referenceArrayBuilder.add(referenceJson);
            }
        }
        JsonArray referenceArray = referenceArrayBuilder.build();
        
        JsonArrayBuilder scheduleRecitationArrayBuilder = Json.createArrayBuilder();
        for (ScheduleItem item: scheduleItems) {
            if (item.getType().equals(props.getProperty(CSGProp.RECITATION_TEXT))) {
                JsonObject scheduleRecitationJson = Json.createObjectBuilder()
                        .add(JSON_MONTH, item.getMonth())
                        .add(JSON_DAY, item.getDay())
                        .add(JSON_TITLE, item.getTitle())
                        .add(JSON_TOPIC, item.getTopic())
                        .build();
                scheduleRecitationArrayBuilder.add(scheduleRecitationJson);
            }
        }
        JsonArray scheduleRecitationArray = scheduleRecitationArrayBuilder.build();
        
        JsonArrayBuilder hwsArrayBuilder = Json.createArrayBuilder();
        for (ScheduleItem item: scheduleItems) {
            if (item.getType().equals(props.getProperty(CSGProp.HWS_TEXT))) {
                JsonObject hwsJson = Json.createObjectBuilder()
                        .add(JSON_MONTH, item.getMonth())
                        .add(JSON_DAY, item.getDay())
                        .add(JSON_TITLE, item.getTitle())
                        .add(JSON_TOPIC, item.getTopic())
                        .add(JSON_LINK, item.getLink())
                        .add(JSON_TIME, item.getTime())
                        .add(JSON_CRITERIA, item.getCriteria())
                        .build();
                hwsArrayBuilder.add(hwsJson);
            }
        }
        JsonArray hwsArray = hwsArrayBuilder.build();
        
        JsonObject scheduleJSO = Json.createObjectBuilder()
                .add(JSON_SUBJECT, dataManager.getCourseInfo().get(0))
                .add(JSON_NUMBER, dataManager.getCourseInfo().get(1))
                .add(JSON_SEMESTER, dataManager.getCourseInfo().get(2))
                .add(JSON_YEAR, dataManager.getCourseInfo().get(3))
                .add(JSON_COURSE_TITLE, dataManager.getCourseInfo().get(4))
                .add(JSON_STARTING_MONDAY_MONTH, dataManager.getStartMonth())
                .add(JSON_STARTING_MONDAY_DAY, dataManager.getStartDay())
                .add(JSON_ENDING_FRIDAY_MONTH, dataManager.getEndMonth())
                .add(JSON_ENDING_FRIDAY_DAY, dataManager.getEndDay())
                .add(JSON_HOLIDAYS, holidayArray)
                .add(JSON_LECTURES, lectureArray)
                .add(JSON_REFERENCES, referenceArray)
                .add(JSON_RECITATIONS, scheduleRecitationArray)
                .add(JSON_HWS, hwsArray)
                .build();
        String scheduleFilePath = sourcePath + "/js/ScheduleData.json";
        
        //TeamsAndStudents.json
        JsonArrayBuilder teamArrayBuilder = Json.createArrayBuilder();
	ObservableList<Team> teams = dataManager.getTeams();
	for (Team team : teams) {	    
	    JsonObject teamJson = Json.createObjectBuilder()
		    .add(JSON_TEAM_NAME, team.getName())
                    .add(JSON_RED, team.getRed())
                    .add(JSON_GREEN, team.getGreen())
                    .add(JSON_BLUE, team.getBlue())
                    .add(JSON_TEXT_COLOR, team.getTextColor())
                    .build();
	    teamArrayBuilder.add(teamJson);
	}
        
        JsonArray teamArray = teamArrayBuilder.build();
        
        JsonArrayBuilder studentArrayBuilder = Json.createArrayBuilder();
	ObservableList<Student> students = dataManager.getStudents();
	for (Student student : students) {	    
	    JsonObject studentJson = Json.createObjectBuilder()
                    .add(JSON_STUDENT_LAST_NAME, student.getLastName())
		    .add(JSON_STUDENT_FIRST_NAME, student.getFirstName())
                    .add(JSON_TEAM, student.getTeam())
                    .add(JSON_ROLE, student.getRole())
                    .build();
	    studentArrayBuilder.add(studentJson);
	}
        
        JsonArray studentArray = studentArrayBuilder.build();
        
        JsonObject teamsAndStudentsJSO = Json.createObjectBuilder()
                .add(JSON_SUBJECT, dataManager.getCourseInfo().get(0))
                .add(JSON_NUMBER, dataManager.getCourseInfo().get(1))
                .add(JSON_SEMESTER, dataManager.getCourseInfo().get(2))
                .add(JSON_YEAR, dataManager.getCourseInfo().get(3))
                .add(JSON_COURSE_TITLE, dataManager.getCourseInfo().get(4))
                .add(JSON_TEAMS, teamArray)
                .add(JSON_STUDENTS, studentArray)
		.build();
        String teamsAndStudentsFilePath = sourcePath + "/js/TeamsAndStudents.json";
        
        //Projects.json
        JsonArrayBuilder workArrayBuilder = Json.createArrayBuilder();
        
        JsonArrayBuilder projectArrayBuilder = Json.createArrayBuilder();
        JsonArrayBuilder studentsInTeamArrayBuilder = Json.createArrayBuilder();
        for (Team team : teams) {
            ArrayList<Student> studentsInTeam = dataManager.getStudentsInTeam(team.getName());
            for (Student student: studentsInTeam) {
                studentsInTeamArrayBuilder.add(student.getFirstName() + " " + student.getLastName());
            }
            JsonArray studentsInTeamArray = studentsInTeamArrayBuilder.build();
            JsonObject projectJson = Json.createObjectBuilder()
                    .add(JSON_TEAM_NAME, team.getName())
                    .add(JSON_STUDENTS, studentsInTeamArray)
                    .add(JSON_TEAM_LINK, team.getLink())
                    .build();
            projectArrayBuilder.add(projectJson);
        }
        
        JsonArray projectsArray = projectArrayBuilder.build();
        JsonObject projectsJSO = Json.createObjectBuilder()
                .add(JSON_SEMESTER, dataManager.getCourseInfo().get(2) + " " + dataManager.getCourseInfo().get(3))
                .add(JSON_PROJECTS, projectsArray)
                .build();
        workArrayBuilder.add(projectsJSO);
        JsonArray workArray = workArrayBuilder.build();
        
        JsonObject workJSO = Json.createObjectBuilder()
                .add(JSON_SUBJECT, dataManager.getCourseInfo().get(0))
                .add(JSON_NUMBER, dataManager.getCourseInfo().get(1))
                .add(JSON_SEMESTER, dataManager.getCourseInfo().get(2))
                .add(JSON_YEAR, dataManager.getCourseInfo().get(3))
                .add(JSON_COURSE_TITLE, dataManager.getCourseInfo().get(4))
                .add(JSON_WORK, workArray)
                .build();
        String projectsFilePath = sourcePath + "/js/ProjectsData.json";
        
        // AND NOW OUTPUT IT TO A JSON FILE WITH PRETTY PRINTING
	Map<String, Object> properties = new HashMap<>(1);
	properties.put(JsonGenerator.PRETTY_PRINTING, true);
	JsonWriterFactory writerFactory = Json.createWriterFactory(properties);
        
        //TAsData
        StringWriter sw1 = new StringWriter();
	JsonWriter jsonWriter1 = writerFactory.createWriter(sw1);
	jsonWriter1.writeObject(tasJSO);
	jsonWriter1.close();
        
	OutputStream os1 = new FileOutputStream(taFilePath);
	JsonWriter jsonFileWriter1 = Json.createWriter(os1);
	jsonFileWriter1.writeObject(tasJSO);
	String prettyPrinted1 = sw1.toString();
	PrintWriter pw1 = new PrintWriter(taFilePath);
	pw1.write(prettyPrinted1);
	pw1.close();
        
        //RecitationData
        StringWriter sw2 = new StringWriter();
	JsonWriter jsonWriter2 = writerFactory.createWriter(sw2);
	jsonWriter2.writeObject(recitationsJSO);
	jsonWriter2.close();
        
	OutputStream os2 = new FileOutputStream(recitationFilePath);
	JsonWriter jsonFileWriter2 = Json.createWriter(os2);
	jsonFileWriter2.writeObject(recitationsJSO);
	String prettyPrinted2 = sw2.toString();
	PrintWriter pw2 = new PrintWriter(recitationFilePath);
	pw2.write(prettyPrinted2);
	pw2.close();
        
        StringWriter sw3 = new StringWriter();
	JsonWriter jsonWriter3 = writerFactory.createWriter(sw3);
	jsonWriter3.writeObject(scheduleJSO);
	jsonWriter3.close();
        
	OutputStream os3 = new FileOutputStream(scheduleFilePath);
	JsonWriter jsonFileWriter3 = Json.createWriter(os3);
	jsonFileWriter3.writeObject(scheduleJSO);
	String prettyPrinted3 = sw3.toString();
	PrintWriter pw3 = new PrintWriter(scheduleFilePath);
	pw3.write(prettyPrinted3);
	pw3.close();
        
        //TeamsAndStudentsData
        StringWriter sw4 = new StringWriter();
	JsonWriter jsonWriter4 = writerFactory.createWriter(sw4);
	jsonWriter4.writeObject(teamsAndStudentsJSO);
	jsonWriter4.close();
        
	OutputStream os4 = new FileOutputStream(teamsAndStudentsFilePath);
	JsonWriter jsonFileWriter4 = Json.createWriter(os4);
	jsonFileWriter4.writeObject(teamsAndStudentsJSO);
	String prettyPrinted4 = sw4.toString();
	PrintWriter pw4 = new PrintWriter(teamsAndStudentsFilePath);
	pw4.write(prettyPrinted4);
	pw4.close();
        
        //ProjectssData
        StringWriter sw5 = new StringWriter();
	JsonWriter jsonWriter5 = writerFactory.createWriter(sw5);
	jsonWriter5.writeObject(workJSO);
	jsonWriter5.close();
        
	OutputStream os5 = new FileOutputStream(projectsFilePath);
	JsonWriter jsonFileWriter5 = Json.createWriter(os5);
	jsonFileWriter5.writeObject(workJSO);
	String prettyPrinted5 = sw5.toString();
	PrintWriter pw5 = new PrintWriter(projectsFilePath);
	pw5.write(prettyPrinted5);
	pw5.close();

        File sourceDirectory = new File(sourcePath);
        File selectedDirectory = new File(directoryPath);
        FileUtils.copyDirectory(sourceDirectory, selectedDirectory, true);
    }

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
