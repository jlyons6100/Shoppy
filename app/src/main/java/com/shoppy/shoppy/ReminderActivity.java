package com.shoppy.shoppy;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.opengl.Visibility;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
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

public class ReminderActivity extends AppCompatActivity {
    ArrayList<Shopping_Item> database = new ArrayList<Shopping_Item>();
    private ArrayList<Shopping_Item> remind = new ArrayList<Shopping_Item>();
    private ArrayList<Shopping_Item> cart = new ArrayList<Shopping_Item>();
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }
    @Override
    protected void onNewIntent(Intent intent) {
        if (intent != null)
            setIntent(intent);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Log.d("CART", "Cart:");
        for (int i = 0; i < cart.size(); i++){
            Log.d("CART", "" + cart.get(i).toString());
        }
        Intent openMainActivity= new Intent(ReminderActivity.this, CartActivity.class);
        openMainActivity.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
        openMainActivity.putExtra("mEmail", getIntent().getStringExtra("mEmail"));
        openMainActivity.putExtra("database", database);
        openMainActivity.putExtra("cart", cart);
        openMainActivity.putExtra("remind", remind);
        startActivityIfNeeded(openMainActivity, 0);

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_reminder);
         database = (ArrayList<Shopping_Item>)getIntent().getSerializableExtra("database");
        remind = (ArrayList<Shopping_Item>)getIntent().getSerializableExtra("remind");
        cart = (ArrayList<Shopping_Item>)getIntent().getSerializableExtra("cart");
        drawCart(remind);
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
        intent.putExtra("remind", remind);
        startActivityForResult(intent, 0);
    }

    public void handleOnClick(View v){
        cart.add(remind.get(v.getId()));
        cart.get(cart.size()-1).setAmount(1);
        Context context = getApplicationContext();
        CharSequence text = remind.get(v.getId()).getName()+ " added to cart!";
        remind.remove(v.getId());
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        for (int i = 0; i < cart.size(); i++){
            Log.d("CART", "After: " + cart.get(i).toString());
        }
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

            // Item text -- price
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

            TextView amount_bar = new TextView(getApplicationContext());
            TextView amount_bar_temp = findViewById(R.id.amount_bar);
            amount_bar.setLayoutParams(amount_bar_temp.getLayoutParams());
            amount_bar.setText("Move to Cart");
            amount_bar.setTextColor(amount_bar_temp.getTextColors());
            amount_bar.setClickable(true );
            amount_bar.setId(i);
            cart_box.addView(amount_bar);
            amount_bar.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    handleOnClick(v);
                     v.setVisibility(View.GONE);

                }

            });



            View v2 = new View(getApplicationContext());
            View v2_temp = findViewById(R.id.card_line);
            v2.setLayoutParams(v2_temp.getLayoutParams());
            v2.setBackground(v2_temp.getBackground());
            card.addView(v2);
            linear_scrollview_horizontal.addView(card);
        }

    }
}
