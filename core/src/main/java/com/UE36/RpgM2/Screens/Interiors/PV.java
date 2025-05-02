package com.UE36.RpgM2.Screens.Interiors;

import com.UE36.RpgM2.Characters.MainCharacter;
import com.UE36.RpgM2.RpgGame;
import com.UE36.RpgM2.Screens.RpgScreen;
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

public class PV implements Screen {
    private final Texture background;
    private final RpgGame game;
    private final OrthographicCamera camera;
    private final SpriteBatch batch;
    private final MainCharacter mainCharacter;
    public BitmapFont font;


    public PV(RpgGame game) {
        this.game = game;
        this.background = new Texture(Gdx.files.internal("Asset/image/Lock.png"));
        camera = new OrthographicCamera();
        camera.setToOrtho(false, 800, 480);
        batch = game.getBatch();
        this.mainCharacter = game.getMainCharacter();
        this.font = game.getFont();
    }

    @Override
    public void show() {

    }

    @Override
    public void render(float delta) {
        ScreenUtils.clear(0, 0, 0.2f, 1);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();
        batch.draw(background, 0, 0);
        font.setColor(Color.WHITE);
        font.draw(batch, "Under Construction ", 100, 250);
        font.draw(batch, "Please Come back later!", 100, 200);
        font.draw(batch, "You may press E to go back", 100, 150);

        batch.end();

        logic();

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

    protected void logic() {
        if(Gdx.input.isKeyPressed(Input.Keys.E)){
            game.setScreen(game.parcValrose);
            game.parcValrose.setUpMainCharacter(mainCharacter, new Vector2(300, 575), mainCharacter.getSpeed());
        }
    }
}
