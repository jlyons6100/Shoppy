package com.shoppy.shoppy;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class CartActivity extends AppCompatActivity {
    ArrayList<Shopping_Item> database = new ArrayList<Shopping_Item>();
    ArrayList<Shopping_Item> cart= new ArrayList<Shopping_Item>();
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent();
        intent.putExtra("database",database);
        intent.putExtra("cart",cart);
        setResult(RESULT_OK, intent);
        super.onBackPressed();
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_cart);
        database = (ArrayList<Shopping_Item>)getIntent().getSerializableExtra("database");
        cart = (ArrayList<Shopping_Item>)getIntent().getSerializableExtra("cart");
        System.out.println("CART IN cartActivity:" + cart);
        drawCart(cart);
    }

    public void checkOut(View v){
        /*Context context = getApplicationContext();
        CharSequence text = "Purchased Items!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();*/
        Intent intent = new Intent(getApplicationContext(), CheckoutActivity.class);
        intent.putExtra("mEmail", getIntent().getStringExtra("mEmail"));
        intent.putExtra("database", database);
        intent.putExtra("cart", cart);
        startActivityForResult(intent, 0);
    }



    public void drawCart(final ArrayList<Shopping_Item> cart){
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
            layoutParams.setMargins(margin/2,margin,0,0);
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
                text.setWidth((int)convertDpToPixel(150, getApplicationContext()));
                text.setHeight((int)convertDpToPixel(75, getApplicationContext()));
                text.setText(cart.get(i).getName() + "-" + cart.get(i).getDescription() +"\n$"+cart.get(i).getPrice());
                card.addView(text);




                final TextView num = new TextView(getApplicationContext());
                num.setText( " " + cart.get(i).getAmount());

                ImageView minus = new ImageView(getApplicationContext());
                int width_minus = (int)convertDpToPixel(30, getApplicationContext());
                int height_minus =(int) convertDpToPixel(30, getApplicationContext());
                LinearLayout.LayoutParams paramsminus = new LinearLayout.LayoutParams(width_minus, height_minus);
                minus.setLayoutParams(paramsminus);
                minus.setImageResource(R.mipmap.ic_minus_round);
                minus.setId(i);
                minus.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //v.getId() will give you the image id
                        if (cart.get(v.getId()).getAmount() != 0) {
                            cart.get(v.getId()).setAmount(cart.get(v.getId()).getAmount() - 1);
                            num.setText(" " + cart.get(v.getId()).getAmount());
                            TextView price_text = (TextView) findViewById(R.id.cart_price);
                            double total_price = 0;
                            int amount_of_items = 0;
                            for (int i = 0; i < cart.size(); i++){
                                amount_of_items += cart.get(i).getAmount();
                                total_price+= (cart.get(i).getAmount()*cart.get(i).getPrice());
                            }
                            price_text.setText("$"+total_price+" / "+amount_of_items+ " items");
                        }
                        else {
                            Context context = getApplicationContext();
                            CharSequence text = "You can't buy a negative amount of an item!";
                            int duration = Toast.LENGTH_SHORT;
                            Toast toast = Toast.makeText(context, text, duration);
                            toast.show();
                        }
                    }
                });
                card.addView(minus);


                card.addView(num);
                num.setTextSize(20);

                ImageView plus = new ImageView(getApplicationContext());
                LinearLayout.LayoutParams paramsplus = new LinearLayout.LayoutParams( width_minus, height_minus);
                plus.setLayoutParams(paramsplus);
                plus.setImageResource(R.mipmap.ic_plus_round);
                plus.setId(i);
                plus.setOnClickListener(new View.OnClickListener() {
                    public void onClick(View v) {
                        //v.getId() will give you the image id
                        cart.get(v.getId()).setAmount(cart.get(v.getId()).getAmount() +1);
                        num.setText( " " +cart.get(v.getId()).getAmount());
                        TextView price_text = (TextView) findViewById(R.id.cart_price);
                        double total_price = 0;
                        int amount_of_items = 0;
                        for (int i = 0; i < cart.size(); i++){
                            amount_of_items += cart.get(i).getAmount();
                            total_price+= (cart.get(i).getAmount()*cart.get(i).getPrice());
                        }
                        price_text.setText("$"+total_price+" / "+amount_of_items+ " items");
                    }
                });
                card.addView(plus);
                if (card != null)
                linear_scrollview.addView(card);
            }
            TextView price_text = (TextView) findViewById(R.id.cart_price);
            double total_price = 0;
            int amount_of_items = 0;
            for (int i = 0; i < cart.size(); i++){
               amount_of_items += cart.get(i).getAmount();
               total_price+= (cart.get(i).getPrice()*cart.get(i).getAmount());
            }
            price_text.setText("$"+total_price+" / "+amount_of_items + " items");
        }
    }
}
