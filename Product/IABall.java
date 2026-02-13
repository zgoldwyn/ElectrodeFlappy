import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.scene.paint.*;

import java.util.*;

public class IABall extends Circle {

    int ballSize;
    Pane field;
    Color color;
    double yVel;
    final double grav = 0.09;
    ArrayList<IAObstacle> obstacles;
    static double dbOver;
    public double sensitivity = 3;


    public IABall(Pane field, double x, double y, double ySpeed, int ballSize, Color color, ArrayList<IAObstacle> obstacles) {
        super(ballSize, color);
        yVel = ySpeed;
        this.ballSize = ballSize;
        this.color = color;
        this.field = field;
        this.setCenterX(x+25);
        this.setCenterY(y+25);
        field.getChildren().add(this);
    }
    public void move() {
        this.setCenterY(this.getCenterY() + yVel);
        if (yVel <= 1.5) {
            yVel += grav;
        } else {
            yVel = 1.5;
        }
        if (this.getCenterY() >= 675) {
            this.setCenterY(675);
            yVel = 0;
        }
        if (this.getCenterY() <= 25) {
            this.setCenterY(25);
            yVel = 1.5;
        }
        if (dbOver >= sensitivity){
                yVel -= 5;
                dbOver = 0;
        }
        /* INSERT KEYBOARD INPUT??
        this.setOnAction(new EventHandler<ActionEvent>() {
            public void handle(ActionEvent ae) {
                yVel -= 20;
            }
        });
        */
    }
    public void setStrength(double dbOver1){
        dbOver = dbOver1;
    }
    public void setSensitivity(double sens){
        sensitivity = sens;
    }
    public double getSensitivity(){
        return sensitivity;
    }
}

