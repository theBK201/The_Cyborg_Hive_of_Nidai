package dev.bk201.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;

public class OptionsUI {
    public static Table create(Game game, Runnable onBack) {
        MyGdxGame g = (MyGdxGame) game;
        GameSettings settings = g.settings;

        Table root = new Table();
        root.setFillParent(true);
        root.center();

        Label title = new Label("Options", MyGdxGame.gameSkin, "title");

        Label sfxLabel = new Label("SFX Volume", MyGdxGame.gameSkin);
        Slider sfxSlider = new Slider(0f, 1f, 0.01f, false, MyGdxGame.gameSkin);
        sfxSlider.setValue(settings.sfxVolume);
        Label sfxValueLabel = new Label((int)(settings.sfxVolume * 100) + "%", MyGdxGame.gameSkin);

        Label musicLabel = new Label("Music Volume", MyGdxGame.gameSkin);
        Slider musicSlider = new Slider(0f,1f,0.01f,false, MyGdxGame.gameSkin);
        musicSlider.setValue(settings.musicVolume);
        Label musicValueLabel = new Label((int)(settings.musicVolume * 100) + "%", MyGdxGame.gameSkin);

        //live update of sliders
        sfxSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float value = sfxSlider.getValue();
                settings.sfxVolume = value;
                sfxValueLabel.setText((int)(value * 100) + "%");
            }
        });

        musicSlider.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                float value = musicSlider.getValue();
                settings.musicVolume = value;
                musicValueLabel.setText((int)(value * 100) + "%");
            }
        });

        TextButton back = new TextButton("Back", MyGdxGame.gameSkin);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onBack.run();
            }
        });

        //layout
        root.defaults().pad(5);
        root.add(title).colspan(3).padBottom(50).center().row();

        root.add(sfxLabel).left().left();
        root.add(sfxSlider).width(200);
        root.add(sfxValueLabel).right().row();

        //spacing between sliders
        root.row().padTop(30);

        root.add(musicLabel).left();
        root.add(musicSlider).width(200);
        root.add(musicValueLabel).right().row();

        root.add(back).colspan(3).center().padTop(20);
        root.setWidth(400);

        return root;
    }
}
