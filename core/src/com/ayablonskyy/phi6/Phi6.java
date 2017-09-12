package com.ayablonskyy.phi6;

import com.ayablonskyy.phi6.screens.MenuScreen;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputAdapter;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Phi6 extends Game {
    public SpriteBatch batch;
    public BitmapFont font;
    public OrthographicCamera camera;

    private MenuScreen menuScreen;
    private final static float CAMERA_RATIO = 10;
    private InputAdapter inputProcessor;
    private InputMultiplexer inputMultiplexer;

    @Override
    public void create() {
        float w = Gdx.graphics.getWidth();
        float h = Gdx.graphics.getHeight();
        camera = new OrthographicCamera(CAMERA_RATIO, CAMERA_RATIO * (h / w));
        camera.position.set(camera.viewportWidth / 2, camera.viewportHeight / 2, 0);
        camera.update();
        batch = new SpriteBatch();
        font = new BitmapFont();
        font.setColor(1,1,1,1);
        inputProcessor = new InputAdapter() {
            @Override
            public boolean keyDown(int keycode) {
                Gdx.app.log("keyDown", String.format("%d", keycode));
                if (keycode == Input.Keys.ESCAPE) {
                    if (getScreen() != menuScreen) {
                        setScreen(menuScreen);
                    } else {
                        Gdx.app.exit();
                    }
                }
                return true;
            }
        };
        inputMultiplexer = new InputMultiplexer();
        inputMultiplexer.addProcessor(inputProcessor);
        Gdx.input.setInputProcessor(inputMultiplexer);
        menuScreen = new MenuScreen(this);
        setScreen(menuScreen);
    }

    public void addInputProcessor(InputProcessor inputProcessor) {
        inputMultiplexer.addProcessor(inputProcessor);
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
        font.dispose();
    }
}
