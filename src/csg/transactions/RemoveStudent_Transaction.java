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
public class RemoveStudent_Transaction implements jTPS_Transaction {
    private Student student;
    private ObservableList<Student> students;
    private CSGData data;
    
    public RemoveStudent_Transaction(Student s, ObservableList<Student> list, CSGData d) {
        student = s;
        students = list;
        data = d;
    }
 
    @Override
    public void doTransaction() {
        students.remove(student);
        data.getTeam(student.getTeam()).removeStudent(student);
        data.markAsEdited();    
    }

    @Override
    public void undoTransaction() {
        students.add(student);
        data.getTeam(student.getTeam()).addStudent(student);
        data.markAsEdited();      
    }
}