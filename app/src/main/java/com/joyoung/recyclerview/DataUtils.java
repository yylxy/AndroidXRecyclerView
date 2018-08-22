package com.joyoung.recyclerview;

import java.util.ArrayList;
import java.util.List;

@SuppressWarnings("WeakerAccess")
public class DataUtils {
    
    public static List<String> getListData() {
        List<String> list = new ArrayList<>();
        for (int i = 'A'; i <= 'z'; i++) {
            list.add("" + (char) i);
        }
        return list;
    }
    public static List<String> getListData(List<String> data) {
      
        for (int i = 'A'; i <= 'Q'; i++) {
            data.add("" + (char) i);
        }
        return data;
    }
}
