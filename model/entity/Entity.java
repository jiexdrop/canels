package com.jiedro.canels.model.entity;

import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.jiedro.canels.GameVariables;

/**
 * Created by jorge on 23/05/17.
 */

abstract class Entity extends StaticTiledMapTile {
    private String name;
    private String description;

    public Entity(TextureRegion textureRegion){
        super(textureRegion);
    }

}
