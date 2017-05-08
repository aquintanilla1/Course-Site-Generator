/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transactions;

import csg.data.CSGData;
import csg.data.Recitation;
import java.util.Collections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import jtps.jTPS_Transaction;

/**
 *
 * @author Alvaro Quintanilla, ID:110289649
 */
public class EditRecitation_Transaction implements jTPS_Transaction {
    private int position;
    private Recitation oldRecitation;
    private Recitation newRecitation;
    private ObservableList<Recitation> recitations;
    private CSGData data;
    private TableView recitationTable;
    private String oldSection, oldInstructor, oldDayTime, oldLocation, oldTa1, oldTa2;
    
    public EditRecitation_Transaction(int pos, Recitation newR, ObservableList<Recitation> list, CSGData d, TableView table) {
        recitations = list;
        position = pos;
        newRecitation = newR;
        data = d;
        recitationTable = table;
        
        oldRecitation = recitations.get(position);
        oldSection = oldRecitation.getSection();
        oldInstructor = oldRecitation.getInstructor();
        oldDayTime = oldRecitation.getDayTime();
        oldLocation = oldRecitation.getLocation();
        oldTa1 = oldRecitation.getTa1();
        oldTa2 = oldRecitation.getTa2();
        
        
        
    }
 
    @Override
    public void doTransaction() {
        recitations.get(position).setSection(newRecitation.getSection());
        recitations.get(position).setInstructor(newRecitation.getInstructor());
        recitations.get(position).setDayTime(newRecitation.getDayTime());
        recitations.get(position).setLocation(newRecitation.getLocation());
        recitations.get(position).setTa1(newRecitation.getTa1());
        recitations.get(position).setTa2(newRecitation.getTa2());

        recitationTable.refresh();
        data.markAsEdited();
    }

    @Override
    public void undoTransaction() {
        recitations.get(position).setSection(oldSection);
        recitations.get(position).setInstructor(oldInstructor);
        recitations.get(position).setDayTime(oldDayTime);
        recitations.get(position).setLocation(oldLocation);
        recitations.get(position).setTa1(oldTa1);
        recitations.get(position).setTa2(oldTa2);

        recitationTable.refresh();   
        data.markAsEdited();
    }
}

