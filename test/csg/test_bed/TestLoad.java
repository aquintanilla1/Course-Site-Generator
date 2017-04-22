package csg.test_bed;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

import csg.data.CSGData;
import csg.files.CSGFiles;
import csg.files.TimeSlot;
import djf.components.AppDataComponent;
import java.io.IOException;
import java.util.ArrayList;
import org.junit.After;
import org.junit.AfterClass;
import org.junit.Before;
import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

/**
 *
 * @author Alvaro Quintanilla, ID: 110289649
 */
public class TestLoad {
    
    public TestLoad() {
    }
    
    @BeforeClass
    public static void setUpClass() {
    }
    
    @AfterClass
    public static void tearDownClass() {
    }
    
    @Before
    public void setUp() {
    }
    
    @After
    public void tearDown() {
    }

    // TODO add test methods here.
    // The methods must be annotated with annotation @Test. For example:
    //
     @Test
     public void testLoad() throws IOException {
        CSGData data = new CSGData();
        CSGFiles files = new CSGFiles();
         
        AppDataComponent dataComponent = data;
        files.loadData(dataComponent, "/Users/Alvaro/Documents/CourseSiteGenerator/work/SaveSiteTest.json");
        
        //Testing Course Info
        assertEquals("Subject", "CSE", data.getCourseInfo().get(0));
        assertEquals("Number", "219", data.getCourseInfo().get(1));
        assertEquals("Semester", "Fall", data.getCourseInfo().get(2));
        assertEquals("Year", "2017", data.getCourseInfo().get(3));
        assertEquals("Course Title", "Computer Science III", data.getCourseInfo().get(4));         assertEquals("Instructor Name", "Richard McKenna", data.getCourseInfo().get(5));
        assertEquals("Instructor Home", "http://www3.cs.stonybrook.edu/~richard/", data.getCourseInfo().get(6));
        assertEquals("Export Directory", "Users/Alvaro/Documents/Whatever", data.getCourseInfo().get(7));
         
        //Testing Site Pages
        assertTrue(data.getSitePages().get(0).getIsUsed());
        assertTrue(data.getSitePages().get(1).getIsUsed());
        assertTrue(data.getSitePages().get(2).getIsUsed());
        assertFalse(data.getSitePages().get(3).getIsUsed());
        assertFalse(data.getSitePages().get(4).getIsUsed());
        
        assertEquals("NavBar Title1", "Home", data.getSitePages().get(0).getNavbarTitle());
        assertEquals("NavBar Title2", "Syllabus", data.getSitePages().get(1).getNavbarTitle());
        assertEquals("NavBar Title3", "Schedule", data.getSitePages().get(2).getNavbarTitle());
        assertEquals("NavBar Title4", "HWs", data.getSitePages().get(3).getNavbarTitle());
        assertEquals("NavBar Title5", "Projects", data.getSitePages().get(4).getNavbarTitle());
        
        assertEquals("File Name1", "index.html", data.getSitePages().get(0).getFileName());
        assertEquals("File Name2", "syllabus.html", data.getSitePages().get(1).getFileName());
        assertEquals("File Name3", "schedule.html", data.getSitePages().get(2).getFileName());
        assertEquals("File Name4", "hws.html", data.getSitePages().get(3).getFileName());
        assertEquals("File Name5", "projects.html", data.getSitePages().get(4).getFileName());
        
        assertEquals("Script1", "HomeBuilder.js", data.getSitePages().get(0).getScript());
        assertEquals("Script2", "SyllabusBuilder.js", data.getSitePages().get(1).getScript());
        assertEquals("Script3", "ScheduleBuilder.js", data.getSitePages().get(2).getScript());
        assertEquals("Script4", "HWsBuilder.js", data.getSitePages().get(3).getScript());
        assertEquals("Script5", "ProjectBuilder.js", data.getSitePages().get(4).getScript());
        
        //Testing Page Style
        assertEquals("Image", "image.png", data.getImagePath());
        assertEquals("Left Footer Image", "leftFooter.jpg", data.getLeftFooterImagePath());
        assertEquals("Right Footer Image", "rightFooter.png", data.getRightFooterImagePath());
        assertEquals("Stylesheet", "test.css", data.getStylesheet());
        
        assertEquals("Start Hour", 10, data.getStartHour());
        assertEquals("End Hour", 20, data.getEndHour());
         
        //Testing TAs
        assertEquals("UndergradTAName", "Barack Obama", data.getTeachingAssistants().get(0).getName());
        assertEquals("UndergradTAEmail", "obama@whitehouse.gov", data.getTeachingAssistants().get(0).getEmail());
        assertTrue(data.getTeachingAssistants().get(0).getIsUndergrad());
        
        assertEquals("UndergradTAName", "Bill Gates", data.getTeachingAssistants().get(1).getName());
        assertEquals("UndergradTAEmail", "billGates@microsoft.com", data.getTeachingAssistants().get(1).getEmail());
        assertFalse(data.getTeachingAssistants().get(1).getIsUndergrad());
        
        //Testing Office Hours
	ArrayList<TimeSlot> officeHours = TimeSlot.buildOfficeHoursList(data);
        
        assertEquals("TAName", "Barack Obama", officeHours.get(0).getName());
        assertEquals("Day", "MONDAY", officeHours.get(0).getDay());
        assertEquals("Time", "11_00am", officeHours.get(0).getTime());
        
        assertEquals("TAName", "Bill Gates", officeHours.get(1).getName());
        assertEquals("Day", "WEDNESDAY", officeHours.get(1).getDay());
        assertEquals("Time", "12_00pm", officeHours.get(1).getTime());
        
        assertEquals("TAName", "Barack Obama", officeHours.get(2).getName());
        assertEquals("Day", "TUESDAY", officeHours.get(2).getDay());
        assertEquals("Time", "1_30pm", officeHours.get(2).getTime());
        
        assertEquals("TAName", "Bill Gates", officeHours.get(3).getName());
        assertEquals("Day", "TUESDAY", officeHours.get(3).getDay());
        assertEquals("Time", "1_30pm", officeHours.get(3).getTime());
        
        //Testing Recitations
        assertEquals("Recitation Section", "R01", data.getRecitations().get(0).getSection());
        assertEquals("Recitation Instructor", "McKenna", data.getRecitations().get(0).getInstructor());
        assertEquals("Day and Time", "Tues 5:30-6:23pm", data.getRecitations().get(0).getDayTime());
        assertEquals("Location", "Old CS 2114", data.getRecitations().get(0).getLocation());
        assertEquals("SupervisingTA1", "Barack Obama", data.getRecitations().get(0).getTa1());
        assertEquals("SupervisingTA2", "Bill Gates", data.getRecitations().get(0).getTa2());
        
        //Testing Calendar Bounds
        assertEquals("Start Monday", "02-25-2017", data.getStartMonday());
        assertEquals("End Friday", "05-16-2017", data.getEndFriday());
        
        //Testing ScheduleItems
        assertEquals("Schedule Type", "Holiday", data.getScheduleItems().get(0).getType());
        //Add Schedule Date here
        assertEquals("Schedule Time", "10:00am", data.getScheduleItems().get(0).getTime());
        assertEquals("Schedule Title", "Snowday", data.getScheduleItems().get(0).getTitle());
        assertEquals("Schedule Topic", "", data.getScheduleItems().get(0).getTopic());
        assertEquals("Schedule Link", "cnn.com", data.getScheduleItems().get(0).getLink());
        assertEquals("Schedule Criteria", "Must Snow", data.getScheduleItems().get(0).getCriteria());
        
        //Testing Teams
        assertEquals("Team Name", "The Aristocrats", data.getTeams().get(0).getName());
        assertEquals("Team Color", "FFFFFF", data.getTeams().get(0).getColor());
        assertEquals("Team Text Color", "999999", data.getTeams().get(0).getTextColor());
        assertEquals("Team Link", "aristocrats.com", data.getTeams().get(0).getLink());
        
        //Testing Students
        assertEquals("First Name", "Pope", data.getStudents().get(0).getFirstName());
        assertEquals("Last Name", "Francis", data.getStudents().get(0).getLastName());
        assertEquals("Team", "The Aristocrats", data.getStudents().get(0).getTeam());
        assertEquals("Role", "Lead Designer", data.getStudents().get(0).getRole());
    }
}
