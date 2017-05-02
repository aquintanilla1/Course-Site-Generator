/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package csg.data;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
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
    
    public ScheduleItem(String type, String date, String time, String title, String topic, String link, String criteria) {
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
    
    public LocalDate getLocalDate() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
        
        return LocalDate.parse(getDate(), formatter);
    }
    
    public String getMonth() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
        LocalDate localDate = LocalDate.parse(date.get(), formatter);
        
        return String.valueOf(localDate.getMonthValue());
    }
    
    public String getDay() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("MM/d/yyyy");
        LocalDate localDate = LocalDate.parse(date.get(), formatter);
        
        return String.valueOf(localDate.getDayOfMonth());
    }
    
    public void setType(String newType) {
        type.set(newType);
    }
    
    public void setDate(String newDate) {
        date.set(newDate);
    }
    
    public void setTime(String newTime) {
        time = newTime;
    }
    
    public void setTitle(String newTitle) {
        title.set(newTitle);
    }
    
    public void setTopic(String newTopic) {
        topic.set(newTopic);
    }
    
    public void setLink(String newLink) {
        link = newLink;
    }
    
    public void setCriteria(String newCriteria) {
        criteria = newCriteria;
    }
}
