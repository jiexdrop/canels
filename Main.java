package com.jiedro.canels;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jiedro.canels.view.World;

public class Main extends Game {
    private SpriteBatch batch;
    
    private BitmapFont font;

    private World world;

    @Override
    public void create() {
        batch = new SpriteBatch();
        font = new BitmapFont();
        world = new World();
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        world.renderTerrain();

        batch.begin();
        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        world.dispose();
    }


}
