package com.jiedro.canels.model.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.physics.box2d.BodyDef;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.jiedro.canels.GameVariables;

/**
 *
 * Created by jiexdrop on 19/06/17.
 */

public abstract class Entity {
    protected Sprite sprite;

    protected float x;
    protected float y;

    protected float velocityX;
    protected float velocityY;

    public Entity(Sprite sprite){
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    }

    public void setOrientation(Orientation orientation){
        switch (orientation){
            case LEFT:
                sprite.setFlip(true,false);
                break;
            case RIGHT:
                sprite.setFlip(false,false);
                break;
        }
    }

    public void setTint(Color color) {
        this.sprite.setColor(color);
    }

    public void move(float velocityX, float velocityY){
        this.velocityX = velocityX;
        this.velocityY = velocityY;
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    public void update() {
        if(velocityX<0){
            this.setOrientation(Orientation.LEFT);
        } else if(velocityX>0) {
            this.setOrientation(Orientation.RIGHT);
        }
        this.x += velocityX;
        this.y += velocityY;
    }
}
