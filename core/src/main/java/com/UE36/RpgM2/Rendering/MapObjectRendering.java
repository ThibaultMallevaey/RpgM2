package com.UE36.RpgM2.Rendering;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;

import java.util.ArrayList;

public class MapObjectRendering {private SpriteBatch batch;
    private TiledMap map;

    public MapObjectRendering(SpriteBatch batch, TiledMap map) {
        this.batch = batch;
        this.map = map;
    }

    private ArrayList<MapObject> getMapObjects(String layerName) {
        ArrayList<MapObject> mapObjects = new ArrayList<>();
        MapLayer layer = map.getLayers().get(layerName);
        for (MapObject object : layer.getObjects()) {
                mapObjects.add(object);
            }
        mapObjects.sort((o1, o2) -> {
            float y1 = (float) o1.getProperties().get("y");
            float y2 = (float) o2.getProperties().get("y");
            return Float.compare(y2, y1);
        });

        return mapObjects;
    }

    // Render a layer by texture
    public void renderLayerObjectsByTexture(String layerName, String texturePath) {
        Texture texture = new Texture(texturePath);
        ArrayList<MapObject> objects = getMapObjects(layerName);
        for (MapObject object : objects) {
            float x = (float) object.getProperties().get("x");
            float y = (float) object.getProperties().get("y");
            float width = (float) object.getProperties().get("width");
            float height = (float) object.getProperties().get("height");

            float originX = width / 2;
            float originY = height / 2;

            float scaleX = object.getProperties().containsKey("scaleX")
                ? (float) object.getProperties().get("scaleX")
                : 1f; // Default scaleX is 1

            float scaleY = object.getProperties().containsKey("scaleY")
                ? (float) object.getProperties().get("scaleY")
                : 1f; // Default scaleY is 1

            float rotation = object.getProperties().containsKey("rotation")
                ? (float) object.getProperties().get("rotation")
                : 0f;

            Integer gid = object.getProperties().get("gid", Integer.class);
            if (gid != null) {
                TiledMapTile tile = map.getTileSets().getTile(gid);
                if (tile != null) {
                    batch.draw(texture, x, y, originX, originY, width, height, scaleX, scaleY);
                }
            }
        }
    }

    public void renderLayerObjectsByTileId(String layerName) {
        MapLayer layer = map.getLayers().get(layerName);
        ArrayList<MapObject> objects = getMapObjects(layerName);

        for (MapObject object : objects) {
            float x = (float) object.getProperties().get("x");
            float y = (float) object.getProperties().get("y");
            float width = (float) object.getProperties().get("width");
            float height = (float) object.getProperties().get("height");

            float originX = width / 2;
            float originY = height / 2;

            float scaleX = object.getProperties().containsKey("scaleX")
                ? (float) object.getProperties().get("scaleX")
                : 1f;

            float scaleY = object.getProperties().containsKey("scaleY")
                ? (float) object.getProperties().get("scaleY")
                : 1f;

            float rotation = object.getProperties().containsKey("rotation")
                ? (float) object.getProperties().get("rotation")
                : 0f;



            Integer gid = object.getProperties().get("gid", Integer.class);
            if (gid != null) {
                TiledMapTile tile = map.getTileSets().getTile(gid);
                if (tile != null) {
                    if (rotation != 0) {
                        System.out.println("Rendering tile: " + gid + " at (" + x + ", " + y + ") with rotation: " + rotation);
                    }
                    batch.draw(tile.getTextureRegion(), x, y, originX, originY, width, height, scaleX, scaleY, rotation);
                }
            }
        }
    }
}
