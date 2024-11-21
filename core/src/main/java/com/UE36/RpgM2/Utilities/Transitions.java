package com.UE36.RpgM2.Utilities;

import com.UE36.RpgM2.Characters.MainCharacter;
import com.UE36.RpgM2.RpgGame;
import com.badlogic.gdx.maps.MapLayer;
import com.badlogic.gdx.maps.MapObject;
import com.badlogic.gdx.maps.objects.EllipseMapObject;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.math.Ellipse;
import com.badlogic.gdx.math.Vector2;

import java.util.ArrayList;

public class Transitions {
    private Vector2 position;
    private TiledMap map;
    private MainCharacter mainCharacter;
    private MapLayer transitionLayer;
    private RpgGame game;

    public Transitions(Vector2 position, TiledMap map, MainCharacter mainCharacter) {
        this.position = position;
        this.map = map;
        this.mainCharacter = mainCharacter;
        this.game = game;
    }

    public ArrayList<EllipseMapObject> getTransitionObjects(String layerName) {
        transitionLayer = map.getLayers().get(layerName);
        ArrayList<EllipseMapObject> ellipseMapObjects = new ArrayList<>();
        if (transitionLayer != null) {
            for (MapObject transitionObject : transitionLayer.getObjects()) {
                if (transitionObject instanceof EllipseMapObject) {
                    ellipseMapObjects.add((EllipseMapObject) transitionObject);
                }
            }
        }
        return ellipseMapObjects;
    }

    public boolean onTransition(String layerName){
        ArrayList<EllipseMapObject> ellipseMapObjects = getTransitionObjects(layerName);
        for (EllipseMapObject ellipseMapObject : ellipseMapObjects) {
            Ellipse ellipse = ellipseMapObject.getEllipse();
            if (ellipse.contains(position)) {
                return true;
                }
            }
        return false;
    }

}
