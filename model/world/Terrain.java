package com.jiedro.canels.model.world;

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

    public void placeTile(int x, int y, Tile tile){
        terrain.put(new Vector2(x ,y), tile);
    }

    public boolean canMove(int x, int y){
        if(terrain.get(new Vector2(x,y))!=Tiles.getWaterTile()){
            return true;
        }
        return false;
    }

    public void updateWorld(int x, int y){

        for (int i = 0; i < GameVariables.CHUNK_SIZE*2; i++) {
            for (int j = 0; j < GameVariables.CHUNK_SIZE*2; j++) {

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

    public void draw(Batch batch, Player player) {
        for (Map.Entry<Vector2, Tile> tile:terrain.entrySet()) {
            if(tile.getKey().x>player.getMapX()-GameVariables.CHUNK_SIZE
                    && tile.getKey().x<player.getMapX()+GameVariables.CHUNK_SIZE
                    && tile.getKey().y>player.getMapY()-GameVariables.CHUNK_SIZE
                    && tile.getKey().y<player.getMapY()+GameVariables.CHUNK_SIZE) {
                batch.draw(tile.getValue().getTexture(),
                        tile.getKey().x * GameVariables.TILES_SIZE,
                        tile.getKey().y * GameVariables.TILES_SIZE);
            }
        }
    }


}
