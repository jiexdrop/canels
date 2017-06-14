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

        generateLayers(0,0);

        renderer = new OrthogonalTiledMapRenderer(map);
    }

    public void generateLayers(int x, int y){
        MapLayers layers = map.getLayers();
        if(layers.getCount()>0)
            layers.remove(0);
        layers.add(update(x,y));
    }


    private TiledMapTileLayer update(int x, int y){
        TiledMapTileLayer layer = new TiledMapTileLayer(GameVariables.CHUNK_SIZE*2,
                GameVariables.CHUNK_SIZE,
                GameVariables.TILES_SIZE,
                GameVariables.TILES_SIZE);

        for (int i = 0; i < GameVariables.CHUNK_SIZE*2; i++) {
            for (int j = 0; j < GameVariables.CHUNK_SIZE; j++) {

                TiledMapTileLayer.Cell cell = new TiledMapTileLayer.Cell();

                if(SimplexNoise.noise(GameVariables.FREQUENCY*(i+x),GameVariables.FREQUENCY*(j+y))<0) {
                    cell.setTile(new StaticTiledMapTile(splicedTiles[0][1]));
                } else {
                    cell.setTile(new StaticTiledMapTile(splicedTiles[1][0]));
                }
                layer.setCell(i, j, cell);
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
