package com.jiedro.canels.view;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jiedro.canels.GameVariables;

/**
 *
 * Created by jiexdrop on 14/06/17.
 */

public class GameTextures {
    private static final Texture mainTextures = new Texture(GameVariables.MAIN_TEXTURES);
    private static final TextureRegion[][] textureRegions = TextureRegion.split(mainTextures,
            GameVariables.TILES_SIZE, GameVariables.TILES_SIZE);

    private static final Texture playerTexture = new Texture(GameVariables.PLAYER);
    private static final TextureRegion[][] playerTextureRegions = TextureRegion.split(playerTexture,
            playerTexture.getWidth()/GameVariables.PLAYER_FRAMES, playerTexture.getHeight());

    private static final Texture mainEnemiesTextures = new Texture(GameVariables.MAIN_ENEMIES);
    private static final TextureRegion[][] enemiesTextureRegions = TextureRegion.split(mainEnemiesTextures,
            GameVariables.TILES_SIZE, GameVariables.TILES_SIZE);

    private static final Texture mainUITextures = new Texture(GameVariables.MAIN_UI);
    private static final TextureRegion[][] mainUITextureRegions = TextureRegion.split(mainUITextures,
            GameVariables.UI_TEXTURE_SIZE, GameVariables.UI_TEXTURE_SIZE);


    static TextureRegion[][] getPlayerTexture() {
        return playerTextureRegions;
    }

    static TextureRegion[][] getSlimesTextures() {
        return enemiesTextureRegions;
    }

    static TextureRegion getTouchPadBackgroundTexture() {
        return mainUITextureRegions[0][0];
    }

    static TextureRegion getTouchPadKnobTexture() {
        return mainUITextureRegions[0][1];
    }

    static TextureRegion getSlotTexture() {
        return mainUITextureRegions[1][1];
    }

    static TextureRegion getSlotOverlayTexture() {
        return mainUITextureRegions[0][2];
    }

    public static TextureRegion getTextureRegion(TileType tileType){
        switch (tileType){
            case WATER:
                return textureRegions[0][0];
            case GRASS:
                return textureRegions[0][1];
            case GROUND:
                return textureRegions[0][2];
            case WALL:
                return textureRegions[1][0];
            case LEAVES:
                return textureRegions[1][2];
            case ROCK:
                return textureRegions[2][0];
            case LOG:
                return textureRegions[2][2];
            case DOOR:
                return textureRegions[1][1];
            case CACTUS:
                return textureRegions[3][0];

            default:
                return textureRegions[0][0];
        }
    }
}
