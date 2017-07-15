package com.jiedro.canels.model.entity;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.view.Textures;
import com.jiedro.canels.view.Tile;

/**
 *
 * Created by on 23/05/17.
 */

public class Player extends Entity {


    public Player(){
        super(Textures.getPlayerTexture(), 3, 3);

        color = GameVariables.PLAYER_SKIN_MOUNTAINS;
    }

    @Override
    public void update() {
        elapsedTime += Gdx.graphics.getDeltaTime();
        currentFrame = walkAnimation.getKeyFrame(elapsedTime,true);

        if(velocityX<0){
            this.setOrientation(Orientation.LEFT);
        } else if(velocityX>0) {
            this.setOrientation(Orientation.RIGHT);
        } else {
            this.setOrientation(Orientation.STILL);
        }

        this.position.x += velocityX;
        this.position.y += velocityY;

        updateGameVariables();
    }

    void updateGameVariables(){

        GameVariables.PLAYER_POSITION.x = getX();
        GameVariables.PLAYER_POSITION.y = getY();

        GameVariables.PLAYER_HEALTH = health;
    }

}
