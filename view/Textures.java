package com.jiedro.canels.view;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jiedro.canels.GameVariables;

/**
 *
 * Created by jiexdrop on 14/06/17.
 */

public class Textures {
    private static final Texture mainTextures = new Texture(GameVariables.MAIN_TEXTURES);
    private static final TextureRegion[][] textureRegions = TextureRegion.split(mainTextures,
            GameVariables.TILES_SIZE, GameVariables.TILES_SIZE);

    private static Texture playerTexture = new Texture(GameVariables.PLAYER);
    private static final TextureRegion[][] playerTextureRegions = TextureRegion.split(playerTexture,
            playerTexture.getWidth()/3, playerTexture.getHeight());

    private static final Texture mainEnemiesTextures = new Texture(GameVariables.MAIN_ENEMIES);
    private static final TextureRegion[][] enemiesTextureRegions = TextureRegion.split(mainEnemiesTextures,
            GameVariables.TILES_SIZE, GameVariables.TILES_SIZE);

    private static Tile waterTile = new Tile(textureRegions[0][0],1, false, GameVariables.WATER_PLAIN);
    private static Tile groundTile = new Tile(textureRegions[0][1],2, true, GameVariables.GROUND_DESERT);
    private static Tile grassTile = new Tile(textureRegions[0][2],3, true, GameVariables.GRASS_PLAIN);
    private static Tile grassRockTile = new Tile(textureRegions[2][0],3, true, GameVariables.WATER_PLAIN);
    private static Tile wallTile = new Tile(textureRegions[1][0], 4, false, GameVariables.WOOD_PLAIN);
    private static Tile doorTile = new Tile(textureRegions[1][1],5, false,  GameVariables.WOOD_PLAIN);
    private static Tile leavesTile = new Tile(textureRegions[1][2],5, true, GameVariables.GRASS_PLAIN);
    private static Tile herbsTile = new Tile(textureRegions[2][1],5, true,   GameVariables.GRASS_PLAIN);
    private static Tile logTile = new Tile(textureRegions[2][2],5, false,  GameVariables.WOOD_PLAIN);
    private static Tile cactusTile = new Tile(textureRegions[3][0],5, false,  GameVariables.GRASS_PLAIN);

    public static Tile getWaterTile() {
        return waterTile;
    }

    public static Tile getGroundTile() {
        return groundTile;
    }

    public static Tile getGrassTile() {
        return grassTile;
    }

    public static Tile getWallTile() {
        return wallTile;
    }

    public static Tile getDoorTile() {
        return doorTile;
    }

    public static Tile getLeavesTile() {
        return leavesTile;
    }

    public static Tile getLogTile() {
        return logTile;
    }

    public static Tile getCactusTile() {
        return cactusTile;
    }

    public static Tile getGrassRockTile() {
        return grassRockTile;
    }

    public static Tile getHerbsTile() {
        return herbsTile;
    }

    public static TextureRegion[][] getPlayerTexture() {
        return playerTextureRegions;
    }

    public static TextureRegion[][] getEnemiesTextures() {
        return enemiesTextureRegions;
    }


}
