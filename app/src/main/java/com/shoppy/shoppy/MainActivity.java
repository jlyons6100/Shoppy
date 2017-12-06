package com.shoppy.shoppy;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.content.res.Resources;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
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
    ArrayList<Shopping_Item> cart = new ArrayList<Shopping_Item>();
    ArrayList<Shopping_Item> recommended = new ArrayList<Shopping_Item>();
    ArrayList<Shopping_Item> remind = new ArrayList<Shopping_Item>();
    ArrayList<ArrayList<Shopping_Item>> orders = new ArrayList<ArrayList<Shopping_Item>>();



    public void textTemplate(TextView tx, TextView tx_temp){
      //tx_temp = findViewById(R.id.text_template);
        ViewGroup.LayoutParams params = tx_temp.getLayoutParams();
        tx.setLayoutParams(params);
        tx.setBackgroundResource(R.drawable.rounded_corner);
        tx.setTextColor(tx_temp.getTextColors());
        tx.setTextSize(tx_temp.getTextSize() / getResources().getDisplayMetrics().scaledDensity);
        tx.setPadding(tx_temp.getPaddingLeft(), tx_temp.getPaddingTop() , tx_temp.getPaddingRight(),  tx_temp.getPaddingBottom());
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
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

            ArrayList<Shopping_Item> order;
            order = (ArrayList<Shopping_Item>) getIntent().getSerializableExtra("order");
            System.out.println(order.get(0).getDescription());
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

                    if(data.hasExtra("placedOrder")){
                       /* Context context = getApplicationContext();
                        CharSequence text = "Order Placed!";
                        int duration = Toast.LENGTH_SHORT;

                        Toast toast = Toast.makeText(context, text, duration);
                        toast.show();*/

                    }
                }

    }

    public void createDatabase(){

        Shopping_Item item1 = new Shopping_Item();
        item1.setName("Milk");
        item1.setImage("item_1");
        item1.setPrice(4.73);
        item1.setDescription("Tucson Dairy Whole Vitman D Milk Gallon");
        item1.setItemID(1);
        item1.setAmount(-1);
        item1.setDaysSinceLastBought(2);
        database.add(item1);

        Shopping_Item item2 = new Shopping_Item();
        item2.setName("Cookies");
        item2.setImage("item_2");
        item2.setPrice(3.50);
        item2.setDescription("Family size Assorted Cream Sandwich Cookies");
        item2.setItemID(2);
        item2.setAmount(-1);
        item2.setDaysSinceLastBought(5);
        database.add(item2);
        recommended.add(item2);

        Shopping_Item item3 = new Shopping_Item();
        item3.setName("Notebook");
        item3.setImage("item_3");
        item3.setPrice(5.00);
        item3.setDescription("Five star Mead Notebook");
        item3.setItemID(3);
        item3.setAmount(-1);
        item3.setDaysSinceLastBought(10);
        database.add(item3);

        remind.add(item3);
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
        }
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
            LinearLayout one_item_temp = findViewById(R.id.one_item);
            one_item.setLayoutParams(one_item_temp.getLayoutParams());
            one_item.setOrientation(LinearLayout.HORIZONTAL);

            ImageView item_img = new ImageView(getApplicationContext());
            ImageView item_img_temp = findViewById(R.id.item_image);
            item_img.setLayoutParams(item_img_temp.getLayoutParams());
            Context c = getApplicationContext();
            int id = c.getResources().getIdentifier("drawable/" + recommended.get(i).getImage(), null, c.getPackageName());
            item_img.setImageResource(id);
            one_item.addView(item_img);

            LinearLayout item_text_box = new LinearLayout(getApplicationContext());
            LinearLayout item_text_box_temp = findViewById(R.id.item_text_box);
            item_text_box.setLayoutParams(item_text_box_temp.getLayoutParams());
            item_text_box.setBackgroundResource(R.drawable.rounded_corner);
            item_text_box.setOrientation(LinearLayout.VERTICAL);

            TextView item_name = new TextView(getApplicationContext());
            item_name.setTextAppearance(R.style.item_name);
            item_name.setText(recommended.get(i).getName());
            item_text_box.addView(item_name);

            LinearLayout item_reason = new LinearLayout(getApplicationContext());
            LinearLayout item_reason_temp = findViewById(R.id.item_reason);
            item_reason.setLayoutParams(item_reason_temp.getLayoutParams());
            item_reason.setOrientation(LinearLayout.HORIZONTAL);

            ImageView item_icon = new ImageView(getApplicationContext());
            ImageView item_icon_temp = findViewById(R.id.item_icon);
            item_icon.setLayoutParams(item_icon_temp.getLayoutParams());
            item_icon.setImageResource(R.drawable.ic_reason_friend);
            item_reason.addView(item_icon);

            TextView item_icon_text = new TextView(getApplicationContext());
            item_icon_text.setTextAppearance(R.style.item_reason);
            item_icon_text.setText("Put the reason here");
            item_reason.addView(item_icon_text);

            item_text_box.addView(item_reason);

            TextView item_detail = new TextView(getApplicationContext());
            item_detail.setTextAppearance(R.style.item_detail);
            item_detail.setText(recommended.get(i).getDescription());
            item_text_box.addView(item_detail);

            LinearLayout cart_box = new LinearLayout(getApplicationContext()); //add to item text box
            LinearLayout cart_box_temp = findViewById(R.id.cart_box);
            cart_box.setLayoutParams(cart_box_temp.getLayoutParams());
            cart_box.setBackgroundResource(R.drawable.rounded_corner);

            TextView carts_text1 = new TextView(getApplicationContext());
            carts_text1.setTextAppearance(R.style.item_dollar);
            carts_text1.setText("$    ");
            TextView carts_text2 = new TextView(getApplicationContext());
            carts_text2.setTextAppearance(R.style.item_price);
            carts_text2.setText(""+recommended.get(i).getPrice());
            TextView carts_text3 = new TextView(getApplicationContext());
            carts_text3.setTextAppearance(R.style.item_wet);
            carts_text3.setText("     /1L");
            cart_box.addView(carts_text1);
            cart_box.addView(carts_text2);
            cart_box.addView(carts_text3);

            LinearLayout buttonAdd = new LinearLayout(getApplicationContext());
            LinearLayout buttonAdd_temp = findViewById(R.id.button_add);
            buttonAdd.setLayoutParams(buttonAdd_temp.getLayoutParams());
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
            shopping_icon_text.setText("Add    ");

            buttonAdd.addView(shopping_icon_text);

            cart_box.addView(buttonAdd);
            item_text_box.addView(cart_box);
            one_item.addView(item_text_box);


            card.addView(one_item);
        }
      /*  for (int i = 0; i < recommended.size(); i++) {
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
            int id = c.getResources().getIdentifier("drawable/" + recommended.get(i).getImage(), null, c.getPackageName());
            image.setImageResource(id);
            card.addView(image);

            TextView text1 = new TextView(getApplicationContext());
            LinearLayout.LayoutParams parmsText = new LinearLayout.LayoutParams(width * 4, height * 2);
            parmsText.setMargins(margin, 0, 0, 0);
            text1.setLayoutParams(parmsText);
            text1.setWidth((int) convertDpToPixel(175, getApplicationContext()));
            text1.setHeight((int) convertDpToPixel(75, getApplicationContext()));
            text1.setText(recommended.get(i).getName() + "-" + recommended.get(i).getDescription() + "\n$" + recommended.get(i).getPrice());
            card.addView(text1);


            Button bt = new Button(this);
            bt.setText("Add to Cart");
            bt.setId(i);
            bt.setLayoutParams(new  LinearLayout.LayoutParams( LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT));
           bt.setOnClickListener(new View.OnClickListener() {
                public void onClick(View v) {
                    // Perform action on click
                    cart.add(recommended.get(v.getId()));
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


            ll.addView(card);
        }*/

      ll.addView(card);
    }

    public void handleBuying( LinearLayout ll, String buy_item ){
        for (int i = 0; i < database.size(); i++) {
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
        }
    }

    public void handleMyOrders(LinearLayout ll) {
        if (orders.size() == 0){
            LinearLayout card = new LinearLayout(getApplicationContext());
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            int margin = (int) convertDpToPixel(10, getApplicationContext());
            layoutParams.setMargins(margin, margin, 0, 0);
            card.setLayoutParams(layoutParams);
            card.setOrientation(LinearLayout.HORIZONTAL);
            int width = (int) convertDpToPixel(50, getApplicationContext());
            int height = (int) convertDpToPixel(50, getApplicationContext());

            TextView text1 = new TextView(getApplicationContext());
            LinearLayout.LayoutParams parmsText = new LinearLayout.LayoutParams(width * 4, height * 2);
            parmsText.setMargins(margin, 0, 0, 0);
            text1.setLayoutParams(parmsText);
            text1.setWidth((int) convertDpToPixel(175, getApplicationContext()));
            text1.setHeight((int) convertDpToPixel(75, getApplicationContext()));
            text1.setText("No Recent Orders");
            card.addView(text1);
            ll.addView(card);
        }
        for (int i = 0; i < orders.size(); i++) {
            for (int j = 0; j < orders.get(i).size(); j++)
            {
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
        int id = c.getResources().getIdentifier("drawable/" + orders.get(i).get(j).getImage(), null, c.getPackageName());
        image.setImageResource(id);
        card.addView(image);

        TextView text1 = new TextView(getApplicationContext());
        LinearLayout.LayoutParams parmsText = new LinearLayout.LayoutParams(width * 4, height * 2);
        parmsText.setMargins(margin, 0, 0, 0);
        text1.setLayoutParams(parmsText);
        text1.setWidth((int) convertDpToPixel(175, getApplicationContext()));
        text1.setHeight((int) convertDpToPixel(75, getApplicationContext()));
        text1.setText(orders.get(i).get(j).getName() + "-" + orders.get(i).get(j).getDescription() + "\n$" + orders.get(i).get(j).getPrice()
                + "\n" + "Last bought " + (orders.get(i).get(j).getDaysSinceLastBought() + " days ago"));
        card.addView(text1);
        ll.addView(card);
    }
        }
    }

    public void handleEditReturn(View v) {
        EditText edit = (EditText) findViewById(R.id.edit_text);

        LinearLayout ll = (LinearLayout) findViewById(R.id.linear_scrollview);
        TextView tv = new TextView(this);
        textTemplate(tv, (TextView)findViewById(R.id.text_template));
       LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // params.weight = 1.0f;
        int margin1 = (int) convertDpToPixel(10, getApplicationContext());
        params.setMargins(0,0, margin1, 0);
        params.gravity = Gravity.RIGHT;

        tv.setLayoutParams(params);
        tv.setForegroundGravity(Gravity.RIGHT);
        tv.setBackgroundResource(R.drawable.rounded_corner);
        tv.setText(edit.getText());
        ll.addView(tv);

        String[] keywords = {"Buy", "Recommend", "My Orders", "View Cart"};
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
                if (index != 1) {
                    tv1.setText(keywords[index] + ":");
                }
                else {
                    tv1.setText("Here you go" + ":");
                }
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
                handleMyOrders(ll);

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
        intent.putExtra("cart", cart);
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
