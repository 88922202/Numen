package com.day.numen.common;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.day.numen.BuildConfig;


/**
 * <font color='#9B77B2'>该类的主要用途:</font><br/><font color='#36FC2C'><b>
 * <p></p>
 * <b/></font><br/><hr/>
 * <b><font color='#05B8FD'>作者: C&C</font></b><br/><br/>
 * <b><font color='#05B8FD'>创建时间：2017/1/21</font></b><br/><br/>
 * <b><font color='#05B8FD'>联系方式：862530304@qq.com</font></b>
 */

public class LogUtils {
    private static final String TAG = "numen";

    private static boolean isOn = true;
    private static int mLevel = Log.VERBOSE;

    public static void v(String message){
        if (isOn && mLevel <= Log.VERBOSE && message != null) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.v(TAG, getLogInfo(stackTraceElement) + ":" + message);
        }
    }

    public static void v(String tag, String message){
        if (isOn && mLevel <= Log.VERBOSE && message != null){
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.v(tag, getLogInfo(stackTraceElement) + ":" + message);
        }
    }

    public static void d(String message){
        if (isOn && mLevel <= Log.DEBUG && message != null) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.d(TAG, getLogInfo(stackTraceElement) + ":" + message);
        }
    }

    public static void d(String tag, String message){
        if (isOn && mLevel <= Log.DEBUG && message != null){
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.d(tag, getLogInfo(stackTraceElement) + ":" + message);
        }
    }

    public static void i(String message){
        if (isOn && mLevel <= Log.INFO && message != null) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.i(TAG, getLogInfo(stackTraceElement) + ":" + message);
        }
    }

    public static void i(String tag, String message){
        if (isOn && mLevel <= Log.INFO && message != null){
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.i(tag, getLogInfo(stackTraceElement) + ":" + message);
        }
    }

    public static void w(String message){
        if (isOn && mLevel <= Log.WARN && message != null) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.w(TAG, getLogInfo(stackTraceElement) + ":" + message);
        }
    }

    public static void w(String tag, String message){
        if (isOn && mLevel <= Log.WARN && message != null){
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.w(tag, getLogInfo(stackTraceElement) + ":" + message);
        }
    }

    public static void e(String message){
        if (isOn && mLevel <= Log.ERROR && message != null) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.e(TAG, getLogInfo(stackTraceElement) + ":" + message);
            //Bugtags.sendException(new Exception(message));
        }
    }

    public static void e(String tag, String message){
        if (isOn && mLevel <= Log.ERROR && message != null){
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.e(tag, getLogInfo(stackTraceElement) + ":" + message);
            //Bugtags.sendException(new Exception(message));
        }
    }

    public static void t(Context context, String message){
        if (isOn && mLevel <= Log.DEBUG && message != null) {
            StackTraceElement stackTraceElement = Thread.currentThread().getStackTrace()[3];
            Log.d(TAG, getLogInfo(stackTraceElement) + ":" + message);
            if (BuildConfig.DEBUG) {
                Toast.makeText(context, message, Toast.LENGTH_SHORT).show();
            }
        }
    }

    /**
     * 输出日志所包含的信息
     */
    private static String getLogInfo(StackTraceElement stackTraceElement) {
        StringBuilder logInfoStringBuilder = new StringBuilder();
        // 获取类名.
        String className = stackTraceElement.getClassName();
        int last = className.lastIndexOf(".");
        className = className.substring(last + 1, className.length());
        // 获取方法名称
        String methodName = stackTraceElement.getMethodName();
        // 获取输出行数
        int lineNumber = stackTraceElement.getLineNumber();

        logInfoStringBuilder.append("[");
        logInfoStringBuilder.append(className);
        logInfoStringBuilder.append(".");
        logInfoStringBuilder.append(methodName);
        logInfoStringBuilder.append("(");
        logInfoStringBuilder.append(lineNumber);
        logInfoStringBuilder.append(")");
        logInfoStringBuilder.append("] ");

        return logInfoStringBuilder.toString();
    }
}
