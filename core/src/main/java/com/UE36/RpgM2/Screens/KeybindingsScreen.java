package com.UE36.RpgM2.Screens;

import com.UE36.RpgM2.RpgGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;

public class KeybindingsScreen implements Screen {

    private final RpgGame game;
    private final Texture background;
    private final OrthographicCamera camera;
    public BitmapFont font;
    private final SpriteBatch batch;

    public KeybindingsScreen(RpgGame game) {
        super();
        this.game = game;
        font = game.getFont();
        background = new Texture("KeyBindings.png");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch = game.getBatch();

    }

    private void logic(){
        if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isTouched()){
            game.setScreen(game.parcValrose);
            game.parcValrose.setUpMainCharacter(game.getMainCharacter(), new Vector2(1070, 10), 500);
        }
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        logic();
        ScreenUtils.clear(0, 0, 0, 0);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        batch.end();
    }

    @Override
    public void resize(int i, int i1) {

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {

    }
}
