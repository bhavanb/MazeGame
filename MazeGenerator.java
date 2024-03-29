import java.util.*;

public class MazeGenerator {
    int height = 33, width = 33;
    int[][] maze = new int[height][width];
    int[][] oldm = new int[height][width];

    public int[][] generateMaze() {

        // Initialize
        setmaze(maze, 1);

        Random rand = new Random();
        // r for rows and c for columns
        // Generate random r
        int r = rand.nextInt(height);
        while (r % 2 == 0) {
            r = rand.nextInt(height);
        }
        // Generate random c
        int c = rand.nextInt(width);
        while (c % 2 == 0) {
            c = rand.nextInt(width);
        }
        // Starting cell
        maze[r][c] = 0;

        // Allocate the maze with recursive method
        recursion(r, c);
        copymaze(maze, oldm);
        return maze;

    }

    public void recursion(int r, int c) {
        // 4 random directions
        Integer[] randDirs = generateRandomDirections();
        // Examine each direction
        for (int i = 0; i < randDirs.length; i++) {

            switch (randDirs[i]) {
                case 1: // Up
                    // Whether 2 cells up is out or not
                    if (r - 2 <= 0)
                        continue;
                    if (maze[r - 2][c] != 0) {
                        maze[r - 2][c] = 0;
                        maze[r - 1][c] = 0;
                        recursion(r - 2, c);
                    }
                    break;
                case 2: // Right
                    // Whether 2 cells to the right is out or not
                    if (c + 2 >= width - 1)
                        continue;
                    if (maze[r][c + 2] != 0) {
                        maze[r][c + 2] = 0;
                        maze[r][c + 1] = 0;
                        recursion(r, c + 2);
                    }
                    break;
                case 3: // Down
                    // Whether 2 cells down is out or not
                    if (r + 2 >= height - 1)
                        continue;
                    if (maze[r + 2][c] != 0) {
                        maze[r + 2][c] = 0;
                        maze[r + 1][c] = 0;
                        recursion(r + 2, c);
                    }
                    break;
                case 4: // Left
                    // Whether 2 cells to the left is out or not
                    if (c - 2 <= 0)
                        continue;
                    if (maze[r][c - 2] != 0) {
                        maze[r][c - 2] = 0;
                        maze[r][c - 1] = 0;
                        recursion(r, c - 2);
                    }
                    break;
            }
        }
    }

    public Integer[] generateRandomDirections() {
        ArrayList<Integer> randoms = new ArrayList<Integer>();
        for (int i = 0; i < 4; i++)
            randoms.add(i + 1);
        Collections.shuffle(randoms);

        return randoms.toArray(new Integer[4]);
    }

    void copymaze(int[][] source, int[][] target) {
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                target[i][j] = source[i][j];
    }

    void setmaze(int[][] maze, int val) {
        for (int i = 0; i < height; i++)
            for (int j = 0; j < width; j++)
                maze[i][j] = val;
    }

    public static void main(String[] args) {
        MazeGenerator m = new MazeGenerator();
        int[][] maze2 = m.generateMaze();
        for (int i = 0; i < 32; i++) {
            for (int j = 0; j < 32; j++) {
                System.out.print(maze2[i][j] + " ");
            }

            System.out.println();
        }
    }
}