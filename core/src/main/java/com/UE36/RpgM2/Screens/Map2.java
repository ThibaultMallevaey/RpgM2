package com.UE36.RpgM2.Screens;

import com.UE36.RpgM2.MainCharacter;
import com.UE36.RpgM2.RpgGame;
import com.UE36.RpgM2.Utilities.MapObjectRendering;
import com.UE36.RpgM2.Utilities.Transitions;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

public class Map2 extends RpgScreen{
    private OrthographicCamera camera;
    private MainCharacter mainCharacter;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private MapObjectRendering mapObjectRendering;
    private Transitions transitions;


    public Map2(RpgGame game, Vector2 position) {
        super(game, position);
        this.mainCharacter = game.getMainCharacter();
        setUpMainCharacter(mainCharacter, position, 500);
        map = new TmxMapLoader().load("Maps/Carte2.tmx");
        mapObjectRendering = new MapObjectRendering(batch, map); // création de l'outil pour render la map
        mapRenderer = new OrthogonalTiledMapRenderer(map); //On définit le render sur orthogonal
    }
    @Override
    public void show() {
    }

    @Override
    protected void logic(){
        this.transitions = new Transitions(mainCharacter.getPosition(), map, mainCharacter);
        if (transitions.onTransition("Lien_Carte3")){
            game.setScreen(new Map3(game, new Vector2(1128, 15)));
        } else if (transitions.onTransition("Lien_Cartef")) {
            game.setScreen(new ParcValrose(game, new Vector2(820, 900)));
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
