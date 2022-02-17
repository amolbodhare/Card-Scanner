package com.adoisstudio.helper;

import android.app.AlertDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.os.Build;
import android.text.Html;
import android.text.Spanned;
import android.text.TextUtils;
import android.util.Base64;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;

/**
 * Created by amitkumar on 05/01/18.
 */

public class H {

    public static void showOkDialog(Context context, String title, String msg) {

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.cancel();
            }
        });

        alertDialog.show();

    }//show ok dialog

    public static void showOkDialog(Context context, String title, String msg, final OnOkListener listener) {

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.cancel();
                listener.onOk();
            }
        });

        alertDialog.show();

    }//show ok dialog


    public static void showYesNoDialog(Context context, String title, String msg, final OnYesNoListener listener) {

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, "Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.cancel();
                listener.onDecision(true);
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, "No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.cancel();
                listener.onDecision(false);
            }
        });

        alertDialog.show();

    }//show yes no dialog

    public static void showYesNoDialog(Context context, String title, String msg,
                                       String yesText, String noText, final OnYesNoListener listener) {

        final AlertDialog alertDialog = new AlertDialog.Builder(context).create();
        alertDialog.setTitle(title);
        alertDialog.setMessage(msg);
        alertDialog.setCancelable(false);

        alertDialog.setButton(AlertDialog.BUTTON_POSITIVE, yesText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.cancel();
                listener.onDecision(true);
            }
        });
        alertDialog.setButton(AlertDialog.BUTTON_NEGATIVE, noText, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                alertDialog.cancel();
                listener.onDecision(false);
            }
        });

        alertDialog.show();

    }//show yes no dialog

    public interface OnOkListener {
        void onOk();
    }

    public interface OnYesNoListener {
        void onDecision(boolean isYes);
    }

    public static Spanned convertHtml(String text) {

        if (text == null) text = "";

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N)
            return Html.fromHtml(text, Html.FROM_HTML_MODE_COMPACT);
        else
            return Html.fromHtml(text);
    }

    //Documents on http://www.adoisstudio.com/android/helper/log
    public static void log(String tag, String msg) {
        try {
            if (!Static.show_log)
                return;

            StackTraceElement[] stackTraceElement = Thread.currentThread()
                    .getStackTrace();
            int currentIndex = -1;
            for (int i = 0; i < stackTraceElement.length; i++) {
                if (stackTraceElement[i].getMethodName().compareTo("log") == 0) {
                    currentIndex = i + 1;
                    break;
                }
            }

            String fullClassName = stackTraceElement[currentIndex].getClassName();
            String className = fullClassName.substring(fullClassName
                    .lastIndexOf(".") + 1);
            String methodName = stackTraceElement[currentIndex].getMethodName();
            String lineNumber = String
                    .valueOf(stackTraceElement[currentIndex].getLineNumber());

            Log.e(tag, msg + " : " + className + "." + methodName + "(" + className + ".java:" + lineNumber + ")");

        } catch (Exception e) {
            e.printStackTrace();
        }
        //Log.e(tag, msg);
    }//log

    public static double getDouble(String val) {

        try {
            return Double.parseDouble(val);
        } catch (Exception ex) {
            return 0;
        }
    }

    public static float convertDpToPixel(float dp, Context context) {
        return dp * ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static float convertPixelsToDp(float px, Context context) {
        return px / ((float) context.getResources().getDisplayMetrics().densityDpi / DisplayMetrics.DENSITY_DEFAULT);
    }

    public static void showMessage(Context context, String msg) {
        Toast toast = Toast.makeText(context, msg, Toast.LENGTH_SHORT);
       /* LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT,LinearLayout.LayoutParams.WRAP_CONTENT);
        TextView textView = new TextView(context);
        textView.setText(msg);
        textView.setPadding(12,7,12,7);
        textView.setTextColor(Color.parseColor("#ffffff"));
        textView.setBackgroundColor(Color.parseColor("#000000"));
        textView.setLayoutParams(layoutParams);
        toast.setView(textView);*/
        toast.setGravity(Gravity.TOP | Gravity.CENTER_HORIZONTAL, 0, 200);
        toast.show();
    }

    public static void hideKeyBoard(Context context, View view) {
        InputMethodManager imm = (InputMethodManager) context.getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null)
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
    }

    public static int extractInt(String string) {
        int i = 0;
        try {
            string = string.trim();
            i = Integer.parseInt(string);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return i;
    }

    public static String getStringImage(Bitmap bitmap) {
        try {
            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 50, baos);
            byte[] imageBytes = baos.toByteArray();
            //String encodedImage =
            return Base64.encodeToString(imageBytes, Base64.DEFAULT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static void clearAllEditText(ViewGroup viewGroup) {
        for (int i = 0; i < viewGroup.getChildCount(); i++) {
            View view = viewGroup.getChildAt(i);
            if (view instanceof ViewGroup)
                clearAllEditText((ViewGroup) view);
            else if (view instanceof EditText)
                ((EditText) view).setText("");
        }
    }

    public static boolean isValidBarCode(String string) {
        char ch = '`';
        boolean bool = false;
        for (int i = 0; i < string.length(); i++)
        {
            ch = string.charAt(i);
            int c = (int)ch;
            //H.log("charIs",c+"");

            if (c>47 && c<58)
                bool = true;
            else if (c>64 && c<91)
                bool = true;
            else if (c>96 && c<123)
                bool = true;
            else if (c== 64 || c==32 || c==46 || c==95 || c==44)//64=@, 32= , 46=., 95=_, 44=,
                bool = true;
            else
                bool = false;

            if (!bool)
                break;
        }

        return bool;
    }
}
