package com.jiedro.canels.model.entity;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.jiedro.canels.GameVariables;

/**
 *
 * Created by jiexdrop on 16/07/17.
 */

public class ItemSlot {
    Item item = new Item();
    int quantity;

    public ItemSlot(){
        quantity = 0;
    }

    public ItemSlot(Item item){
        this.item = item;
        quantity = 1;
    }

    public ItemSlot(Item item, int quantity){
        this.item = item;
        this.quantity = quantity;
    }

    public boolean hasItem(){
        return !item.getName().equals(GameVariables.EMPTY);
    }


    public boolean equals(Item item){
        return this.item.getName().equals(item.getName());
    }

    public void addItem(Item i){
        if(equals(i)){
            quantity++;
        } else {
            this.item = i;
            quantity = 1;
        }
    }

    public void removeItem(){
        if(hasItem()){
            quantity--;
        }
    }

    public String getItemName(){
        return item.getName();
    }

    public Color getItemColor(){
        return item.getColor();
    }

    @Override
    public String toString() {
        if(GameVariables.DEBUG)
            return item.getName() + ":" + quantity;

        return Integer.toString(quantity);
    }
}
