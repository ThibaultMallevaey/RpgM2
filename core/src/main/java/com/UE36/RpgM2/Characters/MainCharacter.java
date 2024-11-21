package com.UE36.RpgM2.Characters;

import com.UE36.RpgM2.RpgGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.UE36.RpgM2.Utilities.Collisions;

public class MainCharacter {
    // Classe pour gérer tout ce qui est relatif au perso principal
    private Texture texture;
    private Vector2 position;
    private Vector2 touchPos;
    private int health;
    private float scale;
    private float speed;
    private Collisions collisions;
    public CharacterInventory inventory;
    private RpgGame game;
    private Boolean isInventoryOpen;
    private Boolean waitingForKeyRelease;

    public MainCharacter(String texturePath, RpgGame game) {
        texture = new Texture(texturePath);
        health = 100;
        scale = 0.2f;
        speed = 100;
        touchPos = new Vector2();
        this.game = game;
        this.collisions = new Collisions(this);
        this.inventory = new CharacterInventory();
        this.isInventoryOpen = false;
        this.waitingForKeyRelease = false;

    }

    public void render(SpriteBatch batch, OrthographicCamera uiCamera) {
        float width = texture.getWidth() * scale;
        float height = texture.getHeight() * scale;
        batch.begin();
        batch.draw(texture, position.x, position.y, width, height);
        batch.end();
        if (Gdx.input.isKeyPressed(Input.Keys.I)) {
            if (!waitingForKeyRelease) { // Only toggle on initial press
                isInventoryOpen = !isInventoryOpen; // Toggle inventory state
                waitingForKeyRelease = true;
            }
        } else {
            waitingForKeyRelease = false; // Reset flag when key is released
        }

        // Render inventory if it's open
        if (isInventoryOpen) {
            displayInventory(batch, uiCamera);
        }
    }

    public void setPosition(Vector2 position) {
        // va permettre de gérer la position du personnage directement depuis les screens
        this.position = position;
    }

    public Vector2 getPosition() {
        // permet de récupérer la position du personnage
        return position;
    }

    private void processInput(float delta, TiledMap map) {
        // gérer les inputs
        Vector2 newPosition = new Vector2(position);
        collisions.setMap(map);

        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            newPosition.y += speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            newPosition.y -= speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            newPosition.x -= speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            newPosition.x += speed * delta;
        }

        if (!collisions.doCollide(newPosition)) {
            position.set(newPosition);
        }
    }

    public void displayInventory(SpriteBatch batch, OrthographicCamera uiCamera){
        BitmapFont font = game.getFont();
        batch.setProjectionMatrix(uiCamera.combined);
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(uiCamera.combined);

        float boxX = 50;
        float boxY = 50;
        float boxWidth = Gdx.graphics.getWidth() - 100;
        float boxHeight = 150;

        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(0, 0, 0, 0.7f); // Semi-transparent black
        shapeRenderer.rect(boxX, boxY, boxWidth, boxHeight); // Fixed position on screen
        shapeRenderer.end();

        // Draw the current line
        batch.begin();
        font.getData().setScale(0.5f);
        for (QuestObject objet : inventory.getQuestObjects()) {
            font.draw(batch, objet.getObjectName(), boxX + 20, boxY + boxHeight - 20);
        }// Position text within the box
        batch.end();
    }

    public void update(float delta, TiledMap map) {
        // Update : lance les fonctions pour mettre à jour le personnage
        processInput(delta, map);

        //System.out.println(getPosition());

    }

    public void dispose() {
        // récupérer des perf
        texture.dispose();
    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public float getSpeed() {
        return speed;
    }

    public void setSpeed(float speed) {
        this.speed = speed;
    }
}
