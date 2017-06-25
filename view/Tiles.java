package com.jiedro.canels.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jiedro.canels.GameVariables;

/**
 *
 * Created by jiexdrop on 14/06/17.
 */

public class Tiles {
    private static final Tile waterTile = new Tile(new Texture(GameVariables.WATER),1);
    private static final Tile groundTile = new Tile(new Texture(GameVariables.GROUND),2);
    private static final Tile grassTile = new Tile(new Texture(GameVariables.GRASS),3);
    private static final Tile wallTile = new Tile(new Texture(GameVariables.WALL),4);
    private static final Tile doorTile = new Tile(new Texture(GameVariables.DOOR),5);

    public static final Tile getWaterTile() {
        return waterTile;
    }

    public static final Tile getGroundTile() {
        return groundTile;
    }

    public static final Tile getGrassTile() {
        return grassTile;
    }

    public static final Tile getWallTile() {
        return wallTile;
    }

    public static final Tile getDoorTile() {
        return doorTile;
    }


}
