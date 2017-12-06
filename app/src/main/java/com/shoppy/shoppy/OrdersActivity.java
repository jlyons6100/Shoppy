package com.shoppy.shoppy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class OrdersActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_orders);

        TextView text = (TextView) findViewById(R.id.orders_text);
        //text.setText("Account settings:" + getIntent().getStringExtra("mEmail"));
    }
}
