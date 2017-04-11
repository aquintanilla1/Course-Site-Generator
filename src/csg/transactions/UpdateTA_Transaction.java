
package csg.transactions;

import csg.data.CSGData;
import csg.data.TeachingAssistant;
import java.util.Collections;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import jtps.jTPS_Transaction;

/**
 *
 * @author Alvaro Quintanilla, ID: 110289649
 */
public class UpdateTA_Transaction implements jTPS_Transaction {
    private TeachingAssistant oldTA;
    private TeachingAssistant newTA;
    private ObservableList<TeachingAssistant> taList;
    private HashMap<String, String> originalTAs;
    private TableView taTable;
    private CSGData data;
    private String oldName;
    private String oldEmail;
    private String newName;
    private String newEmail;
    
    
    
    public UpdateTA_Transaction(TeachingAssistant oldTA, TeachingAssistant newTA, 
            ObservableList<TeachingAssistant> list, CSGData currentData, TableView table) {
        this.oldTA = oldTA;
        this.newTA = newTA;
        taList = list;
        data = currentData;
        taTable = table;
        
        oldName = oldTA.getName();
        oldEmail = oldTA.getEmail();
        newName = newTA.getName();
        newEmail = newTA.getEmail();
        originalTAs = new HashMap();
    }
         

    @Override
    public void doTransaction() {
        for (TeachingAssistant ta: taList) {
            if (ta.getName().equals(oldName) || ta.getEmail().equals(oldEmail)) {
                ta.setName(newName);
                ta.setEmail(newEmail);
                break;
            }
        }
        taTable.refresh();
        Collections.sort(taList);
        
        if (!newName.equals(oldName)) {
            for (int col = 2; col < 7; col++) {
                for (int row = 1; row < data.getNumRows(); row++) {
                    String cellKey = data.getCellKey(col, row);
                    StringProperty cellProp = data.getOfficeHours().get(cellKey);
                    String cellText = cellProp.getValue();
                    if (cellText.contains(oldName)) {
                      cellProp.set(cellText.replace(oldName, newName));
                    }
                }
            }
        }
        data.markAsEdited();
    }

    @Override
    public void undoTransaction() {
        for (TeachingAssistant ta: taList) {
            if (ta.getName().equals(newName) || ta.getEmail().equals(newEmail)) {
                ta.setName(oldName);
                ta.setEmail(oldEmail);
                break;
            }            
        }
        
        taTable.refresh();
        Collections.sort(taList);
        
        if (!oldName.equals(newName)) {
            for (int col = 2; col < 7; col++) {
                for (int row = 1; row < data.getNumRows(); row++) {
                    String cellKey = data.getCellKey(col, row);
                    StringProperty cellProp = data.getOfficeHours().get(cellKey);
                    String cellText = cellProp.getValue();
                    if (cellText.contains(newName)) {
                      cellProp.set(cellText.replace(newName, oldName));
                    }
                }
            }
        }
        data.markAsEdited();
    }
}
