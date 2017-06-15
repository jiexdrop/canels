package com.jiedro.canels.model.entity;

import com.badlogic.gdx.graphics.Texture;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.world.Tiles;

/**
 *
 * Created by jorge on 23/05/17.
 */

public abstract class Weapon extends Entity {
    private WeaponType weaponType;

    public Weapon() {
        super(Tiles.getPlayerTexture());
    }


    public WeaponType getWeaponType() {
        return weaponType;
    }

    public void setWeaponType(WeaponType weaponType) {
        this.weaponType = weaponType;
    }
}
