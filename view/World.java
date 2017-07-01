package com.jiedro.canels.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.entity.Player;
import com.jiedro.canels.model.world.Terrain;

/**
 *
 * Created by jiexdrop on 14/06/17.
 */

public class World {
    private Player player;

    private Terrain terrain;

    private OrthographicCamera entitiesCamera;

    private OrthographicCamera tilemapCamera;

    private SpriteBatch tilemapBatch;

    private SpriteBatch entitiesBatch;

    private UserInterface userInterface;

    public int totalRenderCalls = 0;

    public World(){
        player = new Player();
        terrain = new Terrain();
        entitiesBatch = new SpriteBatch();
        tilemapBatch = new SpriteBatch();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        tilemapCamera = new OrthographicCamera();
        tilemapCamera.setToOrtho(false, (w / h) * GameVariables.ZOOM_LEVEL, GameVariables.ZOOM_LEVEL);
        tilemapCamera.update();

        entitiesCamera = new OrthographicCamera();
        entitiesCamera.setToOrtho(false, (w / h) * GameVariables.ZOOM_LEVEL, GameVariables.ZOOM_LEVEL);
        entitiesCamera.update();

        player.getSprite().setPosition(tilemapCamera.viewportWidth/2 - GameVariables.TILES_SIZE/2,
                tilemapCamera.viewportHeight/2);

        userInterface = new UserInterface(this, entitiesBatch);
    }


    public void renderTerrain(){
        update();

        tilemapBatch.totalRenderCalls = 0;

        tilemapCamera.update();
        entitiesCamera.update();

        entitiesBatch.setProjectionMatrix(entitiesCamera.combined);
        tilemapBatch.setProjectionMatrix(tilemapCamera.combined);

        tilemapBatch.begin();
        terrain.draw(tilemapBatch,
                tilemapCamera.position.x - GameVariables.TILEMAP_CENTER,
                tilemapCamera.position.y - GameVariables.TILEMAP_CENTER);
        tilemapBatch.end();

        entitiesBatch.begin();
        player.getSprite().draw(entitiesBatch);
        entitiesBatch.end();

        totalRenderCalls = tilemapBatch.totalRenderCalls;

        userInterface.draw();
    }

    public void update(){
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (terrain.canMove(tilemapCamera.position.x, tilemapCamera.position.y+1)){
                tilemapCamera.translate(0.f, GameVariables.PLAYER_SPEED);
                terrain.updateWorld(tilemapCamera.position.x - GameVariables.TILEMAP_CENTER,
                        tilemapCamera.position.y - GameVariables.TILEMAP_CENTER);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            if (terrain.canMove(tilemapCamera.position.x, tilemapCamera.position.y-1)) {
                tilemapCamera.translate(0.f, -GameVariables.PLAYER_SPEED);
                terrain.updateWorld(tilemapCamera.position.x - GameVariables.TILEMAP_CENTER,
                        tilemapCamera.position.y - GameVariables.TILEMAP_CENTER);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            if (terrain.canMove(tilemapCamera.position.x-1, tilemapCamera.position.y)) {
                tilemapCamera.translate(-GameVariables.PLAYER_SPEED, 0.f);
                terrain.updateWorld(tilemapCamera.position.x - GameVariables.TILEMAP_CENTER,
                        tilemapCamera.position.y - GameVariables.TILEMAP_CENTER);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            if (terrain.canMove(tilemapCamera.position.x+1, tilemapCamera.position.y)) {
                tilemapCamera.translate(GameVariables.PLAYER_SPEED, 0.f);
                terrain.updateWorld(tilemapCamera.position.x - GameVariables.TILEMAP_CENTER,
                        tilemapCamera.position.y - GameVariables.TILEMAP_CENTER);
            }
        }



    }

    public void dispose(){

    }

    public OrthographicCamera getEntitiesCamera() {
        return entitiesCamera;
    }

    public OrthographicCamera getTilemapCamera() {
        return tilemapCamera;
    }

    public Terrain getTerrain() {
        return terrain;
    }
}
