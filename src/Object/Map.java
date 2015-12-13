package Object;

import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

/**
 * Created by Nguyen on 12/8/2015.
 */
public class Map {
    private int x;
    private int y;
    private Image image;

    public Map(String imagePath) throws SlickException {
        this.image = new Image(imagePath);
        x = 0;
        y = 0;
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

    public Image getImage() {
        return image;
    }
}
