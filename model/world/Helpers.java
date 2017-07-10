package com.jiedro.canels.model.world;

import com.badlogic.gdx.math.Vector2;
import com.jiedro.canels.GameVariables;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jiexdrop on 09/07/17.
 */

public class Helpers {

    public static Vector2 screenToMap(double x, double y){
        final Vector2 screenToMap = new Vector2();
        if(x<0 && y>0){
            screenToMap.x = (Math.round(x)/ GameVariables.TILES_SIZE)-1;
            screenToMap.y = Math.round(y)/GameVariables.TILES_SIZE;
        } else if(x>0 && y<0) {
            screenToMap.x = (Math.round(x)/GameVariables.TILES_SIZE);
            screenToMap.y = (Math.round(y)/GameVariables.TILES_SIZE)-1;
        } else if(x<0 && y<0) {
            screenToMap.x = (Math.round(x)/GameVariables.TILES_SIZE)-1;
            screenToMap.y = (Math.round(y)/GameVariables.TILES_SIZE)-1;
        } else {
            screenToMap.x = Math.round(x)/GameVariables.TILES_SIZE;
            screenToMap.y = Math.round(y)/GameVariables.TILES_SIZE;
        }
        return screenToMap;
    }

    public static Vector2 screenToMap(Vector2 screenToMap){
        return screenToMap(screenToMap.x, screenToMap.y);
    }

    /**
     * when you realised you... missed something...
     * TODO I care
     * @param x waidt
     * @param y tmnfs
     * @return that's wrong
     */
    public static Vector2 mapToMap(double x, double y){
        final Vector2 mapToMap = new Vector2();
        if(x<=0 && y>0){
            mapToMap.x = (Math.round(x))-1;
            mapToMap.y = Math.round(y);
        } else if(x>0 && y<=0) {
            mapToMap.x = (Math.round(x));
            mapToMap.y = (Math.round(y))-1;
        } else if(x<0 && y<0) {
            mapToMap.x = (Math.round(x))-1;
            mapToMap.y = (Math.round(y))-1;
        } else {
            mapToMap.x = Math.round(x);
            mapToMap.y = Math.round(y);
        }
        return mapToMap;
    }

    public static Vector2 mapToScreen(double x, double y){
        final Vector2 mapToScreen = new Vector2();
        if(x<0 && y>0){
            mapToScreen.x = (Math.round(x)* GameVariables.TILES_SIZE)-1;
            mapToScreen.y = Math.round(y)*GameVariables.TILES_SIZE;
        } else if(x>0 && y<0) {
            mapToScreen.x = (Math.round(x)*GameVariables.TILES_SIZE);
            mapToScreen.y = (Math.round(y)*GameVariables.TILES_SIZE)-1;
        } else if(x<0 && y<0) {
            mapToScreen.x = (Math.round(x)*GameVariables.TILES_SIZE)-1;
            mapToScreen.y = (Math.round(y)*GameVariables.TILES_SIZE)-1;
        } else {
            mapToScreen.x = Math.round(x)*GameVariables.TILES_SIZE;
            mapToScreen.y = Math.round(y)*GameVariables.TILES_SIZE;
        }
        return mapToScreen;
    }

    public static Vector2 mapToScreen(Vector2 map){
        return mapToScreen(map.x, map.y);
    }

    public static ArrayDeque<Vector2> convertMovementPoints(HashMap<Vector2, Vector2> movementPoints){
        ArrayDeque<Vector2> result = new ArrayDeque<Vector2>();
        ArrayList<Vector2> list = new ArrayList<Vector2>();
        if(movementPoints!=null) {
            for (Map.Entry<Vector2, Vector2> e : movementPoints.entrySet()) {
                list.add(e.getKey());
                if(e.getValue()!=null)
                list.add(e.getValue());
            }
            Collections.reverse(list);
            for (Vector2 v:list) {
                result.push(mapToScreen(v));
            }
        }
        return result;
    }

}
