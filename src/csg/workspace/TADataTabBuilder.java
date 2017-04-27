/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import csg.CSGProp;
import csg.data.CSGData;
import csg.data.TeachingAssistant;
import csg.style.CSGStyle;
import csg.transactions.ModifyOfficeHours_Transaction;
import djf.settings.AppPropertyType;
import static djf.settings.AppStartupConstants.FILE_PROTOCOL;
import static djf.settings.AppStartupConstants.PATH_IMAGES;
import djf.ui.OKCancelDialogSingleton;
import java.util.ArrayList;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.ScrollPane;
import javafx.scene.control.SelectionMode;
import javafx.scene.control.SplitPane;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Tooltip;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyCodeCombination;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import jtps.jTPS;
import jtps.jTPS_Transaction;
import properties_manager.PropertiesManager;

/**
 *
 * @author Alvaro Quintanilla, ID: 110289649
 */
public class TADataTabBuilder {
    CSGApp app;

    Tab tab;
    BorderPane wholePane;
    // THIS PROVIDES US WITH ACCESS TO THE APP COMPONENTS

    // THIS PROVIDES RESPONSES TO INTERACTIONS WITH THIS WORKSPACE
    TADataController controller;
    
    //Provides framework for undo and redo operations
    jTPS jTPS;
    final KeyCodeCombination undo = new KeyCodeCombination(KeyCode.Z, KeyCodeCombination.CONTROL_DOWN);
    final KeyCodeCombination redo = new KeyCodeCombination(KeyCode.Y, KeyCodeCombination.CONTROL_DOWN);
    

    // NOTE THAT EVERY CONTROL IS PUT IN A BOX TO HELP WITH ALIGNMENT
    
    // FOR THE HEADER ON THE LEFT
    HBox tasHeaderBox;
    Label tasHeaderLabel;
    
    // FOR THE TA TABLE
    TableView<TeachingAssistant> taTable;
    TableColumn<TeachingAssistant, String> nameColumn;
    TableColumn<TeachingAssistant, String> emailColumn;
    TableColumn<TeachingAssistant, Boolean> undergradColumn;

    // THE TA INPUT
    HBox addBox;
    TextField nameTextField;
    TextField emailTextField;
    Button addButton;
    Button editButton;
    Button clearButton;
    Button deleteButton;

    // THE HEADER ON THE RIGHT
    HBox officeHoursHeaderBox;
    Label officeHoursHeaderLabel;
        
    // THE OFFICE HOURS GRID
    GridPane officeHoursGridPane;
    HashMap<String, Pane> officeHoursGridTimeHeaderPanes;
    HashMap<String, Label> officeHoursGridTimeHeaderLabels;
    HashMap<String, Pane> officeHoursGridDayHeaderPanes;
    HashMap<String, Label> officeHoursGridDayHeaderLabels;
    HashMap<String, Pane> officeHoursGridTimeCellPanes;
    HashMap<String, Label> officeHoursGridTimeCellLabels;
    HashMap<String, Pane> officeHoursGridTACellPanes;
    HashMap<String, Label> officeHoursGridTACellLabels;
    
    //The Time ComboBox and submit button.
    ComboBox startTimeComboBox;
    ComboBox endTimeComboBox;
    Button submitTimesButton;
    Label startLabel;
    Label endLabel;

    
    public TADataTabBuilder(CSGApp initApp) {
        app = initApp;
        
        // WE'LL NEED THIS TO GET LANGUAGE PROPERTIES FOR OUR UI
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        tab = new Tab(props.getProperty(CSGProp.TA_TAB));
        jTPS = new jTPS();

        // INIT THE HEADER ON THE LEFT
        tasHeaderBox = new HBox(5);
        String tasHeaderText = props.getProperty(CSGProp.TAS_HEADER_TEXT.toString());
        tasHeaderLabel = new Label(" " + tasHeaderText);
        deleteButton = makeDeleteButton("Remove.png");
        tasHeaderBox.getChildren().add(tasHeaderLabel);
        tasHeaderBox.getChildren().add(deleteButton);
        
        
        // MAKE THE TABLE AND SETUP THE DATA MODEL
        taTable = new TableView();
        taTable.getSelectionModel().setSelectionMode(SelectionMode.SINGLE);
        CSGData data = (CSGData) app.getDataComponent();
        ObservableList<TeachingAssistant> tableData = data.getTeachingAssistants();
        taTable.setItems(tableData);
        
        undergradColumn = new TableColumn(props.getProperty(CSGProp.UNDERGRAD_COLUMN_TEXT));
        undergradColumn.setCellValueFactory(
            param -> param.getValue().isUndergrad()
        );
        
        undergradColumn.setCellFactory(CheckBoxTableCell.forTableColumn(undergradColumn));
        String nameColumnText = props.getProperty(CSGProp.NAME_COLUMN_TEXT.toString());
        String emailColumnText = props.getProperty(CSGProp.EMAIL_COLUMN_TEXT.toString());
        nameColumn = new TableColumn(nameColumnText);
        nameColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("name")
        );
        emailColumn = new TableColumn(emailColumnText);
        emailColumn.setCellValueFactory(
                new PropertyValueFactory<TeachingAssistant, String>("email")
        );
        
        taTable.getColumns().add(undergradColumn);
        taTable.getColumns().add(nameColumn);
        taTable.getColumns().add(emailColumn);
        taTable.setEditable(true);

        // ADD BOX FOR ADDING A TA
        String namePromptText = props.getProperty(CSGProp.NAME_PROMPT_TEXT.toString());
        String emailPromptText = props.getProperty(CSGProp.EMAIL_PROMPT_TEXT.toString());
        String addButtonText = props.getProperty(CSGProp.ADD_BUTTON_TEXT.toString());
        String updateButtonText = props.getProperty(CSGProp.UPDATE_BUTTON_TEXT.toString());
        String clearButtonText = props.getProperty(CSGProp.CLEAR_BUTTON_TEXT.toString());
        nameTextField = new TextField();
        nameTextField.setPromptText(namePromptText);
        emailTextField = new TextField();
        emailTextField.setPromptText(emailPromptText);
        addButton = new Button(addButtonText);
        editButton = new Button(updateButtonText);
        clearButton = new Button(clearButtonText);
        addBox = new HBox();
        nameTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.4));
        emailTextField.prefWidthProperty().bind(addBox.widthProperty().multiply(.4));
        addButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.2));
        editButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.2));
        clearButton.prefWidthProperty().bind(addBox.widthProperty().multiply(.2));
        addBox.getChildren().add(nameTextField);
        addBox.getChildren().add(emailTextField);
        addBox.getChildren().add(addButton);
        addBox.getChildren().add(clearButton);

        // INIT THE HEADER ON THE RIGHT
        officeHoursHeaderBox = new HBox();
        String officeHoursGridText = props.getProperty(CSGProp.OFFICE_HOURS_SUBHEADER.toString());
        officeHoursHeaderLabel = new Label(" " + officeHoursGridText + " ");
        startTimeComboBox = new ComboBox(data.getTimes());
        endTimeComboBox = new ComboBox(data.getTimes());
        startLabel = new Label(props.getProperty(CSGProp.START_TIME_TEXT.toString()));
        endLabel = new Label(props.getProperty(CSGProp.END_TIME_TEXT.toString()));
        submitTimesButton = new Button(props.getProperty(CSGProp.SUBMIT_TIMES_BUTTON_TEXT.toString()));
        
        officeHoursHeaderBox.getChildren().add(officeHoursHeaderLabel);
        officeHoursHeaderBox.getChildren().add(startLabel);
        officeHoursHeaderBox.getChildren().add(startTimeComboBox);
        officeHoursHeaderBox.getChildren().add(endLabel);
        officeHoursHeaderBox.getChildren().add(endTimeComboBox);
        officeHoursHeaderBox.getChildren().add(submitTimesButton);

        // THESE WILL STORE PANES AND LABELS FOR OUR OFFICE HOURS GRID
        officeHoursGridPane = new GridPane();
        officeHoursGridTimeHeaderPanes = new HashMap();
        officeHoursGridTimeHeaderLabels = new HashMap();
        officeHoursGridDayHeaderPanes = new HashMap();
        officeHoursGridDayHeaderLabels = new HashMap();
        officeHoursGridTimeCellPanes = new HashMap();
        officeHoursGridTimeCellLabels = new HashMap();
        officeHoursGridTACellPanes = new HashMap();
        officeHoursGridTACellLabels = new HashMap();
        
        
       
        // ORGANIZE THE LEFT AND RIGHT PANES
        VBox leftPane = new VBox();
        leftPane.getChildren().add(tasHeaderBox);        
        leftPane.getChildren().add(taTable);        
        leftPane.getChildren().add(addBox);
        VBox rightPane = new VBox();
        rightPane.getChildren().add(officeHoursHeaderBox);
        rightPane.getChildren().add(officeHoursGridPane);
        
        // BOTH PANES WILL NOW GO IN A SPLIT PANE
        SplitPane sPane = new SplitPane(leftPane, new ScrollPane(rightPane));
        wholePane = new BorderPane();
        
        // AND PUT EVERYTHING IN THE WORKSPACE
        wholePane.setCenter(sPane);
       // ((BorderPane) workspace).setRight(rightPane);

        // MAKE SURE THE TABLE EXTENDS DOWN FAR ENOUGH
        taTable.prefHeightProperty().bind(wholePane.heightProperty().multiply(1.9));
        
        //Put everything into the tab
        tab.setContent(wholePane);

        // NOW LET'S SETUP THE EVENT HANDLING
        controller = new TADataController(app);
        
        // CONTROLS FOR ADDING TAs
        nameTextField.setOnAction(e -> {
            controller.handleAddTA();
        });
        addButton.setOnAction(e -> {
            controller.handleAddTA();
        });
        
        clearButton.setOnAction(e -> {
            controller.handleClear();
        });
        
        deleteButton.setOnAction(e -> {
            controller.handleRemoveTA();
        });
        
        taTable.setOnMouseClicked(e -> {
            controller.handleUpdateTA();
        });
        
        // Controls for removing TAs
        taTable.setOnKeyPressed(e -> {
            if (e.getCode() == KeyCode.DELETE) {
            controller.handleRemoveTA();
            }
        });
        
        //Handles selection on the TA table
        taTable.setOnKeyReleased(e -> {
            if (e.getCode() == KeyCode.UP || e.getCode() == KeyCode.DOWN) {
                controller.handleUpdateTA();
            }
        });
        
        app.getGUI().getPrimaryScene().setOnKeyPressed(e -> {
            if (undo.match(e)) {
                jTPS.undoTransaction();
            }
            else if (redo.match(e)) {
                jTPS.doTransaction();
            }
        });
        
        submitTimesButton.setOnAction(e -> {
            controller.handleTimeSubmission((String) startTimeComboBox.getValue(), (String) endTimeComboBox.getValue());
        });     
    }
    
    public TADataTabBuilder() {
        officeHoursGridPane = new GridPane();
        officeHoursGridTimeHeaderPanes = new HashMap();
        officeHoursGridTimeHeaderLabels = new HashMap();
        officeHoursGridDayHeaderPanes = new HashMap();
        officeHoursGridDayHeaderLabels = new HashMap();
        officeHoursGridTimeCellPanes = new HashMap();
        officeHoursGridTimeCellLabels = new HashMap();
        officeHoursGridTACellPanes = new HashMap();
        officeHoursGridTACellLabels = new HashMap();
    }
    
    public Tab getTab() {        
        return tab;
    }
    
    public jTPS getJTPS() {
        return jTPS;
    }
    
    public TADataController getTADataController() {
        return controller;
    }
    
    public BorderPane getWholePane() {
        return wholePane;
    }
    public HBox getTAsHeaderBox() {
        return tasHeaderBox;
    }

    public Label getTAsHeaderLabel() {
        return tasHeaderLabel;
    }

    public TableView getTATable() {
        return taTable;
    }

    public HBox getAddBox() {
        return addBox;
    }

    public TextField getNameTextField() {
        return nameTextField;
    }
    
    public TextField getEmailTextField() {
        return emailTextField;
    }

    public Button getAddButton() {
        return addButton;
    }
    
    public Button getEditButton() {
        return editButton;
    }
    
    public Button getClearButton() {
        return clearButton;
    }
    

    public HBox getOfficeHoursSubheaderBox() {
        return officeHoursHeaderBox;
    }

    public Label getOfficeHoursSubheaderLabel() {
        return officeHoursHeaderLabel;
    }

    public GridPane getOfficeHoursGridPane() {
        return officeHoursGridPane;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeHeaderPanes() {
        return officeHoursGridTimeHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeHeaderLabels() {
        return officeHoursGridTimeHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridDayHeaderPanes() {
        return officeHoursGridDayHeaderPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridDayHeaderLabels() {
        return officeHoursGridDayHeaderLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTimeCellPanes() {
        return officeHoursGridTimeCellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTimeCellLabels() {
        return officeHoursGridTimeCellLabels;
    }

    public HashMap<String, Pane> getOfficeHoursGridTACellPanes() {
        return officeHoursGridTACellPanes;
    }

    public HashMap<String, Label> getOfficeHoursGridTACellLabels() {
        return officeHoursGridTACellLabels;
    }
    
    public String getTACellKey(Pane testPane) {
        for (String key : officeHoursGridTACellLabels.keySet()) {
            if (officeHoursGridTACellPanes.get(key) == testPane) {
                return key;
            }
        }
        return null;
    }
    
    //Retrieves col and row numbers
    public int getColNumber(String cellKey) {
        return cellKey.charAt(0) - '0';
    }
    
    public int getRowNumber(String cellKey) {
        String [] numbers = cellKey.split("_");
        
        return Integer.parseInt(numbers[1]);  
    }

    public Label getTACellLabel(String cellKey) {
        return officeHoursGridTACellLabels.get(cellKey);
    }

    public Pane getTACellPane(String cellPane) {
        return officeHoursGridTACellPanes.get(cellPane);
    }
    
    public Label getTimeCellLabel(String cellKey) {
        return officeHoursGridTimeCellLabels.get(cellKey);
    }

    public String buildCellKey(int col, int row) {
        return "" + col + "_" + row;
    }

    public String buildCellText(int militaryHour, String minutes) {
        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        if (militaryHour == 0) {
            hour = 12;
        }
        String cellText = "" + hour + ":" + minutes;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }
    
    private Button makeDeleteButton(String icon) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
	
	// LOAD THE ICON FROM THE PROVIDED FILE
        String imagePath = FILE_PROTOCOL + PATH_IMAGES + icon;
        Image buttonImage = new Image(imagePath);
	
	// NOW MAKE THE BUTTON
        Button button = new Button();
        button.setGraphic(new ImageView(buttonImage));
        Tooltip buttonTooltip = new Tooltip(props.getProperty(CSGProp.DELETE_TA_TOOLTIP));
        button.setTooltip(buttonTooltip);
		
	// AND RETURN THE COMPLETED BUTTON
        return button;
    }
    
    public void reloadOfficeHoursGrid(CSGData dataComponent) {        
        ArrayList<String> gridHeaders = dataComponent.getGridHeaders();

        // ADD THE TIME HEADERS
        for (int i = 0; i < 2; i++) {
            addCellToGrid(dataComponent, officeHoursGridTimeHeaderPanes, officeHoursGridTimeHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));
        }
        
        // THEN THE DAY OF WEEK HEADERS
        for (int i = 2; i < 7; i++) {
            addCellToGrid(dataComponent, officeHoursGridDayHeaderPanes, officeHoursGridDayHeaderLabels, i, 0);
            dataComponent.getCellTextProperty(i, 0).set(gridHeaders.get(i));            
        }
        
        // THEN THE TIME AND TA CELLS
        int row = 1;
        for (int i = dataComponent.getStartHour(); i < dataComponent.getEndHour(); i++) {
            // START TIME COLUMN
            int col = 0;
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(i, "00"));
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row+1);
            dataComponent.getCellTextProperty(col, row+1).set(buildCellText(i, "30"));

            // END TIME COLUMN
            col++;
            int endHour = i;
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row);
            dataComponent.getCellTextProperty(col, row).set(buildCellText(endHour, "30"));
            addCellToGrid(dataComponent, officeHoursGridTimeCellPanes, officeHoursGridTimeCellLabels, col, row+1);
            dataComponent.getCellTextProperty(col, row+1).set(buildCellText(endHour+1, "00"));
            col++;

            
            // AND NOW ALL THE TA TOGGLE CELLS
            while (col < 7) {
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row);
                addCellToGrid(dataComponent, officeHoursGridTACellPanes, officeHoursGridTACellLabels, col, row+1);
                col++;
            }
            row += 2;
        }
        
        //testSave = new TestSave(dataComponent);
        
        //Setting sizes for panes in the office hours grid
        for (Pane p: officeHoursGridTimeHeaderPanes.values()) {
            p.setPadding(new Insets(6,15,6,15));
        }
        for (Pane p: officeHoursGridDayHeaderPanes.values()) {
            p.setPadding(new Insets(6,12,6,12));
        }
        for (Pane p: officeHoursGridTACellPanes.values()) {
           p.setPadding(new Insets(2,12,2,12));
       }

        // CONTROLS FOR TOGGLING TA OFFICE HOURS
        for (Pane p : officeHoursGridTACellPanes.values()) {
            p.setOnMouseClicked(e -> {
                controller.handleCellToggle((Pane) e.getSource());
            });
        }
        
        for (Pane p : officeHoursGridTACellPanes.values()) {
            p.setOnMouseEntered(e -> {
                controller.handleCellHighlighting(p, getTACellKey(p));
            });
        }
        
        for (Pane p : officeHoursGridTACellPanes.values()) {
            p.setOnMouseExited(e -> {
                controller.handleCellUnHighlighting(p, getTACellKey(p));
            });
        }
        
        CSGStyle csgStyle = (CSGStyle)app.getStyleComponent();
        csgStyle.initOfficeHoursGridStyle();
    }
    
    public void addCellToGrid(CSGData dataComponent, HashMap<String, Pane> panes, HashMap<String, Label> labels, int col, int row) {       
        // MAKE THE LABEL IN A PANE
        Label cellLabel = new Label("");
        HBox cellPane = new HBox();
        cellPane.setAlignment(Pos.CENTER);
        cellPane.getChildren().add(cellLabel);

        // BUILD A KEY TO EASILY UNIQUELY IDENTIFY THE CELL
        String cellKey = dataComponent.getCellKey(col, row);
        cellPane.setId(cellKey);
        cellLabel.setId(cellKey);
        
        // NOW PUT THE CELL IN THE WORKSPACE GRID
        officeHoursGridPane.add(cellPane, col, row);
        
        // AND ALSO KEEP IN IN CASE WE NEED TO STYLIZE IT
        panes.put(cellKey, cellPane);
        labels.put(cellKey, cellLabel);
        
        // AND FINALLY, GIVE THE TEXT PROPERTY TO THE DATA MANAGER
        // SO IT CAN MANAGE ALL CHANGES
        dataComponent.setCellProperty(col, row, cellLabel.textProperty());        
    }
    
    public void resetWorkspace() {
        // CLEAR OUT THE GRID PANE
        officeHoursGridPane.getChildren().clear();
        
        // AND THEN ALL THE GRID PANES AND LABELS
        officeHoursGridTimeHeaderPanes.clear();
        officeHoursGridTimeHeaderLabels.clear();
        officeHoursGridDayHeaderPanes.clear();
        officeHoursGridDayHeaderLabels.clear();
        officeHoursGridTimeCellPanes.clear();
        officeHoursGridTimeCellLabels.clear();
        officeHoursGridTACellPanes.clear();
        officeHoursGridTACellLabels.clear();
    }
    
    public void modifyOfficeHoursGrid(CSGData dataComponent, String newStartTime, String newEndTime) {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        ObservableList<String> times = dataComponent.getTimes();
        HashMap<String, StringProperty> oldOfficeHours = new HashMap();
        oldOfficeHours.putAll(dataComponent.getOfficeHours());
        
        if (!TAsInRow(oldOfficeHours, dataComponent, times.indexOf(newStartTime), times.indexOf(newEndTime))) {
            return;
        }
        else {
            jTPS_Transaction transaction = new ModifyOfficeHours_Transaction(dataComponent, workspace, newStartTime, newEndTime);
            jTPS.addTransaction(transaction);
        }
    }
    
    public boolean TAsInRow(HashMap<String, StringProperty> officeHours, CSGData dataComponent,
            int newStartPosition, int newEndPosition) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ObservableList<String> times = dataComponent.getTimes();
        OKCancelDialogSingleton okCancelDialog = OKCancelDialogSingleton.getSingleton();
        String oldStartTime = dataComponent.getCellTextProperty(0, 1).get();
        String oldEndTime = dataComponent.getCellTextProperty(1, (dataComponent.getNumRows() - 1)).get();
        int startRow;

        if (newStartPosition > times.indexOf(oldEndTime) || newEndPosition < times.indexOf(oldStartTime)) {
            for (int col = 2; col < 7; col++) {
                for (int row = 1; row < dataComponent.getNumRows(); row++) {
                    String cellKey = dataComponent.getCellKey(col, row);
                    if (!officeHours.get(cellKey).getValue().equals("")) {
                        okCancelDialog.show(props.getProperty(CSGProp.TAS_IN_CELLS_TITLE.toString()), 
                                props.getProperty(CSGProp.TAS_IN_CELLS_MESSAGE.toString()));
                        String selection = okCancelDialog.getSelection();
                        if (selection.equals(props.getProperty(AppPropertyType.OK_TEXT))) {
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                }
            }
        }
        
        if (newStartPosition > times.indexOf(oldStartTime)) {
            startRow = 1;
            int endRow = ((newStartPosition - times.indexOf(oldStartTime)) * 2);
                for (int col = 2; col < 7; col++) {
                    for (int row = startRow; row < (endRow + 1); row++) {
                        if (row == dataComponent.getNumRows()) {
                            return true;
                        }
                        String cellKey = dataComponent.getCellKey(col, row);
                        if (!officeHours.get(cellKey).getValue().equals("")) {
                            okCancelDialog.show(props.getProperty(CSGProp.TAS_IN_CELLS_TITLE.toString()), 
                                    props.getProperty(CSGProp.TAS_IN_CELLS_MESSAGE.toString()));
                            String selection = okCancelDialog.getSelection();
                            if (selection.equals(props.getProperty(AppPropertyType.OK_TEXT))) {
                                return true;
                            }
                            else {
                                return false;
                            }
                        }
                    }
                }
            }
        
        if (newEndPosition < times.indexOf(oldEndTime)) {
            startRow = dataComponent.getNumRows() - 1;
            int numRowsCutOff = (times.indexOf(oldEndTime) - newEndPosition) * 2;
             for (int col = 2; col < 7; col++) {
                for (int row = startRow; row > (startRow - numRowsCutOff); row--) {
                    if (row == 0) {
                        return true;
                    }
                    String cellKey = dataComponent.getCellKey(col, row);
                    if (!officeHours.get(cellKey).getValue().equals("")) {
                        okCancelDialog.show(props.getProperty(CSGProp.TAS_IN_CELLS_TITLE.toString()), 
                                props.getProperty(CSGProp.TAS_IN_CELLS_MESSAGE.toString()));
                        String selection = okCancelDialog.getSelection();
                        if (selection.equals(props.getProperty(AppPropertyType.OK_TEXT))) {
                            return true;
                        }
                        else {
                            return false;
                        }
                    }
                }
            }
        }
        return true;
    }
    
    
}
