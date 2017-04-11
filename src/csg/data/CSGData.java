/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import csg.CSGApp;
import csg.CSGProp;
import csg.workspace.CSGWorkspace;
import csg.transactions.*;
import csg.workspace.TADataTabBuilder;
import djf.components.AppDataComponent;
import djf.controller.AppFileController;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import jtps.jTPS_Transaction;
import properties_manager.PropertiesManager;

/**
 *
 * @author Alvaro
 */
public class CSGData implements AppDataComponent {
    CSGApp app;
    AppFileController fileController;
    ObservableList<SitePage> sitePages;
    ObservableList<TeachingAssistant> teachingAssistants;
    ObservableList<Recitation> recitations;
    ObservableList<ScheduleItem> scheduleItems;
    ObservableList<Team> teams;
    ObservableList<Student> students;
    
    // THIS WILL STORE ALL THE OFFICE HOURS GRID DATA, WHICH YOU
    // SHOULD NOTE ARE StringProperty OBJECTS THAT ARE CONNECTED
    // TO UI LABELS, WHICH MEANS IF WE CHANGE VALUES IN THESE
    // PROPERTIES IT CHANGES WHAT APPEARS IN THOSE LABELS
    HashMap<String, StringProperty> officeHours;
    
    // THESE ARE THE LANGUAGE-DEPENDENT VALUES FOR
    // THE OFFICE HOURS GRID HEADERS. NOTE THAT WE
    // LOAD THESE ONCE AND THEN HANG ON TO THEM TO
    // INITIALIZE OUR OFFICE HOURS GRID
    ArrayList<String> gridHeaders;

    // THESE ARE THE TIME BOUNDS FOR THE OFFICE HOURS GRID. NOTE
    // THAT THESE VALUES CAN BE DIFFERENT FOR DIFFERENT FILES, BUT
    // THAT OUR APPLICATION USES THE DEFAULT TIME VALUES AND PROVIDES
    // NO MEANS FOR CHANGING THESE VALUES
    int startHour;
    int endHour;
    
    // DEFAULT VALUES FOR START AND END HOURS IN MILITARY HOURS
    public static final int MIN_START_HOUR = 9;
    public static final int MAX_END_HOUR = 20;

    
    public CSGData(CSGApp initApp) {
        app = initApp;
        fileController = app.getGUI().getFileController();
        sitePages = FXCollections.observableArrayList();
        teachingAssistants = FXCollections.observableArrayList();
        recitations = FXCollections.observableArrayList();
        scheduleItems = FXCollections.observableArrayList();
        teams = FXCollections.observableArrayList();
        students = FXCollections.observableArrayList();
        
        // THESE ARE THE DEFAULT OFFICE HOURS
        startHour = MIN_START_HOUR;
        endHour = MAX_END_HOUR;
        
        //THIS WILL STORE OUR OFFICE HOURS
        officeHours = new HashMap();
        
        // THESE ARE THE LANGUAGE-DEPENDENT OFFICE HOURS GRID HEADERS
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        ArrayList<String> timeHeaders = props.getPropertyOptionsList(CSGProp.OFFICE_HOURS_TABLE_HEADERS);
        ArrayList<String> dowHeaders = props.getPropertyOptionsList(CSGProp.DAYS_OF_WEEK);
        gridHeaders = new ArrayList();
        gridHeaders.addAll(timeHeaders);
        gridHeaders.addAll(dowHeaders);
    }
    
     public CSGApp getApp() {
        return app;
    }
    
    public AppFileController getFileController() {
        return fileController;
    }
    
    public int getStartHour() {
        return startHour;
    }

    public int getEndHour() {
        return endHour;
    }
    
    public ArrayList<String> getGridHeaders() {
        return gridHeaders;
    }
    public ObservableList getSitePages() {
        return sitePages;
    }
    
    public ObservableList getTeachingAssistants() {
        return teachingAssistants;
    }

    public ObservableList getRecitations() {
        return recitations;
    }
    
    public ObservableList getScheduleItems() {
        return scheduleItems;
    }
    
    public ObservableList getTeams() {
        return teams;
    }
    
    public ObservableList getStudents() {
        return students;
    }
 
    @Override
    public void resetData() {
        System.out.println("clear");
    }
    
    public ObservableList makeSitePages(ObservableList<SitePage> pages) {
        pages.add(new SitePage(true, "Home", "index.html", "HomeBuilder.js"));
        pages.add(new SitePage(true, "Syllabus", "syllabus.html", "SyllabusBuilder.js"));
        pages.add(new SitePage(true, "Schedule", "schedule.html", "ScheduleBuilder.js"));
        pages.add(new SitePage(true, "HWs", "hws.html", "HWsBuilder.js"));
        pages.add(new SitePage(true, "Projects", "projects.html", "ProjectBuilder.js"));  
        
        return pages;
    }
    
    public String getCellKey(int col, int row) {
        return col + "_" + row;
    }

    public StringProperty getCellTextProperty(int col, int row) {
        String cellKey = getCellKey(col, row);
        return officeHours.get(cellKey);
    }

    public HashMap<String, StringProperty> getOfficeHours() {
        return officeHours;
    }
    
    public int getNumRows() {
        return ((endHour - startHour) * 2) + 1;
    }

    public String getTimeString(int militaryHour, boolean onHour) {
        String minutesText = "00";
        if (!onHour) {
            minutesText = "30";
        }

        // FIRST THE START AND END CELLS
        int hour = militaryHour;
        if (hour > 12) {
            hour -= 12;
        }
        String cellText = "" + hour + ":" + minutesText;
        if (militaryHour < 12) {
            cellText += "am";
        } else {
            cellText += "pm";
        }
        return cellText;
    }
    
    /**
     * This is a method that returns a list of all the times from 12:00am to 
     * 11:30pm, to be displayed by the combo boxes in the workspace.
     * @return 
     */
    public ObservableList<String> getTimes() {
        ObservableList<String> times = FXCollections.observableArrayList();
        times.add("12:00am");
        
        for (int i = 1; i < 24; i++) {
            times.add(getTimeString(i, true));
        }
                      
        return times;
    }
    
    public String getCellKey(String day, String time) {
        int col = gridHeaders.indexOf(day);
        int row = 1;
        int hour = Integer.parseInt(time.substring(0, time.indexOf("_")));
        int milHour = hour;
        if (hour < startHour)
            milHour += 12;
        row += (milHour - startHour) * 2;
        if (time.contains("_30"))
            row += 1;
        return getCellKey(col, row);
    }
    
    public TeachingAssistant getTA(String testName) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName)) {
                return ta;
            }
        }
        return null;
    }
    
    
    public void setStartHour(String time) {
        ObservableList<String> timeList = getTimes();
        int index = timeList.indexOf(time);
        
        this.startHour = index;
    }
    
    public void setEndHour(String time) {
        ObservableList<String> timeList = getTimes();
        int index = timeList.indexOf(time);
        
        this.endHour = index;
    }
    
    /**
     * This method is for giving this data manager the string property
     * for a given cell.
     */
    public void setCellProperty(int col, int row, StringProperty prop) {
        String cellKey = getCellKey(col, row);
        officeHours.put(cellKey, prop);
    }    
    
    /**
     * This method is for setting the string property for a given cell.
     */
    public void setGridProperty(ArrayList<ArrayList<StringProperty>> grid,
                                int column, int row, StringProperty prop) {
        grid.get(row).set(column, prop);
    }
    
//    private void initOfficeHours(int initStartHour, int initEndHour) {
//        // NOTE THAT THESE VALUES MUST BE PRE-VERIFIED
//        startHour = initStartHour;
//        endHour = initEndHour;
//        
//        // EMPTY THE CURRENT OFFICE HOURS VALUES
//        officeHours.clear();
//            
//        // WE'LL BUILD THE USER INTERFACE COMPONENT FOR THE
//        // OFFICE HOURS GRID AND FEED THEM TO OUR DATA
//        // STRUCTURE AS WE GO
//        TAWorkspace workspaceComponent = (TAWorkspace)app.getWorkspaceComponent();
//        workspaceComponent.reloadOfficeHoursGrid(this);
//    }
//    
//    public void initHours(String startHourText, String endHourText) {
//        int initStartHour = Integer.parseInt(startHourText);
//        int initEndHour = Integer.parseInt(endHourText);
//            initOfficeHours(initStartHour, initEndHour);
//    }
    
    public void markAsEdited() {
        fileController.markAsEdited(app.getGUI());
    }
    
    public void clearTAInput() {
        CSGWorkspace csgWorkspace = (CSGWorkspace)app.getWorkspaceComponent();
        TADataTabBuilder taWorkspace = csgWorkspace.getTATabBuilder();
        
        taWorkspace.getTADataController().handleClear();
        taWorkspace.getNameTextField().requestFocus();
    }

    public boolean containsTA(String testName, String testEmail) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName) || ta.getEmail().equals(testEmail)) {
                return true;
            }
        }
        return false;
    }

    public void addTA(String initName, String initEmail, boolean initIsUndergrad, boolean wasLoaded) {
        // MAKE THE TA
        TeachingAssistant ta = new TeachingAssistant(initName, initEmail, initIsUndergrad);

        // ADD THE TA
        if (!containsTA(initName, initEmail)) {
            if (!wasLoaded) {
                CSGWorkspace csgWorkspace = (CSGWorkspace)app.getWorkspaceComponent();
                TADataTabBuilder taWorkspace = csgWorkspace.getTATabBuilder();
                jTPS_Transaction transaction = new AddTA_Transaction(ta, teachingAssistants, this);
                taWorkspace.getJTPS().addTransaction(transaction);
            }
            else {
                teachingAssistants.add(ta);
                Collections.sort(teachingAssistants);
            }
        }
    }
    
    public void updateTA(TeachingAssistant oldTA, TeachingAssistant newTA) {
        CSGWorkspace csgWorkspace = (CSGWorkspace)app.getWorkspaceComponent();
        TADataTabBuilder taWorkspace = csgWorkspace.getTATabBuilder();
        jTPS_Transaction transaction = new UpdateTA_Transaction(oldTA, newTA, teachingAssistants, this, taWorkspace.getTATable());
        taWorkspace.getJTPS().addTransaction(transaction);

        taWorkspace.getTATable().refresh();
        Collections.sort(teachingAssistants);
    }
    
    /**
    Method that removes a TA from the list.
    */
    public void removeTA(String testName, String testEmail) {
        for (TeachingAssistant ta : teachingAssistants) {
            if (ta.getName().equals(testName) || ta.getEmail().equals(testEmail)) {
               HashMap<String, StringProperty> tempHours = new HashMap();
               tempHours.putAll(officeHours);
               
               CSGWorkspace csgWorkspace = (CSGWorkspace)app.getWorkspaceComponent();
               TADataTabBuilder taWorkspace = csgWorkspace.getTATabBuilder();
               jTPS_Transaction transaction = new RemoveTA_Transaction(ta, teachingAssistants, this, tempHours);
               taWorkspace.getJTPS().addTransaction(transaction);
               //Collections.sort(teachingAssistants);
               break;
            }
        }
    }

    public void addOfficeHoursReservation(String day, String time, String taName) {
        String cellKey = getCellKey(day, time);
        toggleTAOfficeHours(cellKey, taName, true);
    }
    
    /**
     * This function toggles the taName in the cell represented
     * by cellKey. Toggle means if it's there it removes it, if
     * it's not there it adds it.
     */
    public void toggleTAOfficeHours(String cellKey, String taName, boolean wasLoaded) {
        StringProperty cellProp = officeHours.get(cellKey);
        String cellText = cellProp.getValue();
        ArrayList<String> cellTextNameList = new ArrayList<String>(Arrays.asList(cellText.split("\n")));
        //Is the TA already in the cell?
        if (cellTextNameList.contains(taName)) {
            removeTAFromCell(cellProp, cellKey, taName);
        }
        //If not, then add the TA into the cell
        else {
            CSGWorkspace csgWorkspace = (CSGWorkspace)app.getWorkspaceComponent();
            TADataTabBuilder taWorkspace = csgWorkspace.getTATabBuilder();
            if (cellText.isEmpty()) {
                if (wasLoaded) {
                    cellProp.setValue(cellText + taName);
                }
                else {
                    jTPS_Transaction transaction = new ToggleAddTAInCell_Transaction(taName, this, cellKey, true);
                    taWorkspace.getJTPS().addTransaction(transaction);
                }
            }
            else {
                if (wasLoaded) {
                    cellProp.setValue(cellText + "\n" + taName);
                }
                else {
                    jTPS_Transaction transaction = new ToggleAddTAInCell_Transaction(taName, this, cellKey, false);
                    taWorkspace.getJTPS().addTransaction(transaction);     
                }
            }
        }
    }
    
    /**
     * This method removes taName from the office grid cell
     * represented by cellProp.
     */
    public void removeTAFromCell(StringProperty cellProp, String cellKey, String taName) {
        CSGWorkspace csgWorkspace = (CSGWorkspace)app.getWorkspaceComponent();
        TADataTabBuilder taWorkspace = csgWorkspace.getTATabBuilder();
        // GET THE CELL TEXT
        String cellText = cellProp.getValue();
        ArrayList<String> cellTextNameList = new ArrayList<String>(Arrays.asList(cellText.split("\n")));
        // IS IT THE ONLY TA IN THE CELL?
        if (cellText.equals(taName)) { //You can just use the cellText
            jTPS_Transaction transaction = new ToggleRemoveTAInCell_Transaction(taName, cellKey, this, true, true);
            taWorkspace.getJTPS().addTransaction(transaction);
        }
        // IS IT THE FIRST TA IN A CELL WITH MULTIPLE TA'S?
        else if (cellTextNameList.indexOf(taName) == 0) { //Going on forward with the next two branches, the arraylist becomes more useful
            jTPS_Transaction transaction = new ToggleRemoveTAInCell_Transaction(taName, cellKey, this, false, true);
            taWorkspace.getJTPS().addTransaction(transaction);
        }
        // IT MUST BE ANOTHER TA IN THE CELL
        else {
            jTPS_Transaction transaction = new ToggleRemoveTAInCell_Transaction(taName, cellKey, this,
                    false, false, cellTextNameList.indexOf(taName));
            taWorkspace.getJTPS().addTransaction(transaction);
        }
    }
}
    

