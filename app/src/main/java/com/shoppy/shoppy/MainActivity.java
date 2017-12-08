package com.shoppy.shoppy;

import android.app.Dialog;
import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.graphics.Color;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.text.InputType;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainActivity extends AppCompatActivity {
    public static float convertDpToPixel(float dp, Context context){
        Resources resources = context.getResources();
        DisplayMetrics metrics = resources.getDisplayMetrics();
        float px = dp * ((float)metrics.densityDpi / DisplayMetrics.DENSITY_DEFAULT);
        return px;
    }
    private static final int SPEECH_REQUEST_CODE = 0;
    //private String mEmail = getIntent().getStringExtra("mEmail");
    EditText text_edit;
    ArrayList<Shopping_Item> database = new ArrayList<Shopping_Item>();
    public ArrayList<Shopping_Item> cart = new ArrayList<Shopping_Item>();
    ArrayList<Shopping_Item> recommended = new ArrayList<Shopping_Item>();
    ArrayList<Shopping_Item> remind = new ArrayList<Shopping_Item>();
    ArrayList<ArrayList<Shopping_Item>> orders = new ArrayList<ArrayList<Shopping_Item>>();

    public void textTemplate(TextView tx, TextView tx_temp){
        //tx_temp = findViewById(R.id.text_template);
        ViewGroup.LayoutParams params = tx_temp.getLayoutParams();
        tx.setLayoutParams(params);
        tx.setBackground(tx_temp.getBackground());
        tx.setTextColor(tx_temp.getTextColors());
        tx.setTextSize(tx_temp.getTextSize() / getResources().getDisplayMetrics().scaledDensity);
        tx.setPadding(tx_temp.getPaddingLeft(), tx_temp.getPaddingTop() , tx_temp.getPaddingRight(),  tx_temp.getPaddingBottom());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        CalligraphyConfig.initDefault(new CalligraphyConfig.Builder()
//                .setDefaultFontPath("fonts/Roboto-RobotoRegular.ttf")
//                .setFontAttrId(R.attr.fontPath)
//                .build()
//        );


        //Log.d("CART", "Cart errors printing");
// clear FLAG_TRANSLUCENT_STATUS flag:
        this.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
        this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        this.getWindow().setStatusBarColor(Color.parseColor("#f27348"));



        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("");

        createDatabase();

        text_edit = (EditText) findViewById(R.id.edit_text);
        text_edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int id, KeyEvent keyEvent) {
                if (id == EditorInfo.IME_ACTION_DONE || id == EditorInfo.IME_NULL) {
                    handleEditReturn(text_edit);
                    return true;
                }
                return false;
            }
        });
    }

    public void handleOnScreenButtons(View v){
        TextView text = (TextView) v;
        text_edit.setText(text.getText());
        text_edit.dispatchKeyEvent(new KeyEvent(0, 0, KeyEvent.ACTION_DOWN,
                KeyEvent.KEYCODE_ENTER, 0));

    }
    @Override
    protected void onNewIntent(Intent intent) {
        if (intent != null)
            setIntent(intent);
    }


    protected void onResume() {
        super.onResume();

        if(getIntent().hasExtra("placedOrder")) {
            LinearLayout ll = (LinearLayout) findViewById(R.id.linear_scrollview);
            TextView tv1 = new TextView(getApplicationContext());
            textTemplate(tv1, (TextView)findViewById(R.id.text_template));

            ll.addView(tv1);
            scrollDownAutomatically();

            ArrayList<Shopping_Item> order;
            order = (ArrayList<Shopping_Item>) getIntent().getSerializableExtra("order");
            orders.add(order);


            TextView recommendations = findViewById(R.id.recommendations_bt);
            recommendations.setVisibility(View.VISIBLE);
            TextView my_orders = findViewById(R.id.my_orders_bt);
            my_orders.setVisibility(View.VISIBLE);

            TextView undo_bt = findViewById(R.id.undo_bt);
            undo_bt.setVisibility(View.GONE);
            TextView modify_number_bt = findViewById(R.id.modify_number_bt);
            modify_number_bt.setVisibility(View.GONE);
            TextView view_cart_bt = findViewById(R.id.view_cart_bt);
            view_cart_bt.setVisibility(View.GONE);
        }
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (data != null) {
            database = (ArrayList<Shopping_Item>) data.getSerializableExtra("database");
            cart = (ArrayList<Shopping_Item>) data.getSerializableExtra("cart");
        }

    }

    public void createDatabase(){

        Shopping_Item item1 = new Shopping_Item();
        item1.setName("Milk");
        item1.setImage("item_1");
        item1.setPrice(4.73);
        item1.setDescription("Tucson Dairy Whole Vitman D Milk Gallon");
        item1.setAmazonLink("https://www.amazon.com/Tuscan-Dairy-Whole-Vitamin-Gallon/dp/B00032G1S0");
        item1.setItemID(1);
        item1.setAmount(-1);
        item1.setDaysSinceLastBought(2);
        item1.setReason_text("You bought this before");
        item1.setReason_resid(R.drawable.ic_reason_time);
        database.add(item1);

        Shopping_Item item2 = new Shopping_Item();
        item2.setName("Cookies");
        item2.setImage("item_2");
        item2.setPrice(3.50);
        item2.setAmazonLink("https://www.amazon.com/Peek-Freans-Assorted-Cookies-300g/dp/B00BPXQ7U8/ref=sr_1_2_s_it?s=grocery&ie=UTF8&qid=1512692110&sr=1-2&keywords=Assorted+Cream+Sandwich+Cookies");
        item2.setDescription("Family size Assorted Cream Sandwich Cookies");
        item2.setItemID(2);
        item2.setAmount(-1);
        item2.setDaysSinceLastBought(5);
        item2.setReason_text("It is on sale");
        item2.setReason_resid(R.drawable.ic_reason_on_sale);
        database.add(item2);
        recommended.add(item2);

        Shopping_Item item3 = new Shopping_Item();
        item3.setName("Notebook");
        item3.setImage("item_3");
        item3.setPrice(5.00);
        item3.setDescription("Five star Mead Notebook");
        item3.setItemID(3);
        item3.setAmazonLink("https://www.amazon.com/Mead-Spiral-Notebook-Subject-Assorted/dp/B00MP2OJHG/ref=sr_1_1?s=grocery&ie=UTF8&qid=1512692152&sr=8-1&keywords=five+star+mead+notebook");
        item3.setAmount(-1);
        item3.setDaysSinceLastBought(10);
        item3.setReason_text("Your friends bought this");
        item3.setReason_resid(R.drawable.ic_reason_friend);
        database.add(item3);
        remind.add(item3);

        Shopping_Item item4 = new Shopping_Item();
        item4.setName("Pencils");
        item4.setImage("item_4");
        item4.setPrice(5.95);
        item4.setDescription("Ticonderoga graphite #2 pencil");
        item4.setItemID(4);
        item4.setAmazonLink("https://www.amazon.com/Dixon-Ticonderoga-Wood-Cased-Pencils-13872/dp/B00125Q75Y/ref=sr_1_1?s=grocery&ie=UTF8&qid=1512692196&sr=8-1&keywords=Ticonderoga+graphite+%232+pencil");
        item4.setAmount(-1);
        item4.setDaysSinceLastBought(2);
        item4.setReason_text("Your friends bought this");
        item4.setReason_resid(R.drawable.ic_reason_friend);
        database.add(item4);
        recommended.add(item4);
        remind.add(item4);

        Shopping_Item item5 = new Shopping_Item();
        item5.setName("Toothpaste");
        item5.setImage("item_5");
        item5.setPrice(2.67);
        item5.setAmazonLink("https://www.amazon.com/Crest-Complete-Multi-Benefit-Whitening-Toothpaste/dp/B0037LGIH8/ref=sr_1_3_s_it?s=grocery&ie=UTF8&qid=1512692227&sr=1-3&keywords=crest+whitening+toothpaste");
        item5.setDescription("Crest whitening toothpaste");
        item5.setItemID(5);
        item5.setAmount(-1);
        item5.setDaysSinceLastBought(2);
        item5.setReason_text("You bought this before");
        item5.setReason_resid(R.drawable.ic_reason_time);
        database.add(item5);
        recommended.add(item5);
    }

    public void handleRemind( LinearLayout ll ){
        for (int i = 0; i < remind.size(); i++) {
            LinearLayout card = new LinearLayout(getApplicationContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            int margin = (int) convertDpToPixel(10, getApplicationContext());
            layoutParams.setMargins(margin, margin, 0, 0);
            card.setLayoutParams(layoutParams);
            card.setOrientation(LinearLayout.HORIZONTAL);


            ImageView image = new ImageView(getApplicationContext());
            int width = (int) convertDpToPixel(50, getApplicationContext());
            int height = (int) convertDpToPixel(50, getApplicationContext());
            LinearLayout.LayoutParams parmsImage = new LinearLayout.LayoutParams(width, height);
            image.setLayoutParams(parmsImage);

            Context c = getApplicationContext();
            int id = c.getResources().getIdentifier("drawable/" + remind.get(i).getImage(), null, c.getPackageName());
            image.setImageResource(id);
            card.addView(image);

            TextView text1 = new TextView(getApplicationContext());
            LinearLayout.LayoutParams parmsText = new LinearLayout.LayoutParams(width * 4, height * 2);
            parmsText.setMargins(margin, 0, 0, 0);
            text1.setLayoutParams(parmsText);
            text1.setWidth((int) convertDpToPixel(175, getApplicationContext()));
            text1.setHeight((int) convertDpToPixel(75, getApplicationContext()));
            text1.setText(remind.get(i).getName() + "-" + remind.get(i).getDescription() + "\n$" + remind.get(i).getPrice());
            card.addView(text1);

            ll.addView(card);
            scrollDownAutomatically();
        }
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
        item_name.setTextAppearance(getApplicationContext(), R.style.item_name);
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
        item_icon.setImageResource(R.drawable.ic_reason_friend);
        item_icon.setImageResource()
        item_reason.addView(item_icon);

        TextView item_icon_text = new TextView(getApplicationContext());
        item_icon_text.setTextAppearance(getApplicationContext(), R.style.item_reason);
        item_icon_text.setText("Put the reason here");
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

    public boolean containsItem(int input_id, ArrayList<Shopping_Item> list){
            for (int i = 0 ;i < list.size();i++) {
                if (list.get(i).getItemID() == input_id)
                    return true;
            }
        return false;
    }
    public void handleRecommend( LinearLayout ll ){
        LinearLayout card = new LinearLayout(getApplicationContext());
        LinearLayout card_temp = findViewById(R.id.rec_temp);
        card.setLayoutParams(card_temp.getLayoutParams());
        card.setOrientation(card_temp.getOrientation());
        card.setBackgroundResource(R.drawable.rounded_corner);

        LinearLayout topBar = new LinearLayout(getApplicationContext());
        LinearLayout topBar_temp = findViewById(R.id.top_bar_temp);
        topBar.setLayoutParams(topBar_temp.getLayoutParams());
        topBar.setOrientation(LinearLayout.HORIZONTAL);


        ImageView topBar_icon = new ImageView(getApplicationContext());
        ImageView topBar_icon_temp = findViewById(R.id.top_bar_icon);
        topBar_icon.setLayoutParams(topBar_icon_temp.getLayoutParams());
        topBar_icon.setImageResource(R.drawable.ic_recommend);
        topBar.addView(topBar_icon);

        TextView top_bar_text = new TextView(getApplicationContext());
        textTemplate(top_bar_text, (TextView) findViewById(R.id.top_bar_text));
        top_bar_text.setText("Recommendations");
        topBar.addView(top_bar_text);

        card.addView(topBar);

        View v = new View(getApplicationContext());
        View v_temp = findViewById(R.id.card_line);
        v.setLayoutParams(v_temp.getLayoutParams());
        v.setBackground(v_temp.getBackground());
        card.addView(v);

        for (int i = 0; i < recommended.size(); i++) {
            LinearLayout one_item = new LinearLayout(getApplicationContext());
            RelativeLayout buttonAdd = oneItem(recommended.get(i), one_item);
            buttonAdd.setId(i);
            final CharSequence name =  recommended.get(i).getName();
            buttonAdd.setOnClickListener(new View.OnClickListener() {

                @Override
                public void onClick(View v) {
                    int added = 0;
                    if (!containsItem( recommended.get(v.getId()).getItemID(), cart)) {
                        cart.add(recommended.get(v.getId()));
                        cart.get(cart.size() - 1).setAmount(1);
                        added = 1;
                    }
                    TextView recommendations = findViewById(R.id.recommendations_bt);
                    recommendations.setVisibility(View.GONE);
                    TextView my_orders = findViewById(R.id.my_orders_bt);
                    my_orders.setVisibility(View.GONE);

                    TextView undo_bt = findViewById(R.id.undo_bt);
                    undo_bt.setVisibility(View.VISIBLE);
                    TextView modify_number_bt = findViewById(R.id.modify_number_bt);
                    modify_number_bt.setVisibility(View.VISIBLE);
                    TextView view_cart_bt = findViewById(R.id.view_cart_bt);
                    view_cart_bt.setVisibility(View.VISIBLE);

                    LinearLayout ll = (LinearLayout) findViewById(R.id.linear_scrollview);
                    TextView tv = new TextView(getApplicationContext());
                    textTemplate(tv, (TextView)findViewById(R.id.text_template));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    // params.weight = 1.0f;
                    int margin1 = (int) convertDpToPixel(10, getApplicationContext());
                    params.setMargins(0,0, margin1, 0);
                    params.gravity = Gravity.LEFT;

                    if (added ==1)
                        tv.setText(name + " added to Cart!");
                    else
                        tv.setText("Already in cart!");

                    ll.addView(tv);
                    scrollDownAutomatically();


                }

            });

            card.addView(one_item);
            View v2 = new View(getApplicationContext());
            View v2_temp = findViewById(R.id.card_line);
            v2.setLayoutParams(v2_temp.getLayoutParams());
            v2.setBackground(v2_temp.getBackground());
            card.addView(v2);
        }



        LinearLayout bottomBar = new LinearLayout(getApplicationContext());
        LinearLayout bottomBar_temp = findViewById(R.id.bottom_bar);
        bottomBar.setLayoutParams(bottomBar_temp.getLayoutParams());
        bottomBar.setOrientation(LinearLayout.HORIZONTAL);

        ImageView bottomBar_icon = new ImageView(getApplicationContext());
        ImageView bottomBar_icon_temp = findViewById(R.id.bottom_bar_img);
        bottomBar_icon.setLayoutParams(bottomBar_icon_temp.getLayoutParams());
        bottomBar_icon.setImageResource(R.drawable.ic_arrow_forward_black_24dp);
        bottomBar.addView(bottomBar_icon);

        TextView bottomBar_text = new TextView(getApplicationContext());
        textTemplate(bottomBar_text, (TextView) findViewById(R.id.bottom_bar_text));
        bottomBar_text.setText("More Recommendations");
        bottomBar.addView(bottomBar_text);

        card.addView(bottomBar);
        bottomBar.setClickable(true );
        bottomBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), RecommendationsActivity.class);
                intent.putExtra("mEmail", getIntent().getStringExtra("mEmail"));
                intent.putExtra("database", database);
                intent.putExtra("cart", cart);
                intent.putExtra("remind", remind);
                intent.putExtra("recommended", recommended);
                startActivityForResult(intent, 0);
            }

        });
        ll.addView(card);

        TextView recommendations = findViewById(R.id.recommendations_bt);
        recommendations.setVisibility(View.GONE);
        TextView my_orders = findViewById(R.id.my_orders_bt);
        my_orders.setVisibility(View.GONE);

        scrollDownAutomatically();
    }

    public void handleBuying( LinearLayout ll, String buy_item ){
        LinearLayout card = new LinearLayout(getApplicationContext());
        LinearLayout card_temp = findViewById(R.id.rec_temp);
        card.setLayoutParams(card_temp.getLayoutParams());
        card.setOrientation(card_temp.getOrientation());
        card.setBackgroundResource(R.drawable.rounded_corner);

        LinearLayout topBar = new LinearLayout(getApplicationContext());
        LinearLayout topBar_temp = findViewById(R.id.top_bar_temp);
        topBar.setLayoutParams(topBar_temp.getLayoutParams());
        topBar.setOrientation(LinearLayout.HORIZONTAL);


        ImageView topBar_icon = new ImageView(getApplicationContext());
        ImageView topBar_icon_temp = findViewById(R.id.top_bar_icon);
        topBar_icon.setLayoutParams(topBar_icon_temp.getLayoutParams());
        topBar_icon.setImageResource(R.drawable.ic_routine_24dp);
        topBar.addView(topBar_icon);

        TextView top_bar_text = new TextView(getApplicationContext());
        textTemplate(top_bar_text, (TextView) findViewById(R.id.top_bar_text));
        top_bar_text.setText("Routine Buy");
        topBar.addView(top_bar_text);

        card.addView(topBar);

        View v = new View(getApplicationContext());
        View v_temp = findViewById(R.id.card_line);
        v.setLayoutParams(v_temp.getLayoutParams());
        v.setBackground(v_temp.getBackground());
        card.addView(v);
        int index = 0;
        for (int i = 0; i < database.size(); i++) {
            LinearLayout one_item = new LinearLayout(getApplicationContext());
            RelativeLayout buttonAdd = oneItem(database.get(i), one_item);

            buttonAdd.setId(i);
            buttonAdd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Perform action on click
                    int added = 0;
                    if (!containsItem( database.get(v.getId()).getItemID(), cart)) {
                        cart.add(database.get(v.getId()));
                        cart.get(cart.size() - 1).setAmount(1);
                        added = 1;
                    }
                    TextView recommendations = findViewById(R.id.recommendations_bt);
                    recommendations.setVisibility(View.GONE);
                    TextView my_orders = findViewById(R.id.my_orders_bt);
                    my_orders.setVisibility(View.GONE);

                    TextView undo_bt = findViewById(R.id.undo_bt);
                    undo_bt.setVisibility(View.VISIBLE);
                    TextView modify_number_bt = findViewById(R.id.modify_number_bt);
                    modify_number_bt.setVisibility(View.VISIBLE);
                    TextView view_cart_bt = findViewById(R.id.view_cart_bt);
                    view_cart_bt.setVisibility(View.VISIBLE);

                    LinearLayout ll = (LinearLayout) findViewById(R.id.linear_scrollview);
                    TextView tv = new TextView(getApplicationContext());
                    textTemplate(tv, (TextView)findViewById(R.id.text_template));
                    LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    // params.weight = 1.0f;
                    int margin1 = (int) convertDpToPixel(10, getApplicationContext());
                    params.setMargins(0,0, margin1, 0);
                    params.gravity = Gravity.LEFT;
                    if (added == 1)
                    tv.setText("Added to Cart!");
                    else
                        tv.setText("Already in cart!");
                    ll.addView(tv);
                    scrollDownAutomatically();


                }

            });

            if (database.get(i).getName().toLowerCase().contains(buy_item.toLowerCase() )) {
                card.addView(one_item);
                index = i;
            }

        }

        View v2 = new View(getApplicationContext());
        View v2_temp = findViewById(R.id.card_line);
        v2.setLayoutParams(v2_temp.getLayoutParams());
        v2.setBackground(v2_temp.getBackground());
        card.addView(v2);
 
        LinearLayout bottomBar = new LinearLayout(getApplicationContext());
        LinearLayout bottomBar_temp = findViewById(R.id.bottom_bar);
        bottomBar.setLayoutParams(bottomBar_temp.getLayoutParams());
        bottomBar.setOrientation(LinearLayout.HORIZONTAL);

        ImageView bottomBar_icon = new ImageView(getApplicationContext());
        ImageView bottomBar_icon_temp = findViewById(R.id.bottom_bar_img);
        bottomBar_icon.setLayoutParams(bottomBar_icon_temp.getLayoutParams());
        bottomBar_icon.setImageResource(R.drawable.ic_arrow_forward_black_24dp);
        bottomBar.addView(bottomBar_icon);

        TextView bottomBar_text = new TextView(getApplicationContext());
        textTemplate(bottomBar_text, (TextView) findViewById(R.id.bottom_bar_text));
        bottomBar_text.setText("Product Detail");
        bottomBar.addView(bottomBar_text);
        card.addView(bottomBar);

        bottomBar.setClickable(true );
        bottomBar.setId(index);
        bottomBar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(database.get(v.getId()).getAmazonLink()); // missing 'http://' will cause crashed
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                startActivity(intent);
            }

        });

        ll.addView(card);
        scrollDownAutomatically();
        /*for (int i = 0; i < database.size(); i++) {
            LinearLayout card = new LinearLayout(getApplicationContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            int margin = (int) convertDpToPixel(10, getApplicationContext());
            layoutParams.setMargins(margin, margin, 0, 0);
            card.setLayoutParams(layoutParams);
            card.setOrientation(LinearLayout.HORIZONTAL);
            ImageView image = new ImageView(getApplicationContext());
            int width = (int) convertDpToPixel(50, getApplicationContext());
            int height = (int) convertDpToPixel(50, getApplicationContext());
            LinearLayout.LayoutParams parmsImage = new LinearLayout.LayoutParams(width, height);
            image.setLayoutParams(parmsImage);
            Context c = getApplicationContext();
            int id = c.getResources().getIdentifier("drawable/" + database.get(i).getImage(), null, c.getPackageName());
            image.setImageResource(id);
            card.addView(image);
            TextView text1 = new TextView(getApplicationContext());
            LinearLayout.LayoutParams parmsText = new LinearLayout.LayoutParams(width * 4, height * 2);
            parmsText.setMargins(margin, 0, 0, 0);
            text1.setLayoutParams(parmsText);
            text1.setWidth((int) convertDpToPixel(175, getApplicationContext()));
            text1.setHeight((int) convertDpToPixel(75, getApplicationContext()));
            text1.setText(database.get(i).getName() + "-" + database.get(i).getDescription() + "\n$" + database.get(i).getPrice()
            + "\n"+"Last bought "+ (database.get(i).getDaysSinceLastBought()+" days ago"));
            card.addView(text1);
            Button bt = new Button(this);
            bt.setText("Add to Cart");
            bt.setId(i);
            bt.setLayoutParams(new  LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
            bt.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                    cart.add(database.get(v.getId()));
                    cart.get(cart.size()-1).setAmount(1);
                    TextView recommendations = findViewById(R.id.recommendations_bt);
                    recommendations.setVisibility(View.GONE);
                    TextView my_orders = findViewById(R.id.my_orders_bt);
                    my_orders.setVisibility(View.GONE);
                    TextView undo_bt = findViewById(R.id.undo_bt);
                    undo_bt.setVisibility(View.VISIBLE);
                    TextView modify_number_bt = findViewById(R.id.modify_number_bt);
                    modify_number_bt.setVisibility(View.VISIBLE);
                    TextView view_cart_bt = findViewById(R.id.view_cart_bt);
                    view_cart_bt.setVisibility(View.VISIBLE);
                    LinearLayout ll = (LinearLayout) findViewById(R.id.linear_scrollview);
                    TextView tv1 = new TextView(getApplicationContext());
                    LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
                    // params.weight = 1.0f;
                    int margin = (int) convertDpToPixel(10, getApplicationContext());
                    params1.setMargins(margin, margin, 0, 0);
                    params1.gravity = Gravity.LEFT;
                    tv1.setLayoutParams(params1);
                    tv1.setForegroundGravity(Gravity.LEFT);
                    tv1.setBackgroundResource(R.drawable.rounded_corner);
                    tv1.setText("Added to Cart!");
                    ll.addView(tv1);
                }
            });
            card.addView(bt);
            if (database.get(i).getName().toLowerCase().contains(buy_item.toLowerCase() )) {
                ll.addView(card);
            }
        }*/
    }

    public void handleMyOrders(LinearLayout ll) {
        TextView tv = new TextView(getApplicationContext());
        textTemplate(tv, (TextView)findViewById(R.id.text_template));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // params.weight = 1.0f;
        int margin1 = (int) convertDpToPixel(10, getApplicationContext());
        params.setMargins(0,0, margin1, 0);
        params.gravity = Gravity.LEFT;
        tv.setText("Opening Orders!");
        ll.addView(tv);
        scrollDownAutomatically();
        Intent intent = new Intent(getApplicationContext(), OrdersActivity.class);

        startActivityForResult(intent, 0);


    }

    public void handleUndo(LinearLayout ll){
        TextView tv = new TextView(getApplicationContext());
        textTemplate(tv, (TextView)findViewById(R.id.text_template));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // params.weight = 1.0f;
        int margin1 = (int) convertDpToPixel(10, getApplicationContext());
        params.setMargins(0,0, margin1, 0);
        params.gravity = Gravity.LEFT;
        if (cart.size() > 0) {
            tv.setText("Moved " + cart.get(cart.size() - 1).getName() + " out of cart!");
            cart.remove(cart.size() - 1);
        }
        else{
            tv.setText("You can't remove nothing");
        }
        ll.addView(tv);
        scrollDownAutomatically();

    }

    public void handleModifyNumber(LinearLayout ll){
        TextView tv = new TextView(getApplicationContext());
        textTemplate(tv, (TextView)findViewById(R.id.text_template));
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // params.weight = 1.0f;
        int margin1 = (int) convertDpToPixel(10, getApplicationContext());
        params.setMargins(0,0, margin1, 0);
        params.gravity = Gravity.LEFT;
        //tv.setText("Enter number of: "+cart.get(cart.size()-1).getName());
        tv.setText("Modify numbers in the shopping cart");
        ll.addView(tv);
        openCart(null);
        scrollDownAutomatically();
    }
    public void handleEditReturn(View v) {
        EditText edit = (EditText) findViewById(R.id.edit_text);

        LinearLayout ll = (LinearLayout) findViewById(R.id.linear_scrollview);
        TextView tv = new TextView(this);
        TextView tv_temp = findViewById(R.id.text_template_in);
        textTemplate(tv, tv_temp);

        tv.setText(edit.getText());
        ll.addView(tv);

        String[] keywords = {"buy", "recommend", "my orders", "view cart", "undo", "modify number"};
        String text = edit.getText().toString();
        int matches = 0;
        int index = 0;
        for (int i = 0; i < keywords.length; i++){
            if ( text.toLowerCase().startsWith(keywords[i].toLowerCase())){
                matches++;
                index = i;
            }
        }

        if (index == 3) openCart(null);

        if (matches == 0 ){
            TextView tv1 = new TextView(this);
            textTemplate(tv1, (TextView)findViewById(R.id.text_template));
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            // params.weight = 1.0f;
            int margin = (int) convertDpToPixel(10, getApplicationContext());
            params1.setMargins(margin, margin, 0, 0);
            params1.gravity = Gravity.LEFT;
            tv1.setLayoutParams(params1);
            tv1.setForegroundGravity(Gravity.LEFT);
            tv1.setBackgroundResource(R.drawable.rounded_corner);
            tv1.setText("What are you trying to ask me?");
            ll.addView(tv1);
        }
        else if (matches == 1){
            TextView tv1 = new TextView(this);
            textTemplate(tv1, (TextView)findViewById(R.id.text_template));
            String bought_item = "";

            if (index > 0) {

                    tv1.setText("Here you go" + ":");
            }
            else{
                String[] strings  = text.split(" ", 2);
                for (int j = 1; j <strings.length; j++){
                    if (j != strings.length-1) {
                        bought_item = bought_item + strings[j] + " ";
                    }else{
                        bought_item = bought_item + strings[j];
                    }
                }
                tv1.setText("According to your shopping history, I recommend this:");
            }
            ll.addView(tv1);

            if(index == 0)
                handleBuying(ll, bought_item);
            else if(index == 1)
                handleRecommend(ll);
            else if(index == 2)
                handleMyOrders(ll); //3 is above all this : if (index == 3) openCart(null);
            else if(index == 4)
                    handleUndo(ll);
            else if(index == 5)
                handleModifyNumber(ll);
            text_edit.setText("");
        }
        else {
            TextView tv1 = new TextView(this);
            textTemplate(tv1, (TextView)findViewById(R.id.text_template));
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            // params.weight = 1.0f;
            params1.gravity = Gravity.LEFT;
            tv1.setLayoutParams(params1);
            tv1.setForegroundGravity(Gravity.LEFT);
            tv1.setText("Ask for recommendations, routine items, or reminders.");
            ll.addView(tv1);
        }

        this.getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_HIDDEN);
        scrollDownAutomatically();
    }

    public void scrollDownAutomatically(){
        final ScrollView scr =  findViewById(R.id.previous_text);
        scr.postDelayed(new Runnable() {
            @Override
            public void run() {
                scr.fullScroll(scr.FOCUS_DOWN);
            }
        },500);
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

    public void openAccount(View v) {
        /*Context context = getApplicationContext();
        CharSequence text = "Account not implemented!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show(); */
        Intent intent = new Intent(getApplicationContext(), AccountActivity.class);
        intent.putExtra("mEmail", getIntent().getStringExtra("mEmail"));
        startActivity(intent);
    }
    public void openMicrophone(View v) {
       /* Context context = getApplicationContext();
        CharSequence text = "Microphone not implemented!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();*/
        try{
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo");
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        }
        catch(ActivityNotFoundException e)
        {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,   Uri.parse("https://market.android.com/details?id=com.ticktalk.translatevoice&hl=en"));
            startActivity(browserIntent);

        }
    }

    public void openCamera(View v) {
       /* Context context = getApplicationContext();
        CharSequence text = "Camera not implemented!";
        int duration = Toast.LENGTH_SHORT;
        Toast toast = Toast.makeText(context, text, duration);
        toast.show();
        */
        try{
            Intent intent = new Intent(RecognizerIntent.ACTION_RECOGNIZE_SPEECH);
            intent.putExtra(RecognizerIntent.EXTRA_LANGUAGE_MODEL,
                    RecognizerIntent.LANGUAGE_MODEL_FREE_FORM);
            intent.putExtra(RecognizerIntent.EXTRA_PROMPT, "Speech recognition demo");
            startActivityForResult(intent, SPEECH_REQUEST_CODE);
        }
        catch(ActivityNotFoundException e)
        {
            Intent browserIntent = new Intent(Intent.ACTION_VIEW,   Uri.parse("https://play.google.com/store/apps/details?id=com.msearcher.camfind&hl=en"));
            startActivity(browserIntent);

        }
    }
}