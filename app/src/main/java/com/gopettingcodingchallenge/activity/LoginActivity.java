package com.gopettingcodingchallenge.activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GoogleAuthProvider;
import com.gopettingcodingchallenge.R;
import com.gopettingcodingchallenge.util.Constants;
import com.gopettingcodingchallenge.util.Utils;

public class LoginActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private static final int RC_SIGN_IN = 901;
    Button btnGoogleLogin;
    private String picUrl = "", name = "", email = "";
    private GoogleApiClient mGoogleApiClient;
    private FirebaseAuth mAuth;
    private FirebaseUser user;
    private FirebaseAuth.AuthStateListener mAuthListener;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        setWidgetReference();
        bindWidgetEvent();
        initialization();
    }

    private void initialization() {

        mAuth = FirebaseAuth.getInstance();
        createSignInUser();
        mAuthListener = new FirebaseAuth.AuthStateListener() {
            @Override
            public void onAuthStateChanged(@NonNull FirebaseAuth firebaseAuth) {
                final FirebaseUser user = firebaseAuth.getCurrentUser();
                if (user != null) {
                    LoginActivity.this.user = user;
                    saveUserData(user);
                }
            }
        };
    }

    private void saveUserData(FirebaseUser user) {
        Utils.getSharedPreference(LoginActivity.this).edit().putString(Constants.PREFERENCE_USER_NAME, user.getDisplayName()).apply();
        Utils.getSharedPreference(LoginActivity.this).edit().putString(Constants.PREFERENCE_AVATAR, (user.getPhotoUrl() != null ? user.getPhotoUrl().toString() : "no-image.jpg")).apply();
        Utils.getSharedPreference(LoginActivity.this).edit().putString(Constants.PREFERECE_EMAIL, user.getEmail()).apply();
        Utils.getSharedPreference(LoginActivity.this).edit().putBoolean(Constants.PREFERENCE_IS_USER_LOGGED_IN, true).apply();
//        Utils.getSharedPreference(LoginActivity.this).edit().putString(Constants.PREFERENCE_MOBILE_NUMBER, phoneNumber).apply();
        startActivity(new Intent(LoginActivity.this, MainActivity.class));
        finish();
    }


    private void createSignInUser() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();

    }

    private void bindWidgetEvent() {
        btnGoogleLogin.setOnClickListener(this);
    }

    private void setWidgetReference() {
        btnGoogleLogin = (Button) findViewById(R.id.btnGoogleLogin);
    }

    @Override
    public void onClick(View view) {
        if (view == btnGoogleLogin) {
            signIn();
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == RC_SIGN_IN) {
            GoogleSignInResult result = Auth.GoogleSignInApi.getSignInResultFromIntent(data);
            if (result.isSuccess()) {
                GoogleSignInAccount account = result.getSignInAccount();
                firebaseAuthWithGoogle(account);
            }
        }
        super.onActivityResult(requestCode, resultCode, data);
    }

    private void firebaseAuthWithGoogle(GoogleSignInAccount account) {
        AuthCredential credential = GoogleAuthProvider.getCredential(account.getIdToken(), null);
        mAuth.signInWithCredential(credential)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                    }
                });

    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }

    @Override
    public void onStart() {
        super.onStart();
        mAuth.addAuthStateListener(mAuthListener);
    }

    @Override
    public void onStop() {
        super.onStop();
        if (mAuthListener != null) {
            mAuth.removeAuthStateListener(mAuthListener);
        }
    }

    private void signIn() {
        Intent signInIntent = Auth.GoogleSignInApi.getSignInIntent(mGoogleApiClient);
        startActivityForResult(signInIntent, RC_SIGN_IN);
    }

}
