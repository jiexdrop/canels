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

public class World implements InputProcessor {
    private Player player;

    private Terrain terrain;

    private OrthographicCamera entitiesCamera;

    private OrthographicCamera tilemapCamera;

    private SpriteBatch tilemapBatch;

    private SpriteBatch entitiesBatch;

    private BitmapFont font;

    public World(){
        Gdx.input.setInputProcessor(this);

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

        player.getSprite().setPosition(tilemapCamera.viewportWidth/2 - GameVariables.TILES_SIZE/2,
                tilemapCamera.viewportHeight/2);


    }


    public void renderTerrain(){
        update();

        tilemapCamera.update();
        entitiesCamera.update();

        entitiesBatch.setProjectionMatrix(entitiesCamera.combined);
        tilemapBatch.setProjectionMatrix(tilemapCamera.combined);

        tilemapBatch.begin();
        terrain.draw(tilemapBatch);
        tilemapBatch.end();

        entitiesBatch.begin();
        player.getSprite().draw(entitiesBatch);
        font.draw(entitiesBatch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        entitiesBatch.end();
    }

    public void update(){
        if(Gdx.input.isKeyPressed(Input.Keys.UP)) {
            if (terrain.canMove(tilemapCamera.position.x, tilemapCamera.position.y+1)){
                tilemapCamera.translate(0.f, GameVariables.PLAYER_SPEED);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.DOWN)){
            if (terrain.canMove(tilemapCamera.position.x, tilemapCamera.position.y-1)) {
                tilemapCamera.translate(0.f, -GameVariables.PLAYER_SPEED);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.LEFT)){
            if (terrain.canMove(tilemapCamera.position.x-1, tilemapCamera.position.y)) {
                tilemapCamera.translate(-GameVariables.PLAYER_SPEED, 0.f);
            }
        }
        if(Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            if (terrain.canMove(tilemapCamera.position.x+1, tilemapCamera.position.y)) {
                tilemapCamera.translate(GameVariables.PLAYER_SPEED, 0.f);
            }
        }

        terrain.updateWorld(tilemapCamera.position.x - ((GameVariables.CHUNK_SIZE-2)*GameVariables.CHUNK_SIZE),
                tilemapCamera.position.y - ((GameVariables.CHUNK_SIZE-2)*GameVariables.CHUNK_SIZE));

        //terrain.placeTile(tilemapCamera.position.x, tilemapCamera.position.y, Tiles.getDoorTile());
    }

    public void dispose(){

    }

    @Override
    public boolean keyDown(int keycode) {
        return false;
    }

    @Override
    public boolean keyUp(int keycode) {
        return false;
    }

    @Override
    public boolean keyTyped(char character) {
        return false;
    }

    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button) {
        Vector3 result = tilemapCamera.unproject(new Vector3(screenX, screenY, 0.f));
        terrain.placeTile(result.x, result.y, Tiles.getDoorTile());
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        Vector3 result = tilemapCamera.unproject(new Vector3(screenX, screenY, 0.f));
        terrain.placeTile(result.x, result.y, Tiles.getDoorTile());
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(int amount) {
        return false;
    }
}
