package com.jiedro.canels.model.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.math.Vector2;
import com.jiedro.canels.GameVariables;

/**
 *
 * Created by on 23/05/17.
 */

public class Player extends Entity {

    public Player(){
        super(new Sprite(new Texture(GameVariables.PLAYER)));
        super.setTint(new Color(Color.rgb888(1,1,1)));

    }

    @Override
    public void update() {
        GameVariables.PLAYER_POSITION.x = getX();
        GameVariables.PLAYER_POSITION.y = getY();


        if(velocityX<0){
            this.setOrientation(Orientation.LEFT);
        } else if(velocityX>0) {
            this.setOrientation(Orientation.RIGHT);
        }

        this.position.x += velocityX;
        this.position.y += velocityY;

        sprite.setPosition(this.position.x, this.position.y);
    }

}
