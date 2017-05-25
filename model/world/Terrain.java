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


    public Terrain(){
        tiles = new Texture(GameVariables.TERRAIN_TILES);

        TextureRegion[][] splicedTiles = TextureRegion.split(tiles, GameVariables.TILES_SIZE, GameVariables.TILES_SIZE);
        map = new TiledMap();


        MapLayers layers = map.getLayers();
        TiledMapTileLayer layer = new TiledMapTileLayer(GameVariables.CHUNK_SIZE, GameVariables.CHUNK_SIZE, GameVariables.TILES_SIZE, GameVariables.TILES_SIZE);
        for (int x = 0; x < GameVariables.CHUNK_SIZE; x++) {
            for (int y = 0; y < GameVariables.CHUNK_SIZE; y++) {

                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();

                if(SimplexNoise.noise(GameVariables.FREQUENCY*x,GameVariables.FREQUENCY*y)<0) {
                    cell.setTile(new StaticTiledMapTile(splicedTiles[0][1]));
                } else {
                    cell.setTile(new StaticTiledMapTile(splicedTiles[0][0]));
                }
                layer.setCell(x, y, cell);
            }
        }

        layers.add(layer);

        renderer = new OrthogonalTiledMapRenderer(map);
    }

    private boolean generateChunk(int x, int y){
        return false;
    }

    public TiledMapRenderer getRenderer() {
        return renderer;
    }

    public void dispose(){
        tiles.dispose();
        map.dispose();
    }

}
