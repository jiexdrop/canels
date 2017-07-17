package com.jiedro.canels.view;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.jiedro.canels.GameVariables;

/**
 * Renders the entities
 * Created by jiexdrop on 14/06/17.
 */

public class Renderer implements Disposable {

    private World world;

    private OrthographicCamera entitiesCamera;

    private OrthographicCamera tilemapCamera;

    private SpriteBatch batch;

    private UserInterface userInterface;

    private WorldInput worldInput;

    private InputMultiplexer inputMultiplexer;

    public Renderer(){
        world = new World();

        batch = new SpriteBatch();

        tilemapCamera = setupCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        entitiesCamera = setupCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        worldInput = new WorldInput(world, tilemapCamera, entitiesCamera);
        userInterface = new UserInterface(world, batch);

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(userInterface);
        inputMultiplexer.addProcessor(worldInput);

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public void resize(int width, int height) {
        userInterface.getViewport().update(width, height, true);
    }

    public void render(){
        batch.totalRenderCalls = 0;

        world.update();

        tilemapCamera.update();
        entitiesCamera.update();

        batch.setProjectionMatrix(tilemapCamera.combined);

        batch.begin();
        world.drawTilemapBackground(batch, tilemapCamera);
        world.drawEntities(batch);
        world.drawTilemapForeground(batch, tilemapCamera);
        batch.end();

        GameVariables.RENDER_CALLS = batch.totalRenderCalls;

        userInterface.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        userInterface.draw();
    }

    private static OrthographicCamera setupCamera(float width, float height){
        OrthographicCamera setupedCamera = new OrthographicCamera();
        setupedCamera.setToOrtho(false, (width / height) * GameVariables.ZOOM_LEVEL, GameVariables.ZOOM_LEVEL);
        setupedCamera.update();
        return setupedCamera;
    }


    @Override
    public void dispose(){
        batch.dispose();
        userInterface.dispose();
    }


}
