package com.jiedro.canels.model.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.view.Tiles;

/**
 *
 * Created by on 23/05/17.
 */

public class Player extends Entity {

    public Player(){
        super(new Sprite(new Texture(GameVariables.PLAYER)));
        super.setTint(Color.FIREBRICK);
        sprite.setPosition(GameVariables.CHUNK_SIZE*GameVariables.CHUNK_SIZE,
                (GameVariables.CHUNK_SIZE*GameVariables.CHUNK_SIZE)/2);
    }

}
