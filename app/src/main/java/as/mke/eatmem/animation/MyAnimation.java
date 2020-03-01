package as.mke.eatmem.animation;
import android.graphics.Camera;
import android.graphics.Matrix;
import android.view.animation.Animation;
import android.view.animation.DecelerateInterpolator;
import android.view.animation.Transformation;

/**
 * 自定义Y轴旋转动画
 * Created by Administrator on 2017/2/10.
 */

public class MyAnimation extends Animation {
    int centerX, centerY;

    int rotate=0;
    Camera camera = new Camera();

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
        setDuration(1000);
        setInterpolator(new DecelerateInterpolator());
    }

    /**
     * 旋转的角度设置
     * @param interpolatedTime
     * @param t
     */
    @Override
    protected void applyTransformation(float interpolatedTime, Transformation t) {
        final Matrix matrix = t.getMatrix();

        camera.save();
        //中心是Y轴旋转，这里可以自行设置X轴 Y轴 Z轴
        camera.rotateY(-rotate * interpolatedTime);//正数往右边翻
        //把我们的摄像头加在变换矩阵上
        camera.getMatrix(matrix);
        //设置翻转中心点
        matrix.preTranslate(-centerX, -centerY);
        matrix.postTranslate(centerX,centerY);
        camera.restore();
    }
    public  void setRotate(int radius){
        this.rotate=radius;

    }
}