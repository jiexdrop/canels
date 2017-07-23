package com.jiedro.canels.view;

import com.badlogic.gdx.graphics.Color;
import com.jiedro.canels.GameVariables;

/**
 *
 * Created by jiexdrop on 20/07/17.
 */

public enum TileType {
    WATER("water", false, 100, GameVariables.WATER_PLAIN),
    GROUND("ground", true, 100, GameVariables.GROUND_DESERT),
    DIRT("dirt", true, 100, GameVariables.DIRT_PLAIN),
    GRASS("grass", true, 100, GameVariables.GRASS_PLAIN),
    LOG("log",true, 100, GameVariables.WOOD_PLAIN),
    DOOR("door", true, 100, GameVariables.WOOD_PLAIN),
    WALL("wall",false, 100, GameVariables.WOOD_PLAIN),
    LEAVES("leaves", true, 100, GameVariables.GRASS_PLAIN),
    CACTUS("cactus",true, 100, GameVariables.CACTUS_DESERT),
    ROCK("rock",true, 100, GameVariables.GROUND_DESERT),
    EMPTY("empty",false, -1, Color.CLEAR);

    private final String name;

    public final boolean walkable;
    public final int resistance;
    public final Color color;

    TileType(String name, boolean walkable, int resistance, Color color) {
        this.name = name;
        this.walkable = walkable;
        this.resistance = resistance;
        this.color = color;
    }


}
