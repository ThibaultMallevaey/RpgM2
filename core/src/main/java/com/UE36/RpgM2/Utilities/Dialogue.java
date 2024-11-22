package com.UE36.RpgM2.Utilities;

import com.UE36.RpgM2.RpgGame;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector3;

import java.util.ArrayList;

public class Dialogue {
        private ArrayList<String> texte;
        private BitmapFont font;
        private int currentLineIndex;
        private boolean waitingForKeyRelease;
        private ShapeRenderer shapeRenderer;

        public Dialogue(RpgGame game) {
            this.texte = new ArrayList<>();
            texte.add("Bonjour");
            texte.add("Test");
            this.font = game.getFont();
            this.currentLineIndex = 0;
            this.waitingForKeyRelease = false;
        }

        public void addLine(String line) {
            texte.add(line);
        }

        public void addMultipleLines(ArrayList<String> lines) {
            texte.addAll(lines);
        }

        public boolean isDialogueComplete() {
            return currentLineIndex >= texte.size();
        }

        public void render(SpriteBatch batch, OrthographicCamera uiCamera) {
            if (texte.isEmpty() || isDialogueComplete()) return;
            batch.setProjectionMatrix(uiCamera.combined);
            ShapeRenderer shapeRenderer = new ShapeRenderer();
            shapeRenderer.setProjectionMatrix(uiCamera.combined);

            float boxX = 50;
            float boxY = 50;
            float boxWidth = Gdx.graphics.getWidth() - 100;
            float boxHeight = 150;

            shapeRenderer.begin(ShapeRenderer.ShapeType.Filled);
            shapeRenderer.setColor(0, 0, 0, 0.7f); // Semi-transparent black
            shapeRenderer.rect(boxX, boxY, boxWidth, boxHeight); // Fixed position on screen
            shapeRenderer.end();

            // Draw the current line
            batch.begin();
            font.setColor(Color.WHITE);
            font.draw(batch, texte.get(currentLineIndex), boxX + 20, boxY + boxHeight - 20); // Position text within the box
            batch.end();

            if (Gdx.input.isKeyPressed(Input.Keys.SPACE) || Gdx.input.isKeyPressed((Input.Keys.E))){
                if (!waitingForKeyRelease) {
                    waitingForKeyRelease = true;
                    currentLineIndex++;
                }
            } else {
                waitingForKeyRelease = false;
            }
        }

        public void dispose() {
            shapeRenderer.dispose();
        }
    }
