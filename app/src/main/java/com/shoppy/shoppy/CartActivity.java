package com.shoppy.shoppy;

import android.content.Context;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {

    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        ArrayList<Shopping_Item> database = new ArrayList<Shopping_Item>();
        database = (ArrayList<Shopping_Item>)getIntent().getSerializableExtra("database");

        ArrayList<Shopping_Item> cart = new ArrayList<Shopping_Item>();
        cart = (ArrayList<Shopping_Item>)getIntent().getSerializableExtra("cart"); //TODO: CHANGE THIS BACK, just testing full cart

        drawCart(cart);
    }

    public void drawCart(ArrayList<Shopping_Item> cart){
        if (cart.size() == 0){
            LinearLayout linear_scrollview_horizontal = (LinearLayout) findViewById(R.id.linear_scrollview_horizontal);
            linear_scrollview_horizontal.setVisibility(View.VISIBLE);
        }
        else {
            LinearLayout linear_scrollview_horizontal = (LinearLayout) findViewById(R.id.linear_scrollview_horizontal);
            linear_scrollview_horizontal.setVisibility(View.GONE);

            LinearLayout linear_scrollview = (LinearLayout) findViewById(R.id.linear_scrollview);


            for (int i = 0 ; i < cart.size(); i++) {
            LinearLayout card = new LinearLayout(getApplicationContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            int margin = (int)convertDpToPixel( 30, getApplicationContext());
            layoutParams.setMargins(0,margin,0,0);
            card.setLayoutParams(layoutParams);
            card.setOrientation(LinearLayout.HORIZONTAL);


                ImageView image = new ImageView(getApplicationContext());
                int width = (int)convertDpToPixel(50, getApplicationContext());
                int height =(int) convertDpToPixel(50, getApplicationContext());
                LinearLayout.LayoutParams parmsImage = new LinearLayout.LayoutParams(width,height);
                image.setLayoutParams(parmsImage);

                Context c = getApplicationContext();
                int id = c.getResources().getIdentifier("drawable/" + cart.get(i).getImage(), null, c.getPackageName());
                image.setImageResource(id);
                card.addView(image);

                TextView text = new TextView(getApplicationContext());
                //int width = (int)convertPixelsToDp(500, getApplicationContext());
                //int height =(int) convertPixelsToDp(500, getApplicationContext());
                LinearLayout.LayoutParams parmsText = new LinearLayout.LayoutParams(width*4,height*2);
                parmsText.setMargins(margin,0,0,0);
                text.setLayoutParams(parmsText);
                text.setWidth((int)convertDpToPixel(175, getApplicationContext()));
                text.setHeight((int)convertDpToPixel(75, getApplicationContext()));
                text.setText(cart.get(i).getName() + "-" + cart.get(i).getDescription() +"\n$"+cart.get(i).getPrice());
                card.addView(text);

                linear_scrollview.addView(card);
            }

        }
    }
}
