package com.UE36.RpgM2.Screens;

import com.UE36.RpgM2.Characters.MainCharacter;
import com.UE36.RpgM2.Characters.NPC;
import com.UE36.RpgM2.RpgGame;
import com.UE36.RpgM2.Utilities.MapObjectRendering;
import com.UE36.RpgM2.Utilities.Transitions;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class Template extends RpgScreen{
    private OrthographicCamera camera;
    private MainCharacter mainCharacter;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private MapObjectRendering mapObjectRendering;
    private Transitions transitions;
    private OrthographicCamera uiCamera;

    public Template(RpgGame game) {
        super(game);

        // importer le personnage principal
        this.mainCharacter = game.getMainCharacter();

        map = new TmxMapLoader().load("Maps/Map.tmx"); // charger la texture de la map

        // créer les renderers
        mapObjectRendering = new MapObjectRendering(batch, map);
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        //créer la caméra pour l'interface utilisateur
        uiCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiCamera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        uiCamera.update();

    }

    @Override
    public void show() {
    }

    @Override
    protected void logic() {
        this.transitions = new Transitions(mainCharacter.getPosition(), map, mainCharacter);
        if (transitions.onTransition("TransitionMap2")) {
            game.setScreen(game.map2); // changer vers l'écran de la carte suivante
            // paramétrer le personnage sur la nouvelle map
            game.map2.setUpMainCharacter(mainCharacter, new Vector2(830, 5), 500);
        }
    }

    @Override
    public void render(float delta) {
        logic();
        basicRendering(delta, map, mapRenderer, mainCharacter);
        // initialisation du batch : render les objets
        batch.begin();
        // render les couches d'objets de la map
        // pour définir une texture pour tous les objets de la couche
        mapObjectRendering.renderLayerObjectsByTexture("Arbre", "Arbre.png");

        // pour render tous le objets
        mapObjectRendering.renderLayerObjectsByTileId("info");

        batch.end();
        //Render le personnage
        mainCharacter.render(batch, uiCamera);
        //update les npcs
        for (NPC npc : npcs) {
            npc.update(batch, uiCamera);
        }
    }

    @Override
    public void dispose() {
        // permet de nettoyer = améliorer les performances
        mapRenderer.dispose();
        batch.dispose();
        mainCharacter.dispose();
    }
}
