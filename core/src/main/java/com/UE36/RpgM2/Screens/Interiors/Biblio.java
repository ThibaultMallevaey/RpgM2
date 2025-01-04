package com.UE36.RpgM2.Screens.Interiors;

import com.UE36.RpgM2.Characters.MainCharacter;
import com.UE36.RpgM2.Characters.NPC;
import com.UE36.RpgM2.RpgGame;
import com.UE36.RpgM2.Screens.Exteriors.Map4;
import com.UE36.RpgM2.Screens.Exteriors.ParcValrose;
import com.UE36.RpgM2.Screens.RpgScreen;
import com.UE36.RpgM2.Utilities.MapObjectRendering;
import com.UE36.RpgM2.Utilities.Transitions;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class Biblio extends RpgScreen {

    private OrthographicCamera camera;
    private MainCharacter mainCharacter;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private MapObjectRendering mapObjectRendering;
    private Transitions transitions;
    private NPC npc;
    private OrthographicCamera uiCamera;

    public Biblio(RpgGame game) {
        super(game);

        this.mainCharacter = game.getMainCharacter();

        map = new TmxMapLoader().load("Maps/Biblio.tmx");
        mapRenderer = new OrthogonalTiledMapRenderer(map);
        mapObjectRendering = new MapObjectRendering(batch, map);

        this.uiCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiCamera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        uiCamera.update();

    }

    @Override
    public void show() {
    }

    @Override
    public void render(float delta) {
        logic();
        basicRendering(delta, map, mapRenderer, mainCharacter);

        batch.begin();
        mapObjectRendering.renderLayerObjectsByTileId("add_knowledge");
        mapObjectRendering.renderLayerObjectsByTileId("map_to_livre");
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
        this.transitions = new Transitions(mainCharacter.getPosition(), map, mainCharacter);
        if (transitions.onTransition("TransitionCarte4")){
            game.setScreen(game.map4);
            game.map4.setUpMainCharacter(game.getMainCharacter(), new Vector2(977, 548), mainCharacter.getSpeed());
        }
    }

}
