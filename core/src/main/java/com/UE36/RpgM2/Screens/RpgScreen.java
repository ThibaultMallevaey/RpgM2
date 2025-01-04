package com.UE36.RpgM2.Screens;

import com.UE36.RpgM2.Characters.MainCharacter;
import com.UE36.RpgM2.Characters.NPC;
import com.UE36.RpgM2.RpgGame;
import com.UE36.RpgM2.Utilities.MapObjectRendering;
import com.UE36.RpgM2.Utilities.Transitions;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.MapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

/** First screen of the application. Displayed after the application is created. */
public abstract class RpgScreen implements Screen {
    // Classe mère pour les écrans. Crée par défault par libGdx

    protected RpgGame game;
    protected SpriteBatch batch;
    private MainCharacter mainCharacter;
    private MapObjectRendering mapObjectRendering;
    private OrthographicCamera camera;
    private TiledMap map;
    private OrthogonalTiledMapRenderer mapRenderer;
    private Transitions transitions;
    protected ArrayList<NPC> npcs;

    public RpgScreen(RpgGame game, Vector2 position) {
        this.game = game;
        this.batch = new SpriteBatch();
        this.camera = new OrthographicCamera(); // idem pour la caméra du joueur
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
        this.mapRenderer = new OrthogonalTiledMapRenderer(map);

        this.npcs = new ArrayList<NPC>();

    }
    protected void setUpMainCharacter(MainCharacter mainCharacter, Vector2 position, int speed) {
        mainCharacter.setPosition(position);
        mainCharacter.setSpeed(speed);
    };

    @Override
    public void show() {
        // Prepare your screen here.
    }

    protected void clampCameraToMapBounds(TiledMap map) {
        int mapWidth = map.getProperties().get("width", Integer.class) * 32;
        int mapHeight = map.getProperties().get("height", Integer.class) * 32;

        camera.position.x = MathUtils.clamp(camera.position.x, camera.viewportWidth / 2f, mapWidth - camera.viewportWidth / 2f);
        camera.position.y = MathUtils.clamp(camera.position.y, camera.viewportHeight / 2f, mapHeight - camera.viewportHeight / 2f);
    }

    protected void basicRendering(float delta, TiledMap map, MapRenderer mapRenderer, MainCharacter mainCharacter){

        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(camera.combined);
        shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
        shapeRenderer.setColor(1, 1, 1, 1);
        shapeRenderer.rect(0, 0, map.getProperties().get("width", Integer.class), map.getProperties().get("height", Integer.class));
        shapeRenderer.end();
        // Tout ce qui est relatif à render = dessiner sur le jeu
        mainCharacter.update(delta, map);
        // Mettre la caméra sur la position du perso
        camera.position.x = mainCharacter.getPosition().x;
        camera.position.y = mainCharacter.getPosition().y;
        clampCameraToMapBounds(map);
        // update la caméra et on render ce que l'on peut voir sur la caméra
        camera.update();
        mapRenderer.setView(camera);

        mapRenderer.render();
        batch.setProjectionMatrix(camera.combined);
    }

    protected abstract void logic();

    public void addNpc(String name, String texturePath, ArrayList<String> texte, Vector2 position, float scale) {
        npcs.add(new NPC(name, texturePath, game, position, scale, texte));
    }

    @Override
    public void render(float delta) {
        // Draw your screen here. "delta" is the time since last render in seconds.
    }

    @Override
    public void resize(int width, int height) {
        // Resize your screen here. The parameters represent the new window size.
    }

    @Override
    public void pause() {
        // Invoked when your application is paused.
    }

    @Override
    public void resume() {
        // Invoked when your application is resumed after pause.
    }

    @Override
    public void hide() {
        // This method is called when another screen replaces this one.
    }

    @Override
    public void dispose() {
        // Destroy screen's assets here.
        mapRenderer.dispose();
        batch.dispose();
    }
}
