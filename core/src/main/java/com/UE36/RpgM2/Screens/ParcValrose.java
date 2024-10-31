package com.UE36.RpgM2.Screens;

import com.UE36.RpgM2.MainCharacter;
import com.UE36.RpgM2.RpgGame;
import com.UE36.RpgM2.Rendering.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.MapObjects;
import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ParcValrose extends RpgScreen {
    // private Texture background;
    private OrthographicCamera camera;
    private MainCharacter mainCharacter;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private MapObjectRendering mapObjectRendering;

    public ParcValrose(RpgGame game) {
        super(game);
        mainCharacter = game.getMainCharacter(); // Get the character from the game

        map = new TmxMapLoader().load("Maps/carteF.tmx");

        mapObjectRendering = new MapObjectRendering(batch, map);

        mapRenderer = new OrthogonalTiledMapRenderer(map);

        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        mainCharacter.update(delta);
        camera.position.x = mainCharacter.getPosition().x;
        camera.position.y = mainCharacter.getPosition().y;

        camera.position.x = MathUtils.clamp(mainCharacter.getPosition().x, camera.viewportWidth / 2f, map.getProperties().get("width", Integer.class) * 32 - camera.viewportWidth / 2f);
        camera.position.y = MathUtils.clamp(mainCharacter.getPosition().y, camera.viewportHeight / 2f, map.getProperties().get("height", Integer.class) * 32 - camera.viewportHeight / 2f);

        camera.update();
        mapRenderer.setView(camera);

        mapRenderer.render();
        batch.setProjectionMatrix(camera.combined);

        batch.begin();

        mapObjectRendering.renderLayerObjectsByTileId("Arbre");
        mapObjectRendering.renderLayerObjectsByTileId("info");
        mapObjectRendering.renderLayerObjectsByTileId("PV_rot");
        mapObjectRendering.renderLayerObjectsByTileId("PV");
        mapObjectRendering.renderLayerObjectsByTileId("acceuil");

        mainCharacter.render(batch);
        batch.end();

    }

    @Override
    public void dispose() {
        mapRenderer.dispose();
        batch.dispose();
        mainCharacter.dispose();
    }
}
