package com.ayablonskyy.phi6.screens;

import com.ayablonskyy.phi6.orbiter.Planet;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by and on 8/15/2017.
 */

public class OrbiterScreen extends ScreenAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private BitmapFont font;
    private Planet planet;
    private Planet moon;
    private Planet target;
    private final int VIEWPORT_WIDTH = 500;

    public OrbiterScreen(OrthographicCamera camera, SpriteBatch batch) {
        this.camera = camera;
        this.batch = batch;
    }

    @Override
    public void show () {
        initCamera();
        font = new BitmapFont();
        planet = new Planet(camera.viewportWidth / 2, camera.viewportHeight / 2, 100, Color.BLUE);
        target = new Planet(camera.viewportWidth / 2 + 50, camera.viewportHeight / 2 + 100, 10, Color.WHITE);

        moon = new Planet(camera.viewportWidth / 2 - 100, camera.viewportHeight / 2 + 100, 10, Color.RED);
        moon.applyForce(target.getPosition().cpy().sub(moon.getPosition()).scl(2));
    }

    @Override
    public void render (float delta) {
        update(delta);

        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        planet.draw(batch);
        target.draw(batch);
        moon.draw(batch);
        font.draw(batch, "velocity: " + moon.velocity.len(), 0, 0);
        batch.end();

    }

    protected void update(float delta) {
        moon.update(delta);
        boolean bumped = moon.bump(planet);
        if (!bumped) {
            moon.applyForce(planet.getPosition().cpy().sub(moon.getPosition()).scl(delta * 20));
        }
    }

    @Override
    public void dispose () {
        planet.dispose();
        moon.dispose();
    }

    private void initCamera () {
        int h = Gdx.graphics.getHeight();
        int w = Gdx.graphics.getWidth();
        camera.setToOrtho(false, VIEWPORT_WIDTH, VIEWPORT_WIDTH * h / w);
    }
}
