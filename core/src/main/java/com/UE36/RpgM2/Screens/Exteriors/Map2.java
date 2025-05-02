package com.UE36.RpgM2.Screens.Exteriors;

import com.UE36.RpgM2.Characters.MainCharacter;
import com.UE36.RpgM2.RpgGame;
import com.UE36.RpgM2.Screens.Interiors.SN;
import com.UE36.RpgM2.Screens.RpgScreen;
import com.UE36.RpgM2.Utilities.MapObjectRendering;
import com.UE36.RpgM2.Utilities.Transitions;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class Map2 extends RpgScreen {
    private final MainCharacter mainCharacter;
    private final TiledMap map;
    private final OrthogonalTiledMapRenderer mapRenderer;
    private final MapObjectRendering mapObjectRendering;
    private final OrthographicCamera uiCamera;


    public Map2(RpgGame game) {
        super(game);
        this.mainCharacter = game.getMainCharacter();
        map = new TmxMapLoader().load("Maps/Carte2.tmx");
        mapObjectRendering = new MapObjectRendering(batch, map); // création de l'outil pour render la map
        mapRenderer = new OrthogonalTiledMapRenderer(map); //On définit le render sur orthogonal
        uiCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiCamera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        uiCamera.update();
    }

    @Override
    protected void logic(){
        Transitions transitions = new Transitions(mainCharacter.getPosition(), map, mainCharacter);
        if (transitions.onTransition("Lien_Carte3")){
            game.setScreen(game.map3);
            game.map3.setUpMainCharacter(mainCharacter, new Vector2(1128, 15), mainCharacter.getSpeed());
        } else if (transitions.onTransition("Lien_Cartef")) {
            game.setScreen(game.parcValrose);
            game.parcValrose.setUpMainCharacter(mainCharacter, new Vector2(820, 900), mainCharacter.getSpeed());
        } else if (transitions.onTransition("TransitionSN")) {
            game.setScreen(game.sn);
            game.sn.setUpMainCharacter(mainCharacter, new Vector2(850, 200), mainCharacter.getSpeed());
        }
    }

    @Override
    public void render(float delta) {
        logic();
        basicRendering(delta, map, mapRenderer, mainCharacter);
        // initialisation du batch : render les objets
        batch.begin();
        mapObjectRendering.renderLayerObjectsByTexture("Arbre", "Arbre.png");
        mapObjectRendering.renderLayerObjectsByTileId("fleur");
        mapObjectRendering.renderLayerObjectsByTileId("ScienceNat");
        batch.end();
        mainCharacter.render(batch, uiCamera);

    }

}
