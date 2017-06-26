package nz.ac.ara.ayreye.finalassignment;

import android.app.Activity;
import android.app.FragmentManager;
import android.content.Context;
import android.media.MediaPlayer;
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

    private String filename = null;
    private int moveCount = 0;

    public ViewModel(Context context) {
        this.context = context;
    }

    public void setModels(Loader newLoader, Saver newSaver) {
        Game game = new Game(
                newLoader, //new AssetLoader(this.context)/*FileLoader()*/,
                newSaver); //new InternalStorageSaver(this.context)/*FileSaver()*/);
        playable = game;
        loadable = game;
        saveable = game;
        loader = game;
        saver = game;
    }

    @Override
    public void loadLevel(String filename) {
        this.loader.load(this.loadable, filename);
        this.filename = filename;
    }

    @Override
    public void loadLevel_INTERNAL_STORAGE(Loader internalStorageLoader, String filename) {
        internalStorageLoader.load(this.loadable, filename);
    }

    @Override
    public void saveLevel(String filename) {
        Toast.makeText(this.context, "saved: [" + filename + "]", Toast.LENGTH_SHORT).show();
        this.saver.save(this.saveable, filename);
    }

    @Override
    public String getMoveCount() {
        return String.valueOf(this.moveCount);
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
        MediaPlayer player;

        if (this.wheresThes().across() == this.wheresMin().across()
                && this.wheresThes().down() == this.wheresMin().down()) {
            result = "lose";
            title = "Game Over!";
            player = MediaPlayer.create(this.context, R.raw.mk_lose);
            player.start();
            this.endGame(title);
        } else if (this.wheresThes().across() == this.wheresExit().across()
                && this.wheresThes().down() == this.wheresExit().down()) {
            result = "win";
            title = "Victory!";
            player = MediaPlayer.create(this.context, R.raw.ff7_victory);
            player.start();
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

    public String getFilename() {
        return this.filename;
    }
}
