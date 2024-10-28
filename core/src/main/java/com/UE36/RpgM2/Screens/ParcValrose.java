package com.UE36.RpgM2.Screens;

import com.UE36.RpgM2.MainCharacter;
import com.UE36.RpgM2.RpgGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;

public class ParcValrose extends RpgScreen {
    private Texture background;
    private OrthographicCamera camera;
    private MainCharacter mainCharacter;

    public ParcValrose(RpgGame game) {
        super(game);
        background = new Texture(Gdx.files.internal("CarteF.png")); // Load your map texture
        mainCharacter = game.getMainCharacter(); // Get the character from the game

        // Initialize the camera with the screen width and height (adjust as needed)
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    @Override
    public void show() {
        // Any specific setup for this screen
    }

    @Override
    public void render(float delta) {
        // Update the camera to follow the character
        mainCharacter.update(delta);
        camera.position.x = mainCharacter.getPosition().x;
        camera.position.y = mainCharacter.getPosition().y;

        // Optional: Clamp the camera to the map boundaries
        camera.position.x = MathUtils.clamp(camera.position.x, camera.viewportWidth / 2f, background.getWidth() - camera.viewportWidth / 2f);
        camera.position.y = MathUtils.clamp(camera.position.y, camera.viewportHeight / 2f, background.getHeight() - camera.viewportHeight / 2f);

        camera.update();
        batch.setProjectionMatrix(camera.combined); // Apply the camera transformation to the batch

        // Render the background and character
        batch.begin();
        batch.draw(background, 0, 0);
        mainCharacter.render(batch); // Draw the main character
        batch.end();
    }

    @Override
    public void dispose() {
        background.dispose(); // Clean up resources
    }
}
