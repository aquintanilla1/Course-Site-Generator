/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.test_bed;

import csg.CSGApp;
import csg.CSGProp;
import csg.data.CSGData;
import csg.data.Recitation;
import csg.data.ScheduleItem;
import csg.data.SitePage;
import csg.data.Student;
import csg.data.TeachingAssistant;
import csg.data.Team;
import csg.files.CSGFiles;
import csg.workspace.TADataTabBuilder;
import djf.components.AppDataComponent;
import java.io.IOException;
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import properties_manager.PropertiesManager;
import javafx.scene.control.Label;

/**
 *
 * @author Alvaro Quintanilla, ID: 110289649
 */
public class TestSave {
    
    public static void main(String [] args) throws IOException {
        CSGData data = new CSGData();
        CSGFiles files = new CSGFiles();
        
        makeTestCourseInfo(data);
        makeTestSitePages(data);
        makeTestPageStyle(data);
        makeTestStartAndEndHours(data);
        makeTestTeachingAssistants(data);
        makeTestTimeSlots(data);
        makeTestRecitations(data);
        makeTestCalendarBounds(data);
        makeTestScheduleItems(data);
        makeTestTeamsAndStudents(data); 
        
        AppDataComponent dataComponent = data;
        files.saveData(dataComponent, "/Users/Alvaro/Documents/CourseSiteGenerator/work/SaveSiteTest.json");
        System.out.println("Test");
    }
    
    private static void makeTestCourseInfo(CSGData data) {
        ObservableList<String> details = FXCollections.observableArrayList();
        details.add("CSE");
        details.add("219");
        details.add("Fall");
        details.add("2017");
        details.add("Computer Science III");
        details.add("Richard McKenna");
        details.add("http://www3.cs.stonybrook.edu/~richard/");
        details.add("Users/Alvaro/Documents/Whatever");
        
        data.setCourseInfo(details);
    }
    
    private static void makeTestSitePages(CSGData data) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ObservableList<SitePage> pages = FXCollections.observableArrayList();
        pages.add(new SitePage(true, "Home", "index.html", "HomeBuilder.js"));
        pages.add(new SitePage(true, "Syllabus", "syllabus.html", "SyllabusBuilder.js"));
        pages.add(new SitePage(true, "Schedule", "schedule.html", "ScheduleBuilder.js"));
        pages.add(new SitePage(false, "HWs", "hws.html", "HWsBuilder.js"));
        pages.add(new SitePage(false, "Projects", "projects.html", "ProjectBuilder.js")); 
        
        data.setTemplateDirectory("Users/Alvaro/Documents/TestDirectory");
        data.setSitePages(pages);
    }
    
    private static void makeTestPageStyle(CSGData data) {
        data.setImage("image.png");
        data.setLeftFooter("leftFooter.jpg");
        data.setRightFooter("rightFooter.png");
        data.setStylesheet("test.css");
    }
    
    private static void makeTestTeachingAssistants(CSGData data) {
        data.getTeachingAssistants().add(new TeachingAssistant("Barack Obama", "obama@whitehouse.gov", true));
        data.getTeachingAssistants().add(new TeachingAssistant("Bill Gates", "billGates@microsoft.com", false));
    }
    
    private static void makeTestTimeSlots(CSGData data) {
        data.initTestOfficeHours();
        data.getOfficeHours().get("2_3").setValue("Barack Obama");
        data.getOfficeHours().get("3_8").setValue("Barack Obama\nBill Gates");
        data.getOfficeHours().get("4_5").setValue("Bill Gates");
    }
    
    private static void makeTestStartAndEndHours(CSGData data) {
        data.setStartHour("10:00am");
        data.setEndHour("8:00pm");
    }
    
    private static void makeTestRecitations(CSGData data) {
        ArrayList<String> recitation1Details = new ArrayList<>();
        recitation1Details.add("R01");
        recitation1Details.add("McKenna");
        recitation1Details.add("Tues 5:30-6:23pm");
        recitation1Details.add("Old CS 2114");
        recitation1Details.add("Barack Obama");
        recitation1Details.add("Bill Gates");

        Recitation recitation1 = new Recitation(recitation1Details);
        
        data.getRecitations().add(recitation1);
    }
    private static void makeTestCalendarBounds(CSGData data) {
        data.setStartMonday("02-25-2017");
        data.setEndFriday("05-16-2017");
    }
    
    private static void makeTestScheduleItems(CSGData data) {
        ScheduleItem item1 = new ScheduleItem("Holiday", "03/25/2017","10:00am", "Snowday", "", "cnn.com", "Must Snow");
        
        data.getScheduleItems().add(item1);
    }
    
    private static void makeTestTeamsAndStudents(CSGData data) {
        Team team1 = new Team("The Aristocrats", "FFFFFF", "999999", "aristocrats.com");
        
        data.getTeams().add(team1);
        
        Student student1 = new Student("Pope", "Francis", "The Aristocrats", "Lead Designer");
        data.getStudents().add(student1);
    }
}
