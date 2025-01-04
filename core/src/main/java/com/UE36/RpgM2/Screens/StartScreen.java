package com.UE36.RpgM2.Screens;

import com.UE36.RpgM2.RpgGame;
import com.UE36.RpgM2.Screens.Exteriors.ParcValrose;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.graphics.Color;


public class StartScreen implements Screen {
    private RpgGame game;
    private Texture background;
    private OrthographicCamera camera;
    BitmapFont font;
    private SpriteBatch batch;

    public StartScreen(RpgGame game){
        super();
        this.game = game;
        font = game.getFont();
        background = new Texture("StartScreen.jpeg");
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = game.getBatch();

    }

    public void show(){

    }

    public void logic(){
        if (Gdx.input.isKeyPressed(Input.Keys.ANY_KEY) || Gdx.input.isTouched()){
            game.setScreen(game.parcValrose);
            game.parcValrose.setUpMainCharacter(game.getMainCharacter(), new Vector2(1070, 10), 500);
        }
    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0);
        font.setColor(Color.WHITE);
        font.draw(batch, "Welcome to RPG !!! ", 100, 150);
        font.draw(batch,"Tap anywhere to begin!", 100, 100);
        batch.end();

        logic();
    }

    @Override
    public void resize(int width, int height) {

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
