/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.workspace;

import csg.CSGApp;
import djf.components.AppDataComponent;
import djf.components.AppWorkspaceComponent;

/**
 *
 * @author Alvaro
 */
public class CSGWorkspace extends AppWorkspaceComponent {
    CSGApp app;
    
    public CSGWorkspace(CSGApp initApp) {
        app = initApp;
    }

    @Override
    public void resetWorkspace() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void reloadWorkspace(AppDataComponent dataComponent) {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
}
