package as.mke.eatmem;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.animation.AlphaAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

import as.mke.eatmem.adapter.FirstPagerAdapter;
import as.mke.eatmem.view.GradientColorTextView;

public class FirstActivity extends AppCompatActivity {

    ViewPager viewPager;
    FirstPagerAdapter firstPagerAdapter;
    List<View> firstView;
    LayoutInflater lf;
    private View view1,view2,view3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  requestWindowFeature(Window.FEATURE_NO_TITLE);

        setContentView(R.layout.firstuse);

       // getActionBar().hide();
        init();
    }
    public void init(){
        viewPager=findViewById(R.id.viewpager);
        firstView=new ArrayList<View>();
        firstPagerAdapter=new FirstPagerAdapter(firstView);

        FloatingActionButton floating1;
        GradientColorTextView hi,hello;

       lf= getLayoutInflater().from(this);

       view1=lf.inflate(R.layout.page1,null);

       floating1=view1.findViewById(R.id.floatingactionbar);
        TranslateAnimation tran=new TranslateAnimation(0,0,500,0);
        tran.setFillAfter(false);//动画结束之后回到原点，默认
        tran.setDuration(1000);
       floating1.setAnimation(tran);

       hello=view1.findViewById(R.id.hello);
        hi=view1.findViewById(R.id.hi);
        AlphaAnimation alpha=new AlphaAnimation(0.0f,1.f);
        alpha.setDuration(1800);
       hello.setAnimation(alpha);
       hi.setAnimation(alpha);



       firstView.add(view1);

       //绑定
        viewPager.setAdapter(firstPagerAdapter);

    }
}
