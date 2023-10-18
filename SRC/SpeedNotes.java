import bagel.Image;
/**
 * Speed notes class stores notes attributes, and then contains a method which initiates itd effects
 */
public class SpeedNotes extends Arrow{
    private int x;
    private int y;
    private int frame;
    private Image image;
    public boolean up=false;

    public boolean down=false;
    public boolean hit=false;

    SpeedNotes(int positionX, int positionY, int frame, Image input){
        super(positionX,positionY,frame,input,false);
        this.x=positionX;
        this.y=positionY;
        this.frame=frame;
        this.image=input;
    }

    /**
     * Increase static variable by 1 when hit, and decrease when slow hit variation is hit.
     * @return void.
     */
    public void pressed(){
        if (hit && up){
            Lanes.arrowSpeed++;
        }

        if (hit && down){
            Lanes.arrowSpeed--;
        }
    }


}
