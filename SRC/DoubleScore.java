import bagel.*;
/**
 * Class that initiates double score mode for 480 frames
 */
public class DoubleScore extends Arrow{
    private int x;
    private int y;
    private int frame;
    private Image image;
    public boolean hit=false;


    DoubleScore(int positionX, int positionY, int frame, Image input){
        super(positionX,positionY,frame,input,false);
        this.x=positionX;
        this.y=positionY;
        this.frame=frame;
        this.image=input;
    }

    /**
     * Doubles static score constants
     * @return void
     */
    public void scoreEdit(){
        if (hit) {
            KeyPressHandle.perfect *= 2;
            KeyPressHandle.good *= 2;
            KeyPressHandle.bad *= 2;
            KeyPressHandle.miss *= 2;
        }
    }


}
