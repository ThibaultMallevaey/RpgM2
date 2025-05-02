package com.UE36.RpgM2.Characters;

import com.UE36.RpgM2.RpgGame;
import com.UE36.RpgM2.Utilities.Dialogue;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class NPC {
    private Texture texture;
    private Vector2 position;
    private MainCharacter character;
    private float scale;
    private Dialogue dialogue;
    private Boolean charInteraction;
    private int nbInteractions;
    private String name;

    public NPC(String name, String texturePath, RpgGame game, Vector2 position) {
        this.name = name;
        this.texture = new Texture(texturePath);
        this.position = position;
        this.character = game.getMainCharacter();
        this.scale = 0.5f;
        this.dialogue = new Dialogue(game);
        this.charInteraction = false;
        this.nbInteractions = 0;
    }

    public NPC(String name, String texturePath, RpgGame game, Vector2 position, float scale, ArrayList<String> texte) {
        this.name = name;
        this.texture = new Texture(texturePath);
        this.position = position;
        this.character = game.getMainCharacter();
        this.dialogue = new Dialogue(game);
        dialogue.addMultipleLines(texte);
        this.charInteraction = false;
        this.nbInteractions = 0;
        this.scale = scale;
    }

    public boolean isCharacterNearby() {
        float distanceSquared = position.dst2(character.getPosition());
        return distanceSquared < 5000;
    }

    public void addSingleDialogueLine(String line) {
        dialogue.addLine(line);
    }

    public void addDialogueLines(ArrayList<String> lines) {
        dialogue.addMultipleLines(lines);
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public void isCharacterInteracting(){
        if (Gdx.input.isKeyPressed(Input.Keys.E)) {this.charInteraction = true;}
    }

    public void startDialogue(SpriteBatch batch, OrthographicCamera uiCamera) {
        if (isCharacterNearby() & charInteraction) {
            if (nbInteractions == 0) {
                character.inventory.addQuestObject(new QuestObject("guide du nouvel arrivant"));
            }
            nbInteractions += 1;
            dialogue.render(batch, uiCamera);
        }
    }

    public void render(SpriteBatch batch) {
        float width = texture.getWidth() * scale;
        float height = texture.getHeight() * scale;
        batch.draw(texture, position.x, position.y, width, height);
    }

    public void update(SpriteBatch batch, OrthographicCamera uiCamera) {
        batch.begin();
        render(batch);
        batch.end();
        isCharacterInteracting();
        startDialogue(batch, uiCamera);
    }

    public void dispose() {
        texture.dispose();
        dialogue.dispose();
    }

    public int getNbInteractions() {
        return nbInteractions;
    }

    public String getName() {
        return name;
    }
}
