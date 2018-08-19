public enum GameStatus {

    WIN(1),
    DEFEAT(2),
    PLAYING(3);

    private int status;

    GameStatus(int status){
        this.status = status;
    }

    public int getValue(){
        return this.status;
    }
}