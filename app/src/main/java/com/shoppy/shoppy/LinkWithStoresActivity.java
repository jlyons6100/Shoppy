package com.shoppy.shoppy;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class LinkWithStoresActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_link_with_stores);

        TextView text = (TextView) findViewById(R.id.link_with_stores_text);
        //text.setText("Account settings:" + getIntent().getStringExtra("mEmail"));
    }

    public void submit(View v){
        Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
        startActivityForResult(intent, 0);
    }
}
