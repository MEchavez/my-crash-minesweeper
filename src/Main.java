public class Main{
    public static void main(String[] args) {        
        Board b = new Board(5,5,3);
        UserView uv = new UserView(5,5,b);
        b.uncover(2,2);
        uv.paint();
    }
}