package Manage;

import Object.Bomb;
import Object.Coin;
import Object.Car;
import Object.Map;
import Helper.Const;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

public class PlayState extends BasicGameState {
    private StateBasedGame mGame;
    private GameContainer mContainer;
    private int mStateId;
    private Map mMap;
    private Car mMyCar;
    private Car mRivalCar;
    private ArrayList<Bomb> mBombs;
    private ArrayList<Coin> mCoins;

    String pos = "Initial";

    private DatagramSocket sendSocket;
    private DatagramSocket receiveSocket;

    private int sendPort = 10001;
    private int receivePort = 10000;

    public PlayState(int id) {
        super();
        this.mStateId = id;

        if (id == Const.MULTI_PLAY_STATE) {
            try {
                sendSocket = new DatagramSocket();
                receiveSocket = new DatagramSocket(receivePort);
            } catch (SocketException e) {
                e.printStackTrace();
            }
            (new Thread() {
                @Override
                public void run() {
                    try {
                        while (true) {
                            byte[] bytes = new byte[256];
                            DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
                            receiveSocket.receive(packet);
                            pos = new String(packet.getData());

                            if (pos.contains(",")) {
                                int rivalX = Integer.parseInt(pos.substring(0, pos.indexOf(",")).trim());
                                int rivalY = Integer.parseInt(pos.substring(pos.indexOf(",") + 1).trim());

                                if (mRivalCar != null) {
                                    mRivalCar.setX(rivalX);
                                    mRivalCar.setY(rivalY);
                                }
                            }
                        }
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    @Override
    public int getID() {
        return mStateId;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        mGame = stateBasedGame;
        mContainer = gameContainer;
        ((MainGame) mGame).setScore(0);

        mMap = new Map(Const.MAP_IMAGE_PATH);

        if (mStateId == Const.MULTI_PLAY_STATE) {
            if (sendPort == 10000) {
                mMyCar = new Car(300, 300, Const.MY_CAR_IMAGE_PATH);
                mRivalCar = new Car(400, 300, Const.RIVAL_CAR_IMAGE_PATH);
            } else {
                mMyCar = new Car(400, 300, Const.MY_CAR_IMAGE_PATH);
                mRivalCar = new Car(300, 300, Const.RIVAL_CAR_IMAGE_PATH);
            }
        } else {
            mMyCar = new Car(300, 300, Const.MY_CAR_IMAGE_PATH);
        }

        Random random = new Random();
        mBombs = new ArrayList<>();
        for (int i = 0; i < 5; i ++) {
            mBombs.add(new Bomb(random.nextInt(Const.WINDOW_WIDTH), random.nextInt(Const.WINDOW_HEIGHT), Const.BOMB_IMAGE_PATH));
        }
        mCoins = new ArrayList<>();
        for (int i = 0; i < 5; i ++) {
            mCoins.add(new Coin(random.nextInt(Const.WINDOW_WIDTH), random.nextInt(Const.WINDOW_HEIGHT), Const.COIN_IMAGE_PATH));
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(mMap.getImage(), 0, mMap.getY() - Const.WINDOW_HEIGHT);
        graphics.drawImage(mMap.getImage(), 0, mMap.getY());
        for (Bomb bomb : mBombs) {
            graphics.drawImage(bomb.getImage(), bomb.getX() - bomb.getR(), bomb.getY() - bomb.getR());
        }
        for (Coin coin : mCoins) {
            if (coin.isVisible()) {
                graphics.drawImage(coin.getImage(), coin.getX() - coin.getR(), coin.getY() - coin.getR());
            }
        }

        graphics.drawImage(mMyCar.getImage(), mMyCar.getX(), mMyCar.getY());

        if (mStateId == Const.MULTI_PLAY_STATE) {
            graphics.drawImage(mRivalCar.getImage(), mRivalCar.getX(), mRivalCar.getY());
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input input = gameContainer.getInput();

        for (Bomb bomb : mBombs) {
            bomb.setY(bomb.getY() + 5);
            if (bomb.getY() > 600) {
                bomb.setY(0);
            }
        }
        for (Coin coin : mCoins) {
            coin.setY(coin.getY() + 5);
            if (coin.getY() > 600) {
                coin.setY(0);
            }
        }
        mMap.setY(mMap.getY() + 5);
        if (mMap.getY() > 600) {
            mMap.setY(0);
        }

        if (input.isKeyDown(Input.KEY_UP)) {
            mMyCar.moveUp();
        }
        if (input.isKeyDown(Input.KEY_DOWN)) {
            mMyCar.moveDown();
        }
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            mMyCar.moveRight();
        }
        if (input.isKeyDown(Input.KEY_LEFT)) {
            mMyCar.moveLeft();
        }

        if (isCollideBomb()) {
            mGame.enterState(Const.RESULT_STATE);
        }
        checkCoinCollided();

        if (mStateId == Const.MULTI_PLAY_STATE) {
            String s = mMyCar.getX() + "," + mMyCar.getY();
            byte[] buff = s.getBytes();
            try {
                DatagramPacket packet = new DatagramPacket(buff, buff.length, InetAddress.getByName("localhost"), sendPort);
                sendSocket.send(packet);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void keyReleased(int key, char c) {
        switch (key) {
            case Input.KEY_ESCAPE:
                mGame.enterState(Const.MENU_STATE);
                break;
        }
    }

    public boolean isCollideBomb() {
        for (Bomb bomb : mBombs) {
            if (mMyCar.getRect().intersects(bomb.getCircle())) return true;
        }
        return false;
    }

    public void checkCoinCollided() {
        for (Coin coin : mCoins) {
            if (coin.isVisible() && mMyCar.getRect().intersects(coin.getCircle())) {
                ((MainGame) mGame).setScore(((MainGame) mGame).getScore() + 1);
                coin.setVisible(false);
            }
        }
    }

}
