package as.mke.eatmem.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Camera;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.util.AttributeSet;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.LinearInterpolator;
import android.view.animation.RotateAnimation;
import android.view.animation.Transformation;
import android.widget.ImageView;


public class BookImageView extends androidx.appcompat.widget.AppCompatImageView {

    private BookAnim bookanim;
    private float angle;//记录RotateAnimation中受插值器数值影响的角度
    private float angle2;//主要用来记录暂停时停留的角度，即View初始旋转角度
    private int viewWidth;
    private int viewHeight;

    public static final int STATE_PLAYING=1;
    public static final int STATE_PAUSE=2;
    public  static final int STATE_STOP=3;

    public int state;

    Camera camera = new Camera();
    private  float rotate=0;
    private float interpolatedTime=0;
    int centerX, centerY;
    Transformation tr;
    public  BookImageView(Context ctx){
       super(ctx);
       init();
   }

   public  BookImageView(Context ctx, AttributeSet attr){
       super(ctx,attr);

       init();
   }

   public BookImageView(Context ctx, AttributeSet attr ,int defsty){
       super(ctx,attr,defsty);
       init();
   }


    private void init(){
        state = STATE_STOP;
        angle = 0;
        angle2 = 0;
        viewWidth = 0;
        viewHeight = 0;
    }


    public  void setRotate(int radius){
        rotate=radius;

    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        viewWidth = getMeasuredWidth();
        viewHeight = getMeasuredHeight();
    }

    @Override
    protected void onDraw(Canvas canvas) {

        super.onDraw(canvas);
    }


   public void turnpage(float rotate)
   {
       this.rotate=rotate;
       if(state == STATE_PLAYING){

       angle = (angle+ angle)%rotate;//可以取余也可以不取，看实际的需求
      bookanim.cancel();
      //stopturn();
       state = STATE_PAUSE;
       invalidate();
   }else {
       bookanim = new BookAnim();
       bookanim.setDuration(1000);
       bookanim.setFillAfter(true);
       bookanim.setInterpolator(new DecelerateInterpolator());//动画时间线性渐变
      // bookanim.setRepeatCount(ObjectAnimator.INFINITE);
       startAnimation(bookanim);
       state = STATE_PLAYING;
   }
   }

    public void stopturn(){
        angle = 0;
        clearAnimation();
        state = STATE_STOP;
        invalidate();
    }

    public class BookAnim extends Animation {

        public BookAnim(){
        }

        /**
         * 获取坐标，定义动画时间
         * @param width
         * @param height
         * @param parentWidth
         * @param parentHeight
         */
        @Override
        public void initialize(int width, int height, int parentWidth, int parentHeight) {
            super.initialize(width, height, parentWidth, parentHeight);
            //获得中心点坐标
            centerX = width/2;
            //width ;//乘以2像撕开掉纸
            centerY = width / 2;
            //动画执行时间 自行定义
           // setDuration(1000);
        //    setInterpolator(new DecelerateInterpolator());
        }

        /**
         * 旋转的角度设置
         * @param interpolatedTimes
         * @param t
         */
        @Override
        protected void applyTransformation(float interpolatedTimes, Transformation t) {
            final Matrix matrix = t.getMatrix();
            angle=rotate ;
                    //* interpolatedTimes;
            camera.save();
            //中心是Y轴旋转，这里可以自行设置X轴 Y轴 Z轴
            camera.rotateY((int)angle);//正数往右边翻
            //把我们的摄像头加在变换矩阵上
            camera.getMatrix(matrix);
            //设置翻转中心点
            matrix.preTranslate(-centerX, -centerY);
            matrix.postTranslate(centerX,centerY);
            camera.restore();




        }
    }
}
