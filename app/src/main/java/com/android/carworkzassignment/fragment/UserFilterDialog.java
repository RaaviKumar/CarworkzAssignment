package com.android.carworkzassignment.fragment;


import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.carworkzassignment.R;
import com.android.carworkzassignment.interfaces.FilterUserListener;
import com.android.carworkzassignment.utils.Utils;

import butterknife.BindView;
import butterknife.ButterKnife;

public class UserFilterDialog extends DialogFragment implements View.OnClickListener {


    //@formatter:off
    @BindView(R.id.et_filter_user_name)   AppCompatEditText etFilterUserName;
    @BindView(R.id.tv_filter_cancel)      AppCompatTextView tvCancel;
    @BindView(R.id.tv_filter_ok)          AppCompatTextView tvOk;
    //@formatter:on
    private FilterUserListener filterUserListener;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_user_filter, container, false);
        ButterKnife.bind(this, rootView);
        initializeResources();
        return rootView;
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        filterUserListener = (FilterUserListener) context;
    }

    private void initializeResources(){
        tvCancel.setOnClickListener(this);
        tvOk.setOnClickListener(this);
    }

    @Override
    public void onDismiss(DialogInterface dialog) {
        super.onDismiss(dialog);
        if(Utils.isNotNull(filterUserListener)){
            filterUserListener.filterUser(etFilterUserName.getText().toString());
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){

            case R.id.tv_filter_ok:

                if(isValid()){
                    dismiss();
                }

                break;


            case R.id.tv_filter_cancel:
                dismiss();
                break;

        }
    }


    private boolean isValid(){
        if(TextUtils.isEmpty(etFilterUserName.getText().toString())){
            showToast(getString(R.string.error_filter_name));
            return false;
        }
        return true;
    }

    private void showToast(String msg){
        Toast.makeText(getActivity(),msg,Toast.LENGTH_LONG).show();
    }



}
