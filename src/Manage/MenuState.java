package Manage;

import Helper.Const;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Input;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

/**
 * Created by Nguyen on 12/13/2015.
 */
public class MenuState extends BasicGameState {
    private StateBasedGame mGame;
    private GameContainer mContainer;
    private int id;

    public MenuState(int id) {
        super();
        this.id = id;
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        mGame = stateBasedGame;
        mContainer = gameContainer;
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawString("1. Single Play Game", 50, 100);
        graphics.drawString("2. Multi Play Game", 50, 120);
        graphics.drawString("3. Quit", 50, 140);
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
    }

    @Override
    public void keyReleased(int key, char c) {
        switch (key) {
            case Input.KEY_1:
                mGame.enterState(Const.SINGLE_PLAY_STATE);
                break;
            case Input.KEY_2:
                mGame.enterState(Const.MULTI_PLAY_STATE);
                break;
            case Input.KEY_3:
                mContainer.exit();
                break;
        }
    }
}
