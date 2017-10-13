package com.day.numen.home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.day.numen.R;
import com.day.numen.common.BaseFragment;
import com.day.numen.databinding.FragmentHomeBinding;
import com.day.numen.settings.SettingsManager;

/**
 * Created by work on 17-9-28.
 */

public class HomeFragment extends BaseFragment {

    private FragmentHomeBinding mHomeBinding;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mHomeBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_home, container, false);
        initView();
        return mHomeBinding.getRoot();
    }

    private void initView() {

        boolean serviceState = SettingsManager.getInstance().isServiceOn();
        mHomeBinding.setChecked(serviceState);
        mHomeBinding.state.setText(serviceState ? "服务已开启" : "服务已停止");
        mHomeBinding.btnSwitch.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean newState = !mHomeBinding.getChecked();
                mHomeBinding.setChecked(newState);
                SettingsManager.getInstance().setServiceState(newState);
                mHomeBinding.state.setText(newState ? "服务已开启" : "服务已停止");
            }
        });
    }
}
