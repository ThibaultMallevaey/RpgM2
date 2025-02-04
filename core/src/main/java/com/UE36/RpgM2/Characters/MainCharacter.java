package com.UE36.RpgM2.Characters;

import com.UE36.RpgM2.RpgGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Vector2;
import com.UE36.RpgM2.Utilities.Collisions;

import java.util.List;

public class MainCharacter {
    // Classe pour gérer tout ce qui est relatif au perso principal


    // Animations pour chaque direction
    private Animation<TextureRegion> walkDown;
    private Animation<TextureRegion> walkDownRight;
    private Animation<TextureRegion> walkRight;
    private Animation<TextureRegion> walkUpRight;
    private Animation<TextureRegion> walkUp;
    private Animation<TextureRegion> walkUpLeft;
    private Animation<TextureRegion> walkLeft;
    private Animation<TextureRegion> walkDownLeft;
    private Animation<TextureRegion> walkDownRightRepeat; // Répétition de la ligne 2
    private TextureRegion currentFrame;
    private float stateTime; // Temps écoulé pour l'animation
    private Vector2 position;
    private int health;
    private float scale;
    private int speed;
    private Collisions collisions;
    public CharacterInventory inventory;
    private RpgGame game;
    private Boolean isInventoryOpen;
    private Boolean waitingForKeyRelease;

    public MainCharacter(String texturePath, RpgGame game) {
        Texture spriteSheet = new Texture("SpriteSheet.png");

        int FRAME_COLS = 4;  // 4 images par ligne
        int FRAME_ROWS = 9;  // 9 lignes

        // Division des sprites en régions (en utilisant la méthode de découpe manuelle)
        walkDown = createAnimation(spriteSheet, 23, 36, FRAME_COLS, 0); // Bas
        walkDownRight = createAnimation(spriteSheet, 23, 36, FRAME_COLS, 1); // Bas et droite
        walkRight = createAnimation(spriteSheet, 23, 36, FRAME_COLS, 2); // Droite
        walkUpRight = createAnimation(spriteSheet, 23, 36, FRAME_COLS, 3); // Haut et droite
        walkUp = createAnimation(spriteSheet, 23, 36, FRAME_COLS, 4); // Haut
        walkUpLeft = createAnimation(spriteSheet, 23, 36, FRAME_COLS, 5); // Haut et gauche
        walkLeft = createAnimation(spriteSheet, 23, 36, FRAME_COLS, 6); // Gauche
        walkDownLeft = createAnimation(spriteSheet, 23, 36, FRAME_COLS, 7); // Bas et gauche
        walkDownRightRepeat = createAnimation(spriteSheet, 23, 36, FRAME_COLS, 8); // Répétition de la ligne 2

        // Initialisation
        currentFrame = walkDown.getKeyFrame(0); // Par défaut, face vers le bas
        stateTime = 0f;


        health = 100;
        scale = 1.3f;
        speed = 100;
        this.game = game;
        this.collisions = new Collisions(this);
        this.inventory = new CharacterInventory();
        this.isInventoryOpen = false;
        this.waitingForKeyRelease = false;

    }
    // Méthode de découpe des animations avec une ligne spécifique pour chaque direction
    private Animation<TextureRegion> createAnimation(Texture texture, int cellWidth, int cellHeight, int cols, int row) {
        // Créer un tableau de frames pour une ligne donnée
        TextureRegion[] frames = new TextureRegion[cols];
        for (int col = 0; col < cols; col++) {
            // Calculer la position de chaque frame
            int x = col * cellWidth;  // Position horizontale de la frame
            int y = row * cellHeight; // Position verticale de la frame
            // Ajouter la frame à l'array
            frames[col] = new TextureRegion(texture, x, y, cellWidth, cellHeight);
        }
        // Retourner l'animation avec les frames découpées
        return new Animation<>(0.1f, frames);
    }

    // Méthode pour afficher l'inventaire
    public void displayInventory(SpriteBatch batch, OrthographicCamera uiCamera) {
        BitmapFont font = game.getFont();
        batch.setProjectionMatrix(uiCamera.combined);
        ShapeRenderer shapeRenderer = new ShapeRenderer();
        shapeRenderer.setProjectionMatrix(uiCamera.combined);

        Texture inventoryBackground = new Texture(Gdx.files.internal("inventory/Inventory_9Slices.png"));
        Texture inventorySlot = new Texture(Gdx.files.internal("inventory/Inventory_Slot_7.png"));

        float boxX = Gdx.graphics.getWidth() / 2;
        float boxY = Gdx.graphics.getHeight() / 2 - 25;
        float boxWidth = Gdx.graphics.getWidth() / 2;
        float boxHeight = Gdx.graphics.getHeight() / 2;

        batch.begin();
        batch.draw(inventoryBackground, boxX, boxY, boxWidth, boxHeight);
        batch.end();

        batch.begin();
        font.getData().setScale(1.5f);
        font.draw(batch, "Inventaire", boxX + 20, boxY + boxHeight - 20);

        float slotSize = (boxWidth - 40) / 6;
        float xOffset = boxX + boxWidth - 320;
        float yOffset = boxY + boxHeight - 110;

        for (int row = 0; row < 3; row++) {
            for (int col = 0; col < 3; col++) {
                batch.draw(inventorySlot, xOffset + col * (slotSize + 30), yOffset - row * (slotSize + 10), slotSize, slotSize);
                int index = row * 3 + col;
                if (index < inventory.getQuestObjects().size()) {
                    QuestObject objet = inventory.getQuestObjects().get(index);
                    String iconPath = "icons/" + objet.getObjectName() + ".png";
                    if (Gdx.files.internal(iconPath).exists()) {
                        Texture icon = new Texture(Gdx.files.internal(iconPath));
                        batch.draw(icon, xOffset + col * (slotSize + 10), yOffset - row * (slotSize + 10), slotSize, slotSize);
                    } else {
                        Texture defaultIcon = new Texture(Gdx.files.internal("icons/default.png"));
                        batch.draw(defaultIcon, xOffset + col * (slotSize + 10), yOffset - row * (slotSize + 10), slotSize, slotSize);
                    }
                    font.getData().setScale(0.5f);
                    font.draw(batch, objet.getObjectName(), xOffset + col * (slotSize + 10), yOffset - row * (slotSize + 10) - 10);

                    // Récupérer les statistiques et afficher ligne par ligne
                    List<String> stats = objet.getStats();  // Assurez-vous que getStats() retourne une liste de chaînes
                    float yOffsetForStats = yOffset - row * (slotSize + 10) - 20;  // Position initiale pour afficher les stats

                    for (String stat : stats) {
                        font.draw(batch, stat, xOffset + col * (slotSize + 10), yOffsetForStats);
                        yOffsetForStats -= 10;  // Décaler verticalement pour la ligne suivante
                    }
                }
            }
        }

        batch.end();
    }

    public void render(SpriteBatch batch, OrthographicCamera uiCamera) {
        stateTime += Gdx.graphics.getDeltaTime();

        float width = currentFrame.getRegionWidth() * scale;
        float height = currentFrame.getRegionHeight() * scale;

        batch.begin();
        batch.draw(currentFrame, position.x, position.y, width, height);
        batch.end();

        if (Gdx.input.isKeyPressed(Input.Keys.I)) {
            if (!waitingForKeyRelease) {
                isInventoryOpen = !isInventoryOpen;
                waitingForKeyRelease = true;
            }
        } else {
            waitingForKeyRelease = false;
        }

        // Afficher l'inventaire
        if (isInventoryOpen) {
            displayInventory(batch, uiCamera);
        }
    }

    public void setPosition(Vector2 position) {
        // va permettre de gérer la position du personnage directement depuis les screens
        this.position = position;
    }

    public Vector2 getPosition() {
        // permet de récupérer la position du personnage
        return position;
    }

    private void processInput(float delta, TiledMap map) {
        Vector2 newPosition = new Vector2(position);
        collisions.setMap(map);

        boolean moving = false;

        // Déplacer le personnage dans les différentes directions et mettre à jour l'animation
        if (Gdx.input.isKeyPressed(Input.Keys.W)) { // Haut
            newPosition.y += speed * delta;
            currentFrame = walkUp.getKeyFrame(stateTime, true);
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S)) { // Bas
            newPosition.y -= speed * delta;
            currentFrame = walkDown.getKeyFrame(stateTime, true);
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.A)) { // Gauche
            newPosition.x -= speed * delta;
            currentFrame = walkLeft.getKeyFrame(stateTime, true);
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.D)) { // Droite
            newPosition.x += speed * delta;
            currentFrame = walkRight.getKeyFrame(stateTime, true);
            moving = true;
        }

        // Détecter les diagonales
        if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.D)) { // Haut et Droite
            currentFrame = walkUpRight.getKeyFrame(stateTime, true);
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.D)) { // Bas et Droite
            currentFrame = walkDownRight.getKeyFrame(stateTime, true);
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.S) && Gdx.input.isKeyPressed(Input.Keys.A)) { // Bas et Gauche
            currentFrame = walkDownLeft.getKeyFrame(stateTime, true);
            moving = true;
        }
        if (Gdx.input.isKeyPressed(Input.Keys.W) && Gdx.input.isKeyPressed(Input.Keys.A)) { // Haut et Gauche
            currentFrame = walkUpLeft.getKeyFrame(stateTime, true);
            moving = true;
        }

        // Réinitialiser le temps de l'animation si le personnage ne se déplace pas
        if (!moving) {
            stateTime = 0;
        }

        // Vérifier les collisions et mettre à jour la position du personnage
        if (!collisions.doCollide(newPosition)) {
            position.set(newPosition);
        }
    }

    public void update(float delta, TiledMap map) {
        // Update : lance les fonctions pour mettre à jour le personnage
        processInput(delta, map);
    }

    public void dispose() {
        // récupérer des perf

    }

    public int getHealth() {
        return health;
    }

    public void setHealth(int health) {
        this.health = health;
    }

    public float getScale() {
        return scale;
    }

    public void setScale(float scale) {
        this.scale = scale;
    }

    public int getSpeed() {
        return speed;
    }

    public void setSpeed(int speed) {
        this.speed = speed;
    }
}
