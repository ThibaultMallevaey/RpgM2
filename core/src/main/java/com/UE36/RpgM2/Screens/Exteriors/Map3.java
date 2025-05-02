package com.UE36.RpgM2.Screens.Exteriors;

import com.UE36.RpgM2.Characters.MainCharacter;
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

public class Map3 extends RpgScreen {
    private OrthographicCamera camera;
    private final MainCharacter mainCharacter;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final MapObjectRendering mapObjectRendering;
    private final OrthographicCamera uiCamera;

    public Map3(RpgGame game) {
        super(game);
        this.mainCharacter = game.getMainCharacter();
        map = new TmxMapLoader().load("Maps/Carte3.tmx");
        mapObjectRendering = new MapObjectRendering(batch, map); // création de l'outil pour render la map
        mapRenderer = new OrthogonalTiledMapRenderer(map); //On définit le render sur orthogonal
        this.uiCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiCamera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        uiCamera.update();

    }

    @Override
    protected void logic(){
        Transitions transitions = new Transitions(mainCharacter.getPosition(), map, mainCharacter);
        if (transitions.onTransition("Lien_Carte2")){
            game.setScreen(game.map2);
            game.map2.setUpMainCharacter(mainCharacter, new Vector2(1128, 900), mainCharacter.getSpeed());
        } else if (transitions.onTransition("Lien_Carte4")){
            game.setScreen(game.map4);
            game.map4.setUpMainCharacter(mainCharacter, new Vector2(20, 450), mainCharacter.getSpeed());
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

}
