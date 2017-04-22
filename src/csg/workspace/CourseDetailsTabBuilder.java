/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import csg.CSGProp;
import csg.data.CSGData;
import csg.data.SitePage;
import javafx.beans.binding.Bindings;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.control.Label;
import javafx.scene.control.Tab;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableColumn.CellDataFeatures;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.cell.CheckBoxTableCell;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.util.Callback;
import properties_manager.PropertiesManager;

/**
 *
 * @author Alvaro Quintanilla, ID: 110289649
 */
public class CourseDetailsTabBuilder {
    CSGApp app;
    Tab tab;
    GridPane topPane, bottomPane;
    VBox centerPane;
    BorderPane wholePane;
    VBox allPane;
    
    HBox imageBox, leftFooterBox, rightFooterBox;
    
    Button changeButton1, changeButton2, changeButton3, changeButton4, selectTemplateDirButton;
    Label topHeader, centerHeader, bottomHeader;
    Label subject, semester, number, year, title, instructorName, instructorHome, exportDirectory,
            defaultDirText, defaultTemplateText, siteTemplateNote, sitePagesText, 
            pageStyleText, bannerText, leftFooterText, rightFooterText, stylesheetText, noImageText1, 
            noImageText2, noImageText3, noteText;
    ComboBox<String> subjectBox, semesterBox, numberBox, yearBox, stylesheetBox;
    TextField titleTextField, instructorNameTextField, instructorHomeTextField;
    
    TableView<SitePage> sitePageTable;
    TableColumn<SitePage, Boolean> useColumn;
    TableColumn<SitePage, String> navBarColumn;
    TableColumn<SitePage, String> fileNameColumn;
    TableColumn<SitePage, String> scriptColumn;


    public CourseDetailsTabBuilder(CSGApp initApp) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        app = initApp;
        wholePane = new BorderPane();
        allPane = new VBox(2);
       
        topPane = buildTopGrid();
        centerPane = buildCenterGrid();
        bottomPane = buildBottomGrid();
        
        
        tab = new Tab(props.getProperty(CSGProp.COURSE_DETAILS_TAB));
        
        wholePane.setTop(topPane);
        wholePane.setPadding(new Insets(15,15,15,15));
        wholePane.setCenter(centerPane);
        wholePane.setBottom(bottomPane);
        allPane.setPadding(new Insets(7,15,15,15));
        allPane.getChildren().addAll(topPane, centerPane, bottomPane);
        tab.setContent(allPane);   
    }
    
    public Tab getTab() {
        return tab;
    }
    
    public BorderPane getWholePane() {
        return wholePane;
    }
    
    public VBox getAllPane() {
        return allPane;
    }
    
    public Label getTopHeaderLabel() {
        return topHeader;
    }
    
    public Label getCenterHeaderLabel() {
        return centerHeader;
    }
    
    public Label getBottomHeaderLabel() {
        return pageStyleText;
    }
    
    public Pane getTopPane() {
        return topPane;
    }
    
    public Pane getCenterPane() {
        return centerPane;
    }
    
    public Pane getBottomPane() {
        return bottomPane;
    }
    
    public TableView getSitePageTable() {
        return sitePageTable;
    }
    
    private GridPane buildTopGrid() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        CSGData data = (CSGData) app.getDataComponent();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setVgap(5);
        grid.setHgap(10);
        grid.setPadding(new Insets(15,15,15,15));
        
        
        //First column in grid
        topHeader = new Label(props.getProperty(CSGProp.COURSE_INFO_TEXT));
        subject = new Label(props.getProperty(CSGProp.SUBJECT_TEXT) + ":");
        semester = new Label(props.getProperty(CSGProp.SEMESTER_TEXT) + ":");
        number = new Label(props.getProperty(CSGProp.NUMBER_TEXT) + ":");
        year = new Label(props.getProperty(CSGProp.YEAR_TEXT) + ":");
        title = new Label(props.getProperty(CSGProp.TITLE_TEXT) + ":");
        instructorName = new Label(props.getProperty(CSGProp.INSTRUCTOR_NAME_TEXT) + ":");
        instructorHome = new Label(props.getProperty(CSGProp.INSTRUCTOR_HOME_TEXT) + ":");
        exportDirectory = new Label(props.getProperty(CSGProp.EXPORT_DIR_TEXT) + ":");
        
        grid.add(topHeader, 0, 0);
        grid.add(subject, 0, 1);
        grid.add(semester, 0, 2);
        grid.add(title, 0, 3);
        grid.add(instructorName, 0, 4);
        grid.add(instructorHome, 0, 5);
        grid.add(exportDirectory, 0, 6);
        
        //Second column in grid
        subjectBox = new ComboBox();
        semesterBox = new ComboBox();
        titleTextField = new TextField();
        instructorNameTextField = new TextField();
        instructorHomeTextField = new TextField();
        defaultDirText = new Label(props.getProperty(CSGProp.DEFAULT_DIR_TEXT));
               
        grid.add(subjectBox, 1, 1);
        grid.add(semesterBox, 1, 2);
        grid.add(titleTextField, 1, 3, 3, 1);
        grid.add(instructorNameTextField, 1, 4, 3, 1);
        grid.add(instructorHomeTextField, 1, 5, 3, 1);
        grid.add(defaultDirText, 1, 6);
        
        //Third column in grid
        number = new Label(props.getProperty(CSGProp.NUMBER_TEXT) + ":");
        year = new Label(props.getProperty(CSGProp.YEAR_TEXT) + ":");
        
        grid.add(number, 2, 1);
        grid.add(year, 2, 2);
        
        //Fourth column in grid
        numberBox = new ComboBox();
        yearBox = new ComboBox();
        changeButton1 = new Button(props.getProperty(CSGProp.CHANGE_BUTTON_TEXT));
        
        grid.add(numberBox, 3, 1);
        grid.add(yearBox, 3, 2);
        grid.add(changeButton1, 3, 6);
        
        return grid;
    }
    
    private VBox buildCenterGrid() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        VBox vbox = new VBox(4);
        vbox.setPadding(new Insets(15,15,15,15));
        CSGData data = (CSGData) app.getDataComponent();
        
        centerHeader = new Label(props.getProperty(CSGProp.SITE_TEMPLATE_TEXT));
        siteTemplateNote = new Label(props.getProperty(CSGProp.SITE_TEMPLATE_NOTE));
        defaultTemplateText = new Label(data.getTemplateDirectory());
        selectTemplateDirButton = new Button(props.getProperty(CSGProp.SELECT_TEMPLATE_DIR_BUTTON_TEXT));
        sitePagesText = new Label(props.getProperty(CSGProp.SITE_PAGES_TEXT));
        
        vbox.getChildren().add(centerHeader);
        vbox.getChildren().add(siteTemplateNote);
        vbox.getChildren().add(defaultTemplateText);
        vbox.getChildren().add(selectTemplateDirButton);
        vbox.getChildren().add(sitePagesText);
        
        sitePageTable = new TableView();
        ObservableList<SitePage> sitePages = data.getSitePages();
        sitePageTable.setItems(sitePages);
        
        useColumn = new TableColumn(props.getProperty(CSGProp.USE_HEADER));
        navBarColumn = new TableColumn(props.getProperty(CSGProp.NAVBAR_TITLE_HEADER));
        fileNameColumn = new TableColumn(props.getProperty(CSGProp.FILE_NAME_HEADER));
        scriptColumn = new TableColumn(props.getProperty(CSGProp.SCRIPT_HEADER));
        
        useColumn.setCellValueFactory(
            param -> param.getValue().isUsed()
        );
        
        useColumn.setCellFactory(CheckBoxTableCell.forTableColumn(useColumn));
        

        navBarColumn.setCellValueFactory(
                new PropertyValueFactory<SitePage, String>("navbarTitle")
        );
        
        fileNameColumn.setCellValueFactory(
                new PropertyValueFactory<SitePage, String>("fileName")
        );
        
        scriptColumn.setCellValueFactory(
                new PropertyValueFactory<SitePage, String>("script")
        );
      
        sitePageTable.getColumns().add(useColumn);
        sitePageTable.getColumns().add(navBarColumn);
        sitePageTable.getColumns().add(fileNameColumn);
        sitePageTable.getColumns().add(scriptColumn);
        sitePages = data.makeSitePages(sitePages);

        sitePageTable.setFixedCellSize(25);
        sitePageTable.prefHeightProperty().bind(sitePageTable.fixedCellSizeProperty().multiply(Bindings.size(sitePageTable.getItems()).add(1.01)));
        sitePageTable.minHeightProperty().bind(sitePageTable.prefHeightProperty());
        sitePageTable.maxHeightProperty().bind(sitePageTable.prefHeightProperty());
        
        sitePageTable.setEditable(true);
             
        vbox.getChildren().add(sitePageTable);

        return vbox;
    }
    
    private GridPane buildBottomGrid() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_LEFT);
        grid.setVgap(5);
        grid.setHgap(10);
        grid.setPadding(new Insets(15,15,15,15));
        
        //pageStyleText, bannerText, leftFooterText, rightFooterText, stylesheetText, noteText;
        
        pageStyleText= new Label(props.getProperty(CSGProp.PAGE_STYLE_TEXT));
        bannerText = new Label(props.getProperty(CSGProp.BANNER_TEXT) + ":");
        leftFooterText = new Label(props.getProperty(CSGProp.LEFT_FOOTER_TEXT) + ":");
        rightFooterText = new Label(props.getProperty(CSGProp.RIGHT_FOOTER_TEXT) + ":");
        stylesheetText = new Label(props.getProperty(CSGProp.STYLESHEET_TEXT) + ":");
        noteText = new Label(props.getProperty(CSGProp.NOTE_TEXT));
        
        grid.add(pageStyleText, 0, 0);
        grid.add(bannerText, 0, 1);
        grid.add(leftFooterText, 0, 2);
        grid.add(rightFooterText, 0, 3);
        grid.add(stylesheetText, 0, 4);
        grid.add(noteText, 0, 5, 4, 1);
        
        noImageText1 = new Label();
        noImageText2 = new Label();
        noImageText3 = new Label();
        stylesheetBox = new ComboBox();
        
        imageBox = new HBox();
        leftFooterBox = new HBox();
        rightFooterBox = new HBox();
        
        grid.add(imageBox, 1, 1);
        grid.add(leftFooterBox, 1, 2);
        grid.add(rightFooterBox, 1, 3);
        grid.add(stylesheetBox, 1, 4, 2, 1);
        
        changeButton2 = new Button(props.getProperty(CSGProp.CHANGE_BUTTON_TEXT));
        changeButton3 = new Button(props.getProperty(CSGProp.CHANGE_BUTTON_TEXT));
        changeButton4 = new Button(props.getProperty(CSGProp.CHANGE_BUTTON_TEXT));
        
        grid.add(changeButton2, 2, 1);
        grid.add(changeButton3, 2, 2);
        grid.add(changeButton4, 2, 3);
        
        return grid;
    }
    
    public void reloadCourseInfo(CSGData data) {
        subjectBox.getSelectionModel().select(data.getCourseInfo().get(0));
        semesterBox.getSelectionModel().select(data.getCourseInfo().get(1));
        numberBox.getSelectionModel().select(data.getCourseInfo().get(2));
        yearBox.getSelectionModel().select(data.getCourseInfo().get(3));
        titleTextField.setText(data.getCourseInfo().get(4));
        instructorNameTextField.setText(data.getCourseInfo().get(5));
        instructorHomeTextField.setText(data.getCourseInfo().get(6));
        defaultDirText.setText(data.getCourseInfo().get(7));
        
        //Reloads Site Template Directory
        defaultTemplateText.setText(data.getTemplateDirectory());
    }
    
    
    
    public void reloadPageStyle(CSGData data) {
        PropertiesManager props = PropertiesManager.getPropertiesManager();

        String imagePath = data.getImagePath();
        String leftFooterPath = data.getLeftFooterImagePath();
        String rightFooterPath = data.getRightFooterImagePath();
        
        //Main banner
        if (imagePath.equals(props.getProperty(CSGProp.NO_IMAGE_SELECTED_TEXT))) {
            noImageText1.setText(imagePath);
            imageBox.getChildren().clear();
            imageBox.getChildren().add(noImageText1);
        }
        else {
            Image banner = new Image("file:" + imagePath, 150, 25, false, false);
            ImageView bannerView = new ImageView(banner);
            imageBox.getChildren().clear();
            imageBox.getChildren().add(bannerView);
        }
        
        //Left Footer
        if (leftFooterPath.equals(props.getProperty(CSGProp.NO_IMAGE_SELECTED_TEXT))) {
            noImageText2.setText(leftFooterPath);
            leftFooterBox.getChildren().clear();
            leftFooterBox.getChildren().add(noImageText2);
        }
        else {
            Image leftFooter = new Image("file:" + leftFooterPath, 150, 25, false, false);
            ImageView leftFooterView = new ImageView(leftFooter);
            leftFooterBox.getChildren().clear();
            leftFooterBox.getChildren().add(leftFooterView);
        }
        
        //Right Footer
        if (rightFooterPath.equals(props.getProperty(CSGProp.NO_IMAGE_SELECTED_TEXT))) {
            noImageText3.setText(rightFooterPath);
            rightFooterBox.getChildren().clear();
            rightFooterBox.getChildren().add(noImageText3);
        }
        else {
            Image rightFooter = new Image("file:" + rightFooterPath, 150, 25, false, false);
            ImageView rightFooterView = new ImageView(rightFooter);
            rightFooterBox.getChildren().clear();
            rightFooterBox.getChildren().add(rightFooterView);
        }
        
        //Finally, the style combo box
        stylesheetBox.getSelectionModel().select(data.getStylesheet());
    }
    
}
