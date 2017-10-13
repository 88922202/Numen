package com.day.numen.common;

import android.content.Context;
import android.text.TextUtils;

import com.meituan.android.walle.WalleChannelReader;

/**
 * Created by wangzhe on 28/9/2017.
 */

public class PhoneUtils {
    public static final String DEFAULT_CHANNEL_NAME="qihoo";

    public static String getChannelName(Context context) {
        //使用美团的walle打包工具进行多渠道打包
        String channel = WalleChannelReader.getChannel(context.getApplicationContext());
        if (TextUtils.isEmpty(channel))
        {
            channel = DEFAULT_CHANNEL_NAME;
        }
        return channel;

    }
}
