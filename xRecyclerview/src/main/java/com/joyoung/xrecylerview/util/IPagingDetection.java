package com.joyoung.xrecylerview.util;

import java.util.List;

public interface IPagingDetection {
    
    boolean isPaging(List initialList, List newList);
    
    void setPagingSize(int pagingSize);
}
