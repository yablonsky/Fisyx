package com.ayablonskyy.phi6;

import com.ayablonskyy.phi6.screens.MenuScreen;
import com.ayablonskyy.phi6.screens.VectorsScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Phi6 extends Game {
    private OrthographicCamera camera;
    private SpriteBatch batch;
    private MenuScreen menuScreen;
    private final static float CAMERA_RATIO = 10;

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(CAMERA_RATIO, CAMERA_RATIO * (h / w));
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        batch = new SpriteBatch();
        menuScreen = new MenuScreen(camera, batch);
        VectorsScreen vectorsScreen = new VectorsScreen(camera);
        this.setScreen(vectorsScreen);
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        camera.viewportWidth = CAMERA_RATIO;
        camera.viewportHeight = CAMERA_RATIO * height / width;
//        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
    }

    @Override
    public void dispose () {
        batch.dispose();
        menuScreen.dispose();
    }
}
