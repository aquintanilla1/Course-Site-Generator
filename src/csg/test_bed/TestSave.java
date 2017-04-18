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
import java.util.ArrayList;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import properties_manager.PropertiesManager;

/**
 *
 * @author Alvaro Quintanilla, ID: 110289649
 */
public class TestSave {
    private CSGData data;
    
    public TestSave(CSGData data) {
        this.data = data;
        
        makeTestCourseInfo();
        makeTestSitePages();
        makeTestPageStyle();
        makeTestStartAndEndHours();
        makeTestTeachingAssistants();
        makeTestTimeSlots();
        makeTestRecitations();
        makeTestScheduleItems();
        makeTestTeamsAndStudents();        
    }
    
    private void makeTestCourseInfo() {
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
    
    private void makeTestSitePages() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ObservableList<SitePage> pages = FXCollections.observableArrayList();
        String home = props.getProperty(CSGProp.HOME);
        String syllabus = props.getProperty(CSGProp.SYLLABUS);
        String schedule = props.getProperty(CSGProp.SCHEDULE);
        String homework = props.getProperty(CSGProp.HW);
        String projects = props.getProperty(CSGProp.PROJECTS);
        pages.add(new SitePage(true, home, "index.html", "HomeBuilder.js"));
        pages.add(new SitePage(true, syllabus, "syllabus.html", "SyllabusBuilder.js"));
        pages.add(new SitePage(true, schedule, "schedule.html", "ScheduleBuilder.js"));
        pages.add(new SitePage(false, homework, "hws.html", "HWsBuilder.js"));
        pages.add(new SitePage(false, projects, "projects.html", "ProjectBuilder.js")); 
        
        data.setSitePages(pages);
    }
    
    private void makeTestPageStyle() {
        data.setImage("testimage.png");
        data.setLeftFooter("testleftfooter.png");
        data.setRightFooter("testrightfooter.png");
        data.setStylesheet("\\test.css");
    }
    
    private void makeTestTeachingAssistants() {
        data.getTeachingAssistants().add(new TeachingAssistant("Barack Obama", "obama@whitehouse.gov", true));
        data.getTeachingAssistants().add(new TeachingAssistant("Bill Gates", "billGates@microsoft.com", true));
        data.getTeachingAssistants().add(new TeachingAssistant("John Doe", "johnDoe@gmail.com", false));
    }
    
    private void makeTestTimeSlots() {
        data.addOfficeHoursReservation("MONDAY", "10_00am", "Barack Obama");
        data.addOfficeHoursReservation("MONDAY", "12_00pm", "Barack Obama");
        data.addOfficeHoursReservation("MONDAY", "2_00pm", "Barack Obama");
        data.addOfficeHoursReservation("WEDNESDAY", "11_00am", "Bill Gates");
        data.addOfficeHoursReservation("FRIDAY", "3_00pm", "John Doe");
    }
    
    private void makeTestStartAndEndHours() {
        data.setStartHour("9:00am");
        data.setEndHour("8:00pm");
    }
    
    private void makeTestRecitations() {
        ArrayList<String> recitation1Details = new ArrayList<>();
        recitation1Details.add("RO1");
        recitation1Details.add("McKenna");
        recitation1Details.add("Tues 5:30-6:23pm");
        recitation1Details.add("Old CS 2114");
        recitation1Details.add("Barack Obama");
        recitation1Details.add("Bill Gates");
        
        ArrayList<String> recitation2Details = new ArrayList<>();
        recitation2Details.add("R66");
        recitation2Details.add("Banerjee");
        recitation2Details.add("Thurs 1:00-1:53pm");
        recitation2Details.add("Old CS 2114");
        recitation2Details.add("Barack Obama");
        recitation2Details.add("John Doe");
        
        Recitation recitation1 = new Recitation(recitation1Details);
        Recitation recitation2 = new Recitation(recitation2Details);
        
        data.getRecitations().add(recitation1);
        data.getRecitations().add(recitation2);
    }
    
    private void makeTestScheduleItems() {
        ScheduleItem item1 = new ScheduleItem("Holiday", "03/25/2017","10:00am", "Snowday", "", "cnn.com", "Must Snow");
        ScheduleItem item2 = new ScheduleItem("Exam", "04/10/2017","5:00pm", "Midterm 2", "Chapters 3-55", "bbc.com", "Take Exam");
        
        data.getScheduleItems().add(item1);
        data.getScheduleItems().add(item2);
    }
    
    private void makeTestTeamsAndStudents() {
        Team team1 = new Team("The Aristocrats", "FFFFFF", "999999", "aristocrats.com");
        Team team2 = new Team("Umbrellas", "AA00BB", "000000", "umbrellas.com");
        
        data.getTeams().add(team1);
        data.getTeams().add(team2);
        
        Student student1 = new Student("Pope", "Francis", "The Aristocrats", "Lead Designer");
        Student student2 = new Student("Mike", "Pence", "Umbrellas", "Lead Programmer");
        Student student3 = new Student("Mel", "Gibson", "The Aristocrats", "Data Designer");
        
        data.getStudents().add(student1);
        data.getStudents().add(student2);
        data.getStudents().add(student3);
    }
    
    
    
}
