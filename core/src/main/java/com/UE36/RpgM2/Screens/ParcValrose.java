package com.UE36.RpgM2.Screens;

import com.UE36.RpgM2.MainCharacter;
import com.UE36.RpgM2.RpgGame;
import com.UE36.RpgM2.Utilities.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class ParcValrose extends RpgScreen {
    private OrthographicCamera camera;
    private MainCharacter mainCharacter;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private MapObjectRendering mapObjectRendering;
    private Transitions transitions;

    public ParcValrose(RpgGame game) {
        //Constructeur
        super(game);
        mainCharacter = game.getMainCharacter(); // Récupérer le personnage depuis RpgGame
        mainCharacter.setPosition(new Vector2 (1070, 10)); // définir la position de départ du personnage
        mainCharacter.setSpeed(500);

        map = new TmxMapLoader().load("Maps/carteF.tmx"); // charger la map (provisoir si on doit l faire en Json?)

        mapObjectRendering = new MapObjectRendering(batch, map); // création de l'outil pour render la map

        mapRenderer = new OrthogonalTiledMapRenderer(map); //On définit le render sur orthogonal

        camera = new OrthographicCamera(); // idem pour la caméra du joueur
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

    }

    @Override
    public void show() {
    }

    private void logic(){
        this.transitions = new Transitions(mainCharacter.getPosition(), map, mainCharacter);
        if (transitions.onTransition("TransitionPV")){
            game.setScreen(new Map2(game));
        } else if (transitions.onTransition("TransitionAcceuil")){
            game.setScreen(new Map3(game));
        }
    }

    @Override
    public void render(float delta) {
        // Tout ce qui est relatif à render = dessiner sur le jeu
        logic();
        // On update les infos du perso
        mainCharacter.update(delta, map);

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
