package as.mke.eatmem;

import android.app.Application;

import as.mke.eatmem.adapter.ScreenAdaptation;

public class App extends Application {

@Override

public void onCreate() {

        super.onCreate();

//需要传入ui设计给的大小,初始化

        new ScreenAdaptation(this,720,1280).register();

        }

        }
