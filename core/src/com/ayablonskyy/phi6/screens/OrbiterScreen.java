package com.ayablonskyy.phi6.screens;

import com.ayablonskyy.phi6.orbiter.Planet;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by and on 8/15/2017.
 */

public class OrbiterScreen extends ScreenAdapter {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private Planet planet;
    private Planet moon;
    private Planet target;
    private Vector2 moonPos;

    public OrbiterScreen(OrthographicCamera camera, SpriteBatch batch) {
        this.camera = camera;
        this.batch = batch;
    }

    @Override
    public void show () {
        planet = new Planet(camera.viewportWidth / 2, camera.viewportHeight / 2, 100, Color.BLUE);
        target = new Planet(camera.viewportWidth / 2, camera.viewportHeight / 2 + 100, 10, Color.WHITE);

        moonPos = new Vector2(camera.viewportWidth / 2 - 150, camera.viewportHeight / 2 + 150);
        moon = new Planet(moonPos.x, moonPos.y, 10, Color.RED);
        moon.applyForce(target.getPosition().cpy().sub(moon.getPosition()).scl(2));
    }

    @Override
    public void render (float delta) {
        camera.update();
        batch.setProjectionMatrix(camera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        batch.begin();
        planet.draw(batch);
        target.draw(batch);
        moon.draw(batch);
        moon.bump(planet);
        batch.end();

//        moon.applyForce(planet.getPosition().cpy().sub(moon.getPosition()).scl(0.1f));
        moon.update(delta);
    }

    @Override
    public void dispose () {
        planet.dispose();
        moon.dispose();
    }
}
