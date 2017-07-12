package com.jiedro.canels.model.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.view.Tile;
import com.jiedro.canels.view.Textures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

/**
 * The terain is where we render tiles
 *
 * Created by jorge on 25/05/17.
 */

public class Terrain {

    private ArrayList<HashMap<Vector2, Tile>> terrains = new ArrayList<HashMap<Vector2, Tile> >();

    private HashMap<Vector2,Tile> backgroundTerrain = new HashMap<Vector2, Tile>();
    private HashMap<Vector2,Tile> foregroundTerrain = new HashMap<Vector2, Tile>();


    private Vector2 p = new Vector2(0,0);
    private Vector2 t = new Vector2(0,0);

    public Terrain(){
        terrains.add(backgroundTerrain);
        terrains.add(foregroundTerrain);
    }

    public void generateTrees( int x, int y, double freq){
        double freqX = freq * (x);
        double freqY = freq * (y);

        if(SimplexNoise.noise(freqX, freqY) > 0 && SimplexNoise.noise(freqX, freqY) < 0.11) {
            foregroundTerrain.put(new Vector2(x, 1 + y), Textures.getLeavesTile());
            foregroundTerrain.put(new Vector2(x, y), Textures.getLogTile());
            backgroundTerrain.put(new Vector2(x, y), Textures.getGrassTile());
        }
    }

    public void placeTile(double x, double y, Tile tile){
        backgroundTerrain.put(Helpers.screenToMap(x,y), tile);
    }

    public boolean canMove(double x, double y){
        for (HashMap<Vector2,Tile> terrain:terrains) {
            if(terrain.get(Helpers.screenToMap(x, y)) != null && terrain.get(Helpers.screenToMap(x, y)).isWalkable()){
                return true;
            }
        }
        return false;
    }

    public boolean canMapMove(double x, double y){
        for (HashMap<Vector2,Tile> terrain:terrains) {
            if(terrain.get(Helpers.mapToMap(x,y)) != null && terrain.get(Helpers.mapToMap(x,y)).isWalkable()){
                return true;
            }
        }
        return false;
    }


    public ArrayList<Vector2> isWalkableNeighbor(float x, float y){
        ArrayList<Vector2> results = new ArrayList<Vector2>();

            if (canMapMove(x + 1, y)) {
                results.add(new Vector2(x + 1, y));
            }
            if (canMapMove(x - 1, y)) {
                results.add(new Vector2(x - 1, y));
            }
            if (canMapMove(x, y + 1)) {
                results.add(new Vector2(x, y + 1));
            }
            if (canMapMove(x, y - 1)) {
                results.add(new Vector2(x, y - 1));
            }

        if ((x + y) % 2 == 0){
            Collections.reverse(results);
        }

        return results;
    }

    public void updateWorld(double xPos, double yPos){
        Vector2 playerMapPos = Helpers.screenToMap(xPos, yPos);
        int x = (int)playerMapPos.x;
        int y = (int)playerMapPos.y;

        for (int i = 0  - (GameVariables.CHUNK_SIZE/2); i < GameVariables.CHUNK_SIZE*2; i++) {
            for (int j = 0 ; j < GameVariables.CHUNK_SIZE +1; j++) {
                t.x = i+x;
                t.y = j+y;

                if(!backgroundTerrain.containsKey(t)) {
                    double freqX = GameVariables.TERRAIN_FREQUENCY * (i+x);
                    double freqY = GameVariables.TERRAIN_FREQUENCY * (j+y);

                    if (SimplexNoise.noise(freqX, freqY) < 0) {
                        backgroundTerrain.put(new Vector2(i+x, j+y), Textures.getWaterTile());
                    } else if ((SimplexNoise.noise(freqX, freqY) > 0.35)  //Land
                            && (SimplexNoise.noise(freqX, freqY) < 0.55)) {
                        backgroundTerrain.put(new Vector2(i + x, j + y), Textures.getGrassTile());
                        generateTrees(i+x, j+y, GameVariables.TREES_FREQUENCY);
                    }
                    else if ((SimplexNoise.noise(freqX, freqY) > 0.55)
                            && (SimplexNoise.noise(freqX, freqY) < 0.75)){
                        backgroundTerrain.put(new Vector2(i + x, j + y), Textures.getGrassTile());
                        generateTrees(i+x, j+y, GameVariables.TREES_FREQUENCY*4);
                    }
                    else if ((SimplexNoise.noise(freqX, freqY) > 0.75) //Mountain
                            && (SimplexNoise.noise(freqX, freqY) < 1)){
                        backgroundTerrain.put(new Vector2(i + x, j + y), Textures.getGrassTile());
                        generateTrees(i+x, j+y, GameVariables.TREES_FREQUENCY);
                    }
                    else {
                        backgroundTerrain.put(new Vector2(i+x, j+y), Textures.getGroundTile());
                    }
                }

            }
        }

    }

    public void drawBackground(Batch batch, double xPos, double yPos) {
        Vector2 playerMapPos = Helpers.screenToMap(xPos, yPos);
        int x = (int)playerMapPos.x;
        int y = (int)playerMapPos.y;


        for (int i = x-GameVariables.CHUNK_SIZE/2; i< x + GameVariables.CHUNK_SIZE+(GameVariables.CHUNK_SIZE/2); i++){
            for (int j = y-GameVariables.CHUNK_SIZE/2; j< y + GameVariables.CHUNK_SIZE+(GameVariables.CHUNK_SIZE/2); j++) {
                p.x = i;
                p.y = j;
                Tile tile = backgroundTerrain.get(p);
                if(tile!=null) {
                    batch.draw(tile.getTexture(),
                            p.x * GameVariables.TILES_SIZE,
                            p.y * GameVariables.TILES_SIZE,
                            tile.getRegionX(),
                            tile.getRegionY(),
                            GameVariables.TILES_SIZE,
                            GameVariables.TILES_SIZE);
                }
            }
        }
    }

    public void drawForeground(Batch batch, double xPos, double yPos) {
        Vector2 playerMapPos = Helpers.screenToMap(xPos, yPos);
        int x = (int)playerMapPos.x;
        int y = (int)playerMapPos.y;


        for (int i = x-GameVariables.CHUNK_SIZE/2; i< x + GameVariables.CHUNK_SIZE+(GameVariables.CHUNK_SIZE/2); i++){
            for (int j = y; j< y + GameVariables.CHUNK_SIZE; j++) {
                p.x = i;
                p.y = j;
                Tile tile = foregroundTerrain.get(p);
                if(tile!=null) {
                    batch.draw(tile.getTexture(),
                            p.x * GameVariables.TILES_SIZE,
                            p.y * GameVariables.TILES_SIZE,
                            tile.getRegionX(),
                            tile.getRegionY(),
                            GameVariables.TILES_SIZE,
                            GameVariables.TILES_SIZE);
                }
            }
        }
    }

}
