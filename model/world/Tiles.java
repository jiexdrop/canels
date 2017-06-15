package com.jiedro.canels.model.world;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.jiedro.canels.GameVariables;

/**
 *
 * Created by jiexdrop on 14/06/17.
 */

public class Tiles {
    private static final Texture playerTexture = new Texture(GameVariables.PLAYER_BASE);
    private static final TextureRegion playerTextureRegion = new TextureRegion(playerTexture);

    public static TextureRegion getPlayerTexture(){
        return playerTextureRegion;
    }
}
