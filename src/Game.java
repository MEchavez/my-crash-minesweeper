public class Game {

    private Board board;
    private UserView uview;
    private int gameStatus;

    public Game(int height, int width, int mines){
        this.board = new Board(height, width,mines);
        this.uview = new UserView(height, width, this.board);
        this.gameStatus = GameStatus.PLAYING.getValue();
    }

    public boolean uncoverCell(int row,int col){
        if(this.board.uncover(row,col)){
            updateStatus();
            this.uview.paint();
            return true;
        }
        return false;
    }

    public boolean markCell(int row, int col){
        if(this.board.markCell(row, col)){
            this.uview.paint();
            return true;
        }            
        return false;
    }

    public boolean unmarkCell(int row, int col){
        if(this.board.unmarkCell(row, col)){
            this.uview.paint();
            return true;
        }            
        return false;
    }

    public int getStatus(){
        return this.gameStatus;
    }

    private void setStatus(int gameStatus){
        this.gameStatus = gameStatus;
    }

    private void updateStatus(){
        int status = this.board.getBoardStatus() ? GameStatus.PLAYING
            .getValue() : GameStatus.DEFEAT.getValue();
        setStatus(status);
    }

    public boolean isComplete(){
        return this.board.getComplete();
    }

    public void printBoard(){
        this.uview.paint();
    }
}