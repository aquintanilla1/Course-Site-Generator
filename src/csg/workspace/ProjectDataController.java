/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import csg.CSGProp;
import csg.data.CSGData;
import csg.data.Student;
import csg.data.Team;
import djf.controller.AppFileController;
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
    boolean isInUpdateState;
    
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
        
        data.addTeam(name, color, textColor, link);
        projectWorkspace.resetTeamFields();
    }
    
    
    public void handleEditTeam() {
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
            
            Team newTeam = new Team(newName, newColor, newTextColor, newLink);
            data.editTeam(newTeam, data.getTeams().indexOf(team));
            teamTable.refresh();
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
        
        data.removeTeam(team.getName());
        projectWorkspace.resetTeamFields();
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
        String team = teamBox.getValue().getName();
        String role = roleTextField.getText();
        
        data.addStudent(firstName, lastName, team, role);
        projectWorkspace.resetStudentFields();
    }
    
    public void handleEditStudent() {
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
            String newTeam = teamBox.getValue().getName();
            String newRole = roleTextField.getText();
            
            Student newStudent = new Student(newFirstName, newLastName, newTeam, newRole);
            data.editStudent(newStudent, data.getStudents().indexOf(student));
            studentTable.refresh();
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
        
        data.removeStudent(student.getFirstName(), student.getLastName());
        projectWorkspace.resetStudentFields();
    }
    
    private String getColorCode(Color color) {
        return color.toString().substring(2, 8);
    }
}
