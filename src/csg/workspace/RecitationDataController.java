/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import csg.CSGProp;
import static csg.CSGProp.EDIT_RECITATION_BUTTON_TEXT;
import static csg.CSGProp.UPDATE_BUTTON_TEXT;
import csg.data.CSGData;
import csg.data.Recitation;
import csg.data.TeachingAssistant;
import djf.controller.AppFileController;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import properties_manager.PropertiesManager;

/**
 *
 * @author Alvaro Quintanilla, ID: 110289649
 */
public class RecitationDataController {
    CSGApp app;
    CSGData data;
    AppFileController fileController;
    Button updateButton;
    boolean isInUpdateState;
    
    
    public RecitationDataController(CSGApp initApp) {
        app = initApp;
        data = (CSGData) app.getDataComponent();        
    }
    
    public void handleAddRecitation() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        RecitationTabBuilder recitationWorkspace = workspace.getRecitationTabBuilder();
        TextField sectionTextField = recitationWorkspace.getSectionTextField();
        TextField instructorTextField = recitationWorkspace.getInstructorTextField();
        TextField dayTimeTextField = recitationWorkspace.getDayTimeTextField();
        TextField locationTextField = recitationWorkspace.getLocationTextField();
        ComboBox<TeachingAssistant> ta1Box = recitationWorkspace.getTa1Box();
        ComboBox<TeachingAssistant> ta2Box = recitationWorkspace.getTa2Box();
        
        String section = sectionTextField.getText();
        String instructor = instructorTextField.getText();
        String dayTime = dayTimeTextField.getText();
        String location = locationTextField.getText();
        String ta1 = ta1Box.getValue().getName();
        String ta2 = ta2Box.getValue().getName();
        
        ArrayList<String> details = new ArrayList<>();
        details.add(section);
        details.add(instructor);
        details.add(dayTime);
        details.add(location);
        details.add(ta1);
        details.add(ta2);
        
        data.addRecitation(details);
        recitationWorkspace.resetDataFields();
    }
    
    public void handleEditRecitation() {
        isInUpdateState = true;
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        RecitationTabBuilder recitationWorkspace = workspace.getRecitationTabBuilder();
        TableView recitationTable = recitationWorkspace.getRecitationTable();
        TextField sectionTextField = recitationWorkspace.getSectionTextField();
        TextField instructorTextField = recitationWorkspace.getInstructorTextField();
        TextField dayTimeTextField = recitationWorkspace.getDayTimeTextField();
        TextField locationTextField = recitationWorkspace.getLocationTextField();
        ComboBox<TeachingAssistant> ta1Box = recitationWorkspace.getTa1Box();
        ComboBox<TeachingAssistant> ta2Box = recitationWorkspace.getTa2Box();
        
        Object selectedObject = recitationTable.getSelectionModel().getSelectedItem();
        Recitation recitation = (Recitation) selectedObject;
        
        if (recitation == null) {
            return;
        }
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        sectionTextField.setText(recitation.getSection());
        instructorTextField.setText(recitation.getInstructor());
        dayTimeTextField.setText(recitation.getDayTime());
        locationTextField.setText(recitation.getLocation());
        ta1Box.setValue(data.getTA(recitation.getTa1()));
        ta2Box.setValue(data.getTA(recitation.getTa2()));
        
        updateButton = recitationWorkspace.getAddUpdateButton();
        updateButton.setText(props.getProperty(EDIT_RECITATION_BUTTON_TEXT));
            
        updateButton.setOnAction(e -> {
            String newSection = sectionTextField.getText();
            String newInstructor = instructorTextField.getText();
            String newDayTime = dayTimeTextField.getText();
            String newLocation = locationTextField.getText();
            String newTa1 = ta1Box.getValue().getName();
            String newTa2 = ta2Box.getValue().getName();

            ArrayList<String> details = new ArrayList<>();
            details.add(newSection);
            details.add(newInstructor);
            details.add(newDayTime);
            details.add(newLocation);
            details.add(newTa1);
            details.add(newTa2);

            Recitation newRecitation = new Recitation(details);
            data.editRecitation(newRecitation, data.getRecitations().indexOf(recitation));
            recitationTable.refresh();
        });        
    }
    
    public void handleClear() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        RecitationTabBuilder recitationWorkspace = workspace.getRecitationTabBuilder();
        recitationWorkspace.resetDataFields();
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        updateButton =  recitationWorkspace.getAddUpdateButton();
        updateButton.setText(props.getProperty(CSGProp.ADD_RECITATION_BUTTON_TEXT));
        
        updateButton.setOnAction(e -> {
            handleAddRecitation();
        });
        isInUpdateState = false;
    }
    
    public void handleRemoveRecitation() {
        CSGWorkspace csgWorkspace = (CSGWorkspace)app.getWorkspaceComponent();
        RecitationTabBuilder recitationWorkspace = csgWorkspace.getRecitationTabBuilder();
        TableView recitationTable = recitationWorkspace.getRecitationTable();
        CSGData data = (CSGData)app.getDataComponent();
        
        Object selectedObject = recitationTable.getSelectionModel().getSelectedItem();
        Recitation recitation = (Recitation) selectedObject;
        
        if (recitation == null) {
            return;
        }
        
        String section = recitation.getSection();
        String dayTime = recitation.getDayTime();
        String location = recitation.getLocation();
        
        data.removeRecitation(section, dayTime, location);
        if (isInUpdateState) {
            TextField sectionTextField = recitationWorkspace.getSectionTextField();
            TextField instructorTextField = recitationWorkspace.getInstructorTextField();
            TextField dayTimeTextField = recitationWorkspace.getDayTimeTextField();
            TextField locationTextField = recitationWorkspace.getLocationTextField();
            ComboBox<TeachingAssistant> ta1Box = recitationWorkspace.getTa1Box();
            ComboBox<TeachingAssistant> ta2Box = recitationWorkspace.getTa2Box();
            
            selectedObject = recitationTable.getSelectionModel().getSelectedItem();
            recitation = (Recitation) selectedObject;
            
            if (recitation != null) {
                sectionTextField.setText(recitation.getSection());
                instructorTextField.setText(recitation.getInstructor());
                dayTimeTextField.setText(recitation.getDayTime());
                locationTextField.setText(recitation.getLocation());
                ta1Box.setValue(data.getTA(recitation.getTa1()));
                ta2Box.setValue(data.getTA(recitation.getTa2()));
            }
            else {
                handleClear();
            }
        }
    }       
}
