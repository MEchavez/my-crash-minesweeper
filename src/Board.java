import java.security.SecureRandom;

public class Board {

    private int[][] grid;

    public Board(int height, int width, int mines) {
        insertMines(width, height, mines);
    }

    private void insertMines(int width, int height, int mines) {
        this.grid = new int[height][width];
        SecureRandom sr = new SecureRandom();
        int length = height * width;
        int[] positions = new int[length];        
        int rand;
        int  aux;
        int row, col;

        for(int index = 0; index < length; index++) 
            positions[index] = index;
        
        for(int index = length-1; index > 0; index--) {
            rand = sr.nextInt(index);
            aux = positions[index];
            positions[index] = positions[rand];
            positions[rand] = aux;            
        }

        for(int index = 0; index < length; index++) {
            row = positions[index] / width ;
            col = positions[index] % width;
            this.grid[row][col] = (index < mines) ? -1 : 0;
        }
        
    }

}