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

    protected double x = 0;
    protected double y = 0;

    protected double xVelocity = 0;
    protected double yVelocity = 0;

    protected BodyDef bodyDef = new BodyDef();
    protected FixtureDef fixtureDef = new FixtureDef();
    protected PolygonShape polygonShape = new PolygonShape();

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

    public BodyDef getBodyDef() {
        return bodyDef;
    }

    public FixtureDef getFixtureDef() {
        return fixtureDef;
    }
}
