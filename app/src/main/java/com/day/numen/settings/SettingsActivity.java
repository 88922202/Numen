package com.day.numen.settings;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.day.numen.R;
import com.day.numen.common.BaseActivity;

public class SettingsActivity extends BaseActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_safe);

        FragmentManager fragmentManager = getSupportFragmentManager();
        Fragment settingsFragment = new SettingsFragment();
        fragmentManager.beginTransaction().replace(R.id.container, settingsFragment).commit();
    }
}
