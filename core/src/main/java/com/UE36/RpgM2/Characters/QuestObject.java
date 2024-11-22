package com.UE36.RpgM2.Characters;

import java.util.ArrayList;

public class QuestObject {
    private String objectName;
    private String objectValue;
    private ArrayList<String> stats;

    public QuestObject(String objectName) {
        this.objectName = objectName;
        stats = new ArrayList<>();
    }

    public QuestObject(String objectName, ArrayList<String> stats) {
        this.objectName = objectName;
        this.stats = stats;
    }

    public QuestObject(String objectName, String stat) {
        this.objectName = objectName;
        this.stats = new ArrayList<>();
        addStat(stat);
    }

    public void addStat(String stat) {
        stats.add(stat);
    }

    public ArrayList<String> getStats(){
        return stats;
    }

    public String getObjectName() {
        return objectName;
    }

    public void setObjectName(String objectName) {
        this.objectName = objectName;
    }

    public String getObjectValue() {
        return objectValue;
    }

    public void setObjectValue(String objectValue) {
        this.objectValue = objectValue;
    }
}
