package com.joyoung.xrecylerview.util;

import java.util.List;

/**
 * 分页的检测
 */
public class PagingDetection implements IPagingDetection{
    
    @Override
    public boolean isPaging(List initialList, List newList) {
        return false;
    }
    
    @Override
    public void setPagingSize(int pagingSize) {
    
    }
}
