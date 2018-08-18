package com.android.carworkzassignment.application;

import android.app.Application;

import com.google.firebase.FirebaseApp;

public class CarworkzApplication extends Application{

    @Override
    public void onCreate() {
        super.onCreate();
        FirebaseApp.initializeApp(this);
    }
}
