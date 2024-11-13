package com.UE36.RpgM2;

import com.UE36.RpgM2.Screens.ParcValrose;
import com.UE36.RpgM2.Screens.RpgScreen;
import com.UE36.RpgM2.Screens.StartScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

import java.awt.*;

public class RpgGame extends Game {

    // Classe principale du jeu

    private SpriteBatch batch;
    private MainCharacter mainCharacter;
    private BitmapFont font;

    @Override
    public void create() {
        this.font = new BitmapFont(Gdx.files.internal("white.fnt"));
        font.setColor(Color.WHITE);
        batch = new SpriteBatch();
        mainCharacter = new MainCharacter("test.png");

        setScreen(new StartScreen(this)); // Définir ParcValrose en tant qu'écran
        // de début (peut être en faire un dédié ?)
    }

    public BitmapFont getFont() {
        return font;
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public void setBatch(SpriteBatch batch) {
        this.batch = batch;
    }

    public void setMainCharacter(MainCharacter mainCharacter) {
        this.mainCharacter = mainCharacter;
    }

    public MainCharacter getMainCharacter() {
        return mainCharacter;
    }

    @Override
    public void render() {
        super.render();
    }

    @Override
    public void dispose() {
        batch.dispose();
        mainCharacter.dispose();
        getScreen().dispose();
    }
}
