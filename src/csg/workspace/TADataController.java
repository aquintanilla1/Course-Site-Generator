/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import djf.controller.AppFileController;
import static csg.CSGProp.*;
import djf.ui.AppMessageDialogSingleton;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.Pane;
import properties_manager.PropertiesManager;
import csg.CSGApp;
import csg.data.CSGData;
import csg.data.TeachingAssistant;
import csg.style.CSGStyle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javafx.collections.ObservableList;
import javafx.scene.control.Button;
import csg.workspace.CSGWorkspace;
import csg.workspace.TADataTabBuilder;

/**
 * This class provides responses to all taWorkspace interactions, meaning
 * interactions with the application controls not including the file
 * toolbar.
 * 
 * @author Richard McKenna
 * @coauthor Alvaro Quintanilla, ID: 110289649
 * @version 1.0
 */
public class TADataController {
    // THE APP PROVIDES ACCESS TO OTHER COMPONENTS AS NEEDED
    CSGApp app;
    AppFileController fileController;
    Button updateButton;
    boolean isInUpdateState;

    /**
     * Constructor, note that the app must already be constructed.
     */
    public TADataController(CSGApp initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
    }
    
    /**
     * This method responds to when the user requests to add
     * a new TA via the UI. Note that it must first do some
     * validation to make sure a unique name and email address
     * has been provided.
     */
    public void handleAddTA() {
        // WE'LL NEED THE WORKSPACE TO RETRIEVE THE USER INPUT VALUES
        CSGWorkspace csgWorkspace = (CSGWorkspace)app.getWorkspaceComponent();
        TADataTabBuilder taWorkspace = csgWorkspace.getTATabBuilder();
        TextField nameTextField = taWorkspace.getNameTextField();
        TextField emailTextField = taWorkspace.getEmailTextField();
        String name = nameTextField.getText();
        String email = emailTextField.getText();
        
        // WE'LL NEED TO ASK THE DATA SOME QUESTIONS TOO
        CSGData data = (CSGData)app.getDataComponent();
        
        // WE'LL NEED THIS IN CASE WE NEED TO DISPLAY ANY ERROR MESSAGES
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        //We'll need this to verify if the email is valid or not
        Pattern pattern = Pattern.compile("^[_A-Za-z0-9-\\+]+(\\.[_A-Za-z0-9-]+)*@"
		+ "[A-Za-z0-9-]+(\\.[A-Za-z0-9]+)*(\\.[A-Za-z]{2,})$");
        Matcher matcher = pattern.matcher(email);
        
        
        // DID THE USER NEGLECT TO PROVIDE A TA NAME?
        if (name.isEmpty()) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TA_NAME_TITLE), props.getProperty(MISSING_TA_NAME_MESSAGE));            
        }
        // Did the user neglect to provide a TA email?
        else if (email.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(MISSING_TA_EMAIL_TITLE), props.getProperty(MISSING_TA_EMAIL_MESSAGE));
        }
        // Did the user input an invalid email address?
        else if (!matcher.matches()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(INVALID_TA_EMAIL_TITLE), props.getProperty(INVALID_TA_EMAIL_MESSAGE));
        }
        // DOES A TA ALREADY HAVE THE SAME NAME OR EMAIL?
        else if (data.containsTA(name, email)) {
	    AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE), props.getProperty(TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE));                                    
        }
        // EVERYTHING IS FINE, ADD A NEW TA
        else {
            // ADD THE NEW TA TO THE DATA
            data.addTA(name, email, false, false);
           
            // CLEAR THE TEXT FIELDS
            nameTextField.setText("");
            emailTextField.setText("");
            
            // AND SEND THE CARET BACK TO THE NAME TEXT FIELD FOR EASY DATA ENTRY
            nameTextField.requestFocus();
        }
    }
    
    public void handleUpdateTA() {
        isInUpdateState = true;
        CSGWorkspace csgWorkspace = (CSGWorkspace)app.getWorkspaceComponent();
        TADataTabBuilder taWorkspace = csgWorkspace.getTATabBuilder();
        TableView taTable = taWorkspace.getTATable();
        CSGData data = (CSGData)app.getDataComponent();
        TextField nameTextField = taWorkspace.getNameTextField();
        TextField emailTextField = taWorkspace.getEmailTextField();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();  
        TeachingAssistant ta = (TeachingAssistant)selectedItem;
        
        if (ta == null) {
            return;
        }
        else {
            
            nameTextField.setText(ta.getName());
            emailTextField.setText(ta.getEmail());
            
            updateButton = taWorkspace.getAddButton();
            updateButton.setText(props.getProperty(UPDATE_BUTTON_TEXT));
            
            updateButton.setOnAction(e -> {
                String newTAName = nameTextField.getText();
                String newTAEmail = emailTextField.getText();
                TeachingAssistant newTA = new TeachingAssistant(newTAName, newTAEmail, false);
                data.updateTA(ta, newTA);
            });            
        }
    }
    
    public void handleClear() {
        CSGWorkspace csgWorkspace = (CSGWorkspace)app.getWorkspaceComponent();
        TADataTabBuilder taWorkspace = csgWorkspace.getTATabBuilder();
        TableView taTable = taWorkspace.getTATable();
        TextField nameTextField = taWorkspace.getNameTextField();
        TextField emailTextField = taWorkspace.getEmailTextField();
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        updateButton = taWorkspace.getAddButton();
        updateButton.setText(props.getProperty(ADD_BUTTON_TEXT));
        
        taTable.getSelectionModel().clearSelection();
        nameTextField.setText("");
        emailTextField.setText("");
        
        nameTextField.requestFocus();
        
        updateButton.setOnAction(e -> {
            handleAddTA();
        });
        isInUpdateState = false;
    }
    
    public void handleRemoveTA() {
        //Obtain TA table and data
        CSGWorkspace csgWorkspace = (CSGWorkspace)app.getWorkspaceComponent();
        TADataTabBuilder taWorkspace = csgWorkspace.getTATabBuilder();
        TableView taTable = taWorkspace.getTATable();
        CSGData data = (CSGData)app.getDataComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        //Select the TA
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();  
        
        TeachingAssistant ta = (TeachingAssistant)selectedItem;
        
        if (ta == null) {
            return;
        }
        else {
            String taName = ta.getName();
            String taEmail = ta.getEmail();
            data.removeTA(taName, taEmail);
        }
        if (isInUpdateState) {
            selectedItem = taTable.getSelectionModel().getSelectedItem();  
            ta = (TeachingAssistant)selectedItem;
            TextField nameTextField = taWorkspace.getNameTextField();
            TextField emailTextField = taWorkspace.getEmailTextField();
            nameTextField.setText(ta.getName());
            emailTextField.setText(ta.getEmail());
        }
    }
   

    /**
     * This function provides a response for when the user clicks
     * on the office hours grid to add or remove a TA to a time slot.
     * 
     * @param pane The pane that was toggled.
     */
    public void handleCellToggle(Pane pane) {
        // GET THE TABLE
        CSGWorkspace csgWorkspace = (CSGWorkspace)app.getWorkspaceComponent();
        TADataTabBuilder taWorkspace = csgWorkspace.getTATabBuilder();
        TableView taTable = taWorkspace.getTATable();
        
        // IS A TA SELECTED IN THE TABLE?
        Object selectedItem = taTable.getSelectionModel().getSelectedItem();
        
        // GET THE TA
        TeachingAssistant ta = (TeachingAssistant)selectedItem;
        if (ta == null) {
            return;
        }
        else {
            String taName = ta.getName();
            CSGData data = (CSGData)app.getDataComponent();
            String cellKey = pane.getId();

            // AND TOGGLE THE OFFICE HOURS IN THE CLICKED CELL
            data.toggleTAOfficeHours(cellKey, taName, false);
        }
    }
    
    public void handleTimeSubmission(String startTime, String endTime) {
        CSGWorkspace csgWorkspace = (CSGWorkspace)app.getWorkspaceComponent();
        TADataTabBuilder taWorkspace = csgWorkspace.getTATabBuilder();
        CSGData data = (CSGData)app.getDataComponent();
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ObservableList<String> times = data.getTimes();
        
        if (times.indexOf(startTime) >= times.indexOf(endTime)) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
            dialog.show(props.getProperty(INVALID_TIMES_TITLE), props.getProperty(INVALID_TIMES_MESSAGE));
        }
        else {
            taWorkspace.modifyOfficeHoursGrid(data, startTime, endTime);
        }
    }
    
//    public void handleCellHighlighting(Pane p, String key) {
//        CSGStyle taStyle = (CSGStyle)app.getStyleComponent();
//        taStyle.highlightCells(p, key);
//    }
//    
//    public void handleCellUnHighlighting(Pane p, String key) {
//        CSGStyle taStyle = (CSGStyle)app.getStyleComponent();
//        taStyle.unhighlightCells(p, key);
//    }
}
