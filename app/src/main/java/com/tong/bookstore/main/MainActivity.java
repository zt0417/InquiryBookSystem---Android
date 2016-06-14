package com.tong.bookstore.main;

import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import com.tong.bookstore.BookStoreNotification;
import com.tong.bookstore.R;
import com.tong.bookstore.bookstore.BookStoreFragment;
import com.tong.bookstore.database.SQLiteHelper;
import com.tong.bookstore.market.MarketFragment;
import com.tong.bookstore.mybook.MyBookFragment;
import com.tong.bookstore.receive.ScreenListener;
import com.tong.bookstore.setting.SettingFragment;

/**
 * Created by tzhang1-cc on 2016/3/11.
 */
//This funcation is create main activity
public class MainActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Toolbar toolBar;
    private DrawerLayout drawerLayout;
    private NavigationView navigation;
    private MyBookFragment myBookFragment;
    private FragmentManager fragmentManager;
    private BookStoreFragment bookStoreFragment;
    private MarketFragment marketFragment;
    private SettingFragment settingFragment;
    private Fragment tempFragment = null;
    private long lastTime = 0;
    //screen listener
    private ScreenListener screenListener;

    private static final String TAG = "MainActivity";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        findViewByIds();
        initViews();
        iniScreenBroadCast();
    }


    //
    private void iniScreenBroadCast() {
        screenListener = new ScreenListener(getApplicationContext());
        screenListener.begin(screenStateListener);
    }

    private ScreenListener.ScreenStateListener screenStateListener = new ScreenListener.ScreenStateListener() {

        @Override
        public void onScreenOn() {
            Log.i(TAG, "onScreenOn");
        }

        @Override
        public void onScreenOff() {
            BookStoreNotification.cancelAll(getApplicationContext());
            Log.i(TAG, "onScreenOff");
        }

        @Override
        public void onUserPresent() {
            Log.i(TAG, "onUserPresent");
        }
    };

    private void initViews() {
        setSupportActionBar(toolBar);
        ActionBarDrawerToggle mActionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolBar, R.string.open, R.string.close);
        mActionBarDrawerToggle.syncState();
        drawerLayout.addDrawerListener(mActionBarDrawerToggle);
        navigation.setNavigationItemSelectedListener(this);
        navigation.setCheckedItem(R.id.nav_menu_market);

        fragmentManager = getSupportFragmentManager();
        marketFragment = new MarketFragment();
        fragmentManager.beginTransaction().replace(R.id.fragment_layout, marketFragment).commit();
        tempFragment = marketFragment;
        SQLiteHelper.getDB(getApplicationContext());
    }

    private void findViewByIds() {
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        toolBar = (Toolbar) findViewById(R.id.toolbar);
        navigation = (NavigationView) findViewById(R.id.navigation);
    }

    // menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_setting, menu);
        return true;
    }

    //about actvity
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.action_about) {
            startActivity(new Intent(this, AboutActivity.class));
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    // navigation part
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {

        Fragment fragment = null;
        String title = "";

        switch (item.getItemId()) {
            case R.id.nav_menu_my_book:
                title = (String) item.getTitle();

                if (myBookFragment == null) {
                    myBookFragment = new MyBookFragment();
                }
                fragment = myBookFragment;

                break;
            case R.id.nav_menu_book_store:
                title = (String) item.getTitle();

                if (bookStoreFragment == null) {
                    bookStoreFragment = new BookStoreFragment();
                }
                fragment = bookStoreFragment;

                break;
            case R.id.nav_menu_market:
                title = (String) item.getTitle();

                if (marketFragment == null) {
                    marketFragment = new MarketFragment();
                }
                fragment = marketFragment;

                break;
            case R.id.nav_menu_setting:
                title = (String) item.getTitle();

                if (settingFragment == null) {
                    settingFragment = new SettingFragment();
                }
                fragment = settingFragment;

                break;
        }

        item.setChecked(true);
        drawerLayout.closeDrawers();
        toolBar.setTitle(title);
        switchContent(tempFragment, fragment);
        return true;
    }

    public void switchContent(Fragment from, Fragment to) {
        if (tempFragment != to) {
            tempFragment = to;
            FragmentTransaction transaction = fragmentManager.beginTransaction();
            if (!to.isAdded()) {
                transaction.hide(from).add(R.id.fragment_layout, to).commit();
            } else {
                transaction.hide(from).show(to).commit();
            }
        }
    }


    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawers();
            return;
        }
        if (System.currentTimeMillis() - lastTime < 2000) {
            super.onBackPressed();
        } else {
            lastTime = System.currentTimeMillis();
            Toast.makeText(MainActivity.this, getString(R.string.exit_point), Toast.LENGTH_SHORT).show();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        if (screenListener != null) {
            screenListener.unregisterListener();
            screenListener = null;
        }

        SQLiteDatabase db = SQLiteHelper.getDB(getApplicationContext());
        if (db != null) {
            db.close();
        }
    }
}
