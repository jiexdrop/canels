package com.jiedro.canels.model.world;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.view.GameTextures;
import com.jiedro.canels.view.TileType;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * The terrain is where we render tiles
 *
 * Created by jiexdrop on 25/05/17.
 */

public class Terrain {


    private HashMap<Vector2,TileType> backgroundTerrain = new HashMap<Vector2, TileType>();

    private HashMap<Vector2,TileType> vegetationTerrain = new HashMap<Vector2, TileType>();

    private HashMap<Vector2,TileType> foregroundTerrain = new HashMap<Vector2, TileType>();


    private Vector2 p = new Vector2(0,0);
    private Vector2 t = new Vector2(0,0);

    public Terrain(){

    }

    public void generateVegetation(int x, int y, double freq){
        double freqX = freq * (x);
        double freqY = freq * (y);

        Vector2 where = new Vector2(x, y);

        //TODO Better generation

        if(!foregroundTerrain.containsKey(where)) {
            double simplexRockNoise = SimplexNoise.noise(freqX * GameVariables.CHUNK_SIZE, freqY * GameVariables.CHUNK_SIZE);
            double simplexDungeonNoise = SimplexNoise.noise(freqX * GameVariables.CHUNK_SIZE, freqY * GameVariables.CHUNK_SIZE);
            double simplexVegetationNoise = SimplexNoise.noise(freqX, freqY);
            double simplexTreesNoise = SimplexNoise.noise(freqX, freqY);


            if (simplexRockNoise > 0.11 && simplexRockNoise < 0.111) { //Dungeon
                foregroundTerrain.put(where, TileType.DOOR);
            }

            if (simplexRockNoise > 0.90 && simplexRockNoise < 1) { //Rocks
                vegetationTerrain.put(where, TileType.ROCK);
            }

            if (simplexTreesNoise > 0 && simplexTreesNoise < 0.09) {
                Random random = new Random();

                int last = random.nextInt(2)+1;

                foregroundTerrain.put(where, TileType.LOG);
                for (int i = 0; i < last; i++) {
                    foregroundTerrain.put(where.cpy().add(0,i), TileType.LOG);
                }
                foregroundTerrain.put(where.cpy().add(0,last), TileType.LEAVES);
            }
        }
    }

    public void generateCactus(int x, int y, double freq){
        double freqX = freq * (x);
        double freqY = freq * (y);

        double simplexNoise = SimplexNoise.noise(freqX, freqY);

        if(simplexNoise > 0.5 && simplexNoise < 0.51) {
            foregroundTerrain.put(new Vector2(x, y), TileType.CACTUS);
        }
    }

    public void placeTile(double x, double y, TileType tileType){
        backgroundTerrain.put(Helpers.screenToMap(x,y), tileType);
    }

    public boolean isScreenWalkable(double x, double y){
        boolean result = false;

        if(backgroundTerrain.get(Helpers.screenToMap(x, y)) != null){
            result = backgroundTerrain.get(Helpers.screenToMap(x, y)).walkable;
        }

        return result;
    }

    public boolean isMapWalkable(double x, double y){
        boolean result = false;

        if(backgroundTerrain.get(Helpers.mapToMap(x, y)) != null){
            result = backgroundTerrain.get(Helpers.mapToMap(x, y)).walkable;
        }

        return result;
    }


    public ArrayList<Vector2> isWalkableNeighbor(float x, float y){
        ArrayList<Vector2> results = new ArrayList<Vector2>();

        if (isMapWalkable(x + 1, y)) {
            results.add(new Vector2(x + 1, y));
        }
        if (isMapWalkable(x - 1, y)) {
            results.add(new Vector2(x - 1, y));
        }
        if (isMapWalkable(x, y + 1)) {
            results.add(new Vector2(x, y + 1));
        }
        if (isMapWalkable(x, y - 1)) {
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
            for (int j = 0 - (GameVariables.CHUNK_SIZE/2); j < GameVariables.CHUNK_SIZE*2; j++) {
                t.x = i+x;
                t.y = j+y;

                if(!backgroundTerrain.containsKey(t)) {
                    double freqX = GameVariables.TERRAIN_FREQUENCY * (i+x);
                    double freqY = GameVariables.TERRAIN_FREQUENCY * (j+y);

                    double simplexNoise = SimplexNoise.noise(freqX, freqY);

                    if (simplexNoise < 0) {
                        backgroundTerrain.put(new Vector2(i+x, j+y), TileType.WATER);
                    } else if ((simplexNoise > 0.35)  //Land
                            && (simplexNoise < 0.55)) {
                        backgroundTerrain.put(new Vector2(i + x, j + y), TileType.GRASS);
                        generateVegetation(i+x, j+y, GameVariables.TREES_FREQUENCY);
                    }
                    else if ((simplexNoise > 0.55)
                            && (simplexNoise < 0.75)){
                        backgroundTerrain.put(new Vector2(i + x, j + y), TileType.GRASS);
                        generateVegetation(i+x, j+y, GameVariables.TREES_FREQUENCY);
                    }
                    else if ((simplexNoise > 0.75) //Mountain
                            && (simplexNoise < 1)){
                        backgroundTerrain.put(new Vector2(i + x, j + y), TileType.GRASS);
                        generateVegetation(i+x, j+y, GameVariables.TREES_FREQUENCY);
                    }
                    else {
                        backgroundTerrain.put(new Vector2(i+x, j+y), TileType.GROUND);
                        generateCactus(i+x,j+y, GameVariables.TREES_FREQUENCY*GameVariables.CHUNK_SIZE);
                    }
                }

            }
        }

    }

    public void drawBackground(Batch batch, double xPos, double yPos) {
        Vector2 playerMapPos = Helpers.screenToMap(xPos, yPos);
        int x = (int)playerMapPos.x;
        int y = (int)playerMapPos.y;

        drawTerrain(batch, backgroundTerrain, x, y);
        drawTerrain(batch, vegetationTerrain, x, y);

    }

    public void drawTerrain(Batch batch, HashMap<Vector2, TileType> terrain, int x, int y){
        for (int i = x-GameVariables.CHUNK_SIZE/2; i< x + GameVariables.CHUNK_SIZE+(GameVariables.CHUNK_SIZE/2); i++){
            for (int j = y -GameVariables.CHUNK_SIZE/8; j< y + GameVariables.CHUNK_SIZE+(GameVariables.CHUNK_SIZE/8); j++) {
                p.x = i;
                p.y = j;
                TileType tile = terrain.get(p);
                if(tile!=null) {
                    TextureRegion tileTextureRegion = GameTextures.getTextureRegion(tile);
                    batch.setColor(tile.color);
                    batch.draw(tileTextureRegion.getTexture(),
                            p.x * GameVariables.TILES_SIZE,
                            p.y * GameVariables.TILES_SIZE,
                            tileTextureRegion.getRegionX(),
                            tileTextureRegion.getRegionY(),
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

        drawTerrain(batch, foregroundTerrain, x, y);
    }

    public void removeTree(float x, float y) {
        Vector2 tilesPos = Helpers.screenToMap(x, y);
        for (Vector2 t:getTreeTiles(tilesPos)) {
            foregroundTerrain.remove(t);
        }
    }

    public void removeTile(Vector2 pos) {
        if(foregroundTerrain.containsKey(pos))
            foregroundTerrain.remove(pos);
        /*if(backgroundTerrain.containsKey(pos))
            backgroundTerrain.remove(pos);*/
        if(vegetationTerrain.containsKey(pos))
            vegetationTerrain.remove(pos);
        /*if(!backgroundTerrain.containsKey(pos))
            backgroundTerrain.put(pos, TileType.ROCK);*/
    }

    private ArrayList<Vector2> getTreeTiles(Vector2 treePos){
        ArrayList<Vector2> result = new ArrayList<Vector2>();
        result.add(treePos.cpy());
        boolean completed = false;

        while (!completed){
            treePos.add(0,1);
            if(foregroundTerrain.get(treePos)==null)
                completed = true;
            result.add(treePos.cpy());
        }
        return result;
    }


    public TileType getVegetationTileFromPos(Vector2 pos) {
        return foregroundTerrain.get(pos);
    }
}
