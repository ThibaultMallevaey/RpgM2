package com.UE36.RpgM2.Screens;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.files.FileHandle;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.utils.JsonReader;
import com.badlogic.gdx.utils.JsonValue;

public class JsonMapLoader {

    // début de ce à quoi pourrait ressembler notre classe pour importer et lire notre carte depuis un Json

    private final int tileWidth;
    private final int tileHeight;

    public JsonMapLoader(int tileWidth, int tileHeight) {
        this.tileWidth = tileWidth;
        this.tileHeight = tileHeight;
    }

    public TiledMap load(String jsonFilePath) {
        TiledMap map = new TiledMap();
        FileHandle file = Gdx.files.internal(jsonFilePath);
        JsonReader jsonReader = new JsonReader();
        JsonValue root = jsonReader.parse(file);

        // Parse layers, focusing on tile layers
        JsonValue layers = root.get("layers");
        for (JsonValue layerJson : layers) {
            if (layerJson.getString("type").equals("tilelayer")) {
                TiledMapTileLayer tileLayer = parseTileLayer(layerJson);
                map.getLayers().add(tileLayer);
            }
        }

        JsonValue tilesets = root.get("tilesets");


        return map;
    }

    private TiledMapTileLayer parseTileLayer(JsonValue layerJson) {
        int width = layerJson.getInt("width");
        int height = layerJson.getInt("height");
        TiledMapTileLayer layer = new TiledMapTileLayer(width, height, tileWidth, tileHeight);
        return layer;
    }

        // Split the tileset texture into individual tiles
        //TextureRegion[][] tiles = TextureRegion.split(tileWidth, tileHeight);

        /* Parse the tile data
        JsonValue data = layerJson.get("data");
        int x = 0, y = height - 1;
        for (JsonValue tileValue : data) {
            int gid = tileValue.asInt();
            if (gid != 0) {  // 0 typically represents an empty tile
                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();

                // Assuming gid corresponds to tileset order; adjust as needed based on JSON structure
                int tileX = (gid - 1) % tiles[0].length;
                int tileY = (gid - 1) / tiles[0].length;

                TextureRegion tileRegion = tiles[tileY][tileX];
                cell.setTile(new StaticTiledMapTile(tileRegion));
                layer.setCell(x, y, cell);
            }
            x++;
            if (x == width) {
                x = 0;
                y--;
            }
        }

        return layer;
    }

    public void dispose() {
    }*/

    /*public void main(String[] args) {
        JsonMapLoader map = new JsonMapLoader(1, 1);
        map.load("carteF.json");
        System.out.println(map);

    }*/

}
