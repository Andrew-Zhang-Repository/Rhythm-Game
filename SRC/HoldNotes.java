import bagel.Image;
/**
 * Basic hold notes class that is similiar to the Arrow superclass
 */

public class HoldNotes extends Arrow{
    private int x;
    private int y;

    private int frame;
    private Image image;
    private boolean holdStart=false;
    private boolean release=false;
    public boolean spawned=false;

    private String ID;

    HoldNotes(int positionX, int positionY, int frame, Image input,String ID){
        super(positionX,positionY,frame,input,false);
        this.x=positionX;
        this.y=positionY;
        this.frame=frame;
        this.image=input;
        this.ID=ID;
    }


}
