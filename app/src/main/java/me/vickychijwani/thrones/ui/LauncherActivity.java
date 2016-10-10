package me.vickychijwani.thrones.ui;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.MenuItem;

import me.vickychijwani.thrones.R;

public class LauncherActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private static final String TAG = "LauncherActivity";

    private EpisodesFragment mEpisodesFragment = null;
    private WallpapersFragment mWallpapersFragment = null;

    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_episodes);

        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        NavigationView navView =  (NavigationView) findViewById(R.id.nav_view);
        navView.setNavigationItemSelectedListener(this);

        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (fragment == null) {
            FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
            if (mEpisodesFragment == null) {
                mEpisodesFragment = EpisodesFragment.newInstance();
                ft.replace(R.id.fragment_container, mEpisodesFragment);
            } else {
                ft.attach(mEpisodesFragment);
            }
            ft.commit();
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        mDrawerLayout.closeDrawer(GravityCompat.START);
        Fragment currentFragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        Fragment newFragment;
        switch (item.getItemId()) {
            case R.id.action_episode_recaps:
                if (currentFragment instanceof EpisodesFragment) {
                    return true;
                }
                if (mEpisodesFragment == null) {
                    mEpisodesFragment = EpisodesFragment.newInstance();
                }
                newFragment = mEpisodesFragment;
                break;
            case R.id.action_wallpapers:
                if (currentFragment instanceof WallpapersFragment) {
                    return true;
                }
                if (mWallpapersFragment == null) {
                    mWallpapersFragment = WallpapersFragment.newInstance();
                }
                newFragment = mWallpapersFragment;
                break;
            default:
                return false;
        }
        //noinspection ConstantConditions
        if (newFragment == null) {
            Log.wtf(TAG, "You forgot to assign to newFragment!");
        }
        FragmentTransaction ft = getSupportFragmentManager()
                .beginTransaction()
                .detach(currentFragment);
        if (newFragment.isDetached()) {
            ft.attach(newFragment);
        } else {
            ft.add(R.id.fragment_container, newFragment);
        }
        ft.commit();
        return true;
    }

}