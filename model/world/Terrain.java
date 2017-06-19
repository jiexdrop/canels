package com.jiedro.canels.model.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
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
    private TiledMap map;
    private TiledMapRenderer renderer;

    private TiledMapTileLayer entities = new TiledMapTileLayer(GameVariables.CHUNK_SIZE,
            GameVariables.CHUNK_SIZE,
            GameVariables.TILES_SIZE,
            GameVariables.TILES_SIZE);
    private TiledMapTileLayer ground = new TiledMapTileLayer(GameVariables.CHUNK_SIZE*2,
            GameVariables.CHUNK_SIZE,
            GameVariables.TILES_SIZE,
            GameVariables.TILES_SIZE);


    private Map<Vector2, com.jiedro.canels.view.Tile> terrain = new HashMap<Vector2, com.jiedro.canels.view.Tile>();

    private Map<Vector2, com.jiedro.canels.view.Tile> terrainEntities = new HashMap<Vector2, com.jiedro.canels.view.Tile>();

    public Terrain(){
        map = new TiledMap();
        MapLayers layers = map.getLayers();
        layers.add(ground);
        layers.add(entities);



        renderer = new OrthogonalTiledMapRenderer(map);
    }

    public Tile generateStructure(int x, int y){
        if(Math.random()>0.99) {
            terrain.put(new Vector2(x, y), Tiles.getWallTile());
            return Tiles.getWallTile();
        }
        return Tiles.getGrassTile();
    }

    public void update(Player player){
        updateWorld((int)player.getMapX(), (int)player.getMapY());
    }

    public void placeTile(int x, int y, int playerX, int playerY, Tile tile){
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(tile);
        ground.setCell(x,y,cell);

        terrain.put(new Vector2(x + playerX ,y + playerY), tile);
    }

    private void updateWorld(int x, int y){


        for (int i = 0; i < GameVariables.CHUNK_SIZE*2; i++) {
            for (int j = 0; j < GameVariables.CHUNK_SIZE; j++) {

                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();


                ground.setCell(i, j, cell);

                if(!terrain.containsKey(new Vector2(i+x,j+y))) {

                    if (SimplexNoise.noise(GameVariables.FREQUENCY * (i + x), GameVariables.FREQUENCY * (j + y)) < 0) {
                        cell.setTile(Tiles.getWaterTile());
                        terrain.put(new Vector2(i + x, j + y), Tiles.getWaterTile());
                    } else if ((SimplexNoise.noise(GameVariables.FREQUENCY * (i + x), GameVariables.FREQUENCY * (j + y)) > 0.35)
                            && (SimplexNoise.noise(GameVariables.FREQUENCY * (i + x), GameVariables.FREQUENCY * (j + y)) < 1)) {
                        Tile tile = generateStructure(i+x, j+y);
                        cell.setTile(tile);
                        terrain.put(new Vector2(i + x, j + y), tile);

                    } else {
                        cell.setTile(Tiles.getGroundTile());
                        terrain.put(new Vector2(i + x, j + y), Tiles.getGroundTile());
                    }


                } else {
                    cell.setTile(terrain.get(new Vector2(i + x, j + y)));
                }

            }
        }
    }

    public TiledMapRenderer getRenderer() {
        return renderer;
    }

    public void dispose(){
        map.dispose();
    }

}
