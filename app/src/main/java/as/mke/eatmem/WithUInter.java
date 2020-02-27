package as.mke.eatmem;


import android.content.res.Resources;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import as.mke.eatmem.adapter.FirstPagerAdapter;
import as.mke.eatmem.adapter.ScreenAdaptation;
import as.mke.eatmem.decoder.GVSDecoder;
import as.mke.eatmem.processor.UInter;
import as.mke.eatmem.view.GradientColorTextView;

public class WithUInter extends AppCompatActivity {

    static {
        System.loadLibrary("decoder-lib");
    }
    private String TAG="WithUInter";

    ViewPager viewPager;
    FirstPagerAdapter firstPagerAdapter;
    List<View> firstView;
    LayoutInflater lf;
    private View view1,view2,view3;
    UInter parx;
    View v;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//需要传入ui设计给的大小,初始化


       // parx = new UInter(this);
        try {
            long time = System.currentTimeMillis();

            String filename="firstuse.xml";
         int size=   getAssets().open(filename).available();
         byte[]  data=new byte[size];
            getAssets().open(filename).read(data);
            // v= parx.parx(new String(data));
            ImageView iv=new ImageView(this);

//            GVSDecoder g=new GVSDecoder();
            InputStream in= getAssets().open("tiger.svg");
            byte [] by=new byte[in.available()];
            in.read(by);

            String str=new String(by);
            setContentView(iv);

            int[] arr=getPixels(str);
                    //str);
                    //R.layout.page1);
         //   Bitmap bmp=Bitmap.createBitmap(arr,800,800,Bitmap.Config.ARGB_8888);

        //    iv.setImageBitmap(bmp);

            // init();


            long timeAfter = System.currentTimeMillis()-time;
      //      ((TextView)findViewById(parx.getIds().get("time"))).setText(timeAfter+"ms");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    public  void init() {
        viewPager=findViewById(parx.getIds().get("viewpager"));
        firstView=new ArrayList<View>();
        firstPagerAdapter=new FirstPagerAdapter(firstView);

        final FloatingActionButton floating1,floating2;
        GradientColorTextView hi,hello;

        lf= getLayoutInflater().from(this);

        view1=lf.inflate(R.layout.page1,null);

        floating1=view1.findViewById(R.id.floatingactionbar);
        final TranslateAnimation tran=new TranslateAnimation(0,0,300,0);
        tran.setFillAfter(false);//动画结束之后回到原点，默认
        tran.setDuration(1000);
        floating1.setAnimation(tran);

        hello=view1.findViewById(R.id.hello);
        hi=view1.findViewById(R.id.hi);
        AlphaAnimation alpha=new AlphaAnimation(0.0f,1.f);
        alpha.setDuration(1800);
        hello.setAnimation(alpha);
        hi.setAnimation(alpha);


        view2=lf.inflate(R.layout.page2,null);
        floating2=view2.findViewById(R.id.floatingactionbar);

        final TranslateAnimation tran2=new TranslateAnimation(0,0,300,0);
        tran2.setFillAfter(false);//动画结束之后回到原点，默认
        tran2.setDuration(1000);
        floating2.setAnimation(tran2);


        firstView.add(view1);
        firstView.add(view2);
        viewPager.setAdapter(firstPagerAdapter);

    }
    public native int[] getPixels(String xmldata);
}
