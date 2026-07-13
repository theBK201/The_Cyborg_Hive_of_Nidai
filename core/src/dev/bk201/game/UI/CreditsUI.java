package dev.bk201.game.UI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import dev.bk201.game.MyGdxGame;

public class CreditsUI {
    public static Table create(Game game, Runnable onBack) {
        MyGdxGame g = (MyGdxGame) game;

        Table root = new Table();
        root.setFillParent(true);
        root.center();
        root.defaults().width(500);

        Label title = new Label("Credits", MyGdxGame.gameSkin, "title");

        //layout
        root.add(title).colspan(2).padBottom(30).center().row();

        Label musicHeader = new Label("Music", MyGdxGame.gameSkin);
        Label musicLink = new Label("Retro Arcade Game Music - MondaMusic", MyGdxGame.gameSkin, "paragraph");
        makeLink(musicLink, "https://pixabay.com/users/mondamusic-54713575/");

        root.add(musicHeader).left().padBottom(5).row();
        root.add(musicLink).left().padBottom(20).row();

        Label soundHeader = new Label("Sound Effects", MyGdxGame.gameSkin);
        Label soundLink = new Label("Laser/Explosion/Game Over sounds - freesound_community", MyGdxGame.gameSkin, "paragraph");
        makeLink(soundLink, "https://pixabay.com/sound-effects/film-special-effects-game-over-arcade-6435/");

        root.add(soundHeader).left().padBottom(5).row();
        root.add(soundLink).left().padBottom(20).row();

        Label artworkHeader = new Label("Artwork", MyGdxGame.gameSkin);
        Label artworkLink = new Label("Explosion sprites Pack 1 - ansimuz", MyGdxGame.gameSkin, "paragraph");
        makeLink(artworkLink, "https://ansimuz.itch.io/explosion-animations-pack");

        Label artworkLink1 = new Label("Star Soldier UI Skin for LibGDX - RAY3K", MyGdxGame.gameSkin, "paragraph");
        makeLink(artworkLink1, "https://ray3k.wordpress.com/artwork/star-soldier-ui-skin-for-libgdx/");

        Label artworkLink2 = new Label("Game Assets - Magnific", MyGdxGame.gameSkin, "paragraph");
        makeLink(artworkLink2, "https://www.magnific.com/icon/spaceship_1702089#fromView=keyword&page=1&position=0&uuid=10895a52-6f6a-42f1-93bb-981b98e13ac3");

        TextButton back = new TextButton("Back", MyGdxGame.gameSkin);
        back.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeEvent event, Actor actor) {
                onBack.run();
            }
        });

        root.add(artworkHeader).left().padBottom(5).row();
        root.add(artworkLink).left().padBottom(20).row();
        root.add(artworkLink1).left().padBottom(20).row();
        root.add(artworkLink2).left().padBottom(20).row();
        root.add(new Label("Background And Homepage (AI Generated) - ChatGPT 5.5", MyGdxGame.gameSkin, "paragraph")).left().padBottom(20).row();
        root.add(back).colspan(2).center().padTop(20).center().width(150);

        return root;
    }

    private static void makeLink(Label label, String url) {
        label.setColor(MyGdxGame.gameSkin.getColor("sky-blue"));

        label.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                Gdx.net.openURI(url);
            }

            @Override
            public void enter(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                label.setColor(com.badlogic.gdx.graphics.Color.WHITE);
            }

            @Override
            public void exit(InputEvent event, float x, float y, int pointer, Actor fromActor) {
                label.setColor(MyGdxGame.gameSkin.getColor("sky-blue"));
            }
        });
    }
}
