package dev.bk201.game;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;

public class GameSettings {
    private static final String PREFS_NAME = "CHoN_settings";
    private final Preferences preferences;

    public float sfxVolume;
    public float musicVolume;

    public GameSettings() {
        preferences = Gdx.app.getPreferences(PREFS_NAME);
        load();
    }

    public void load(){
        sfxVolume = preferences.getFloat("sfxVolume", 0.5f);
        musicVolume = preferences.getFloat("musicVolume", 0.15f);
    }

    public void save() {
        preferences.putFloat("sfxVolume", sfxVolume);
        preferences.putFloat("musicVolume", musicVolume);
        preferences.flush(); // important: writes to disk
    }
}
