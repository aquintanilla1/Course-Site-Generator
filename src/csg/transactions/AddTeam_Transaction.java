/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.transactions;

import csg.data.CSGData;
import csg.data.Team;
import javafx.collections.ObservableList;
import jtps.jTPS_Transaction;

/**
 *
 * @author Alvaro Quintanilla, ID:110289649
 */
public class AddTeam_Transaction implements jTPS_Transaction {
    private Team team;
    private ObservableList<Team> teams;
    private CSGData data;
    
    public AddTeam_Transaction(Team t, ObservableList<Team> list, CSGData d) {
        team = t;
        teams = list;
        data = d;
    }
 
    @Override
    public void doTransaction() {
        teams.add(team);
        data.markAsEdited();    
    }

    @Override
    public void undoTransaction() {
        teams.remove(team);
        data.markAsEdited();      
    }
}
