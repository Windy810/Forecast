package com.windy.forecast;

import android.app.Application;

public class VariableApp extends Application {
    private static VariableApp instance = null;
    public static VariableApp getInstance(){
        return instance;
    }
    @Override
    public void onCreate(){
        super.onCreate();
        instance = this;
    }
}
