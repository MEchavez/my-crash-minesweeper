public interface ISubject {
    public void attach(IObserver o);
    public void notify(int row, int col, int value);
}