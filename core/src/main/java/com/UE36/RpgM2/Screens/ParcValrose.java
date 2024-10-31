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
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Array;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Map;

public class ParcValrose extends RpgScreen {
    private OrthographicCamera camera;
    private MainCharacter mainCharacter;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private MapObjectRendering mapObjectRendering;

    public ParcValrose(RpgGame game) {
        //Constructeur
        super(game);
        mainCharacter = game.getMainCharacter(); // Récupérer le personnage depuis RpgGame
        mainCharacter.setPosition(new Vector2 (1070, 10)); // définir la position de départ du personnage

        map = new TmxMapLoader().load("Maps/carteF.tmx"); // charger la map (provisoir si on doit l faire en Json?)

        mapObjectRendering = new MapObjectRendering(batch, map); // création de l'outil pour render la map

        mapRenderer = new OrthogonalTiledMapRenderer(map); //On définit le render sur orthogonal

        camera = new OrthographicCamera(); // idem pour la caméra du joueur
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        // Tout ce qui est relatif à render = dessiner sur le jeu

        // On update les infos du perso
        mainCharacter.update(delta);

        // Mettre la caméra sur la position du perso
        camera.position.x = mainCharacter.getPosition().x;
        camera.position.y = mainCharacter.getPosition().y;

        //Bloquer la caméra sur les bords de map
        camera.position.x = MathUtils.clamp(mainCharacter.getPosition().x, camera.viewportWidth / 2f, map.getProperties().get("width", Integer.class) * 32 - camera.viewportWidth / 2f);
        camera.position.y = MathUtils.clamp(mainCharacter.getPosition().y, camera.viewportHeight / 2f, map.getProperties().get("height", Integer.class) * 32 - camera.viewportHeight / 2f);

        // update la caméra et on render ce que l'on peut voir sur la caméra
        camera.update();
        mapRenderer.setView(camera);

        mapRenderer.render();
        batch.setProjectionMatrix(camera.combined);

        // initialisation du batch : render les objets
        batch.begin();

        // render les couches d'objets de la map
        mapObjectRendering.renderLayerObjectsByTexture("Arbre", "Arbre.png");
        mapObjectRendering.renderLayerObjectsByTileId("info");
        mapObjectRendering.renderLayerObjectsByTileId("PV_rot");
        mapObjectRendering.renderLayerObjectsByTileId("PV");
        mapObjectRendering.renderLayerObjectsByTileId("acceuil");

        mainCharacter.render(batch); // render le perso
        batch.end();

    }

    @Override
    public void dispose() {
        // permet de nettoyer = améliorer les performances
        mapRenderer.dispose();
        batch.dispose();
        mainCharacter.dispose();
    }
}
