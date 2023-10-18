import java.io.File;
import bagel.*;
import bagel.util.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;


/**
 * Runnable Code for SWEN20003 Project 2, Semester 2, 2023
 * Please enter your name below
 * Andrew Zhang
 */

public class ShadowDance extends AbstractGame {
    private final static int WINDOW_WIDTH = 1024;
    private final static int WINDOW_HEIGHT = 768;
    private final static String GAME_TITLE = "SHADOW DANCE";




    //Make all game entities//
    private final Image BACKGROUND_IMAGE = new Image("res/background.png");
    private Font titleScreen;
    private Font startKey;
    private Font instruction;
    private Image laneLeft;
    private Image laneRight;
    private Image laneUp;
    private Image laneDown;
    private Image laneSpecial;
    private Image leftArrow;
    private Image rightArrow;
    private Image upArrow;
    private Image downArrow;
    private Image leftArrowHold;
    private Image rightArrowHold;
    private Image upArrowHold;
    private Image downArrowHold;
    private Image bomb;
    private Image timesTwo;
    private Image speedUp;
    private Image speedDown;
    private Image enemy;
    private Image guardian;
    private Image projectile;


    private Font score;
    private Font clear;
    private Font failed;
    private Font scoreWriting;




    //title screen positions//
    private int titleX=220;
    private int titleY=250;
    private int startX=320;
    private int startY=440;
    private int instructionX=385+55;
    private int instructionY=465+50;
    private int numX=385;
    private int numY=465;
    private int clearX=368;
    private int clearY=WINDOW_HEIGHT/2;
    private double tryX=294.5;
    private int tryY= WINDOW_HEIGHT/2;
    private int returnX=155;
    private int returnY=WINDOW_HEIGHT/2+140;




    // Arrays and points for calculations//
    private int laneY=384;
    private int duration=30;
    private Point leftPos;
    private Point rightPos;
    private Point upPos;
    private Point downPos;
    private Point specialPos;
    private Arrow[] arrowArray;
    private ArrayList<Arrow> leftLane;
    private ArrayList<Arrow> rightLane;
    private ArrayList<Arrow> upLane;
    private ArrayList<Arrow> downLane;
    private ArrayList<Arrow> specialLane;


    private double arrowY = 100;
    private int level1Score=150;
    private int level2Score=400;
    private int level3Score=350;
    private int holdY=24;
    private int frameCount = 0;



    //boolean variables to control game progression//
    private boolean startGame = false;
    private boolean gameOver = false;


    //level constants//
    private int LEVEL1=0;
    private int LEVEL2=1;
    private int LEVEL3=2;




    //Initiate all font/drawings before actually place them on screen//
    public ShadowDance() {
        super(WINDOW_WIDTH, WINDOW_HEIGHT, GAME_TITLE);
        titleScreen = new Font("res/FSO8BITR.TTF", 60);
        startKey = new Font("res/FSO8BITR.TTF", 24);
        instruction = new Font("res/FSO8BITR.TTF", 24);


        laneLeft = new Image("res/laneLeft.png");
        laneRight = new Image("res/laneRight.png");
        laneUp = new Image("res/laneUp.png");
        laneDown = new Image("res/laneDown.png");
        laneSpecial = new Image("res/laneSpecial.png");


        leftArrow = new Image("res/noteLeft.png");
        rightArrow = new Image("res/noteRight.png");
        upArrow = new Image("res/noteUP.png");
        downArrow = new Image("res/noteDown.png");


        leftArrowHold = new Image("res/holdNoteLeft.png");
        rightArrowHold = new Image("res/holdNoteRight.png");
        upArrowHold = new Image("res/holdNoteUp.png");
        downArrowHold = new Image("res/holdNoteDown.png");
        bomb= new Image("res/noteBomb.png");
        timesTwo= new Image("res/note2x.png");
        speedUp= new Image("res/noteSpeedUp.png");
        speedDown= new Image("res/noteSlowDown.png");


        enemy=new Image("res/enemy.png");
        guardian=new Image("res/guardian.png");
        projectile= new Image("res/arrow.png");


        score = new Font("res/FSO8BITR.TTF", 30);
        clear = new Font("res/FSO8BITR.TTF", 64);
        failed = new Font("res/FSO8BITR.TTF", 64);
        scoreWriting = new Font("res/FSO8BITR.TTF", 40);
    }




    /**
     * Method to read in CSV files that contain the keyword "levels"
     *void parameters
     */
    private ArrayList<ArrayList<String>> csvUnbox(){
        ArrayList<String> filenames = new ArrayList<String>();
        ArrayList<ArrayList<String>> nestedList = new ArrayList<ArrayList<String>>();
        ArrayList<ArrayList<String>> lanePositions = new ArrayList<ArrayList<String>>();
        File directory = new File("res");
        File[] csvFiles = directory.listFiles((dir, name) -> name.endsWith(".csv"));
        if (csvFiles!=null){
            for (File file: csvFiles){
                if (file.getName().contains("level")) {
                    filenames.add("res/"+file.getName());
                }
            }
        }


        for (String i : filenames){
            try (BufferedReader br = new BufferedReader(new FileReader(i))) {
                while (br.readLine() != null) {


                }
            } catch (IOException e) {
                e.printStackTrace();
            }
            ArrayList<String> List = new ArrayList<String>();
            ArrayList<String> positionList= new ArrayList<String>();
            try (BufferedReader br = new BufferedReader(new FileReader(i))) {
                String line;
                while ((line = br.readLine()) != null) {
                    if (!line.contains("Lane")) {
                        List.add(line);
                    }
                    else{
                        positionList.add(line);
                    }
                }
                nestedList.add(positionList);
                nestedList.add(List);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
        return nestedList;
    }




    /**
     * This sets the position for each lane depending on the level.
     *
     * @param input Expects an array of strings.
     * @return void.
     */
    private void positionSet(String[] input) {
        for (int i = 0; i < input.length; i++) {
            String[] split = input[i].split(",");
            if (split[0].equalsIgnoreCase("Lane")) {
                if (split[1].equalsIgnoreCase("Left")) {
                    leftPos = new Point(Integer.parseInt(split[2]), arrowY);
                } else if (split[1].equalsIgnoreCase("Right")) {
                    rightPos = new Point(Integer.parseInt(split[2]), arrowY);
                } else if (split[1].equalsIgnoreCase("Up")) {
                    upPos = new Point(Integer.parseInt(split[2]), arrowY);
                } else if (split[1].equalsIgnoreCase("Down")) {
                    downPos = new Point(Integer.parseInt(split[2]), arrowY);
                } else if (split[1].equalsIgnoreCase("Special")){
                    specialPos= new Point(Integer.parseInt(split[2]), arrowY);
                }
            } else {
                continue;
            }
        }
    }


    /**
     * Adds all notes into it's designated lanes depending on the csv file read.
     * @param lane which is a nested ArrayList.
     * @return void.
     */
    private void laneInitialise(ArrayList<ArrayList<String>> lane){
        leftLane=new ArrayList<>();
        rightLane=new ArrayList<>();
        upLane=new ArrayList<>();
        downLane=new ArrayList<>();
        specialLane=new ArrayList<>();
        int leftIndex=0;
        int rightIndex=1;
        int upIndex=2;
        int downIndex=3;
        int specialIndex=4;


        for (String i: lane.get(leftIndex)){
            String[] split= i.split(",");
            int frame= Integer.parseInt(split[2]);
            if (split[1].equalsIgnoreCase("Normal")) {
                Arrow insert = new Arrow((int) leftPos.x, (int) leftPos.y, frame, leftArrow, false);
                leftLane.add(insert);
            }
            else if (split[1].equalsIgnoreCase("Hold")) {
                HoldNotes insert = new HoldNotes((int) leftPos.x, holdY, frame, leftArrowHold,"left");
                leftLane.add(insert);
            }
            else{
                BombNotes insert = new BombNotes((int) leftPos.x, (int) leftPos.y, frame, bomb);
                leftLane.add(insert);
            }
        }


        for (String i: lane.get(rightIndex)){
            String[] split= i.split(",");
            int frame= Integer.parseInt(split[2]);
            if (split[1].equalsIgnoreCase("Normal")) {
                Arrow insert = new Arrow((int) rightPos.x, (int) rightPos.y, frame, rightArrow, false);
                rightLane.add(insert);
            }
            else if (split[1].equalsIgnoreCase("Hold")) {
                HoldNotes insert = new HoldNotes((int) rightPos.x, holdY, frame, rightArrowHold,"right");
                rightLane.add(insert);
            }
            else{
                BombNotes insert = new BombNotes((int) rightPos.x, (int) rightPos.y, frame, bomb);
                rightLane.add(insert);
            }
        }


        for (String i: lane.get(upIndex)){
            String[] split= i.split(",");
            int frame= Integer.parseInt(split[2]);
            if (split[1].equalsIgnoreCase("Normal")) {
                Arrow insert = new Arrow((int) upPos.x, (int) upPos.y, frame, upArrow, false);
                upLane.add(insert);
            }
            else if (split[1].equalsIgnoreCase("Hold")) {
                HoldNotes insert = new HoldNotes((int) upPos.x, holdY, frame, upArrowHold,"up");
                upLane.add(insert);
            }
            else{
                BombNotes insert = new BombNotes((int) upPos.x, (int) upPos.y, frame, bomb);
                upLane.add(insert);
            }
        }


        for (String i: lane.get(downIndex)){
            String[] split= i.split(",");
            int frame= Integer.parseInt(split[2]);
            if (split[1].equalsIgnoreCase("Normal")) {
                Arrow insert = new Arrow((int) downPos.x, (int) downPos.y, frame, downArrow, false);
                downLane.add(insert);
            }
            else if (split[1].equalsIgnoreCase("Hold")) {
                HoldNotes insert = new HoldNotes((int) downPos.x, holdY , frame, downArrowHold,"down");
                downLane.add(insert);
            }
            else{
                BombNotes insert = new BombNotes((int) downPos.x, (int) downPos.y, frame, bomb);
                downLane.add(insert);
            }
        }


        for (String i: lane.get(specialIndex)){
            String[] split= i.split(",");
            int frame= Integer.parseInt(split[2]);
            if (split[1].equalsIgnoreCase("DoubleScore")) {
                Arrow insert = new DoubleScore((int) specialPos.x, (int) specialPos.y, frame, timesTwo);
                specialLane.add(insert);
            }
            else if (split[1].equalsIgnoreCase("SpeedUp")) {
                SpeedNotes insert = new SpeedNotes((int) specialPos.x, (int) specialPos.y, frame, speedUp);
                insert.up=true;
                specialLane.add(insert);
            }
            else{
                SpeedNotes insert = new SpeedNotes((int) specialPos.x, (int) specialPos.y, frame, speedDown);
                insert.down=true;
                specialLane.add(insert);
            }
        }
    }


    /**
     * Adds notes to their designated lanes
     */


    ArrayList<ArrayList<String>> contain;
    ArrayList<ArrayList<String>> lanes;
    ArrayList<ArrayList<ArrayList<String>>> listOfLevels;


    /**
     * Sets position of lanes, and puts all notes in one level in a sorted double nested ArrayLists
     * returns void
     */
    private void laneSet(ArrayList<ArrayList<String>> List){
        ArrayList<ArrayList<ArrayList<String>>> nested = new ArrayList<ArrayList<ArrayList<String>>>();
        ArrayList<ArrayList<String>> Lanes = new ArrayList<ArrayList<String>>();
        for(int i=0;i<List.size();i++){
            ArrayList<ArrayList<String>> combine = new ArrayList<ArrayList<String>>();
            if (i%2!=0){
                ArrayList<String> right = new ArrayList<String>();
                ArrayList<String> left = new ArrayList<String>();
                ArrayList<String> up = new ArrayList<String>();
                ArrayList<String> down = new ArrayList<String>();
                ArrayList<String> special = new ArrayList<String>();
                for (String j : List.get(i)){
                    if (j.startsWith("Right")){
                        right.add(j);
                    }
                    else if (j.startsWith("Left")){
                        left.add(j);
                    }
                    else if (j.startsWith("Up")){
                        up.add(j);
                    }
                    else if (j.startsWith("Down")){
                        down.add(j);
                    }
                    else{
                        special.add(j);
                    }
                }
                combine.add(left);
                combine.add(right);
                combine.add(up);
                combine.add(down);
                combine.add(special);
                nested.add(combine);
            }
            else{
                Lanes.add(List.get(i));
            }
        }
        lanes=Lanes;
        listOfLevels=nested;
    }


    /**
     * The entry point for the program.
     */
    public static void main(String[] args) {
        ShadowDance game = new ShadowDance();
        game.contain= game.csvUnbox();
        game.laneSet(game.contain);
        game.run();
    }


    /**
     * Adds new instances of ArrayList and other classes for a reset
     * Returns void
     */
    private void levelReset(){
        level1=false;
        level2=false;
        level3 = false;
        gameOver = false;
        entities=false;
        frameCount = 0;
        contain = new ArrayList<ArrayList<String>>();
        leftLane = new ArrayList<Arrow>();
        rightLane = new ArrayList<Arrow>();
        upLane = new ArrayList<Arrow>();
        downLane = new ArrayList<Arrow>();
        specialLane = new ArrayList<Arrow>();
        Enemy.List=new ArrayList<Enemy>();
        Guardian.List=new ArrayList<Projectiles>();
        Lanes.arrowSpeed=2;
        KeyPressHandle.messageScore=null;
        leftPos = null;
        rightPos = null;
        upPos = null;
        downPos = null;
        specialPos = null;
        startGame = false;
        startScreen = true;
        total.total = 0;
    }




    /**
     * Performs a state update.
     * Allows the game to exit when the escape key is pressed.
     */


    private int scoreCount=0;
    private TotalScore total;
    private int scoreCoords=35;
    private Lanes left;
    private Lanes right;
    private Lanes up;
    private Lanes down;
    private Lanes special;
    private boolean entities;
    private Guardian support;
    private Enemy demon;


    private boolean level1=false;
    private boolean level2=false;
    private boolean level3=false;
    private boolean startScreen=true;



    /**
     * This method updates the game state 60 times every second.
     *
     * @param input which is user input.
     * @return void.
     */


    @Override
    protected void update(Input input) {
        if (input.wasPressed(Keys.ESCAPE)) {
            Window.close();
        }




        BACKGROUND_IMAGE.draw(Window.getWidth() / 2.0, Window.getHeight() / 2.0);
        if (!startGame && startScreen) {
            titleScreen.drawString("SHADOW DANCE", titleX, titleY);
            startKey.drawString("SELECT LEVELS WITH", startX, startY);
            instruction.drawString("NUMBER KEYS", numX, numY);
            instruction.drawString("1 2 3", instructionX, instructionY);


            if (input.wasPressed(Keys.NUM_1) && !startGame){
                String[] arrayVer=lanes.get(LEVEL1).toArray(new String[lanes.get(LEVEL1).size()]);
                positionSet(arrayVer);
                laneInitialise(listOfLevels.get(LEVEL1));
                startGame=true;
                startScreen=false;
                level1=true;
            }


            if (input.wasPressed(Keys.NUM_2) && !startGame){
                String[] arrayVer=lanes.get(LEVEL2).toArray(new String[lanes.get(LEVEL2).size()]);
                positionSet(arrayVer);
                laneInitialise(listOfLevels.get(LEVEL2));
                startGame=true;
                startScreen=false;
                level2=true;
            }


            if (input.wasPressed(Keys.NUM_3) && !startGame){
                String[] arrayVer=lanes.get(LEVEL3).toArray(new String[lanes.get(LEVEL3).size()]);
                positionSet(arrayVer);
                laneInitialise(listOfLevels.get(LEVEL3));
                support=new Guardian(guardian,projectile);
                demon= new Enemy(enemy);
                entities=true;
                startGame=true;
                startScreen=false;
                level3=true;
            }


        }






        // Game start after space bar has been pressed //
        if (startGame && !gameOver) {

            score.drawString("Score "+total.total,scoreCoords,scoreCoords);


            frameCount++;


            KeyPressHandle.resetter();
            KeyPressHandle.frameCounter();


            if(entities){
                Enemy.update();
                support.draw();
                support.findNearest(input);
                support.shoot();
                support.checkHit();
                demon.checkFrames();
                demon.drawFromList();
            }



            if (leftPos!=null) {laneLeft.draw(leftPos.x, laneY);}
            if (upPos!=null){laneUp.draw(upPos.x, laneY);}
            if (downPos!=null){laneDown.draw(downPos.x, laneY);}
            if (rightPos!=null){laneRight.draw(rightPos.x, laneY);}
            if (specialPos!=null){laneSpecial.draw(specialPos.x, laneY);}




            if (KeyPressHandle.messageScore!=null && KeyPressHandle.wasPressed){
                scoreCount++;
                if (scoreCount<=30){
                    if (KeyPressHandle.messageScore.equalsIgnoreCase("perfect")) {
                        scoreWriting.drawString(KeyPressHandle.messageScore, 402, WINDOW_HEIGHT/2);
                    }
                    else if (KeyPressHandle.messageScore.equalsIgnoreCase("good")) {
                        scoreWriting.drawString(KeyPressHandle.messageScore, 450, WINDOW_HEIGHT/2);
                    }
                    else if (KeyPressHandle.messageScore.equalsIgnoreCase("bad")) {
                        scoreWriting.drawString(KeyPressHandle.messageScore, 466, WINDOW_HEIGHT/2);
                    }
                    else if (KeyPressHandle.messageScore.equalsIgnoreCase("miss")) {
                        scoreWriting.drawString(KeyPressHandle.messageScore, 450, WINDOW_HEIGHT/2);
                    }
                    else if (KeyPressHandle.messageScore.equalsIgnoreCase("speed up")) {
                        scoreWriting.drawString(KeyPressHandle.messageScore, 392, WINDOW_HEIGHT/2);
                    }
                    else if (KeyPressHandle.messageScore.equalsIgnoreCase("slow down")) {
                        scoreWriting.drawString(KeyPressHandle.messageScore, 376, WINDOW_HEIGHT/2);
                    }
                    else{
                        scoreWriting.drawString(KeyPressHandle.messageScore, 360, WINDOW_HEIGHT/2);
                    }
                }


                if (scoreCount>=30){
                    scoreCount=0;
                    KeyPressHandle.wasPressed=false;
                }


            }


            if (!leftLane.isEmpty()){left= new Lanes(leftLane,frameCount,"left");
                left.drawNotes(input);}
            if (!rightLane.isEmpty()){right= new Lanes(rightLane,frameCount,"right");
                right.drawNotes(input);}
            if (!upLane.isEmpty()){up= new Lanes(upLane,frameCount,"up");
                up.drawNotes(input);}
            if (!downLane.isEmpty()){down= new Lanes(downLane,frameCount,"down");
                down.drawNotes(input);}
            if (!specialLane.isEmpty()){special= new Lanes(specialLane,frameCount,"special");
                special.drawNotes(input);}


            if(leftLane.isEmpty() && rightLane.isEmpty() && upLane.isEmpty() && downLane.isEmpty()
                    && specialLane.isEmpty()){
                gameOver=true;
            }
        }




        if (gameOver && level1){
            startKey.drawString("press space to return to level selection", returnX, returnY);
            if (total.total>=level1Score) {
                clear.drawString("CLEAR!", clearX, clearY);
            }
            else {
                failed.drawString("TRY AGAIN", tryX, tryY);


            }
            if (input.wasPressed(Keys.SPACE)){
                KeyPressHandle.messageScore=null;
                levelReset();
            }
        }
        else if(gameOver && level2){
            startKey.drawString("press space to return to level selection", returnX, returnY);
            if (total.total>=level2Score) {
                clear.drawString("CLEAR!", clearX, clearY);
            }
            else {
                failed.drawString("TRY AGAIN", tryX, tryY);
            }
            if (input.wasPressed(Keys.SPACE)){
                levelReset();
            }
        }
        else if (gameOver && level3) {
            startKey.drawString("press space to return to level selection", returnX, returnY);
            if (total.total >= level3Score) {
                clear.drawString("CLEAR!", clearX, clearY);
            } else {
                failed.drawString("TRY AGAIN", tryX, tryY);
            }
            if (input.wasPressed(Keys.SPACE)) {
                levelReset();
            }
        }


    }
}





































