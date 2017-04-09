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
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import properties_manager.PropertiesManager;

/**
 *
 * @author Alvaro
 */
public class RecitationTabBuilder {
    CSGApp app;
    Tab tab;
    VBox wholePane;
    HBox header;
    GridPane bottomPane;
    
    TableView recitationTable;
    TableColumn<Recitation, String> sectionColumn;
    TableColumn<Recitation, String> instructorColumn;
    TableColumn<Recitation, String> dayTimeColumn;
    TableColumn<Recitation, String> locationColumn;
    TableColumn<Recitation, String> ta1Column;
    TableColumn<Recitation, String> ta2Column;
    
    Button deleteButton, addUpdateButton, clearButton;
    Label headerText, addEditHeaderText, sectionText, instructorText, dayTimeText, locationText,
            ta1Text, ta2Text;
    TextField sectionTextField, instructorTextField, dayTimeTextField, locationTextField;
    ComboBox ta1Box, ta2Box;
    
    
    public RecitationTabBuilder(CSGApp initApp) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        app = initApp;
        tab = new Tab(props.getProperty(CSGProp.RECITATION_TAB));
        wholePane = new VBox(4);
        
        header = makeHeader();
        
        recitationTable = new TableView();
        CSGData data = (CSGData) app.getDataComponent();
        ObservableList<Recitation> recitations = data.getRecitations();
        recitationTable.setItems(recitations);
        initializeTable();
        
        bottomPane = buildBottomPane();
        
        wholePane.setPadding(new Insets(15, 15, 15, 15));
        wholePane.getChildren().add(header);
        wholePane.getChildren().add(recitationTable);
        wholePane.getChildren().add(bottomPane);
        tab.setContent(wholePane);
    }
    
    public Tab getTab() {
        return tab;
    }
    
    public GridPane getBottomPane() {
        return bottomPane;
    }
    
    private HBox makeHeader() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        HBox hbox = new HBox(4);
        headerText = new Label(props.getProperty(CSGProp.RECITATION_HEADER));
        deleteButton = new Button("-");
        hbox.getChildren().addAll(headerText, deleteButton);
        
        return hbox;
    }
    
    private void initializeTable() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        sectionColumn = new TableColumn(props.getProperty(CSGProp.SECTION_TEXT));
        instructorColumn = new TableColumn(props.getProperty(CSGProp.INSTRUCTOR_TEXT));
        dayTimeColumn = new TableColumn(props.getProperty(CSGProp.DAY_TIME_TEXT));
        locationColumn = new TableColumn(props.getProperty(CSGProp.LOCATION_TEXT));
        ta1Column = new TableColumn(props.getProperty(CSGProp.TA_HEADER));
        ta2Column = new TableColumn(props.getProperty(CSGProp.TA_HEADER));
        
        recitationTable.getColumns().add(sectionColumn);
        recitationTable.getColumns().add(instructorColumn);
        recitationTable.getColumns().add(dayTimeColumn);
        recitationTable.getColumns().add(locationColumn);
        recitationTable.getColumns().add(ta1Column);
        recitationTable.getColumns().add(ta2Column);
        
        recitationTable.setFixedCellSize(25);
        recitationTable.prefHeightProperty().bind(recitationTable.fixedCellSizeProperty().multiply(10));
        recitationTable.minHeightProperty().bind(recitationTable.prefHeightProperty());
        recitationTable.maxHeightProperty().bind(recitationTable.prefHeightProperty());
    }
    
    private GridPane buildBottomPane() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setVgap(10);
        grid.setHgap(10);
        grid.setPadding(new Insets(15,15,15,15));
        
        addEditHeaderText = new Label(props.getProperty(CSGProp.ADD_EDIT_HEADER));
        sectionText = new Label(props.getProperty(CSGProp.SECTION_TEXT));
        instructorText = new Label(props.getProperty(CSGProp.INSTRUCTOR_TEXT));
        dayTimeText = new Label(props.getProperty(CSGProp.DAY_TIME_TEXT));
        locationText = new Label(props.getProperty(CSGProp.LOCATION_TEXT));
        ta1Text = new Label(props.getProperty(CSGProp.SUPERVISING_TA_TEXT));
        ta2Text = new Label(props.getProperty(CSGProp.SUPERVISING_TA_TEXT));
        addUpdateButton = new Button(props.getProperty(CSGProp.ADD_UPDATE_BUTTON_TEXT));
        
        grid.add(addEditHeaderText, 0, 0);
        grid.add(sectionText, 0, 1);
        grid.add(instructorText, 0, 2);
        grid.add(dayTimeText, 0, 3);
        grid.add(locationText, 0, 4);
        grid.add(ta1Text, 0, 5);
        grid.add(ta2Text, 0, 6);
        grid.add(addUpdateButton, 0, 7);

        sectionTextField = new TextField();
        instructorTextField = new TextField();
        dayTimeTextField = new TextField();
        locationTextField = new TextField();
        ta1Box = new ComboBox();
        ta2Box = new ComboBox();
        clearButton = new Button(props.getProperty(CSGProp.CLEAR_BUTTON_TEXT));
        
        grid.add(sectionTextField, 1, 1, 2, 1);
        grid.add(instructorTextField, 1, 2, 2, 1);
        grid.add(dayTimeTextField, 1, 3, 2, 1);
        grid.add(locationTextField, 1, 4, 2, 1);
        grid.add(ta1Box, 1, 5, 2, 1);
        grid.add(ta2Box, 1, 6, 2, 1);
        grid.add(clearButton, 1, 7);

        return grid;
    }
}
