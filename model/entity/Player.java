package com.jiedro.canels.model.entity;

import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.world.World;

import java.util.ArrayList;

/**
 *
 * Created by on 23/05/17.
 */

public class Player extends Living {

    ArrayList<ItemSlot> itemSlots = new ArrayList<ItemSlot>();

    public Player(){
        name = "player";
        health = 3;
        color = GameVariables.PLAYER_SKIN_MOUNTAINS;

        for (int i = 0; i < GameVariables.PLAYER_ITEM_SLOTS; i++){
            itemSlots.add(new ItemSlot());
        }
    }

    @Override
    public void update(World world) {
        elapsedTime += world.deltaTime;

        if(velocity.x>0){
            setOrientation(Orientation.RIGHT);
        } else if(velocity.x<0) {
            setOrientation(Orientation.LEFT);
        } else {
            setOrientation(Orientation.STILL);
        }

        position.add(velocity);

        updateGameVariables();
    }

    @Override
    public void hit(Living e) {
        e.health--;
    }

    void updateGameVariables(){

        GameVariables.PLAYER_POSITION.x = getX();
        GameVariables.PLAYER_POSITION.y = getY();

        GameVariables.PLAYER_HEALTH = health;
    }

    public ArrayList<ItemSlot> getItemSlots() {
        return itemSlots;
    }

    public void addItem(Item i) {
        ItemSlot is = nextSlot(i);
        if(is!=null){
            is.addItem(i);
        }
    }

    private ItemSlot nextSlot(Item i){
        for (ItemSlot is:itemSlots) {
            if(is.equals(i)){
                return is;
            }
        }
        for (ItemSlot is:itemSlots) {
            if(!is.hasItem()){
                return is;
            }
        }
       return null;
    }
}
