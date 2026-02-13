import javafx.application.*;
import javafx.stage.*;
import javafx.scene.*;
import javafx.event.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;
import javafx.animation.*;
import javafx.util.*;
import java.util.*;

public class IAObstacle extends Rectangle{
    int width;
    int height;
    Pane field;
    double xVel;
    ArrayList<IAObstacle> obstacles;

    public IAObstacle(Pane field, int width, int height, double xVel, ArrayList<IAObstacle> obstacles) {
        super(width,height,Color.RED);
        this.field = field;
        this.width = width;
        this.height = height;
        this.xVel = Math.abs(xVel);
        this.obstacles = obstacles;
        field.getChildren().add(this);
        obstacles.add(this);
        this.setFill(Color.RED);
        this.setVisible(true);

    }

    public IAObstacle(Pane field,int x, int y, int width, int height, double xVel, ArrayList<IAObstacle> obstacles) {
        super(width,height,Color.RED);
        this.field = field;
        this.width = width;
        this.height = height;
        this.xVel = Math.abs(xVel);
        this.obstacles = obstacles;
        field.getChildren().add(this);
        obstacles.add(this);
        this.setFill(Color.RED);
        this.setVisible(true);

        this.setX(x);
        this.setY(y);

    }

    public void move(){
        if(this.getX() <= -200){
            this.setX(1500);
            this.setY(this.getY()-15);
            this.setHeight(this.getHeight()+15);
        }
        else{
            this.setX(this.getX()-xVel);
        }
    }

    public void setXVel(int vel){
        xVel = vel;
    }


}
