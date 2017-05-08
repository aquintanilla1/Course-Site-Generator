/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transactions;

import csg.data.CSGData;
import csg.data.Student;
import csg.data.Team;
import java.util.ArrayList;
import java.util.Collections;
import javafx.collections.ObservableList;
import jtps.jTPS_Transaction;

/**
 *
 * @author Alvaro Quintanilla, ID:110289649
 */
public class RemoveTeam_Transaction implements jTPS_Transaction {
    private Team team;
    private ObservableList<Team> teams;
    private ArrayList<Student> students;
    private CSGData data;
    
    public RemoveTeam_Transaction(Team t, ObservableList<Team> list, CSGData d) {
        team = t;
        teams = list;
        data = d;
        students = team.getStudents();
    }
 
    @Override
    public void doTransaction() {
        teams.remove(team);
        data.removeStudentsInTeam(team);
        data.markAsEdited();    
    }

    @Override
    public void undoTransaction() {
        teams.add(team);
        data.getStudents().addAll(students);
        data.markAsEdited();      
    }
}