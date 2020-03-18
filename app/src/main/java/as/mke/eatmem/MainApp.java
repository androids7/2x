package as.mke.eatmem;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.widget.Toast;

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
        user.setUsername(System.currentTimeMillis()+"");
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
    }
    @SuppressLint("WrongConstant")
    public void toast(String s){
        Toast.makeText(getBaseContext(),s,0).show();
    }
}
