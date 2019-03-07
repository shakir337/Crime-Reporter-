package com.example.crimereporter1;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        findViewById(R.id.report).setOnClickListener(this);
        findViewById(R.id.tips).setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.report:
                startActivity(new Intent(this,ReportActivity.class));
                break;
            case R.id.tips:
                startActivity(new Intent(this,TipsActivity.class));
                break;
        }
    }
}
