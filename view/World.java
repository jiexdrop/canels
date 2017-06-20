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

    private OrthographicCamera camera;

    private SpriteBatch batch;

    private BitmapFont font;


    private MainInputProcessor mainInputProcessor;

    public World(){
        player = new Player();
        terrain = new Terrain();
        batch = new SpriteBatch();
        font = new BitmapFont();

        terrain.update(player);


        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * GameVariables.ZOOM_LEVEL, GameVariables.ZOOM_LEVEL);
        camera.update();


        player.getSprite().setPosition((GameVariables.CHUNK_SIZE)*GameVariables.TILES_SIZE, (GameVariables.CHUNK_SIZE/2)*GameVariables.TILES_SIZE);

        mainInputProcessor = new MainInputProcessor(player, terrain, camera);

        Gdx.input.setInputProcessor(mainInputProcessor);
    }


    public void renderTerrain(){
        update();

        camera.update();
        batch.setProjectionMatrix(camera.combined);
        terrain.getRenderer().setView(camera);
        terrain.getRenderer().render();

        batch.begin();

        player.getSprite().draw(batch);
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        batch.end();
    }

    public void update(){
        if (Gdx.input.isKeyPressed(Input.Keys.UP)) {
            this.move(player,0,1, 0, GameVariables.PLAYER_SPEED);
        } else if (Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            this.move(player,0,-1, 0, -GameVariables.PLAYER_SPEED);
        } else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            this.move(player,-1,0, -GameVariables.PLAYER_SPEED, 0);
        } else if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)) {
            this.move(player,1, 0, GameVariables.PLAYER_SPEED, 0);
        } else {
            player.move(0,0);
        }

        player.update();
        terrain.update(player);
    }

    public void move(Entity entity, int x, int y, double xVelocity, double yVelocity){
        if(terrain.canMove(entity.getScreenX()+x, entity.getScreenY()+y)){
            entity.move(xVelocity,yVelocity);
        } else {
            entity.move(0,0);
        }
    }

    public void dispose(){
        terrain.dispose();
    }
}
