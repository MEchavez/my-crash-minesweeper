import java.util.Map;
import java.util.HashMap;
import java.security.SecureRandom;

public class Board implements ISubject{

    private int[][] grid;
    private final int MINE = -1;
    private IObserver userBoard = null;
    private int height;
    private int width;
    private int mines;
    private boolean playable;
    private boolean complete;
    private int uncoveredCells;
    private Map<String,Integer> markedCells;

    public Board(int height, int width, int mines) {
        this.height = height;
        this.width = width;
        this.mines = mines;
        this.uncoveredCells = 0;
        this.playable = true;
        this.markedCells = new HashMap<>();
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

    public boolean uncover(int row, int col){
        row = row-1;
        col = col-1;
        int cellValue;

        if(isMine(row, col)){
            revealMines();            
            return true;
        }

        if(!isMine(row, col) && !isUncovered(row, col) && !isMarked(row, col)){
            cellValue = this.grid[row][col];
            if(cellValue > 0){
                notify(row, col, cellValue);
                this.grid[row][col] = CellStatus.UNCOVERED.getValue();
                setUncoveredCells(getUncoveredCells() + 1);
                updateCompleteness();
                return true;               
            }else if(cellValue == 0){
                notify(row, col, cellValue);
                this.grid[row][col] = CellStatus.UNCOVERED.getValue();
                setUncoveredCells(getUncoveredCells() + 1);
                updateCompleteness();
                revealAdjacents(row,col);
                return true;
            }
        }         
        return false;
    }

    public boolean markCell(int row, int col){
        String index;
        int value;
        row = row-1;
        col = col-1;

        if(!isMarked(row, col) && !isUncovered(row,col)){
            index = ""+row+col;
            value = this.grid[row][col];
            this.markedCells.put(index, value);
            this.grid[row][col] = CellStatus.MARKED.getValue();
            value = value = this.grid[row][col];
            notify(row, col, value);
            return true;
        }
        return false;
    }

    public boolean unmarkCell(int row, int col){
        String index;
        int value;
        row = row-1;
        col = col-1;

        index = ""+row+col;
        if(this.markedCells.containsKey(index)){
            value = this.markedCells.get(index);
            this.grid[row][col] = value;
            value = CellStatus.UNMARKED.getValue();
            notify(row, col, value);
            this.markedCells.remove(index);
            return true;
        }
        return false;
    }

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
                            setUncoveredCells(getUncoveredCells() + 1);
                            updateCompleteness();
                            revealAdjacents(subrow, subcol);
                        }
                            
                        else if(value > 0){
                            value = this.grid[subrow][subcol];
                            notify(subrow, subcol, value);
                            this.grid[subrow][subcol] = CellStatus.UNCOVERED
                                .getValue();   
                            setUncoveredCells(getUncoveredCells() + 1);
                            updateCompleteness();         
                        }                       
                }
            }
        }
    }

    private void revealMines(){
        for(int row = 0; row < this.height; row++){
            for(int col = 0; col<this.width; col++){
                if(this.grid[row][col] == this.MINE)
                    notify(row, col, this.MINE);
            }
        }
        setBoardStatusTo(false);
    }


    private boolean isMarked(int row, int col){
        return this.grid[row][col] == CellStatus.MARKED.getValue();
    }

    private boolean isUncovered(int row, int col){
        return this.grid[row][col] == CellStatus.UNCOVERED.getValue();
    }

    private boolean isMine(int row, int col) {
        return this.grid[row][col] == this.MINE;
    }

    private void updateCompleteness(){
        int totalCells = this.height * this.width;
        if(getUncoveredCells() + this.mines == totalCells){
            setBoardStatusTo(false);
            setCompleteTo(true);
        }
            
    }

    @Override 
    public void attach(IObserver o){
        this.userBoard = o;
    }

    @Override
    public void notify(int row, int col, int value){
        String strValue = (value == 0) ? "-" : 
                          (value == this.MINE) ? "+" :
                          (value == CellStatus.MARKED.getValue()) ? "P" :
                          (value == CellStatus.UNMARKED.getValue()) ? "." :
                            ""+value;
        this.userBoard.update(row, col, strValue);
    }

    private void setBoardStatusTo(boolean status){
        setPlayable(status);
    }
    
    public boolean getBoardStatus(){
        return getPlayable();
    }

    private void setPlayable(boolean p){
        this.playable = p;
    }

    private boolean getPlayable(){
        return this.playable;
    }

    private void setUncoveredCells(int cs){
        this.uncoveredCells = cs;
    }

    public int getUncoveredCells(){
        return this.uncoveredCells;
    }

    private void setCompleteTo(boolean c){
        this.complete = c;
    }

    public boolean getComplete(){
        return this.complete;
    }

}