package nz.ac.ara.ayreye.finalassignment;

import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;

public class GameActivity extends AppCompatActivity {

    ViewModel viewModel;
    ImageView theseusIV;
    ImageView minotaurIV;

    private float x1 = 0;
    private float x2 = 0;
    private float y1 = 0;
    private float y2 = 0;
    private static final int MIN_DISTANCE = 100;
    private double xStart = 0;
    private double yStart = 0;
    private double cellSize = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        Intent intent = getIntent();
        String filename = intent.getStringExtra(MainActivity.EXTRA_FILENAME);

        viewModel = new ViewModel(this);
        viewModel.loadLevel(filename);

        GameView gameView = new GameView(this);
        gameView.setViewModel(viewModel);
        gameView.setClickable(true);

        setContentView(gameView);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.game_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.save_game) {
            return true;
        } else if (id == R.id.load_game) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }
/*
    private void setImages() {
        theseusIV = (ImageView) findViewById(R.id.theseusIV);
        minotaurIV = (ImageView) findViewById(R.id.minotaurIV);

        theseusIV.layout(0, 0, (int) cellSize, (int) cellSize);
        float xTheseusOld = convertedUnitX(viewModel.wheresThes().across());
        float yTheseusOld = convertedUnitY(viewModel.wheresThes().down());
        this.theseusIV.setX(xTheseusOld);
        this.theseusIV.setY(yTheseusOld);

        float xMinotaurOld = convertedUnitX(viewModel.wheresMin().across());
        float yMinotaurOld = convertedUnitY(viewModel.wheresMin().down());
        this.minotaurIV.setX(xMinotaurOld);
        this.minotaurIV.setY(yMinotaurOld);
    }

    private int convertedUnitX(int num) {
        return (int) (num * this.cellSize + this.xStart);
    }

    private int convertedUnitY(int num) {
        return (int) (num * this.cellSize + this.yStart);
    }

    private void setDimensions() {
        DisplayMetrics displayMetrics = this.getResources().getDisplayMetrics();
        float dpHeight = displayMetrics.heightPixels / displayMetrics.density;
        float dpWidth = displayMetrics.widthPixels / displayMetrics.density;

        double width = (double) dpWidth;
        double height = (double) dpHeight;
        Log.d("width", String.valueOf(width));
        Log.d("height", String.valueOf(height));

        double xCenter = width / 2;
        double yCenter = height / 2;
        Log.d("xCenter", String.valueOf(xCenter));
        Log.d("yCenter", String.valueOf(yCenter));

        double levelWidth = width * 0.9; // x
        double levelHeight = levelWidth;
        double levelXCenter = levelWidth / 2;
        double levelYCenter = levelHeight / 2;
        this.xStart = xCenter - levelXCenter;
        this.yStart = yCenter - levelYCenter;
        Log.d("xStart", String.valueOf(this.xStart));
        Log.d("yStart", String.valueOf(this.yStart));

        this.cellSize = levelWidth / this.viewModel.getWidthAcross(); // x
        Log.d("this.cellSize", String.valueOf(this.cellSize));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        // this.gDetect.onTouchEvent(event);

        Direction direction = null;
        int action = MotionEventCompat.getActionMasked(event);
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                x1 = event.getX();
                y1 = event.getY();
                break;
            case MotionEvent.ACTION_UP:
                x2 = event.getX();
                y2 = event.getY();

                float xDelta = x2 - x1;
                float yDelta = y2 - y1;

                float absXDelta = Math.abs(xDelta);
                float absYDelta = Math.abs(yDelta);

                if(absXDelta > absYDelta
                        && absXDelta > MIN_DISTANCE){
                    //move forward or backward
                    if(xDelta > 0) {
                        toaster("-> right");
                        direction = Direction.RIGHT;
                    } else {
                        toaster("left <-");
                        direction = Direction.LEFT;
                    }
                } else if(absYDelta > MIN_DISTANCE){
                    if(yDelta > 0) {
                        toaster("v down");
                        direction = Direction.DOWN;
                    } else {
                        toaster("^ up");
                        direction = Direction.UP;
                    }
                }
                break;
        }
        // invalidate();
        if (direction != null) {
            // this.move(direction);
            this.moveT(direction);
        }
        return super.onTouchEvent(event);
    }

    private void moveT(Direction direction) {

        int xTheseusOld = convertedUnitX(viewModel.wheresThes().across());
        int yTheseusOld = convertedUnitY(viewModel.wheresThes().down());
        viewModel.moveThes(direction);
        int xTheseusNew = convertedUnitX(viewModel.wheresThes().across());
        int yTheseusNew = convertedUnitY(viewModel.wheresThes().down());

        int xMinotaurOld1 = convertedUnitX(viewModel.wheresMin().across());
        int yMinotaurOld1 = convertedUnitY(viewModel.wheresMin().down());
        viewModel.moveMin();
        int xMinotaurNew1 = convertedUnitX(viewModel.wheresMin().across());
        int yMinotaurNew1 = convertedUnitY(viewModel.wheresMin().down());

        int xMinotaurOld2 = convertedUnitX(viewModel.wheresMin().across());
        int yMinotaurOld2 = convertedUnitY(viewModel.wheresMin().down());
        viewModel.moveMin();
        int xMinotaurNew2 = convertedUnitX(viewModel.wheresMin().across());
        int yMinotaurNew2 = convertedUnitY(viewModel.wheresMin().down());

        ObjectAnimator xTheseusAnim = ObjectAnimator.ofFloat(this.theseusIV, "x", xTheseusOld + (xTheseusNew - xTheseusOld));
        ObjectAnimator yTheseusAnim = ObjectAnimator.ofFloat(this.theseusIV, "y", yTheseusOld + (yTheseusNew - yTheseusOld));

        ObjectAnimator xMinotaurAnim1 = ObjectAnimator.ofFloat(this.minotaurIV, "x", xMinotaurOld1 + (xMinotaurNew1 - xMinotaurOld1));
        ObjectAnimator yMinotaurAnim1 = ObjectAnimator.ofFloat(this.minotaurIV, "y", yMinotaurOld1 + (yMinotaurNew1 - yMinotaurOld1));

        ObjectAnimator xMinotaurAnim2 = ObjectAnimator.ofFloat(this.minotaurIV, "x", xMinotaurOld2 + (xMinotaurNew2 - xMinotaurOld2));
        ObjectAnimator yMinotaurAnim2 = ObjectAnimator.ofFloat(this.minotaurIV, "y", yMinotaurOld2 + (yMinotaurNew2 - yMinotaurOld2));

        xTheseusAnim.start();
        yTheseusAnim.start();
        xMinotaurAnim1.start();
        yMinotaurAnim1.start();
        xMinotaurAnim2.start();
        yMinotaurAnim2.start();
    }

    private void toaster(String message) {
        Toast.makeText(this,
                message,
                Toast.LENGTH_SHORT).show();
    }
*/
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
