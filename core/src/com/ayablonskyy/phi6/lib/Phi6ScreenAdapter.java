package com.ayablonskyy.phi6.lib;

import com.ayablonskyy.phi6.Phi6;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;

/**
 * Created by and on 8/15/2017.
 */

public abstract class Phi6ScreenAdapter extends ScreenAdapter {
    protected Phi6 phi6;
    protected float viewportWidth;

    public Phi6ScreenAdapter(Phi6 phi6) {
        this.phi6 = phi6;
    }

    @Override
    public void show() {
        super.show();
        initCamera();
    }

    protected void initCamera() {
        int h = Gdx.graphics.getHeight();
        int w = Gdx.graphics.getWidth();
        phi6.camera.setToOrtho(false, viewportWidth, viewportWidth * h / w);
    }

    @Override
    public void render(float delta) {
        super.render(delta);

        update(delta);

        phi6.camera.update();
        phi6.batch.setProjectionMatrix(phi6.camera.combined);

        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        phi6.batch.begin();
        draw(delta);
        phi6.batch.end();
    }

    protected abstract void update(float delta);
    protected abstract void draw(float delta);
}
