package as.mke.eatmem.view;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ExpandableListView;

import androidx.annotation.NonNull;

import as.mke.eatmem.R;
import as.mke.eatmem.WithUInter;
import as.mke.eatmem.adapter.ExpandableListViewAdapter;

public class DialogDiy extends Dialog {
    Context context;
    View view;
    public DialogDiy(@NonNull Context context) {
        super(context);
        this.context=context;
    }
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        //去掉dialog的标题，需要在setContentView()之前
       // this.requestWindowFeature(Window.FEATURE_NO_TITLE);
        Window window = this.getWindow();
        //去掉dialog默认的padding
      //  window.getDecorView().setPadding(0, 0, 0, 0);

        DisplayMetrics dm = new DisplayMetrics();
       // ((WithUInter)context).getWindowManager().getDefaultDisplay().getMetrics(dm);
        WindowManager.LayoutParams lp = window.getAttributes();
        lp.width = WindowManager.LayoutParams.MATCH_PARENT;
        lp.height = WindowManager.LayoutParams.MATCH_PARENT;
        //设置dialog的位置在底部
        lp.gravity = Gravity.BOTTOM;
        //设置dialog的动画
        lp.windowAnimations = R.style.dialog_animation;
        window.setAttributes(lp);


    }

    @Override
    public void show() {
        super.show();

    }
}
