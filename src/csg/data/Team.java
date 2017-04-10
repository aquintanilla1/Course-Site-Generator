/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Alvaro
 */
public class Team {
    private final StringProperty name;
    private final StringProperty color;
    private final StringProperty textColor;
    private final StringProperty link;
    
    public Team(String name, String color, String textColor, String link) {
        this.name = new SimpleStringProperty(name);
        this.color = new SimpleStringProperty(color);
        this.textColor = new SimpleStringProperty(textColor);
        this.link = new SimpleStringProperty(link);
    }
    
    public String getName() {
        return name.get();
    }
    
    public String getColor() {
        return color.get();
    }
    
    public String getTextColor() {
        return textColor.get();
    }
    
    public String getLink() {
        return link.get();
    }
}
