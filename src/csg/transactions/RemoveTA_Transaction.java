
package csg.transactions;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import jtps.jTPS_Transaction;
import csg.data.*;


/**
 *
 * @author Alvaro Quintanilla, ID: 110289649
 */
public class RemoveTA_Transaction implements jTPS_Transaction {
    private TeachingAssistant ta;
    private ObservableList<TeachingAssistant> teachingAssistants;
    private CSGData data;
    private HashMap<String, String> originalTAs;
    
    public RemoveTA_Transaction(TeachingAssistant newTA, ObservableList<TeachingAssistant> taList,
        CSGData currentData, HashMap<String, StringProperty> hours) {
        ta = newTA;
        teachingAssistants = taList;
        data = currentData;
        originalTAs = new HashMap();
        setOriginalTAs();
    }
    

    @Override
    public void doTransaction() {  
        teachingAssistants.remove(ta);
        
        if (teachingAssistants.isEmpty()) {
            data.clearTAInput();
        }
        
        for (StringProperty cellProp: data.getOfficeHours().values()) {
            String cellText = cellProp.getValue();
            ArrayList<String> namesInCell = new ArrayList<String>(Arrays.asList(cellText.split("\n")));
            if (namesInCell.contains(ta.getName())) {
                namesInCell.remove(ta.getName());
            }
            cellText = "";
            
            for (String name: namesInCell) {
                if (!name.equals(namesInCell.get(namesInCell.size() - 1))) {
                    cellText+=name + "\n";
                }
                else {
                    cellText+=name;
                }
            }
            cellProp.set(cellText);
            data.markAsEdited();
        }
    }

    @Override
    public void undoTransaction() {
        teachingAssistants.add(ta);
        Collections.sort(teachingAssistants);  
               
        for (int col = 2; col < 7; col++) {
            for (int row = 1; row < data.getNumRows(); row++) {
                String cellKey = data.getCellKey(col, row);
                StringProperty cellProp = data.getOfficeHours().get(cellKey);
                cellProp.set(originalTAs.get(cellKey));
            }
        }
        data.markAsEdited();
    }
    
    private void setOriginalTAs() {
        for (int col = 2; col < 7; col++) {
            for (int row = 1; row < data.getNumRows(); row++) {
                String cellKey = data.getCellKey(col, row);
                StringProperty cellProp = data.getOfficeHours().get(cellKey);
                String oldTAs = cellProp.getValue();
                originalTAs.put(cellKey, oldTAs);
            }
        }    
    }
}
