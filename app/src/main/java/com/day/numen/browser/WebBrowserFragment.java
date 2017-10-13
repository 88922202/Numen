package com.day.numen.browser;

import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebSettings;

import com.day.numen.R;
import com.day.numen.common.BaseFragment;
import com.day.numen.databinding.FragmentWebBrowserBinding;

/**
 * Created by work on 17-9-30.
 */

public class WebBrowserFragment extends BaseFragment {

    private FragmentWebBrowserBinding mWebBrowserBinding;

    private String mBaseUrl;
    private String mBaseTitle;
    private boolean showToolbar = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        mWebBrowserBinding = DataBindingUtil.inflate(inflater, R.layout.fragment_web_browser, container, false);
        initView();
        return mWebBrowserBinding.getRoot();
    }

    private void initView() {
        WebSettings settings = mWebBrowserBinding.webView.getSettings();
        settings.setJavaScriptEnabled(true);
        mWebBrowserBinding.webView.removeJavascriptInterface("searchBoxJavaBridge_");

        // init settings
        settings.setAllowFileAccess(true);
        settings.setAllowContentAccess(true);
        settings.setDatabaseEnabled(true);
        settings.setDomStorageEnabled(true);
        settings.setAppCacheEnabled(true);
        settings.setSavePassword(false);
        settings.setSaveFormData(false);
        settings.setUseWideViewPort(true);
        settings.setLoadWithOverviewMode(true);
        settings.setDefaultTextEncodingName("UTF-8");
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.JELLY_BEAN) {
            settings.setAllowUniversalAccessFromFileURLs(true);
        }
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            settings.setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }

        initData();
    }

    private void initData() {
        Bundle bundle = getArguments();
        if (bundle != null) {
            mBaseUrl = bundle.getString("key.base.url", "");
            mBaseTitle = bundle.getString("key.base.title", "");
            showToolbar = bundle.getBoolean("key.base.toolbar", false);
        }
        if (TextUtils.isEmpty(mBaseUrl)) {
            getActivity().finish();
            return;
        }
        mWebBrowserBinding.toolbar.setVisibility(showToolbar ? View.VISIBLE : View.GONE);

        mWebBrowserBinding.webView.loadUrl(mBaseUrl);
    }
}
