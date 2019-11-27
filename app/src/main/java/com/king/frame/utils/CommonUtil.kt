package com.king.frame.utils

import android.app.Activity
import android.content.Context
import android.os.IBinder
import android.text.TextUtils
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.Toast

/**
 * @author king
 * @date 2017/3/2 14:42
 */

object CommonUtil {

    /**
     * 隐藏软键盘
     * @param activity
     */
    private fun hideSoftKeyboard(activity: Activity) {
        val inputMethodManager = activity.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        try {
            val windowToken = activity.currentFocus!!.windowToken
            if (windowToken != null) {
                inputMethodManager.hideSoftInputFromWindow(windowToken, 0)
            }
        } catch (e: Exception) {

        }

    }

    /**
     * 隐藏软键盘
     * @param context
     * @param editTexts
     */
    fun hideSoftKeyboard(context: Context, vararg editTexts: EditText) {
        val inputMethodManager = context.getSystemService(Activity.INPUT_METHOD_SERVICE) as InputMethodManager
        for (editText in editTexts) {
            inputMethodManager.hideSoftInputFromWindow(editText.windowToken, 0)
            editText.clearFocus()
        }
    }

    /**
     * Toast
     * @param context
     * @param msg
     */
    fun showToast(context: Context, msg: String) {
        if (!TextUtils.isEmpty(msg)) {
            Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
        }
    }
}
