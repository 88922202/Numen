package com.day.numen.contacts;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.MenuItem;

import com.day.numen.R;
import com.day.numen.common.BaseActivity;
import com.day.numen.databinding.ActivityContactListBinding;

/**
 * Created by work on 17-9-29.
 * <p>
 * 联系人列表
 */

public class ContactListActivity extends BaseActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        ActivityContactListBinding binding = DataBindingUtil.setContentView(this, R.layout.activity_contact_list);

        setSupportActionBar(binding.toolbar);

        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        }

        initView();
    }

    private void initView() {
        getSupportFragmentManager().beginTransaction().replace(R.id.content_container, new ContactListFragment()).commit();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home: {
                finish();
                return true;
            }
            default: {
                break;
            }
        }
        return super.onOptionsItemSelected(item);
    }
}
