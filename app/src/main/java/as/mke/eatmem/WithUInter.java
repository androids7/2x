package as.mke.eatmem;


import android.content.res.Resources;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;


import androidx.appcompat.app.AppCompatActivity;

import org.xmlpull.v1.XmlPullParserException;

import java.io.IOException;
import java.io.InputStream;

import as.mke.eatmem.adapter.ScreenAdaptation;
import as.mke.eatmem.processor.UInter;
import as.mke.eatmem.view.GradientColorTextView;

public class WithUInter extends AppCompatActivity {

    private String TAG="WithUInter";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//需要传入ui设计给的大小,初始化


        UInter parx = new UInter(this);
        try {
            long time = System.currentTimeMillis();

            String filename="test.xml";
         int size=   getAssets().open(filename).available();
         byte[]  data=new byte[size];
            getAssets().open(filename).read(data);
View v= parx.parx(new String(data));
            setContentView(v);
                    //R.layout.page1);
            float sizes=   Resources.getSystem().getDisplayMetrics().scaledDensity;
            Log.d(TAG,sizes+"sp");



            long timeAfter = System.currentTimeMillis()-time;
      //      ((TextView)findViewById(parx.getIds().get("time"))).setText(timeAfter+"ms");
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
