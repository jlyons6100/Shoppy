package com.shoppy.shoppy;

import android.content.Context;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        TextView text = (TextView) findViewById(R.id.account_text);
        //text.setText("Account settings:" + getIntent().getStringExtra("mEmail"));
    }
    public void openAccountSettings(View v) {
        Context context = getApplicationContext();
        CharSequence text = "Open Account Settings not implemented!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    public void openLinkWithStores(View v) {
        Context context = getApplicationContext();
        CharSequence text = "Open Link with Stores not implemented!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
    public void openManageAddresses(View v) {
        Context context = getApplicationContext();
        CharSequence text = "Open Manage Adresses not implemented!";
        int duration = Toast.LENGTH_SHORT;

        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
    }
}
