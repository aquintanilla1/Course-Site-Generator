/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.files;

import csg.CSGApp;
import csg.data.CSGData;
import csg.data.Recitation;
import csg.data.ScheduleItem;
import csg.data.SitePage;
import csg.data.Student;
import csg.data.TeachingAssistant;
import csg.data.Team;
import djf.components.AppDataComponent;
import djf.components.AppFileComponent;
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
    
    static final String JSON_RECITATION_SECTION = "recitationSection";
    static final String JSON_RECITATION_INSTRUCTOR = "recitationInstructor";
    static final String JSON_DAY_TIME = "day/Time";
    static final String JSON_LOCATION = "location";
    static final String JSON_SUPERVISING_TA_ONE = "supervisingTA1";
    static final String JSON_SUPERVISING_TA_TWO = "supervisingTA2";
    static final String JSON_RECITATIONS = "recitations";
    
    static final String JSON_STARTING_MONDAY = "startingMonday";
    static final String JSON_ENDING_FRIDAY = "endingFriday";
    
    static final String JSON_TYPE = "type";
    static final String JSON_DATE = "date";
    static final String JSON_SCHEDULE_TIME = "scheduleTime";
    static final String JSON_SCHEDULE_TITLE = "scheduleTitle";
    static final String JSON_TOPIC = "topic";
    static final String JSON_SCHEDULE_LINK = "scheduleLink";
    static final String JSON_CRITERIA = "criteria";
    static final String JSON_SCHEDULE_ITEMS = "schedule_items";
    
    static final String JSON_TEAM_NAME = "teamName";
    static final String JSON_COLOR = "color";
    static final String JSON_TEXT_COLOR = "textColor";
    static final String JSON_TEAM_LINK = "teamLink";
    static final String JSON_TEAMS = "teams";
    
    static final String JSON_STUDENT_FIRST_NAME = "firstName";
    static final String JSON_STUDENT_LAST_NAME = "lastName";
    static final String JSON_STUDENT_TEAM = "studentTeam";
    static final String JSON_ROLE = "role";
    static final String JSON_STUDENTS = "students";
    
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
                    .add(JSON_SUPERVISING_TA_ONE, recitation.getTA1())
                    .add(JSON_SUPERVISING_TA_TWO, recitation.getTA2())
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
            dataManager.getCourseInfo().add(jsonCourseInfo.getString(JSON_SUBJECT));
            dataManager.getCourseInfo().add(jsonCourseInfo.getString(JSON_NUMBER));
            dataManager.getCourseInfo().add(jsonCourseInfo.getString(JSON_SEMESTER));
            dataManager.getCourseInfo().add(jsonCourseInfo.getString(JSON_YEAR));
            dataManager.getCourseInfo().add(jsonCourseInfo.getString(JSON_COURSE_TITLE));
            dataManager.getCourseInfo().add(jsonCourseInfo.getString(JSON_INSTRUCTOR_NAME));
            dataManager.getCourseInfo().add(jsonCourseInfo.getString(JSON_INSTRUCTOR_HOME));
            dataManager.getCourseInfo().add(jsonCourseInfo.getString(JSON_EXPORT_DIRECTORY));
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
        }
        
        dataManager.setStartMonday(json.getString(JSON_STARTING_MONDAY));
        dataManager.setEndFriday(json.getString(JSON_ENDING_FRIDAY));
        
        JsonArray jsonScheduleItemArray = json.getJsonArray(JSON_SCHEDULE_ITEMS);
        for (int i = 0; i < jsonScheduleItemArray.size(); i++) {
            JsonObject jsonScheduleItem = jsonScheduleItemArray.getJsonObject(i);
            String type = jsonScheduleItem.getString(JSON_TYPE);
            String time = jsonScheduleItem.getString(JSON_SCHEDULE_TIME);
            String title = jsonScheduleItem.getString(JSON_SCHEDULE_TITLE);
            String topic = jsonScheduleItem.getString(JSON_TOPIC);
            String scheduleLink = jsonScheduleItem.getString(JSON_SCHEDULE_LINK);
            String criteria = jsonScheduleItem.getString(JSON_CRITERIA);
            dataManager.addScheduleItem(type, title, time, title, topic, scheduleLink, criteria);
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
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void importData(AppDataComponent data, String filePath) throws IOException {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
