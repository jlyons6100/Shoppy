package com.shoppy.shoppy;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.WindowManager;
import android.widget.TextView;

import java.util.ArrayList;

public class CheckoutActivity extends AppCompatActivity {
    ArrayList<Shopping_Item> database = new ArrayList<Shopping_Item>();
    ArrayList<Shopping_Item> cart= new ArrayList<Shopping_Item>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_checkout);

        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        this.getWindow().setStatusBarColor(Color.parseColor("#f27348"));

        database = (ArrayList<Shopping_Item>)getIntent().getSerializableExtra("database");
        cart = (ArrayList<Shopping_Item>)getIntent().getSerializableExtra("cart");
       double total = 0;
        for (int i = 0; i < cart.size();i++){
          total += (cart.get(i).getPrice()*cart.get(i).getAmount());
        }
    }

    public void placeOrder(View v){
        Intent openMainActivity= new Intent(CheckoutActivity.this, MainActivity.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        openMainActivity.putExtra("mEmail", getIntent().getStringExtra("mEmail"));
        openMainActivity.putExtra("database", database);
        openMainActivity.putExtra("cart", cart);
        openMainActivity.putExtra("placedOrder", "Order Placed!");
        startActivityIfNeeded(openMainActivity, 0);
    }
}
