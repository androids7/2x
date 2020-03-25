package as.mke.eatmem;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.drawerlayout.widget.DrawerLayout;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.os.Build;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import com.google.android.material.navigation.NavigationView;

import java.util.List;

import as.mke.eatmem.obj.MUser;

import as.mke.eatmem.utils.Apputil;
import cn.bmob.v3.Bmob;
import cn.bmob.v3.BmobQuery;
import cn.bmob.v3.BmobUser;
import cn.bmob.v3.exception.BmobException;
import cn.bmob.v3.listener.FindListener;
import cn.bmob.v3.listener.SaveListener;

public class MainApp extends AppCompatActivity {
    /**
     * 查询用户表
     */
    MUser user;
    String UniqueID="";
    private void queryUser() {
        BmobQuery<MUser> bmobQuery = new BmobQuery<>();
        bmobQuery.findObjects(new FindListener<MUser>() {
            @Override
            public void done(List<MUser> object, BmobException e) {
                if (e == null) {
                    for(int i=0;i<object.size();i++) {
                        if (object.get(i).getUniqueID().equals(UniqueID))
                        {
                            MUser ms= new MUser();
                            ms.setUsername(object.get(i).getUsername());
                            ms.setPassword(ms.getUsername());
                          ms.login(new SaveListener<MUser>() {
                                @Override
                                public void done(MUser bmobUser, BmobException e) {
                                    if (e == null) {
                                     //   user = BmobUser.getCurrentUser(MUser.class);
                                        toast("登陆成功："+ user.getUsername());
                                    } else {
                                        toast( "登录失败：" + e.getMessage());
                                    }
                                }
                            });
                        }

                    }
                  toast("查询成功:");
                } else {
                   toast("查询失败:"+e.toString());
                }
            }
        });
    }

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //提供以下两种方式进行初始化操作：

        //第一：默认初始化
        Bmob.initialize(getBaseContext(), "388fbecbb993ea7b21a7603c2f2a1f29");
        // 注:自v3.5.2开始，数据sdk内部缝合了统计sdk，开发者无需额外集成，传渠道参数即可，不传默认没开启数据统计功能
        //Bmob.initialize(this, "Your Application ID","bmob");
        UniqueID= new Apputil(this).getUniqueID();
        user=new MUser();
        queryUser();



        user.setUniqueID(UniqueID);
        user.setUsername(System.currentTimeMillis()+"eatmem");
        user.setPassword(user.getUsername());
        if(!BmobUser.isLogin()) {
            user.signUp(new SaveListener<String>() {
                @Override
                public void done(String s, BmobException e) {
                    if (e == null) {
                        toast("添加数据成功，返回objectId为：" + s);
                    } else {
                        toast("创建数据失败：" + e.getMessage());
                    }
                }

            });

           user.login(new SaveListener<MUser>() {
               @Override
               public void done(MUser bmobUser, BmobException e) {
                   if (e == null) {
                       MUser user = BmobUser.getCurrentUser(MUser.class);
                    toast("登陆成功："+ user.getUsername());
                   } else {
                      toast( "登录失败：" + e.getMessage());
                   }
               }
           });
        }
        setContentView(R.layout.activity_main_app);


        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        NavigationView navigationview = (NavigationView) findViewById(R.id.navigation_view);
        final DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);//将toolbar与ActionBar关联
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, 0, 0);
        drawer.setDrawerListener(toggle);//初始化状态
        toggle.syncState();


        /*---------------------------添加头布局和尾布局-----------------------------*/
        //获取xml头布局view
        View headerView = navigationview.getHeaderView(0);
        //添加头布局的另外一种方式
        //View headview=navigationview.inflateHeaderView(R.layout.navigationview_header);

        //寻找头部里面的控件
        /*
        ImageView imageView = headerView.findViewById(R.id.iv_head);
        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getApplicationContext(), "点击了头像", Toast.LENGTH_LONG).show();
            }
        });
        */
        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                return false;
            }
        });
        ColorStateList csl = (ColorStateList) getResources().getColorStateList(R.color.nav_menu_text_color);
        //设置item的条目颜色
        navigationview.setItemTextColor(csl);
        //去掉默认颜色显示原来颜色  设置为null显示本来图片的颜色
        navigationview.setItemIconTintList(csl);

        //设置单个消息数量
        LinearLayout llAndroid = (LinearLayout) navigationview.getMenu().findItem(R.id.single_1).getActionView();
        TextView msg= (TextView) llAndroid.findViewById(R.id.msg_bg);
        msg.setText("99+");

        //设置条目点击监听
        navigationview.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                //安卓
                Toast.makeText(getApplicationContext(), menuItem.getTitle(), Toast.LENGTH_LONG).show();
                //设置哪个按钮被选中
//                menuItem.setChecked(true);
                //关闭侧边栏
//                drawer.closeDrawers();
                return false;
            }
        });


    }
    @SuppressLint("WrongConstant")
    public void toast(String s){
        Toast.makeText(getBaseContext(),s,0).show();
    }
}
