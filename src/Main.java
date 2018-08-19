public class Main{
    public static void main(String[] args) {        
        Board b = new Board(5,5,3);
        UserView uv = new UserView(5,5,b);
        b.markCell(1,1);
        uv.paint();
        b.unmarkCell(1,1);
        uv.paint();
        b.uncover(2,2);
        uv.paint();
        System.out.printf("Total cells: %d%nMines: %d%nUncovered Cells: %d%n",
            25, 3, b.getUncoveredCells());
    }
}