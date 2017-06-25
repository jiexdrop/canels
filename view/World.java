package com.jiedro.canels.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.entity.Entity;
import com.jiedro.canels.model.entity.Player;
import com.jiedro.canels.model.input.MainInputProcessor;
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

    private BitmapFont font;


    private MainInputProcessor mainInputProcessor;

    public World(){
        player = new Player();
        terrain = new Terrain();
        entitiesBatch = new SpriteBatch();
        tilemapBatch = new SpriteBatch();
        font = new BitmapFont();




        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        tilemapCamera = new OrthographicCamera();
        tilemapCamera.setToOrtho(false, (w / h) * GameVariables.ZOOM_LEVEL, GameVariables.ZOOM_LEVEL);
        tilemapCamera.update();


        entitiesCamera = new OrthographicCamera();
        entitiesCamera.setToOrtho(false, (w / h) * GameVariables.ZOOM_LEVEL, GameVariables.ZOOM_LEVEL);
        entitiesCamera.update();


        player.getSprite().setPosition((GameVariables.CHUNK_SIZE)*GameVariables.TILES_SIZE, (GameVariables.CHUNK_SIZE/2)*GameVariables.TILES_SIZE);

        mainInputProcessor = new MainInputProcessor(player, terrain, tilemapCamera, entitiesCamera);

        Gdx.input.setInputProcessor(mainInputProcessor);
    }


    public void renderTerrain(){
        update();

        tilemapCamera.update();
        entitiesCamera.update();

        entitiesBatch.setProjectionMatrix(entitiesCamera.combined);
        tilemapBatch.setProjectionMatrix(tilemapCamera.combined);


        tilemapBatch.begin();
        terrain.draw(tilemapBatch, player);
        tilemapBatch.end();

        entitiesBatch.begin();
        player.getSprite().draw(entitiesBatch);
        font.draw(entitiesBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        entitiesBatch.end();
    }

    public void update(){
        if(Gdx.input.isKeyPressed(Input.Keys.UP)){
            player.move(0,(float)GameVariables.PLAYER_SPEED);
            tilemapCamera.position.set(player.getX(), player.getY(), 0.f);
            terrain.updateWorld(player.getMapX()-GameVariables.CHUNK_SIZE,player.getMapY()-GameVariables.CHUNK_SIZE);
        } else if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            player.move(0,-(float)GameVariables.PLAYER_SPEED);
            tilemapCamera.position.set(player.getX(), player.getY(), 0.f);
            terrain.updateWorld(player.getMapX()-GameVariables.CHUNK_SIZE,player.getMapY()-GameVariables.CHUNK_SIZE);
        } else if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            player.move(-(float)GameVariables.PLAYER_SPEED,0);
            tilemapCamera.position.set(player.getX(), player.getY(), 0.f);
            terrain.updateWorld(player.getMapX()-GameVariables.CHUNK_SIZE,player.getMapY()-GameVariables.CHUNK_SIZE);
        } else if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            player.move((float)GameVariables.PLAYER_SPEED,0);
            tilemapCamera.position.set(player.getX(), player.getY(), 0.f);
            terrain.updateWorld(player.getMapX()-GameVariables.CHUNK_SIZE,player.getMapY()-GameVariables.CHUNK_SIZE);
        } else {
            player.move(0,0);
        }

        player.update();

    }

    public void dispose(){

    }
}
