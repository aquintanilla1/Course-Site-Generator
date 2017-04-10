/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGApp;
import djf.components.AppDataComponent;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

/**
 *
 * @author Alvaro
 */
public class CSGData implements AppDataComponent {
    CSGApp app;
    ObservableList<SitePage> sitePages;
    ObservableList<Recitation> recitations;
    ObservableList<ScheduleItem> scheduleItems;
    ObservableList<Team> teams;
    ObservableList<Student> students;

    
    public CSGData(CSGApp initApp) {
        app = initApp;
        sitePages = FXCollections.observableArrayList();
    }
    
    public ObservableList getSitePages() {
        return sitePages;
    }

    public ObservableList getRecitations() {
        return recitations;
    }
    
    public ObservableList getScheduleItems() {
        return scheduleItems;
    }
    
    public ObservableList getTeams() {
        return teams;
    }
    
    public ObservableList getStudents() {
        return students;
    }
    
    
    
    @Override
    public void resetData() {
        System.out.println("clear");
    }
    
    public ObservableList makeSitePages(ObservableList<SitePage> pages) {
        pages.add(new SitePage(true, "Home", "index.html", "HomeBuilder.js"));
        pages.add(new SitePage(true, "Syllabus", "syllabus.html", "SyllabusBuilder.js"));
        pages.add(new SitePage(true, "Schedule", "schedule.html", "ScheduleBuilder.js"));
        pages.add(new SitePage(true, "HWs", "hws.html", "HWsBuilder.js"));
        pages.add(new SitePage(true, "Projects", "projects.html", "ProjectBuilder.js"));  
        
        return pages;
    }
    
}
