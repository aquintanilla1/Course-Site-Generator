/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import csg.data.CSGData;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;
import javafx.geometry.Insets;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.control.TabPane;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.BorderPane;
import jtps.jTPS;

/**
 *
 * @author Alvaro Quintanilla, ID: 110289649
 */
public class CSGWorkspace extends AppWorkspaceComponent {
    CSGApp app;
    TabPane tabs;
    
    Button redoButton;
    Button undoButton;
    
    jTPS jTPS;
    final KeyCodeCombination undo = new KeyCodeCombination(KeyCode.Z, KeyCodeCombination.CONTROL_DOWN);
    final KeyCodeCombination redo = new KeyCodeCombination(KeyCode.Y, KeyCodeCombination.CONTROL_DOWN);
    
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
        jTPS = new jTPS();
        cdWorkspace = new CourseDetailsTabBuilder(app);
        taWorkspace = new TADataTabBuilder(app);
        recitationWorkspace = new RecitationTabBuilder(app);
        scheduleWorkspace = new ScheduleTabBuilder(app);
        projectWorkspace = new ProjectTabBuilder(app);
        
        undoButton = app.getGUI().getUndoButton();
        redoButton = app.getGUI().getRedoButton();
        
        tabs = new TabPane();
        courseDetailsTab = cdWorkspace.getTab();
        taDataTab = taWorkspace.getTab();
        recitationTab = recitationWorkspace.getTab();
        scheduleTab = scheduleWorkspace.getScheduleTab();
        projectTab = projectWorkspace.getProjectTab();
        
        
        tabs.getTabs().add(courseDetailsTab);
        tabs.getTabs().add(taDataTab);
        tabs.getTabs().add(recitationTab);
        tabs.getTabs().add(scheduleTab);
        tabs.getTabs().add(projectTab);
        
        tabs.setTabMinWidth(265);
        tabs.setTabMaxWidth(265);
        
        
        for (Tab t: tabs.getTabs()) {
            t.setClosable(false);
        }

        workspace =  new BorderPane();
        ((BorderPane) workspace).setCenter(tabs);
        ((BorderPane) workspace).setPadding(new Insets(10,10,10,10));
        
        app.getGUI().getPrimaryScene().setOnKeyPressed(e -> {
            if (undo.match(e)) {
                jTPS.undoTransaction();
            }
            else if (redo.match(e)) {
                jTPS.doTransaction();
            }
        });
        
        undoButton.setOnAction(e -> {
            jTPS.undoTransaction();
        });
        
        redoButton.setOnAction(e -> {
            jTPS.doTransaction();
        });
    }
    
    public TabPane getTabs() {
        return tabs;
    }
    public CourseDetailsTabBuilder getCDTabBuilder() {
        return cdWorkspace;
    }
    
    public TADataTabBuilder getTATabBuilder() {
        return taWorkspace;
    }
    
    public RecitationTabBuilder getRecitationTabBuilder() {
        return recitationWorkspace;
    }
    
    public ScheduleTabBuilder getScheduleTabBuilder() {
        return scheduleWorkspace;
    }
    
    public ProjectTabBuilder getProjectTabBuilder() {
        return projectWorkspace;
    }
    
    public BorderPane getWorkspace() {
        return (BorderPane) workspace;
    }
    
    public jTPS getJTPS() {
        return jTPS;
    }
   

    @Override
    public void resetWorkspace() {
        taWorkspace.resetWorkspace();
    }

    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        CSGData csgData = (CSGData)dataComponent;
        cdWorkspace.reloadCourseInfo(csgData);
        cdWorkspace.reloadPageStyle(csgData);
        taWorkspace.reloadOfficeHoursGrid(csgData);
        scheduleWorkspace.reloadCalendarBounds(csgData);
    }
    
}
