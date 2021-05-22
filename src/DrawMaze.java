package MazeFX;
 
import java.util.*;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;
import javafx.scene.paint.Color;


public class DrawMaze extends Application
{
     private static final int W = 264, H = 264; 
     int height = 33, width = 33;
     int[][] maze = new int[height][width];
     int[][] oldm = new int[height][width];
     Rectangle[][] walls = new Rectangle[33][33];
     Group g = new Group();
     Scene scene = new Scene(g, W, H, Color.WHITE); 
     public void start(Stage stage)
     {
        stage.setScene(scene);
        stage.show();
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
     public int[][] DrawMaze()
     {
         
         // Initialize
         setmaze(maze, 1);
     
         Random rand = new Random();
         // r for row�?c for column
         // Generate random r
         int r = rand.nextInt(height);
         while (r % 2 == 0) 
         {
             r = rand.nextInt(height);
         }
         // Generate random c
         int c = rand.nextInt(width);
         while (c % 2 == 0) 
         {
             c = rand.nextInt(width);
         }
         // Starting cell
         maze[r][c] = 0;
     
         //　Allocate the maze with recursive method
         recursion(r, c);
         copymaze(maze, oldm);
         renderMaze();
         return maze;
         
     }
     public void recursion(int r, int c) 
     {
         // 4 random directions
         Integer[] randDirs = generateRandomDirections();
         // Examine each direction
         for (int i = 0; i < randDirs.length; i++) 
         {
     
             switch(randDirs[i])
             {
                 case 1: // Up
                     //　Whether 2 cells up is out or not
                     if (r - 2 <= 0)
                         continue;
                     if (maze[r - 2][c] != 0) 
                     {
                         maze[r-2][c] = 0;
                         maze[r-1][c] = 0;
                         recursion(r - 2, c);
                     }
                     break;
                 case 2: // Right
                     // Whether 2 cells to the right is out or not
                     if (c + 2 >= width - 1)
                         continue;
                     if (maze[r][c + 2] != 0) 
                     {
                         maze[r][c + 2] = 0;
                         maze[r][c + 1] = 0;
                         recursion(r, c + 2);
                     }
                     break;
                 case 3: // Down
                     // Whether 2 cells down is out or not
                     if (r + 2 >= height - 1)
                         continue;
                     if (maze[r + 2][c] != 0) 
                     {
                         maze[r+2][c] = 0;
                         maze[r+1][c] = 0;
                         recursion(r + 2, c);
                     }
                     break;
                 case 4: // Left
                     // Whether 2 cells to the left is out or not
                     if (c - 2 <= 0)
                         continue;
                     if (maze[r][c - 2] != 0) 
                     {
                         maze[r][c - 2] = 0;
                         maze[r][c - 1] = 0;
                         recursion(r, c - 2);
                     }
                     break;
             }
         }     
     } 
     public Integer[] generateRandomDirections() 
     {
          ArrayList<Integer> randoms = new ArrayList<Integer>();
          for (int i = 0; i < 4; i++)
               randoms.add(i + 1);
          Collections.shuffle(randoms);
     
         return randoms.toArray(new Integer[4]);
     }
     void copymaze(int[][] source, int[][] target)
     {
         for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                target[i][j] = source[i][j];
     }
     void setmaze(int[][] maze, int val)
     {
         for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                maze[i][j] = val;
     }
     public static void main(String[] args)
     {
         DrawMaze m = new DrawMaze();
         int[][] maze2 = m.DrawMaze();
         for (int i = 0; i < 32; i++) 
            {
                for (int j = 0; j < 32; j++) 
                {
                    System.out.print(maze2[i][j] + " ");
                }
      
                System.out.println();
            }
     }
}
