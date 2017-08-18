package com.ayablonskyy.phi6.lib;

import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

/**
 * Created by and on 8/15/2017.
 */

public class Phi6ScreenAdapter extends ScreenAdapter {
    protected OrthographicCamera camera;
    protected SpriteBatch batch;

    public Phi6ScreenAdapter(OrthographicCamera camera, SpriteBatch batch) {
        this.camera = camera;
        this.batch = batch;
    }
}
