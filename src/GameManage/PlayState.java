package GameManage;

import GameObject.Car;
import org.newdawn.slick.*;
import org.newdawn.slick.state.BasicGameState;
import org.newdawn.slick.state.StateBasedGame;

import java.io.IOException;
import java.net.*;

public class PlayState extends BasicGameState {

    private int id;
    private Image map;

    private int mapY;
    private Car myCar;
    private Car rivalCar;
    private DatagramSocket sendSocket;
    String pos = "Initial";

    private int sendPort = 10001;
    private int receivePort = 10000;

    public PlayState(int id) {
        super();
        this.id = id;

        mapY = 0;
        try {
            sendSocket = new DatagramSocket();
        } catch (SocketException e) {
            e.printStackTrace();
        }

        (new Thread() {
            @Override
            public void run() {
                try {
                    DatagramSocket socket = new DatagramSocket(receivePort);

                    while (true) {
                        byte[] bytes = new byte[256];
                        DatagramPacket packet = new DatagramPacket(bytes, bytes.length);
                        socket.receive(packet);
                        pos = new String(packet.getData());
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
        map = new Image("res/track.png");

        if (sendPort == 10001) {
            myCar = new Car(300, 300, "res/car1.png");
            rivalCar = new Car(400, 300, "res/car2.png");
        } else {
            myCar = new Car(400, 300, "res/car1.png");
            rivalCar = new Car(300, 300, "res/car2.png");
        }
    }

    @Override
    public void render(GameContainer gameContainer, StateBasedGame stateBasedGame, Graphics graphics) throws SlickException {
        graphics.drawImage(map, 0, mapY - 600);
        graphics.drawImage(map, 0, mapY);

        graphics.drawString(pos, 100, 100);

        graphics.drawImage(myCar.getImage(), myCar.getX(), myCar.getY());
        graphics.draw(myCar.getRect());

        graphics.drawImage(rivalCar.getImage(), rivalCar.getX(), rivalCar.getY());
        graphics.draw(rivalCar.getRect());
    }

    @Override
    public void update(GameContainer gameContainer, StateBasedGame stateBasedGame, int i) throws SlickException {
        Input input = gameContainer.getInput();

        mapY += 5;
        if (mapY > 600) {
            mapY = 0;
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

        if (pos.contains(",")) {
            rivalCar.setX(Integer.parseInt(pos.substring(0, pos.indexOf(","))));
            rivalCar.setY(Integer.parseInt(pos.substring(pos.indexOf(","))));
        }
    }
}
