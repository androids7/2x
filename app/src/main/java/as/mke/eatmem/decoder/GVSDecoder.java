package as.mke.eatmem.decoder;

import as.mke.eatmem.MainActivity;

public class GVSDecoder {
    static {
      //  System.loadLibrary("decoder-lib");
    }

    public GVSDecoder(MainActivity activity){
      //  init(this);

    }
    public native void init(MainActivity activity);
    public native int[] getPixels(String xmldata);

}
