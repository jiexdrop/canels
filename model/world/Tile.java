package com.jiedro.canels.model.world;

import com.badlogic.ashley.core.Entity;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.jiedro.canels.GameVariables;

/**
 *
 * Created by jiexdrop on 15/06/17.
 */

public class Tile extends StaticTiledMapTile {
    public Tile(TextureRegion textureRegion){
        super(textureRegion);
    }
}
