package com.UE36.RpgM2.Utilities;

import com.UE36.RpgM2.Characters.MainCharacter;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.PolygonMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Polygon;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Collisions {
    private MapLayer collisionLayer;
    private TiledMap map;
    private MainCharacter character;


    public Collisions(MainCharacter character) {
        this.character = character;
    }

    public void setMap(TiledMap map) {
        this.map = map;
        collisionLayer = map.getLayers().get("Collision");
    }

    private ArrayList<PolygonMapObject> getCollisionObjects() {
        ArrayList<PolygonMapObject> collisionObjects = new ArrayList<>();
        if (collisionLayer != null) {
            for (MapObject collisionObject : collisionLayer.getObjects()) {
                if (collisionObject instanceof PolygonMapObject) {
                    collisionObjects.add((PolygonMapObject) collisionObject);
                }
            }
        }
        return collisionObjects;
    }

    public boolean doCollide(Vector2 newPosition) {
        ArrayList<PolygonMapObject> collisionObjects = getCollisionObjects();
        for (PolygonMapObject polygonObject : collisionObjects) {
            Polygon polygon = polygonObject.getPolygon();
            if (polygon.contains(newPosition.x + 15, newPosition.y + 15)) {
                // +15 = corrige plus ou moins l'offset des bordures due à la vue de la caméra
                return true;
            }
        }
        return false;
    }



}
