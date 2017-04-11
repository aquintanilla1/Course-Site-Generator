package csg.transactions;

import csg.data.CSGData;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import jtps.jTPS;
import jtps.jTPS_Transaction;

/**
 *
 * @author Alvaro Quintanilla, ID: 110289649
 */
public class ToggleAddTAInCell_Transaction implements jTPS_Transaction {
    private String taName;
    private String cellKey;
    private CSGData data;
    private boolean isFirst;
    
    public ToggleAddTAInCell_Transaction(String name, CSGData currentData, String key, boolean first) {
        taName = name;
        data = currentData;
        cellKey = key;
        isFirst = first;
        
    }
    
    public void doTransaction() {
        StringProperty cellProp = data.getOfficeHours().get(cellKey);
        if (isFirst) {
            cellProp.setValue(cellProp.getValue() + taName);
        }
        else {
            cellProp.setValue(cellProp.getValue() + "\n" + taName);
        }
        data.markAsEdited();
    }
    
    public void undoTransaction() {
        StringProperty cellProp = data.getOfficeHours().get(cellKey);
        if (isFirst) {
            cellProp.setValue("");
        }
        else {
            cellProp.setValue(cellProp.getValue().substring(0, (cellProp.getValue().length() - taName.length() - 1)));
        }
        data.markAsEdited();
    }
    
}
