package com.jiedro.canels;

import com.badlogic.gdx.math.Vector2;

/**
 *
 * Created by jorge on 25/05/17.
 */

public class GameVariables {

    //FOLDERS
    private static final String TEXTURES_FOLDER = "textures/";

    private static final String USER_INTERFACE_FOLDER = "ui/";

    private static final String TERRAIN_FOLDER = TEXTURES_FOLDER + "terrain/";

    private static final String ENTITIES_FOLDER = TEXTURES_FOLDER + "entities/";

    private static final String PLAYER_FOLDER = ENTITIES_FOLDER + "player/";

    private static final String ENEMIES_FOLDER = ENTITIES_FOLDER + "enemies/";

    private static final String PLAYER_CHEST_FOLDER = PLAYER_FOLDER + "chest/";

    private static final String PLAYER_HELMET_FOLDER = PLAYER_FOLDER + "helmet/";

    private static final String PLAYER_PANTS_FOLDER = PLAYER_FOLDER + "pants/";

    //TERRAIN
    public static final String MAIN_TEXTURES = TERRAIN_FOLDER + "main_textures.png";

    //ENEMIES
    public static final String MAIN_ENEMIES = ENEMIES_FOLDER + "main_enemies.png";

    //PLAYER
    public static final String PLAYER = PLAYER_FOLDER + "player.png";

    public static final String PLAYER_CLOTH_HELMET = PLAYER_HELMET_FOLDER + "cloth.png";

    public static final String PLAYER_CLOTH_CHEST = PLAYER_CHEST_FOLDER + "cloth.png";

    public static final String PLAYER_CLOTH_PANTS = PLAYER_PANTS_FOLDER + "cloth.png";

    //USER INTERFACE
    public static final String TOUCH_PAD_KNOB = USER_INTERFACE_FOLDER + "touch_pad_knob.png";

    public static final String TOUCH_PAD_BACKGROUND = USER_INTERFACE_FOLDER + "touch_pad_background.png";

    public static final int TOUCH_PAD_SIZE = 200;

    public static final int TOUCH_PAD_OFFSET_X = 10;

    public static final int TOUCH_PAD_OFFSET_Y = 10;

    //CONSTANTS
    public static int TEXTURES_NUMBER = 256;

    public static int ZOOM_LEVEL = 256;

    public static float ZOOM_INPUT = 0.1f;

    public static double FREQUENCY = 0.01d;//0.01d

    public static int TILES_SIZE = 16;

    public static int CHUNK_SIZE = TILES_SIZE;

    public static final float PLAYER_SPEED = 100;

    public static final float ENTITIES_SPEED = 100;

    public static final int TILEMAP_CENTER = (GameVariables.CHUNK_SIZE*GameVariables.CHUNK_SIZE)/2;

    public static final int ENEMIES_PAR_LEVEL = 10;

    public static final int ENEMIES_SPACING = 256;

    public static final float TIME_STEP = 1/60f;


    //THIS IS FOR GUI ... TO REVIEW
    public static int ENTITIES = 0;

    public static Vector2 PLAYER_POSITION = new Vector2(0,0);

}