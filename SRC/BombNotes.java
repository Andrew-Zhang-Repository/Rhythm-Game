import bagel.Image;

import java.util.ArrayList;
/**
 * Class that initiates bomb notes effects and it's attributes
 */
public class BombNotes extends Arrow{
    private int x;
    private int y;
    private int frame;
    private Image image;


    BombNotes(int positionX, int positionY, int frame, Image input){
        super(positionX,positionY,frame,input,false);
        this.x=positionX;
        this.y=positionY;
        this.frame=frame;
        this.image=input;
    }

    /**
     * Marks all notes that is on the screen for bomb removal
     * @param List takes in a lane list and marks notes for removal.
     * @return void.
     */
    public void checkOnScreen(ArrayList<Arrow> List){
            for (Arrow arrow : List) {
                if (arrow.spawned) {
                    arrow.markedForBomb=true;
                }
            }
    }
}
