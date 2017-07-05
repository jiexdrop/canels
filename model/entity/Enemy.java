package com.jiedro.canels.model.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.view.Textures;

/**
 *
 * Created by jiexdrop on 04/07/17.
 */

public class Enemy extends Entity {
    public Enemy(float x, float y) {
        super(new Sprite(Textures.getSlimeSprite()));
        super.setTint(new Color(Color.argb8888(255, 178,106,70)));
        sprite.setPosition(x,y);
        this.x =x;
        this.y = y;
    }
}
