/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transactions;

import csg.data.CSGData;
import csg.data.Team;
import java.util.Collections;
import javafx.collections.ObservableList;
import javafx.scene.control.TableView;
import jtps.jTPS_Transaction;

/**
 *
 * @author Alvaro Quintanilla, ID:110289649
 */
public class EditTeam_Transaction implements jTPS_Transaction {
    private int position;
    private Team oldTeam;
    private Team newTeam;
    private ObservableList<Team> teams;
    private CSGData data;
    private TableView teamTable;
    private String oldName, oldColor, oldTextColor, oldLink;
    
    public EditTeam_Transaction(int pos, Team newT, ObservableList<Team> list, CSGData d, TableView table) {
        teams = list;
        position = pos;
        newTeam = newT;
        data = d;
        teamTable = table;
        
        oldTeam = teams.get(position);
        oldName = oldTeam.getName();
        oldColor = oldTeam.getColor();
        oldTextColor = oldTeam.getTextColor();
        oldLink = oldTeam.getLink();
    }
 
    @Override
    public void doTransaction() {
        teams.get(position).setName(newTeam.getName());
        teams.get(position).setColor(newTeam.getColor());
        teams.get(position).setTextColor(newTeam.getTextColor());
        teams.get(position).setLink(newTeam.getLink());
       
        teamTable.refresh();
        data.markAsEdited();
    }

    @Override
    public void undoTransaction() {
        teams.get(position).setName(oldName);
        teams.get(position).setColor(oldColor);
        teams.get(position).setTextColor(oldTextColor);
        teams.get(position).setLink(oldLink);

        teamTable.refresh();   
        data.markAsEdited();
    }
}