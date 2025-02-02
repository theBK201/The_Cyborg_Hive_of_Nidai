package dev.bk201.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

public class GameScreen implements Screen {
    private Stage stage;
    private Game game;
    public SpriteBatch batch;
    public FitViewport viewport;
    public BitmapFont font;
    Texture backgroundTexture;
    Texture shipTexture;
    Texture ufoTexture;
    Sprite shipSprite;
    Rectangle shipRectangle;
    Rectangle ufoRectangle;
    Vector2 touchPos;
    Array<Sprite> ufoSprites;
    float dropTimer;
    int ufoKilled;

    public GameScreen(Game aGame){
        this.game = aGame;
        stage = new Stage(new ScreenViewport());
        backgroundTexture = new Texture("assets/background.png");
        shipTexture = new Texture("assets/spaceship.png");
        ufoTexture = new Texture("assets/ufo.png");

        shipSprite = new Sprite(shipTexture);
        shipSprite.setSize(60,60);

        touchPos = new Vector2();

        shipRectangle = new Rectangle();
        ufoRectangle = new Rectangle();
        ufoSprites = new Array<>();
    }
    @Override
    public void show(){
        Gdx.app.log("MainScreen","launched");
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float delta){
//        Gdx.gl.glClearColor(1,1,1,1);
//        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        stage.act();
        draw();
        input();
        logic();
    }

    private void input() {
        float speed = 500f;
        float delta = Gdx.graphics.getDeltaTime();

        if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
            shipSprite.translateX(speed * delta);
        }
        else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
            shipSprite.translateX(-speed * delta);
        }

//        if (Gdx.input.isTouched()) {
//            touchPos.set(Gdx.input.getX(), Gdx.input.getY());
//            viewport.unproject(touchPos);
//            shipSprite.setCenterX(touchPos.x);
//        }
    }

    private void logic(){

    }

    private void draw() {
        batch = new SpriteBatch();
        viewport = new FitViewport(900,600);
        ScreenUtils.clear(Color.BLACK);
//        viewport.apply();
//        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        batch.draw(backgroundTexture,0,0, worldWidth, worldHeight);
        shipSprite.draw(batch);

        for (Sprite ufoSprite: ufoSprites) {
            ufoSprite.draw(batch);
        }
        batch.end();
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
        backgroundTexture.dispose();
        ufoTexture.dispose();
        shipTexture.dispose();
    }
}
