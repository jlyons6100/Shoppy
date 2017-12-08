package com.shoppy.shoppy;

import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.view.WindowManager;
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

        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        this.getWindow().setStatusBarColor(Color.parseColor("#f27348"));
        database = (ArrayList<Shopping_Item>)getIntent().getSerializableExtra("database");
        cart = (ArrayList<Shopping_Item>)getIntent().getSerializableExtra("cart");
        recommended = (ArrayList<Shopping_Item>)getIntent().getSerializableExtra("recommended");
        drawCart(recommended);
    }

    public void textTemplate(TextView tx, TextView tx_temp){
        //tx_temp = findViewById(R.id.text_template);
        ViewGroup.LayoutParams params = tx_temp.getLayoutParams();
        tx.setLayoutParams(params);
        tx.setBackground(tx_temp.getBackground());
        tx.setTextColor(tx_temp.getTextColors());
        tx.setTextSize(tx_temp.getTextSize() / getResources().getDisplayMetrics().scaledDensity);
        tx.setPadding(tx_temp.getPaddingLeft(), tx_temp.getPaddingTop() , tx_temp.getPaddingRight(),  tx_temp.getPaddingBottom());
    }

    public  RelativeLayout oneItem(Shopping_Item item, LinearLayout one_item) {
        LinearLayout one_item_temp = findViewById(R.id.one_item);
        one_item.setLayoutParams(one_item_temp.getLayoutParams());
        one_item.setOrientation(LinearLayout.HORIZONTAL);

        // Item image
        ImageView item_img = new ImageView(getApplicationContext());
        ImageView item_img_temp = findViewById(R.id.item_image);
        item_img.setLayoutParams(item_img_temp.getLayoutParams());
        Context c = getApplicationContext();
        int id = c.getResources().getIdentifier("drawable/" + item.getImage(), null, c.getPackageName());
        item_img.setImageResource(id);
        one_item.addView(item_img);

        // Item text
        RelativeLayout item_text_box = new RelativeLayout(getApplicationContext());
        RelativeLayout item_text_box_temp = findViewById(R.id.item_text_box);
        item_text_box.setLayoutParams(item_text_box_temp.getLayoutParams());
        item_text_box.setBackgroundResource(R.drawable.rounded_corner);

        // Item text -- name
        TextView item_name = new TextView(getApplicationContext());
        item_name.setTextAppearance(getApplicationContext(),R.style.item_name);
        item_name.setText(item.getName());
        item_text_box.addView(item_name);

        // Item text -- reason
        LinearLayout item_reason = new LinearLayout(getApplicationContext());
        LinearLayout item_reason_temp = findViewById(R.id.item_reason);
        item_reason.setLayoutParams(item_reason_temp.getLayoutParams());
        item_reason.setOrientation(LinearLayout.HORIZONTAL);


        ImageView item_icon = new ImageView(getApplicationContext());
        ImageView item_icon_temp = findViewById(R.id.item_icon);
        item_icon.setLayoutParams(item_icon_temp.getLayoutParams());
        item_icon.setImageResource(item.getReason_resid());
        item_reason.addView(item_icon);

        TextView item_icon_text = new TextView(getApplicationContext());
        item_icon_text.setTextAppearance(getApplicationContext(), R.style.item_reason);
        item_icon_text.setText(item.getReason_text());
        textTemplate(item_icon_text, (TextView)findViewById(R.id.item_icon_text));
        item_reason.addView(item_icon_text);

        item_text_box.addView(item_reason);

        // Item text -- detail
        TextView item_detail = new TextView(getApplicationContext());
        TextView detail_temp = findViewById(R.id.item_detail);
        item_detail.setLayoutParams(detail_temp.getLayoutParams());
        item_detail.setTextAppearance(getApplicationContext(),R.style.item_detail);
        item_detail.setText(item.getDescription());
        item_text_box.addView(item_detail);

        // Item text -- price and add
        RelativeLayout cart_box = new RelativeLayout(getApplicationContext()); //add to item text box
        RelativeLayout cart_box_temp = findViewById(R.id.cart_box);
        cart_box.setLayoutParams(cart_box_temp.getLayoutParams());
        cart_box.setBackgroundResource(R.drawable.rounded_corner);

        TextView carts_text1 = new TextView(getApplicationContext());
        TextView dollar_temp = findViewById(R.id.cart_dollar);
        carts_text1.setLayoutParams(dollar_temp.getLayoutParams());
        carts_text1.setTextAppearance(getApplicationContext(),R.style.item_dollar);
        carts_text1.setText("$");
        TextView carts_text2 = new TextView(getApplicationContext());
        TextView price_temp = findViewById(R.id.cart_price);
        carts_text2.setLayoutParams(price_temp.getLayoutParams());
        carts_text2.setTextAppearance(getApplicationContext(),R.style.item_price);
        carts_text2.setText(""+item.getPrice());
        TextView carts_text3 = new TextView(getApplicationContext());
        TextView wet_temp = findViewById(R.id.cart_wet);
        carts_text3.setLayoutParams(wet_temp.getLayoutParams());
        carts_text3.setTextAppearance(getApplicationContext(),R.style.item_wet);
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


        ImageView shopping_icon = new ImageView(getApplicationContext());
        ImageView shopping_icon_temp = findViewById(R.id.shopping_icon);
        shopping_icon.setLayoutParams(shopping_icon_temp.getLayoutParams());
        shopping_icon.setImageResource(R.drawable.ic_shopping_cart);
        buttonAdd.addView(shopping_icon);

        TextView shopping_icon_text = new TextView(getApplicationContext());
        TextView shopping_icon_text_temp = findViewById(R.id.shopping_icon_text);
        textTemplate(shopping_icon_text, shopping_icon_text_temp);

        shopping_icon_text.setBackgroundResource(0);
        shopping_icon_text.setText("Add");
        buttonAdd.addView(shopping_icon_text);

        cart_box.addView(buttonAdd);
        item_text_box.addView(cart_box);
        one_item.addView(item_text_box);

        return buttonAdd;
    }

    public void drawCart(final ArrayList<Shopping_Item> recommend)
    {
        LinearLayout linear_scrollview_horizontal = findViewById(R.id.cartpage_linear_scrollview);

        for (int i = 0 ; i < recommend.size(); i++) {
            LinearLayout one_item = new LinearLayout(getApplicationContext());
            RelativeLayout buttonAdd = oneItem(recommend.get(i), one_item);
            buttonAdd.setId(i);
            buttonAdd.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    // Perform action on click
                    cart.add(recommend.get(v.getId()));
                    //Log.d("CART", "CART: " );
                    //Log.d("CART", cart.get(0).getDescription());
                    cart.get(cart.size()-1).setAmount(1);

                    Context context = getApplicationContext();
                    CharSequence text = recommend.get(v.getId()).getName()+ " added to cart!";
                    int duration = Toast.LENGTH_SHORT;
                    Toast toast = Toast.makeText(context, text, duration);
                    toast.setGravity(Gravity.CENTER, 0, 0);
                    toast.show();
                }

            });

            linear_scrollview_horizontal.addView(one_item);
            View v2 = new View(getApplicationContext());
            View v2_temp = findViewById(R.id.card_line);
            v2.setLayoutParams(v2_temp.getLayoutParams());
            v2.setBackground(v2_temp.getBackground());
            linear_scrollview_horizontal.addView(v2);
        }
    }

    public void openCart(View v) {
        /*Context context = getApplicationContext();
        CharSequence text = "Cart not implemented!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();*/

        Intent intent = new Intent(getApplicationContext(), CartActivity.class);
        intent.putExtra("mEmail", getIntent().getStringExtra("mEmail"));
        intent.putExtra("database", database);
        //Log.d("CART", "Opening cart" + cart.get(0).getDescription() );
        intent.putExtra("cart", cart);
        intent.putExtra("remind", remind);
        intent.putExtra("recommended", recommended);
        startActivityForResult(intent, 0);
    }
}
