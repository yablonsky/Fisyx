package com.ayablonskyy.phi6.screens;

import com.ayablonskyy.phi6.Phi6;
import com.ayablonskyy.phi6.lib.Phi6ScreenAdapter;
import com.ayablonskyy.phi6.orbiter.Planet;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by and on 9/11/2017.
 */

public class BounceScreen extends Phi6ScreenAdapter {
    private Planet ball;
    private Planet ballOrigin;
    private Vector2 gravity;

    public BounceScreen(Phi6 phi6) {
        super(phi6);
        viewportWidth = 1000;
        gravity = new Vector2(0, -10);
    }

    @Override
    public void show() {
        super.show();
        Vector2 viewportCenter = new Vector2(phi6.camera.viewportWidth / 2, phi6.camera.viewportHeight / 2);
        ball = new Planet(viewportCenter, 50, Color.BLUE);
        ballOrigin = new Planet(viewportCenter, 50, Color.WHITE);
    }

    @Override
    protected void update(float delta) {
        ball.applyForce(gravity);
        ball.update(delta);
        boolean bounce = ball.boxBounce(0, 0, phi6.camera.viewportWidth, phi6.camera.viewportHeight);
        if (bounce) {
            Gdx.app.log("Bounce Velocity: ", ((Float) ball.velocity.len()).toString());
        }
    }

    @Override
    protected void draw(float delta) {
        ballOrigin.draw(phi6.batch);
        ball.draw(phi6.batch, phi6.font, true);
    }
}
