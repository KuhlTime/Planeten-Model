import com.sun.org.apache.xpath.internal.operations.Bool;
import javafx.animation.Interpolator;
import javafx.animation.PathTransition;
import javafx.animation.Transition;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Rectangle;
import javafx.stage.Stage;
import javafx.util.Duration;
import java.util.*;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    Sternsystem sonnensystem = Sternsystem.sonnensystem;

    Boolean animationPlaying = false;
    Boolean firstTime = true;

    ArrayList<Duration> pauseTimestamps = new ArrayList<Duration>();
    ArrayList<Transition> transitions = new ArrayList<Transition>();

    @Override
    public void start(Stage primaryStage) throws Exception {
        int width = 800;
        int height = 600;

        Group root = new Group();
        Scene scene = new Scene(root, width, height);

        Rectangle background = new Rectangle(width,height, Color.rgb(52, 73, 94));
        root.getChildren().add(background);

        primaryStage.setTitle("Planeten Model");
        primaryStage.setScene(scene);

        int planetenCount = sonnensystem.planeten.length;

        // Start / Stop Button
        Button btn = new Button("Start");
        double btnWidth = 80;
        btn.setPrefWidth(btnWidth);
        double x = width - btnWidth - 20;
        btn.setLayoutX(x);
        btn.setLayoutY(20);

        btn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                if (animationPlaying) {
                    btn.setText("Start");
                } else {
                    btn.setText("Stop");
                }

                animation(!animationPlaying);

                animationPlaying = !animationPlaying;
            }
        });

        // Reset Button
        Button resetBtn = new Button("Reset");
        resetBtn.setPrefWidth(btnWidth);
        resetBtn.setLayoutX(x);
        resetBtn.setLayoutY(60);

        resetBtn.setOnAction(new EventHandler<ActionEvent>() {
            @Override
            public void handle(ActionEvent event) {
                resetAnimation();
                btn.setText("Start");
            }
        });

        // Label
        Label label = new Label("Zeit");

        // Sonne
        int midX = width/2;
        int midY = height/2;
        Stern stern = sonnensystem.stern;
        Circle sternCircle = new Circle(midX,midY,20, stern.farbe);
        root.getChildren().add(sternCircle);

        // Planeten
        for (int i = 0; i < planetenCount; i++) {
            Planet planet = sonnensystem.planeten[i];

            double factor = ((double) i + 1) / (planetenCount + 1);
            double y = 10 + midY + (height/2)*factor;

            Circle planetKreis = new Circle(midX, y, 10, planet.farbe);
            Circle umlaufbahn = new Circle(midX, midY, y-midY);

            double geschFaktor = 10E6;

            Duration transDuration = new Duration((((double) planet.umlaufgeschwindigkeit) / geschFaktor) * 1000);

            PathTransition transition = new PathTransition();
            transition.setInterpolator(Interpolator.LINEAR);
            transition.setNode(planetKreis);
            transition.setPath(umlaufbahn);
            transition.setDuration(transDuration);
            transition.setCycleCount(-1);

            transitions.add(transition);

            umlaufbahn.setStroke(Color.rgb(255,255,255,0.6));
            umlaufbahn.setFill(Color.TRANSPARENT);
            umlaufbahn.setStrokeWidth(2);

            root.getChildren().add(umlaufbahn);
            root.getChildren().add(planetKreis);
        }

        // Element hinzufügen
        root.getChildren().add(btn);
        root.getChildren().add(resetBtn);

        primaryStage.show();
    }

    void animation(Boolean start) {
        int i = 0;

        if (!start) {
            // Lösche alle speicherpunkte
            pauseTimestamps.clear();
        }

        for (Transition transition : transitions) {
            if (start) {
                if (firstTime) {
                    // align all the planets to the bottom when first time playing
                    Double totalDurationInSeconds = transition.getCycleDuration().toSeconds();
                    Double startingDuration = totalDurationInSeconds * (1.0/4.0);
                    transition.playFrom(new Duration(startingDuration * 1000));
                } else {
                    Duration pausedAt = pauseTimestamps.get(i);
                    transition.playFrom(pausedAt);
                }
            } else {
                // speichere die pausierungs Zeitpunkte
                pauseTimestamps.add(transition.getCurrentTime());
                transition.pause();
            }

            i++;
        }

        firstTime = false;
    }

    void resetAnimation() {
        firstTime = true;
        animation(true);
        animation(false); // This start - stop should put them in their default position
        firstTime = true;
        animationPlaying = false;
        pauseTimestamps.clear();
    }
}
