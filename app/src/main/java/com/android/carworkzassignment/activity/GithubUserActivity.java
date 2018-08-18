package com.android.carworkzassignment.activity;

import android.arch.lifecycle.ViewModelProviders;
import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;

import com.android.carworkzassignment.R;
import com.android.carworkzassignment.adapter.GithubUserAdapter;
import com.android.carworkzassignment.entity.GithubUserEntityModel;
import com.android.carworkzassignment.fragment.UserFilterDialog;
import com.android.carworkzassignment.interfaces.FilterUserListener;
import com.android.carworkzassignment.utils.Utils;
import com.android.carworkzassignment.viewmodel.GithubUserDataModel;

import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GithubUserActivity extends BaseActivity implements View.OnClickListener,FilterUserListener{

    //@formatter:off
    @BindView(R.id.rv_github_user)  RecyclerView       rvGithubUser;
    @BindView(R.id.iv_user_filter)  AppCompatImageView ivGithubUserFilter;

    //@formatter:on
    private GithubUserDataModel model;
    private GithubUserAdapter   userAdapter;
    private LinearLayoutManager linearLayoutManager;
    private final int MAX_USER_LIMIT = 200;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_user);
        ButterKnife.bind(this);
        initializeResources();
    }

    private void initializeResources() {
        linearLayoutManager = new LinearLayoutManager(this);
        rvGithubUser.setLayoutManager(linearLayoutManager);
        ivGithubUserFilter.setOnClickListener(this);
        setScrollListener();
        showProgressDialog();
        model = ViewModelProviders.of(this).get(GithubUserDataModel.class);
        model.getAllGithubUser().observe(this, githubUserEntityModelList -> {

            if (Utils.isNotNull(githubUserEntityModelList) && githubUserEntityModelList.size() > 0) {
                for (int i = 0; i < githubUserEntityModelList.size(); i++) {
                    if (Utils.isNotNull(githubUserEntityModelList.get(i))) {
                        setAdapter(githubUserEntityModelList);
                    }
                }
                hideProgressDialog();
            }


        });


        int USER_SINCE_PAGE_DATA = 0;
        model.loadAllGithubUser(USER_SINCE_PAGE_DATA);
    }

    private void setAdapter(List<GithubUserEntityModel> githubUserEntityModelList){
        if(userAdapter == null){
            userAdapter = new GithubUserAdapter(this,githubUserEntityModelList);
            rvGithubUser.setAdapter(userAdapter);
        }else{
            userAdapter.notifyGithubUserDataset(githubUserEntityModelList);
        }
    }

    private void setScrollListener() {
        rvGithubUser.addOnScrollListener(new RecyclerView.OnScrollListener() {
            @Override
            public void onScrolled(RecyclerView recyclerView, int dx, int dy) {


                if (dy > 0)
                {

                    int visibleItemCount = linearLayoutManager.getChildCount();
                    int totalItemCount = linearLayoutManager.getItemCount();
                    int pastVisiblesItems = linearLayoutManager.findFirstVisibleItemPosition();

                    if(Utils.isNotNull(userAdapter)){

                        if(userAdapter.getItemCount() < MAX_USER_LIMIT){
                            if ((visibleItemCount + pastVisiblesItems) >= totalItemCount) {
                                if(userAdapter.getLastUserId() > 0){
                                    int lastSelectedUserId = userAdapter.getLastUserId();
                                    model.loadAllGithubUser(lastSelectedUserId);
                                }

                            }

                        }
                    }

                }

            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.iv_user_filter:
                showUserFilterDialog();
                break;

        }
    }

    private void showUserFilterDialog() {
        FragmentTransaction fragmentTransaction = getSupportFragmentManager().beginTransaction();
        UserFilterDialog fragment = new UserFilterDialog();
        fragment.show(fragmentTransaction, "UserFilterDialog");
    }

    @Override
    public void filterUser(String filterUserName) {

        model.filterGithubUser(filterUserName).observe(this, githubUserEntityModelList -> {

            if (Utils.isNotNull(githubUserEntityModelList)) {
                for (int i = 0; i < githubUserEntityModelList.size(); i++) {
                    if (Utils.isNotNull(githubUserEntityModelList.get(i))) {
                        setAdapter(githubUserEntityModelList);
                    }
                }
            }

        });
    }
}
