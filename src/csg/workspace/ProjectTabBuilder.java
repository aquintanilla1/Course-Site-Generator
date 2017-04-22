/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import csg.CSGProp;
import csg.data.CSGData;
import csg.data.Recitation;
import csg.data.ScheduleItem;
import csg.data.Student;
import csg.data.Team;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import javafx.beans.binding.Bindings;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ColorPicker;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author Alvaro Quintanilla, ID: 110289649
 */
public class ProjectTabBuilder {
    CSGApp app;
    Tab tab;
    VBox wholePane, topPane, bottomPane;
    HBox teamSubHeader, studentSubHeader;
    Button addUpdateButton1, addUpdateButton2, clearButton1, clearButton2, teamDeleteButton, studentDeleteButton;
    Label projectHeaderText, teamSubHeaderText, studentSubHeaderText, addEditText1, addEditText2,
            nameText, colorText, textColorText, linkText, firstNameText, lastNameText, teamText,
            roleText;
    TextField nameTextField, linkTextField, firstNameTextField, lastNameTextField, teamTextField, roleTextField;
    ComboBox teamBox;
    ColorPicker colorPicker, textColorPicker;
    TableView teamTable, studentTable;
    TableColumn<Team, String> nameColumn, colorColumn, textColorColumn, linkColumn;
    TableColumn<Student, String> firstNameColumn, lastNameColumn, teamColumn, roleColumn;
    
    public ProjectTabBuilder(CSGApp initApp) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        app = initApp;
        tab = new Tab(props.getProperty(CSGProp.PROJECT_TAB));
        wholePane = new VBox(2);
        wholePane.setPadding(new Insets(1, 15, 15, 15));
        topPane = buildTopPane();
        bottomPane = buildBottomPane();
        
        projectHeaderText = new Label(props.getProperty(CSGProp.PROJECTS_HEADER));
        
        wholePane.getChildren().add(projectHeaderText);
        wholePane.getChildren().add(topPane);
        wholePane.getChildren().add(bottomPane);
        ScrollPane scrollPane = new ScrollPane(wholePane);
        scrollPane.setFitToHeight(true);
        scrollPane.setFitToWidth(true);
        tab.setContent(scrollPane);
    }
    
    public Tab getProjectTab() {
        return tab;
    }
    
    public VBox getTopPane() {
        return topPane;
    }
    
    public VBox getBottomPane() {
        return bottomPane;
    }
    
    public Label getProjectHeaderLabel() {
        return projectHeaderText;
    }
    
    public VBox getWholePane() {
        return wholePane;
    }
    
    public TableView getTeamTable() {
        return teamTable;
    }
    
    public TableView getStudentTable() {
        return studentTable;
    }
    
    public Label getTeamsLabel() {
        return teamSubHeaderText;
    }
    
    public Label getStudentsLabel() {
        return studentSubHeaderText;
    }
    
    public Label getAddEditLabel1() {
        return addEditText1;
    }
    
    public Label getAddEditLabel2() {
        return addEditText2;
    }
    
    private VBox buildTopPane() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        VBox vbox = new VBox(5);
        vbox.setPadding(new Insets(15, 15, 15, 15));
        teamSubHeader = new HBox(10);
        teamSubHeaderText = new Label(props.getProperty(CSGProp.TEAM_SUB_HEADER));
        teamDeleteButton = makeDeleteButton("Remove.png", true);
        teamSubHeader.getChildren().addAll(teamSubHeaderText, teamDeleteButton);
        vbox.getChildren().add(teamSubHeader);
        
        teamTable = new TableView();
        CSGData data = (CSGData) app.getDataComponent();
        teamTable.setItems(data.getTeams());
        
        nameColumn = new TableColumn(props.getProperty(CSGProp.NAME_COLUMN_TEXT));
        colorColumn = new TableColumn(props.getProperty(CSGProp.COLOR_TEXT));
        textColorColumn = new TableColumn(props.getProperty(CSGProp.TEXT_COLOR_TEXT));
        linkColumn = new TableColumn(props.getProperty(CSGProp.LINK_TEXT));
        
        nameColumn.setCellValueFactory(new PropertyValueFactory<Team, String> ("name"));
        colorColumn.setCellValueFactory(new PropertyValueFactory<Team, String> ("color"));
        textColorColumn.setCellValueFactory(new PropertyValueFactory<Team, String> ("textColor"));
        linkColumn.setCellValueFactory(new PropertyValueFactory<Team, String> ("link"));

        teamTable.getColumns().add(nameColumn);
        teamTable.getColumns().add(colorColumn);
        teamTable.getColumns().add(textColorColumn);
        teamTable.getColumns().add(linkColumn);
        
        teamTable.setFixedCellSize(25);
        teamTable.prefHeightProperty().bind(teamTable.fixedCellSizeProperty().multiply(5));
        teamTable.minHeightProperty().bind(teamTable.prefHeightProperty());
        teamTable.maxHeightProperty().bind(teamTable.prefHeightProperty());
        
        vbox.getChildren().add(teamTable);
        
        GridPane innerGrid = buildTopInnerGrid();
        vbox.getChildren().add(innerGrid);

        return vbox;
    }
    
    private VBox buildBottomPane() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        VBox vbox = new VBox(5);
        vbox.setPadding(new Insets(15, 15, 15, 15));
        studentSubHeader = new HBox(10);
        studentSubHeaderText = new Label(props.getProperty(CSGProp.STUDENTS_SUBHEADER));
        studentDeleteButton = makeDeleteButton("Remove.png", false);
        studentSubHeader.getChildren().addAll(studentSubHeaderText, studentDeleteButton);
        vbox.getChildren().add(studentSubHeader);
        
        studentTable = new TableView();
        CSGData data = (CSGData) app.getDataComponent();
        studentTable.setItems(data.getStudents());
        
        firstNameColumn = new TableColumn(props.getProperty(CSGProp.FIRST_NAME_TEXT));
        lastNameColumn = new TableColumn(props.getProperty(CSGProp.LAST_NAME_TEXT));
        teamColumn = new TableColumn(props.getProperty(CSGProp.TEAM_TEXT));
        roleColumn = new TableColumn(props.getProperty(CSGProp.ROLE_TEXT));
        
        firstNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String> ("firstName"));
        lastNameColumn.setCellValueFactory(new PropertyValueFactory<Student, String> ("lastName"));
        teamColumn.setCellValueFactory(new PropertyValueFactory<Student, String> ("team"));
        roleColumn.setCellValueFactory(new PropertyValueFactory<Student, String> ("role"));

        
        studentTable.getColumns().add(firstNameColumn);
        studentTable.getColumns().add(lastNameColumn);
        studentTable.getColumns().add(teamColumn);
        studentTable.getColumns().add(roleColumn);
        
        studentTable.setFixedCellSize(25);
        studentTable.prefHeightProperty().bind(studentTable.fixedCellSizeProperty().multiply(5));
        studentTable.minHeightProperty().bind(studentTable.prefHeightProperty());
        studentTable.maxHeightProperty().bind(studentTable.prefHeightProperty());
        
        vbox.getChildren().add(studentTable);       
        GridPane innerGrid = buildBottomInnerGrid();
        vbox.getChildren().add(innerGrid);
        
        return vbox;
    }
    
    private GridPane buildTopInnerGrid() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10,10,10,10));
        
        addEditText1 = new Label(props.getProperty(CSGProp.ADD_EDIT_HEADER));
        nameText = new Label(props.getProperty(CSGProp.NAME_TEXT) + ":");
        colorText = new Label(props.getProperty(CSGProp.COLOR_TEXT) + ":");
        linkText = new Label(props.getProperty(CSGProp.LINK_TEXT) + ":");
        addUpdateButton1 = new Button(props.getProperty(CSGProp.ADD_UPDATE_BUTTON_TEXT));
        
        grid.add(addEditText1, 0, 0);
        grid.add(nameText, 0, 1);
        grid.add(colorText, 0, 2);
        grid.add(linkText, 0, 3);
        grid.add(addUpdateButton1, 0, 4);
        
        nameTextField = new TextField();
        colorPicker = new ColorPicker();
        colorPicker.setMinHeight(25.0);
        colorPicker.setPrefHeight(25.0);
        colorPicker.setMaxHeight(25.0);
        linkTextField = new TextField();
        clearButton1 = new Button(props.getProperty(CSGProp.CLEAR_BUTTON_TEXT));
                
        grid.add(nameTextField, 1, 1);
        grid.add(colorPicker, 1, 2);
        grid.add(linkTextField, 1, 3, 3, 1);
        grid.add(clearButton1, 1, 4);
        
        textColorText = new Label(props.getProperty(CSGProp.TEXT_COLOR_TEXT) + ":");
        textColorPicker = new ColorPicker();
        
        grid.add(textColorText, 2, 2);
        grid.add(textColorPicker, 3, 2);

        textColorText = new Label(props.getProperty(CSGProp.TEXT_COLOR_TEXT) + ":");

        return grid;
    }
    
    private GridPane buildBottomInnerGrid() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setVgap(4);
        grid.setHgap(10);
        grid.setPadding(new Insets(10,10,10,10));
        
        addEditText2 = new Label(props.getProperty(CSGProp.ADD_EDIT_HEADER));
        firstNameText = new Label(props.getProperty(CSGProp.FIRST_NAME_TEXT) + ":");
        lastNameText = new Label(props.getProperty(CSGProp.LAST_NAME_TEXT) + ":");
        teamText = new Label(props.getProperty(CSGProp.TEAM_TEXT) + ":");
        roleText = new Label(props.getProperty(CSGProp.ROLE_TEXT) + ":");
        addUpdateButton2 = new Button(props.getProperty(CSGProp.ADD_UPDATE_BUTTON_TEXT));
        
        grid.add(addEditText2, 0, 0);
        grid.add(firstNameText, 0, 1);
        grid.add(lastNameText, 0, 2);
        grid.add(teamText, 0, 3);
        grid.add(roleText, 0, 4);
        grid.add(addUpdateButton2, 0, 5);
        
        firstNameTextField = new TextField();
        lastNameTextField = new TextField();
        teamBox = new ComboBox();
        roleTextField = new TextField();
        clearButton2 = new Button(props.getProperty(CSGProp.CLEAR_BUTTON_TEXT));
        
        grid.add(firstNameTextField, 1, 1);
        grid.add(lastNameTextField, 1, 2);
        grid.add(teamBox, 1, 3);
        grid.add(roleTextField, 1, 4);
        grid.add(clearButton2, 1, 5);

        return grid;      
    }
    
    private Button makeDeleteButton(String icon, boolean isTeamDeleteButton) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
	
	// LOAD THE ICON FROM THE PROVIDED FILE
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + icon;
        Image buttonImage = new Image(imagePath);
	
	// NOW MAKE THE BUTTON
        Button button = new Button();
        button.setGraphic(new ImageView(buttonImage));
        
        Tooltip buttonTooltip;
        if (isTeamDeleteButton) {
            buttonTooltip = new Tooltip(props.getProperty(CSGProp.DELETE_TEAM_TOOLTIP));
        }
        else {
            buttonTooltip = new Tooltip(props.getProperty(CSGProp.DELETE_STUDENT_TOOLTIP));
        }
        button.setTooltip(buttonTooltip);
		
	// AND RETURN THE COMPLETED BUTTON
        return button;
    }
    
}
