package com.jiedro.canels.model.world;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.entity.Player;
import com.jiedro.canels.view.Tile;

/**
 * Has all entities
 * Created by jiexdrop on 03/07/17.
 */

public class World {
    private Player player;

    private Terrain terrain;


    public World() {
        player = new Player();
        terrain = new Terrain();
    }

    public void movePlayer(float knobPercentX, float knobPercentY) {
        float dt = Gdx.graphics.getDeltaTime();

        if (terrain.canMove(player.getX() + (knobPercentX * (dt * GameVariables.PLAYER_SPEED)),
                player.getY() + (knobPercentY * (dt * GameVariables.PLAYER_SPEED)))){
            player.move(knobPercentX * (dt * GameVariables.PLAYER_SPEED),
                    knobPercentY * (dt * GameVariables.PLAYER_SPEED));

            terrain.updateWorld(player.getX() - GameVariables.TILEMAP_CENTER,
                    player.getY() - GameVariables.TILEMAP_CENTER);
        } else {
            player.move(0,0);
        }

    }

    public void drawTilemap(SpriteBatch tilemapBatch, OrthographicCamera tilemapCamera) {
        tilemapCamera.position.set(player.getX(),
                player.getY(), 0.f);
        terrain.draw(tilemapBatch,
                player.getX() - GameVariables.TILEMAP_CENTER,
                player.getY() - GameVariables.TILEMAP_CENTER);
    }

    public void drawEntities(SpriteBatch entitiesBatch, OrthographicCamera entitiesCamera) {
        player.getSprite().draw(entitiesBatch);
    }

    public void placeTile(float x, float y, Tile groundTile) {
        terrain.placeTile(x,y,groundTile);
    }

    /**
     * Can update the model from here
     */
    public void update() {
        player.update();
    }
}
