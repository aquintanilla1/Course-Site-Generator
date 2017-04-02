/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg;

import csg.data.CSGData;
import csg.files.CSGFiles;
import csg.style.CSGStyle;
import csg.workspace.CSGWorkspace;
import djf.AppTemplate;

/**
 *
 * @author Alvaro
 */
public class CSGApp extends AppTemplate {
    /**
     * @param args the command line arguments
     */
    
    @Override
    public void buildAppComponentsHook() {
        dataComponent = new CSGData(this);
        workspaceComponent = new CSGWorkspace(this);
        fileComponent = new CSGFiles(this);
        styleComponent = new CSGStyle(this);    }
    
    public static void main(String[] args) {
        launch(args);
    }

    
}
