package com.jiedro.canels.model.entity;

import com.badlogic.gdx.math.Vector2;

/**
 *
 * Created by jiexdrop on 16/07/17.
 */

class ItemSlot {
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
        return item!=null;
    }


    public boolean equals(Item item){
        return this.item.getName().equals(item.getName());
    }

    public void addItem(Item i){
        if(hasItem()){
            quantity++;
        }else {
            this.item = i;
            quantity = 1;
        }
    }

    public void removeItem(){
        if(hasItem()){
            quantity--;
        }
    }
}
