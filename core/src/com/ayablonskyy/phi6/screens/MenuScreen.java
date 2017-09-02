package com.ayablonskyy.phi6.screens;

import com.ayablonskyy.phi6.Phi6;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Pixmap;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;

/**
 * Created by and on 8/15/2017.
 */

public class MenuScreen extends ScreenAdapter {
    private final Phi6 phi6;
    private Stage stage;
    private Table table;

    private enum ButtonLabel {
        VECTORS("Vectors"), ORBITER("Orbiter"), HELP("Help");

        public final String label;
        ButtonLabel(String label) {
            this.label = label;
        }
    }

    public MenuScreen(final Phi6 phi6) {
        this.phi6 = phi6;
    }

    @Override
    public void resize(int width, int height) {
        super.resize(width, height);
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void show() {
        super.show();
        stage = new Stage();
        table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        TextButton.TextButtonStyle style = new TextButton.TextButtonStyle();
        Pixmap pixmap = new Pixmap(1, 1, Pixmap.Format.RGB888);
        pixmap.setColor(Color.GRAY);
        style.up = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.setColor(Color.LIGHT_GRAY);
        style.down = new TextureRegionDrawable(new TextureRegion(new Texture(pixmap)));
        pixmap.dispose();
        style.font = phi6.font;
        TextButton vectors = new TextButton(ButtonLabel.VECTORS.label, style);
//        vectors.setSize();
        table.add(vectors).expand().fill();
        table.row();
        TextButton orbiter = new TextButton(ButtonLabel.ORBITER.label, style);
        table.add(orbiter).expand().fill();
        table.row();
        table.add(new TextButton(ButtonLabel.HELP.label, style)).expand().fill();
        table.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("ChangeListener", String.format("Event: %s, Actor: %s", event, actor));
                if (actor instanceof TextButton) {
                    String label = ((TextButton) actor).getLabel().getText().toString();
                    Gdx.app.log("ChangeListener", String.format("Label: %s", label));
                    if (label.equals(ButtonLabel.VECTORS.label)) {
                        phi6.setScreen(new VectorsScreen(phi6.camera));
                    } else if (label.equals(ButtonLabel.ORBITER.label)) {
                        phi6.setScreen(new OrbiterScreen(phi6.camera, phi6.batch));
                    } else if (label.equals(ButtonLabel.HELP.label)) {
                        Gdx.app.log("ChangeListener", "Help button");
                    }
                    dispose();
                }

            }
        });
        phi6.addInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        super.render(delta);
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(Gdx.graphics.getDeltaTime());
        stage.draw();
    }

    @Override
    public void dispose() {
        super.dispose();
        stage.dispose();
    }
}
