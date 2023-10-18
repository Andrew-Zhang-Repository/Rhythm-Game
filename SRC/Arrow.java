
/**
 * Basically a class that represents a note and its attributes for a game.
 */
import bagel.*;
public class Arrow {
    public int x;
    public int y;
    public int frame;
    private Image image;
    public boolean hit=false;
    private boolean isHold=false;
    public boolean spawned=false;
    public boolean markedForBomb=false;
    public boolean markedForDemon=false;



    Arrow(int positionX, int positionY, int frame, Image input, boolean arrowType){
        this.x=positionX;
        this.y=positionY;
        this.frame=frame;
        this.image=input;
        this.isHold=arrowType;
    }

    /**
     * Draw method:
     * @return void.
     */
    public void draw(){image.draw(x,y);
    }

}
