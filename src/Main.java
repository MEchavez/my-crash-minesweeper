import java.util.Scanner;
import java.util.regex.Pattern;
import java.util.regex.Matcher;

public class Main{
    public static void main(String[] args) {

        Scanner sc = new Scanner(System.in);
        String userInput;
        int row = -1, col = -1;
        int height = -1, width = -1, mines = -1;
        String operation = "-1";
        Pattern movePattern = Pattern.compile("(\\d)+ (\\d)+ [UMR]");
        Pattern settingsPattern = Pattern.compile("(\\d)+ (\\d)+ (\\d)+");
        Matcher matcher;
        String[] inputTokens;
        final String UNCOVER = "U";
        final String UNMARK = "R";
        final String MARK = "M";
        boolean match;

        do{
            System.out.println("\nEnter you game settings in the" 
                + " following order:");
            System.out.print("number of <rows> <cols> <mines>\n>> ");
            userInput = sc.nextLine();
            matcher = settingsPattern.matcher(userInput);
        }while(!matcher.matches());
        
        inputTokens = userInput.split(" ");
        height = Integer.parseInt(inputTokens[0]);
        width = Integer.parseInt(inputTokens[1]);
        mines = Integer.parseInt(inputTokens[2]);

        Game g = new Game(height, width, mines);
        System.out.println();
        g.printBoard();

        while(g.getStatus() == GameStatus.PLAYING.getValue()){
            
            do{
                System.out.print("Next move >> ");
                userInput = sc.nextLine();
                matcher = movePattern.matcher(userInput);
                match = matcher.matches();
                if(match){
                    inputTokens = userInput.split(" ");
                    row = Integer.parseInt(inputTokens[0]);
                    col = Integer.parseInt(inputTokens[1]);
                    operation = inputTokens[2];
                }
            }while(!match ||
                !(row > 0 && row <= height && col > 0 && col <= width));
            
            System.out.println();
            switch(operation){
                case UNCOVER:
                    if(!g.uncoverCell(row, col))
                        System.out.println("Can't uncover that cell");
                    break;
                case MARK:
                    if(!g.markCell(row, col))
                        System.out.println("Can't mark that cell");
                    break;
                case UNMARK:
                    if(!g.unmarkCell(row, col))
                        System.out.println("Can't unmark that cell");
                    break;
            }
            
        }
        
        if(g.isComplete())
            System.out.println("Congratulations! you win :)\n");
        else if(g.getStatus() == GameStatus.DEFEAT.getValue())
            System.out.println("Game Over :(\n");

    }
}