/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import csg.CSGProp;
import static csg.CSGProp.EDIT_RECITATION_BUTTON_TEXT;
import static csg.CSGProp.MISSING_SCHEDULE_DATE_MESSAGE;
import static csg.CSGProp.MISSING_SCHEDULE_DATE_TITLE;
import static csg.CSGProp.MISSING_SCHEDULE_TITLE_MESSAGE;
import static csg.CSGProp.MISSING_SCHEDULE_TITLE_TITLE;
import static csg.CSGProp.MISSING_SCHEDULE_TYPE_MESSAGE;
import static csg.CSGProp.MISSING_SCHEDULE_TYPE_TITLE;
import static csg.CSGProp.REMOVE_SCHEDULE_ITEM_MESSAGE;
import static csg.CSGProp.REMOVE_SCHEDULE_ITEM_TITLE;
import static csg.CSGProp.UPDATE_BUTTON_TEXT;
import csg.data.CSGData;
import csg.data.Recitation;
import csg.data.ScheduleItem;
import csg.data.TeachingAssistant;
import djf.controller.AppFileController;
import djf.settings.AppPropertyType;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.OKCancelDialogSingleton;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import properties_manager.PropertiesManager;
/**
 *
 * @author Alvaro Quintanilla, ID: 110289649
 */
public class ScheduleDataController {
    CSGApp app;
    CSGData data;
    AppFileController fileController;
    Button updateButton;
    boolean isInUpdateState;
    
    public ScheduleDataController(CSGApp initApp) {
        app = initApp;
        data = (CSGData) app.getDataComponent();
    }
    
    public void handleAddItem() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ScheduleTabBuilder scheduleWorkspace = workspace.getScheduleTabBuilder();    
        ComboBox<String> typeBox = scheduleWorkspace.getTypeBox();
        DatePicker datePicker = scheduleWorkspace.getDatePicker();
        TextField timeTextField = scheduleWorkspace.getTimeTextField();
        TextField titleTextField = scheduleWorkspace.getTitleTextField();
        TextField topicTextField = scheduleWorkspace.getTopicTextField();
        TextField linkTextField = scheduleWorkspace.getLinkTextField();
        TextField criteriaTextField = scheduleWorkspace.getCriteriaTextField();
                
        String type = "";
        String date = "";
        String time = timeTextField.getText();
        String title = titleTextField.getText();
        String topic = topicTextField.getText();
        String link = linkTextField.getText();
        String criteria = criteriaTextField.getText();
        
        if (!(typeBox.getValue() == null)) {
            type = typeBox.getValue();
        }
        
        if (!(datePicker.getValue() == null)) {
            date = getDate(datePicker.getValue());
        }
        
        if (verifyScheduleItem(type, date, title)) {
            return;
        }
        else {
            data.addScheduleItem(type, date, time, title, topic, link, criteria);
            scheduleWorkspace.clearDataFields();
        }
    } 
    
    public void handleEditItem() {
        isInUpdateState = true;
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ScheduleTabBuilder scheduleWorkspace = workspace.getScheduleTabBuilder(); 
        TableView scheduleTable = scheduleWorkspace.getScheduleTable();
        ComboBox<String> typeBox = scheduleWorkspace.getTypeBox();
        DatePicker datePicker = scheduleWorkspace.getDatePicker();
        TextField timeTextField = scheduleWorkspace.getTimeTextField();
        TextField titleTextField = scheduleWorkspace.getTitleTextField();
        TextField topicTextField = scheduleWorkspace.getTopicTextField();
        TextField linkTextField = scheduleWorkspace.getLinkTextField();
        TextField criteriaTextField = scheduleWorkspace.getCriteriaTextField();
        
        Object selectedItem = scheduleTable.getSelectionModel().getSelectedItem();
        ScheduleItem item = (ScheduleItem) selectedItem;
        
        if (item == null) {
            return;
        }
        
        typeBox.setValue(item.getType());
        datePicker.setValue(item.getLocalDate());
        timeTextField.setText(item.getTime());
        titleTextField.setText(item.getTitle());
        topicTextField.setText(item.getTopic());
        linkTextField.setText(item.getLink());
        criteriaTextField.setText(item.getCriteria());
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        updateButton = scheduleWorkspace.getAddUpdateButton();
        updateButton.setText(props.getProperty(CSGProp.EDIT_SCHEDULE_ITEM_BUTTON_TEXT));
        
        updateButton.setOnAction(e -> { 
            String newType = "";
            String newDate = "";
            String newTime = timeTextField.getText();
            String newTitle = titleTextField.getText();
            String newTopic = topicTextField.getText();
            String newLink = linkTextField.getText();
            String newCriteria = criteriaTextField.getText();
            
            if (!(typeBox.getValue() == null)) {
                newType = typeBox.getValue();
            }
        
            if (!(datePicker.getValue() == null)) {
                newDate = getDate(datePicker.getValue());
            }   
            
            if (verifyScheduleItem(newType, newDate, newTitle)) {
                return;
            }
            else {
                ScheduleItem newItem = new ScheduleItem(newType, newDate, newTime, newTitle, newTopic, newLink, newCriteria);
                data.editScheduleItem(newItem, data.getScheduleItems().indexOf(item));
                scheduleTable.refresh();
            }
        });
    }
    
    public void handleClear() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ScheduleTabBuilder scheduleWorkspace = workspace.getScheduleTabBuilder();
        scheduleWorkspace.clearDataFields();
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        updateButton =  scheduleWorkspace.getAddUpdateButton();
        updateButton.setText(props.getProperty(CSGProp.ADD_SCHEDULE_ITEM_BUTTON_TEXT));
        
        updateButton.setOnAction(e -> {
            handleAddItem();
        });
        isInUpdateState = false;
    }
    
    public void handleRemoveItem() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ScheduleTabBuilder scheduleWorkspace = workspace.getScheduleTabBuilder();
        TableView scheduleTable = scheduleWorkspace.getScheduleTable();
        
        Object selectedItem = scheduleTable.getSelectionModel().getSelectedItem();
        ScheduleItem item = (ScheduleItem) selectedItem;
        
        if (item == null) {
            return;
        }
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        OKCancelDialogSingleton okCancelDialog = OKCancelDialogSingleton.getSingleton();
        okCancelDialog.show(props.getProperty(REMOVE_SCHEDULE_ITEM_TITLE), props.getProperty(REMOVE_SCHEDULE_ITEM_MESSAGE));
        String selection = okCancelDialog.getSelection();

        if (selection.equals(props.getProperty(AppPropertyType.OK_TEXT))) {
            String type = item.getType();
            String date = item.getDate();
            String title = item.getTitle();
            data.removeScheduleItem(type, date, title);
            scheduleWorkspace.clearDataFields();
        }
        else {
            return;
        }        
        if (isInUpdateState) {            
            selectedItem = scheduleTable.getSelectionModel().getSelectedItem();
            item = (ScheduleItem) selectedItem;
            
            if (item != null) {
                ComboBox<String> typeBox = scheduleWorkspace.getTypeBox();
                DatePicker datePicker = scheduleWorkspace.getDatePicker();
                TextField timeTextField = scheduleWorkspace.getTimeTextField();
                TextField titleTextField = scheduleWorkspace.getTitleTextField();
                TextField topicTextField = scheduleWorkspace.getTopicTextField();
                TextField linkTextField = scheduleWorkspace.getLinkTextField();
                TextField criteriaTextField = scheduleWorkspace.getCriteriaTextField();
                
                typeBox.setValue(item.getType());
                datePicker.setValue(item.getLocalDate());
                timeTextField.setText(item.getTime());
                titleTextField.setText(item.getTitle());
                topicTextField.setText(item.getTopic());
                linkTextField.setText(item.getLink());
                criteriaTextField.setText(item.getCriteria());
            }
            else {
                handleClear();
            }
        }
    }
    
    private String getDate(LocalDate localDate) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
        return localDate.format(formatter);
    }
    
    private boolean verifyScheduleItem(String type, String date, String title) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
       
        if (type.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_SCHEDULE_TYPE_TITLE), props.getProperty(MISSING_SCHEDULE_TYPE_MESSAGE));
            return true;
        }
        else if (date.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_SCHEDULE_DATE_TITLE), props.getProperty(MISSING_SCHEDULE_DATE_MESSAGE));
            return true;
        }
        else if (title.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_SCHEDULE_TITLE_TITLE), props.getProperty(MISSING_SCHEDULE_TITLE_MESSAGE));
            return true;
        }
        else {
            return false;
        }
    }
}
