import bagel.Image;
import java.util.ArrayList;
import java.util.Random;
/**
 * Class that spawns enemies and stores its attributes
 */
public class Enemy{
    public static int frameCount=0;
    public boolean marked=false;
    private int spawnTimer=600;
    public int x;
    public int y;
    private int speed=1;
    static ArrayList<Enemy> List = new ArrayList<>();
    public boolean isSpawned=false;
    private Image enemy;
    Enemy(Image image){
        this.enemy=image;
    }

    private int randomiseX(){
        Random random = new Random();
        int randomX = random.nextInt(801) + 100;
        return randomX;
    }

    private int randomiseY(){
        Random random = new Random();
        int randomY = random.nextInt(401) + 100;
        return randomY;
    }

    private int randomiseSpeed(){
        Random random=new Random();
        int randomSpeed = (random.nextInt(2) * 2) - 1;
        return randomSpeed;
    }

    // Timer that spawns an enemy every 600 frames//
    public void checkFrames(){
        if (frameCount%spawnTimer==0){
            Enemy newEnemy=new Enemy(enemy);
            newEnemy.x=randomiseX();
            newEnemy.y=randomiseY();
            newEnemy.speed=randomiseSpeed();
            List.add(newEnemy);
        }
    }

    // Draws demon from the ArrayList//
    public void drawFromList(){
        for (Enemy enemy : List){
            if(!enemy.isSpawned){
                enemy.isSpawned=true;
            }

            if (enemy.isSpawned){
                enemy.draw();
                enemy.x+=enemy.speed;
            }

            if (enemy.isSpawned && enemy.x==0 || enemy.x==900){
                enemy.speed*=-1;
            }
        }
    }

    /**
     * checks for notes that was marked by a demon
     * @param note
     * @return void
     */

    public static void mark(Arrow note){

        for (Enemy enemy : List){
            int distance=(int)Math.sqrt(Math.pow((note.x-enemy.x),2)+Math.pow((note.y-enemy.y),2));
            if (distance<=104 && !(note instanceof BombNotes) && !(note instanceof HoldNotes) &&
                    !(note instanceof SpeedNotes)){
                note.markedForDemon=true;
            }
        }
    }

    private void draw(){
        enemy.draw(x,y);
    }

    // Update method derived from the ShadowDance method//
    public static void update(){
        frameCount++;
    }




}
