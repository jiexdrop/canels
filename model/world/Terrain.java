package com.jiedro.canels.model.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.MapLayers;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapRenderer;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.entity.Player;

/**
 *
 * Created by jorge on 25/05/17.
 */

public class Terrain {
    private TiledMap map;
    private TiledMapRenderer renderer;

    public Terrain(){
        map = new TiledMap();

        renderer = new OrthogonalTiledMapRenderer(map);
    }

    public void generateLayers(Player player){
        clearLayers();
        MapLayers layers = map.getLayers();
        layers.add(updateWorld(player.getMapX(), player.getMapY()));
        layers.add(placePlayer(player));
    }

    private TiledMapTileLayer placePlayer(Player player){
        TiledMapTileLayer layer = new TiledMapTileLayer(GameVariables.CHUNK_SIZE,
                GameVariables.CHUNK_SIZE,
                GameVariables.TILES_SIZE,
                GameVariables.TILES_SIZE);
        TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();
        cell.setTile(player);
        layer.setCell(GameVariables.CHUNK_SIZE-2, GameVariables.CHUNK_SIZE/2, cell);

        return layer;
    }

    private TiledMapTileLayer updateWorld(int x, int y){
        TiledMapTileLayer layer = new TiledMapTileLayer(GameVariables.CHUNK_SIZE*2,
                GameVariables.CHUNK_SIZE,
                GameVariables.TILES_SIZE,
                GameVariables.TILES_SIZE);

        for (int i = 0; i < GameVariables.CHUNK_SIZE*2; i++) {
            for (int j = 0; j < GameVariables.CHUNK_SIZE; j++) {

                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();

                layer.setCell(i, j, cell);
                if(SimplexNoise.noise(GameVariables.FREQUENCY*(i+x),GameVariables.FREQUENCY*(j+y))<0) {
                    cell.setTile(Tiles.getWaterTile());
                } else {
                    cell.setTile(Tiles.getGroundTile());
                }
            }
        }

        return layer;
    }

    private void clearLayers(){
        MapLayers layers = map.getLayers();

        for (int i = 0; i<layers.getCount(); i++){
            layers.remove(i);
        }
    }

    public TiledMapRenderer getRenderer() {
        return renderer;
    }

    public void dispose(){
        map.dispose();
    }

}
