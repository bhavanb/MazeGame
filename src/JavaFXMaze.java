package MazeFX;

import javafx.animation.AnimationTimer;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.*;  
import javafx.scene.layout.*;
import javafx.scene.image.*;
import javafx.scene.input.KeyEvent;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import javafx.scene.shape.*;
import javafx.geometry.*;

import MazeFX.generateMaze;
public class JavaFXMaze extends Application {
    private static final int W = 264, H = 264;
    generateMaze m = new generateMaze();
    int[][] maze = m.generateMaze();    
    int px = 8,py = 8,lx,ly, level = 1;
    Label lvl1 = new Label("Level Complete");
    Label lvl2 = new Label("Press ENTER for Next Level");
    Label lvl3 = new Label("Press R to Replay Level");
    Group g = new Group();
    VBox l = new VBox(20,lvl1, lvl2, lvl3);    
    Scene lvl = new Scene(l, W, H, Color.WHITE);
    Scene scene = new Scene(g, W, H, Color.WHITE);    
    Rectangle[][] walls = new Rectangle[33][33];
    Stage window;
    @Override
    public void start(Stage stage) throws Exception {
        l.setAlignment(Pos.CENTER);
        window = stage;
        window.setTitle("Maze Game");
        renderMaze();
               
        
        
        

        scene.setOnKeyPressed(new EventHandler<KeyEvent>() 
        {
            @Override
            public void handle(KeyEvent event) 
            {
                switch (event.getCode()) 
                {
                    case W:    py-=8; break;                    
                    case A:  px-=8; break;
                    case S:  py+=8; break;
                    case D: px+=8; break;
                    case R: px = 8;py = 8; break;
                    case L: px = 248;py = 248; break;                                        
                }
                if (maze[px/8][py/8] == 1) 
                {
                  px = lx;
                  py = ly;
                }
                else{walls[lx/8][ly/8].setFill(Color.WHITE);}
                walls[px/8][py/8].setFill(Color.RED);
                
            }
        });
        
        lvl.setOnKeyPressed(new EventHandler<KeyEvent>() 
        {
            @Override
            public void handle(KeyEvent event) 
            {
                switch (event.getCode()) 
                {
                    case R: m.copymaze(m.oldm, maze); /*System.out.println("r");*/ break;                    
                    case ENTER: maze = m.generateMaze();level++;/* System.out.println("enter");*/ break;                                                            
                }
                px = 8;
                py = 8;
                renderMaze();
                window.setScene(scene);
            }
        });

        window.setScene(scene);
        window.show();

        AnimationTimer timer = new AnimationTimer() 
        {
            @Override
            public void handle(long now) 
            {                                                
                if(lx != px || ly!=py)
                {
                    lx = px;
                    ly = py;
                }
                if((px == 248) && (py == 248))
                {           
                   level();
                   
                } 
            }
        };
        timer.start();
    }
    public static void main(String[] args) { launch(args); }
    public void level()
    {       
        lvl1.setText("Level "+level+" Complete");
        window.setScene(lvl);
    }
    public void renderMaze()
    {
        for(int i = 0;i<33;i++)
            for(int j = 0;j<33;j++)
            {
                walls[i][j] = new Rectangle(i*8,j*8,8,8);
                if(maze[i][j] == 0){walls[i][j].setFill(Color.WHITE);}
                g.getChildren().add(walls[i][j]);
            }
        walls[1][1].setFill(Color.RED);
    }    
}