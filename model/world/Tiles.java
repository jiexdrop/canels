package com.jiedro.canels.model.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.jiedro.canels.GameVariables;

/**
 *
 * Created by jiexdrop on 14/06/17.
 */

public class Tiles {
    private static final Tile playerTile = new Tile(new TextureRegion(new Texture(GameVariables.PLAYER_BASE)));
    private static final Tile groundTile = new Tile(new TextureRegion(new Texture(GameVariables.GROUND)));
    private static final Tile wallTile = new Tile(new TextureRegion(new Texture(GameVariables.WALL)));
    private static final Tile doorTile = new Tile(new TextureRegion(new Texture(GameVariables.DOOR)));
    private static final Tile waterTile = new Tile(new TextureRegion(new Texture(GameVariables.WATER)));

    public static StaticTiledMapTile getGroundTile() {
        return groundTile;
    }

    public static StaticTiledMapTile getWallTile() {
        return wallTile;
    }

    public static StaticTiledMapTile getDoorTile() {
        return doorTile;
    }

    public static StaticTiledMapTile getWaterTile() {
        return waterTile;
    }

    public static StaticTiledMapTile getPlayerTile(){
        return playerTile;
    }
}
