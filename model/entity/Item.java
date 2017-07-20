package com.jiedro.canels.model.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;
import com.jiedro.canels.model.world.World;

/**
 * Created by jiexdrop on 16/07/17.
 */

public class Item extends Entity {
    String description;

    public Item(String name, Vector2 pos, Color color){
        super();
        position = pos.cpy();
        this.color = color.cpy();
        this.name = name;
    }

    @Override
    public void update(World world) {

    }

    @Override
    public boolean toClean() {
        return false;
    }

    @Override
    public boolean isAlive() {
        return false;
    }
}
