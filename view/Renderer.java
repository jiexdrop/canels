package com.jiedro.canels.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.Disposable;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.entity.Entity;
import com.jiedro.canels.model.entity.Living;
import com.jiedro.canels.model.entity.Orientation;
import com.jiedro.canels.model.world.Helpers;
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

    private ShapeRenderer shapeRenderer;

    private UserInterface userInterface;

    private WorldInput worldInput;

    private InputMultiplexer inputMultiplexer;

    private AnimatedEntity slimesAnimation = new AnimatedEntity(GameTextures.getSlimesTextures(),GameVariables.SLIME_FRAMES, GameVariables.SLIME_ID);

    private AnimatedEntity breakingAnimation = new AnimatedEntity(GameTextures.getSlimesTextures(), 18f, GameVariables.BREAK_FRAMES, GameVariables.BREAK_ID);

    private AnimatedEntity playerAnimation = new AnimatedEntity(GameTextures.getPlayerTexture(),GameVariables.PLAYER_FRAMES, GameVariables.PLAYER_ID);

    public Renderer(){
        world = new World();

        batch = new SpriteBatch();
        shapeRenderer = new ShapeRenderer();

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
        shapeRenderer.setProjectionMatrix(tilemapCamera.combined);

        batch.begin();
        drawTilemapBackground(batch, tilemapCamera);
        drawLivings(batch);
        drawTilemapForeground(batch, tilemapCamera);
        drawHints(batch);
        batch.end();

        shapeRenderer.begin(ShapeRenderer.ShapeType.Line);
        drawHints(shapeRenderer);
        shapeRenderer.end();



        GameVariables.RENDER_CALLS = batch.totalRenderCalls;

        userInterface.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        userInterface.draw();
    }

    private void drawHints(ShapeRenderer shapeRenderer) {
        //Draw square arround items on the ground
        for (Entity e:world.getEntities()) {
            if(!e.isAlive()) {
                shapeRenderer.rect(e.getX(),e.getY(), GameVariables.ITEM_SIZE, GameVariables.ITEM_SIZE);
            }
        }
    }

    private void drawHints(SpriteBatch batch) {
        //Breaking animation
        if(world.getWorkingTile()!=null) {
            Vector2 pos = Helpers.mapToScreen(world.getWorkingTile());
            batch.draw(breakingAnimation.getCurrentFrame(world.getHoldTime(), Orientation.RIGHT),
                    pos.x, pos.y);
        }

    }

    public void drawLivings(SpriteBatch batch) {
        batch.setColor(world.getPlayer().getColor());
        batch.draw(playerAnimation.getCurrentFrame(world.getPlayer().getElapsedTime(),
                world.getPlayer().getOrientation()),
                world.getPlayer().getX(),
                world.getPlayer().getY());

        for (Entity e:world.getEntities()) {
            batch.setColor(e.getColor());
            if(e.isAlive()) {
                batch.draw(slimesAnimation.getCurrentFrame(e.getElapsedTime(),
                        ((Living)e).getOrientation()), e.getX(), e.getY());
            }
            else {
                TileType tileType = TileType.valueOf(e.getName().toUpperCase());
                batch.draw(GameTextures.getTextureRegion(tileType),
                        e.getX(), e.getY(),
                        GameVariables.ITEM_SIZE,GameVariables.ITEM_SIZE);
            }
        }

    }

    public void drawTilemapBackground(SpriteBatch tilemapBatch, OrthographicCamera tilemapCamera) {
        tilemapCamera.position.set(world.getPlayer().getX(),
                world.getPlayer().getY(), 0.f);

        world.getTerrain().drawBackground(tilemapBatch,
                world.getPlayer().getX() - GameVariables.TILEMAP_CENTER,
                world.getPlayer().getY() - GameVariables.TILEMAP_CENTER);


        GameVariables.ENTITIES = world.getEntities().size();
    }

    public void drawTilemapForeground(SpriteBatch tilemapBatch, OrthographicCamera tilemapCamera) {
        tilemapCamera.position.set(world.getPlayer().getX(),
                world.getPlayer().getY(), 0.f);

        world.getTerrain().drawForeground(tilemapBatch,
                world.getPlayer().getX() - GameVariables.TILEMAP_CENTER,
                world.getPlayer().getY() - GameVariables.TILEMAP_CENTER);

        GameVariables.ENTITIES = world.getEntities().size();
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
