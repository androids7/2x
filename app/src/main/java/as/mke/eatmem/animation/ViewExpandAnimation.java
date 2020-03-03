package as.mke.eatmem.animation;
import android.view.View;
import android.view.animation.AccelerateInterpolator;
import android.view.animation.Animation;
import android.view.animation.Transformation;
import android.widget.AbsListView.LayoutParams;

public class ViewExpandAnimation extends Animation {

    private View mAnimationView = null;
    private LayoutParams mViewLayoutParams = null;
    private int mStart = 0;
    private int mEnd = 0;

    public ViewExpandAnimation(View view){
        animationSettings(view, 500);
    }

    public ViewExpandAnimation(View view, int duration){
        animationSettings(view, duration);
    }

    private void animationSettings(View view, int duration){
        setDuration(duration);
        setInterpolator(new AccelerateInterpolator());
        mAnimationView = view;
        mViewLayoutParams = (LayoutParams) view.getLayoutParams();
        mStart = 0;
        mEnd = mViewLayoutParams.height;
       // view.setVisibility(View.VISIBLE);
    }

    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        super.applyTransformation(interpolatedTime, t);

        if(interpolatedTime < 1.0f){
            mViewLayoutParams.height = mStart + (int) ((mEnd - mStart) * interpolatedTime);
            // invalidate
            mAnimationView.requestLayout();
        }else{
            mViewLayoutParams.height = mEnd;
            mAnimationView.requestLayout();

        }
    }
}