package com.jiedro.canels.view;

import com.jiedro.canels.GameVariables;

import java.util.ArrayList;

/**
 * Created by jiexdrop on 20/07/17.
 */

public class GameTiles {

    private static Tile waterTile = new Tile(GameTextures.getTextureRegions()[0][0], "water", false, GameVariables.WATER_PLAIN);
    private static Tile groundTile = new Tile(GameTextures.getTextureRegions()[0][1], "ground", true, GameVariables.GROUND_DESERT);
    private static Tile dirtTile = new Tile(GameTextures.getTextureRegions()[0][1], "dirt", true, GameVariables.GROUND_PLAIN);
    private static Tile rockTile = new Tile(GameTextures.getTextureRegions()[0][1], "rock", true, GameVariables.ROCK_PLAIN);
    private static Tile grassTile = new Tile(GameTextures.getTextureRegions()[0][2], "grass", true, GameVariables.GRASS_PLAIN);
    private static Tile grassRockTile = new Tile(GameTextures.getTextureRegions()[2][0], "grass_rock", true, GameVariables.WATER_PLAIN);
    private static Tile wallTile = new Tile(GameTextures.getTextureRegions()[1][0], "wall", false, GameVariables.WOOD_PLAIN);
    private static Tile doorTile = new Tile(GameTextures.getTextureRegions()[1][1], "door", false,  GameVariables.WOOD_PLAIN);
    private static Tile leavesTile = new Tile(GameTextures.getTextureRegions()[1][2], "leaves", true, GameVariables.GRASS_PLAIN);
    private static Tile herbsTile = new Tile(GameTextures.getTextureRegions()[2][1], "herbs", true,   GameVariables.GRASS_PLAIN);
    private static Tile logTile = new Tile(GameTextures.getTextureRegions()[2][2], "log", false,  GameVariables.WOOD_PLAIN);
    private static Tile cactusTile = new Tile(GameTextures.getTextureRegions()[3][0], "catus", false,  GameVariables.GRASS_PLAIN);


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

    public static Tile getDirtTile() {
        return dirtTile;
    }

    public static Tile getRockTile() {
        return rockTile;
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

}
