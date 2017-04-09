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
 * @author Alvaro
 */
public class Recitation {
    private ArrayList<String> attributes; 
    private final StringProperty section;
    private final StringProperty instructor;
    private final StringProperty dayTime;
    private final StringProperty location;
    private final StringProperty ta1;
    private final StringProperty ta2;
    
    public Recitation(ArrayList<String> strings) {
        attributes = strings;
        section = new SimpleStringProperty(attributes.get(0));
        instructor = new SimpleStringProperty(attributes.get(1));
        dayTime = new SimpleStringProperty(attributes.get(2));
        location = new SimpleStringProperty(attributes.get(3));
        ta1 = new SimpleStringProperty(attributes.get(4));
        ta2 = new SimpleStringProperty(attributes.get(5));
    }
    
    public String getSection() {
        return section.get();
    }
    
    public String getInstructor() {
        return instructor.get();
    }
    
    public String getDayTime() {
        return dayTime.get();
    }
    
    public String getLocation() {
        return location.get();
    }
    
    public String getTA1() {
        return ta1.get();
    }    
}
