package com.UE36.RpgM2;

import com.UE36.RpgM2.Screens.ParcValrose;
import com.UE36.RpgM2.Screens.RpgScreen;
import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RpgGame extends Game {
    private SpriteBatch batch;
    private MainCharacter mainCharacter;

    @Override
    public void create() {
        batch = new SpriteBatch();
        mainCharacter = new MainCharacter();

        setScreen(new ParcValrose(this)); // Start with the ParcValrose screen
    }

    public SpriteBatch getBatch() {
        return batch;
    }

    public MainCharacter getMainCharacter() {
        return mainCharacter;
    }

    @Override
    public void render() {
        super.render(); // Calls render method of the current screen
    }

    @Override
    public void dispose() {
        batch.dispose();
        mainCharacter.dispose();
        getScreen().dispose();
    }
}
