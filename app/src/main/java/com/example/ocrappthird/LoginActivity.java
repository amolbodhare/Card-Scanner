package com.example.ocrappthird;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.adoisstudio.helper.Api;
import com.adoisstudio.helper.H;
import com.adoisstudio.helper.Json;
import com.adoisstudio.helper.LoadingDialog;
import com.adoisstudio.helper.Session;
import com.example.ocrappthird.commen.P;

public class LoginActivity extends AppCompatActivity implements  Api.OnLoadingListener,Api.OnErrorListener
{

    Button loginButton;
    private LoadingDialog loadingDialog;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        loginButton=(Button)findViewById(R.id.loginBtn);

        loadingDialog = new LoadingDialog(this);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                makeRegisterJson();
            }
        });

    }


    private void makeRegisterJson()
    {

        final Json json = new Json();

        String string = ((EditText) findViewById(R.id.edUname)).getText().toString();

        if (string.isEmpty()) {
            H.showMessage(this, "Please enter username");
            findViewById(R.id.edUname).requestFocus();
            return;
        }

        json.addString(P.usr, string);


        string=((EditText) findViewById(R.id.edPass)).getText().toString();

        if (string.isEmpty()) {
            H.showMessage(this, "Please enter password");
            findViewById(R.id.edPass).requestFocus();
            return;
        }
        json.addString(P.pwd, string);


        Api.newApi(this, P.baseUrl).addJson(json)
                //Api.newApi(this, P.createUrl).addJson(json)
                .setMethod(Api.POST)
                .onLoading(this)
                .onError(this)
                .onSuccess(new Api.OnSuccessListener() {
                    @Override
                    public void onSuccess(Json json)
                    {

                        String string =json.getString(P.full_name);
                        Session session=new Session(getApplicationContext());
                                session.addString(P.full_name,string);
                                startActivity(new Intent(LoginActivity.this,MainActivity.class));
                                finish();
                    }
                })
                .run("signup");
    }

    @Override
    public void onLoading(boolean isLoading) {
        if (isLoading)
            loadingDialog.show();
        else
            loadingDialog.cancel();
    }

    @Override
    public void onError() {
        Toast.makeText(LoginActivity.this, "Login Failed", Toast.LENGTH_SHORT).show();
    }
}
