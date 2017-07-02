package com.jiedro.canels.view;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Matrix4;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.physics.box2d.Body;
import com.badlogic.gdx.physics.box2d.Box2D;
import com.badlogic.gdx.physics.box2d.Box2DDebugRenderer;
import com.badlogic.gdx.physics.box2d.CircleShape;
import com.badlogic.gdx.physics.box2d.Fixture;
import com.badlogic.gdx.physics.box2d.FixtureDef;
import com.badlogic.gdx.physics.box2d.PolygonShape;
import com.badlogic.gdx.physics.box2d.World;
import com.jiedro.canels.GameVariables;
import com.jiedro.canels.model.entity.Player;
import com.jiedro.canels.model.world.Terrain;

/**
 *
 * Created by jiexdrop on 14/06/17.
 */

public class WorldRender {
    private Player player;

    private Terrain terrain;

    private OrthographicCamera entitiesCamera;

    private OrthographicCamera tilemapCamera;

    private SpriteBatch tilemapBatch;

    private SpriteBatch entitiesBatch;

    private UserInterface userInterface;

    public int totalRenderCalls = 0;

    World world;

    private Box2DDebugRenderer debugRenderer;

    private float accumulator = 0;

    public WorldRender(){
        Box2D.init();
        world = new World(new Vector2(0, 0), true);

        debugRenderer = new Box2DDebugRenderer();

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

        player.getSprite().setPosition((GameVariables.TILES_SIZE*GameVariables.TILES_SIZE),
                (GameVariables.TILES_SIZE*GameVariables.TILES_SIZE)/2);


        userInterface = new UserInterface(this, entitiesBatch);
        Body body = world.createBody(player.getBodyDef());

        body.createFixture(player.getFixtureDef());
    }

    private void doPhysicsStep(float deltaTime) {
        // fixed time step
        // max frame time to avoid spiral of death (on slow devices)
        float frameTime = Math.min(deltaTime, 0.25f);
        accumulator += frameTime;
        while (accumulator >= GameVariables.TIME_STEP) {
            world.step(GameVariables.TIME_STEP, GameVariables.VELOCITY_ITERATIONS, GameVariables.POSITION_ITERATIONS);
            accumulator -= GameVariables.TIME_STEP;
        }
    }


    public void renderTerrain(){
        doPhysicsStep(Gdx.graphics.getDeltaTime());

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


        Matrix4 cameraCopy = tilemapCamera.combined.cpy();
        cameraCopy.translate(GameVariables.TILES_SIZE/2,GameVariables.TILES_SIZE/2,0f);
        debugRenderer.render(world, cameraCopy.scl(GameVariables.TILES_SIZE));

        userInterface.draw();
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
