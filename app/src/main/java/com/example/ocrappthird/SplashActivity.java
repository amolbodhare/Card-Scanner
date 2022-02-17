package com.example.ocrappthird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;

import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Session;
import com.example.ocrappthird.commen.P;

public class SplashActivity extends AppCompatActivity {

    Context context;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        //startActivity();
        startNewActivity();
    }

    private void startNewActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent intent;
                intent=new Intent(SplashActivity.this,MainActivity.class);
                startActivity(intent);
                finish();
            }
        }, 1500);
    }


    private void startActivity() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                /* Create an Intent that will start the Menu-Activity. */
                Intent intent;
                Session session=new Session(getApplicationContext());
                String string = session.getString(P.full_name);
                H.log("tokenIs", string);

                if (string == null || string.isEmpty())
                    intent = new Intent(SplashActivity.this, LoginActivity.class);

                else
                    intent = new Intent(SplashActivity.this, MainActivity.class);

                startActivity(intent);
                finish();
            }
        }, 1230);
    }



}
