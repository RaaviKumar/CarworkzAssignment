package com.android.carworkzassignment.repositories;


import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;

import com.android.carworkzassignment.dao.GithubUserDao;
import com.android.carworkzassignment.dataprovider.JsonCachceRequest;
import com.android.carworkzassignment.dataprovider.VolleySingleton;
import com.android.carworkzassignment.entity.GithubUserEntityModel;
import com.android.carworkzassignment.threadexecutors.AppExecutors;
import com.android.carworkzassignment.utils.UrlConstants;
import com.android.carworkzassignment.utils.Utils;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.HttpHeaderParser;

import java.io.UnsupportedEncodingException;
import java.util.List;


public class GithubUserRepository {

    private Context       context;
    private GithubUserDao githubUserDao;

    public GithubUserRepository(Context context, GithubUserDao githubUserDao) {
        this.context       = context;
        this.githubUserDao = githubUserDao;
    }


    public LiveData<List<GithubUserEntityModel>> getGithubUsers(int userSince) {
        fetchGithubUser(userSince);
        return githubUserDao.getAllGithubUser(); // return a LiveData directly from the database.
    }

    public void fetchGithubUser(int userSince) {

        JsonCachceRequest jsonCachceRequest = new JsonCachceRequest(Request.Method.GET, UrlConstants.BASE_URL + UrlConstants.GITHUB_ALL_USER_URL
                + UrlConstants.QUESTION_PARAM + UrlConstants.SINCE_KEY+ UrlConstants.EQUALS_PARAM +userSince, new Response.Listener<NetworkResponse>() {
            @Override
            public void onResponse(NetworkResponse response) {
                try {


                    String networkData = new String(response.data,
                            HttpHeaderParser.parseCharset(response.headers));

                    Log.d("response ", networkData + "");

                    List<GithubUserEntityModel> userEntityModel = Utils.deSerializeResponseList(networkData,GithubUserEntityModel.class);

                    new AppExecutors().networkIO().execute(() -> {
                        if(Utils.isNotNull(userEntityModel)){
                            for (GithubUserEntityModel userData : userEntityModel){
                                githubUserDao.addGithubUser(userData);
                            }
                        }
                    });



                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }
        }, error -> {

        });


        if (context != null) {
            jsonCachceRequest.setShouldCache(true);
            VolleySingleton.getInstance(context.getApplicationContext()).addToRequestQueue(jsonCachceRequest);
        }

    }

}
