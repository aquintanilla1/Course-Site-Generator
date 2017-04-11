/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg;

/**
 *
 * @author Alvaro
 */
public enum CSGProp { 
       // FOR SIMPLE OK/CANCEL DIALOG BOXES
    OK_PROMPT,
    CANCEL_PROMPT,
    
    // THESE ARE FOR TEXT PARTICULAR TO THE APP'S WORKSPACE CONTROLS
    TAS_HEADER_TEXT,
    NAME_COLUMN_TEXT,
    EMAIL_COLUMN_TEXT,
    NAME_PROMPT_TEXT,
    EMAIL_PROMPT_TEXT,
    ADD_BUTTON_TEXT,
    UPDATE_BUTTON_TEXT,
    CLEAR_BUTTON_TEXT,
    SUBMIT_TIMES_BUTTON_TEXT,
    START_TIME_TEXT,
    END_TIME_TEXT,
    EMAIL_PATTERN,
    OFFICE_HOURS_SUBHEADER,
    OFFICE_HOURS_TABLE_HEADERS,
    DAYS_OF_WEEK,
    REMOVE_ICON,
    //Verification messages for changing office hours grid
    TAS_IN_CELLS_TITLE,
    TAS_IN_CELLS_MESSAGE,
    
    // THESE ARE FOR ERROR MESSAGES PARTICULAR TO THE APP
    MISSING_TA_NAME_TITLE,
    MISSING_TA_NAME_MESSAGE,
    MISSING_TA_EMAIL_TITLE,
    MISSING_TA_EMAIL_MESSAGE,
    TA_NAME_AND_EMAIL_NOT_UNIQUE_TITLE,
    TA_NAME_AND_EMAIL_NOT_UNIQUE_MESSAGE,
    INVALID_TA_EMAIL_TITLE,
    INVALID_TA_EMAIL_MESSAGE,
    INVALID_TIMES_TITLE,
    INVALID_TIMES_MESSAGE,
    
    //Course Details Tab
    COURSE_DETAILS_TAB,
    COURSE_INFO_TEXT,
    SUBJECT_TEXT,
    SEMESTER_TEXT,
    NUMBER_TEXT,
    YEAR_TEXT,
    TITLE_TEXT,
    INSTRUCTOR_NAME_TEXT,
    INSTRUCTOR_HOME_TEXT,
    EXPORT_DIR_TEXT,
    DEFAULT_DIR_TEXT,
    CHANGE_BUTTON_TEXT,
    
    SITE_TEMPLATE_TEXT,
    SITE_TEMPLATE_DIRECTORY_TEXT,
    SITE_TEMPLATE_NOTE,
    SELECT_TEMPLATE_DIR_BUTTON_TEXT,
    SITE_PAGES_TEXT,
    USE_HEADER,
    NAVBAR_TITLE_HEADER,
    FILE_NAME_HEADER,
    SCRIPT_HEADER,
    
    PAGE_STYLE_TEXT,
    BANNER_TEXT,
    LEFT_FOOTER_TEXT,
    RIGHT_FOOTER_TEXT,
    STYLESHEET_TEXT,
    NOTE_TEXT,
    NO_IMAGE_SELECTED_TEXT,
    
    TA_TAB,
    UNDERGRAD_COLUMN_TEXT,
    DELETE_TA_TOOLTIP,
    
    //Recitation Tab
    RECITATION_TAB,
    RECITATION_HEADER,
    ADD_EDIT_HEADER,
    SECTION_TEXT,
    INSTRUCTOR_TEXT,
    DAY_TIME_TEXT,
    LOCATION_TEXT,
    TA_HEADER,
    SUPERVISING_TA_TEXT,
    ADD_UPDATE_BUTTON_TEXT, 
    
    //Schedule Tab
    SCHEDULE_TAB,
    CALENDAR_BOUNDARIES_TEXT,
    STARTING_MONDAY_TEXT,
    ENDING_FRIDAY_TEXT,
    SCHEDULE_ITEMS_TEXT,
    TYPE_TEXT,
    DATE_TEXT,
    TIME_TEXT,
    TOPIC_TEXT,
    LINK_TEXT,
    CRITERIA_TEXT,
    
    //Project Tab
    PROJECT_TAB,
    PROJECTS_HEADER,
    TEAM_SUB_HEADER,
    STUDENTS_SUBHEADER,
    NAME_TEXT,
    COLOR_TEXT,
    TEXT_COLOR_TEXT,
    FIRST_NAME_TEXT,
    LAST_NAME_TEXT,
    TEAM_TEXT,
    ROLE_TEXT,
}
