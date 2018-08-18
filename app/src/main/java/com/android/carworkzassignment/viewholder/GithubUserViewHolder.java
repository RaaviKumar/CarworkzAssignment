package com.android.carworkzassignment.viewholder;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.android.carworkzassignment.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GithubUserViewHolder extends RecyclerView.ViewHolder{

    //@formatter:off
    public @BindView(R.id.iv_user_image) AppCompatImageView ivUserImage;

    public @BindView(R.id.iv_user_name)  AppCompatTextView  tvUserName;

    //@formatter:on

    public GithubUserViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this,itemView);
    }

}
