/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import java.util.ArrayList;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Alvaro Quintanilla, ID: 110289649
 */
public class Team {
    private final StringProperty name;
    private final StringProperty color;
    private final StringProperty textColor;
    private final StringProperty link;
    private final ArrayList<Student> students;
    
    public Team(String name, String color, String textColor, String link) {
        this.name = new SimpleStringProperty(name);
        this.color = new SimpleStringProperty(color);
        this.textColor = new SimpleStringProperty(textColor);
        this.link = new SimpleStringProperty(link);
        this.students = new ArrayList<>();
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
    
    public String getRed() {
        String hex = color.get().substring(0, 2);
        int decimal = Integer.parseInt(hex, 16);
        
        return String.valueOf(decimal);
    }
    
    public String getGreen() {
        String hex = color.get().substring(2, 4);
        int decimal = Integer.parseInt(hex, 16);
        
        return String.valueOf(decimal);
    }
    
    public String getBlue() {
        String hex = color.get().substring(4);
        int decimal = Integer.parseInt(hex, 16);
        
        return String.valueOf(decimal);
    }
}
