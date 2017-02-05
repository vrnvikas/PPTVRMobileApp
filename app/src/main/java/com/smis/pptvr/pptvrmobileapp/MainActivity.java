package com.smis.pptvr.pptvrmobileapp;

import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Base64;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import com.arlib.floatingsearchview.FloatingSearchView;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.GetTokenResult;
import com.smis.pptvr.pptvrmobileapp.CustomChromeTabs.CustomTabActivityHelper;
import com.smis.pptvr.pptvrmobileapp.TabFragments.TabOneFragment;
import com.smis.pptvr.pptvrmobileapp.TabFragments.TabTwoFragment;
import com.smis.pptvr.pptvrmobileapp.network.FilterChangeListener;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener{

    public FloatingSearchView toolbar;
    private TabLayout tabLayout;
    private ViewPager viewPager;
    private CustomTabActivityHelper mCustomTabActivityHelper;
    private FilterChangeListener mListener;
    public  NavigationView navigationView;
    public TabOneFragment privateFragment;
    public TabTwoFragment publicFragment;
    private TextView tvUserName;
    private TextView tvUserEmail;

    private int[] tabIcons = {
            R.drawable.ic_action_person,
            R.drawable.ic_action_group,
            R.drawable.ic_action_call
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        toolbar = (FloatingSearchView) findViewById(R.id.toolbar);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);

        toolbar.attachNavigationDrawerToMenuButton(drawer);

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        tvUserName = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvUserDisplayName);
        tvUserEmail = (TextView) navigationView.getHeaderView(0).findViewById(R.id.tvUserEmail);
        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tablayout);
        tabLayout.setupWithViewPager(viewPager);



        mCustomTabActivityHelper = new CustomTabActivityHelper();

//        mListener = (FilterChangeListener) getBaseContext();
        final FirebaseUser mUser = FirebaseAuth.getInstance().getCurrentUser();
        mUser.getToken(true)
                .addOnCompleteListener(new OnCompleteListener<GetTokenResult>() {
                    public void onComplete(@NonNull Task<GetTokenResult> task) {
                        if (task.isSuccessful()) {
                            String idToken = task.getResult().getToken();
                            Log.d("GoogleActivity","userToken: "+mUser.getDisplayName());
                            // Send token to your backend via HTTPS

                        } else {
                            // Handle error -> task.getException();

                        }
                    }
                });

        tvUserName.setText(mUser.getDisplayName());
        if(mUser.getEmail() != null){
            tvUserEmail.setText(mUser.getEmail());
        }


    }


    public FloatingSearchView getSearchView(){
        return toolbar;
    }

    @Override
    protected void onResume() {
        super.onResume();

    }

    @Override
    protected void onStart() {
        super.onStart();
        mCustomTabActivityHelper.bindCustomTabsService(this);
    }

    @Override
    protected void onStop() {
        super.onStop();
        mCustomTabActivityHelper.unbindCustomTabsService(this);
    }

    private void setupViewPager(ViewPager viewPager) {
        privateFragment = new TabOneFragment();
        publicFragment = new TabTwoFragment();
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(privateFragment, "Private");
        adapter.addFragment(publicFragment, "Public");
        viewPager.setAdapter(adapter);

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
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.all_projects) {
            privateFragment.changeFilter("all");
            publicFragment.changeFilter("all");
        } else if (id == R.id.most_viewed_projects) {
           privateFragment.changeFilter("most_viewed");
            publicFragment.changeFilter("most_viewed");
        } else if (id == R.id.starred_projects) {
            privateFragment.changeFilter("most_starred");
            publicFragment.changeFilter("most_starred");
        } else if (id == R.id.logout) {
            Intent myIntent = new Intent(this, SignInActivity.class);
            myIntent.putExtra("initState","signout");
            startActivity(myIntent);
        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}
