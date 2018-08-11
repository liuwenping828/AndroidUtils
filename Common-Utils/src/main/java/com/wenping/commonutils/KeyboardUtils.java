package com.wenping.commonutils;

import android.content.Context;
import android.support.annotation.NonNull;
import android.view.View;
import android.view.inputmethod.InputMethodManager;

/**
 * 软键盘的显示和隐藏
 */
public class KeyboardUtils {

    private KeyboardUtils() {
    }

    public static void showKeyboard(@NonNull View view) {
        view.requestFocus();
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext()
                .getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.showSoftInput(view, 0);
        }
    }

    public static void hideKeyboard(@NonNull View view) {
        InputMethodManager inputMethodManager = (InputMethodManager) view.getContext()
                .getApplicationContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (inputMethodManager != null) {
            inputMethodManager.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }
}
