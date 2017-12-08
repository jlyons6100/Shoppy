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

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);

        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        this.getWindow().setStatusBarColor(Color.parseColor("#f27348"));


        TextView text = (TextView) findViewById(R.id.account_text);
        //text.setText("Account settings:" + getIntent().getStringExtra("mEmail"));
    }
    public void openOrders(View v) {
        Intent intent = new Intent(getApplicationContext(), OrdersActivity.class);
        intent.putExtra("mEmail", getIntent().getStringExtra("mEmail"));
        startActivity(intent);
    }

    public void openAccountSettings(View v) {
//        Context context = getApplicationContext();
//        CharSequence text = "Open Account Settings not implemented!";
//        int duration = Toast.LENGTH_SHORT;
//
//        Toast toast = Toast.makeText(context, text, duration);
//        toast.show();

        Intent intent = new Intent(getApplicationContext(), AccountSettingsActivity.class);
        intent.putExtra("mEmail", getIntent().getStringExtra("mEmail"));
        startActivity(intent);
    }
    public void openLinkWithStores(View v) {
//        Context context = getApplicationContext();
//        CharSequence text = "Open Link with Stores not implemented!";
//        int duration = Toast.LENGTH_SHORT;
//
//        Toast toast = Toast.makeText(context, text, duration);
//        toast.show();

        Intent intent = new Intent(getApplicationContext(), LinkWithStoresActivity.class);
        intent.putExtra("mEmail", getIntent().getStringExtra("mEmail"));
        startActivity(intent);

    }
    public void openManageAddresses(View v) {
//        Context context = getApplicationContext();
//        CharSequence text = "Open Manage Adresses not implemented!";
//        int duration = Toast.LENGTH_SHORT;
//
//        Toast toast = Toast.makeText(context, text, duration);
//        toast.show();

        Intent intent = new Intent(getApplicationContext(), ManageAddressesActivity.class);
        intent.putExtra("mEmail", getIntent().getStringExtra("mEmail"));
        startActivity(intent);

    }
}
