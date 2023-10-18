import bagel.*;
import java.util.ArrayList;
/**
 * Class handles every single key press and handles scoring and implements some effects for notes.
 */
public class KeyPressHandle {
    private int stationaryPoint=657;
    public static int frameCount = 0;
    public static int perfect = 10;
    public static int good = 5;
    public static int bad = -1;
    public static int miss = -5;
    private ArrayList<Arrow> List;
    private Arrow note;
    private String direction;
    private TotalScore score;
    private int stationaryY = 657;

    public static String messageScore;



    KeyPressHandle(ArrayList<Arrow> List, Arrow note, String direction) {
        this.List = List;
        this.note = note;
        this.direction = direction;
    }

    // Counts frames from ShadowDance's update method //
    public static void frameCounter() {
        frameCount++;
    }

    public void demonMarkCheck(){
        if (note.markedForDemon){
            List.remove(note);
        }
    }

    public void markCheck() {
        if (note.markedForBomb) {
            List.remove(note);
        }
    }

    /**
     *
     * @param input, this parameter checks for press.
     * @return void.
     */
    public void normalNotes(Input input) {
        int distance = Math.abs(stationaryY - note.y);

        if (input.wasPressed(Keys.LEFT) && note.spawned && direction.equalsIgnoreCase("left")
                && !(note instanceof HoldNotes) && !(note instanceof BombNotes)) {
            note.hit = true;
            Score(distance);
        }
        if (input.wasPressed(Keys.LEFT) && note.spawned && direction.equalsIgnoreCase("left")
                && !(note instanceof HoldNotes) && (note instanceof BombNotes)) {
            note.hit = true;
            specialScore(distance);
        }


        if (input.wasPressed(Keys.RIGHT) && note.spawned && direction.equalsIgnoreCase("right")
                && !(note instanceof HoldNotes) && !(note instanceof BombNotes)) {
            note.hit = true;
            Score(distance);
        }
        if (input.wasPressed(Keys.RIGHT) && note.spawned && direction.equalsIgnoreCase("right")
                && !(note instanceof HoldNotes) && (note instanceof BombNotes)) {
            note.hit = true;
            specialScore(distance);
        }


        if (input.wasPressed(Keys.UP) && note.spawned && direction.equalsIgnoreCase("up")
                && !(note instanceof HoldNotes) && !(note instanceof BombNotes)) {
            note.hit = true;
            Score(distance);
        }
        if (input.wasPressed(Keys.UP) && note.spawned && direction.equalsIgnoreCase("up")
                && !(note instanceof HoldNotes) && (note instanceof BombNotes)) {
            note.hit = true;
            specialScore(distance);
        }


        if (input.wasPressed(Keys.DOWN) && note.spawned && direction.equalsIgnoreCase("down")
                && !(note instanceof HoldNotes) && !(note instanceof BombNotes)) {
            note.hit = true;
            Score(distance);
        }


        if (input.wasPressed(Keys.DOWN) && note.spawned && direction.equalsIgnoreCase("down")
                && !(note instanceof HoldNotes) && (note instanceof BombNotes)) {
            note.hit = true;
            specialScore(distance);
        }
    }

    /**
     *
     * @param input, this parameter checks for press but for special notes.
     * @return void.
     */
    public void specialNotes(Input input) {
        int distance = Math.abs(stationaryY - note.y);

        if (input.wasPressed(Keys.SPACE) && note.spawned && direction.equalsIgnoreCase("special")
                && (note instanceof SpeedNotes)) {
            note.hit = true;
            specialScore(distance);
        }

        if (input.wasPressed(Keys.SPACE) && note.spawned && direction.equalsIgnoreCase("special")
                && (note instanceof DoubleScore)) {
            note.hit = true;
            specialScore(distance);
        }

        if (input.wasPressed(Keys.SPACE) && note.spawned && direction.equalsIgnoreCase("special")
                && (note instanceof BombNotes)) {
            note.hit = true;
            specialScore(distance);
        }
    }

    static boolean leftHold=false;
    static boolean rightHold=false;
    static boolean upHold=false;
    static boolean downHold=false;

    /**
     *
     * @param input, this parameter checks for press for hold notes.
     * @return void.
     */
    public void holdNotes(Input input) {
        int distanceBottom= Math.abs(stationaryY-(note.y+82));
        int distanceTop= Math.abs(stationaryY-(note.y-82));

        if (input.wasPressed(Keys.LEFT) && note.spawned && direction.equalsIgnoreCase("LEFT")
                && (note instanceof HoldNotes)) {
            ScoreNoRemove(distanceBottom);
            note.hit = true;
            leftHold=true;
        }
        if (leftHold && input.wasReleased(Keys.LEFT) && note.spawned && direction.equalsIgnoreCase("left")
                && (note instanceof HoldNotes) && note.hit) {
            leftHold=false;
            Score(distanceTop);
        }

        if (input.wasPressed(Keys.RIGHT) && note.spawned && direction.equalsIgnoreCase("right")
                && (note instanceof HoldNotes)) {
            ScoreNoRemove(distanceBottom);
            note.hit = true;
            rightHold=true;
        }
        if (rightHold && input.wasReleased(Keys.RIGHT) && note.spawned && direction.equalsIgnoreCase("right")
                && (note instanceof HoldNotes) && note.hit) {
            rightHold=false;
            Score(distanceTop);
        }

        if (input.wasPressed(Keys.UP) && note.spawned && direction.equalsIgnoreCase("up")
                && (note instanceof HoldNotes)) {
            ScoreNoRemove(distanceBottom);
            note.hit = true;
            upHold=true;
        }
        if (upHold && input.wasReleased(Keys.UP) && note.spawned && direction.equalsIgnoreCase("up")
                && (note instanceof HoldNotes) && note.hit) {
            upHold=false;
            Score(distanceTop);
        }

        if (input.wasPressed(Keys.DOWN) && note.spawned && direction.equalsIgnoreCase("down")
                && (note instanceof HoldNotes)) {
            ScoreNoRemove(distanceBottom);
            note.hit = true;
            downHold=true;
        }
        if (downHold && input.wasReleased(Keys.DOWN) && note.spawned && direction.equalsIgnoreCase("down")
                && (note instanceof HoldNotes) && note.hit) {
            downHold=false;
            Score(distanceTop);
        }


    }

    /**
     *
     * every method below is responsible for note removal and note scoring
     */


    public void noHit(){
        if(!note.hit && note.y>=768 && !(note instanceof HoldNotes)){
            messageScore="miss";
            wasPressed=true;
            score.total+=miss;
            List.remove(note);
        }
        if(note.hit && note.y>=768 && !(note instanceof HoldNotes)){
            messageScore="miss";
            wasPressed=true;
            score.total+=miss;
            List.remove(note);
        }
    }

    public void noHitHold(){
        if(!note.hit && note.y>=768+82 && (note instanceof HoldNotes)){
            messageScore="miss";
            wasPressed=true;
            score.total+=miss;
            List.remove(note);
        }
        if(note.hit && note.y>=768+82 && (note instanceof HoldNotes)){
            messageScore="miss";
            wasPressed=true;
            score.total+=miss;
            List.remove(note);
        }
    }

    private void ScoreNoRemove(int distance){
        if (distance<=15){
            messageScore="perfect";
            wasPressed=true;
            score.total+=perfect;
        }

        else if(distance<=50 && distance>15){
            messageScore="good";
            wasPressed=true;
            score.total+=good;
        }

        else if (distance<=100 && distance>50){
            messageScore="bad";
            wasPressed=true;
            score.total+=bad;
        }

        else if (distance<=200 && distance>100 && note.hit){
            messageScore="miss";
            wasPressed=true;
            score.total+=miss;
        }

    }

    public static boolean wasPressed=false;

    private void Score(int distance){


        if (distance<=15 && note.hit){
            messageScore="perfect";
            wasPressed=true;
            List.remove(note);
            score.total+=perfect;
        }
        else if(distance<=50 && distance>15 && note.hit){
            messageScore="good";
            wasPressed=true;
            List.remove(note);
            score.total+=good;
        }
        else if (distance<=100 && distance>50 && note.hit){
            messageScore="bad";
            wasPressed=true;
            List.remove(note);
            score.total+=bad;
        }
        else if (distance<=200 && distance>100 && note.hit){
            messageScore="miss";
            wasPressed=true;
            List.remove(note);
            score.total+=miss;
        }
    }
    private static boolean reset=false;
    public static void resetter(){
        if(frameCount>=480 && reset){
            perfect=10;
            good=5;
            bad=-1;
            miss=-5;
            reset=false;
        }
    }



    private void specialScore(int distance){
        if (distance<=50 && note.hit){
            if ((note instanceof SpeedNotes)) {
                score.total+=15;
                SpeedNotes test= (SpeedNotes) note;

                if(test.up){
                    messageScore="speed up";
                    wasPressed=true;
                }
                else{
                    messageScore="slow down";
                    wasPressed=true;
                }

                test.hit=true;
                test.pressed();
                List.remove(note);
            }
            else if((note instanceof DoubleScore)){
                frameCount=0;
                score.total+=15;
                DoubleScore test= (DoubleScore) note;
                test.hit=true;
                reset=true;
                if (frameCount<=480){
                    test.scoreEdit();
                }
                List.remove(note);
            }
            else if ((note instanceof BombNotes)){
                messageScore="lane clear";
                wasPressed=true;
                BombNotes bombNote = (BombNotes)note;
                bombNote.checkOnScreen(List);
                List.remove(note);

            }
        }

    }


}
