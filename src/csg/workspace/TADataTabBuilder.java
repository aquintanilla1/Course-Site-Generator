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
public class TADataTabBuilder {
    Tab tab;
    Button button;
    
    public TADataTabBuilder() {
        BorderPane pane = new BorderPane();
        button = new Button("ta");
        
        tab = new Tab("TA Data");
        
        pane.setCenter(button);
        tab.setContent(pane);
    }
    
    public Tab buildTADataTab() {
        
        
        return tab;
    }
    
    
}
