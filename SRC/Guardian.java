import bagel.*;
import java.util.ArrayList;
/**
 * Class that handles guardian attributes and is linked with projectiles class
 */

public class Guardian{
    private Image arrow;
    private Image guardian;
    private int x=800;
    private int y=600;
    private int speed=6;
    private double bigNum=100000;
    public static ArrayList<Projectiles> List = new ArrayList<>();
    Guardian(Image image,Image arrow){
        this.guardian=image;
        this.arrow=arrow;
    }

    public void draw(){
        guardian.draw(x,y);
    }

    /**
     * Finds the closest enemy relative to guardian position
     * @param input Description of the first parameter.
     * @return void
     */
    public void findNearest(Input input){
        if (input.wasPressed(Keys.LEFT_SHIFT)) {
            double min=bigNum;
            Enemy nearest=null;
            for (Enemy enemy : Enemy.List) {
                if (enemy.isSpawned) {
                    double distance = Math.sqrt(Math.pow((x - enemy.x), 2) + Math.pow((y - enemy.y), 2));
                    if (distance<min){
                        min=distance;
                        nearest=enemy;
                    }
                }
            }
            if (min!=bigNum && nearest!=null) {
                Projectiles projectile= new Projectiles(arrow);
                int xD=nearest.x;
                int yD=nearest.y;

                projectile.enemyX=xD;
                projectile.enemyY=yD;
                projectile.correspondingTarget=nearest;
                List.add(projectile);
            }
        }
    }

    /**
     * Shoots at enemy given the closest enemy location from previous method
     * @return void
     */
    public void shoot(){

        for (Projectiles projectile: List){

            double dirX = projectile.enemyX - x;
            double dirY = projectile.enemyY - y;
            double distance = Math.sqrt(Math.pow((projectile.x - projectile.enemyX), 2) +
                    Math.pow((projectile.y - projectile.enemyY), 2));

            if (distance != 0) {
                dirX /= distance;
                dirY /= distance;
            }


            double dX=speed*dirX;
            double dY=speed*dirY;


            if (distance>=62) {
                projectile.rotationSet(x,y,projectile.enemyX, projectile.enemyY);
                projectile.draw();
                projectile.x += dX;
                projectile.y += dY;
            }

            if (distance<=62){
                projectile.correspondingTarget.marked=true;
                projectile.hit=true;
            }
        }
    }

    /**
     * checks all enemies that have been hit so they can later get removed
     * @return void
     */
    public void checkHit(){
        for (Projectiles projectile: List){
            if (projectile.correspondingTarget.marked){
                Enemy.List.remove(projectile.correspondingTarget);
            }
        }

    }
}
