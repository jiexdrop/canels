package com.jiedro.canels.model.entity.weapons;

import com.jiedro.canels.model.entity.Weapon;
import com.jiedro.canels.model.entity.WeaponType;

/**
 * Created by jorge on 23/05/17.
 */

public class Sword extends Weapon {
    public Sword(){
        setWeaponType(WeaponType.NEAR);
    }
}
