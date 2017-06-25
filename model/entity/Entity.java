package com.jiedro.canels.model.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.jiedro.canels.GameVariables;

/**
 *
 * Created by jiexdrop on 19/06/17.
 */

public abstract class Entity {
    protected Sprite sprite;

    protected int mapX = 0;
    protected int mapY = 0;

    protected float x = 0;
    protected float y = 0;

    protected double xVelocity = 0;
    protected double yVelocity = 0;

    public Entity(Sprite sprite){
        this.sprite = sprite;
    }

    public Sprite getSprite() {
        return sprite;
    };

    public void setTint(Color color) {
        this.sprite.setColor(color);
    }

    public float getX() {
        return x;
    }

    public float getY() {
        return y;
    }

    /**
     *
     * @return the
     */
    public int getScreenX(){
        return (int)this.getSprite().getX()/ GameVariables.TILES_SIZE;
    }

    /**
     *
     * @return the
     */
    public int getScreenY(){
        return (int)this.getSprite().getY()/ GameVariables.TILES_SIZE;
    }

    public int getMapX() {
        return mapX;
    }

    public int getMapY() {
        return mapY;
    }

    public abstract void move(double xVelocity, double yVelocity);

    public abstract void update();
}
