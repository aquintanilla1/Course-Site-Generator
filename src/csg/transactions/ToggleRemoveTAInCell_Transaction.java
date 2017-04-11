
package csg.transactions;

import csg.data.CSGData;
import java.util.ArrayList;
import java.util.Arrays;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import jtps.jTPS;
import jtps.jTPS_Transaction;

/**
 *
 * @author Alvaro Quintanilla, ID: 110289649
 */
public class ToggleRemoveTAInCell_Transaction implements jTPS_Transaction {
    private String taName;
    private String cellKey;
    private CSGData data;
    private boolean isOnly;
    private boolean isFirst;
    private int position;
    
    public ToggleRemoveTAInCell_Transaction(String name, String key, CSGData currentData, boolean only, boolean first) {
        taName = name;
        cellKey = key;
        data = currentData;
        isOnly = only;
        isFirst = first;
    }
    
    public ToggleRemoveTAInCell_Transaction(String name, String key, CSGData currentData, boolean only, boolean first, int pos) {
        taName = name;
        cellKey = key;
        data = currentData;
        isOnly = only;
        isFirst = first;
        position = pos;
    }
    
    
    public void doTransaction() {
        StringProperty cellProp = data.getOfficeHours().get(cellKey);
        if (isOnly) {
            cellProp.setValue("");
        }
        else {
            String cellText = cellProp.getValue();
            if (isFirst) {
                int startIndex = cellText.indexOf("\n") + 1;
                cellText = cellText.substring(startIndex);
                cellProp.setValue(cellText);
            }
            else {
                ArrayList<String> listOfNames = new ArrayList<String>(Arrays.asList(cellText.split("\n")));
                listOfNames.remove(taName);
                cellText = "";
                for (String name: listOfNames) {
                    if (!name.equals(listOfNames.get(listOfNames.size() - 1))) {
                        cellText+=name + "\n";
                    }
                    else {
                        cellText+=name;
                    }
                }
                cellProp.setValue(cellText);
            }
        }
        data.markAsEdited();
    }
    
    public void undoTransaction() {
        StringProperty cellProp = data.getOfficeHours().get(cellKey);
        if (isOnly) {
            cellProp.setValue(taName);
        }
        else {
            String cellText = cellProp.getValue();
            if (isFirst) {
                cellText = taName + "\n" + cellText;
                cellProp.setValue(cellText);
            }
            else {
                ArrayList<String> listOfNames = new ArrayList<String>(Arrays.asList(cellText.split("\n")));
                listOfNames.add(position, taName);
                cellText = "";
                for (String name: listOfNames) {
                    if (!name.equals(listOfNames.get(listOfNames.size() - 1))) {
                        cellText+=name + "\n";
                    }
                    else {
                        cellText+=name;
                    }
                }
                cellProp.setValue(cellText);
            }
        }
        data.markAsEdited();
    } 
}
