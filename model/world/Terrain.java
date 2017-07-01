package com.jiedro.canels.model.world;

import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Batch;
import com.badlogic.gdx.math.Vector2;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.entity.Player;
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
        return terrain.get(screenToMap(x,y))==null?false:terrain.get(screenToMap(x,y)).isWalkable();
    }

    public Vector2 screenToMap(double x, double y){
        if(x<0 && y>0){
            return new Vector2((Math.round(x)/GameVariables.TILES_SIZE)-1,Math.round(y)/GameVariables.TILES_SIZE);
        } else if(x>0 && y<0) {
            return new Vector2((Math.round(x)/GameVariables.TILES_SIZE),(Math.round(y)/GameVariables.TILES_SIZE)-1);
        } else if(x<0 && y<0) {
            return new Vector2((Math.round(x)/GameVariables.TILES_SIZE)-1,(Math.round(y)/GameVariables.TILES_SIZE)-1);
        } else {
            return new Vector2(Math.round(x)/GameVariables.TILES_SIZE,Math.round(y)/GameVariables.TILES_SIZE);
        }
    }

    public void updateWorld(double xPos, double yPos){
        Vector2 playerMapPos = screenToMap(xPos, yPos);
        int x = (int)playerMapPos.x;
        int y = (int)playerMapPos.y;

        for (int i = 0; i < GameVariables.CHUNK_SIZE; i++) {
            for (int j = 0; j < GameVariables.CHUNK_SIZE; j++) {

                if(!terrain.containsKey(new Vector2(i+x,j+y))) {

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
        for (Map.Entry<Vector2, Tile> tile:terrain.entrySet()) {
            if(tile.getKey().x > x - GameVariables.CHUNK_SIZE
                    && tile.getKey().x < x + GameVariables.CHUNK_SIZE
                    && tile.getKey().y > y - GameVariables.CHUNK_SIZE
                    && tile.getKey().y < y + GameVariables.CHUNK_SIZE) {
                batch.draw(tile.getValue().getTexture(),
                        tile.getKey().x * GameVariables.TILES_SIZE,
                        tile.getKey().y * GameVariables.TILES_SIZE);

            }
        }
    }

}
