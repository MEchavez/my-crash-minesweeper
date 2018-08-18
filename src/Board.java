import java.security.SecureRandom;

public class Board implements ISubject{

    private int[][] grid;
    private final int MINE = -1;
    private IObserver userBoard = null;
    private int height;
    private int width;

    public Board(int height, int width, int mines) {
        this.height = height;
        this.width = width;
        insertMines(height, width, mines);
        insertAdjacentValues(height, width);
        print();
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
            this.grid[row][col] = (index < mines) ? this.MINE : 0;
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

    public void uncover(int row, int col){
        row = row-1;
        col = col-1;
        int cellValue;
        if(!isMine(row, col) && !isUncovered(row, col) && !isMarked(row, col)){
            cellValue = this.grid[row][col];
            if(cellValue > 0){
                notify(row, col, cellValue);
                this.grid[row][col] = CellStatus.UNCOVERED.getValue();                
            }else if(cellValue == 0){
                notify(row, col, cellValue);
                this.grid[row][col] = CellStatus.UNCOVERED.getValue(); 
                ////repaint
                //System.out.println("Repintando Board");
                //print();
                /////////// 
                revealAdjacents(row,col);
            }
        }
        ////////////////////////// TO DO            
        //if(isMine(row, col))
        //    revealMines();
    }

    //public void mark(int row, int col){
        
    //}

    private boolean isMarked(int row, int col){
        return this.grid[row][col] == CellStatus.MARKED.getValue();
    }

    private boolean isUncovered(int row, int col){
        return this.grid[row][col] == CellStatus.UNCOVERED.getValue();
    }

    //private void revealMines(){

    //}

    private void revealAdjacents(int row, int col){
        int value;
        for(int subrow=row-1; subrow<row+2; subrow++){
            for(int subcol=col-1; subcol<col+2; subcol++){
                if(subrow >= 0 && subrow < this.height && subcol >= 0 &&
                    subcol < this.width && !(subrow == row && subcol == col)){
                        
                        value = this.grid[subrow][subcol];

                        if(value == 0){
                            notify(subrow, subcol, value);
                            this.grid[subrow][subcol] = CellStatus.UNCOVERED
                                .getValue();
                            ////repaint
                            //System.out.println("Repintando Board");
                            //print();
                            ///////////
                            revealAdjacents(subrow, subcol);
                        }
                            
                        else if(value > 0){
                            value = this.grid[subrow][subcol];
                            notify(subrow, subcol, value);
                            this.grid[subrow][subcol] = CellStatus.UNCOVERED
                                .getValue();                
                        }                        

                }
            }
        }
    }

    private boolean isMine(int row, int col) {
        return this.grid[row][col] == this.MINE;
    }

    @Override 
    public void attach(IObserver o){
        this.userBoard = o;
    }

    @Override
    public void notify(int row, int col, int value){
        String strValue = value == 0 ? "-" : ""+value;
        this.userBoard.update(row, col, strValue);
    }

    public void print(){
        System.out.print("*******************************\n ");
        for(int x=0; x<width; x++)
            System.out.printf("  %3s", String.valueOf(x+1));
        System.out.println();
        for(int row=0; row<height; row++){
            System.out.printf("%s", ""+(row+1));
            for(int col=0; col<width; col++){
                System.out.printf("  %3s",""+this.grid[row][col]);
            }
            System.out.println();
        }
        System.out.print("\n*******************************\n");
    }

}