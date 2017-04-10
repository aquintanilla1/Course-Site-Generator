/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import csg.CSGProp;
import csg.data.CSGData;
import csg.data.ScheduleItem;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author Alvaro
 */
public class ScheduleTabBuilder {
    CSGApp app;
    Tab tab;
    VBox wholePane, bottomPane;
    GridPane topPane;
    TableView scheduleTable;
    TableColumn<ScheduleItem, String> typeColumn;
    TableColumn<ScheduleItem, String> dateColumn;
    TableColumn<ScheduleItem, String> titleColumn;
    TableColumn<ScheduleItem, String> topicColumn;
          
    HBox topSubHeader, bottomSubHeader1, bottomSubHeader2;
    DatePicker startPicker, endPicker, datePicker;
    Button deleteButton, addUpdateButton, clearButton;
    Label scheduleHeader, calendarText, startMonText, endFriText, scheduleItemsText,
            addEditText, typeText, dateText, timeText, titleText, topicText, linkText, criteriaText;
    ComboBox typeBox;
    TextField timeTextField, titleTextField, topicTextField, linkTextField, criteriaTextField;
    
            
    
    public ScheduleTabBuilder(CSGApp initApp) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        app = initApp;
        tab = new Tab(props.getProperty(CSGProp.SCHEDULE_TAB));
        wholePane = new VBox(2);
        topPane = buildTopPane();
        bottomPane = buildBottomPane();
        scheduleHeader = new Label(props.getProperty(CSGProp.SCHEDULE_TAB));
        
        bottomPane.setPadding(new Insets(15,15,15,15));
        wholePane.setPadding(new Insets(15, 15, 15, 15));
        wholePane.getChildren().add(scheduleHeader);
        wholePane.getChildren().add(topPane);
        wholePane.getChildren().add(bottomPane);
        
        tab.setContent(wholePane);
    }
    
    public Tab getScheduleTab() {
        return tab;
    }
    
    public GridPane getTopPane() {
        return topPane;
    }
    
    public VBox getBottomPane() {
        return bottomPane;
    }
    
    private GridPane buildTopPane() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(15,15,15,15));
        
//        Button deleteButton, addUpdateButton, clearButton;
//    Label scheduleHeader, calendarText, startMonText, endFriText, scheduleItemsText,
//            addEditText, typeText, dateText, timeText, titleText, topicText, linkText, criteriaText;
        topSubHeader = new HBox();
        calendarText = new Label(props.getProperty(CSGProp.CALENDAR_BOUNDARIES_TEXT));
        startMonText = new Label(props.getProperty(CSGProp.STARTING_MONDAY_TEXT));
        endFriText = new Label(props.getProperty(CSGProp.ENDING_FRIDAY_TEXT));
        startPicker = new DatePicker();
        endPicker = new DatePicker();
        
        topSubHeader.getChildren().add(calendarText);
        
        grid.add(topSubHeader, 0, 0);
        grid.add(startMonText, 0, 1);
        grid.add(startPicker, 1, 1);
        grid.add(endFriText, 2, 1);
        grid.add(endPicker, 3, 1);
        
        return grid;
    }
    
    private VBox buildBottomPane() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        VBox vbox = new VBox(5);
        bottomSubHeader1 = new HBox(5);
        scheduleItemsText = new Label(props.getProperty(CSGProp.SCHEDULE_ITEMS_TEXT));
        deleteButton = new Button("-");
        bottomSubHeader1.getChildren().addAll(scheduleItemsText, deleteButton);
        
        vbox.getChildren().add(bottomSubHeader1);
        
        scheduleTable = new TableView();
        CSGData data = (CSGData) app.getDataComponent();
        ObservableList<ScheduleItem> scheduleItems = data.getScheduleItems();
        scheduleTable.setItems(scheduleItems);
        typeColumn = new TableColumn(props.getProperty(CSGProp.TYPE_TEXT));
        dateColumn = new TableColumn(props.getProperty(CSGProp.DATE_TEXT));
        titleColumn = new TableColumn(props.getProperty(CSGProp.TITLE_TEXT));
        topicColumn = new TableColumn(props.getProperty(CSGProp.TOPIC_TEXT));
        
        scheduleTable.getColumns().add(typeColumn);
        scheduleTable.getColumns().add(dateColumn);
        scheduleTable.getColumns().add(titleColumn);
        scheduleTable.getColumns().add(topicColumn);
        
        scheduleTable.setFixedCellSize(25);
        scheduleTable.prefHeightProperty().bind(scheduleTable.fixedCellSizeProperty().multiply(10));
        scheduleTable.minHeightProperty().bind(scheduleTable.prefHeightProperty());
        scheduleTable.maxHeightProperty().bind(scheduleTable.prefHeightProperty());

        vbox.getChildren().add(scheduleTable);
        
        GridPane innerGrid = buildInnerGrid();
        vbox.getChildren().add(innerGrid);
        return vbox;
    }
    
    private GridPane buildInnerGrid() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(10,10,10,10));
        
        addEditText = new Label(props.getProperty(CSGProp.ADD_EDIT_HEADER));
        typeText = new Label(props.getProperty(CSGProp.TYPE_TEXT) + ":");
        dateText = new Label(props.getProperty(CSGProp.DATE_TEXT) + ":");
        timeText = new Label(props.getProperty(CSGProp.TIME_TEXT) + ":");
        titleText = new Label(props.getProperty(CSGProp.TITLE_TEXT) + ":");
        topicText = new Label(props.getProperty(CSGProp.TOPIC_TEXT) + ":");
        linkText = new Label(props.getProperty(CSGProp.LINK_TEXT) + ":");
        criteriaText = new Label(props.getProperty(CSGProp.CRITERIA_TEXT) + ":");
        addUpdateButton = new Button(props.getProperty(CSGProp.ADD_UPDATE_BUTTON_TEXT));
        
        grid.add(addEditText, 0, 0);
        grid.add(typeText, 0, 1);
        grid.add(dateText, 0, 2);
        grid.add(timeText, 0, 3);
        grid.add(titleText, 0, 4);
        grid.add(topicText, 0, 5);
        grid.add(linkText, 0, 6);
        grid.add(criteriaText, 0, 7);
        grid.add(addUpdateButton, 0, 8);
        
        typeBox = new ComboBox();
        datePicker = new DatePicker();
        timeTextField = new TextField();
        titleTextField = new TextField();
        topicTextField = new TextField();
        linkTextField = new TextField();
        criteriaTextField = new TextField();
        clearButton = new Button(props.getProperty(CSGProp.CLEAR_BUTTON_TEXT));
        
        grid.add(typeBox, 1, 1);
        grid.add(datePicker, 1, 2);
        grid.add(timeTextField, 1, 3);
        grid.add(titleTextField, 1, 4, 4, 1);
        grid.add(topicTextField, 1, 5, 4, 1);
        grid.add(linkTextField, 1, 6, 4, 1);
        grid.add(criteriaTextField, 1, 7, 4, 1);
        grid.add(clearButton, 1, 8);

        
        

        
        
        return grid;
    }
}
