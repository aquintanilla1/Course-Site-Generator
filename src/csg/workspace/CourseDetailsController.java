/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import csg.CSGProp;
import static csg.CSGProp.MISSING_HTML_FILES_MESSAGE;
import static csg.CSGProp.MISSING_HTML_FILES_TITLE;
import csg.data.CSGData;
import csg.data.SitePage;
import djf.controller.AppFileController;
import djf.ui.AppMessageDialogSingleton;
import java.io.File;
import java.io.FilenameFilter;
import java.util.ArrayList;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.stage.DirectoryChooser;
import javafx.stage.FileChooser;
import javafx.stage.FileChooser.ExtensionFilter;
import org.apache.commons.io.FileUtils;
import properties_manager.PropertiesManager;

/**
 *
 * @author Alvaro Quintanilla, ID: 110289649
 */
public class CourseDetailsController {
    CSGApp app;
    CSGData data;
    AppFileController fileController;
    
    public CourseDetailsController(CSGApp initApp) {
        app = initApp;
        data = (CSGData) app.getDataComponent();
    }
    
    public void handleExportDirectoryChange() {
        DirectoryChooser dc = new DirectoryChooser();
        File selectedDirectory = dc.showDialog(app.getGUI().getWindow());
        
        if (selectedDirectory == null) {
            return;
        }
        else {
            data.setExportPath(selectedDirectory.getAbsolutePath());
            CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
            CourseDetailsTabBuilder cdWorkspace = workspace.getCDTabBuilder();
            cdWorkspace.setExportDirectoryText("./" + selectedDirectory.getParentFile().getName() + "/" + selectedDirectory.getName());
        }
    }
    
    public void handleTemplateChange() {
        PropertiesManager props = PropertiesManager.getPropertiesManager();
        
        String sourcePath = System.getProperty("user.dir") + "/data/public_html/templates";
        DirectoryChooser dc = new DirectoryChooser();
        dc.setInitialDirectory(new File(sourcePath));
        File selectedDirectory = dc.showDialog(app.getGUI().getWindow());
        
        if (selectedDirectory == null) {
            return;
        }
        
        FilenameFilter htmlFilter = new FilenameFilter() {
            public boolean accept(File dir, String name) {
                String lowercaseName = name.toLowerCase();
                if (lowercaseName.endsWith(".html")) {
                    return true;
                } 
                else {
                    return false;
                }
            }
        };
                
        String[] htmlFileNames = selectedDirectory.list(htmlFilter);
        ArrayList<SitePage> pages = new ArrayList<>();
        for (String s: htmlFileNames) {
            String title;
            if (s.equals("index.html")) {
                title = "Home";
            }
            else if (s.equals("hws.html")) {
                title = "HWs";
            }
            else {
                title = s.replaceAll(".html", "");
                title = title.substring(0, 1).toUpperCase() + title.substring(1);
            }
            
            pages.add(new SitePage(true, title, s, (title + "Builder.js")));
        }
        
        if (pages.isEmpty()) {
            AppMessageDialogSingleton dialog = AppMessageDialogSingleton.getSingleton();
	    dialog.show(props.getProperty(MISSING_HTML_FILES_TITLE), props.getProperty(MISSING_HTML_FILES_MESSAGE));
        }
        else {
            data.getSitePages().clear();
            for (SitePage p: pages) {
                data.getSitePages().add(p);
            }
            CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
            CourseDetailsTabBuilder cdWorkspace = workspace.getCDTabBuilder();
            cdWorkspace.setTemplateText("./" + selectedDirectory.getParentFile().getName() + "/" + selectedDirectory.getName());
            data.setTemplateDirectory(selectedDirectory.getAbsolutePath());
        }
//        File sourceDirectory = new File(sourcePath);
//        File selectedDirectory = new File(directoryPath);
//        FileUtils.copyDirectory(sourceDirectory, selectedDirectory, true);
    }
    
    public void handleImageChange(HBox imageBox, String imageType) {
        FileChooser imageChooser = new FileChooser();
        imageChooser.getExtensionFilters().add(new ExtensionFilter("Image Files", "*.png", "*.jpg", "*.gif", "*.jpeg"));
        File selectedImage = imageChooser.showOpenDialog(app.getGUI().getWindow());
        
        if (selectedImage == null) {
            return;
        }
        CSGWorkspace workspace = (CSGWorkspace) app.getWorkspaceComponent();
        CourseDetailsTabBuilder cdWorkspace = workspace.getCDTabBuilder();
        
        if (imageType.equals("Main")) {
            data.setImage(selectedImage.getAbsolutePath());
        }
        else if (imageType.equals("Left")) {
            data.setLeftFooter(selectedImage.getAbsolutePath());
        }
        else {
            data.setRightFooter(selectedImage.getAbsolutePath());
        }
        data.markAsEdited();
        
        
        Image banner = new Image("file:" + selectedImage.getAbsolutePath(), 150, 25, false, false);
        ImageView bannerView = new ImageView(banner);
        imageBox.getChildren().clear();
        imageBox.getChildren().add(bannerView);
    }    
}
