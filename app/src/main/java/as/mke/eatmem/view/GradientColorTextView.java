package as.mke.eatmem.view;

import android.graphics.Typeface;
import android.util.Log;
import android.widget.TextView;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.Shader;
import android.util.AttributeSet;

import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by Haron on 2017/11/14.
 */

public class GradientColorTextView extends AppCompatTextView {

    private LinearGradient mLinearGradient;
    private Paint mPaint;
    private int mViewWidth = 0;
    private Rect mTextBound = new Rect();
    private String TAG="GradientColorTextView";
    private  Context c;
    public GradientColorTextView (Context context, AttributeSet attrs) {
        super(context, attrs);
        this.c=context;
    }

    public GradientColorTextView (Context context) {
        super(context);
        this.c=context;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        mViewWidth = getMeasuredWidth();
        mPaint = getPaint();
        mPaint.setTypeface(Typeface.createFromAsset(c.getAssets(),"font.ttf"));
        String mTipText = getText().toString();
        mPaint.getTextBounds(mTipText, 0, mTipText.length(), mTextBound);
        mLinearGradient = new LinearGradient(0, 0, mViewWidth, 0,
                new int[]{0xFFF56608, 0xFF3DA6F8},
                null, Shader.TileMode.REPEAT);
        mPaint.setShader(mLinearGradient);
        Log.d(TAG,""+mTextBound);
        canvas.drawText(mTipText, getMeasuredWidth() / 2 - mTextBound.width() / 2, getMeasuredHeight() / 2 + mTextBound.height() / 2, mPaint);
    }
}