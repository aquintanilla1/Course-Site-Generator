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
public class Student {
    private final StringProperty firstName;
    private final StringProperty lastName;
    private final StringProperty team;
    private final StringProperty role;
    
    public Student(String firstName, String lastName, String team, String role) {
        this.firstName = new SimpleStringProperty(firstName);
        this.lastName = new SimpleStringProperty(lastName);
        this.team = new SimpleStringProperty(team);
        this.role = new SimpleStringProperty(role);
    }
    
    public String getFirstName() {
        return firstName.get();
    }
    
    public String getLastName() {
        return lastName.get();
    }
    
    public String getTeam() {
        return team.get();
    }
    
    public String getRole() {
        return role.get();
    }
}

