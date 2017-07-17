package com.jiedro.canels.model.world;

import com.badlogic.gdx.math.Vector2;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.view.Textures;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

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

    public static Vector2 mapToScreen(Vector2 map){
        return mapToScreen(map.x, map.y);
    }


}
