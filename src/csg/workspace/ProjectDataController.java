/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import csg.CSGProp;
import static csg.CSGProp.MISSING_STUDENT_NAME_MESSAGE;
import static csg.CSGProp.MISSING_STUDENT_NAME_TITLE;
import static csg.CSGProp.MISSING_STUDENT_TEAM_MESSAGE;
import static csg.CSGProp.MISSING_STUDENT_TEAM_TITLE;
import static csg.CSGProp.MISSING_TEAM_COLOR_MESSAGE;
import static csg.CSGProp.MISSING_TEAM_COLOR_TITLE;
import static csg.CSGProp.MISSING_TEAM_LINK_MESSAGE;
import static csg.CSGProp.MISSING_TEAM_LINK_TITLE;
import static csg.CSGProp.MISSING_TEAM_NAME_MESSAGE;
import static csg.CSGProp.MISSING_TEAM_NAME_TITLE;
import static csg.CSGProp.MISSING_TEAM_TEXT_COLOR_MESSAGE;
import static csg.CSGProp.MISSING_TEAM_TEXT_COLOR_TITLE;
import static csg.CSGProp.REMOVE_STUDENT_MESSAGE;
import static csg.CSGProp.REMOVE_STUDENT_TITLE;
import static csg.CSGProp.REMOVE_TEAM_MESSAGE;
import static csg.CSGProp.REMOVE_TEAM_TITLE;
import static csg.CSGProp.TEAM_NAME_AND_LINK_NOT_UNIQUE_MESSAGE;
import static csg.CSGProp.TEAM_NAME_AND_LINK_NOT_UNIQUE_TITLE;
import csg.data.CSGData;
import csg.data.Student;
import csg.data.Team;
import djf.controller.AppFileController;
import djf.settings.AppPropertyType;
import djf.ui.AppMessageDialogSingleton;
import djf.ui.OKCancelDialogSingleton;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.paint.Color;
import properties_manager.PropertiesManager;

/**
 *
 * @author Alvaro
 */
public class ProjectDataController {
    CSGApp app;
    CSGData data;
    AppFileController fileController;
    Button updateButton1;
    Button updateButton2;
    boolean isInUpdateState1;
    boolean isInUpdateState2;
    
    public ProjectDataController(CSGApp initApp) {
        app = initApp;
        data = (CSGData) app.getDataComponent();
    }
    
    public void handleAddTeam() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectTabBuilder projectWorkspace = workspace.getProjectTabBuilder();
        TextField nameTextField = projectWorkspace.getNameTextField();
        ColorPicker colorPicker = projectWorkspace.getColorPicker();
        ColorPicker textColorPicker = projectWorkspace.getTextColorPicker();
        TextField linkTextField = projectWorkspace.getLinkTextField();
        
        String name = nameTextField.getText();
        String color = getColorCode(colorPicker.getValue());
        String textColor = getColorCode(textColorPicker.getValue());
        String link = linkTextField.getText();
        
        if (verifyTeam(name, color, textColor, link)) {
            return;
        }
        else {
            data.addTeam(name, color, textColor, link);
            projectWorkspace.resetTeamFields();
        }
    }
    
    
    public void handleEditTeam() {
        isInUpdateState1 = true;
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectTabBuilder projectWorkspace = workspace.getProjectTabBuilder();
        TableView teamTable = projectWorkspace.getTeamTable();
        TextField nameTextField = projectWorkspace.getNameTextField();
        ColorPicker colorPicker = projectWorkspace.getColorPicker();
        ColorPicker textColorPicker = projectWorkspace.getTextColorPicker();
        TextField linkTextField = projectWorkspace.getLinkTextField();
        
        Object selectedItem = teamTable.getSelectionModel().getSelectedItem();
        Team team = (Team) selectedItem;
        
        if (team == null) {
            return;
        }
        
        nameTextField.setText(team.getName());
        colorPicker.setValue(Color.web(team.getColor()));
        textColorPicker.setValue(Color.web(team.getTextColor()));
        linkTextField.setText(team.getLink());
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        updateButton1 = projectWorkspace.getAddUpdateButton1();
        updateButton1.setText(props.getProperty(CSGProp.EDIT_TEAM_BUTTON_TEXT));
        
        updateButton1.setOnAction(e -> { 
            String newName = nameTextField.getText();
            String newColor = getColorCode(colorPicker.getValue());
            String newTextColor = getColorCode(textColorPicker.getValue());
            String newLink = linkTextField.getText();
            
            if (verifyTeam(newName, newColor, newTextColor, newLink)) {
                return;
            }
            else {
                Team newTeam = new Team(newName, newColor, newTextColor, newLink);
                data.editTeam(newTeam, data.getTeams().indexOf(team));
                teamTable.refresh();
            }
        });
    }
    
    public void handleClearTeam() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectTabBuilder projectWorkspace = workspace.getProjectTabBuilder();
        projectWorkspace.resetTeamFields();
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        updateButton1 =  projectWorkspace.getAddUpdateButton1();
        updateButton1.setText(props.getProperty(CSGProp.ADD_TEAM_BUTTON_TEXT));
        
        updateButton1.setOnAction(e -> {
            handleAddTeam();
        });
        isInUpdateState1 = false;
    }
    
    public void handleRemoveTeam() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectTabBuilder projectWorkspace = workspace.getProjectTabBuilder();
        TableView teamTable = projectWorkspace.getTeamTable();
        
        Object selectedItem = teamTable.getSelectionModel().getSelectedItem();
        Team team = (Team) selectedItem;
        
        if (team == null) {
            return;
        }
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        OKCancelDialogSingleton okCancelDialog = OKCancelDialogSingleton.getSingleton();
        okCancelDialog.show(props.getProperty(REMOVE_TEAM_TITLE), props.getProperty(REMOVE_TEAM_MESSAGE));
        String selection = okCancelDialog.getSelection();

        if (selection.equals(props.getProperty(AppPropertyType.OK_TEXT))) {
            data.removeTeam(team.getName());
            projectWorkspace.resetTeamFields();
        }
        else {
            return;
        }
        if (isInUpdateState1) {
            selectedItem = teamTable.getSelectionModel().getSelectedItem();
            team = (Team) selectedItem;
            
            if (team != null) {
                TextField nameTextField = projectWorkspace.getNameTextField();
                ColorPicker colorPicker = projectWorkspace.getColorPicker();
                ColorPicker textColorPicker = projectWorkspace.getTextColorPicker();
                TextField linkTextField = projectWorkspace.getLinkTextField();

                nameTextField.setText(team.getName());
                colorPicker.setValue(Color.web(team.getColor()));
                textColorPicker.setValue(Color.web(team.getTextColor()));
                linkTextField.setText(team.getLink());
            }
            else {
                handleClearTeam();
            }
        }
    }
    
    public void handleAddStudent() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectTabBuilder projectWorkspace = workspace.getProjectTabBuilder();
        TextField firstNameTextField = projectWorkspace.getFirstNameTextField();
        TextField lastNameTextField = projectWorkspace.getLastNameTextField();
        ComboBox<Team> teamBox = projectWorkspace.getTeamBox();
        TextField roleTextField = projectWorkspace.getRoleTextField();
        
        String firstName = firstNameTextField.getText();
        String lastName = lastNameTextField.getText();
        String team = "";
        String role = roleTextField.getText();
        
        if (!(teamBox.getValue() == null)) {
           team = teamBox.getValue().getName();
        }
        
        if (verifyStudent(firstName, lastName, team)) {
            return;
        }
        else {
            data.addStudent(firstName, lastName, team, role);
            projectWorkspace.resetStudentFields();
        }
    }
    
    public void handleEditStudent() {
        isInUpdateState2 = true;
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectTabBuilder projectWorkspace = workspace.getProjectTabBuilder();
        TableView studentTable = projectWorkspace.getStudentTable();
        TextField firstNameTextField = projectWorkspace.getFirstNameTextField();
        TextField lastNameTextField = projectWorkspace.getLastNameTextField();
        ComboBox<Team> teamBox = projectWorkspace.getTeamBox();
        TextField roleTextField = projectWorkspace.getRoleTextField();
        
        Object selectedItem = studentTable.getSelectionModel().getSelectedItem();
        Student student = (Student) selectedItem;
        
        if (student == null) {
            return;
        }
        
        firstNameTextField.setText(student.getFirstName());
        lastNameTextField.setText(student.getLastName());
        teamBox.setValue(data.getTeam(student.getTeam()));
        roleTextField.setText(student.getRole());
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        updateButton2 = projectWorkspace.getAddUpdateButton2();
        updateButton2.setText(props.getProperty(CSGProp.EDIT_STUDENT_BUTTON_TEXT));
        
        updateButton2.setOnAction(e -> { 
            String newFirstName = firstNameTextField.getText();
            String newLastName = lastNameTextField.getText();
            String newTeam = "";
            String newRole = roleTextField.getText();
            
            if (!(teamBox.getValue() == null)) {
                newTeam = teamBox.getValue().getName();
            }
            
            if (verifyStudent(newFirstName, newLastName, newTeam)) {
                return;
            }
            else {      
                Student newStudent = new Student(newFirstName, newLastName, newTeam, newRole);
                data.editStudent(newStudent, data.getStudents().indexOf(student));
                studentTable.refresh();
            }
        });
    }
    
    public void handleClearStudent() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectTabBuilder projectWorkspace = workspace.getProjectTabBuilder();
        projectWorkspace.resetStudentFields();
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        updateButton2 =  projectWorkspace.getAddUpdateButton2();
        updateButton2.setText(props.getProperty(CSGProp.ADD_STUDENT_BUTTON_TEXT));
        
        updateButton2.setOnAction(e -> {
            handleAddTeam();
        });
        isInUpdateState2 = false;
    }
    
    public void handleRemoveStudent() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ProjectTabBuilder projectWorkspace = workspace.getProjectTabBuilder();
        TableView studentTable = projectWorkspace.getStudentTable();
        
        Object selectedItem = studentTable.getSelectionModel().getSelectedItem();
        Student student = (Student) selectedItem;
        
        if (student == null) {
            return;
        }
        
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        OKCancelDialogSingleton okCancelDialog = OKCancelDialogSingleton.getSingleton();
        okCancelDialog.show(props.getProperty(REMOVE_STUDENT_TITLE), props.getProperty(REMOVE_STUDENT_MESSAGE));
        String selection = okCancelDialog.getSelection();

        if (selection.equals(props.getProperty(AppPropertyType.OK_TEXT))) {
            data.removeStudent(student.getFirstName(), student.getLastName());
            projectWorkspace.resetStudentFields();
        }
        else {
            return;
        }
        if (isInUpdateState2) {
            selectedItem = studentTable.getSelectionModel().getSelectedItem();
            student = (Student) selectedItem;
            
            if (student != null) {
                TextField firstNameTextField = projectWorkspace.getFirstNameTextField();
                TextField lastNameTextField = projectWorkspace.getLastNameTextField();
                ComboBox<Team> teamBox = projectWorkspace.getTeamBox();
                TextField roleTextField = projectWorkspace.getRoleTextField();
                
                firstNameTextField.setText(student.getFirstName());
                lastNameTextField.setText(student.getLastName());
                teamBox.setValue(data.getTeam(student.getTeam()));
                roleTextField.setText(student.getRole());
            }
            else {
                handleClearStudent();
            }
        }
        
    }
    
    private String getColorCode(Color color) {
        return color.toString().substring(2, 8);
    }
    
    private boolean verifyTeam(String name, String color, String textColor, String link) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        if (name.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TEAM_NAME_TITLE), props.getProperty(MISSING_TEAM_NAME_MESSAGE));
            return true;
        }
        else if (color.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TEAM_COLOR_TITLE), props.getProperty(MISSING_TEAM_COLOR_MESSAGE));
            return true; 
        }
        else if (textColor.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TEAM_TEXT_COLOR_TITLE), props.getProperty(MISSING_TEAM_TEXT_COLOR_MESSAGE));
            return true; 
        }
        else if (link.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_TEAM_LINK_TITLE), props.getProperty(MISSING_TEAM_LINK_MESSAGE));
            return true; 
        }
        else if (data.containsTeam(name, link) && !isInUpdateState1) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(TEAM_NAME_AND_LINK_NOT_UNIQUE_TITLE), props.getProperty(TEAM_NAME_AND_LINK_NOT_UNIQUE_MESSAGE));
            return true; 
        }
        else {
            return false;
        }
    }
    
    private boolean verifyStudent(String firstName, String lastName, String team) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        if (firstName.isEmpty() || lastName.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_STUDENT_NAME_TITLE), props.getProperty(MISSING_STUDENT_NAME_MESSAGE));
            return true;
        }
        else if (team.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_STUDENT_TEAM_TITLE), props.getProperty(MISSING_STUDENT_TEAM_MESSAGE));
            return true; 
        }
        else {
            return false;
        }
    }
}
