package com.example.crimereporter1;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

public class PreLoginActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pre_login);
        findViewById(R.id.btn_user).setOnClickListener(this);
        findViewById(R.id.btn_police).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_user:

                Intent i = new Intent(PreLoginActivity.this, LoginActivity.class);
                startActivity(i);
                //finish();
                break;
            case R.id.btn_police:
                Intent intent = new Intent(PreLoginActivity.this, PoliceLoginActivity.class);
                startActivity(intent);
                //Toast.makeText(getApplicationContext(),"Police Login coming soon...",Toast.LENGTH_SHORT).show();
                break;
        }
    }
}