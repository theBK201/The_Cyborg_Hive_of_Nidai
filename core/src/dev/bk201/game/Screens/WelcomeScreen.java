package dev.bk201.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.*;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import dev.bk201.game.Helpers.MenuInput;
import dev.bk201.game.MyGdxGame;


public class WelcomeScreen implements Screen {

    private Stage stage;
    private Game game;
    private TextButton playButton;
    private TextButton optionButton;
    private TextButton creditsButton;
    private MenuInput menuInput;

    public WelcomeScreen(Game aGame){
        game = aGame;
        stage = new Stage(new ScreenViewport());

        // A Screen Welcome Message
        Label title = new Label("The Cyborg \n Hive of Nidai", MyGdxGame.gameSkin, "title");
        title.setAlignment(Align.center);
        title.setY((float) (Gdx.graphics.getHeight() * 2) /3);
        title.setWidth(Gdx.graphics.getWidth());

        //Welcome image
        Texture startImageTexture = new Texture("assets/start-image.jpg");
        Image bg = new Image(startImageTexture);
        bg.setFillParent(true);

        stage.addActor(bg);
        stage.addActor(title);

        //Welcome screen layout
        Table welcomeScreenTable = new Table();
        welcomeScreenTable.setFillParent(true);
        welcomeScreenTable.bottom();

        // The Welcome screen buttons
        Image selector = new Image(MyGdxGame.gameSkin.getDrawable("right-arrow"));
        //avoids the warning by using a dummy Actor instead of an empty cell.
        Actor optionDummy = new Actor();
        Actor creditDummy = new Actor();

        playButton= new TextButton("Play!", MyGdxGame.gameSkin);
        playButton.addListener(new ChangeListener(){
            @Override
            public void changed (ChangeEvent event, Actor actor){
                game.setScreen(new GameScreen(game));
            }
        });

        optionButton = new TextButton("Options", MyGdxGame.gameSkin);
        optionButton.addListener(new ChangeListener(){
            @Override
            public void changed (ChangeEvent event, Actor actor){
                game.setScreen(new OptionsScreen(game));
            }
        });

        creditsButton = new TextButton("Credits", MyGdxGame.gameSkin);
        creditsButton.addListener(new ChangeListener(){
            @Override
            public void changed (ChangeEvent event, Actor actor){
                game.setScreen(new CreditsScreen(game));
            }
        });

        Cell<Image> playSelector = welcomeScreenTable.add(selector).width(30);
        welcomeScreenTable.add(playButton).width(200).height(80).row();

        Cell<Actor> optionSelector = welcomeScreenTable.add(optionDummy);
        optionSelector.width(30);
        welcomeScreenTable.add(optionButton).width(200).height(80).row();

        Cell<Actor> creditSelector = welcomeScreenTable.add(creditDummy);
        creditSelector.width(30);
        welcomeScreenTable.add(creditsButton).width(200).height(80).row();

        stage.addActor(welcomeScreenTable);

        menuInput = new MenuInput(playButton, optionButton, creditsButton);
        menuInput.setSelector(selector, playSelector, optionSelector, creditSelector);
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

        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
