package com.jiedro.canels.model.world;

import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.math.Vector2;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.view.Tile;
import com.jiedro.canels.view.Tiles;

import java.util.HashMap;
import java.util.Map;

/**
 * The terain is where we render tiles
 *
 * Created by jorge on 25/05/17.
 */

public class Terrain {

    private Map<Vector2, Tile> terrain = new HashMap<Vector2, com.jiedro.canels.view.Tile>();

    private Vector2 p = new Vector2(0,0);
    private Vector2 t = new Vector2(0,0);

    public Terrain(){
        terrain = new HashMap<Vector2, Tile>();
    }

    public Tile generateStructure(int x, int y){
        if(Math.random()>0.99) {
            terrain.put(new Vector2(x, y), Tiles.getWallTile());
            return Tiles.getWallTile();
        }
        return Tiles.getGrassTile();
    }

    public void placeTile(double x, double y, Tile tile){
        terrain.put(screenToMap(x,y), tile);
    }

    public boolean canMove(double x, double y){
        return terrain.get(screenToMap(x, y)) != null && terrain.get(screenToMap(x, y)).isWalkable();
    }

    public static Vector2 screenToMap(double x, double y){
        final Vector2 screenToMap = new Vector2();
        if(x<0 && y>0){
            screenToMap.x = (Math.round(x)/GameVariables.TILES_SIZE)-1;
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

    public void updateWorld(double xPos, double yPos){
        Vector2 playerMapPos = screenToMap(xPos, yPos);
        int x = (int)playerMapPos.x;
        int y = (int)playerMapPos.y;

        for (int i = 0  - (GameVariables.CHUNK_SIZE/2); i < GameVariables.CHUNK_SIZE*2; i++) {
            for (int j = 0 ; j < GameVariables.CHUNK_SIZE +1; j++) {
                t.x = i+x;
                t.y = j+y;

                if(!terrain.containsKey(t)) {

                    if (SimplexNoise.noise(GameVariables.FREQUENCY * (i+x), GameVariables.FREQUENCY * (j+y)) < 0) {
                        terrain.put(new Vector2(i+x, j+y), Tiles.getWaterTile());
                    } else if ((SimplexNoise.noise(GameVariables.FREQUENCY * (i+x), GameVariables.FREQUENCY * (j+y)) > 0.35)
                            && (SimplexNoise.noise(GameVariables.FREQUENCY * (i+x), GameVariables.FREQUENCY * (j+y)) < 1)) {
                        Tile tile = generateStructure(i+x, j+y);
                        terrain.put(new Vector2(i+x, j+y), tile);

                    } else {
                        terrain.put(new Vector2(i+x, j+y), Tiles.getGroundTile());
                    }
                }

            }
        }

    }

    public void draw(Batch batch, double xPos, double yPos) {
        Vector2 playerMapPos = screenToMap(xPos, yPos);
        int x = (int)playerMapPos.x;
        int y = (int)playerMapPos.y;


        for (int i = x-GameVariables.CHUNK_SIZE/2 ;
             i< x + GameVariables.CHUNK_SIZE+(GameVariables.CHUNK_SIZE/2); i++){
            for (int j = y; j< y + GameVariables.CHUNK_SIZE + 1; j++) {
                p.x = i;
                p.y = j;
                Tile tile = terrain.get(p);
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
