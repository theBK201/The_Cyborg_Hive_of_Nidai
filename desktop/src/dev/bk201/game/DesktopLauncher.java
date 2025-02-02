package dev.bk201.game;

import com.badlogic.gdx.Graphics;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import dev.bk201.game.MyGdxGame;

// Please note that on macOS your application needs to be started with the -XstartOnFirstThread JVM argument
public class DesktopLauncher {
	public static void main (String[] arg) {
		int width = 900;
		int height = 600;

		Graphics.Monitor primary = Lwjgl3ApplicationConfiguration.getPrimaryMonitor();
		Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
		Graphics.DisplayMode mode = Lwjgl3ApplicationConfiguration.getDisplayMode(primary);

		int posX = primary.virtualX + mode.width/2 - width/2;
		int posY = primary.virtualY + mode.height/2 - height/2;

		config.setForegroundFPS(60);
		config.setTitle("The Cyborg Hive of Nidai");
		config.setResizable(false);
		config.setWindowedMode(width, height);
		config.setWindowPosition(posX,posY);
		config.useVsync(false);
		new Lwjgl3Application(new MyGdxGame(), config);
	}
}
