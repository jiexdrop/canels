package com.jiedro.canels.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.utils.Disposable;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.world.World;

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

    private int totalRenderCalls = 0;


    public Renderer(){
        world = new World();

        batch = new SpriteBatch();

        tilemapCamera = setupCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        entitiesCamera = setupCamera(Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        worldInput = new WorldInput(world, tilemapCamera, entitiesCamera);
        userInterface = new UserInterface(world, batch);

        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(userInterface.getStage());
        inputMultiplexer.addProcessor(worldInput);

        Gdx.input.setInputProcessor(inputMultiplexer);
    }

    public void renderTerrain(){
        batch.totalRenderCalls = 0;

        world.update();

        tilemapCamera.update();
        entitiesCamera.update();

        batch.setProjectionMatrix(tilemapCamera.combined);

        batch.begin();
        world.drawTilemapBackground(batch, tilemapCamera);
        world.drawEntities(batch, entitiesCamera);
        world.drawTilemapForeground(batch, tilemapCamera);

        batch.end();

        totalRenderCalls = batch.totalRenderCalls;

        userInterface.draw(totalRenderCalls);
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
    }


}
