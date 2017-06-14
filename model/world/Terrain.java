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

/**
 * Created by jorge on 25/05/17.
 */

public class Terrain {
    Texture tiles;
    TiledMap map;
    TiledMapRenderer renderer;
    TiledMapTileLayer[] chunksLayers;
    TextureRegion[][] splicedTiles;

    public Terrain(){
        tiles = new Texture(GameVariables.TERRAIN_TILES);
        chunksLayers = new TiledMapTileLayer[GameVariables.TOTAL_CHUNKS];

        splicedTiles = TextureRegion.split(tiles, GameVariables.TILES_SIZE, GameVariables.TILES_SIZE);
        map = new TiledMap();

        generateLayers();

        renderer = new OrthogonalTiledMapRenderer(map);
    }

    private void generateLayers(){
        MapLayers layers = map.getLayers();


        layers.add(generateChunk(0,0));
        layers.add(generateChunk(16,0));
    }


    private TiledMapTileLayer generateChunk(int x, int y){
        TiledMapTileLayer layer = new TiledMapTileLayer(GameVariables.CHUNK_SIZE+x,
                GameVariables.CHUNK_SIZE+y,
                GameVariables.TILES_SIZE,
                GameVariables.TILES_SIZE);

        for (int i = 0; i < GameVariables.CHUNK_SIZE; i++) {
            for (int j = 0; j < GameVariables.CHUNK_SIZE; j++) {

                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();

                if(SimplexNoise.noise(GameVariables.FREQUENCY*(i+x),GameVariables.FREQUENCY*(j+y))<0) {
                    cell.setTile(new StaticTiledMapTile(splicedTiles[0][1]));
                } else {
                    cell.setTile(new StaticTiledMapTile(splicedTiles[1][0]));
                }
                layer.setCell(i+x, j+y, cell);
            }
        }

        return layer;
    }

    public TiledMapRenderer getRenderer() {
        return renderer;
    }

    public void dispose(){
        tiles.dispose();
        map.dispose();
    }

}
