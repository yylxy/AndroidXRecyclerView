package com.joyoung.xrecylerview.recyclerview;

@SuppressWarnings("unused")
public class XRecyclerViewGlobal {
    public static int[] colors = new int[]{0x3F51B5, 0x99ff4081};
    
    public static String[] LOADING_TEXT = new String[]{"加载中...", "没有数据了" };
    
    
    static int[] getRefreshColor() {
        return colors;
    }
    
    public static void setColors(int[] colors) {
        XRecyclerViewGlobal.colors = colors;
    }
    
    public static void setLoadingText(String[] loadingText) {
        LOADING_TEXT = loadingText;
    }
}
