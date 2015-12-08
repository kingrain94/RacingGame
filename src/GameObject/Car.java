package GameObject;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Rectangle;

/**
 * Created by Nguyen on 12/8/2015.
 */
public class Car {
    private int x;
    private int y;
    private Rectangle rect;
    private Image image;

    public Car(int x, int y, String imagePath) throws SlickException {
        this.x = x;
        this.y = y;
        this.image = new Image(imagePath);
        this.rect = new Rectangle(x, y, image.getWidth(), image.getHeight());
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
    }

    public Rectangle getRect() {
        return rect;
    }

    public void setRect(Rectangle rect) {
        this.rect = rect;
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public void moveUp() {
        this.y -= 10;
        rect.setBounds(x, y, image.getWidth(), image.getHeight());
    }
    public void moveDown() {
        this.y += 10;
        rect.setBounds(x, y, image.getWidth(), image.getHeight());
    }
    public void moveRight() {
        this.x += 10;
        rect.setBounds(x, y, image.getWidth(), image.getHeight());
    }
    public void moveLeft() {
        this.x -= 10;
        rect.setBounds(x, y, image.getWidth(), image.getHeight());
    }
}
