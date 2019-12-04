package com.xixi.intelligent.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.text.TextUtils;
import android.util.DisplayMetrics;
import android.view.WindowManager;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.security.MessageDigest;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * Created by xixi on 2019/12/4.
 */

public class ToastUtils {
    public static void showMsg(Context context, String msg) {
        if(context!=null){
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }

    public static void showMsg(Context context, int msg) {
        if(context!=null) {
            Toast.makeText(context, context.getString(msg), Toast.LENGTH_SHORT).show();
        }
    }
}
