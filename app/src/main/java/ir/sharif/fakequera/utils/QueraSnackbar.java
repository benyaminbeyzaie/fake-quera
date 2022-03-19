package ir.sharif.fakequera.utils;

import android.view.Gravity;
import android.view.View;
import android.widget.FrameLayout;

import com.google.android.material.snackbar.Snackbar;

public class QueraSnackbar {
    public static void showTopSnackBar(View context, String message) {
        Snackbar snack = Snackbar.make(context, message, Snackbar.LENGTH_LONG);
        snack.show();
    }
}
