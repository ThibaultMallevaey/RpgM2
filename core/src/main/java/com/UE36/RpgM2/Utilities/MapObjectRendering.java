package com.UE36.RpgM2.Utilities;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTile;

import java.util.ArrayList;

public class MapObjectRendering {
    // Class pour gérer le rendering des couches d'objets
    private SpriteBatch batch;
    private TiledMap map;

    public MapObjectRendering(SpriteBatch batch, TiledMap map) {
        this.batch = batch;
        this.map = map;
    }

    private ArrayList<MapObject> getMapObjects(String layerName) {
        // permet de récupérer sous forme de liste les objets d'une couche d'objet tiled
        ArrayList<MapObject> mapObjects = new ArrayList<>();
        MapLayer layer = map.getLayers().get(layerName);
        for (MapObject object : layer.getObjects()) {
                mapObjects.add(object);
            }
        // trier la liste
        mapObjects.sort((o1, o2) -> {
            float y1 = (float) o1.getProperties().get("y");
            float y2 = (float) o2.getProperties().get("y");
            return Float.compare(y2, y1);
        });

        return mapObjects;
    }

    public void renderLayerObjectsByTexture(String layerName, String texturePath) {
        // rendering à partir d'une texture définie en png (surtout pour la couche arbre pour éviter de
        // gérer tout les probèmes de rotation
        Texture texture = new Texture(texturePath);
        ArrayList<MapObject> objects = getMapObjects(layerName);
        for (MapObject object : objects) {
            float x = (float) object.getProperties().get("x");
            float y = (float) object.getProperties().get("y");

            Integer gid = object.getProperties().get("gid", Integer.class);
            if (gid != null) {
                TiledMapTile tile = map.getTileSets().getTile(gid);
                if (tile != null) {
                    batch.draw(texture, x, y);
                }
            }
        }
    }

    public void renderLayerObjectsByTileId(String layerName) {
        // render les objets directement depuis les tilesets
        MapLayer layer = map.getLayers().get(layerName);
        ArrayList<MapObject> objects = getMapObjects(layerName);
        if (!objects.isEmpty()){

            for (MapObject object : objects) {
                // récupérer les propriétés de chaque objet pour les positionner correctement
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
                        batch.draw(tile.getTextureRegion(), x, y, originX, originY, width, height, scaleX, scaleY, rotation);
                    }
                }
            }
        }
    }
}
