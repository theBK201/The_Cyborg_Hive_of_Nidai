package dev.bk201.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.bk201.game.Helpers.MenuInput;
import dev.bk201.game.UI.CreditsUI;

public class CreditsScreen implements Screen {
    private Stage stage;
    private Game game;
    private MenuInput menuInput;

    public CreditsScreen(Game aGame) {
        this.game = aGame;
        stage = new Stage(new ScreenViewport());

        CreditsUI ui = CreditsUI.create(game, () -> {
           game.setScreen(new WelcomeScreen(game));
        });

        stage.addActor(ui.uiTable);
        menuInput = new MenuInput(ui.backButton);
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
        menuInput.selectFirst();
    }

    @Override
    public void render(float delta) {
        menuInput.update();

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
