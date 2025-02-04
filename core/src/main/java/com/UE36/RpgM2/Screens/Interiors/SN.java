package com.UE36.RpgM2.Screens.Interiors;

import com.UE36.RpgM2.Characters.MainCharacter;
import com.UE36.RpgM2.Characters.NPC;
import com.UE36.RpgM2.RpgGame;
import com.UE36.RpgM2.Screens.Exteriors.Map2;
import com.UE36.RpgM2.Screens.RpgScreen;
import com.UE36.RpgM2.Utilities.MapObjectRendering;
import com.UE36.RpgM2.Utilities.Transitions;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class SN extends RpgScreen {
    private final MainCharacter mainCharacter;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final MapObjectRendering mapObjectRendering;
    private final OrthographicCamera uiCamera;

    public SN(RpgGame game) {
        super(game);

        this.mainCharacter = game.getMainCharacter();
        map = new TmxMapLoader().load("Maps/SN1.tmx");
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

        batch.begin();
        mapObjectRendering.renderLayerObjectsByTileId("level_explication");
        mapObjectRendering.renderLayerObjectsByTileId("Microscope");
        mapObjectRendering.renderLayerObjectsByTileId("ascenceur");
        mapObjectRendering.renderLayerObjectsByTileId("add_knowledge");
        batch.end();
        mainCharacter.render(batch, uiCamera);

    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
        batch.dispose();
        mainCharacter.dispose();

    }

    @Override
    protected void logic() {
        Transitions transitions = new Transitions(mainCharacter.getPosition(), map, mainCharacter);
        if (transitions.onTransition("TransitionSN2")){
            game.setScreen(game.sn2);
            game.sn2.setUpMainCharacter(game.getMainCharacter(), new Vector2(475, 700), mainCharacter.getSpeed());
        } else if (transitions.onTransition("TransitionCarte2")){
            game.setScreen(game.map2);
            game.map2.setUpMainCharacter(game.getMainCharacter(), new Vector2(1385, 523), mainCharacter.getSpeed());
        }
    }
}
