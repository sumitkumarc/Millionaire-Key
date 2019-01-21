package com.maker.millionairekey.Model;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Extra {

    public static final int NUM_OF_COLUMNS = 2;
    public static final List<String> FILE_EXTN_VIDEO;
    static {
        String[] strArr = new String[NUM_OF_COLUMNS];
        strArr[0] = "mp4";
        strArr[1] = "3gp";
        FILE_EXTN_VIDEO = Arrays.asList(strArr);
    }
    public static List<String> extractUrls(String text) {
        List<String> containedUrls = new ArrayList();
        Matcher urlMatcher = Pattern.compile("((https?|ftp|gopher|telnet|file):((//)|(\\\\))+[\\w\\d:#@%/;$()~_?\\+-=\\\\\\.&]*)", Pattern.CASE_INSENSITIVE ).matcher(text);
        while (urlMatcher.find()) {
            containedUrls.add(text.substring(urlMatcher.start(0), urlMatcher.end(0)));
        }
        return containedUrls;
    }

    public static void showToast(Activity context, String message, int status) {
        if (context != null) {
            Toast.makeText(context,message,Toast.LENGTH_SHORT).show();
        }
    }
    public static boolean isNetwork(Context context) {

        ConnectivityManager cm = (ConnectivityManager) context
                .getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();
        if (netInfo != null && netInfo.isConnectedOrConnecting()) {
            return true;
        }
        return false;
    }
}