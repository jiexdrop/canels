package com.jiedro.canels.model.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.view.Tiles;

/**
 *
 * Created by on 23/05/17.
 */

public class Player extends Entity {

    public Player(){
        super(new Sprite(new Texture(GameVariables.PLAYER)));
        super.getSprite().setColor(Color.FIREBRICK);
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

        this.mapX = (int) this.x/GameVariables.TILES_SIZE;
        this.mapY = (int) this.y/GameVariables.TILES_SIZE;
    }
}
