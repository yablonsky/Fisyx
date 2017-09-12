package com.ayablonskyy.phi6.screens;

import com.ayablonskyy.phi6.Phi6;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

import java.util.LinkedHashMap;
import java.util.Map;

/**
 * Created by and on 8/15/2017.
 */

public class MenuScreen extends ScreenAdapter {
    private final Phi6 phi6;
    private Stage stage;
    private Table table;
    private Skin skin;

    private enum ButtonLabel {
        VECTORS("Vectors"), BOUNCE("Bounce"), ORBITER("Orbiter"), HELP("Help");

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
        skin = new Skin(Gdx.files.internal("uiskin.json"));
        stage = new Stage();
        table = new Table();
        table.setFillParent(true);
        table.setDebug(true);
        stage.addActor(table);

        final LinkedHashMap<ButtonLabel, TextButton> buttons = new LinkedHashMap<ButtonLabel, TextButton>();
        buttons.put(ButtonLabel.VECTORS, new TextButton(ButtonLabel.VECTORS.label, skin));
        buttons.put(ButtonLabel.BOUNCE, new TextButton(ButtonLabel.BOUNCE.label, skin));
        buttons.put(ButtonLabel.ORBITER, new TextButton(ButtonLabel.ORBITER.label, skin));
        buttons.put(ButtonLabel.HELP, new TextButton(ButtonLabel.HELP.label, skin));

        for (Map.Entry<ButtonLabel, TextButton> entry : buttons.entrySet()) {
            table.add(entry.getValue()).expand().fill();
            table.row();
        }

        table.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.log("ChangeListener", String.format("Event: %s, Actor: %s", event, actor));
                if (actor instanceof TextButton) {
                    if (actor == buttons.get(ButtonLabel.VECTORS)) {
                        phi6.setScreen(new VectorsScreen(phi6));
                    } else if (actor == buttons.get(ButtonLabel.BOUNCE)) {
                        phi6.setScreen(new BounceScreen(phi6));
                    } else if (actor == buttons.get(ButtonLabel.ORBITER)) {
                        phi6.setScreen(new OrbiterScreen(phi6));
                    } else if (actor == buttons.get(ButtonLabel.HELP)) {
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
