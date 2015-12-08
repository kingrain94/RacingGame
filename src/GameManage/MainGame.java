package GameManage;

import org.newdawn.slick.AppGameContainer;
import org.newdawn.slick.GameContainer;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.state.StateBasedGame;

public class MainGame extends StateBasedGame {

    public MainGame(String name) {
        super(name);
        addState(new PlayState(0));
    }

    @Override
    public void initStatesList(GameContainer gameContainer) throws SlickException {
        getState(0).init(gameContainer, this);
    }

    public static void main(String[] args) throws SlickException {
        AppGameContainer appGameContainer = new AppGameContainer(new MainGame("Sample Racing"), 800, 600, false);
        appGameContainer.setShowFPS(true);
        appGameContainer.setTargetFrameRate(60);
        appGameContainer.setAlwaysRender(true);
        appGameContainer.start();
    }
}
