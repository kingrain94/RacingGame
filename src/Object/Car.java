package Object;

import Helper.Const;
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
    private boolean visible;

    public Car(int x, int y, String imagePath) throws SlickException {
        this.x = x;
        this.y = y;
        this.image = new Image(imagePath);
        this.rect = new Rectangle(x, y, image.getWidth(), image.getHeight());
        this.visible = true;
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

    public boolean isVisible() {
        return visible;
    }

    public void setVisible(boolean visible) {
        this.visible = visible;
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
        if (this.y - Const.MOVE_DY >= 0) {
            this.y -= Const.MOVE_DY;
            updateRectangle();
        }
    }
    public void moveDown() {
        if (this.y + Const.MOVE_DY <= Const.WINDOW_HEIGHT - image.getHeight()) {
            this.y += Const.MOVE_DY;
            updateRectangle();
        }
    }
    public void moveRight() {
        if (this.x + Const.MOVE_DX <= Const.WINDOW_WIDTH - image.getWidth()) {
            this.x += Const.MOVE_DX;
            updateRectangle();
        }
    }
    public void moveLeft() {
        if (this.x - Const.MOVE_DX >= 0) {
            this.x -= Const.MOVE_DX;
            updateRectangle();
        }
    }

    public void updateRectangle() {
        rect.setBounds(x, y, image.getWidth(), image.getHeight());
    }
}
