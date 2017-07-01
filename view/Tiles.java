package com.jiedro.canels.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jiedro.canels.GameVariables;

/**
 *
 * Created by jiexdrop on 14/06/17.
 */

public class Tiles {
    private static final Texture mainTextures = new Texture(GameVariables.MAIN_TEXTURES);
    private static final TextureRegion[][] textureRegions = TextureRegion.split(mainTextures,
            GameVariables.TILES_SIZE, GameVariables.TILES_SIZE);


    private static Tile waterTile = new Tile(textureRegions[1][0],1, false);
    private static Tile groundTile = new Tile(textureRegions[1][0],2);
    private static Tile grassTile = new Tile(textureRegions[1][0],3);
    private static Tile wallTile = new Tile(textureRegions[1][0], 4, false);
    private static Tile doorTile = new Tile(textureRegions[1][0],5, false);

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
