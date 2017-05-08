package csg.transactions;

import java.util.Collections;
import javafx.collections.ObservableList;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import csg.data.*;

/**
 *
 * @author Alvaro Quintanilla, ID: 110289649
 */
public class AddStudent_Transaction implements jTPS_Transaction {
    private Student student;
    private ObservableList<Student> students;
    private CSGData data;
    
    public AddStudent_Transaction(Student s, ObservableList<Student> list, CSGData d) {
        student = s;
        students = list;
        data = d;
    }
    
    @Override
    public void doTransaction() {
        students.add(student);
        data.getTeam(student.getTeam()).addStudent(student);
        data.markAsEdited();
    }

    @Override
    public void undoTransaction() {
        students.remove(student);
        data.getTeam(student.getTeam()).removeStudent(student);
        data.markAsEdited();
    }
}