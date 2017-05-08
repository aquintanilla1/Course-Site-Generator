/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transactions;

import csg.data.CSGData;
import csg.data.Student;
import java.util.Collections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import jtps.jTPS_Transaction;

/**
 *
 * @author Alvaro Quintanilla, ID:110289649
 */
public class EditStudent_Transaction implements jTPS_Transaction {
    private int position;
    private Student oldStudent;
    private Student newStudent;
    private ObservableList<Student> students;
    private CSGData data;
    private TableView studentTable;
    private String oldFirst, oldLast, oldTeam, oldRole;
    
    public EditStudent_Transaction(int pos, Student newS, ObservableList<Student> list, CSGData d, TableView table) {
        students = list;
        position = pos;
        newStudent = newS;
        data = d;
        studentTable = table;
        
        oldStudent = students.get(position);
        oldFirst = oldStudent.getFirstName();
        oldLast = oldStudent.getLastName();
        oldTeam = oldStudent.getTeam();
        oldRole = oldStudent.getRole();
    }
 
    @Override
    public void doTransaction() {
        data.getTeam(oldStudent.getTeam()).removeStudent(oldStudent);
        students.get(position).setFirstName(newStudent.getFirstName());
        students.get(position).setLastName(newStudent.getLastName());
        students.get(position).setTeam(newStudent.getTeam());
        students.get(position).setRole(newStudent.getRole());
        data.getTeam(newStudent.getTeam()).addStudent(newStudent);
        
        studentTable.refresh();
        data.markAsEdited();
    }

    @Override
    public void undoTransaction() {
        data.getTeam(newStudent.getTeam()).removeStudent(newStudent);
        students.get(position).setFirstName(oldFirst);
        students.get(position).setLastName(oldLast);
        students.get(position).setTeam(oldTeam);
        students.get(position).setRole(oldRole);
        data.getTeam(newStudent.getTeam()).addStudent(new Student(oldFirst, oldLast, oldTeam, oldRole));        
        studentTable.refresh();
        data.markAsEdited();
    }
}