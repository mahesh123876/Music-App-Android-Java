package com.alban.gods.Adapter;

public class TrendingUtility {
    public static String TconvertDuration(long Tduration){
        long Tminutes=(Tduration/1000)/60;
        long Tseconds=(Tduration/1000)%60;
        String Tconverted=String.format("%d:%02d",Tminutes,Tseconds);
        return Tconverted;

    }
}
