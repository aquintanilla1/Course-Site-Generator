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
import jtps.jTPS_Transaction;

/**
 *
 * @author Alvaro Quintanilla, ID:110289649
 */
public class RemoveRecitation_Transaction implements jTPS_Transaction {
    private Recitation recitation;
    private ObservableList<Recitation> recitations;
    private CSGData data;
    
    public RemoveRecitation_Transaction(Recitation r, ObservableList<Recitation> list, CSGData d) {
        recitation = r;
        recitations = list;
        data = d;
    }
 
    @Override
    public void doTransaction() {
        recitations.remove(recitation);
        data.markAsEdited();    
    }

    @Override
    public void undoTransaction() {
        recitations.add(recitation);
        data.markAsEdited();      
    }
}