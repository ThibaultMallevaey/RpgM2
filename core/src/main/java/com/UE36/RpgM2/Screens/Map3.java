package com.UE36.RpgM2.Screens;

import com.UE36.RpgM2.Characters.MainCharacter;
import com.UE36.RpgM2.RpgGame;
import com.UE36.RpgM2.Utilities.MapObjectRendering;
import com.UE36.RpgM2.Utilities.Transitions;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class Map3 extends RpgScreen{
    private OrthographicCamera camera;
    private MainCharacter mainCharacter;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private MapObjectRendering mapObjectRendering;
    private Transitions transitions;
    private OrthographicCamera uiCamera;

    public Map3(RpgGame game, Vector2 position) {
        super(game, position);
        this.mainCharacter = game.getMainCharacter();
        setUpMainCharacter(mainCharacter, position, 500);
        map = new TmxMapLoader().load("Maps/Carte3.tmx");
        mapObjectRendering = new MapObjectRendering(batch, map); // création de l'outil pour render la map
        mapRenderer = new OrthogonalTiledMapRenderer(map); //On définit le render sur orthogonal
        uiCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiCamera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        uiCamera.update();
    }

    @Override
    public void show() {
    }

    @Override
    protected void logic(){
        this.transitions = new Transitions(mainCharacter.getPosition(), map, mainCharacter);
        if (transitions.onTransition("Lien_Carte2")){
            game.setScreen(new Map2(game, new Vector2(1128, 900)));
        } else if (transitions.onTransition("Lien_Carte4")){
            game.setScreen(new Map4(game, new Vector2(20, 450)));
        }
    }

    @Override
    public void render(float delta) {
        logic();
        basicRendering(delta, map, mapRenderer, mainCharacter);
        // initialisation du batch : render les objets
        batch.begin();
        mapObjectRendering.renderLayerObjectsByTexture("arbre", "Arbre.png");
        mapObjectRendering.renderLayerObjectsByTileId("Chimie1");
        mapObjectRendering.renderLayerObjectsByTileId("minichateau");
        batch.end();
        mainCharacter.render(batch, uiCamera); // render le perso


    }

    @Override
    public void dispose() {
        // permet de nettoyer = améliorer les performances
        mapRenderer.dispose();
        batch.dispose();
        mainCharacter.dispose();
    }

}
