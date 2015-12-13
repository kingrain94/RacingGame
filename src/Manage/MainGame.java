package Manage;

import Helper.Const;
import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MainGame extends StateBasedGame {
    private int mScore;

    public MainGame(String name) {
        super(name);
        addState(new MenuState(Const.MENU_STATE));
        addState(new PlayState(Const.SINGLE_PLAY_STATE));
        addState(new PlayState(Const.MULTI_PLAY_STATE));
        addState(new ResultState(Const.RESULT_STATE));
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        getState(Const.MENU_STATE).init(gameContainer, this);
        getState(Const.SINGLE_PLAY_STATE).init(gameContainer, this);
        getState(Const.MULTI_PLAY_STATE).init(gameContainer, this);
        getState(Const.RESULT_STATE).init(gameContainer, this);

        enterState(Const.MENU_STATE);
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer appGameContainer = new AppGameContainer(new MainGame("Sample Racing"), Const.WINDOW_WIDTH,
                Const.WINDOW_HEIGHT, false);
        appGameContainer.setShowFPS(true);
        appGameContainer.setTargetFrameRate(60);
        appGameContainer.setAlwaysRender(true);
        appGameContainer.start();
    }

    public int getScore() {
        return mScore;
    }

    public void setScore(int mScore) {
        this.mScore = mScore;
    }
}
