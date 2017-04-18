/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.style;

import csg.data.CSGData;
import csg.data.Recitation;
import csg.data.ScheduleItem;
import csg.data.SitePage;
import csg.data.Student;
import csg.data.TeachingAssistant;
import csg.data.Team;
import csg.test_bed.TestSave;
import csg.workspace.CSGWorkspace;
import csg.workspace.CourseDetailsTabBuilder;
import csg.workspace.TADataTabBuilder;
import djf.AppTemplate;
import djf.components.AppStyleComponent;
import java.util.HashMap;
import javafx.scene.Node;
import javafx.scene.control.TabPane;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.layout.Pane;

/**
 *
 * @author Alvaro Quintanilla, ID: 110289649
 */
public class CSGStyle extends AppStyleComponent {
    private AppTemplate app;
    public static String CLASS_INFO_PANE = "info_pane";
    public static String TAB_LABEL = "tab_label";
    public static String PANE = "pane";
    public static String WHOLE_PANE = "whole_pane";
    public static String CLASS_TABLE = "table";
    public static String CLASS_TABLE_COLUMN_HEADER = "table_column_header";
    
    public static String CLASS_TA_HEADER_PANE = "ta_header_pane";
    public static String HEADER_LABEL = "header_label";
    public static String SUBHEADER_LABEL = "subheader_label";
    
    // ON THE LEFT WE HAVE THE TA ENTRY
    
    public static String CLASS_ADD_TA_PANE = "add_ta_pane";
    public static String CLASS_ADD_TA_NAME_TEXT_FIELD = "add_ta_name_text_field";
    public static String CLASS_ADD_TA_EMAIL_TEXT_FIELD = "add_ta_email_text_field";
    public static String CLASS_ADD_TA_BUTTON = "add_ta_button";
    public static String CLASS_UPDATE_TA_BUTTON = "update_ta_button";
    public static String CLASS_SUBMIT_TIME_BUTTON = "submit_time_button";
    public static String CLASS_CLEAR_TA_BUTTON = "clear_ta_button";
    
    public static String CLASS_OFFICE_HOURS_GRID = "office_hours_grid";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_PANE = "office_hours_grid_time_column_header_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_LABEL = "office_hours_grid_time_column_header_label";
    public static String CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_PANE = "office_hours_grid_day_column_header_pane";
    public static String CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_LABEL = "office_hours_grid_day_column_header_label";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_CELL_PANE = "office_hours_grid_time_cell_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TIME_CELL_LABEL = "office_hours_grid_time_cell_label";
    public static String CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE = "office_hours_grid_ta_cell_pane";
    public static String CLASS_OFFICE_HOURS_GRID_TA_CELL_LABEL = "office_hours_grid_ta_cell_label";

    public CSGStyle(AppTemplate initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
        
        super.initStylesheet(app);
        app.getGUI().initFileToolbarStyle();
        app.getGUI().initEditToolbarStyle();
        app.getGUI().initHeaderStyle();
        app.getGUI().initBackgroundStyle();
        initCourseWorkspace();
    }
    
    private void initCourseWorkspace() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        
        workspace.getTabs().getStyleClass().add(PANE);
        workspace.getCDTabBuilder().getTopHeaderLabel().getStyleClass().add(SUBHEADER_LABEL);
        workspace.getCDTabBuilder().getCenterHeaderLabel().getStyleClass().add(SUBHEADER_LABEL);
        workspace.getCDTabBuilder().getBottomHeaderLabel().getStyleClass().add(SUBHEADER_LABEL);
        workspace.getCDTabBuilder().getTopPane().getStyleClass().add(PANE);
        workspace.getCDTabBuilder().getCenterPane().getStyleClass().add(PANE);
        workspace.getCDTabBuilder().getBottomPane().getStyleClass().add(PANE);
        workspace.getCDTabBuilder().getAllPane().getStyleClass().add(WHOLE_PANE);
        TableView<SitePage> sitePageTable = workspace.getCDTabBuilder().getSitePageTable();
        sitePageTable.getStyleClass().add(CLASS_TABLE);
        for (TableColumn tableColumn : sitePageTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TABLE_COLUMN_HEADER);
        }
        
        
        workspace.getTATabBuilder().getTAsHeaderLabel().getStyleClass().add(HEADER_LABEL);
        workspace.getTATabBuilder().getTAsHeaderBox().getStyleClass().add(CLASS_TA_HEADER_PANE);
        workspace.getTATabBuilder().getOfficeHoursSubheaderBox().getStyleClass().add(CLASS_TA_HEADER_PANE);
        workspace.getTATabBuilder().getOfficeHoursSubheaderLabel().getStyleClass().add(HEADER_LABEL);
        TableView<TeachingAssistant> taTable = workspace.getTATabBuilder().getTATable();
        taTable.getStyleClass().add(CLASS_TABLE);
        for (TableColumn tableColumn : taTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TABLE_COLUMN_HEADER);
        }
        
        workspace.getRecitationTabBuilder().getRecitationHeaderLabel().getStyleClass().add(HEADER_LABEL);
        workspace.getRecitationTabBuilder().getAddEditLabel().getStyleClass().add(SUBHEADER_LABEL);
        workspace.getRecitationTabBuilder().getRecitationTable().getStyleClass().add(PANE);
        workspace.getRecitationTabBuilder().getBottomPane().getStyleClass().add(PANE);
        workspace.getRecitationTabBuilder().getWholePane().getStyleClass().add(WHOLE_PANE);
        TableView<Recitation> recitationTable = workspace.getRecitationTabBuilder().getRecitationTable();
        recitationTable.getStyleClass().add(CLASS_TABLE);
        for (TableColumn tableColumn : recitationTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TABLE_COLUMN_HEADER);
        }
        
        workspace.getScheduleTabBuilder().getScheduleHeaderLabel().getStyleClass().add(HEADER_LABEL);
        workspace.getScheduleTabBuilder().getCalendarBoundsLabel().getStyleClass().add(SUBHEADER_LABEL);
        workspace.getScheduleTabBuilder().getScheduleItemsLabel().getStyleClass().add(SUBHEADER_LABEL);
        workspace.getScheduleTabBuilder().getAddEditLabel().getStyleClass().add(SUBHEADER_LABEL);
        workspace.getScheduleTabBuilder().getTopPane().getStyleClass().add(PANE);
        workspace.getScheduleTabBuilder().getBottomPane().getStyleClass().add(PANE);
        workspace.getScheduleTabBuilder().getWholePane().getStyleClass().add(WHOLE_PANE);
        TableView<ScheduleItem> scheduleTable = workspace.getScheduleTabBuilder().getScheduleTable();
        scheduleTable.getStyleClass().add(CLASS_TABLE);
        for (TableColumn tableColumn : scheduleTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TABLE_COLUMN_HEADER);
        }


        workspace.getProjectTabBuilder().getProjectHeaderLabel().getStyleClass().add(HEADER_LABEL);
        workspace.getProjectTabBuilder().getTopPane().getStyleClass().add(PANE);
        workspace.getProjectTabBuilder().getBottomPane().getStyleClass().add(PANE);
        workspace.getProjectTabBuilder().getWholePane().getStyleClass().add(WHOLE_PANE);
        workspace.getProjectTabBuilder().getTeamsLabel().getStyleClass().add(SUBHEADER_LABEL);
        workspace.getProjectTabBuilder().getStudentsLabel().getStyleClass().add(SUBHEADER_LABEL);
        workspace.getProjectTabBuilder().getAddEditLabel1().getStyleClass().add(SUBHEADER_LABEL);
        workspace.getProjectTabBuilder().getAddEditLabel2().getStyleClass().add(SUBHEADER_LABEL);
        TableView<Team> teamTable = workspace.getProjectTabBuilder().getTeamTable();
        teamTable.getStyleClass().add(CLASS_TABLE);
        for (TableColumn tableColumn : teamTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TABLE_COLUMN_HEADER);
        }
        TableView<Student> studentTable = workspace.getProjectTabBuilder().getStudentTable();
        studentTable.getStyleClass().add(CLASS_TABLE);
        for (TableColumn tableColumn : studentTable.getColumns()) {
            tableColumn.getStyleClass().add(CLASS_TABLE_COLUMN_HEADER);
        }
        
        workspace.getWorkspace().getStyleClass().add(TAB_LABEL);
    }
    
    public void initOfficeHoursGridStyle() {
        // RIGHT SIDE - THE OFFICE HOURS GRID TIME HEADERS
        CSGWorkspace workspaceComponent = (CSGWorkspace)app.getWorkspaceComponent();
        TADataTabBuilder taWorkspaceComponent = workspaceComponent.getTATabBuilder();
        taWorkspaceComponent.getOfficeHoursGridPane().getStyleClass().add(CLASS_OFFICE_HOURS_GRID);
        setStyleClassOnAll(taWorkspaceComponent.getOfficeHoursGridTimeHeaderPanes(), CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_PANE);
        setStyleClassOnAll(taWorkspaceComponent.getOfficeHoursGridTimeHeaderLabels(), CLASS_OFFICE_HOURS_GRID_TIME_COLUMN_HEADER_LABEL);
        setStyleClassOnAll(taWorkspaceComponent.getOfficeHoursGridDayHeaderPanes(), CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_PANE);
        setStyleClassOnAll(taWorkspaceComponent.getOfficeHoursGridDayHeaderLabels(), CLASS_OFFICE_HOURS_GRID_DAY_COLUMN_HEADER_LABEL);
        setStyleClassOnAll(taWorkspaceComponent.getOfficeHoursGridTimeCellPanes(), CLASS_OFFICE_HOURS_GRID_TIME_CELL_PANE);
        setStyleClassOnAll(taWorkspaceComponent.getOfficeHoursGridTimeCellLabels(), CLASS_OFFICE_HOURS_GRID_TIME_CELL_LABEL);
        setStyleClassOnAll(taWorkspaceComponent.getOfficeHoursGridTACellPanes(), CLASS_OFFICE_HOURS_GRID_TA_CELL_PANE);
        setStyleClassOnAll(taWorkspaceComponent.getOfficeHoursGridTACellLabels(), CLASS_OFFICE_HOURS_GRID_TA_CELL_LABEL);
    }
    
    public void highlightCells(Pane highlightedCell, String key) {
        CSGWorkspace workspaceComponent = (CSGWorkspace) app.getWorkspaceComponent();
        TADataTabBuilder taWorkspaceComponent = workspaceComponent.getTATabBuilder();
        HashMap<String, Pane> officeHoursGridTACellPanes = taWorkspaceComponent.getOfficeHoursGridTACellPanes();
        HashMap<String, Pane> officeHoursGridDayHeaderPanes = taWorkspaceComponent.getOfficeHoursGridDayHeaderPanes();
        HashMap<String, Pane> officeHoursGridTimeCellPanes = taWorkspaceComponent.getOfficeHoursGridTimeCellPanes();

        int highlightedCellCol = taWorkspaceComponent.getColNumber(key); 
        int highlightedCellRow = taWorkspaceComponent.getRowNumber(key);
        
        //Highlights cells above the main, highlighted cell the mouse is over.
        for (int row = 1; row < highlightedCellRow; row++) {
            officeHoursGridTACellPanes.get(taWorkspaceComponent.buildCellKey(highlightedCellCol, row)).setStyle("-fx-border-color: #FFFFAA;");
        }
        
        //Highlights cells to the left of the main, highlighted cell the mouse is over.
        for (int col = 2; col < highlightedCellCol; col++) {
            officeHoursGridTACellPanes.get(taWorkspaceComponent.buildCellKey(col, highlightedCellRow)).setStyle("-fx-border-color: #FFFFAA;");
        }
        
        //Highlights header panes for days and time
        officeHoursGridDayHeaderPanes.get(taWorkspaceComponent.buildCellKey(highlightedCellCol, 0)).setStyle("-fx-border-color: #FFFFAA;");
        officeHoursGridTimeCellPanes.get(taWorkspaceComponent.buildCellKey(0, highlightedCellRow)).setStyle("-fx-border-color: #FFFFAA; -fx-border-style: solid");
        officeHoursGridTimeCellPanes.get(taWorkspaceComponent.buildCellKey(1, highlightedCellRow)).setStyle("-fx-border-color: #FFFFAA; -fx-border-style: solid");

        highlightedCell.setStyle("-fx-border-color: yellow;");
    }
    
    /**
     * This method is responsible for unhighlighting the cell that the 
     * mouse exits in the office hour girds and all of the cells above or the 
     * left of it.
     * @param highlightedCell
     *      The cell to be highlighted
     * @param key 
     *      The key of the cell to obtain the row and column number of the 
     *      adjacent cells to be highlighted.
     */
    public void unhighlightCells(Pane highlightedCell, String key) {
        CSGWorkspace workspaceComponent = (CSGWorkspace) app.getWorkspaceComponent();
        TADataTabBuilder taWorkspaceComponent = workspaceComponent.getTATabBuilder();
        HashMap<String, Pane> officeHoursGridTACellPanes = taWorkspaceComponent.getOfficeHoursGridTACellPanes();
        HashMap<String, Pane> officeHoursGridDayHeaderPanes = taWorkspaceComponent.getOfficeHoursGridDayHeaderPanes();
        HashMap<String, Pane> officeHoursGridTimeCellPanes = taWorkspaceComponent.getOfficeHoursGridTimeCellPanes();
        int highlightedCellCol = taWorkspaceComponent.getColNumber(key); 
        int highlightedCellRow = taWorkspaceComponent.getRowNumber(key);
        
        for (int i = 1; i < highlightedCellRow; i++) {
            officeHoursGridTACellPanes.get(taWorkspaceComponent.buildCellKey(highlightedCellCol, i)).setStyle("-fx-border-color: black;");
        }
        
        for (int col = 2; col < highlightedCellCol; col++) {
            officeHoursGridTACellPanes.get(taWorkspaceComponent.buildCellKey(col, highlightedCellRow)).setStyle("-fx-border-color: black;");
        }
        
        officeHoursGridDayHeaderPanes.get(taWorkspaceComponent.buildCellKey(highlightedCellCol, 0)).setStyle("-fx-border: none");
        officeHoursGridTimeCellPanes.get(taWorkspaceComponent.buildCellKey(0, highlightedCellRow)).setStyle("-fx-border-color: white;");
        officeHoursGridTimeCellPanes.get(taWorkspaceComponent.buildCellKey(1, highlightedCellRow)).setStyle("-fx-border-color: white");

        highlightedCell.setStyle("-fx-border-color: black;");
    }
    private void setStyleClassOnAll(HashMap nodes, String styleClass) {
        for (Object nodeObject : nodes.values()) {
            Node n = (Node)nodeObject;
            n.getStyleClass().add(styleClass);
        }
    }
}
