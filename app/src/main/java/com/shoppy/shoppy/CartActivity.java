package com.shoppy.shoppy;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.inputmethod.InputMethodManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
        //System.out.println("CART IN cartActivity:" + cart.get(0).getDescription());
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



    public void drawCart(final ArrayList<Shopping_Item> cart)
    {
        if (cart.size() == 0){
            LinearLayout linear_scrollview_horizontal = findViewById(R.id.cartpage_linear_scrollview);
            linear_scrollview_horizontal.setVisibility(View.VISIBLE);
        }
        else {
            LinearLayout linear_scrollview_horizontal = findViewById(R.id.cartpage_linear_scrollview);
            linear_scrollview_horizontal.setVisibility(View.GONE);

            LinearLayout linear_scrollview = (LinearLayout) findViewById(R.id.cartpage_linear_scrollview);

            LinearLayout one_item_temp = findViewById(R.id.one_item);
            for (int i = 0 ; i < cart.size(); i++) {
                LinearLayout card = new LinearLayout(getApplicationContext());
                card.setLayoutParams(one_item_temp.getLayoutParams());
                card.setOrientation(LinearLayout.HORIZONTAL);
                card.setGravity(Gravity.BOTTOM);

                // Item image
                ImageView item_img = new ImageView(getApplicationContext());
                ImageView item_img_temp = findViewById(R.id.item_image);
                item_img.setLayoutParams(item_img_temp.getLayoutParams());
                Context c = getApplicationContext();
                int id = c.getResources().getIdentifier("drawable/" + cart.get(i).getImage(), null, c.getPackageName());
                item_img.setImageResource(id);
                card.addView(item_img);

                // Item text
                RelativeLayout item_text_box = new RelativeLayout(getApplicationContext());
                RelativeLayout item_text_box_temp = findViewById(R.id.item_text_box);
                item_text_box.setLayoutParams(item_text_box_temp.getLayoutParams());
                item_text_box.setBackgroundResource(R.drawable.rounded_corner);

                // Item text -- name
                TextView item_name = new TextView(getApplicationContext());
                item_name.setTextAppearance(R.style.item_name);
                item_name.setText(cart.get(i).getName());
                item_text_box.addView(item_name);

                // Item text -- detail
                TextView item_detail = new TextView(getApplicationContext());
                TextView detail_temp = findViewById(R.id.item_detail);
                item_detail.setLayoutParams(detail_temp.getLayoutParams());
                item_detail.setTextAppearance(R.style.item_detail);
                item_detail.setText(cart.get(i).getDescription());
                item_text_box.addView(item_detail);

                // Item text -- price and add
                RelativeLayout cart_box = new RelativeLayout(getApplicationContext()); //add to item text box
                RelativeLayout cart_box_temp = findViewById(R.id.cart_box);
                cart_box.setLayoutParams(cart_box_temp.getLayoutParams());
                cart_box.setBackgroundResource(R.drawable.rounded_corner);

                TextView carts_text1 = new TextView(getApplicationContext());
                TextView dollar_temp = findViewById(R.id.cart_dollar);
                carts_text1.setLayoutParams(dollar_temp.getLayoutParams());
                carts_text1.setTextAppearance(R.style.item_dollar);
                carts_text1.setText("$");
                TextView carts_text2 = new TextView(getApplicationContext());
                TextView price_temp = findViewById(R.id.cart_price);
                carts_text2.setLayoutParams(price_temp.getLayoutParams());
                carts_text2.setTextAppearance(R.style.item_price);
                carts_text2.setText(""+cart.get(i).getPrice());
                TextView carts_text3 = new TextView(getApplicationContext());
                TextView wet_temp = findViewById(R.id.cart_wet);
                carts_text3.setLayoutParams(wet_temp.getLayoutParams());
                carts_text3.setTextAppearance(R.style.item_wet);
                carts_text3.setText(" / 1L");
                cart_box.addView(carts_text1);
                cart_box.addView(carts_text2);
                cart_box.addView(carts_text3);

                RelativeLayout buttonAdd = new RelativeLayout(getApplicationContext());
                RelativeLayout buttonAdd_temp = findViewById(R.id.button_add);
                buttonAdd.setClickable(true);

                buttonAdd.setLayoutParams(buttonAdd_temp.getLayoutParams());
                buttonAdd.setPadding(buttonAdd_temp.getPaddingLeft(), buttonAdd_temp.getPaddingTop(), buttonAdd_temp.getPaddingRight(), buttonAdd_temp.getPaddingBottom());
                buttonAdd.setBackgroundResource(R.drawable.add_button);
                buttonAdd.setId(i);

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
