package com.jiedro.canels;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.jiedro.canels.model.entity.Player;
import com.jiedro.canels.model.entity.behaviours.WeaponBehaviour;
import com.jiedro.canels.model.input.MainInputProcessor;
import com.jiedro.canels.model.world.Terrain;

public class Main extends Game {
    SpriteBatch batch;

    OrthographicCamera camera;
    BitmapFont font;
    Player player;

    Terrain terrain;

    @Override
    public void create() {
        int zoom_level = 256;

        batch = new SpriteBatch();
        font = new BitmapFont();

        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();

        camera = new OrthographicCamera();
        camera.setToOrtho(false, (w / h) * zoom_level, zoom_level);
        camera.update();

        player = new Player();
        player.setPosition(camera.position.x, camera.position.y);
        player.setAttackBehaviour(new WeaponBehaviour());


        player.attack();

        terrain = new Terrain();

        Gdx.input.setInputProcessor(new MainInputProcessor(camera, player));
    }

    @Override
    public void render() {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        camera.position.x = player.getX();
        camera.position.y = player.getY();

        terrain.getRenderer().setView(camera);
        terrain.getRenderer().render();

        batch.begin();
        player.draw(batch);

        font.draw(batch, "FPS: " + Gdx.graphics.getFramesPerSecond(), 10, 20);
        batch.end();
    }

    @Override
    public void dispose() {
        batch.dispose();
        font.dispose();
        terrain.dispose();
    }


}
