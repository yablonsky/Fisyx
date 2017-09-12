package com.ayablonskyy.phi6.screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;

/**
 * Created by and on 8/15/2017.
 */

public class VectorsScreen extends ScreenAdapter {
    Game phi6;
    ShapeRenderer batch;
    OrthographicCamera camera;
    Vector2 vector;
    Vector2 wall;
    Vector2 orig;
    Vector2 projec;
    Vector2 rejec;
    Vector2 reflection;

    public VectorsScreen(Game phi6) {
        this.phi6 = phi6;
    }

    @Override
    public void show() {
        super.show();
        camera.position.setZero();
        orig = new Vector2();
        batch = new ShapeRenderer();
        wall = new Vector2(0, camera.viewportHeight / 2);

        vector = new Vector2(camera.viewportWidth / 4, -camera.viewportHeight / 2);
        vector.scl(.3f);

        update(0);
    }

    public Vector2 reflectionSimple(Vector2 norWall) {
        /**
         * Project vector onto wall normal unit vector (points to square centre point)
         * http://www.3dkingdoms.com/weekly/weekly.php?a=2
         * Vnew = -2*(V dot N)*N + V
         */
        return norWall.scl(-2 * vector.dot(norWall)).add(vector);
    }

    public Vector2 reflectionFull() {
        return projec.cpy().sub(rejec);
    }

    public void update(float delta) {
        Vector2 norWall = wall.cpy().nor();
        projec = norWall.cpy().scl(vector.dot(norWall));
        rejec = vector.cpy().sub(projec);
        reflection = reflectionSimple(rejec.cpy().nor());
//        reflection = reflectionFull();
        wall.setAngle(wall.angle() + delta * 10);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        camera.update();
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        batch.setProjectionMatrix(camera.combined);

        batch.begin(ShapeRenderer.ShapeType.Line);
        batch.setColor(Color.WHITE);
        batch.line(vector.cpy().scl(-1), orig);
        batch.line(orig, reflection);

        batch.setColor(Color.GRAY);
        batch.line(0, 0, camera.viewportWidth, 0);
        batch.line(0, 0, 0, camera.viewportHeight);

        batch.setColor(Color.YELLOW);
        batch.line(wall.cpy().scl(-1), wall);

        batch.setColor(Color.RED);
        batch.line(projec.cpy().scl(-1), orig);

        batch.setColor(Color.BLUE);
        batch.line(rejec.cpy().scl(-1), orig);

        batch.end();

        update(delta);
    }
}
