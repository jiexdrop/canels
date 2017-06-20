package com.jiedro.canels.model.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.view.Tiles;

/**
 *
 * Created by jorge on 23/05/17.
 */

public class Player extends Entity {

    public Player(){
        super(new Sprite(new Texture(GameVariables.PLAYER)));
    }

    @Override
    public void move(double xVelocity, double yVelocity){
        this.xVelocity = xVelocity;
        this.yVelocity = yVelocity;
    }

    @Override
    public void update() {
        this.x += xVelocity;
        this.y += yVelocity;
    }
}
