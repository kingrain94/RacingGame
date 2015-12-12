package GameManage;

import GameObject.Bomb;
import GameObject.Car;
import GameObject.Map;
import Helper.Constants;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.Random;

public class PlayState extends BasicGameState {

    private int id;

    private Map map;
    private Car myCar;
    private Car rivalCar;
    private ArrayList<Bomb> bombs;

    String pos = "Initial";

    private DatagramSocket sendSocket;
    private DatagramSocket receiveSocket;

    private int sendPort = 10001;
    private int receivePort = 10000;

    public PlayState(int id) {
        super();
        this.id = id;

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

                            if (rivalCar != null) {
                                rivalCar.setX(rivalX);
                                rivalCar.setY(rivalY);
                            }
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }).start();
    }

    @Override
    public int getID() {
        return id;
    }

    @Override
    public void init(GameContainer gameContainer, StateBasedGame stateBasedGame) throws SlickException {
        map = new Map(Constants.MAP_IMAGE_PATH);

        if (sendPort == 10000) {
            myCar = new Car(300, 300, Constants.MY_CAR_IMAGE_PATH);
            rivalCar = new Car(400, 300, Constants.RIVAL_CAR_IMAGE_PATH);
        } else {
            myCar = new Car(400, 300, Constants.MY_CAR_IMAGE_PATH);
            rivalCar = new Car(300, 300, Constants.RIVAL_CAR_IMAGE_PATH);
        }

        Random random = new Random();
        bombs = new ArrayList<>();
        for (int i = 0; i < 5; i ++) {
            bombs.add(new Bomb(random.nextInt(Constants.WINDOW_WIDTH), random.nextInt(Constants.WINDOW_HEIGHT), Constants.BOMB_IMAGE_PATH));
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(map.getImage(), 0, map.getY() - Constants.WINDOW_HEIGHT);
        graphics.drawImage(map.getImage(), 0, map.getY());

        graphics.drawImage(myCar.getImage(), myCar.getX(), myCar.getY());
        graphics.draw(myCar.getRect());

        graphics.drawImage(rivalCar.getImage(), rivalCar.getX(), rivalCar.getY());
        graphics.draw(rivalCar.getRect());

        for (Bomb bomb : bombs) {
            graphics.drawImage(bomb.getImage(), bomb.getX() - bomb.getR(), bomb.getY() - bomb.getR());
            graphics.draw(bomb.getCircle());
        }
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input input = gameContainer.getInput();

        for (Bomb bomb : bombs) {
            bomb.setY(bomb.getY() + 5);
            if (bomb.getY() > 600) {
                bomb.setY(0);
            }
        }
        map.setY(map.getY() + 5);
        if (map.getY() > 600) {
            map.setY(0);
        }

        if (input.isKeyDown(Input.KEY_UP)) {
            myCar.moveUp();
        }
        if (input.isKeyDown(Input.KEY_DOWN)) {
            myCar.moveDown();
        }
        if (input.isKeyDown(Input.KEY_RIGHT)) {
            myCar.moveRight();
        }
        if (input.isKeyDown(Input.KEY_LEFT)) {
            myCar.moveLeft();
        }

        String s = myCar.getX() + "," + myCar.getY();
        byte[] buff = s.getBytes();
        try {
            DatagramPacket packet = new DatagramPacket(buff, buff.length, InetAddress.getByName("localhost"), sendPort);
            sendSocket.send(packet);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
