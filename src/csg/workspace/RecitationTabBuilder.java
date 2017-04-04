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
public class RecitationTabBuilder {
    Tab tab;
    Button button;
    
    public RecitationTabBuilder() {}
    
    public Tab buildRecitationTab() {
        BorderPane pane = new BorderPane();
        button = new Button("recitation");
        
        tab = new Tab("Recitation");
        
        pane.setCenter(button);
        tab.setContent(pane);
        
        return tab;
    }
    
    
    
}
