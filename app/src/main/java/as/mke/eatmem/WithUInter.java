package as.mke.eatmem;


import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;


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
import as.mke.eatmem.decoder.GVSDecoder;
import as.mke.eatmem.processor.UInter;
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
    PrintStream ps;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//需要传入ui设计给的大小,初始化

        bao=new ByteArrayOutputStream();
        ps=new PrintStream(bao);
        System.setOut(ps);
        System.setErr(ps);


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
            InputStream in= getAssets().open("tiger2.svg");
            byte [] by=new byte[in.available()];
            in.read(by);

            String str=new String(by);
            setContentView(iv);


           // char[] arr=
            new Thread(new Runnable() {
                @Override
                public void run() {

                }
            }).start();











            GVSDecoder decoder=new GVSDecoder();
            int pixels[]=decoder.getPixels(str);
          //  int[] pix=getPixels(str);

            System.out.println("pix array length : "+pixels.length);
            Bitmap bmp=Bitmap.createBitmap(pixels ,800,800,Bitmap.Config.ARGB_8888);

           iv.setImageBitmap(bmp);

            VectorDrawable vd=new VectorDrawable();
          //  vd.inflate();
            // init();

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


    private static final void writePixel(int[] pixels, final byte red, final byte green, final byte blue,
                                         final byte alpha, final boolean hasAlpha, final int offset) {
        int pixel;
        if (hasAlpha) {
            pixel = (red & 0xff);
            pixel |= ((green & 0xff) << 8);
            pixel |= ((blue & 0xff) << 16);
            pixel |= ((alpha & 0xff) << 24);
            pixels[offset / 4] = pixel;
        } else {
            pixel = (red & 0xff);
            pixel |= ((green & 0xff) << 8);
            pixel |= ((blue & 0xff) << 16);
            pixels[offset / 4] = pixel;
        }
    }
    private static int[] readBuffer(ByteArrayInputStream in, int width, int height, int srcBytesPerPixel, boolean acceptAlpha,
                                    boolean flipVertically) throws IOException {

        int[] pixels = new int[width * height];
        byte[] buffer = new byte[srcBytesPerPixel];

        final boolean copyAlpha = (srcBytesPerPixel == 4) && acceptAlpha;
        final int dstBytesPerPixel = acceptAlpha ? srcBytesPerPixel : 3;
        final int trgLineSize = width * dstBytesPerPixel;

        int dstByteOffset = 0;

        for (int y = 0; y < height; y++) {
            for (int x = 0; x < width; x++) {
                int read = in.read(buffer, 0, srcBytesPerPixel);

                if (read < srcBytesPerPixel) {
                    return pixels;
                }
                int actualByteOffset = dstByteOffset;
                if (!flipVertically) {
                    actualByteOffset = ((height - y - 1) * trgLineSize) + (x * dstBytesPerPixel);
                }

                if (copyAlpha) {
                    writePixel(pixels, buffer[2], buffer[1], buffer[0], buffer[3], true, actualByteOffset);
                } else {
                    writePixel(pixels, buffer[2], buffer[1], buffer[0], (byte) 0, false, actualByteOffset);
                }

                dstByteOffset += dstBytesPerPixel;
            }
        }
        return pixels;
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
