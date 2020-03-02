package as.mke.eatmem.processor;
import android.content.Context;
import android.content.res.ColorStateList;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.os.Build;

import android.util.Log;
import android.view.Gravity;

import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import androidx.annotation.RequiresApi;
import androidx.viewpager.widget.ViewPager;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserException;
import org.xmlpull.v1.XmlPullParserFactory;

import java.io.IOException;
import java.io.StringReader;
import java.lang.reflect.Constructor;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Random;

import static android.util.TypedValue.COMPLEX_UNIT_DIP;
import static android.util.TypedValue.COMPLEX_UNIT_PX;
import static android.util.TypedValue.COMPLEX_UNIT_SP;

/**
 * Created by JediB on 3/26/2017.
 */

public class UInter {

    private String LOG_TAG = "UInter";
    private  float mWidth = 720;
    private  float mHeight = 1280;

    private Context ctx;
    private LinkedList<View> views = new LinkedList<View>();
    private Map<String, Integer> ids = new HashMap<String, Integer>();

    private ImageLoader customImageLoader = null;

    private boolean logging = true;

    public UInter(Context ctx){
        this.ctx = ctx;
    }

    public Map<String, Integer> getIds(){

        return ids;
    }

    public View parx(String xml) throws XmlPullParserException, IOException {

        XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
        factory.setNamespaceAware(true);
        XmlPullParser xpp = factory.newPullParser();

        xpp.setInput( new StringReader( xml ) );
        int eventType = xpp.getEventType();
        while (eventType != XmlPullParser.END_DOCUMENT) {
            xpp.getPrefix();
            if(eventType == XmlPullParser.START_DOCUMENT) {
                if(logging) Log.d(LOG_TAG, "Parsing document");
            } else if(eventType == XmlPullParser.START_TAG) {
                if(logging) Log.d(LOG_TAG, "Tag opened: " + xpp.getName());
                View v = parseTag(xpp);
                if(v!=null) {
                    if (!views.isEmpty())
                        ((ViewGroup) views.getLast()).addView(v.getRootView());
                    views.add(v);
                }
            } else if(eventType == XmlPullParser.END_TAG) {
                if(views.size()>1)
                    views.removeLast();
                if(logging) Log.d(LOG_TAG,"Tag closed "+xpp.getName());
            }
            eventType = xpp.next();
        }
        System.out.println("End document");

        return views.getFirst();

    }

    @SuppressWarnings("unused")
    public void setCustomImageLoader(ImageLoader imageLoader){
        this.customImageLoader = imageLoader;
    }

    private View parseTag(XmlPullParser xpp){

        String tag = xpp.getName();

        switch(tag){

            case "ScrollView": {
                ScrollView v = new ScrollView(ctx);
                v = (ScrollView) parseAttributes(v, xpp);
                return v;
            }
            case "LinearLayout": {
                LinearLayout v = new LinearLayout(ctx);
                v = (LinearLayout) parseAttributes(v, xpp);
                return v;
            }
            case "RelativeLayout": {
                RelativeLayout v = new RelativeLayout(ctx);
                v = (RelativeLayout) parseAttributes(v, xpp);
                return v;
            }
            case "ImageView": {
                ImageView v = new ImageView(ctx);
                v = (ImageView) parseAttributes(v, xpp);
                return v;
            }
            case "TextView": {
                TextView v = new TextView(ctx);
                v = (TextView) parseAttributes(v, xpp);
                return v;
            }
            case "Button": {
                Button v = new Button(ctx);
                v = (Button) parseAttributes(v, xpp);
                return v;
            }
            case "androidx.viewpager.widget.ViewPager":{
                ViewPager v=new ViewPager(ctx);
                v=(ViewPager)parseAttributes(v,xpp);
                return  v;
            }

            /*

            add by androids7 2020.2.25
             */

            case "com.google.android.material.floatingactionbutton.FloatingActionButton":{
                FloatingActionButton v=new FloatingActionButton(ctx);
                v=(FloatingActionButton)parseAttributes(v,xpp);
                return  v;
            }


        }

        return new View(ctx);

    }

    // Turn back now!
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private View parseAttributes(View v, XmlPullParser xpp){

        int WIDTH = ViewGroup.LayoutParams.WRAP_CONTENT;
        int HEIGHT = ViewGroup.LayoutParams.WRAP_CONTENT;

        int Layout_MarginLef=0;
        int Layout_MarginRight=0;
        int Layout_MarginTOP=0;
        int Layout_MarginBottom=0;




        String a = ""+xpp.getAttributeValue(null, "id");

        if(a.length()>0&&!a.equals("null")){
            if(a.startsWith("@+id/")){
                v.setId(new Random().nextInt(999999)+ids.size());
                ids.put(a.replace("@+id/", ""), v.getId());
            }
        }

        a = ""+xpp.getAttributeValue(null, "background");
        if(logging) Log.d(LOG_TAG, "background:"+a);

        if(a.length()>0&&!a.equals("null")){
            if(a.contains("@drawable/")){
                int drawable = ctx.getResources().getIdentifier(a.replace("@", ""),
                        "drawable", ctx.getPackageName());

                if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.LOLLIPOP)
                    v.setBackground(ctx.getResources().getDrawable(drawable));
                else
                    v.setBackgroundDrawable(ctx.getResources().getDrawable(drawable));
            }else if(a.contains("@color/")){
                int drawable = ctx.getResources().getIdentifier(a.replace("@", ""),
                        "color", ctx.getPackageName());

                v.setBackgroundColor(ctx.getResources().getColor(drawable));
            }else if(a.contains("#")){
                v.setBackgroundColor(Color.parseColor(a));
            }
        }

        a = ""+xpp.getAttributeValue(null, "src");
        if(logging) Log.d(LOG_TAG, "src:"+a);

        if(a.length()>0&&!a.equals("null")){
            if(a.contains("@drawable/")){
                int drawable = ctx.getResources().getIdentifier(a.replace("@", ""),
                        "drawable", ctx.getPackageName());

                ((ImageView)v).setImageDrawable(ctx.getResources().getDrawable(drawable));
            }else if(a.contains("//")){
                if(customImageLoader==null)
                    ((ImageView)v).setImageURI(Uri.parse(a));
                else
                    customImageLoader.onUri(a, v);
            }
        }

        a = ""+xpp.getAttributeValue(null, "layout_width");
        if(logging) Log.d(LOG_TAG, "layout_width:"+a);

        if(a.length()>0&&!a.equals("null")){
            if(a.equals("match_parent")||a.equals("fill_parent")){
                WIDTH = ViewGroup.LayoutParams.MATCH_PARENT;
            }else if(a.equals("wrap_content")){
                WIDTH = ViewGroup.LayoutParams.WRAP_CONTENT;
            }else if(a.endsWith("px")){
                ViewGroup.LayoutParams newLayoutParams = v.getLayoutParams();
                WIDTH = Integer.parseInt(a.replace("px", ""));
            }else if(a.endsWith("dp")){
                WIDTH = (int) (Integer.parseInt(a.replace("dp", "")) *
                        Resources.getSystem().getDisplayMetrics().density);
            }
            if(logging) Log.d(LOG_TAG, "WIDTH SET TO: " + WIDTH);
        }

        a = ""+xpp.getAttributeValue(null, "layout_height");
        if(logging) Log.d(LOG_TAG, "layout_height:"+a);

        if(a.length()>0&&!a.equals("null")){
            if(a.equals("match_parent")||a.equals("fill_parent")){
                HEIGHT = ViewGroup.LayoutParams.MATCH_PARENT;
            }else if(a.equals("wrap_content")){
                HEIGHT = ViewGroup.LayoutParams.WRAP_CONTENT;
            }else if(a.endsWith("px")){
                HEIGHT = Integer.parseInt(a.replace("px", ""));
            }else if(a.endsWith("dp")){
                HEIGHT = (int) (Integer.parseInt(a.replace("dp", "")) *
                        Resources.getSystem().getDisplayMetrics().density);
            }
        }

        v.setLayoutParams(new ViewGroup.LayoutParams(WIDTH, HEIGHT));

//add by androids7 2020.2.25

        LinearLayout.LayoutParams lp=new LinearLayout.LayoutParams(WIDTH, HEIGHT);


        a = ""+xpp.getAttributeValue(null, "layout_marginLeft");
        if(logging) Log.d(LOG_TAG, "layout_marginLeft:"+a);

        if(a.length()>0&&!a.equals("null")){

            if(a.contains("sp")){
                Layout_MarginLef= (int) ( Integer.parseInt(a.replace("sp", ""))*
                        Resources.getSystem().getDisplayMetrics().scaledDensity);
            }else if(a.contains("dp")){
                Layout_MarginLef=(int)(Integer.parseInt(a.replace("dp", ""))*
                        ( Resources.getSystem().getDisplayMetrics().density));
            }else if(a.contains("px")){
                Layout_MarginLef=(Integer.parseInt(a.replace("px", "")));
            }
            // lp.setMargins(Layout_MarginLef,Layout_MarginTOP,Layout_MarginRight,Layout_MarginBottom);
        }





        a = ""+xpp.getAttributeValue(null, "layout_marginLeft");
        if(logging) Log.d(LOG_TAG, "layout_marginLeft:"+a);

        if(a.length()>0&&!a.equals("null")){

            if(a.contains("sp")){
                Layout_MarginLef= (int) ( Integer.parseInt(a.replace("sp", ""))*
                        Resources.getSystem().getDisplayMetrics().scaledDensity);
            }else if(a.contains("dp")){
                Layout_MarginLef=(Integer.parseInt(a.replace("dp", ""))*
                        (int) Resources.getSystem().getDisplayMetrics().density);
            }else if(a.contains("px")){
                Layout_MarginLef=(Integer.parseInt(a.replace("px", "")));
            }
            //  lp.setMargins(Layout_MarginLef,Layout_MarginTOP,Layout_MarginRight,Layout_MarginBottom);
        }

        a = ""+xpp.getAttributeValue(null, "layout_marginTop");
        if(logging) Log.d(LOG_TAG, "layout_marginTop:"+a);

        if(a.length()>0&&!a.equals("null")){

            if(a.contains("sp")){
                Layout_MarginTOP= (int) ( Integer.parseInt(a.replace("sp", ""))*
                        Resources.getSystem().getDisplayMetrics().scaledDensity);
            }else if(a.contains("dp")){
                Layout_MarginTOP=(Integer.parseInt(a.replace("dp", ""))*
                        (int) Resources.getSystem().getDisplayMetrics().density);
            }else if(a.contains("px")){
                Layout_MarginTOP=(Integer.parseInt(a.replace("px", "")));
            }
            //  lp.setMargins(Layout_MarginLef,Layout_MarginTOP,Layout_MarginRight,Layout_MarginBottom);
        }


        a = ""+xpp.getAttributeValue(null, "layout_marginRight");
        if(logging) Log.d(LOG_TAG, "layout_marginRight:"+a);

        if(a.length()>0&&!a.equals("null")){

            if(a.contains("sp")){
                Layout_MarginRight= (int) ( Integer.parseInt(a.replace("sp", ""))*
                        Resources.getSystem().getDisplayMetrics().scaledDensity);
            }else if(a.contains("dp")){
                Layout_MarginRight=(Integer.parseInt(a.replace("dp", ""))*
                        (int) Resources.getSystem().getDisplayMetrics().density);
            }else if(a.contains("px")){
                Layout_MarginRight=(Integer.parseInt(a.replace("px", "")));
            }

        }


        a = ""+xpp.getAttributeValue(null, "layout_marginBottom");
        if(logging) Log.d(LOG_TAG, "layout_marginBottom:"+a);

        if(a.length()>0&&!a.equals("null")){

            if(a.contains("sp")){
                Layout_MarginBottom= (int) ( Integer.parseInt(a.replace("sp", ""))*
                        Resources.getSystem().getDisplayMetrics().scaledDensity);
            }else if(a.contains("dp")){
                Layout_MarginBottom=(Integer.parseInt(a.replace("dp", ""))*
                        (int) Resources.getSystem().getDisplayMetrics().density);
            }else if(a.contains("px")){
                Layout_MarginBottom=(Integer.parseInt(a.replace("px", "")));
            }
            // lp.setMargins(Layout_MarginLef,Layout_MarginTOP,Layout_MarginRight,Layout_MarginBottom);
        }



        a = ""+xpp.getAttributeValue(null, "gravity");
        if(logging) Log.d(LOG_TAG, "gravity:"+a);

        if(a.length()>0&&!a.equals("null")){

            if(v instanceof RelativeLayout) {
                int mGravity = 0;
                if (a.contains("center")) {
                    ((RelativeLayout) v).setGravity(Gravity.CENTER);
                } else if (a.contains("top")) {
                    ((RelativeLayout) v).setGravity(Gravity.TOP);
                } else if (a.contains("right")) {
                    ((RelativeLayout) v).setGravity(Gravity.RIGHT);
                } else if (a.contains("bottom")) {
                    ((RelativeLayout) v).setGravity(Gravity.BOTTOM);
                } else if (a.contains("|")) {
                    String[] data = a.split("|");


                    for (String da : data) {

                        if (da.contains("top")) {
                            mGravity = mGravity | Gravity.TOP;
                        }

                        if (da.contains("right")) {
                            mGravity = mGravity | Gravity.RIGHT;
                        }


                        if (da.contains("left")) {
                            mGravity = mGravity | Gravity.LEFT;
                        }


                        if (da.contains("bottom")) {
                            mGravity = mGravity | Gravity.BOTTOM;
                        }
                    }

                }
                //if (logging) Log.d(LOG_TAG, "gravity:" + mGravity);
                ((RelativeLayout) v).setGravity(mGravity);
            }
            if(v instanceof LinearLayout) {
                int mGravity = 0;
                if (a.contains("center")) {
                    ((LinearLayout) v).setGravity(Gravity.CENTER);
                } else if (a.contains("top")) {
                    ((LinearLayout) v).setGravity(Gravity.TOP);
                } else if (a.contains("right")) {
                    ((LinearLayout) v).setGravity(Gravity.RIGHT);
                } else if (a.contains("bottom")) {
                    ((LinearLayout) v).setGravity(Gravity.BOTTOM);
                } else if (a.contains("|")) {
                    String[] data = a.split("|");


                    for (String da : data) {

                        if (da.contains("top")) {
                            mGravity = mGravity | Gravity.TOP;
                        }

                        if (da.contains("right")) {
                            mGravity = mGravity | Gravity.RIGHT;
                        }


                        if (da.contains("left")) {
                            mGravity = mGravity | Gravity.LEFT;
                        }


                        if (da.contains("bottom")) {
                            mGravity = mGravity | Gravity.BOTTOM;
                        }
                    }
                    //   if(logging) Log.d(LOG_TAG, "gravity:"+mGravity);

                }

                ((LinearLayout) v).setGravity(mGravity);
            }
        }


        a = ""+xpp.getAttributeValue(null, "layout_gravity");
        if(logging) Log.d(LOG_TAG, "layout_gravity:"+a);

        if(a.length()>0&&!a.equals("null")){
            //  LinearLayout.LayoutParams llp = new LinearLayout.LayoutParams(WIDTH, HEIGHT);

            if(v instanceof FloatingActionButton) {

                if(logging) Log.d(LOG_TAG, "FloatingActionButtonlayout_gravity:"+a);

                //此处相当于布局文件中的Android:layout_gravity属性
                // lp.gravity = Gravity.RIGHT;

                if (a.contains("center")) {
                    lp.gravity = Gravity.CENTER;
                } else if (a.contains("top")) {
                    lp.gravity = Gravity.TOP;
                } else if (a.contains("right")) {
                    lp.gravity = Gravity.RIGHT;
                } else if (a.contains("bottom")) {
                    lp.gravity = Gravity.BOTTOM;
                } else if (a.contains("|")) {
                    String[] data = a.split("|");

                    int mGravity = 0;
                    for (String da : data) {

                        if (da.contains("top")) {
                            lp.gravity = lp.gravity | Gravity.TOP;
                        }

                        if (da.contains("right")) {
                            lp.gravity = lp.gravity | Gravity.RIGHT;
                        }


                        if (da.contains("left")) {
                            lp.gravity = lp.gravity | Gravity.LEFT;
                        }


                        if (da.contains("bottom")) {
                            lp.gravity = lp.gravity | Gravity.BOTTOM;
                        }
                    }
                    //if (logging) Log.d(LOG_TAG, "gravity:" + mGravity);
                    //


                    // ((LinearLayout)v).setGravity(llp.gravity);
                }
            }
            //  v.setLayoutParams(lp);

        }


        a = ""+xpp.getAttributeValue(null, "layout_weight");
        if(logging) Log.d(LOG_TAG, "layout_weight:"+a);

        if(a.length()>0&&!a.equals("null")){
            if( v instanceof LinearLayout) {
                //   LinearLayout.LayoutParams lp2 = new LinearLayout.LayoutParams()
                lp.weight=Float.parseFloat(a);
                // v.setLayoutParams(lp);
            }

        }
        lp.setMargins(Layout_MarginLef,Layout_MarginTOP,Layout_MarginRight,Layout_MarginBottom);
        v.setLayoutParams(lp);

        a = ""+xpp.getAttributeValue(null, "padding");
        if(logging) Log.d(LOG_TAG, "padding:"+a);

        if(a.length()>0&&!a.equals("null")){
            if(a.endsWith("px")){
                v.setPadding(Integer.parseInt(a.replace("px", "")),
                        Integer.parseInt(a.replace("px", "")),
                        Integer.parseInt(a.replace("px", "")),
                        Integer.parseInt(a.replace("px", ""))
                );
            }else if(a.endsWith("dp")){
                v.setPadding((int) (Integer.parseInt(a.replace("dp", "")) * Resources.getSystem().getDisplayMetrics().density),
                        (int) (Integer.parseInt(a.replace("dp", ""))
                                * Resources.getSystem().getDisplayMetrics().density),
                        (int) (Integer.parseInt(a.replace("dp", ""))
                                * Resources.getSystem().getDisplayMetrics().density),
                        (int) (Integer.parseInt(a.replace("dp", ""))
                                * Resources.getSystem().getDisplayMetrics().density)
                );
            }
        }

        a = ""+xpp.getAttributeValue(null, "text");
        if(logging) Log.d(LOG_TAG, "text:"+a);

        if(a.length()>0&&!a.equals("null")){
            if(a.contains("@string/")){
                int string = ctx.getResources().getIdentifier(a.replace("@", ""),
                        "string", ctx.getPackageName());

                ((TextView)v).setText(ctx.getResources().getString(string));
            }else{
                ((TextView)v).setText(a);
            }
        }

        a = ""+xpp.getAttributeValue(null, "textSize");
        if(logging) Log.d(LOG_TAG, "textSize:"+a);

        if(a.length()>0&&!a.equals("null")){
            if(a.contains("sp")){
                float size=Float.parseFloat(a.replace("sp", ""));
                //          *
                //          Resources.getSystem().getDisplayMetrics().scaledDensity;
                // Log.d(LOG_TAG,size+"sp");
                //  Log.d(LOG_TAG,"scaleDensity :"+Resources.getSystem().getDisplayMetrics().scaledDensity+"sp");
                ((TextView)v).setTextSize(COMPLEX_UNIT_SP,size);
            }else if(a.contains("dp")){
                ((TextView)v).setTextSize(COMPLEX_UNIT_DIP, Integer.parseInt(a.replace("dp", "")));
                //  ((TextView)v).setTextSize(COMPLEX_UNIT_SP,size);
            }else if(a.contains("px")){
                ((TextView)v).setTextSize(COMPLEX_UNIT_PX,Integer.parseInt(a.replace("px", "")));
            }
        }

        a = ""+xpp.getAttributeValue(null, "textColor");
        if(logging) Log.d(LOG_TAG, "textColor:"+a);

        if(a.length()>0&&!a.equals("null")){
            if(a.contains("@color/")){
                int drawable = ctx.getResources().getIdentifier(a.replace("@", ""),
                        "color", ctx.getPackageName());

                ((TextView)v).setTextColor(ctx.getResources().getColor(drawable));
            }else if(a.contains("#")){
                ((TextView)v).setTextColor(Color.parseColor(a));
            }
        }

        /*add by androids7

         */

        a = ""+xpp.getAttributeValue(null, "fabCustomSize");
        if(logging) Log.d(LOG_TAG, "fabCustomSize:"+a);

        if(a.length()>0&&!a.equals("null")){
            if(a.contains("sp")){
                ((FloatingActionButton)v).setCustomSize(Integer.parseInt(a.replace("sp", ""))*
                        (int) Resources.getSystem().getDisplayMetrics().scaledDensity);
            }else if(a.contains("dp")){
                ((FloatingActionButton)v).setCustomSize(Integer.parseInt(a.replace("dp", ""))*
                        (int)  Resources.getSystem().getDisplayMetrics().density);
            }else if(a.contains("px")){
                ((FloatingActionButton)v).setCustomSize(Integer.parseInt(a.replace("px", "")));
            }
        }



        a = ""+xpp.getAttributeValue(null, "elevation");
        if(logging) Log.d(LOG_TAG, " elevation:"+a);

        if(a.length()>0&&!a.equals("null")){
            if(a.contains("sp")){
                ((FloatingActionButton)v).setCompatElevation(Integer.parseInt(a.replace("sp", ""))*
                        Resources.getSystem().getDisplayMetrics().scaledDensity);
            }else if(a.contains("dp")){
                ((FloatingActionButton)v).setCompatElevation(Integer.parseInt(a.replace("dp", ""))*
                        Resources.getSystem().getDisplayMetrics().density);
            }else if(a.contains("px")){
                ((FloatingActionButton)v).setCompatElevation(Integer.parseInt(a.replace("px", "")));
            }
        }



        a = ""+xpp.getAttributeValue(null, "pressedTranslationZ");
        if(logging) Log.d(LOG_TAG, " pressedTranslationZ:"+a);

        if(a.length()>0&&!a.equals("null")){
            if(a.contains("sp")){
                ((FloatingActionButton)v).setCompatPressedTranslationZ(Integer.parseInt(a.replace("sp", ""))*
                        Resources.getSystem().getDisplayMetrics().scaledDensity);
            }else if(a.contains("dp")){
                ((FloatingActionButton)v).setCompatPressedTranslationZ(Integer.parseInt(a.replace("dp", ""))*
                        Resources.getSystem().getDisplayMetrics().density);
            }else if(a.contains("px")){
                ((FloatingActionButton)v).setCompatPressedTranslationZ(Integer.parseInt(a.replace("px", "")));
            }
        }




        a = ""+xpp.getAttributeValue(null, "backgroundTint");
        if(logging) Log.d(LOG_TAG, " backgroundTint:"+a);

        if(a.length()>0&&!a.equals("null")){
            if(a.contains("@color/")){
                int drawable = ctx.getResources().getIdentifier(a.replace("@", ""),
                        "color", ctx.getPackageName());

                ((FloatingActionButton)v).setBackgroundTintList(ColorStateList.valueOf(ctx.getResources().getColor(drawable)));
            }else if(a.contains("#")){
                ((FloatingActionButton)v).setBackgroundTintList(ColorStateList.valueOf(Color.parseColor(a)));
            }
        }


        a = ""+xpp.getAttributeValue(null, "scaleType");
        if(logging) Log.d(LOG_TAG, " scaleType:"+a);

        if(a.length()>0&&!a.equals("null")){
            if(a.contains("center")){
                ((FloatingActionButton)v).setScaleType(ImageView.ScaleType.CENTER);
            }
            else if(a.contains("centerInside")) {
                ((FloatingActionButton)v).setScaleType(ImageView.ScaleType.CENTER_INSIDE);
            }

            //      ((FloatingActionButton)v).setCompatPressedTranslationZ(Integer.parseInt(a.replace("dp", ""))*
            //              Resources.getSystem().getDisplayMetrics().density);
            //   }else if(a.contains("px")){
            //       ((FloatingActionButton)v).setCompatPressedTranslationZ(Integer.parseInt(a.replace("px", "")));
            //  }
        }


        a = ""+xpp.getAttributeValue(null, "alignParentStart");
        if(logging) Log.d(LOG_TAG, "alignParentStart:"+a);
        if(a.equals("true")) addOrRemoveProperty(v, RelativeLayout.ALIGN_PARENT_START, true);

        a = ""+xpp.getAttributeValue(null, "layout_alignParentEnd");
        if(logging) Log.d(LOG_TAG, "alignParentEnd:"+a);
        if(a.equals("true")) addOrRemoveProperty(v, RelativeLayout.ALIGN_PARENT_END, true);

        a = ""+xpp.getAttributeValue(null, "layout_alignParentBottom");
        if(logging) Log.d(LOG_TAG, "alignParentBottom:"+a);
        if(a.equals("true")) addOrRemoveProperty(v, RelativeLayout.ALIGN_PARENT_BOTTOM, true);

        a = ""+xpp.getAttributeValue(null, "layout_alignParentLeft");
        if(logging) Log.d(LOG_TAG, "alignParentLeft:"+a);
        if(a.equals("true")) addOrRemoveProperty(v, RelativeLayout.ALIGN_PARENT_LEFT, true);

        a = ""+xpp.getAttributeValue(null, "layout_alignParentRight");
        if(logging) Log.d(LOG_TAG, "alignParentRight:"+a);
        if(a.equals("true")) addOrRemoveProperty(v, RelativeLayout.ALIGN_PARENT_RIGHT, true);

        a = ""+xpp.getAttributeValue(null, "layout_alignParentTop");
        if(logging) Log.d(LOG_TAG, "alignParentTop:"+a);
        if(a.equals("true")) addOrRemoveProperty(v, RelativeLayout.ALIGN_PARENT_TOP, true);

        a = ""+xpp.getAttributeValue(null, "layout_centerInParent");
        if(logging) Log.d(LOG_TAG, "centerInParent:"+a);
        if(a.equals("true")) addOrRemoveProperty(v, RelativeLayout.CENTER_IN_PARENT, true);

        a = ""+xpp.getAttributeValue(null, "layout_centerHorizontal");
        if(logging) Log.d(LOG_TAG, "centerHorizontal:"+a);
        if(a.equals("true")) addOrRemoveProperty(v, RelativeLayout.CENTER_HORIZONTAL, true);

        a = ""+xpp.getAttributeValue(null, "layout_centerVertical");
        if(logging) Log.d(LOG_TAG, "centerVertical:"+a);
        if(a.equals("true")) addOrRemoveProperty(v, RelativeLayout.CENTER_VERTICAL, true);

        a = ""+xpp.getAttributeValue(null, "orientation");
        if(logging) Log.d(LOG_TAG, "orientation:"+a);
        if(a.equals("vertical"))
            ((LinearLayout)v).setOrientation(LinearLayout.VERTICAL);
        else if(a.equals("horizontal"))
            ((LinearLayout)v).setOrientation(LinearLayout.HORIZONTAL);

        return v;
    }

    // Thanks Hiren Patel
    // Here's a hackish way to make it work.
    private void addOrRemoveProperty(View view, int property, boolean flag){
        RelativeLayout relativeLayout = null;
        RelativeLayout.LayoutParams layoutParams;
        if(view instanceof RelativeLayout){
            layoutParams = (RelativeLayout.LayoutParams) view.getLayoutParams();
        }else if(view.getRootView() instanceof  RelativeLayout) {
            layoutParams = (RelativeLayout.LayoutParams) view.getRootView().getLayoutParams();
        } else {
            relativeLayout = new RelativeLayout(ctx);
            layoutParams = new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT,
                    RelativeLayout.LayoutParams.WRAP_CONTENT);
        }
        if(flag){
            layoutParams.addRule(property);
        }else {
            if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.JELLY_BEAN_MR1)
                layoutParams.removeRule(property);
            else
                layoutParams.addRule(property, 0);
        }
        if(relativeLayout!=null) {
            relativeLayout.setLayoutParams(layoutParams);
            relativeLayout.addView(view);
        }else{
            view.getRootView().setLayoutParams(layoutParams);
        }
    }




    public abstract class ImageLoader {
        public abstract void onUri(String uri, View view);
    }

}

