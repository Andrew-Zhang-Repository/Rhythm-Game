
import bagel.*;
/**
 * Sets projection attributes and angular shift
 */
public class Projectiles{
    public int x=800;

    public int y=600;
    boolean hit=false;
    private DrawOptions rotation;
    public Enemy correspondingTarget;

    public int enemyX;
    public int enemyY;

    private Image arrow;
    Projectiles(Image image){
        this.arrow=image;
    }

    /**
     * Method sets angular shift for arrows in accordance to enemy location to the guardian.
     * @param x1 point one x
     * @param y1 point one y
     * @param x2 point two x
     * @param y2 point two y
     * @return void
     */

    public void rotationSet(int x1, int y1, int x2, int y2){
        int opposite=y2-y1;
        int adjacent=x2-x1;
        double angle = Math.atan2(opposite, adjacent);
        rotation = new DrawOptions();
        rotation.setRotation(angle);
    }

    public void draw(){
        arrow.draw(x,y,rotation);
    }

}
