package com.UE36.RpgM2.Screens;

import com.UE36.RpgM2.Characters.MainCharacter;
import com.UE36.RpgM2.Characters.NPC;
import com.UE36.RpgM2.Characters.QuestObject;
import com.UE36.RpgM2.RpgGame;
import com.UE36.RpgM2.Utilities.*;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class ParcValrose extends RpgScreen {
    private OrthographicCamera camera;
    private MainCharacter mainCharacter;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private MapObjectRendering mapObjectRendering;
    private Transitions transitions;
    private NPC npc;
    private OrthographicCamera uiCamera;

    public ParcValrose(RpgGame game, Vector2 position) {
        //Constructeur
        super(game, position);

        this.mainCharacter = game.getMainCharacter();
        setUpMainCharacter(mainCharacter, position, 500);

        map = new TmxMapLoader().load("Maps/carteF.tmx"); // charger la map (provisoire si on doit l faire en Json?)
        mapObjectRendering = new MapObjectRendering(batch, map); // création de l'outil pour render la map
        mapRenderer = new OrthogonalTiledMapRenderer(map); //On définit le render sur orthogonal

        this.npc = new NPC("npc_2.png", game, new Vector2(700, 450));
        npc.setScale(0.23f);
        ArrayList<String> texte = new ArrayList<>();
        texte.add("Bonjour");
        texte.add("Comment allez-vous ?");
        npc.setDialogueLines(texte);

        uiCamera = new OrthographicCamera(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        uiCamera.position.set(Gdx.graphics.getWidth() / 2f, Gdx.graphics.getHeight() / 2f, 0);
        uiCamera.update();

        mainCharacter.inventory.addQuestObject(new QuestObject("Test", "Speed + 10"));
    }

    @Override
    public void show() {
    }

    @Override
    protected void logic(){
        this.transitions = new Transitions(mainCharacter.getPosition(), map, mainCharacter);
        if (transitions.onTransition("Lien_Carte2")){
            game.setScreen(new Map2(game, new Vector2(830, 5)));
        }
    }

    @Override
    public void render(float delta) {
        logic();
        basicRendering(delta, map, mapRenderer, mainCharacter);
        // initialisation du batch : render les objets
        batch.begin();
        // render les couches d'objets de la map
        mapObjectRendering.renderLayerObjectsByTexture("Arbre", "Arbre.png");
        mapObjectRendering.renderLayerObjectsByTileId("info");
        mapObjectRendering.renderLayerObjectsByTileId("PV_rot");
        mapObjectRendering.renderLayerObjectsByTileId("PV");
        mapObjectRendering.renderLayerObjectsByTileId("acceuil");
        mapObjectRendering.renderLayerObjectsByTileId("Collision");
        batch.end();
        mainCharacter.render(batch, uiCamera);
        npc.update(batch, uiCamera);


    }

    @Override
    public void dispose() {
        // permet de nettoyer = améliorer les performances
        mapRenderer.dispose();
        batch.dispose();
        mainCharacter.dispose();
        npc.dispose();
    }
}
