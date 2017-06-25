package com.jiedro.canels;

/**
 *
 * Created by jorge on 25/05/17.
 */

public class GameVariables {

    //FOLDERS
    private static final String TERRAIN_FOLDER = "textures/terrain/";

    private static final String PLAYER_FOLDER = "textures/player/";

    private static final String PLAYER_CHEST_FOLDER = PLAYER_FOLDER + "chest/";

    private static final String PLAYER_HELMET_FOLDER = PLAYER_FOLDER + "helmet/";

    private static final String PLAYER_PANTS_FOLDER = PLAYER_FOLDER + "pants/";

    //TERRAIN_FOLDER
    public static final String WATER = TERRAIN_FOLDER + "water.png";

    public static final String GROUND = TERRAIN_FOLDER + "ground.png";

    public static final String GRASS = TERRAIN_FOLDER + "grass.png";

    public static final String WALL = TERRAIN_FOLDER + "wall.png";

    public static final String DOOR = TERRAIN_FOLDER + "door.png";

    //PLAYER
    public static final String PLAYER = PLAYER_FOLDER + "player.png";

    public static final String PLAYER_CLOTH_HELMET = PLAYER_HELMET_FOLDER + "cloth.png";

    public static final String PLAYER_CLOTH_CHEST = PLAYER_CHEST_FOLDER + "cloth.png";

    public static final String PLAYER_CLOTH_PANTS = PLAYER_PANTS_FOLDER + "cloth.png";

    //CONSTANTS
    public static int ZOOM_LEVEL = 256;

    public static double FREQUENCY = 0.01d;//0.01d

    public static int TILES_SIZE = 16;

    public static int TOTAL_CHUNKS = 9;

    public static int CHUNK_SIZE = 16;

    public static final double PLAYER_SPEED = 1;

}