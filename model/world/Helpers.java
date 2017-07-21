package com.jiedro.canels.model.world;

import com.badlogic.gdx.math.Vector2;
import com.jiedro.canels.GameVariables;

/**
 * Helpers to convert coordinates from screen to map
 * Created by jiexdrop on 09/07/17.
 */

public class Helpers {

    public static Vector2 screenToMap(double x, double y){
        final Vector2 screenToMap = new Vector2();
        screenToMap.x = Math.round((x-(GameVariables.TILES_SIZE/2))/GameVariables.TILES_SIZE);
        screenToMap.y = Math.round((y-(GameVariables.TILES_SIZE/2))/GameVariables.TILES_SIZE);
        return screenToMap;
    }

    public static Vector2 screenToMap(Vector2 screenToMap){
        return screenToMap(screenToMap.x, screenToMap.y);
    }

    public static Vector2 mapToMap(double x, double y){
        final Vector2 mapToMap = new Vector2();
        mapToMap.x = Math.round(x);
        mapToMap.y = Math.round(y);
        return mapToMap;
    }

    public static Vector2 mapToScreen(double x, double y){
        final Vector2 mapToScreen = new Vector2();
        mapToScreen.x = Math.round(x*GameVariables.TILES_SIZE);
        mapToScreen.y = Math.round(y*GameVariables.TILES_SIZE);
        return mapToScreen;
    }

    public static Vector2 itemToScreen(Vector2 pos, int i){
        final Vector2 mapToScreen = new Vector2();
        mapToScreen.x = Math.round((pos.x*GameVariables.TILES_SIZE)+(GameVariables.ITEM_SIZE*1.5));
        mapToScreen.y = Math.round((pos.y*GameVariables.TILES_SIZE)+(GameVariables.ITEM_SIZE*1.5));
        return mapToScreen;
    }

    public static Vector2 mapToScreen(Vector2 map){
        return mapToScreen(map.x, map.y);
    }

    public static boolean checkIfNear(double x1, double y1, float r1, double x2, double y2, float r2){
        return Math.abs((x1 - x2) * (x1 - x2) + (y1 - y2) * (y1 - y2)) < (r1 + r2) * (r1 + r2);
    }

}
