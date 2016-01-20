package com.appease.testdroid.views;

import android.app.ActionBar;
import android.app.FragmentManager;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.widget.DrawerLayout;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.appease.testdroid.NavigationDrawerFragment;
import com.appease.testdroid.R;
import com.appease.testdroid.common.Constant;


public class WelcomeActivity extends FragmentActivity implements NavigationDrawerFragment.NavigationDrawerCallbacks {

    private static final String TAG = WelcomeActivity.class.getSimpleName();

    private NavigationDrawerFragment mNavigationDrawerFragment;
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);

        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment.setUp(R.id.navigation_drawer, (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(int position) {
        if(Constant.Debug) Log.d(TAG, "onNavigationDrawerItemSelected()");

        FragmentManager fragmentManager = getFragmentManager();

        switch (position) {
            case 0:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new SurveysFragment())
                        .commit();
                mTitle = getString(R.string.title_section);
                break;
            case 1:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new ImagesFragment())
                        .commit();
                mTitle = getString(R.string.title_images);
                break;
            case 2:
                fragmentManager.beginTransaction()
                        .replace(R.id.container, new NewsFragment())
                        .commit();
                mTitle = getString(R.string.title_news);
                break;
        }
    }

    public void onSectionAttached(int number) {
        if(Constant.Debug) Log.d(TAG, "onSectionAttached()");
        switch (number) {
            case 0:
                mTitle = getString(R.string.title_section);
                break;
            case 1:
                mTitle = getString(R.string.title_images);
                break;
            case 2:
                mTitle = getString(R.string.title_news);
                break;
        }
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            getMenuInflater().inflate(R.menu.welcome, menu);
            restoreActionBar();
            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (Constant.Debug) Log.d(TAG, "onOptionsItemSelected()");
        int id = item.getItemId();
        if (id == R.id.action_settings) {
            if (Constant.Debug) Log.d(TAG, "onOptionsItemSelected() settings clicked");
            FragmentManager fragmentManager = getFragmentManager();

            fragmentManager.beginTransaction()
                    .replace(R.id.container, new PrefsFragment())
                    .commit();

            return true;
        }

        if (id == R.id.action_email) {
            Intent emailIntent = new Intent(Intent.ACTION_SENDTO, Uri.fromParts("mailto", "", null));
            emailIntent.putExtra(Intent.EXTRA_SUBJECT, "SampleTestingApp says...");
            emailIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(Intent.createChooser(emailIntent, "Sending email..."));
        }
        return super.onOptionsItemSelected(item);
    }
}
