package com.android.carworkzassignment.activity;

import android.content.Intent;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.widget.AppCompatButton;
import android.support.v7.widget.AppCompatEditText;
import android.support.v7.widget.AppCompatTextView;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.android.carworkzassignment.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.FirebaseApp;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GithubSignInActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "AnonymousAuth";

    //@formatter:off
    @BindView(R.id.field_email)     AppCompatEditText mEmailField;
    @BindView(R.id.field_password)  AppCompatEditText mPasswordField;
    @BindView(R.id.button_submit)   AppCompatButton   btnSubmit;
    @BindView(R.id.tv_auth_skip)    AppCompatTextView tvAuthSkip;
    //@formatter:on

    private FirebaseAuth mAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_github_sign_in);
        ButterKnife.bind(this);
        initializeResources();
    }

    private void initializeResources() {
        mAuth = FirebaseAuth.getInstance();
        underLineTextView(tvAuthSkip);
        // [END initialize_auth]
        tvAuthSkip.setOnClickListener(this);
        btnSubmit.setOnClickListener(this);
    }

    private void underLineTextView(TextView textView){
        textView.setPaintFlags(textView.getPaintFlags() | Paint.UNDERLINE_TEXT_FLAG);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.button_submit:
                hideKeyboard(v);
                if(isValid()){
                    signInAnonymously();
                }

                break;

            case R.id.tv_auth_skip:
                startUserActivity();
                break;

        }
    }


    private void signInAnonymously() {
        showProgressDialog();

        mAuth.signInAnonymously()
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Log.d(TAG, "signInAnonymously:success");
                            FirebaseUser user = mAuth.getCurrentUser();
                            checkUserLoggedInStatus(user);
                        } else {
                            // If sign in fails, display a message to the user.
                            showToastMsg(getString(R.string.error_authentication_failed));
                        }

                        hideProgressDialog();

                    }
                });
    }

    private void checkUserLoggedInStatus(FirebaseUser user){
        boolean isSignedIn = (user != null);

        // Status text
        if (isSignedIn) {
            startUserActivity();
        }
    }

    private void startUserActivity(){
        Intent intent=new Intent(this,GithubUserActivity.class);
        startActivity(intent);
        finish();
    }


    private boolean isValid(){
        if(TextUtils.isEmpty(mEmailField.getText())){
            showToastMsg(getString(R.string.error_please_enter_email_address));
            return false;
        }else if(TextUtils.isEmpty(mPasswordField.getText())){
            showToastMsg(getString(R.string.error_please_enter_password));
            return false;
        }
        return true;
    }


}
