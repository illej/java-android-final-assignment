package nz.ac.ara.ayreye.finalassignment;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.os.Bundle;
import android.widget.Toast;

/**
 * Created by Elliot on 6/19/2017.
 */

public class ViewModel implements ViewModelable {

    private Context context;

    private Playable playable;
    private Loadable loadable;
    private Saveable saveable;
    private Loader loader;
    private Saver saver;

    private String filename;
    private int moveCount = 0;

    public ViewModel(Context context) {
        this.context = context;

        this.setModels();
        //this.newGame();
    }

    private void setModels() {
        Game game = new Game(new AssetLoader(this.context)/*FileLoader()*/, new FileSaver());
        playable = game;
        loadable = game;
        saveable = game;
        loader = game;
        saver = game;
    }

    private void newGame() {
        this.loadable.setDepthDown(200);
        this.loadable.setWidthAcross(200);
        this.loadable.addTheseus(new Pointer(10, 20));
    }

    public void loadLevel(String filename) {
        this.loader.load(this.loadable, filename);
        this.filename = filename;
    }

    @Override
    public void saveLevel() {
        Toast.makeText(this.context, "saved!", Toast.LENGTH_SHORT).show();
    }

    @Override
    public int getMoveCount() {
        return this.moveCount;
    }

    @Override
    public int getDepthDown() {
        return this.saveable.getDepthDown();
    }

    @Override
    public int getWidthAcross() {
        return this.saveable.getWidthAcross();
    }

    @Override
    public Point wheresThes() {
        return this.saveable.wheresTheseus();
    }

    @Override
    public Point wheresMin() {
        return this.saveable.wheresMinotaur();
    }

    @Override
    public Point wheresExit() {
        return this.saveable.wheresExit();
    }

    @Override
    public Wall whatsAbove(Point point) {
        return this.saveable.whatsAbove(point);
    }

    @Override
    public Wall whatsLeft(Point point) {
        return this.saveable.whatsLeft(point);
    }

    @Override
    public void moveThes(Direction direction) {
        this.moveCount++;
        this.playable.moveTheseus(direction);
    }

    @Override
    public void moveMin() {
        this.playable.moveMinotaur();
    }

    @Override
    public String checkWinState() {
        String result = null;
        String title = null;
        if (this.wheresThes() == null) {
            result = "lose";
            title = "Game Over! ~:<";
            this.endGame(title);
        } else if (this.wheresThes().across() == this.wheresExit().across()
                && this.wheresThes().down() == this.wheresExit().down()) {
            result = "win";
            title = "Victory! c|:D";
            this.endGame(title);
        }
        return result;
    }

    private void endGame(String title) {
        FragmentManager fragmentManager = ((Activity) this.context).getFragmentManager();
        EndGameFragment endGame = new EndGameFragment();
        Bundle args = new Bundle();
        args.putString("title", title);
        args.putString("currentLevel", this.filename);
        endGame.setArguments(args);
        endGame.show(fragmentManager, "title");
    }

    public String getLevelDatas() {
        return this.filename;
    }
}
