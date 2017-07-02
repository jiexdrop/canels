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
        super.getSprite().setColor(Color.FIREBRICK);
        super.bodyDef = new BodyDef();
        super.bodyDef.type = BodyDef.BodyType.DynamicBody;

        bodyDef.position.set(GameVariables.TILES_SIZE,GameVariables.TILES_SIZE/2);
        super.polygonShape = new PolygonShape();
        polygonShape.setAsBox(0.5f,0.5f);
        super.fixtureDef = new FixtureDef();
        super.fixtureDef.shape = polygonShape;
        super.fixtureDef.density = 0.5f;
        super.fixtureDef.friction = 0.4f;
        super.fixtureDef.restitution = 0.6f; // Make it bounce a little bit
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
