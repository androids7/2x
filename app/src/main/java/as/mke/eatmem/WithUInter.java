package as.mke.eatmem;


import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.annotation.SuppressLint;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.VectorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.RotateAnimation;
import android.view.animation.ScaleAnimation;
import android.view.animation.Transformation;
import android.view.animation.TranslateAnimation;
import android.widget.ExpandableListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;


import androidx.appcompat.app.AlertDialog;
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

import as.mke.eatmem.adapter.ExpandableListViewAdapter;
import as.mke.eatmem.adapter.FirstPagerAdapter;
import as.mke.eatmem.animation.MyAnimation;
import as.mke.eatmem.animation.ViewExpandAnimation;
import as.mke.eatmem.decoder.GVSDecoder;
import as.mke.eatmem.processor.UInter;
import as.mke.eatmem.view.BookImageView;
import as.mke.eatmem.view.DialogDiy;

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
    private View view1,view2,view3,userpage;
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

//渐变动画


    LinearLayout bookoutlayout,booklayout;
    ViewGroup.LayoutParams bookoutlayoutparams,booklayoutparams,pagerparams;
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
         //   getAssets().open(filename).read(data);
             //v= parx.parx(new String(data));


//


            setContentView(R.layout.guider_pager);

            circleLayout=findViewById(R.id.circlelayout);
            bookoutlayout=findViewById(R.id.bookoutlayout);
            booklayout=findViewById(R.id.booklayout);

            pager=findViewById(R.id.imagepager);
//
//           Animation rotate= AnimationUtils.loadAnimation(this,R.anim.addshow);
//
//           // my.setRepeatCount(Animation.INFINITE); //旋转的次数（无数次）
//            booklayout.startAnimation(rotate);
//            pager.startAnimation(rotate);



         Animation bookup= AnimationUtils.loadAnimation(this,R.anim.bookup);

         bookoutlayout.startAnimation(bookup);



            vp=findViewById(R.id.viewpagers);


            firstView=new ArrayList<View>();
            firstPagerAdapter=new FirstPagerAdapter(firstView);
            lf= getLayoutInflater().from(this);

            view1=lf.inflate(R.layout.page1,null);
            view2=lf.inflate(R.layout.page2,null);
            view3=lf.inflate(R.layout.page3,null);
            userpage=lf.inflate(R.layout.userpaper,null);

            TextView userxieyi=userpage.findViewById(R.id.userpaper);
            userxieyi.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    String[] groups = {"许可范围", "权限声明", "云端服务","权利声明","免责范围","费用相关","修订日期"};

                    //注意，字符数组不要写成{{"A1,A2,A3,A4"}, {"B1,B2,B3,B4，B5"}, {"C1,C2,C3,C4"}}
                    String[][] childs = {{"用户可以非商业性、无限制量地下载、复制、分发、安装及使用本软件"}, {"获取网络状态，主要是检测是否连接网络", "获取其他数据、同步云端保存设置等"},
                            {"服务器可能因为技术原因而无法服务，我们保证不泄漏您的个人隐私，传输过程中只加密部分重要数据，包括：", "云端设置信息",
                                    "一个账号一般对应一部设备，若该手机恢复出厂设置而您已经激活了高级账号的将会失效。此时您需要手动恢复，若无法恢复请联系开发者","若您同意则将会注册您的设备ID，只涉及安卓唯一的ID码，并非串号"}
                            ,{  "开发者拥有本软件的所有权和知识产权等全部权利","请您在使用本软件前，务必确定您使用的是正规渠道取得的","若您使用修改版本造成的数据损失，故障及其其他问题，我们不承担任何责任"
                            ,"若您发现修改版本，您也通过邮箱向我们举报","在未取得开发者授权的情况下，禁止","反向工程","反向编译","未经作者的允许修改本应用","盗用本软件的图片","二次具有商业的分发"},
                            //免责范围
                            {"在发布本软件或更新之前，开发者已对应用进行了测试","但由于移动设备系统的多样性，不能保证本软件会兼容所有用户的移动设备，也无法保证用户在使用本软件过程中，不会出现故障"
                                    ,"因此使用本软件及其相关服务所存在的风险和一切后果将完全由其自己承担"},
                            //费用相关
                            {"软件中包含付费项目，您可以通过购买高级版来解除功能限制","您需要自行负担使用本软件过程中产生的其他相关费用，如移动通讯提供商收取的费用"
                                    ,"本软件的付费价格由开发者决定，由于电子软件技术一经付费不可退还","如果您的购买不生效，可能是支付系统故障，也可以联系开发者进行处理"
                            },{"2020 年 03 月 02 日"}};

                //    final View view=lf.inflate(R.layout.xieyi,null);

                    LayoutInflater lf = LayoutInflater.from(context);
                    View view = lf.inflate(R.layout.xieyi, null);
                   // AlertDialog.Builder builder=new AlertDialog.Builder(context);
                   // AlertDialog diy=builder.create();
                    DialogDiy diy= new DialogDiy(context);
//                    WindowManager.LayoutParams wl=  diy.getWindow().getAttributes();
//                    // diy.setActivity((WithUInter)this);
//                    RelativeLayout.LayoutParams relayout=new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.MATCH_PARENT, RelativeLayout.LayoutParams.MATCH_PARENT);
//
//                    view.setLayoutParams(relayout);

                    diy.show();
                    diy.setContentView(view);
                    ExpandableListView expand_list_id;

                    expand_list_id = view.findViewById(R.id.expandableListView);
                    ExpandableListViewAdapter adapter = new ExpandableListViewAdapter(context, groups, childs);
                    expand_list_id.setAdapter(adapter);
                    //默认展开全部个数组
                    for (int i = 0; i < adapter.getGroupCount(); i++) {

                        expand_list_id.expandGroup(i);

                    }
                    // expand_list_id.expandGroup(0);
                    //关闭数组某个数组，可以通过该属性来实现全部展开和只展开一个列表功能
                    //expand_list_id.collapseGroup(0);

                    expand_list_id.setOnGroupClickListener(new ExpandableListView.OnGroupClickListener() {
                        @Override
                        public boolean onGroupClick(ExpandableListView expandableListView, View view, int groupPosition, long l) {
                            //  showToastShort(groups[groupPosition]);

                            view.startAnimation(new ViewExpandAnimation(view));
                            return false;
                        }
                    });
                    //子视图的点击事件
                    expand_list_id.setOnChildClickListener(new ExpandableListView.OnChildClickListener() {
                        @Override
                        public boolean onChildClick(ExpandableListView expandableListView, View view, int groupPosition, int childPosition, long l) {
                            //   showToastShort(childs[groupPosition][childPosition]);
                            return true;
                        }
                    });
                    //用于当组项折叠时的通知。
                    expand_list_id.setOnGroupCollapseListener(new ExpandableListView.OnGroupCollapseListener() {
                        @Override
                        public void onGroupCollapse(int groupPosition) {
                            // showToastShort("折叠了数据___"+groups[groupPosition]);
                        }
                    });
                    //
                    //用于当组项折叠时的通知。
                    expand_list_id.setOnGroupExpandListener(new ExpandableListView.OnGroupExpandListener() {
                        @Override
                        public void onGroupExpand(int groupPosition) {
                            //     showToastShort("展开了数据___"+groups[groupPosition]);
                        }
                    });



                }
            });

            firstView.add(view1);
            firstView.add(view2);
            firstView.add(view3);

            bookoutlayoutparams=bookoutlayout.getLayoutParams();
            booklayoutparams=booklayout.getLayoutParams();
            pagerparams=pager.getLayoutParams();
            NUMPAGES=firstView.size();
            addGuidePointTolist();
            addArrow();
            vp.setAdapter(firstPagerAdapter);



            vp.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {


                @Override
                public void onPageScrolled(int position, float positionOffsett, int positionOffsetPixels) {





                    pager.turnpage(-180 * (positionOffset));
                    positionOffset=positionOffsett;
                }


                @Override
                public void onPageSelected(int position) {


                    pager.stopturn();
                    setGuidePoint(position);


                    if(position==2){

//
//                        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(dp2px(context,80),dp2px(context,80));
//                        params.gravity=Gravity.CENTER;
//                        pager.setLayoutParams(params);
//
//                        pager.setVisibility(View.VISIBLE);
                        int height =bookoutlayout.getHeight();
                        ValueAnimator scaleY = ValueAnimator.ofInt(0, dp2px(context,80));
                        ////第二个高度 需要注意一下, 因为view默认是GONE  无法直接获取高度
                        scaleY.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
                            @Override
                            public void onAnimationUpdate(ValueAnimator animation)
                            {int animatorValue = Integer.valueOf(animation.getAnimatedValue() + "");
                            LinearLayout.LayoutParams params = (LinearLayout.LayoutParams) pager.getLayoutParams();
                            params.height = animatorValue;
                            params.gravity=Gravity.CENTER;
                            params.width=animatorValue;
                            booklayout.setLayoutParams(params);
                            pager.invalidate();
                            }});
                        scaleY.setTarget(pager);
                        scaleY.setDuration(300);
                        scaleY.start();
                        bookoutlayout.addView(userpage);
                    }

                    else{
                        LinearLayout.LayoutParams bookparams=new LinearLayout.LayoutParams (booklayoutparams);

                        LinearLayout.LayoutParams bookoutparams=new LinearLayout.LayoutParams (bookoutlayoutparams);
                        bookoutparams.weight=1;
                        booklayout.setLayoutParams(bookparams);
                        pager.setLayoutParams(bookparams);

                       // pager.clearAnimation();
                        pager.invalidate();
                        bookoutlayout.setLayoutParams(bookoutparams);
                        bookoutlayout.removeView(userpage);
                    }
/*
                    if(position==2){
                        LinearLayout.LayoutParams params=new LinearLayout.LayoutParams(dp2px(context,80),dp2px(context,80));

                        params.gravity=Gravity.CENTER;
                        booklayout.setLayoutParams(params);
                        pager.setLayoutParams(params);
                        bookoutlayout.setLayoutParams(params);
                    }
                    else{
                        LinearLayout.LayoutParams bookparams=new LinearLayout.LayoutParams (booklayoutparams);
                        bookparams.weight=1;
                        LinearLayout.LayoutParams bookoutparams=new LinearLayout.LayoutParams (bookoutlayoutparams);
                        bookoutparams.weight=1;
                        booklayout.setLayoutParams(bookparams);
                        pager.setLayoutParams(pagerparams);
                        bookoutlayout.setLayoutParams(bookoutparams);
                    }

 */
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



    private void addArrow()
    {

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

         ImageView arrow=new ImageView(context);
         ViewGroup.LayoutParams params=  new ViewGroup.LayoutParams(ViewGroup. LayoutParams.WRAP_CONTENT,ViewGroup. LayoutParams . WRAP_CONTENT);

        arrow.setPadding(padding, 0, padding, 0);
         arrow.setLayoutParams(params);
         arrow.setImageResource(R.drawable.ic_keyboard_arrow_right_black_24dp);
         circleLayout.addView(arrow);

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
