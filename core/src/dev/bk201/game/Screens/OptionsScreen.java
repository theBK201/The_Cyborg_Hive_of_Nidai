package dev.bk201.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.bk201.game.GameSettings;
import dev.bk201.game.MyGdxGame;
import dev.bk201.game.OptionsUI;

public class OptionsScreen implements Screen {
    private Stage stage;
    private Game game;
    private GameSettings settings;

    public OptionsScreen(Game aGame) {
        this.game = aGame;
        this.settings = ((MyGdxGame) aGame).settings;

        stage = new Stage(new ScreenViewport());

        Table ui = OptionsUI.create(game, () -> {
           game.setScreen(new WelcomeScreen(game));
        });

        stage.addActor(ui);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        stage.draw();
    }

    @Override public void resize(int w, int h) {}

    @Override public void pause() {}

    @Override public void resume() {}

    @Override public void hide() {}

    @Override public void dispose() {
        stage.dispose();
    }
}
