/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableBooleanValue;

/**
 *
 * @author Alvaro Quintanilla, ID: 110289649
 */
public class SitePage {
    private final BooleanProperty isUsed;
    private final StringProperty navbarTitle;
    private final StringProperty fileName;
    private final StringProperty script;
    
    public SitePage(boolean isUsed, String navbarTitle, String fileName, String script) {
        this.isUsed = new SimpleBooleanProperty(isUsed);
        this.navbarTitle = new SimpleStringProperty(navbarTitle);
        this.fileName = new SimpleStringProperty(fileName);
        this.script = new SimpleStringProperty(script);
    }
    
    public ObservableBooleanValue isUsed() {
        return isUsed;
    }
    
    public boolean getIsUsed() {
        return isUsed.get();
    }
    
    public String getNavbarTitle() {
        return navbarTitle.get();
    }
    
    public String getFileName() {
        return fileName.get();
    }
    
    public String getScript() {
        return script.get();
    }
    
    public void setUsed(Boolean value) {
        isUsed.set(value);
    }
    
    public String toString() {
        return isUsed.get() + " " + navbarTitle.get() + " " + fileName.get() + " " + script.get();
    }
}
