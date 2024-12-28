package dev.bk201.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {
    private Stage stage;
    private Game game;
    public GameScreen(Game aGame){
        game = aGame;
        stage = new Stage(new ScreenViewport());
    }
    @Override
    public void show(){
        Gdx.app.log("MainScreen","launched");
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta){
        Gdx.gl.glClearColor(1,1,1,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        stage.draw();
    }

    @Override
    public void resize(int width, int height){

    }
    @Override
    public void pause(){

    }    @Override
    public void resume(){

    }    @Override
    public void hide(){

    }
    @Override
    public void dispose(){
        stage.dispose();
    }
}
