package com.UE36.RpgM2;

import com.UE36.RpgM2.Characters.MainCharacter;
import com.UE36.RpgM2.Screens.Exteriors.*;
import com.UE36.RpgM2.Screens.Interiors.*;
import com.UE36.RpgM2.Screens.StartScreen;
import com.badlogic.gdx.Game;

/** {@link com.badlogic.gdx.ApplicationListener} implementation shared by all platforms. */
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RpgGame extends Game {

    // Classe principale du jeu

    private SpriteBatch batch;
    private MainCharacter mainCharacter;
    private BitmapFont font;

    public ParcValrose parcValrose;
    public Map2 map2;
    public Map3 map3;
    public Map4 map4;

    public Acceuil acceuil;
    public Biblio biblio;
    public PV pv;
    public SN sn;
    public SN2 sn2;

    @Override
    public void create() {
        this.font = new BitmapFont(Gdx.files.internal("white.fnt"));
        batch = new SpriteBatch();
        mainCharacter = new MainCharacter("test.png", this);

        setScreen(new StartScreen(this));

        parcValrose = new ParcValrose(this);
        map2 = new Map2(this);
        map3 = new Map3(this);
        map4 = new Map4(this);
        acceuil = new Acceuil(this);
        biblio = new Biblio(this);
        //pv = new PV(this);
        sn = new SN(this);
        sn2 = new SN2(this);

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
