package dev.bk201.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import dev.bk201.game.Screens.WelcomeScreen;

public class MyGdxGame extends Game {
	static public Skin gameSkin;
	public GameSettings settings;
	public Music bgMusic;

	@Override
	public void create () {
		gameSkin = new Skin(Gdx.files.internal("skin/star-soldier-ui.json"));
		settings = new GameSettings();

		bgMusic = Gdx.audio.newMusic(Gdx.files.internal("assets/sounds/game-music1.mp3"));
		bgMusic.setLooping(true);
		bgMusic.setVolume(settings.musicVolume);
		bgMusic.play();

		this.setScreen(new WelcomeScreen(this));
	}

	@Override
	public void render () {
		bgMusic.setVolume(settings.musicVolume);
		super.render();
	}
	
	@Override
	public void dispose () {
		bgMusic.dispose();
		gameSkin.dispose();
	}
}
