package com.example.crimereporter1;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.Toast;

import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity {
    private static int SPLASH_TIME_OUT = 3000;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        final ProgressDialog progressDialog=new ProgressDialog(MainActivity.this);
        progressDialog.setTitle("Please Wait");
        progressDialog.setMessage("Loading App");
        progressDialog.setCancelable(false);
        //progressDialog.show();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                SharedPreferences sp=getSharedPreferences(
                        "login",MODE_PRIVATE);
                String s1=sp.getString("email",null);
                String s2=sp.getString("password",null);
                String s3=sp.getString("type",null);
                progressDialog.show();
                if(s1==null&& s2==null) {

                    Intent i = new Intent(MainActivity.this, PreLoginActivity.class);
                    startActivity(i);

                    progressDialog.dismiss();
                    finish();
                }else if(s3.equals("police")){
                    //Toast.makeText(MainActivity.this, "Police panel coming soon....", Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(MainActivity.this, PolicePanelActivity.class);
                    startActivity(i);

                   progressDialog.dismiss();
                    finish();
                }else{
                    Intent i = new Intent(MainActivity.this, HomeActivity.class);
                    startActivity(i);

                    progressDialog.dismiss();
                    finish();
                }

            }
        },SPLASH_TIME_OUT);


    }
}
