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
public class AddTA_Transaction implements jTPS_Transaction {
    private TeachingAssistant ta;
    private ObservableList<TeachingAssistant> teachingAssistants;
    private CSGData data;
    
    public AddTA_Transaction(TeachingAssistant newTA, ObservableList<TeachingAssistant> taList, CSGData currentData) {
        ta = newTA;
        teachingAssistants = taList;
        data = currentData;
    }
    
    @Override
    public void doTransaction() {
        teachingAssistants.add(ta);
        Collections.sort(teachingAssistants);
        data.markAsEdited();
    }

    @Override
    public void undoTransaction() {
        teachingAssistants.remove(ta);
        data.markAsEdited();
    }
}
