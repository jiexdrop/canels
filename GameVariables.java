package com.jiedro.canels;

import com.badlogic.gdx.graphics.Color;
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

    public static final int SLIME_FRAMES = 3;

    public static final int SLIME_ID = 0;

    public static final int BREAK_ID = 5;

    public static final int BREAK_FRAMES = 6;

    public static final Color SLIME_COLOR = new Color(0x35b72000);

    //PLAYER
    public static final String PLAYER = PLAYER_FOLDER + "player.png";

    public static final String PLAYER_CLOTH_HELMET = PLAYER_HELMET_FOLDER + "cloth.png";

    public static final String PLAYER_CLOTH_CHEST = PLAYER_CHEST_FOLDER + "cloth.png";

    public static final String PLAYER_CLOTH_PANTS = PLAYER_PANTS_FOLDER + "cloth.png";

    public static final Color PLAYER_SKIN_PLAINS = new Color(0xfaa87aff);

    public static final Color PLAYER_SKIN_MOUNTAINS = new Color(0xfab87aff);

    public static final Color PLAYER_SKIN_DESERT = new Color(0x6d4128ff);

    public static final int PLAYER_FRAMES = 3;

    public static final int PLAYER_ID = 0;

    //CONSTANTS
    public static final int ZOOM_LEVEL = 256;

    public static final float ZOOM_INPUT = 0.1f;

    public static final double TERRAIN_FREQUENCY = 0.01d;//0.01d

    public static final double TREES_FREQUENCY = 1d;//1d

    public static final int TILES_SIZE = 16;

    public static final int CHUNK_SIZE = TILES_SIZE;

    public static final int PLAYER_SPEED = 80;

    public static final int SLIMES_SPEED = 10;

    public static final int ENTITIES_SPEED = 10;

    public static final int NORMAL_SPEED = 100;

    public static final int TILEMAP_CENTER = (GameVariables.CHUNK_SIZE*GameVariables.CHUNK_SIZE)/2;

    public static final int ENEMIES_PAR_LEVEL = 10;

    public static final int ENEMIES_SPACING = 256;

    public static final int NORMAL_SIZE = 16;

    public static final int ENEMY_SIZE = 16;

    public static final int ITEM_SIZE = TILES_SIZE/4;

    public static final int CURSOR_SIZE = 16;

    public static final float TIME_STEP = 1/60f;

    //DESERT COLORS
    public static final Color CACTUS_DESERT = new Color(0x35b720ff);

    public static final Color GROUND_DESERT = new Color(0xe5e47cff);

    //PLAIN COLORS
    public static final Color WATER_PLAIN = new Color(0x35b7c1ff);

    public static final Color GRASS_PLAIN = new Color(0x35b720ff);

    public static final Color TREE_PLAIN = new Color(0x35b720ff);

    public static final Color WOOD_PLAIN = new Color(0x635230ff);

    public static final Color GROUND_PLAIN = new Color(0x5a553bff);

    public static final Color ROCK_PLAIN = new Color(0xa9a9a9ff);

    public static final Color DIRT_PLAIN = new Color(0x5a553bff);

    //USER INTERFACE
    public static final String MAIN_UI = USER_INTERFACE_FOLDER + "main_ui.png";

    public static final int UI_TEXTURE_SIZE = 16;

    public static final int TOUCH_PAD_SIZE = 192;

    public static final int UI_OFFSET = 16;

    public static int SIDEBAR_WIDTH = UI_TEXTURE_SIZE*6;

    public static int SIDEBAR_BUTTON_HEIGHT = UI_TEXTURE_SIZE*6;

    public static int ENTITIES = 0;

    public static int RENDER_CALLS = 0;

    public static int HOLD_TIME = 0;

    public static int PLAYER_HEALTH = 0;

    public static final int PLAYER_ITEM_SLOTS = 16;

    public static final int VISIBLE_PLAYER_ITEM_SLOTS = PLAYER_ITEM_SLOTS/4;

    public static String EMPTY = "EMPTY";

    public static String CLEAR = "";

    public static Vector2 PLAYER_POSITION = new Vector2(0,0);

    //DEBUG
    public static boolean DEBUG = true;

}