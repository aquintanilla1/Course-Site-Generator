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
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author Alvaro Quintanilla, ID: 110289649
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
    
    ScheduleDataController controller;
        
    public ScheduleTabBuilder(CSGApp initApp) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        app = initApp;
        tab = new Tab(props.getProperty(CSGProp.SCHEDULE_TAB));
        wholePane = new VBox(2);
        topPane = buildTopPane();
        bottomPane = buildBottomPane();
        scheduleHeader = new Label(props.getProperty(CSGProp.SCHEDULE_HEADER));
        
        bottomPane.setPadding(new Insets(15,15,15,15));
        wholePane.setPadding(new Insets(15, 15, 15, 15));
        wholePane.getChildren().add(scheduleHeader);
        wholePane.getChildren().add(topPane);
        wholePane.getChildren().add(bottomPane);
        
        tab.setContent(wholePane);
        
        controller = new ScheduleDataController(app);
        
        typeBox.setOnAction(e -> {
            
            if (typeBox.getValue() == null) {
                timeTextField.setDisable(false);
                topicTextField.setDisable(false);
                linkTextField.setDisable(false);
                criteriaTextField.setDisable(false);
            }
            
            else if (typeBox.getValue().equals(props.getProperty(CSGProp.HOLIDAY_TEXT))) {
                timeTextField.setDisable(true);
                topicTextField.setDisable(true);
                linkTextField.setDisable(false);
                criteriaTextField.setDisable(true);
            }
            else if (typeBox.getValue().equals(props.getProperty(CSGProp.LECTURE_TEXT))) {
                timeTextField.setDisable(false);
                topicTextField.setDisable(false);
                linkTextField.setDisable(false);
                criteriaTextField.setDisable(true);
            }
            else if (typeBox.getValue().equals(props.getProperty(CSGProp.HWS_TEXT))) {
                timeTextField.setDisable(false);
                topicTextField.setDisable(false);
                linkTextField.setDisable(false);
                criteriaTextField.setDisable(false);
            }
            else if (typeBox.getValue().equals(props.getProperty(CSGProp.RECITATION_TEXT))) {
                timeTextField.setDisable(true);
                topicTextField.setDisable(false);
                linkTextField.setDisable(true);
                criteriaTextField.setDisable(true);
            }
            else if (typeBox.getValue().equals(props.getProperty(CSGProp.REFERENCE_TEXT))) {
                timeTextField.setDisable(true);
                topicTextField.setDisable(false);
                linkTextField.setDisable(false);
                criteriaTextField.setDisable(true);
            }
        });
        
        addUpdateButton.setOnAction(e -> {
            controller.handleAddItem();
        });
        
        clearButton.setOnAction(e -> {
            controller.handleClear();
        });
        
        scheduleTable.setOnMouseClicked(e -> { 
            controller.handleEditItem();
        });
        
        deleteButton.setOnAction(e -> {
            controller.handleRemoveItem();
        });
        
        scheduleTable.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DELETE) {
            controller.handleRemoveItem();
            }
        });
        
        startPicker.setOnAction(e -> {
            controller.handleStartDate(startPicker.getValue());
        });
        
        endPicker.setOnAction(e -> {
            controller.handleEndDate(endPicker.getValue());
        });
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
    
    public VBox getWholePane() {
        return wholePane;
    }
    
    public TableView getScheduleTable() {
        return scheduleTable;
    }
    
    public Label getScheduleHeaderLabel() {
        return scheduleHeader;
    }
    
    public Label getCalendarBoundsLabel() {
        return calendarText;
    }
    
    public Label getScheduleItemsLabel() {
        return scheduleItemsText;
    }
    
    public Label getAddEditLabel() {
        return addEditText;
    }
    
    private GridPane buildTopPane() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(15,15,15,15));

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
        deleteButton = makeDeleteButton("Remove.png");
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
        
        typeColumn.setCellValueFactory(new PropertyValueFactory<ScheduleItem, String> ("type"));
        dateColumn.setCellValueFactory(new PropertyValueFactory<ScheduleItem, String> ("date"));
        titleColumn.setCellValueFactory(new PropertyValueFactory<ScheduleItem, String> ("title"));
        topicColumn.setCellValueFactory(new PropertyValueFactory<ScheduleItem, String> ("topic"));

        
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
        CSGData data = (CSGData) app.getDataComponent();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setVgap(5);
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
        addUpdateButton = new Button(props.getProperty(CSGProp.ADD_SCHEDULE_ITEM_BUTTON_TEXT));
        
        grid.add(addEditText, 0, 0);
        grid.add(typeText, 0, 1);
        grid.add(dateText, 0, 2);
        grid.add(timeText, 0, 3);
        grid.add(titleText, 0, 4);
        grid.add(topicText, 0, 5);
        grid.add(linkText, 0, 6);
        grid.add(criteriaText, 0, 7);
        grid.add(addUpdateButton, 0, 8);
        
        typeBox = new ComboBox(data.getScheduleItemTypes());
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
    
    private Button makeDeleteButton(String icon) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
	
	// LOAD THE ICON FROM THE PROVIDED FILE
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + icon;
        Image buttonImage = new Image(imagePath);
	
	// NOW MAKE THE BUTTON
        Button button = new Button();
        button.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(CSGProp.DELETE_SCHEDULE_ITEM_TOOLTIP));
        button.setTooltip(buttonTooltip);
		
	// AND RETURN THE COMPLETED BUTTON
        return button;
    }
    
    public void reloadCalendarBounds(CSGData data) {
        String startMon = data.getStartMonday();
        String endFri = data.getEndFriday();
        
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM-d-yyyy");
        
        if (!startMon.isEmpty()) {
            LocalDate startDate = LocalDate.parse(startMon, formatter);
            startPicker.setValue(startDate);
        }
        
        if (!endFri.isEmpty()) {
            LocalDate endDate = LocalDate.parse(endFri, formatter);
            endPicker.setValue(endDate);
        }
    }

    public DatePicker getStartPicker() {
        return startPicker;
    }

    public DatePicker getEndPicker() {
        return endPicker;
    }

    public DatePicker getDatePicker() {
        return datePicker;
    }

    public Button getAddUpdateButton() {
        return addUpdateButton;
    }

    public Button getClearButton() {
        return clearButton;
    }

    public ComboBox getTypeBox() {
        return typeBox;
    }

    public TextField getTimeTextField() {
        return timeTextField;
    }

    public TextField getTitleTextField() {
        return titleTextField;
    }

    public TextField getTopicTextField() {
        return topicTextField;
    }

    public TextField getLinkTextField() {
        return linkTextField;
    }

    public TextField getCriteriaTextField() {
        return criteriaTextField;
    }

    public ScheduleDataController getController() {
        return controller;
    }
    
    public void clearDataFields() {
        typeBox.getSelectionModel().clearSelection();
        timeTextField.clear();
        datePicker.setValue(null);
        titleTextField.clear();
        topicTextField.clear();
        linkTextField.clear();
        criteriaTextField.clear();
    }
}
