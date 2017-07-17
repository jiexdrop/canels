package com.jiedro.canels.model.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;

/**
 *
 * Created by jiexdrop on 19/06/17.
 */

public abstract class Entity {

    Vector2 velocity;
    Vector2 position;

    Color color;

    Orientation orientation;

    //RPG
    int health = 0;


    public Entity(){
        this.position = new Vector2(0,0);
        this.velocity = new Vector2(0,0);
        this.orientation = Orientation.STILL;
    }


    public Orientation getOrientation() {
        return orientation;
    }

    public void setOrientation(Orientation orientation){
        this.orientation = orientation;

    }

    public void move(float velocityX, float velocityY){
        this.velocity.x = velocityX;
        this.velocity.y = velocityY;
    }

    public float getX() {
        return position.x;
    }

    public float getY() {
        return position.y;
    }


    public void update() {
        if(velocity.x>0){
            setOrientation(Orientation.RIGHT);
        } else if(velocity.x<0) {
            setOrientation(Orientation.LEFT);
        } else {
            setOrientation(Orientation.STILL);
        }
    }

    public Color getColor() {
        return color;
    }
}
