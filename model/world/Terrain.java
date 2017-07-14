package com.jiedro.canels.model.world;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.view.Tile;
import com.jiedro.canels.view.Textures;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Random;

/**
 * The terain is where we render tiles
 *
 * Created by jorge on 25/05/17.
 */

public class Terrain {

    private ArrayList<HashMap<Vector2, Tile>> terrains = new ArrayList<HashMap<Vector2, Tile> >();

    private HashMap<Vector2,Tile> backgroundTerrain = new HashMap<Vector2, Tile>();

    private HashMap<Vector2,Tile> vegetationTerrain = new HashMap<Vector2, Tile>();

    private HashMap<Vector2,Tile> foregroundTerrain = new HashMap<Vector2, Tile>();


    private Vector2 p = new Vector2(0,0);
    private Vector2 t = new Vector2(0,0);

    public Terrain(){
        terrains.add(backgroundTerrain);
        terrains.add(foregroundTerrain);
    }

    public void generateVegetation(int x, int y, double freq){
        double freqX = freq * (x);
        double freqY = freq * (y);

        Vector2 where = new Vector2(x, y);


        if(!foregroundTerrain.containsKey(where)) {
            double simplexRockNoise = SimplexNoise.noise(freqX * GameVariables.CHUNK_SIZE, freqY * GameVariables.CHUNK_SIZE);
            double simplexDungeonNoise = SimplexNoise.noise(freqX * GameVariables.CHUNK_SIZE, freqY * GameVariables.CHUNK_SIZE);
            double simplexVegetationNoise = SimplexNoise.noise(freqX, freqY);
            double simplexTreesNoise = SimplexNoise.noise(freqX, freqY);


            if (simplexRockNoise > 0.11 && simplexRockNoise < 0.111) { //Dungeon
                foregroundTerrain.put(where, Textures.getDoorTile());
            }

            if (simplexRockNoise > 0.90 && simplexRockNoise < 1) { //Rocks
                vegetationTerrain.put(where, Textures.getGrassRockTile());
            }

            if (simplexTreesNoise > 0 && simplexTreesNoise < 0.09) {
                Random random = new Random();

                int last = random.nextInt(2)+1;

                foregroundTerrain.put(where, Textures.getLogTile());
                for (int i = 0; i < last; i++) {
                    foregroundTerrain.put(where.cpy().add(0,i), Textures.getLogTile());
                }
                foregroundTerrain.put(where.cpy().add(0,last), Textures.getLeavesTile());
            }
        }
    }

    public void generateCactus(int x, int y, double freq){
        double freqX = freq * (x);
        double freqY = freq * (y);

        double simplexNoise = SimplexNoise.noise(freqX, freqY);

        if(simplexNoise > 0.5 && simplexNoise < 0.51) {
            foregroundTerrain.put(new Vector2(x, y), Textures.getCactusTile());
        }
    }

    public void placeTile(double x, double y, Tile tile){
        backgroundTerrain.put(Helpers.screenToMap(x,y), tile);
    }

    public boolean canMove(double x, double y){
        boolean result = false;
        for (HashMap<Vector2,Tile> terrain:terrains) {
            if(terrain.get(Helpers.screenToMap(x, y)) != null){
                if(terrain.get(Helpers.screenToMap(x, y)).isWalkable()) {
                    result = true;
                } else {
                    result = false;
                }
            }
        }
        return result;
    }

    public boolean canMapMove(double x, double y){
        boolean result = false;
        for (HashMap<Vector2,Tile> terrain:terrains) {
            if(terrain.get(Helpers.mapToMap(x, y)) != null){
                if(terrain.get(Helpers.mapToMap(x, y)).isWalkable()) {
                    result = true;
                } else {
                    result = false;
                }
            }
        }
        return result;
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
            for (int j = 0 - (GameVariables.CHUNK_SIZE/2); j < GameVariables.CHUNK_SIZE*2; j++) {
                t.x = i+x;
                t.y = j+y;

                if(!backgroundTerrain.containsKey(t)) {
                    double freqX = GameVariables.TERRAIN_FREQUENCY * (i+x);
                    double freqY = GameVariables.TERRAIN_FREQUENCY * (j+y);

                    double simplexNoise = SimplexNoise.noise(freqX, freqY);

                    if (simplexNoise < 0) {
                        backgroundTerrain.put(new Vector2(i+x, j+y), Textures.getWaterTile());
                    } else if ((simplexNoise > 0.35)  //Land
                            && (simplexNoise < 0.55)) {
                        backgroundTerrain.put(new Vector2(i + x, j + y), Textures.getGrassTile());
                        generateVegetation(i+x, j+y, GameVariables.TREES_FREQUENCY);
                    }
                    else if ((simplexNoise > 0.55)
                            && (simplexNoise < 0.75)){
                        backgroundTerrain.put(new Vector2(i + x, j + y), Textures.getGrassTile());
                        generateVegetation(i+x, j+y, GameVariables.TREES_FREQUENCY);
                    }
                    else if ((simplexNoise > 0.75) //Mountain
                            && (simplexNoise < 1)){
                        backgroundTerrain.put(new Vector2(i + x, j + y), Textures.getGrassTile());
                        generateVegetation(i+x, j+y, GameVariables.TREES_FREQUENCY);
                    }
                    else {
                        backgroundTerrain.put(new Vector2(i+x, j+y), Textures.getGroundTile());
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

    public void drawTerrain(Batch batch, HashMap<Vector2, Tile> terrain, int x, int y){
        for (int i = x-GameVariables.CHUNK_SIZE/2; i< x + GameVariables.CHUNK_SIZE+(GameVariables.CHUNK_SIZE/2); i++){
            for (int j = y; j< y + GameVariables.CHUNK_SIZE+(GameVariables.CHUNK_SIZE/2); j++) {
                p.x = i;
                p.y = j;
                Tile tile = terrain.get(p);
                if(tile!=null) {
                    batch.setColor(tile.getColor());
                    batch.setBlendFunction(Batch.C1,Batch.X4);
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

        drawTerrain(batch, foregroundTerrain, x, y);
    }

}
