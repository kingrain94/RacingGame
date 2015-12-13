package Object;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;
import org.newdawn.slick.geom.Circle;

/**
 * Created by Nguyen on 12/12/2015.
 */
public class Bomb {
    private int x;
    private int y;
    private int r;
    private Image image;
    private Circle circle;

    public Bomb(int x, int y, String imagePath) throws SlickException {
        this.x = x;
        this.y = y;
        this.image = new Image(imagePath);
        this.r = image.getWidth() / 2;
        this.circle = new Circle(x, y, r);
    }

    public int getX() {
        return x;
    }

    public void setX(int x) {
        this.x = x;
        updateCirCle();
    }

    public int getY() {
        return y;
    }

    public void setY(int y) {
        this.y = y;
        updateCirCle();
    }

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
        updateCirCle();
    }

    public Image getImage() {
        return image;
    }

    public void setImage(Image image) {
        this.image = image;
    }

    public Circle getCircle() {
        return circle;
    }

    public void setCircle(Circle circle) {
        this.circle = circle;
    }

    public void updateCirCle() {
        circle.setCenterX(this.x);
        circle.setCenterY(this.y);
        circle.setRadius(this.r);
    }
}
