/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Alvaro
 */
public class CourseDetailsTabBuilder {
    CSGApp app;
    Tab tab;
    Button button;
    
    public CourseDetailsTabBuilder() {}
    
    public Tab buildCourseDetailsTab() {
        BorderPane pane = new BorderPane();
        button = new Button("cd");
        
        tab = new Tab("Course Details");
        
        pane.setCenter(button);
        tab.setContent(pane);
        
        return tab;
    }
    
}
