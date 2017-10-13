package com.day.numen;

import android.Manifest;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.NavigationView;
import android.support.design.widget.Snackbar;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AlertDialog;
import android.support.v7.widget.Toolbar;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;

import com.day.numen.browser.WebBrowserFragment;
import com.day.numen.common.BaseActivity;
import com.day.numen.common.BaseFragment;
import com.day.numen.common.OttoBus;
import com.day.numen.common.PermissionManager;
import com.day.numen.contacts.ContactListActivity;
import com.day.numen.contacts.model.Contact;
import com.day.numen.home.HomeFragment;
import com.day.numen.keeplive.KeepLiveManager;
import com.day.numen.settings.SettingsActivity;
import com.day.numen.settings.SettingsFragment;
import com.day.numen.urgent.UrgentContactListFragment;
import com.day.numen.urgent.event.MessageEvent;
import com.day.numen.urgent.helper.UrgentHelper;

import cn.bmob.v3.Bmob;
import cn.bmob.v3.update.BmobUpdateAgent;

public class MainActivity extends BaseActivity implements NavigationView.OnNavigationItemSelectedListener {

    private Handler mHandler = new Handler();

    private BaseFragment mCurrentFragment;

    private FloatingActionButton mActionButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        mActionButton = (FloatingActionButton) findViewById(R.id.fab);

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

        // 显示首页
        onNavigationItemSelected(R.id.nav_home);
        // 检查权限
        checkPermission();

        KeepLiveManager.startJobScheduler(this);

        Bmob.initialize(this, "");
        //BmobUpdateAgent.initAppVersion();
        BmobUpdateAgent.silentUpdate(this);
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
            Intent intent = new Intent(this, SettingsActivity.class);
            startActivity(intent);
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);

        // Handle navigation view item clicks here.
        final int id = item.getItemId();

        mHandler.postDelayed(new Runnable() {
            @Override
            public void run() {
                onNavigationItemSelected(id);
            }
        }, 300);

        switch (id) {
            case R.id.nav_home: {
                return true;
            }
            case R.id.nav_urgent_contacts: {
                return true;
            }
            case R.id.nav_settings: {
                return true;
            }
            case R.id.nav_help: {
                return true;
            }
            case R.id.nav_about: {
                return true;
            }
            default: {
                return false;
            }
        }
    }


    private void onNavigationItemSelected(int id) {
        switch (id) {
            case R.id.nav_home: {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(getString(R.string.app_name));
                }
                mCurrentFragment = new HomeFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_container, mCurrentFragment).commit();
                changeActionButton();
                break;
            }
            case R.id.nav_urgent_contacts: {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(getString(R.string.nav_urgent_contacts));
                }
                mCurrentFragment = new UrgentContactListFragment();
                getSupportFragmentManager().beginTransaction().replace(R.id.content_container, mCurrentFragment).commit();
                changeActionButton();
                break;
            }
            case R.id.nav_settings: {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(getString(R.string.nav_settings));
                }
                mCurrentFragment = new SettingsFragment();
                Bundle args = new Bundle();
                args.putBoolean(SettingsFragment.FROM, getIntent().getBooleanExtra(SettingsFragment.FROM, true));
                mCurrentFragment.setArguments(args);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_container, mCurrentFragment).commit();
                changeActionButton();
                break;
            }
            case R.id.nav_help: {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(getString(R.string.nav_help));
                }
                Bundle bundle = new Bundle();
                bundle.putString("key.base.title", "");
                bundle.putString("key.base.url", "file:///android_asset/doc/doc.html");
                bundle.putBoolean("key.base.toolbar", false);

                mCurrentFragment = new WebBrowserFragment();
                mCurrentFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_container, mCurrentFragment).commit();
                changeActionButton();
                break;
            }
            case R.id.nav_about: {
                if (getSupportActionBar() != null) {
                    getSupportActionBar().setTitle(getString(R.string.nav_about));
                }
                Bundle bundle = new Bundle();
                bundle.putString("key.base.title", "");
                bundle.putString("key.base.url", "file:///android_asset/doc/about.html");
                bundle.putBoolean("key.base.toolbar", false);

                mCurrentFragment = new WebBrowserFragment();
                mCurrentFragment.setArguments(bundle);
                getSupportFragmentManager().beginTransaction().replace(R.id.content_container, mCurrentFragment).commit();
                changeActionButton();
                break;
            }
            default: {
                break;
            }
        }
    }

    private void changeActionButton() {
        if (mCurrentFragment instanceof UrgentContactListFragment) {
            mActionButton.setVisibility(View.VISIBLE);
            mActionButton.setImageResource(R.drawable.ic_person_add_white_24dp);
            mActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(final View view) {
                    View layout = View.inflate(MainActivity.this, R.layout.layout_urgent_contact_add, null);
                    final EditText nameView = layout.findViewById(R.id.tv_name);
                    final EditText mobileView = layout.findViewById(R.id.tv_mobile);
                    new AlertDialog.Builder(MainActivity.this)
                            .setTitle(R.string.contact_add)
                            .setView(layout)
                            .setPositiveButton(R.string.ok, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    String name = nameView.getText().toString();
                                    if (TextUtils.isEmpty(name)) {
                                        Snackbar.make(view, R.string.error_contact_name_required, Snackbar.LENGTH_LONG).show();
                                        return;
                                    }
                                    String mobile = mobileView.getText().toString();
                                    if (TextUtils.isEmpty(mobile)) {
                                        Snackbar.make(view, R.string.error_contact_phone_number_required, Snackbar.LENGTH_LONG).show();
                                        return;
                                    }
                                    UrgentHelper.addUrgentContact(view, new Contact(name, mobile));
                                }
                            })
                            .setNegativeButton(R.string.cancel, null)
                            .setNeutralButton(R.string.contact_choice, new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialogInterface, int i) {
                                    PermissionManager.requestPermissions(MainActivity.this, new PermissionManager.OnPermissionListener() {
                                        @Override
                                        public void onRequestPermissionsResult(boolean success, String[] permissions, int[] grantResults, boolean[] showRationale) {
                                            if (success) {
                                                startActivityForResult(new Intent(MainActivity.this, ContactListActivity.class), 123);
                                            }
                                        }
                                    }, Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS);
                                }
                            })
                            .show();
                }
            });
        } else {
            mActionButton.setVisibility(View.GONE);
            mActionButton.setOnClickListener(null);
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 123) {
            if (mCurrentFragment instanceof UrgentContactListFragment) {
                OttoBus.getInstance().post(new MessageEvent("refresh", null));
            }
        }
    }

    private void checkPermission() {
        PermissionManager.requestPermissions(this, null, Manifest.permission.READ_PHONE_STATE,
                Manifest.permission.RECEIVE_BOOT_COMPLETED,
                Manifest.permission.SEND_SMS,
                Manifest.permission.READ_SMS,
                Manifest.permission.RECEIVE_SMS);
    }

}
