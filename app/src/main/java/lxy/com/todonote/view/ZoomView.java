package lxy.com.todonote.view;

import android.animation.Animator;
import android.animation.AnimatorSet;
import android.animation.ObjectAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatImageView;

/**
 * @date: 2019/12/17
 * @author lxy
 */
public class ZoomView extends AppCompatImageView {
    public ZoomView(Context context) {
        this(context, null);
    }

    public ZoomView(Context context, AttributeSet attrs) {
        this(context, attrs, 0);
    }

    public ZoomView(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int action = event.getAction();
        switch (action) {
            case MotionEvent.ACTION_DOWN:
                zoomIn();
                break;
            case MotionEvent.ACTION_CANCEL:
            case MotionEvent.ACTION_UP:
                zoomOut();
                break;
            default:
                break;
        }
        return super.onTouchEvent(event);
    }

    private void zoomIn() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(this, "scaleX", 1f, 0.8f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(this, "scaleY", 1f, 0.8f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(300).play(animatorX).with(animatorY);
        set.start();
    }

    private void zoomOut() {
        ObjectAnimator animatorX = ObjectAnimator.ofFloat(this, "scaleX", 0.8f, 1f);
        ObjectAnimator animatorY = ObjectAnimator.ofFloat(this, "scaleY", 0.8f, 1f);
        AnimatorSet set = new AnimatorSet();
        set.setDuration(300).play(animatorX).with(animatorY);
        set.start();
    }
}
