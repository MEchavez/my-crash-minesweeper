public enum CellStatus {

    UNCOVERED(-2),
    MARKED(-3),
    UNMARKED(-4);

    private int value;

    CellStatus(int value){
        this.value = value;
    }

    public int getValue(){
        return value;
    }
}