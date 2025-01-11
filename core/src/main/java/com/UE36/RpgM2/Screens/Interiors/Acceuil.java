package com.UE36.RpgM2.Screens.Interiors;

import com.UE36.RpgM2.Characters.MainCharacter;
import com.UE36.RpgM2.Characters.NPC;
import com.UE36.RpgM2.RpgGame;
import com.UE36.RpgM2.Screens.RpgScreen;
import com.UE36.RpgM2.Utilities.MapObjectRendering;
import com.UE36.RpgM2.Utilities.Transitions;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class Acceuil extends RpgScreen {
    private final MainCharacter mainCharacter;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final MapObjectRendering mapObjectRendering;
    private final OrthographicCamera uiCamera;

    public Acceuil(RpgGame game) {
        super(game);

        this.mainCharacter = game.getMainCharacter();

        map = new TmxMapLoader().load("Maps/Acceuil.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        mapObjectRendering = new MapObjectRendering(batch, map);

        this.uiCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiCamera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        uiCamera.update();

    }

    @Override
    public void render(float delta) {
        logic();
        basicRendering(delta, map, mapRenderer, mainCharacter);

        mainCharacter.render(batch, uiCamera);

        for (NPC npc : npcs) {
            npc.update(batch, uiCamera);
        }

    }

    @Override
    protected void logic() {
        Transitions transitions = new Transitions(mainCharacter.getPosition(), map, mainCharacter);
        if (transitions.onTransition("TransitionValrose")){
            game.setScreen(game.parcValrose);
            game.parcValrose.setUpMainCharacter(game.getMainCharacter(), new Vector2(1400, 400), mainCharacter.getSpeed());
        }
    }
}
