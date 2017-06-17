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
    private static final Tile playerTile = new Tile(new TextureRegion(new Texture(GameVariables.PLAYER)),0)
            .addTextureRegion(new TextureRegion(new Texture(GameVariables.PLAYER_CLOTH_HELMET)))
            .addTextureRegion(new TextureRegion(new Texture(GameVariables.PLAYER_CLOTH_CHEST)))
            .addTextureRegion(new TextureRegion(new Texture(GameVariables.PLAYER_CLOTH_PANTS)));

    private static final Tile waterTile = new Tile(new TextureRegion(new Texture(GameVariables.WATER)),1);
    private static final Tile groundTile = new Tile(new TextureRegion(new Texture(GameVariables.GROUND)),2);
    private static final Tile grassTile = new Tile(new TextureRegion(new Texture(GameVariables.GRASS)),3);
    private static final Tile wallTile = new Tile(new TextureRegion(new Texture(GameVariables.WALL)),4);
    private static final Tile doorTile = new Tile(new TextureRegion(new Texture(GameVariables.DOOR)),5);

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

    public static Tile getPlayerTile(){
        return playerTile;
    }

}
