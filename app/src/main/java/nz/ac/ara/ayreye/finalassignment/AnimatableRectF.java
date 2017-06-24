package nz.ac.ara.ayreye.finalassignment;

import android.animation.ObjectAnimator;
import android.graphics.Rect;
import android.graphics.RectF;

/**
 * Created by Elliot on 6/20/2017.
 */

public class AnimatableRectF extends RectF{
    public AnimatableRectF() {
        super();
    }

    public AnimatableRectF(float left, float top, float right, float bottom) {
        super(left, top, right, bottom);
    }

    public AnimatableRectF(RectF r) {
        super(r);
    }

    public AnimatableRectF(Rect r) {
        super(r);
    }

    public void setTop(float top){
        this.top = top;
    }

    public void setBottom(float bottom){
        this.bottom = bottom;
    }

    public void setRight(float right){
        this.right = right;
    }

    public void setLeft(float left){
        this.left = left;
    }
}
