package com.day.numen.common;

import android.support.annotation.NonNull;
import android.support.v4.app.Fragment;

/**
 * Created by chenning on 17-9-28.
 */

public class BaseFragment extends Fragment {

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        PermissionManager.onRequestPermissionsResult(this, requestCode, permissions, grantResults);
    }
}
