package com.UE36.RpgM2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MainCharacter {
    // Classe pour gérer tout ce qui est relatif au perso principal
    private Texture texture;
    private Vector2 position;
    private Vector2 touchPos;
    private int health;
    private float scale;
    private float speed;

    public MainCharacter() {
        texture = new Texture("test.png");
        health = 100;
        scale = 0.25f;
        speed = 100;
        touchPos = new Vector2();
    }

    public void render(SpriteBatch batch) {
        float width = texture.getWidth() * scale;
        float height = texture.getHeight() * scale;
        batch.draw(texture, position.x, position.y, width, height);
    }

    public void setPosition(Vector2 position) {
        // va permettre de gérer la position du personnage directement depuis les screens
        this.position = position;
    }

    public Vector2 getPosition() {
        // permet de récupérer la position du personnage
        return position;
    }

    private void processInput(float delta) {
        // gérer les inputs
        if (Gdx.input.isKeyPressed(Input.Keys.W)) {
            position.y += speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) {
            position.y -= speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) {
            position.x -= speed * delta;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) {
            position.x += speed * delta;
        }
    }

    public void update(float delta) {
        // Update : lance les fonctions pour mettre à jour le personnage
        processInput(delta);
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
