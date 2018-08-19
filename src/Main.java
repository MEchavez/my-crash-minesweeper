import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main{
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String userInput;
        int row, col;
        String operation;
        Pattern pattern = Pattern.compile("(\\d)+ (\\d)+ [UMR]");
        Matcher matcher;
        String[] inputTokens;
        //ask for height, width and mines before create new game
        Game g = new Game(5, 5, 3);
        g.printBoard();

        while(g.getStatus() == GameStatus.PLAYING.getValue()){
            do{
                System.out.print("Next move >> ");
                userInput = sc.nextLine();
                matcher = pattern.matcher(userInput);
            }while(!matcher.matches());
            inputTokens = userInput.split(" ");
            row = Integer.parseInt(inputTokens[0]);
            col = Integer.parseInt(inputTokens[1]);
            operation = inputTokens[2];
            System.out.println();
            g.uncoverCell(row, col);            
        }
        if(g.getStatus() == GameStatus.DEFEAT.getValue())
            System.out.println("Game Over :(");


    }
}