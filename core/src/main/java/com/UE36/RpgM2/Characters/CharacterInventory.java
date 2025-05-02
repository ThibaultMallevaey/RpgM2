package com.UE36.RpgM2.Characters;

import java.util.ArrayList;

public class CharacterInventory {
    private ArrayList<QuestObject> questObjects;

    public CharacterInventory() {
        questObjects = new ArrayList<QuestObject>();
    }

    public void addQuestObject(QuestObject questObject) {
        questObjects.add(questObject);
    }

    public void removeQuestObject(QuestObject questObject) {
        questObjects.remove(questObject);
    }

    public boolean isObjectInInventory(QuestObject questObject) {
        return questObjects.contains(questObject);
    }

    public ArrayList<QuestObject> getQuestObjects() {
        return questObjects;
    }

    public void setQuestObjects(ArrayList<QuestObject> questObjects) {
        this.questObjects = questObjects;
    }
}
