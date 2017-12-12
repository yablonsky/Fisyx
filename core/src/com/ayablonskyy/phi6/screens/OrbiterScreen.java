package com.ayablonskyy.phi6.screens;

import com.ayablonskyy.phi6.Phi6;
import com.ayablonskyy.phi6.lib.Phi6ScreenAdapter;
import com.ayablonskyy.phi6.orbiter.Planet;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by and on 8/15/2017.
 */

public class OrbiterScreen extends Phi6ScreenAdapter {
    private Planet planet;
    private Planet moon;
    private Planet target;
    private InputAdapter inputProcessor;

    public OrbiterScreen(Phi6 phi6) {
        super(phi6);
        viewportWidth = 500;
    }

    @Override
    public void show () {
        super.show();
        inputProcessor = new InputAdapter() {
            @Override
            public boolean scrolled(int amount) {
                phi6.camera.zoom += amount * 0.2;
                return true;
            }
        };
        phi6.addInputProcessor(inputProcessor);
        Vector2 viewportCenter = new Vector2(phi6.camera.viewportWidth / 2, phi6.camera.viewportHeight / 2);
        planet = new Planet(viewportCenter, 100, Color.BLUE);
        moon = new Planet(viewportCenter.x - 100, viewportCenter.y + 100, 10, Color.RED);
        target = new Planet(viewportCenter.x - 100, viewportCenter.y + 100, 10, Color.WHITE);
        moon.applyForce(new Vector2(100, 0));
    }

    @Override
    public void draw (float delta) {
        planet.draw(phi6.batch);
        target.draw(phi6.batch);
        moon.draw(phi6.batch);
        phi6.font.draw(phi6.batch, "mass: " + planet.mass, planet.position.x, planet.position.y);
        phi6.font.draw(phi6.batch, "distance: " + planet.position.cpy().sub(moon.position).len(), planet.position.x, planet.position.y-10);
        phi6.font.draw(phi6.batch, "mass: " + moon.mass, moon.position.x, moon.position.y);
        phi6.font.draw(phi6.batch, "velocity: " + moon.velocity.len(), moon.position.x, moon.position.y - 10);
    }

    protected void update(float delta) {
        Vector2 step = planet.getPosition().cpy().sub(moon.getPosition()).setLength(1).scl(.2f);
        moon.update(delta);
//        planet.attract(moon);
        boolean bumped = moon.bump(planet);
        if (!bumped) {
            moon.acceleration.add(step);
//            moon.applyForce(planet.getPosition().cpy().sub(moon.getPosition()).scl(delta * 20));
        }
    }

    @Override
    public void dispose () {
        planet.dispose();
        moon.dispose();
    }

}
