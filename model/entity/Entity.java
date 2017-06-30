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

    protected double x = 0;
    protected double y = 0;

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

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }


    public abstract void move(double xVelocity, double yVelocity);

    public abstract void update();
}
