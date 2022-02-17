package com.example.ocrappthird.commen;

import android.app.Application;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.provider.Settings;
import android.view.MenuItem;
import android.view.View;

import androidx.appcompat.widget.PopupMenu;


public class App extends Application
{
    public  static  String device_id="";
    public static boolean IS_DEV = false;
    public static boolean IS_NOT_FIRST_TIME_LOGIN = false;

    @Override
    public void onCreate() {
        super.onCreate();
        device_id= Settings.Secure.getString(this.getContentResolver(),Settings.Secure.ANDROID_ID);
    }

    public static boolean isInternetAvailable(Context context) {
        boolean connected = false;

        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (activeNetwork != null) {
            // connected to the internet
            switch (activeNetwork.getType()) {
                case ConnectivityManager.TYPE_WIFI:
                    // connected to wifi
                    connected = true;
                    break;

                case ConnectivityManager.TYPE_MOBILE:
                    // connected to mobile data
                    connected = true;
                    break;

                default:
                    connected = false;
            }
        } else {
            // not connected to the internet
            connected = false;
        }

        return connected;
    }
   /* public static  void showPopUpMenu(Context context, final View btn_view, final PopUpMenuInterface popUpMenuInterface) {
        PopupMenu popup = new PopupMenu(context , btn_view);
        popup.getMenuInflater().inflate(R.menu.options_menu, popup.getMenu());

        popup.setOnMenuItemClickListener(new PopupMenu.OnMenuItemClickListener() {
            public boolean onMenuItemClick(MenuItem item) {

                if (item.getTitle().equals("Sync Now"))
                {

                    popUpMenuInterface.callBack();
                    //startJobScheduler();
                } else {
                    //cancelJob(btn_view);
                }

                return true;
            }
        });

        popup.show();//showing popup menu
    }

    public interface PopUpMenuInterface
    {
        void callBack();
    }*/
}
