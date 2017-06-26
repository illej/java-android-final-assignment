package nz.ac.ara.ayreye.finalassignment;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class GameActivity extends AppCompatActivity {

    ViewModel viewModel;
    Loader loader;

    private String[] fileList;
    private String filename;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String filename = intent.getStringExtra(MainActivity.EXTRA_FILENAME);
        String loadMethod = intent.getStringExtra(MainActivity.EXTRA_LOAD_METHOD);

        viewModel = new ViewModel(this);

        if (loadMethod.equals("assets")) {
            Log.d("> SELECTING LOADER", String.valueOf(loadMethod));
            loader = new AssetLoader(this);
        } else if (loadMethod.equals("internal")) {
            Log.d("> SELECTING LOADER", String.valueOf(loadMethod));
            loader = new InternalStorageLoader(this);
        }

        viewModel.setModels(loader, new InternalStorageSaver(this));
        viewModel.loadLevel(filename);

        GameView gameView = new GameView(this);
        gameView.setViewModel(viewModel);
        gameView.setClickable(true);

        setContentView(gameView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.save_game) {
            this.saveGameState();
            return true;
        } else if (id == R.id.load_game) {
            this.loadGameState();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    private void saveGameState() {
        AlertDialog.Builder builder = new AlertDialog.Builder(this);
        builder.setTitle("Enter a name:");

        // Set up the input
        final EditText input = new EditText(this);
        // Specify the type of input expected; this, for example, sets the input as a password, and will mask the text
        input.setInputType(InputType.TYPE_CLASS_TEXT);
        builder.setView(input);

        // Set up the buttons
        builder.setPositiveButton("Save", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                filename = input.getText().toString() + ".txt";
                viewModel.saveLevel(filename);
            }
        });
        builder.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });

        builder.show();
    }

    private void loadGameState() {
        this.populateFileList();

        LevelSelectFragment levelSelect = new LevelSelectFragment();
        Bundle args = new Bundle();
        args.putStringArray("levels", this.fileList);
        args.putString(MainActivity.EXTRA_LOAD_METHOD, "internal");
        levelSelect.setArguments(args);
        levelSelect.show(getFragmentManager(), "levels");
    }

    private void populateFileList() {

        String[] list = this.fileList(); //getAssets().list("");
        List<String> arrayList = new ArrayList<>();
        for (String file : list) {
            Log.d("GameActv Created: IntSt", file);
            if (file.endsWith(".txt")) {
                arrayList.add(file);
            }
        }
        this.fileList = new String[arrayList.size()];
        for (int i = 0; i < arrayList.size(); i++) {
            this.fileList[i] = arrayList.get(i);
        }

    }

    public static Bitmap decodeSampledBitmapFromResource(Resources res,
                                                         int resId,
                                                         int reqWidth,
                                                         int reqHeight) {
        // First decode with inJustDecodeBounds=true to check dimensions
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);

        // Decode bitmap with inSampleSize set
        options.inJustDecodeBounds = false;
        return BitmapFactory.decodeResource(res, resId, options);
    }

    public static int calculateInSampleSize(BitmapFactory.Options options,
                                            int reqWidth,
                                            int reqHeight) {
        // Raw height and width of image
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {

            final int halfHeight = height / 2;
            final int halfWidth = width / 2;

            // Calculate the largest inSampleSize value that is a power of 2 and keeps both
            // height and width larger than the requested height and width.
            while ((halfHeight / inSampleSize) > reqHeight
                    && (halfWidth / inSampleSize) > reqWidth) {
                inSampleSize *= 2;
            }
        }

        return inSampleSize;
    }
}
