package com.miguan.youmi.module.common.util;


import java.util.List;

/**
 * Desc:
 * <p>
 * Author: SonnyJack
 * Date: 2018-09-25
 * Copyright: Copyright (c) 2013-2018
 * Company: @米冠网络
 * Update Comments:
 */
public class PickButterKnife {

    public static <T> void apply(List<T> dataList, PickAction<T> pickAction){
        apply(dataList, false, pickAction);
    }

    //会根据 pickAction的返回值决定是否继续循环
    public static <T> void apply(List<T> dataList, boolean filterNull, PickAction<T> pickAction){
        if(null == dataList || dataList.isEmpty()){
            return;
        }
        for (int i = 0; i < dataList.size(); i++) {
            T item = dataList.get(i);
            if(null == item && filterNull){
                continue;
            }
            boolean isContinue = pickAction.apply(item, i);
            if(!isContinue){
                return;
            }
        }
    }

    //循环
    public static <T> void apply(List<T> dataList, boolean filterNull, PickLoopAction<T> pickLoopAction){
        if(null == dataList || dataList.isEmpty()){
            return;
        }
        for (int i = 0; i < dataList.size(); i++) {
            T item = dataList.get(i);
            if(null == item && filterNull){
                continue;
            }
            pickLoopAction.apply(item, i);
        }
    }

    public interface PickAction<T> {
        boolean apply(T item, int position);
    }

    public interface PickLoopAction<T> {
        void apply(T item, int position);
    }
}
