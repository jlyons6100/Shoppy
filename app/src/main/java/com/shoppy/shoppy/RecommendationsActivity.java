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
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class RecommendationsActivity extends AppCompatActivity {
    ArrayList<Shopping_Item> database = new ArrayList<Shopping_Item>();
    ArrayList<Shopping_Item> cart = new ArrayList<Shopping_Item>();
    ArrayList<Shopping_Item> remind = new ArrayList<Shopping_Item>();
    ArrayList<Shopping_Item> recommended = new ArrayList<Shopping_Item>();
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
        setContentView(R.layout.activity_recommendations);
        database = (ArrayList<Shopping_Item>)getIntent().getSerializableExtra("database");
        cart = (ArrayList<Shopping_Item>)getIntent().getSerializableExtra("cart");
        recommended = (ArrayList<Shopping_Item>)getIntent().getSerializableExtra("recommended");
        drawCart(recommended);
    }


    public void drawCart(final ArrayList<Shopping_Item> cart)
    {
        LinearLayout linear_scrollview_horizontal = findViewById(R.id.cartpage_linear_scrollview);

        LinearLayout one_item_temp = findViewById(R.id.one_item);
        ImageView item_img_temp = findViewById(R.id.item_image);
        RelativeLayout item_text_box_temp = findViewById(R.id.item_text_box);
        TextView name_temp = findViewById(R.id.item_name);
        TextView detail_temp = findViewById(R.id.item_detail);
        RelativeLayout cart_box_temp = findViewById(R.id.cart_box);

        TextView dollar_temp = findViewById(R.id.cart_dollar);
        TextView price_temp = findViewById(R.id.cart_price);
        TextView wet_temp = findViewById(R.id.cart_wet);

        for (int i = 0 ; i < cart.size(); i++) {
            LinearLayout card = new LinearLayout(getApplicationContext());
            card.setLayoutParams(one_item_temp.getLayoutParams());
            card.setOrientation(LinearLayout.HORIZONTAL);
            card.setGravity(Gravity.BOTTOM);

            // Item image
            ImageView item_img = new ImageView(getApplicationContext());
            item_img.setLayoutParams(item_img_temp.getLayoutParams());
            Context c = getApplicationContext();
            int id = c.getResources().getIdentifier("drawable/" + cart.get(i).getImage(), null, c.getPackageName());
            item_img.setImageResource(id);
            card.addView(item_img);

            // Item text
            RelativeLayout item_text_box = new RelativeLayout(getApplicationContext());
            item_text_box.setLayoutParams(item_text_box_temp.getLayoutParams());
            item_text_box.setBackgroundResource(R.drawable.rounded_corner);

            // Item text -- name
            TextView item_name = new TextView(getApplicationContext());
            item_name.setLayoutParams(name_temp.getLayoutParams());
            item_name.setTextAppearance(R.style.item_name);
            item_name.setText(cart.get(i).getName());
            item_text_box.addView(item_name);

            // Item text -- detail
            TextView item_detail = new TextView(getApplicationContext());
            item_detail.setLayoutParams(detail_temp.getLayoutParams());
            item_detail.setTextAppearance(R.style.item_detail);
            item_detail.setText(cart.get(i).getDescription());
            item_text_box.addView(item_detail);

            // Item text -- price and amount
            RelativeLayout cart_box = new RelativeLayout(getApplicationContext()); //add to item text box
            cart_box.setLayoutParams(cart_box_temp.getLayoutParams());
            cart_box.setBackgroundResource(R.drawable.rounded_corner);

            TextView carts_text1 = new TextView(getApplicationContext());
            carts_text1.setLayoutParams(dollar_temp.getLayoutParams());
            carts_text1.setTextAppearance(R.style.item_dollar);
            carts_text1.setText("$");
            TextView carts_text2 = new TextView(getApplicationContext());
            carts_text2.setLayoutParams(price_temp.getLayoutParams());
            carts_text2.setTextAppearance(R.style.item_price);
            carts_text2.setText(""+cart.get(i).getPrice());
            TextView carts_text3 = new TextView(getApplicationContext());
            carts_text3.setLayoutParams(wet_temp.getLayoutParams());
            carts_text3.setTextAppearance(R.style.item_wet);
            carts_text3.setText(" / 1L");
            cart_box.addView(carts_text1);
            cart_box.addView(carts_text2);
            cart_box.addView(carts_text3);
            item_text_box.addView(cart_box);

            card.addView(item_text_box);

            RelativeLayout amount_bar = new RelativeLayout(getApplicationContext());
            RelativeLayout amount_bar_temp = findViewById(R.id.amount_bar);
            amount_bar.setLayoutParams(amount_bar_temp.getLayoutParams());

            ImageView minus = new ImageView(getApplicationContext());
            ImageView minus_temp = findViewById(R.id.amount_minus);
            minus.setLayoutParams(minus_temp.getLayoutParams());
            minus.setImageResource(R.drawable.ic_remove_circle_black_24dp);
            amount_bar.addView(minus);

            final TextView num = new TextView(getApplicationContext());
            TextView amount_num_temp = findViewById(R.id.amount_num);
//            textTemplate(amount_num, amount_num_temp);
            num.setLayoutParams(amount_num_temp.getLayoutParams());
            num.setText("" + cart.get(i).getAmount());
            num.setGravity(Gravity.CENTER);
            amount_bar.addView(num);

            ImageView plus = new ImageView(getApplicationContext());
            ImageView plus_temp = findViewById(R.id.amount_plus);
            plus.setLayoutParams(plus_temp.getLayoutParams());
            plus.setImageResource(R.drawable.ic_add_circle_black_24dp);
            amount_bar.addView(plus);

            minus.setClickable(true);
            plus.setClickable(true);

            minus.setId(i);
            minus.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //v.getId() will give you the image id
                    if (cart.get(v.getId()).getAmount() != 0) {
                        cart.get(v.getId()).setAmount(cart.get(v.getId()).getAmount() - 1);
                        num.setText("" + cart.get(v.getId()).getAmount());
                        double total_price = 0;
                        int amount_of_items = 0;
                        for (int i = 0; i < cart.size(); i++){
                            amount_of_items += cart.get(i).getAmount();
                            total_price += (cart.get(i).getAmount()*cart.get(i).getPrice());
                        }

                    }
                    else {
                        Context context = getApplicationContext();
                        CharSequence text = "Click Edit to delete items.";
                        int duration = Toast.LENGTH_SHORT;
                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();
                    }
                }
            });

            plus.setId(i);
            plus.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    //v.getId() will give you the image id
                    cart.get(v.getId()).setAmount(cart.get(v.getId()).getAmount() +1);
                    num.setText( "" +cart.get(v.getId()).getAmount());
                    double total_price = 0;
                    int amount_of_items = 0;
                    for (int i = 0; i < cart.size(); i++){
                        amount_of_items += cart.get(i).getAmount();
                        total_price += (cart.get(i).getAmount()*cart.get(i).getPrice());
                    }

                }
            });
            cart_box.addView(amount_bar);

            linear_scrollview_horizontal.addView(card);
            View v2 = new View(getApplicationContext());
            View v2_temp = findViewById(R.id.card_line);
            v2.setLayoutParams(v2_temp.getLayoutParams());
            v2.setBackground(v2_temp.getBackground());
            linear_scrollview_horizontal.addView(v2);
        }
    }
}
