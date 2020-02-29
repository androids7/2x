package as.mke.eatmem.decoder;

public class GVSDecoder {
    static {
        System.loadLibrary("decoder-lib");
    }

    public GVSDecoder(){


    }
    public int[] getPixels(String xmldata) {
        return  getPixelsNative(xmldata);

    }
    /*
    public int[] getPixels(String xmldata) {

       long []data= getPixelsNative(xmldata);
        int []pixels=new int[data.length];
        int ii=0;
        for(long aa:data) {
            int clr = new Long(aa).intValue();
            pixels[ii]=clr;
            ii++;
        }
        return  pixels;
    }
    */

  //  public native long[] getPixelsNative(String xmldata);
  public native int[] getPixelsNative(String xmldata);
}
