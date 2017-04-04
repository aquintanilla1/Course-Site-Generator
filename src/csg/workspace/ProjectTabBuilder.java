/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import javafx.scene.control.Button;
import javafx.scene.control.Tab;
import javafx.scene.layout.BorderPane;

/**
 *
 * @author Alvaro
 */
public class ProjectTabBuilder {
    Tab tab;
    Button button;
    
    public ProjectTabBuilder() {}
    
    public Tab buildProjectTab() {
        BorderPane pane = new BorderPane();
        button = new Button("proj");
        
        tab = new Tab("Project");
        
        pane.setCenter(button);
        tab.setContent(pane);
        
        return tab;
    }
    
}
