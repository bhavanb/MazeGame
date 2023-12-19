import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.geometry.*;
import javafx.scene.input.KeyCode;
import javafx.scene.text.Font;
import java.time.Duration;
import java.time.Instant;

public class MazeGame extends Application {
    private static final int W = 264, H = 264;
    MazeGenerator m = new MazeGenerator();
    int[][] maze = m.generateMaze();
    int px = 8, py = 8, lx, ly, level = 1;
    Label start1 = new Label("MazeGame");
    Label start2 = new Label("Use WASD or arrow keys to move");
    Label start3 = new Label("Press R to restart the level");
    Label start4 = new Label("Press any key to start");
    Label start5 = new Label("Made by Bhavan Basker, Vidyashilp Academy");
    Label lvl1 = new Label("Level Complete");
    Label lvl2 = new Label("Press ENTER for Next Level");
    Label lvl3 = new Label("Press R to Replay Level");
    Label Timer = new Label();
    Group g = new Group();
    VBox s = new VBox(20, start1, start2, start3, start4, start5);
    VBox l = new VBox(20, lvl1, lvl2, lvl3);
    VBox t = new VBox(Timer);
    Scene startScene = new Scene(s, W, H, Color.WHITE);
    Scene lvl = new Scene(l, W, H, Color.WHITE);
    Scene scene = new Scene(g, W, H, Color.WHITE);
    Rectangle[][] walls = new Rectangle[33][33];
    Stage window;
    Instant start;
    Duration d;

    @Override
    public void start(Stage stage) throws Exception {
        start1.setFont(new Font("Arial", 30));
        Timer.setFont(new Font("Arial", 6));
        Timer.setTextFill(Color.color(1, 1, 1));
        s.setAlignment(Pos.CENTER);
        l.setAlignment(Pos.CENTER);
        window = stage;
        window.setTitle("Maze Game");
        renderMaze();
        window.setScene(startScene);
        window.show();
        startScene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode k = event.getCode();
                if (k == KeyCode.ENTER) {
                    start = Instant.now();
                    // g.getChildren().add(t);
                    window.setScene(scene);
                }
                if (k == KeyCode.ESCAPE)
                    stage.close();
            }
        });

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode k = event.getCode();
                if (k == KeyCode.W || k == KeyCode.UP)
                    py -= 8;
                else if (k == KeyCode.A || k == KeyCode.LEFT)
                    px -= 8;
                else if (k == KeyCode.S || k == KeyCode.DOWN)
                    py += 8;
                else if (k == KeyCode.D || k == KeyCode.RIGHT)
                    px += 8;
                else if (k == KeyCode.R) {
                    px = 8;
                    py = 8;
                } else if (k == KeyCode.L) {
                    px = 248;
                    py = 248;
                } else if (k == KeyCode.ESCAPE)
                    stage.close();
                if (maze[px / 8][py / 8] == 1) {
                    px = lx;
                    py = ly;
                } else {
                    walls[lx / 8][ly / 8].setFill(Color.WHITE);
                }
                walls[px / 8][py / 8].setFill(Color.RED);

            }
        });

        lvl.setOnKeyPressed(new EventHandler<KeyEvent>() {
            @Override
            public void handle(KeyEvent event) {
                KeyCode key = event.getCode();
                if (key == KeyCode.R)
                    m.copymaze(m.oldm, maze);
                if (key == KeyCode.ESCAPE)
                    stage.close();
                else if (key == KeyCode.ENTER) {
                    maze = m.generateMaze();
                    level++;
                }
                px = 8;
                py = 8;
                renderMaze();
                start = Instant.now();
                window.setScene(scene);
            }
        });
        AnimationTimer timer = new AnimationTimer() {
            @Override
            public void handle(long now) {
                if (lx != px || ly != py) {
                    lx = px;
                    ly = py;
                }
                if ((px == 248) && (py == 248)) {
                    level();
                }
                d = Duration.between(start, Instant.now());
                Timer.setText(
                        "Level " + level + " | "
                                + String.format("%02d:%02d:%02d", d.toHours(), d.toMinutesPart(), d.toSecondsPart()));
            }
        };
        start = Instant.now();
        timer.start();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public void level() {
        Duration e = Duration.between(start, Instant.now());
        lvl1.setText("Level " + level + " Complete in "
                + String.format("%02d:%02d:%02d", e.toHours(), e.toMinutesPart(), e.toSecondsPart()));
        window.setScene(lvl);
        px = 0;
        py = 0;
    }

    public void renderMaze() {
        g.getChildren().clear();
        for (int i = 0; i < 33; i++)
            for (int j = 0; j < 33; j++) {
                walls[i][j] = new Rectangle(i * 8, j * 8, 8, 8);
                if (maze[i][j] == 0) {
                    walls[i][j].setFill(Color.WHITE);
                }
                g.getChildren().add(walls[i][j]);
            }
        walls[1][1].setFill(Color.RED);
        g.getChildren().add(t);
    }
}