package dev.bk201.game.Screens;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Sound;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Stack;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.Drawable;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.ScreenUtils;
import com.badlogic.gdx.utils.viewport.FitViewport;
import dev.bk201.game.GameSettings;
import dev.bk201.game.MyGdxGame;
import dev.bk201.game.OptionsUI;

import java.util.Iterator;

public class GameScreen implements Screen {
    private Stage stage;
    private Stage optionsStage;
    private Game game;
    private final GameSettings settings;
    public SpriteBatch batch;
    public FitViewport viewport;
    private final Animation<TextureRegion> explosionAnim;
    private final Label pressSpace;
    private final Label ufoKilledLabel;
    private final Label hpTextLabel;
    private final Label hpPercentageLabel;
    private final Label gameOverLabel;
    private final Table gameOverTable;
    private final TextButton restartButton;
    private final TextButton exitButton;
    private final Sound laserSound;
    private final Sound explosionSound;
    private final Sound gameOverSound;
    Texture backgroundTexture;
    Texture shipTexture;
    Texture ufoTexture;
    Texture laserTexture;
    Image hpBarBg;
    Image hpBarFill;
    Sprite shipSprite;
    Rectangle shipRectangle;
    Vector2 touchPos;
    Array<Sprite> ufoSprites;
    Array<Sprite> laserSprites;
    Array<Explosion> explosions;
    boolean optionsOpen = false;
    boolean gameStarted = false;
    boolean gameOver = false;
    boolean pause = false;
    float dropTimer;
    float spawnInterval;
    float textFlashTimer;
    float displayHp;
    int FRAME_COLS = 7;
    int FRAME_ROWS = 1;
    int ufoKilled;
    int maxHp = 200;
    int hp;

    public GameScreen(Game aGame){
        this.game = aGame;
        this.settings = ((MyGdxGame) aGame).settings;
        batch = new SpriteBatch();
        viewport = new FitViewport(900,600);
        stage = new Stage(viewport);
        optionsStage = new Stage(viewport);
        spawnInterval = 2.5F;
        displayHp = maxHp;
        hp = maxHp;

        //initializing Assets
        backgroundTexture = new Texture("assets/background.png");
        shipTexture = new Texture("assets/spaceship.png");
        laserTexture = new Texture("assets/laser_blast.png");
        ufoTexture = new Texture("assets/ufo.png");
        Texture explosionSheet = new Texture("assets/explosion pack 1/Explosions pack/explosion-1-g/spritesheet.png");
        laserSound = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/laser.wav"));
        explosionSound = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/explosion-1.wav"));
        gameOverSound = Gdx.audio.newSound(Gdx.files.internal("assets/sounds/game-over.wav"));
        Drawable bgDrawable = MyGdxGame.gameSkin.getDrawable("dark-blue");
        Drawable fillDrawable = MyGdxGame.gameSkin.getDrawable("white");

        //initializing Stage Actors
        pressSpace = new Label("Press SPACE", MyGdxGame.gameSkin);
        pressSpace.setPosition(
                stage.getViewport().getWorldWidth() /2f,
                stage.getViewport().getWorldHeight() / 2f,
                Align.center);
        ufoKilledLabel = new Label("Kills: " + ufoKilled, MyGdxGame.gameSkin);
        gameOverLabel = new Label("GAME OVER", MyGdxGame.gameSkin);
        restartButton = new TextButton("RESTART", MyGdxGame.gameSkin);
        exitButton = new TextButton("EXIT", MyGdxGame.gameSkin);
        hpTextLabel = new Label("HP", MyGdxGame.gameSkin);
        hpPercentageLabel = new Label("100%", MyGdxGame.gameSkin);
        hpBarBg = new Image(bgDrawable);
        hpBarBg.setSize(100, 15);
        hpBarFill = new Image(fillDrawable);
        hpBarFill.setSize(100, 15);
        hpBarFill.setOrigin(0, 0);

        Table killsCounterTable = new Table();
        killsCounterTable.setFillParent(true);
        killsCounterTable.bottom().left().pad(10);
        killsCounterTable.add(ufoKilledLabel);

        Stack barStack = new Stack();
        barStack.add(hpBarBg);
        barStack.add(hpBarFill);
        Table hpTable = new Table();
        hpTable.setFillParent(true);
        hpTable.bottom().right().pad(10);
        hpTable.add(hpTextLabel).padRight(5);
        hpTable.add(barStack).width(100).height(15).padRight(5);
        hpTable.add(hpPercentageLabel);

        gameOverTable = new Table();
        gameOverTable.setFillParent(true);
        gameOverTable.center();
        gameOverTable.add(gameOverLabel).padBottom(20).row();
        gameOverTable.add(restartButton).width(200).height(80).row();
        gameOverTable.add(exitButton).width(200).height(80);
        gameOverTable.defaults().pad(3);
        gameOverTable.setVisible(false);

        stage.addActor(pressSpace);
        stage.addActor(killsCounterTable);
        stage.addActor(hpTable);
        stage.addActor(gameOverTable);

        Table optionUi = OptionsUI.create(game, ()-> {
            optionsOpen = false;
            pause = false;
            Gdx.input.setInputProcessor(stage);
        });
        optionsStage.addActor(optionUi);

        //initializing Game Sprites and size
        shipSprite = new Sprite(shipTexture);
        shipSprite.setSize(60,60);
        shipSprite.setPosition(stage.getViewport().getWorldWidth() /2f , 25);

        //Explosion texture
        TextureRegion[][] txr = TextureRegion.split(
                explosionSheet,
                explosionSheet.getWidth() / FRAME_COLS,
                explosionSheet.getHeight() / FRAME_ROWS);
        Array<TextureRegion> frames = new Array<>();

        for (int i = 0; i < FRAME_ROWS; i++) {
            for (int j = 0; j < FRAME_COLS; j++) {
                frames.add(txr[i][j]);
            }
        }

        explosionAnim = new Animation<>(0.05f, frames, Animation.PlayMode.NORMAL);

        //Buttons
        restartButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                restartGame();
            }
        });

        exitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                Gdx.app.exit();
            }
        });

        touchPos = new Vector2();

        shipRectangle = new Rectangle();
        ufoSprites = new Array<>();
        laserSprites = new Array<>();
        explosions = new Array<>();
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
        stage.act(delta);
        input(delta);
        logic(delta);
        draw();
    }

    private void input(float delta) {
        float speed = 500f;

        if (gameStarted && !gameOver && !pause) {
            if (Gdx.input.isKeyPressed(Input.Keys.RIGHT)){
                shipSprite.translateX(speed * delta);
            }
            else if (Gdx.input.isKeyPressed(Input.Keys.LEFT)) {
                shipSprite.translateX(-speed * delta);
            }

            if (shipSprite.getX() < 0) {
                shipSprite.setX(0);
            }

            if (shipSprite.getX() > (viewport.getWorldWidth() - shipSprite.getWidth())) {
                shipSprite.setX((viewport.getWorldWidth() - shipSprite.getWidth()));
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            if (!gameStarted) {
                gameStarted = true;
            } else if (!pause) {
                Sprite laserSprite = new Sprite(laserTexture);
                laserSprite.setSize(20, 20);
                laserSprite.setY(shipSprite.getY() + shipSprite.getHeight());
                laserSprite.setX(shipSprite.getX() + (shipSprite.getWidth() / 2) - (laserSprite.getWidth() / 2));

                laserSprites.add(laserSprite);
                laserSound.play(settings.sfxVolume);
            }
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ESCAPE)) {
            optionsOpen = !optionsOpen;

            if (optionsOpen) {
                pause = true;
                Gdx.input.setInputProcessor(optionsStage);
            } else {
                pause = false;
                Gdx.input.setInputProcessor(stage);
            }
        }
    }

    private void logic(float delta){
        if (optionsOpen) return;

        textFlashTimer += delta;
        displayHp = MathUtils.lerp(displayHp, hp, 5F * delta);

        float ratio = displayHp / (float) maxHp;
        ratio = MathUtils.clamp(ratio, 0f, 1f);

        float barWidth = hpBarBg.getWidth();
        hpBarFill.setWidth(barWidth * ratio);

        int percent = (int)(ratio * 100);
        hpPercentageLabel.setText(percent + "%");

        if (!gameStarted) {
            pressSpace.setVisible((textFlashTimer % 1) < 0.5);
        } else {
            pressSpace.setVisible(false);
        }

        if (gameStarted && !gameOver) {
            float laserSpeed = 300f;
            float ufoSpeed = 50F;

            dropTimer += delta;

            if (dropTimer > spawnInterval) {
                Sprite ufoSprite = new Sprite(ufoTexture);
                ufoSprite.setSize(50, 50);
                ufoSprite.setPosition(MathUtils.random(viewport.getWorldWidth() - ufoSprite.getWidth()), viewport.getWorldHeight());
                ufoSprites.add(ufoSprite);
                dropTimer = 0;
            }

            for (Sprite ufo : ufoSprites) {
                ufo.setY(ufo.getY() - ufoSpeed * delta);
            }
            for (Sprite laser : laserSprites) {
                laser.setY(laser.getY() + laserSpeed * delta);
            }

            Iterator<Sprite> laserCollisionIt = laserSprites.iterator();
            while (laserCollisionIt.hasNext()) {
                Sprite laser = laserCollisionIt.next();
                Iterator<Sprite> ufoIt = ufoSprites.iterator();

                while (ufoIt.hasNext()) {
                    Sprite ufo = ufoIt.next();
                    if (laser.getBoundingRectangle().overlaps(ufo.getBoundingRectangle())) {
                        laserCollisionIt.remove();
                        ufoIt.remove();
                        explosionSound.play(settings.sfxVolume);

                        Explosion e = new Explosion();
                        e.x = ufo.getX() + ufo.getWidth() / 2f;
                        e.y = ufo.getY() + ufo.getHeight() / 2f;
                        explosions.add(e);

                        ufoKilled++;
                        ufoKilledLabel.setText("Kills: " + ufoKilled);
                        break;
                    }
                }
            }

            Iterator<Sprite> laserIt = laserSprites.iterator();
            while (laserIt.hasNext()) {
                Sprite laser = laserIt.next();
                if (laser.getY() >= viewport.getWorldHeight()) {
                    laserIt.remove();
                }
            }

            Iterator<Sprite> ufoOffScreenIt = ufoSprites.iterator();
            while (ufoOffScreenIt.hasNext()){
                Sprite ufo = ufoOffScreenIt.next();
                if (ufo.getY() < 0) {
                    ufoOffScreenIt.remove();
                    hp = hp - 10;
                }
            }

            Iterator<Explosion> exIt = explosions.iterator();
            while (exIt.hasNext()) {
                Explosion e = exIt.next();
                e.stateTime += delta;

                if (explosionAnim.isAnimationFinished(e.stateTime)) {
                    exIt.remove();
                }
            }

            hp = Math.max(0,hp);

            if (hp == 0 ) {
                gameOver = true;
                gameOverTable.setVisible(true);
                gameOverSound.play(settings.sfxVolume);
            }

            shipRectangle.set(shipSprite.getX(), shipSprite.getY(), shipSprite.getWidth(), shipSprite.getHeight());
        }
    }

    private void draw() {
        ScreenUtils.clear(Color.BLACK);
//        viewport.apply();
//        batch.setProjectionMatrix(viewport.getCamera().combined);
        batch.begin();

        float worldWidth = viewport.getWorldWidth();
        float worldHeight = viewport.getWorldHeight();
        float centerX = worldWidth / 2;
        float centerY = worldHeight / 2;
        batch.draw(backgroundTexture,0,0, worldWidth, worldHeight);
        shipSprite.draw(batch);

        if (gameStarted && !gameOver) {
            for (Sprite laser : laserSprites) {
                laser.draw(batch);
            }
            for (Sprite ufoSprite : ufoSprites) {
                ufoSprite.draw(batch);
            }

            for (Explosion e : explosions) {
                float scale = 1.6f;
                TextureRegion frame = explosionAnim.getKeyFrame(e.stateTime);
                float width = frame.getRegionWidth();
                float height = frame.getRegionHeight();
                float drawWidth = width * scale;
                float drawHeight = height * scale;
                batch.draw(frame, e.x - drawWidth / 2f, e.y - drawHeight / 2f, width * scale, height * scale);
            }
        }
        batch.end();

        stage.draw();

        if (optionsOpen) {
            optionsStage.act(Gdx.graphics.getDeltaTime());
            optionsStage.draw();
        }
    }

    private void restartGame() {
        gameStarted = false;
        gameOver = false;
        hp = maxHp;
        displayHp = maxHp;
        ufoKilled = 0;
        ufoKilledLabel.setText("Kills: 0");
        ufoSprites.clear();
        laserSprites.clear();
        dropTimer = 0;
        textFlashTimer = 0;
        shipSprite.setPosition(viewport.getWorldWidth() / 2f, 25);

        gameOverTable.setVisible(false);
    }

    @Override
    public void resize(int width, int height){
        stage.getViewport().update(width, height, true);
        optionsStage.getViewport().update(width, height, true);
        viewport.update(width, height, true);
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
        laserTexture.dispose();
        laserSound.dispose();
        explosionSound.dispose();
        gameOverSound.dispose();
    }

    private static class Explosion {
        float x, y;
        float stateTime = 0f;
        boolean finished = false;
    }
}
