package dev.bk201.game;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import dev.bk201.game.MyGdxGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		config.setForegroundFPS(60);
		config.setTitle("The Cyborg Hive of Nidai");
		config.setResizable(false);
		config.setWindowPosition(1950,330);
		config.setWindowedMode(900, 600);
		config.useVsync(true);
		new Lwjgl3Application(new MyGdxGame(), config);
	}
}
