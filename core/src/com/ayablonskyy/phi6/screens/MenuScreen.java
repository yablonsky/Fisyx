package com.ayablonskyy.phi6.screens;

import com.ayablonskyy.phi6.lib.Phi6ScreenAdapter;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;

/**
 * Created by and on 8/15/2017.
 */

public class MenuScreen extends Phi6ScreenAdapter{
    private Rectangle vectorsButton;
    private Rectangle orbiterButton;

    public MenuScreen(OrthographicCamera camera, SpriteBatch batch) {
        super(camera, batch);
    }

    @Override
    public void show() {
        super.show();
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
    }
}
