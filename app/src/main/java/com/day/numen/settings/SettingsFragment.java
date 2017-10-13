package com.day.numen.settings;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.day.numen.MainActivity;
import com.day.numen.R;
import com.day.numen.common.BaseFragment;

/**
 * Created by wangzhe on 28/9/2017.
 */

public class SettingsFragment extends BaseFragment {

    public static final String FROM = "from";

    private EditText mOldPassword;
    private EditText mNewPassword;
    private EditText mConfirmPassword;
    private RelativeLayout mUploadTypeForm;
    private CheckBox mSmsBox;
    private CheckBox mEmailBox;
    private Button mOkButton;

    private boolean mHavePassword;
    private boolean mFromDrawer;            //是否从drawer进入

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        Bundle args = getArguments();
        if (args != null) {
            mFromDrawer = getArguments().getBoolean(FROM);
        }

        View view = inflater.inflate(R.layout.fragment_safe, container, false);
        mOldPassword = view.findViewById(R.id.old_password);
        mNewPassword = view.findViewById(R.id.new_password);
        mConfirmPassword = view.findViewById(R.id.confirm_password);
        mUploadTypeForm = view.findViewById(R.id.form_upload_type);
        mUploadTypeForm.setVisibility(mFromDrawer ? View.VISIBLE : View.GONE);
        mSmsBox = view.findViewById(R.id.cb_sms);
        mSmsBox.setChecked(SettingsManager.getInstance().getUploadType("sms"));
        mEmailBox = view.findViewById(R.id.cb_email);
        mEmailBox.setChecked(SettingsManager.getInstance().getUploadType("email"));
        mOkButton = view.findViewById(R.id.ok);
        mHavePassword = SettingsManager.getInstance().havePassword();
        mOldPassword.setVisibility(mHavePassword ? View.VISIBLE : View.GONE);
        mOkButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                saveSettings();
            }
        });

        return view;
    }


    private void saveSettings() {
        String old = mOldPassword.getText().toString();
        if (mHavePassword && TextUtils.isEmpty(old)) {
            mOldPassword.setError(getString(R.string.error_invalid_password));
            return;
        }

        if (mHavePassword && !SettingsManager.getInstance().isPasswordRight(old)) {
            mOldPassword.setError(getString(R.string.error_incorrect_password));
            return;
        }

        String once = mNewPassword.getText().toString();
        String twice = mConfirmPassword.getText().toString();
        if (TextUtils.isEmpty(once)) {
            once = old;
        }
        if (TextUtils.isEmpty(twice)) {
            twice = old;
        }
        if (once.equals(twice)) {
            SettingsManager.getInstance().saveOrUpdatePassword(once);
            Toast.makeText(getActivity(), R.string.save_settings_success, Toast.LENGTH_SHORT).show();
            if (!mFromDrawer) {
                mConfirmPassword.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        Intent intent = new Intent(getActivity(), MainActivity.class);
                        startActivity(intent);
                        getActivity().finish();
                    }
                }, 300);

            }else {
                SettingsManager.getInstance().setUploadType("sms", mSmsBox.isChecked());
                SettingsManager.getInstance().setUploadType("email", mEmailBox.isChecked());
            }
        } else {
            mConfirmPassword.setError(getString(R.string.different_password));
        }

    }
}
