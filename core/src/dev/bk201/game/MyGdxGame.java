package dev.bk201.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import dev.bk201.game.Screens.WelcomeScreen;

public class MyGdxGame extends Game {
	static public Skin gameSkin;
	@Override
	public void create () {
		gameSkin = new Skin(Gdx.files.internal("skin/star-soldier-ui.json"));
		this.setScreen(new WelcomeScreen(this));
	}

	@Override
	public void render () {
		super.render();
	}
	
	@Override
	public void dispose () {
	}
}
