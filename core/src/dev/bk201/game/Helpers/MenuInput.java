package dev.bk201.game.Helpers;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.ui.Cell;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;

public class MenuInput {

    private final TextButton[] buttons;
    private int selectedIndex;
    private Cell<?>[] selectorCells;
    private Actor selector;

    public MenuInput(TextButton... buttons) {
        this.buttons = buttons;
        selectedIndex = 0;
    }

    public void update() {
        if (Gdx.input.isKeyJustPressed(Input.Keys.DOWN)) {
            selectedIndex = (selectedIndex + 1) % buttons.length;
            updateSelection();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.UP)) {
            selectedIndex--;
            if (selectedIndex < 0) {
                selectedIndex = buttons.length - 1;
            }
            updateSelection();
        }

        if (Gdx.input.isKeyJustPressed(Input.Keys.ENTER) ||
                Gdx.input.isKeyJustPressed(Input.Keys.SPACE)) {
            activateSelection();
        }
    }

    private void updateSelection() {
        if (selector == null || selectorCells == null)
            return;

        selector.setVisible(false);

        for (Cell<?> selectorCell : selectorCells) {
            selectorCell.setActor(null);
        }

        selectorCells[selectedIndex].setActor(selector);
        selector.setVisible(true);
    }

    private void activateSelection() {
        buttons[selectedIndex].fire(new ChangeListener.ChangeEvent());
    }

    public void setSelector(Actor selector, Cell<?>... selectorCells) {
        this.selector = selector;
        this.selectorCells = selectorCells;
        updateSelection();
    }

    public void selectFirst() {
        selectedIndex = 0;
        updateSelection();
    }

    public int getSelectedIndex() {
        return selectedIndex;
    }

    public TextButton getSelectedButton() {
        return buttons[selectedIndex];
    }
}
