package com.UE36.RpgM2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class MainCharacter {
    private Texture texture;
    private Vector2 position; // Character's position on the map
    private Vector2 touchPos;
    private int health;
    private float scale;
    private float speed;

    public MainCharacter() {
        texture = new Texture("test.png"); // Load character texture
        position = new Vector2(100, 100); // Starting position
        health = 100; // Starting health
        scale = 0.25f;
        speed = 100;
        touchPos = new Vector2();
    }

    public void render(SpriteBatch batch) {
        float width = texture.getWidth() * scale;
        float height = texture.getHeight() * scale;
        batch.draw(texture, position.x, position.y, width, height);
    }

    public Vector2 getPosition() {
        return position;
    }

    private void processInput(float delta) {
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
        // Update character position or state here
        processInput(delta);
    }

    public void dispose() {
        texture.dispose();
    }

    // Getters and setters for position, health, etc.

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

}
