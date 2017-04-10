/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.style;

import csg.workspace.CSGWorkspace;
import csg.workspace.CourseDetailsTabBuilder;
import djf.AppTemplate;
import djf.components.AppStyleComponent;
import javafx.scene.control.TabPane;

/**
 *
 * @author Alvaro
 */
public class CSGStyle extends AppStyleComponent {
    private AppTemplate app;
    public static String CLASS_INFO_PANE = "info_pane";
    public static String TAB_LABEL = "tab_label";
    public static String PANE = "pane";


    public CSGStyle(AppTemplate initApp) {
        // KEEP THIS FOR LATER
        app = initApp;
        
        super.initStylesheet(app);
        app.getGUI().initFileToolbarStyle();
        app.getGUI().initEditToolbarStyle();
        app.getGUI().initHeaderStyle();
        initCourseWorkspace();
    }
    
    private void initCourseWorkspace() {
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        workspace.getTabs().getStyleClass().add(PANE);
        workspace.getCDTabBuilder().getTab().getStyleClass().add(TAB_LABEL);
        workspace.getCDTabBuilder().getTopPane().getStyleClass().add(PANE);
        workspace.getCDTabBuilder().getCenterPane().getStyleClass().add(PANE);
        workspace.getCDTabBuilder().getBottomPane().getStyleClass().add(PANE);
        
        workspace.getRecitationTabBuilder().getBottomPane().getStyleClass().add(PANE);
        
        workspace.getScheduleTabBuilder().getTopPane().getStyleClass().add(PANE);
        workspace.getScheduleTabBuilder().getBottomPane().getStyleClass().add(PANE);

        workspace.getProjectTabBuilder().getTopPane().getStyleClass().add(PANE);
        workspace.getProjectTabBuilder().getBottomPane().getStyleClass().add(PANE);
        
        //workspace.getTabs().getStyleClass().add(TAB_LABEL);
        
        
        
    }
    
}
