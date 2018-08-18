import java.security.SecureRandom;

public class Board {

    private int[][] grid;
    private final int MINE = -1;

    public Board(int height, int width, int mines) {
        insertMines(height, width, mines);
        insertAdjacentValues(height, width);
    }

    private void insertMines(int height, int width, int mines) {
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

    private void insertAdjacentValues(int height, int width){
        for(int row=0; row<height; row++) {
            for(int col=0; col<width; col++){
                for(int subrow = row-1; subrow < row+2; subrow++){
                    for(int subcol = col-1; subcol < col+2; subcol++){
                        if(isMine(row, col) && subrow>=0 && subrow<height && 
                            subcol>=0 && subcol < width &&
                            !(subrow == row && subcol == col) &&
                            !isMine(subrow,subcol))
                        this.grid[subrow][subcol]++;
                    }
                }                                
            }
        }
    }

    private boolean isMine(int row, int col) {
        return this.grid[row][col] == this.MINE;
    }

}