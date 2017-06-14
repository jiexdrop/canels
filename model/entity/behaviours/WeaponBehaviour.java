package com.jiedro.canels.model.entity.behaviours;

import com.jiedro.canels.model.entity.Behaviour;
import com.jiedro.canels.model.entity.Weapon;
import com.jiedro.canels.model.entity.weapons.Sword;

/**
 * Created by jorge on 23/05/17.
 */

public class WeaponBehaviour implements Behaviour {

    private Weapon weapon = new Sword();

    @Override
    public void behave() {
        System.out.println("Weapon attack: " + weapon.getWeaponType().toString());
    }
}
