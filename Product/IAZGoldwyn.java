import javax.sound.sampled.*;
import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.concurrent.atomic.AtomicBoolean;


import javafx.application.*;
//import javafx.event.ActionEvent;
//import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.*;
import javafx.scene.layout.*;
import javafx.scene.control.*;
import javafx.geometry.*;



public class IAZGoldwyn extends Application{

    public static void main(String[] args) {
        launch(args);
    }

    @Override

    public void start(Stage myStage) throws Exception {//runs javafx application
        AtomicBoolean GAMEOVER = new AtomicBoolean(false);//this is atomic to avoid issues with finalized variables
        final boolean[] gamePlaying = {false};
        final int[] countContainer = {1}; // another different workaround i found for needing final inside lambda
        final double[] avgdBContainer = {0};
        final double[] startGameQuietValue = {0};

        //init screen
        Label lblConfigMin = new Label("Slide the slider to the minimum dB value: ");
            lblConfigMin.setScaleX(2);
            lblConfigMin.setScaleY(2);
        Label lblConfifMax = new Label("Slide the slider to the maximum dB value: ");
            lblConfifMax.setScaleX(2);
            lblConfifMax.setScaleY(2);
        Label lbldB = new Label("dB: ");
            lbldB.setScaleX(2);
            lbldB.setScaleY(2);

        Slider sdrdb = new Slider(-10, -5, 0.0);//for device, min = -10, max = -5
            sdrdb.setShowTickMarks(true);
            sdrdb.setMajorTickUnit(5);
            sdrdb.setMinorTickCount(4);
            sdrdb.setShowTickLabels(true);
            sdrdb.setOrientation(Orientation.VERTICAL);
            sdrdb.setScaleShape(true);
            sdrdb.setScaleX(4);
            sdrdb.setScaleY(4);
        Slider sdrConfigDBMin = new Slider(-30,30,-5);//for device, min = -10, max = -5
            sdrConfigDBMin.setShowTickMarks(true);
            sdrConfigDBMin.setMajorTickUnit(10);
            sdrConfigDBMin.setMinorTickCount(6);
            sdrConfigDBMin.setShowTickLabels(true);
            sdrConfigDBMin.setOrientation(Orientation.HORIZONTAL);
            sdrConfigDBMin.setScaleShape(true);
            sdrConfigDBMin.setScaleX(2);
            sdrConfigDBMin.setScaleY(2);
        Slider sdrConfigDBMax = new Slider(-30,30,10);
            sdrConfigDBMax.setShowTickMarks(true);
            sdrConfigDBMax.setMajorTickUnit(10);
            sdrConfigDBMax.setMinorTickCount(6);
            sdrConfigDBMax.setShowTickLabels(true);
            sdrConfigDBMax.setOrientation(Orientation.HORIZONTAL);
            sdrConfigDBMax.setScaleShape(true);
            sdrConfigDBMax.setScaleX(2);
            sdrConfigDBMax.setScaleY(2);
        Slider sdrSetSens = new Slider(0,5,3);
            sdrSetSens.setShowTickMarks(true);
            sdrSetSens.setMajorTickUnit(1);
            //sdrSetSens.setMinorTickCount(1);
            sdrSetSens.setShowTickLabels(true);
            sdrSetSens.setOrientation(Orientation.VERTICAL);
            sdrSetSens.setScaleShape(true);
            sdrSetSens.setScaleX(4);
            sdrSetSens.setScaleY(4);

        Button btnConfigMin = new Button("Click to Set Minimum:  " + (int) sdrConfigDBMin.getValue() + " ");
            btnConfigMin.setScaleX(2);
            btnConfigMin.setScaleY(2);
            //btnConfigMin.setCursor(Cursor.OPEN_HAND);
        Button btnConfigMax = new Button("Click to Set Maximum:  " + (int) sdrConfigDBMax.getValue() + " ");
            btnConfigMax.setScaleX(2);
            btnConfigMax.setScaleY(2);
        Button btnStartGame = new Button("Click to Start");
            btnStartGame.setScaleX(2);
            btnStartGame.setScaleY(2);
            btnStartGame.setAlignment(Pos.CENTER);
        Button btnSetSens = new Button("Set Sensitivity: "+ (int)sdrSetSens.getValue());
            btnSetSens.setScaleX(1.5);
            btnSetSens.setScaleY(1.5);
            btnSetSens.setAlignment(Pos.CENTER);
            btnSetSens.setOnDragDone(event -> sdrSetSens.setValue((int)sdrSetSens.getValue()));

        //game elements
        Rectangle sky = new Rectangle(0,0,1500,700);
        sky.setFill(Color.LIGHTSKYBLUE);

        Rectangle floor = new Rectangle(0,700,1500,300);
        floor.setFill(Color.SANDYBROWN);

        Button backToMenu = new Button("Back");

        ArrayList<IAObstacle> obstacles = new ArrayList<>();


        final double[] score = {0};

        Label lblScore = new Label("Score:  ");
        lblScore.setAlignment(Pos.TOP_RIGHT);

//panes
        GridPane menuPane = new GridPane();
        menuPane.setAlignment(Pos.CENTER);
        menuPane.setHgap(70);
        menuPane.setVgap(40);

        // Add the things to the GridPane
        menuPane.add(lblConfigMin, 0, 0);
        menuPane.add(sdrConfigDBMin, 0, 1);
        menuPane.add(btnConfigMin,0,2);

        menuPane.add(btnStartGame, 0, 3);

        menuPane.add(lblConfifMax,0,4);
        menuPane.add(sdrConfigDBMax, 0, 5);
        menuPane.add(btnConfigMax,0,6);

        menuPane.add(lbldB,3,3);
        menuPane.add(sdrdb, 4, 3);

        menuPane.add(sdrSetSens,7,3);
        menuPane.add(btnSetSens,5,3);

        Pane gamePane = new Pane();

        Background gameBackground = new Background(new BackgroundFill(Color.GREEN, null, null));
        gamePane.setBackground(gameBackground);
        gamePane.getChildren().add(floor);
        gamePane.getChildren().add(sky);
        gamePane.getChildren().add(backToMenu);
        gamePane.getChildren().add(lblScore);
        lblScore.setLayoutX(1350);
        lblScore.setLayoutY(25);
        lblScore.setScaleX(2);
        lblScore.setScaleY(2);
        //label for gameover
        Label lblGAMEOVER = new Label("GAME OVER!");
        lblGAMEOVER.setTextFill(Color.RED);
        lblGAMEOVER.setStyle("-fx-font-weight: bold; -fx-text-fill: red; -fx-stroke: black; -fx-stroke-width: 10;");
        lblGAMEOVER.setScaleY(8);
        lblGAMEOVER.setScaleX(8);
        lblGAMEOVER.setAlignment(Pos.CENTER);
        gamePane.getChildren().add(lblGAMEOVER);
        lblGAMEOVER.setLayoutX(1500/2-25);
        lblGAMEOVER.setLayoutY(1000/2-25);
        lblGAMEOVER.setVisible(false);

        //initializations of plauer and obstacle
        IABall player = new IABall(gamePane,100,100,1,25,Color.GREEN,obstacles);

        int obsY = 0;
        IAObstacle obs1 = new IAObstacle(gamePane,1500, obsY,200,200,3,obstacles);
        IAObstacle obs2 = new IAObstacle(gamePane,1500, obsY+600,200,100,3,obstacles);

        obs1.setVisible(true);
        obs2.setVisible(true);
        //System.out.println("\n\n\n\n\n\n" + obs1.getX() + ", " + obs1.getY() + "\n\n\n\n\n\n");

        //The following is for the grid elements.
/*
        for (int i = 0; i < 50000; i+=50) {
            gamePane.getChildren().add(new Line(i,-10000,i,10000));//0,0 -- 0,10000 & 100,0 -- 100, 1000
        }
        for (int i = 0; i < 50000; i+=50) {
            gamePane.getChildren().add(new Line(-10000,i,10000,i));//0,0 -- 0,10000 & 100,0 -- 100, 1000
        }
*/

//end grid elements

        myStage.setTitle("Decibel Program");
        Scene mySceneMenu = new Scene(menuPane, 1500, 1000);
        Scene mySceneGame = new Scene(gamePane, 1500, 1000);
        myStage.setScene(mySceneMenu);


        btnConfigMin.setOnAction(event -> sdrdb.setMin((int)sdrConfigDBMin.getValue()));

        btnConfigMax.setOnAction(event -> sdrdb.setMax((int)sdrConfigDBMax.getValue()));
        btnSetSens.setOnAction(event -> player.setSensitivity((int)sdrSetSens.getValue()));

        btnStartGame.setOnAction(event -> {
            btnStartGame.setText("Game Starting. Quiet please.");

            for (int i = 0; i < 1000; i++) {
                startGameQuietValue[0] += sdrdb.getValue();
            }
            startGameQuietValue[0] /= 1000;
            //System.out.println("\n\n\n\n\nStartGameQuietValue: " + startGameQuietValue[0] + "\n\n\n\n\n");
            myStage.setScene(mySceneGame);
            gamePlaying[0] = true;
        });

        backToMenu.setOnAction(event -> {
            btnStartGame.setText("Back To Game");
            myStage.setScene(mySceneMenu);
            if (gamePlaying[0] == true){gamePlaying[0] = false;}
            else if (gamePlaying[0] == false){
                gamePlaying[0] = true;
            }
        });

        // set up audio format
        AudioFormat audioFormat = new AudioFormat(44100, 16, 1, true, false);

        // get default microphone input
        DataLine.Info dataLineInfo = new DataLine.Info(TargetDataLine.class, audioFormat);
        TargetDataLine targetDataLine = (TargetDataLine) AudioSystem.getLine(dataLineInfo);

        // open microphone to capture audio
        targetDataLine.open(audioFormat);
        targetDataLine.start();

        // start separate thread for audio processing
        Thread audioThread = new Thread(() -> {
            // buffer for audio data
            byte[] buffer = new byte[1024];
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();

        //https://docs.oracle.com/javase/tutorial/sound/capturing.html source

            System.out.println("listening... \npress Ctrl+C to exit or close Muscular Movement.");

            while (!GAMEOVER.get()) {
                int bytesRead = targetDataLine.read(buffer, 0, buffer.length);
                if (gamePlaying[0]){
                    player.move();
                    for(IAObstacle x : obstacles){
                        if(x != null) {
                            x.move();
                            //System.out.println("\n\n\n\n\n\n\n\n\n" + obs1.getX() + ", " + obs1.getY() + "\n\n\n\n\n\n\n\n\n");
                        }
                    }
                    score[0] += 0.01;

                }

                for(IAObstacle j : obstacles){
                    if(player.intersects(j.getLayoutBounds()) && score[0] >= 3){
                        GAMEOVER.set(true);
                            lblGAMEOVER.setVisible(true);

                    }
                    //else{
                        //break;
                    //}
                }

                byteArrayOutputStream.write(buffer, 0, bytesRead);
                // convert bytes to doubles & calculate dB level
                byte[] audioData = byteArrayOutputStream.toByteArray();
                double sum = 0;
                for (byte audioByte : audioData) {
                    double audioSample = audioByte / 32768.0; // convert a byte to a double between -1 and 1
                    sum += audioSample * audioSample;
                }
                double rms = Math.sqrt(sum / audioData.length);
                double dB = 20 * Math.log10(rms); //dB math
                // update JavaFX application:
                Platform.runLater(() -> {
                    if (countContainer[0] == 5) {//averages a total of 5 audio samples and then executes(this takes milliseconds)
                        sdrdb.setValue((avgdBContainer[0] / 5)+ 55.5); //dB Value
                        //System.out.println((avgdBContainer[0] / 5)+ 55.5);
                        if (( ( (avgdBContainer[0] / 5)  + 55.5) - startGameQuietValue[0]) >= player.getSensitivity()){ //if dB value - silence > sensitivity
                            player.setStrength(( (avgdBContainer[0] / 5)  + 55.5) - startGameQuietValue[0]);//tell the ball how many dB
                        }
                        countContainer[0] = 1;
                        avgdBContainer[0] = 0;
                    }
                    else {
                        avgdBContainer[0] += dB;
                        countContainer[0]++;
                    }
                    btnConfigMax.setText("Click to Set Maximum:  " + (int) sdrConfigDBMax.getValue() + " ");
                    btnConfigMin.setText("Click to Set Minimum:  " + (int) sdrConfigDBMin.getValue() + " ");
                    btnSetSens.setText("Set Sensitivity: "+ (int) sdrSetSens.getValue());
                    lblScore.setText("Score: " + (int)score[0]);
                });
                byteArrayOutputStream.reset();//reset audiostream bytes
            }

        });
        audioThread.setDaemon(true); // allow the application to exit even if thread is running
        audioThread.start();

        myStage.show(); // show JavaFX window
    }
}