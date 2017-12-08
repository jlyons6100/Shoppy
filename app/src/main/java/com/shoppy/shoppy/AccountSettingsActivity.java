package com.shoppy.shoppy;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;
import android.widget.Toast;

public class AccountSettingsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_settings);

        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        this.getWindow().setStatusBarColor(Color.parseColor("#f27348"));

        TextView text = (TextView) findViewById(R.id.account_settings_text);
        //text.setText("Account settings:" + getIntent().getStringExtra("mEmail"));
    }
    public void submit(View v){
        Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
        startActivityForResult(intent, 0);
    }
}
