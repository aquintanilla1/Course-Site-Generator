
package csg.transactions;

import java.util.Collections;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import jtps.jTPS_Transaction;
import csg.data.CSGData;
import csg.workspace.CSGWorkspace;
/**
 *
 * @author Alvaro Quintanilla, ID: 110289649
 */
public class ModifyOfficeHours_Transaction implements jTPS_Transaction  {
    private CSGData data;
    private CSGWorkspace workspace;
    private String newStartTime;
    private String newEndTime;
    private String oldStartTime;
    private String oldEndTime;
    private ObservableList<String> times;
    private HashMap<String, StringProperty> oldOfficeHours;
    private int oldNumOfRows;
    
    public ModifyOfficeHours_Transaction(CSGData data, CSGWorkspace workspace, String newStart, String newEnd) {
        this.data = data;
        this.workspace = workspace;
        newStartTime = newStart;
        newEndTime = newEnd;
        
        oldStartTime = data.getCellTextProperty(0, 1).get();
        oldEndTime = data.getCellTextProperty(1, (data.getNumRows() - 1)).get();
        times = data.getTimes();
        oldOfficeHours = new HashMap();
        oldOfficeHours.putAll(data.getOfficeHours());    
        oldNumOfRows = data.getNumRows();
    }
    
    public void doTransaction() {
        data.setStartHour(newStartTime);
        data.setEndHour(newEndTime);
        workspace.resetWorkspace();
        workspace.reloadWorkspace(data);

        HashMap<String, StringProperty> newOfficeHours = data.getOfficeHours();
        int newNumOfRows = data.getNumRows();
              
        if (times.indexOf(newStartTime) < times.indexOf(oldStartTime)) {
            int startingPoint = ((times.indexOf(oldStartTime) - times.indexOf(newStartTime)) * 2);
           
            for (int col = 2; col < 7; col++) {
              for (int row = (startingPoint + 1); row < newNumOfRows; row++) {
                    if ((row - startingPoint) == oldNumOfRows) {
                        break;
                    }
                    else {
                        String newCellKey = data.getCellKey(col, row);
                        String oldCellKey = data.getCellKey(col, (row - startingPoint));
                        StringProperty newCellProp = newOfficeHours.get(newCellKey);
                        newCellProp.setValue(oldOfficeHours.get(oldCellKey).getValue());
                    }
                }
            }
        }
        else if (times.indexOf(newStartTime) > times.indexOf(oldStartTime)) {
            int startingPoint = ((times.indexOf(newStartTime) - times.indexOf(oldStartTime)) * 2);
            for (int col = 2; col < 7; col++) {
              for (int row = 1; row < newNumOfRows; row++) {
                    if ((row + startingPoint) >= oldNumOfRows) {
                        break;
                    }
                    else {
                        String newCellKey = data.getCellKey(col, row);
                        String oldCellKey = data.getCellKey(col, (row + startingPoint));
                        StringProperty newCellProp = newOfficeHours.get(newCellKey);
                        newCellProp.setValue(oldOfficeHours.get(oldCellKey).getValue());
                    }
                }
            }
        }
        else {
            for (int col = 2; col < 7; col++) {
              for (int row = 1; row < newNumOfRows; row++) {
                    if (row == oldNumOfRows) {
                        break;
                    }
                    else {
                        String newCellKey = data.getCellKey(col, row);
                        StringProperty newCellProp = newOfficeHours.get(newCellKey);
                        newCellProp.setValue(oldOfficeHours.get(newCellKey).getValue());
                    }
                }
            }
        }
        data.markAsEdited();
    } 
    
    public void undoTransaction() {
                data.getOfficeHours().putAll(oldOfficeHours);

        data.setStartHour(oldStartTime);
        data.setEndHour(oldEndTime);
        workspace.resetWorkspace();
        workspace.reloadWorkspace(data);
        for (int col = 2; col < 7; col++) {
            for (int row = 1; row < data.getNumRows(); row++) {
                String cellKey = data.getCellKey(col, row);
                StringProperty cellProp = data.getOfficeHours().get(cellKey);
                cellProp.setValue(oldOfficeHours.get(cellKey).getValue());
            }
        }
        data.markAsEdited();
    }
}
