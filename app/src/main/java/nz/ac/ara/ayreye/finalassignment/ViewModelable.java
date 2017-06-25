package nz.ac.ara.ayreye.finalassignment;

/**
 * Created by Jacob on 19/06/2017.
 */

public interface ViewModelable {
    void loadLevel(String filename);
    void loadLevel_INTERNAL_STORAGE(Loader isLoader, String filename);
    void saveLevel(String filename);
    String getMoveCount();
    int getDepthDown();
    int getWidthAcross();
    Point wheresThes();
    Point wheresMin();
    Point wheresExit();
    Wall whatsAbove(Point point);
    Wall whatsLeft(Point point);
    void moveThes(Direction direction);
    void moveMin();
    String checkWinState();
}
