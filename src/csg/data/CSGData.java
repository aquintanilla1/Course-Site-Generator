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
    
    public CSGData(CSGApp initApp) {
        app = initApp;
        sitePages = FXCollections.observableArrayList();
    }
    
    public ObservableList getSitePages() {
        return sitePages;
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
