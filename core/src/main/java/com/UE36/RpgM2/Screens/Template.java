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
    private final MainCharacter mainCharacter;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final MapObjectRendering mapObjectRendering;
    private final OrthographicCamera uiCamera;

    public Template(RpgGame game) {
        super(game);

        // importer le personnage principal
        this.mainCharacter = game.getMainCharacter();

        // charger la texture de la map, remplacer Map.tmx avec le bon fichier
        map = new TmxMapLoader().load("Maps/Map.tmx");

        // créer les renderers
        mapObjectRendering = new MapObjectRendering(batch, map);
        mapRenderer = new OrthogonalTiledMapRenderer(map);

        //créer la caméra pour l'interface utilisateur
        uiCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiCamera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        uiCamera.update();

    }


    @Override
    protected void logic() {
        Transitions transitions = new Transitions(mainCharacter.getPosition(), map, mainCharacter);
        if (transitions.onTransition("TransitionMap2")) {
            game.setScreen(game.map2); // changer vers l'écran de la carte suivante
            // paramétrer le personnage sur la nouvelle map
            game.map2.setUpMainCharacter(mainCharacter, new Vector2(830, 5), 500);
        }
    }

    @Override
    public void render(float delta) {
        //excétuer la logique du jeu (comme les transitions)
        logic();

        //render du fond de la map et organisation de la caméra
        basicRendering(delta, map, mapRenderer, mainCharacter);

        // initialisation du batch : render les objets
        batch.begin();

        // render les couches d'objets de la map
        // pour définir une texture pour tous les objets de la couche
        mapObjectRendering.renderLayerObjectsByTexture("Arbre", "Arbre.png");

        // pour render tous le objets d'une couche
        mapObjectRendering.renderLayerObjectsByTileId("info");

        batch.end();
        //Render le personnage
        mainCharacter.render(batch, uiCamera);
        //update les npcs
        for (NPC npc : npcs) {
            npc.update(batch, uiCamera);
        }
    }
}
