package com.android.carworkzassignment.utils;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;

public class Utils {

    public static boolean isConnectedToInternet(Context context) {
        if (context != null) {
            ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
            NetworkInfo networkInfo = connectivityManager.getActiveNetworkInfo();
            return networkInfo != null && networkInfo.isConnectedOrConnecting();
        }

        return false;
    }

    public static boolean isNotNull(Object object){
        if(object == null){
            return false;
        }else{
            return true;
        }
    }

    public static <T> List<T> deSerializeResponseList(String json, Class<T> clazz) {
        List<T> list = null;
        try {
            list = new Gson().fromJson(json, new ListWithElements<T>(clazz));
        } catch (Exception e) {

        }
        return list;
    }

    private static class ListWithElements<T> implements ParameterizedType {

        private Class<T> elementsClass;

        public ListWithElements(Class<T> elementsClass) {
            this.elementsClass = elementsClass;
        }

        public Type[] getActualTypeArguments() {
            return new Type[]{elementsClass};
        }

        public Type getRawType() {
            return List.class;
        }

        public Type getOwnerType() {
            return null;
        }
    }




}
