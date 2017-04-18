/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;

/**
 *
 * @author Alvaro Quintanilla, ID: 110289649
 */
public class ScheduleItem {
    private final StringProperty type;
    private final StringProperty date;
    private String time;
    private final StringProperty title;
    private final StringProperty topic;
    private String link;
    private String criteria;
    
    public ScheduleItem(String type, String date, String time, String title, String topic,  String link, String criteria) {
        this.type = new SimpleStringProperty(type);
        this.date = new SimpleStringProperty(date);
        this.time = time;
        this.title = new SimpleStringProperty(title);
        this.topic = new SimpleStringProperty(topic);
        this.link = link;
        this.criteria = criteria;
    }
    
    public String getType() {
        return type.get();
    }
    
    public String getDate() {
        return date.get();
    }
    
    public String getTime() {
        return time;
    }
    
    public String getTitle() {
        return title.get();
    }
    
    public String getTopic() {
        return topic.get();
    }
    
    public String getLink() {
        return link;
    }
    
    public String getCriteria() {
        return criteria;
    }
}
