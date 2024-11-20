package com.UE36.RpgM2;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

public class NPC {
    private RpgGame game;
    private SpriteBatch batch;
    private Texture texture;
    private Vector2 position;
    private MainCharacter character;
    private float scale;

    public NPC(String texturePath, RpgGame game, Vector2 position) {
        this.game = game;
        batch = new SpriteBatch();
        this.texture = new Texture(texturePath);
        this.position = position;
        character = game.getMainCharacter();
        scale = 0.5f;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public Vector2 getPosition() {
        return position;
    }

    public void setPosition(Vector2 position) {
        this.position = position;
    }

    public boolean isCharacterNearby(){
        float distanceSquared = position.dst2(character.getPosition());  // dst2 gives squared distance
        return distanceSquared < 50;
    }

    public void update(){
        render(batch);
        //if(isCharacterNearby()){
            System.out.println("NPC is nearby");
        //}
    }

    public void render(SpriteBatch batch) {
        float width = texture.getWidth() * scale;
        float height = texture.getHeight() * scale;
        batch.draw(texture, position.x, position.y, width, height);

    }
}
