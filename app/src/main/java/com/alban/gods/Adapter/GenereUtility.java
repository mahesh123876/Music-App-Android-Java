package com.alban.gods.Adapter;

public class GenereUtility {
    public static String GconvertDuration(long gduration){
        long gminutes=(gduration/1000)/60;
        long gseconds=(gduration/1000)%60;
        String Gconverted=String.format("%d:%02d",gminutes,gseconds);
        return Gconverted;

    }
}