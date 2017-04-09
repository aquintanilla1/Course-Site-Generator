/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Alvaro
 */
public class CSGWorkspace extends AppWorkspaceComponent {
    CSGApp app;
    TabPane tabs;
    
    Tab courseDetailsTab;
    Tab taDataTab;
    Tab recitationTab;
    Tab scheduleTab;
    Tab projectTab;
    
    CourseDetailsTabBuilder cdWorkspace;
    TADataTabBuilder taWorkspace;
    RecitationTabBuilder recitationWorkspace;
    ScheduleTabBuilder scheduleWorkspace;
    ProjectTabBuilder projectWorkspace;
    
    
    public CSGWorkspace(CSGApp initApp) {
        app = initApp;
        cdWorkspace = new CourseDetailsTabBuilder(app);
        taWorkspace = new TADataTabBuilder();
        recitationWorkspace = new RecitationTabBuilder(app);
        scheduleWorkspace = new ScheduleTabBuilder();
        projectWorkspace = new ProjectTabBuilder();
        
        tabs = new TabPane();
        courseDetailsTab = cdWorkspace.getTab();
        taDataTab = taWorkspace.buildTADataTab();
        recitationTab = recitationWorkspace.getTab();
        scheduleTab = scheduleWorkspace.buildScheduleTab();
        projectTab = projectWorkspace.buildProjectTab();
        
        
        tabs.getTabs().add(courseDetailsTab);
        tabs.getTabs().add(taDataTab);
        tabs.getTabs().add(recitationTab);
        tabs.getTabs().add(scheduleTab);
        tabs.getTabs().add(projectTab);
        
        
        for (Tab t: tabs.getTabs()) {
            t.setClosable(false);
        }

        workspace =  new BorderPane();
        ((BorderPane) workspace).setCenter(tabs);
        ((BorderPane) workspace).setPadding(new Insets(10,10,10,10));
    }
    
    public TabPane getTabs() {
        return tabs;
    }
    public CourseDetailsTabBuilder getCDTabBuilder() {
        return cdWorkspace;
    }
    
    public RecitationTabBuilder getRecitationTabBuilder() {
        return recitationWorkspace;
    }

    @Override
    public void resetWorkspace() {
    }

    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
    }
    
}
