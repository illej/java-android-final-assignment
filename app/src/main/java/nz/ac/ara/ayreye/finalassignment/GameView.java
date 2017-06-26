package nz.ac.ara.ayreye.finalassignment;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v4.view.MotionEventCompat;
import android.support.v7.widget.AppCompatImageView;
import android.view.MotionEvent;
import android.widget.Toast;

/**
 * Created by Elliot on 6/19/2017.
 */

public class GameView extends AppCompatImageView {

    private Context context;
    private ViewModel viewModel;
    // private GestureDetectorCompat gDetect;

    // unit conversion
    private double cellSize;
    private double xStart;
    private double yStart;

    // movement data
    private float x1 = 0;
    private float x2 = 0;
    private float y1 = 0;
    private float y2 = 0;
    private static final int MIN_DISTANCE = 100;

    // animation data
    private AnimatableRectF theseusRekt;
    private AnimatableRectF minotaurRekt;
    private AnimatableRectF exitRekt;

    public GameView(Context context) {
        super(context);
        this.context = context;
        // this.gDetect = new GestureDetectorCompat(context, new GestureListener());
    }

    public void setViewModel(ViewModel viewModel) {
        this.viewModel = viewModel;
    }

    private void initialiseRectFEntities() {
        this.theseusRekt = new AnimatableRectF(
                this.convertedUnitX(this.viewModel.wheresThes().across()),
                this.convertedUnitY(this.viewModel.wheresThes().down()),
                this.convertedUnitX(this.viewModel.wheresThes().across()) + (int) this.cellSize,
                this.convertedUnitY(this.viewModel.wheresThes().down()) + (int) this.cellSize);
        this.minotaurRekt = new AnimatableRectF(
                this.convertedUnitX(this.viewModel.wheresMin().across()),
                this.convertedUnitY(this.viewModel.wheresMin().down()),
                this.convertedUnitX(this.viewModel.wheresMin().across()) + (int) this.cellSize,
                this.convertedUnitY(this.viewModel.wheresMin().down()) + (int) this.cellSize);
        this.exitRekt = new AnimatableRectF(
                this.convertedUnitX(this.viewModel.wheresExit().across()),
                this.convertedUnitY(this.viewModel.wheresExit().down()),
                this.convertedUnitX(this.viewModel.wheresExit().across()) + (int) this.cellSize,
                this.convertedUnitY(this.viewModel.wheresExit().down()) + (int) this.cellSize);
    }

    @Override
    public void onDraw(Canvas canvas) {
        this.viewModel.checkWinState();
        this.setDimensions();
        this.drawBackground(canvas, Color.DKGRAY);
        this.drawLevel(canvas, this.viewModel.getWidthAcross(), this.viewModel.getDepthDown());

        // draw entities
        this.initialiseRectFEntities();

        // draw level title
        this.drawTitle(canvas, this.viewModel.getFilename());
        this.drawMoveCount(canvas, this.viewModel.getMoveCount());

        // exit -----------------------------------------------------------------------------------
        Bitmap bitmapExit = GameActivity.decodeSampledBitmapFromResource(
                getResources(),
                R.drawable.exit_3,
                (int) cellSize,
                (int) cellSize);
        int xExit = this.convertedUnitX(this.viewModel.wheresExit().across());
        int yExit = this.convertedUnitY(this.viewModel.wheresExit().down());
        this.drawEntityBitmap(canvas, xExit, yExit, bitmapExit);

        // theseus --------------------------------------------------------------------------------
        Bitmap bitmapTheseus = GameActivity.decodeSampledBitmapFromResource(
                getResources(),
                R.drawable.link,
                (int) cellSize,
                (int) cellSize);
        int xTheseus = this.convertedUnitX(this.viewModel.wheresThes().across());
        int yTheseus = this.convertedUnitY(this.viewModel.wheresThes().down());
        this.drawEntityBitmap(canvas, xTheseus, yTheseus, bitmapTheseus);

        // minotaur -------------------------------------------------------------------------------
        Bitmap bitmapMinotaur = GameActivity.decodeSampledBitmapFromResource(
                getResources(),
                R.drawable.ganon,
                (int) cellSize,
                (int) cellSize);
        int xMinotaur = this.convertedUnitX(this.viewModel.wheresMin().across());
        int yMinotaur = this.convertedUnitY(this.viewModel.wheresMin().down());
        this.drawEntityBitmap(canvas, xMinotaur, yMinotaur, bitmapMinotaur);
    }

    private void drawBackground(Canvas canvas, int color) {
        Paint p = new Paint();
        p.setColor(color);
        p.setStyle(Paint.Style.FILL);
        canvas.drawRect(
                0, 0,
                getMeasuredWidth(),
                getMeasuredHeight(),
                p);
    }

    private void drawLevel(Canvas canvas, int width, int height) {
        Bitmap bitmapLeftTop = GameActivity.decodeSampledBitmapFromResource(
                getResources(),
                R.drawable.left_top_2,
                (int) cellSize,
                (int) cellSize);
        Bitmap bitmapLeft = GameActivity.decodeSampledBitmapFromResource(
                getResources(),
                R.drawable.left_3,
                (int) cellSize,
                (int) cellSize);
        Bitmap bitmapTop = GameActivity.decodeSampledBitmapFromResource(
                getResources(),
                R.drawable.top_3,
                (int) cellSize,
                (int) cellSize);
        Bitmap bitmapBlank = GameActivity.decodeSampledBitmapFromResource(
                getResources(),
                R.drawable.blank_2,
                (int) cellSize,
                (int) cellSize);
        Bitmap bitmap = null;
        for (int i = 0; i < height; i++) {
            for (int j = 0; j < width; j++) {
                if (this.viewModel.whatsAbove(new Pointer(j, i)) == Wall.SOMETHING
                        && this.viewModel.whatsLeft(new Pointer(j, i)) == Wall.SOMETHING) {
                    bitmap = bitmapLeftTop;
                } else if (this.viewModel.whatsAbove(new Pointer(j, i)) == Wall.SOMETHING
                        && this.viewModel.whatsLeft(new Pointer(j, i)) == Wall.NOTHING) {
                    bitmap = bitmapTop;
                } else if (this.viewModel.whatsAbove(new Pointer(j, i)) == Wall.NOTHING
                        && this.viewModel.whatsLeft(new Pointer(j, i)) == Wall.SOMETHING) {
                    bitmap = bitmapLeft;
                } else {
                    bitmap = bitmapBlank;
                }
                canvas.drawBitmap(bitmap,
                        new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),
                        new Rect(
                                this.convertedUnitX(j),
                                this.convertedUnitY(i),
                                this.convertedUnitX(j) + (int) cellSize,
                                this.convertedUnitY(i) + (int) cellSize), null);
            }
        }
    }

    private void drawEntity(Canvas canvas, int x, int y, int color, AnimatableRectF rectF) {
        // TODO: tidy up
        Paint p = new Paint();
        p.setColor(color);
        p.setStyle(Paint.Style.FILL);
        if (rectF == null) {
            canvas.drawRect(
                    x, y,
                    x + (int) this.cellSize,
                    y + (int) this.cellSize,
                    p);
        } else {
            canvas.drawRect(
                    rectF,
                    p);
        }
    }

    private void drawEntityBitmap(Canvas canvas, int x, int y, Bitmap bitmap) {
        canvas.drawBitmap(bitmap,
                new Rect(0, 0, bitmap.getWidth(), bitmap.getHeight()),
                new Rect(x, y,
                        x + (int) cellSize,
                        y + (int) cellSize), null);
    }

    private void drawTitle(Canvas canvas, String title) {
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(50);
        double margin = getMeasuredWidth() * 0.05;
        String content = "level: " + title;
        canvas.drawText(content, (float) margin, (float) margin, p);
    }

    private void drawMoveCount(Canvas canvas, String count) {
        Paint p = new Paint();
        p.setColor(Color.WHITE);
        p.setTextSize(50);

        String content = "moves: " + count;
        float textWidth = p.measureText(content);
        double xMargin = (getMeasuredWidth() * 0.95) - textWidth;
        double yMargin = getMeasuredWidth() * 0.05;
        canvas.drawText(content, (float) xMargin, (float) yMargin, p);
    }

    /*
        MOVEMENT ----------------------------------------------------------------------------------
     */

    private void move_WITH_JACOBS_HACKS(/*Direction direction*/) {
        String result;
        if ((result = this.viewModel.checkWinState()) == null) {
            this.viewModel.moveMin();
            invalidate();
            try {
                this.setClickable(false);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        viewModel.moveMin();
                        invalidate();
                        setClickable(true);
                    }
                }, 300);
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            Toast.makeText(this.context, result, Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
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

                if (absXDelta > absYDelta
                        && absXDelta > MIN_DISTANCE) {
                    if (xDelta > 0) {
                        direction = Direction.RIGHT;
                    } else {
                        direction = Direction.LEFT;
                    }
                } else if (absYDelta > MIN_DISTANCE) {
                    if (yDelta > 0) {
                        direction = Direction.DOWN;
                    } else {
                        direction = Direction.UP;
                    }
                }

                if (absXDelta < MIN_DISTANCE
                        && absYDelta < MIN_DISTANCE) {
                    int xTh = convertedUnitX(this.viewModel.wheresThes().across());
                    int yTh = convertedUnitY(this.viewModel.wheresThes().down());
                    Rect pauseRect = new Rect(xTh, yTh, xTh + (int) cellSize, yTh + (int) cellSize);
                    if (x1 > pauseRect.left
                            && x1 < pauseRect.right
                            && y1 > pauseRect.top
                            && y1 < pauseRect.bottom) {
                        direction = null;
                        this.move_WITH_JACOBS_HACKS();
                    }
                }
                break;
        }
        invalidate();
        if (direction != null) {
            this.viewModel.moveThes(direction);
            this.move_WITH_JACOBS_HACKS(/*direction*/);
        }
        return super.onTouchEvent(event);
    }

    /*
        HELPER FUNCTIONS --------------------------------------------------------------------------
     */

    private int convertedUnitX(int num) {
        return (int) (num * this.cellSize + this.xStart);
    }

    private int convertedUnitY(int num) {
        return (int) (num * this.cellSize + this.yStart);
    }

    private void drawMessage(Canvas canvas, String message) {
        Paint p = new Paint();
        p.setColor(Color.YELLOW);
        p.setTextSize(70);
        canvas.drawText(message, 100, 100, p);
    }

    private void setDimensions() {
        double width = getMeasuredWidth();
        double height = getMeasuredHeight();

        double xCenter = width / 2;
        double yCenter = height / 2;

        double levelWidth = width * 0.9;
        double levelHeight = levelWidth;
        double levelXCenter = levelWidth / 2;
        double levelYCenter = levelHeight / 2;
        this.xStart = xCenter - levelXCenter;
        this.yStart = yCenter - levelYCenter;

        this.cellSize = levelWidth / this.viewModel.getWidthAcross();
    }

    private void toaster(String message) {
        Toast.makeText(this.context,
                message,
                Toast.LENGTH_SHORT).show();
    }

//    private ObjectAnimator setupAnimationX(float offset) {
//        ObjectAnimator anim = ObjectAnimator.ofFloat(
//                this.theseusAt,
//                "across()",
//                offset);
//        anim.setRepeatCount(0);
//        return anim;
//    }
//
//    private ObjectAnimator setupAnimationY(float offset) {
//        ObjectAnimator anim = ObjectAnimator.ofFloat(
//                this.theseusAt, // start
//                "down()", // property
//                offset); // target
//        anim.setRepeatCount(0);
//        return anim;
//    }

    /*
        GESTURE INNER CLASS -----------------------------------------------------------------------
     */

    /*public class GestureListener extends GestureDetector.SimpleOnGestureListener {

        private float flingMin = 100;
        private float velocityMin = 100;

        @Override
        public boolean onDown(MotionEvent event) {
            toaster("touched"); // works!
            return true;
        }

        @Override
        public boolean onFling(MotionEvent event1,
                               MotionEvent event2,
                               float velocityX,
                               float velocityY) {
            toaster("flung?");
            Log.d("motionE1", String.valueOf(event1));
            Log.d("motionE2", String.valueOf(event2));
            Log.d("velX", String.valueOf(velocityX));
            Log.d("velY", String.valueOf(velocityY));
            boolean forward = false;
            boolean backward = false;

            //calculate the change in X position within the fling gesture
            float horizontalDiff = event2.getX() - event1.getX();
            //calculate the change in Y position within the fling gesture
            float verticalDiff = event2.getY() - event1.getY();

            float absHDiff = Math.abs(horizontalDiff);
            float absVDiff = Math.abs(verticalDiff);
            float absVelocityX = Math.abs(velocityX);
            float absVelocityY = Math.abs(velocityY);

            if(absHDiff>absVDiff && absHDiff>flingMin && absVelocityX>velocityMin){
                //move forward or backward
                if(horizontalDiff>0) toaster("-> right");
                else toaster("left <-");
            } else if(absVDiff>flingMin && absVelocityY>velocityMin){
                if(verticalDiff>0) toaster("v down");
                else toaster("^ up");
            }
            return true;
        }
    }*/
}
