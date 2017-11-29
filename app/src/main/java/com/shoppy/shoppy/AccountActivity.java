package com.shoppy.shoppy;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        TextView text = (TextView) findViewById(R.id.account_text);
        //text.setText("Account settings:" + getIntent().getStringExtra("mEmail"));
    }
}
