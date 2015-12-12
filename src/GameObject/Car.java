package GameObject;

import Helper.Constants;
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
        updateRectangle();
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        updateRectangle();
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
        if (this.y - 10 >= 0) {
            this.y -= 10;
            updateRectangle();
        }
    }
    public void moveDown() {
        if (this.y + 10 <= Constants.WINDOW_HEIGHT - image.getHeight()) {
            this.y += 10;
            updateRectangle();
        }
    }
    public void moveRight() {
        if (this.x + 10 <= Constants.WINDOW_WIDTH - image.getWidth()) {
            this.x += 10;
            updateRectangle();
        }
    }
    public void moveLeft() {
        if (this.x - 10 >= 0) {
            this.x -= 10;
            updateRectangle();
        }
    }

    public void updateRectangle() {
        rect.setBounds(x, y, image.getWidth(), image.getHeight());
    }
}
