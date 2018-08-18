package com.android.carworkzassignment.adapter;

import android.content.Context;
import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.android.carworkzassignment.R;
import com.android.carworkzassignment.entity.GithubUserEntityModel;
import com.android.carworkzassignment.helper.GlideApp;
import com.android.carworkzassignment.utils.Utils;
import com.android.carworkzassignment.viewholder.GithubUserViewHolder;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class GithubUserAdapter extends RecyclerView.Adapter<GithubUserViewHolder> {

    private Context                     context;
    private List<GithubUserEntityModel> userEntityModelList;
    private View view;

    public GithubUserAdapter(Context context, List<GithubUserEntityModel> userEntityModelList) {
        this.context = context;
        this.userEntityModelList = userEntityModelList;
    }

    @NonNull
    @Override
    public GithubUserViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        view = LayoutInflater.from(context).inflate(R.layout.row_github_user,parent,false);
        return new GithubUserViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull GithubUserViewHolder holder, int position) {

        if(Utils.isNotNull(userEntityModelList)){

            GithubUserEntityModel githubUserEntityModel = userEntityModelList.get(position);
            if(Utils.isNotNull(githubUserEntityModel)){

                GlideApp.with(context)
                        .load(githubUserEntityModel.getAvatar_url())
                        .apply(RequestOptions.circleCropTransform())
                        .diskCacheStrategy(DiskCacheStrategy.ALL)
                        .placeholder(R.drawable.ic_user_avatar_placeholder)
                        .into(holder.ivUserImage);

                if(Utils.isNotNull(githubUserEntityModel.getLogin())){
                    holder.tvUserName.setText(githubUserEntityModel.getLogin());
                }

            }

        }

    }

    @Override
    public int getItemCount() {
        if(Utils.isNotNull(userEntityModelList)){
            return userEntityModelList.size();
        }else{
            return 0;
        }
    }

    public void notifyGithubUserDataset(List<GithubUserEntityModel> githubUserEntityModelList){
        userEntityModelList = githubUserEntityModelList;
        notifyDataSetChanged();
    }

    public int getLastUserId(){
        if(Utils.isNotNull(userEntityModelList) && Utils.isNotNull(userEntityModelList.get(getItemCount()-1))){
            return userEntityModelList.get(getItemCount()-1).getId();
        }
        return -1;
    }


}
