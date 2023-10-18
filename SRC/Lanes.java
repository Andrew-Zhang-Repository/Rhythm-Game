import bagel.*;
import java.util.ArrayList;
/**
 * Class is responsible for drawing every single note type
 */
public class Lanes {
    public static int arrowSpeed=2;
    private ArrayList<Arrow> List;
    private int frameCount;
    private int scoreCount;
    private String direction;
    private KeyPressHandle keys;


    Lanes(ArrayList<Arrow> arrowList, int frameCount, String direction) {
        this.scoreCount=frameCount;
        this.List = arrowList;
        this.frameCount = frameCount;
        this.direction=direction;
    }


    /**
     * Draws notes depending on it's class.
     *
     * @param input testing for player input later for keypress handling.
     * @return void.
     */
    public void drawNotes(Input input) {
        for (int i = 0; i < List.size(); i++) {
            if (List.get(i).frame == frameCount && !List.get(i).spawned) {
                if (List.get(i) instanceof HoldNotes){
                    HoldNotes hold = (HoldNotes)List.get(i);
                    hold.spawned=true;
                }
                List.get(i).spawned = true;
            }

            if(List.get(i).spawned) {
                keys=new KeyPressHandle(List,List.get(i),direction);
                Arrow note=List.get(i);
                Enemy.mark(note);
                note.draw();
                note.y += arrowSpeed;

                if (!(note instanceof HoldNotes) && !(note instanceof DoubleScore) && !(note instanceof SpeedNotes)){
                    keys.demonMarkCheck();
                    keys.markCheck();
                    keys.normalNotes(input);
                    keys.noHit();
                }
                else if (note instanceof HoldNotes){
                    keys.demonMarkCheck();
                    keys.markCheck();
                    keys.holdNotes(input);
                    keys.noHitHold();
                }
                else{
                    keys.specialNotes(input);
                    keys.noHit();
                }

            }
        }
    }

}

