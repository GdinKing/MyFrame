package com.king.frame.utils;

import android.app.Activity;
import android.content.Context;
import android.os.IBinder;
import android.text.TextUtils;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.Toast;

/**
 * @author king
 * @date 2017/3/2 14:42
 */

public class CommonUtil {

    /**
     * 隐藏软键盘
     * @param activity
     */
    private static void hideSoftKeyboard(Activity activity) {
        InputMethodManager inputMethodManager = (InputMethodManager) activity.getSystemService(Activity.INPUT_METHOD_SERVICE);
        try {
            IBinder windowToken = activity.getCurrentFocus().getWindowToken();
            if (windowToken != null) {
                inputMethodManager.hideSoftInputFromWindow(windowToken, 0);
            }
        } catch (Exception e) {

        }
    }
    /**
     * 隐藏软键盘
     * @param context
     * @param editTexts
     */
    public static void hideSoftKeyboard(Context context, EditText... editTexts) {
        InputMethodManager inputMethodManager = (InputMethodManager) context.getSystemService(Activity.INPUT_METHOD_SERVICE);
        for (EditText editText:editTexts){
            inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(),0);
            editText.clearFocus();
        }
    }

    /**
     * Toast
     * @param context
     * @param msg
     */
    public static void showToast(Context context,String msg){
        if(!TextUtils.isEmpty(msg)) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show();
        }
    }
}
