package com.jiedro.canels.model.entity;

import com.badlogic.gdx.maps.tiled.TiledMapTile;
import com.badlogic.gdx.maps.tiled.tiles.StaticTiledMapTile;
import com.jiedro.canels.model.world.Tiles;

/**
 *
 * Created by jorge on 23/05/17.
 */

public class Player extends StaticTiledMapTile {
    private int x = 0;
    private int y = 0;

    public Player(){
        super(Tiles.getPlayerTile());
    }

    public void move(Direction direction){
        switch (direction){
            case UP:
                y++;
                break;
            case DOWN:
                y--;
                break;
            case LEFT:
                x--;
                break;
            case RIGHT:
                x++;
                break;
        }
    }

    public int getMapX() {
        return x;
    }

    public int getMapY() {
        return y;
    }
}
