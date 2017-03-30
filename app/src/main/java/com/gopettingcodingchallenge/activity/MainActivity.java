package com.gopettingcodingchallenge.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.firebase.auth.FirebaseAuth;
import com.gopettingcodingchallenge.R;
import com.gopettingcodingchallenge.fragment.GuideCartFragment;
import com.gopettingcodingchallenge.fragment.GuideListFragment;
import com.gopettingcodingchallenge.util.CircleTransform;
import com.gopettingcodingchallenge.util.Constants;
import com.gopettingcodingchallenge.util.Utils;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, GoogleApiClient.OnConnectionFailedListener {
    ImageView ivProfileImage;
    TextView tvName, tvEmail;
    GoogleApiClient mGoogleApiClient;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        ivProfileImage = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.ivProfileImage);
        tvName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvName);
        tvEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvEmail);

        Picasso.with(this).load(Utils.getSharedPreference(this).getString(Constants.PREFERENCE_AVATAR, "")).fit().centerCrop().transform(new CircleTransform()).into(ivProfileImage);
        tvName.setText(Utils.getSharedPreference(this).getString(Constants.PREFERENCE_USER_NAME, ""));
        tvEmail.setText(Utils.getSharedPreference(this).getString(Constants.PREFERECE_EMAIL, ""));
        Log.d("avatar", "onCreate: " + Utils.getSharedPreference(this).getString(Constants.PREFERENCE_AVATAR, ""));

        Utils.replaceFragment(new GuideListFragment(), GuideListFragment.class.getName(), false, false, getSupportFragmentManager(), R.id.flContainer);
        initGoogleApiClient();
    }

    private void initGoogleApiClient() {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.default_web_client_id))
                .requestEmail()
                .build();
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */, this /* OnConnectionFailedListener */)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();
        if (id == R.id.nav_home) {
            Utils.replaceFragment(new GuideListFragment(), GuideListFragment.class.getName(), false, false, getSupportFragmentManager(), R.id.flContainer);
        } else if (id == R.id.nav_cart) {
            Utils.replaceFragment(new GuideCartFragment(), GuideCartFragment.class.getName(), false, false, getSupportFragmentManager(), R.id.flContainer);
        } else if (id == R.id.nav_logout) {
            signOut();
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void signOut() {
        Auth.GoogleSignInApi.signOut(mGoogleApiClient);
        FirebaseAuth.getInstance().signOut();
        Utils.getSharedPreference(this).edit().clear().apply();
        startActivity(new Intent(this, LoginActivity.class));
        finish();
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
