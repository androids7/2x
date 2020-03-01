package as.mke.eatmem;


import android.annotation.SuppressLint;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import as.mke.eatmem.adapter.FirstPagerAdapter;
import as.mke.eatmem.animation.MyAnimation;
import as.mke.eatmem.decoder.GVSDecoder;
import as.mke.eatmem.processor.UInter;
import as.mke.eatmem.view.BookImageView;
import as.mke.eatmem.view.GradientColorTextView;

public class WithUInter extends AppCompatActivity {

    private String TAG="WithUInter";

    public boolean isDEBUG() {
        return DEBUG;
    }

    public void setDEBUG(boolean DEBUG) {
        this.DEBUG = DEBUG;
    }

    boolean DEBUG;
    ViewPager viewPager;
    FirstPagerAdapter firstPagerAdapter;
    List<View> firstView;
    LayoutInflater lf;
    private View view1,view2,view3;
    UInter parx;
    View v;

    ByteArrayOutputStream bao;
    ViewPager vp;
    MyAnimation my,my2;
    PrintStream ps;
    ImageView book;
    BookImageView pager;
     int NUMPAGES=3;
     Context context;
    LinearLayout circleLayout;
    List<ImageView> guidePointList;
    float positionOffset=0;
    int oldposition=0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//需要传入ui设计给的大小,初始化

        this.context=this;
        bao=new ByteArrayOutputStream();
        ps=new PrintStream(bao);
        System.setOut(ps);
        System.setErr(ps);


        parx = new UInter(this);
        try {
            long time = System.currentTimeMillis();

            String filename="firstuse.xml";
         int size=   getAssets().open(filename).available();
         byte[]  data=new byte[size];
            getAssets().open(filename).read(data);
             v= parx.parx(new String(data));


//


            setContentView(R.layout.guider_pager);

            circleLayout=findViewById(R.id.circlelayout);
            book=findViewById(R.id.imagebook);
            pager=findViewById(R.id.imagepager);
        //    pagerleft=findViewById(R.id.imagepagerleft);

          //  Animation rotate= AnimationUtils.loadAnimation(this,R.anim.bookrotate);
           my=new MyAnimation();
            my2=new MyAnimation();
           // my.setRepeatCount(Animation.INFINITE); //旋转的次数（无数次）
            my.setFillEnabled(true);

          //  my.setRepeatCount(Animation.INFINITE);
            my.setRepeatMode(Animation.RESTART);
            vp=findViewById(R.id.viewpagers);

            firstView=new ArrayList<View>();
            firstPagerAdapter=new FirstPagerAdapter(firstView);
            lf= getLayoutInflater().from(this);

            view1=lf.inflate(R.layout.page1,null);
            view2=lf.inflate(R.layout.page2,null);
            firstView.add(view1);
            firstView.add(view2);
            firstView.add(new TextView(this));

            NUMPAGES=firstView.size();
            addGuidePointTolist();
            vp.setAdapter(firstPagerAdapter);



            vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


                @Override
                public void onPageScrolled(int position, float positionOffsett, int positionOffsetPixels) {





                    pager.turnpage(-180 * positionOffset);
                    positionOffset=positionOffsett;
                }


                @Override
                public void onPageSelected(int position) {


                    pager.stopturn();
                    setGuidePoint(position);

                    /*
                    switch (position){
                        case 0:

                            pagerleft.turnpage(180);


                            break;
                        case 1:
                            if(oldposition==0)
                            pager.turnpage(-180);
                            if(oldposition==2)
                                pagerleft.turnpage(180);

                            break;
                        case 2:
                            pager.turnpage(-180);
                            break;
                    }
*/
                    oldposition=position;


                }

                @Override
                public void onPageScrollStateChanged(int state) {


                }

            });

           // char[] arr=
            new Thread(new Runnable() {
                @Override
                public void run() {

                }
            }).start();










            //init();

            @SuppressLint("SdCardPath") String filenames="/sdcard/.cc/log.txt";

            new File(filenames).delete();
            FileWriter fw=new FileWriter(filenames);
            fw.write(new String(bao.toByteArray()));
            fw.close();


            long timeAfter = System.currentTimeMillis()-time;
      //      ((TextView)findViewById(parx.getIds().get("time"))).setText(timeAfter+"ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     设置引导点的状态(选中 |未选中)
     */
     private void setGuidePoint(int position) {
         for (int i = 0; i < guidePointList.size(); i++) {
             if (position == i) {
                 guidePointList.get(i).setImageResource(R.drawable.shape_guide_point_active);
             } else {
                 guidePointList.get(i).setImageResource(R.drawable.shape_guide_point_deactive);
             }
         }
     }
    /**
     *添加引导点到list
     * */
     private void addGuidePointTolist() {
         guidePointList=new ArrayList<ImageView>();
     float scale = getResources() . getDisplayMetrics() . density;
     int padding = (int) (3 * scale + 0.5f);
     for(int i=0;i<NUMPAGES;i++){
     ImageView circle = new ImageView( context) ;
     circle.setImageResource(R.drawable.shape_guide_point_deactive) ;
     circle.setLayoutParams(new ViewGroup.LayoutParams(ViewGroup. LayoutParams.WRAP_CONTENT,ViewGroup. LayoutParams . WRAP_CONTENT));

     circle.setAdjustViewBounds(true);
     circle.setPadding(padding, 0, padding, 0);
     circleLayout. addView(circle);
     guidePointList.add(circle);
         setGuidePoint(0);
     }


     }

     /**
     * dp和像素转换
     */
    private int dp2px(Context context, float dipValue) {
        float m = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * m + 0.5f);
    }

    public  void init() {
        viewPager=findViewById(parx.getIds().get("viewpager"));
        firstView=new ArrayList<View>();
        firstPagerAdapter=new FirstPagerAdapter(firstView);

        final FloatingActionButton floating1,floating2;
        GradientColorTextView hi,hello;

        lf= getLayoutInflater().from(this);

        view1=lf.inflate(R.layout.page1,null);

       // floating1=view1.findViewById(R.id.floatingactionbar);
        final TranslateAnimation tran=new TranslateAnimation(0,0,300,0);
        tran.setFillAfter(false);//动画结束之后回到原点，默认
        tran.setDuration(1000);
      //  floating1.setAnimation(tran);

      //  hello=view1.findViewById(R.id.hello);
      //  hi=view1.findViewById(R.id.hi);
        AlphaAnimation alpha=new AlphaAnimation(0.0f,1.f);
        alpha.setDuration(1800);
      //  hello.setAnimation(alpha);
      //  hi.setAnimation(alpha);


        view2=lf.inflate(R.layout.page2,null);
      //  floating2=view2.findViewById(R.id.floatingactionbar);

        final TranslateAnimation tran2=new TranslateAnimation(0,0,300,0);
        tran2.setFillAfter(false);//动画结束之后回到原点，默认
        tran2.setDuration(1000);
       // floating2.setAnimation(tran2);


        firstView.add(view1);
        firstView.add(view2);
        viewPager.setAdapter(firstPagerAdapter);

    }

    /**
     * byte数组中取int数值
     *
     * @param src
     *            byte数组
     * @return int数值
     */
    public static int bytesToInt(byte[] src) {
        int value;
        value = (int) ((src[0] & 0xFF)
                | ((src[1] & 0xFF)<<8)
                | ((src[2] & 0xFF)<<16)
                | ((src[3] & 0xFF)<<24));
        return value;

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

    }
}
