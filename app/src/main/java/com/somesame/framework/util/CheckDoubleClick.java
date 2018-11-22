package com.somesame.framework.util;

/**
 * Created by Admin on 17/6/1.
 */
public class CheckDoubleClick {

    private static long lastClickTime=0;
    public static boolean isFastDoubleClick() {
        long time = System.currentTimeMillis();
        long timeD = time - lastClickTime;
        if ( 0 < timeD && timeD < 500) {
            return true;
        }
        lastClickTime = time;
        return false;
    }
}
