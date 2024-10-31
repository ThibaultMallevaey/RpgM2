package com.UE36.RpgM2.Rendering;

import com.badlogic.gdx.Gdx;
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
            batch.draw(texture, x, y);
        }
    }

    // Render a layer by gid = tileId
    public void renderLayerObjectsByTileId(String layerName) {
        MapLayer layer = map.getLayers().get(layerName);
        ArrayList<MapObject> objects = getMapObjects(layerName);

        for (MapObject object : objects) {
            float x = (float) object.getProperties().get("x");
            float y = (float) object.getProperties().get("y");

            Integer gid = object.getProperties().get("gid", Integer.class);
            if (gid != null) {
                TiledMapTile tile = map.getTileSets().getTile(gid);
                if (tile != null) {
                    batch.draw(tile.getTextureRegion(), x, y);
                }
            }
        }
    }
}
