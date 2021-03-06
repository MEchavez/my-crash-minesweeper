import java.util.Arrays;

public class UserView implements IObserver {
    
    private String[][] grid;
    private int height;
    private int width;

    public UserView(int height, int width, Board b){
        cleanBuild(height, width);
        b.attach(this);
        this.height = height;
        this.width = width;
    }

    private void cleanBuild(int height, int width){
        this.grid = new String[height][width];
        for(String[] a : this.grid) Arrays.fill(a, ".");
    }

    @Override
    public void update(int row, int col, String value){
        this.grid[row][col] = value;
    }

    public void paint(){
        System.out.printf("%7s", " ");
        for(int x=0; x<width; x++)
            System.out.printf("  %3s", String.valueOf(x+1));
        System.out.print("\n\t");
        for(int x=0; x<width*5; x++)
            System.out.print("-");
        System.out.println();
        for(int row=0; row<height; row++){
            System.out.printf("%5s |", ""+(row+1));
            for(int col=0; col<width; col++){
                System.out.printf("  %3s",""+this.grid[row][col]);
            }
            System.out.println();
        }
    }

}