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
    
    public ArrayList<Student> getStudents() {
        return students;
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
    
    public void setName(String newName) {
        name.set(newName);
    }
    
    public void setColor(String newColor) {
        color.set(newColor);
    }
    
    public void setTextColor(String newTextColor) {
        textColor.set(newTextColor);
    }
    
    public void setLink(String newLink) {
        link.set(newLink);
    }
    
    public String toString() {
        return name.getValue();
    }
    
    public void addStudent(Student s) {
        students.add(s);
    }
    
    public void removeStudent(Student deletedStudent) {
        for (Student s: students) {
            if (s.getFirstName().equals(deletedStudent.getFirstName()) && s.getLastName().equals(deletedStudent.getLastName())) {
                students.remove(s);
                break;
            }
        }
    }
}
