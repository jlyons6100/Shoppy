package com.shoppy.shoppy;

import android.content.ActivityNotFoundException;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.speech.RecognizerIntent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Gravity;
import android.view.KeyEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.util.List;

public class MainActivity extends AppCompatActivity {
    private static final int SPEECH_REQUEST_CODE = 0;
    //private String mEmail = getIntent().getStringExtra("mEmail");
    EditText text_edit;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        Toolbar myToolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(myToolbar);
        getSupportActionBar().setTitle("");

        /*LinearLayout ll = (LinearLayout) findViewById(R.id.linear_scrollview);
        TextView tv = new TextView(this);
        tv.setGravity(Gravity.LEFT);
        tv.setLayoutParams(new RelativeLayout.LayoutParams(RelativeLayout.LayoutParams.WRAP_CONTENT
                , RelativeLayout.LayoutParams.WRAP_CONTENT));
        tv.setBackgroundResource(R.drawable.rounded_corner);
        tv.setText("your text");
        ll.addView(tv);*/

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

    public void handleEditReturn(View v) {
        EditText edit = (EditText) findViewById(R.id.edit_text);

        LinearLayout ll = (LinearLayout) findViewById(R.id.linear_scrollview);
        TextView tv = new TextView(this);
        LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
        // params.weight = 1.0f;
        params.gravity = Gravity.RIGHT;
        tv.setLayoutParams(params);
        tv.setForegroundGravity(Gravity.RIGHT);
        tv.setBackgroundResource(R.drawable.rounded_corner);
        tv.setText(edit.getText());
        ll.addView(tv);

        String[] keywords = {"Routine", "Recommend", "Remind"};
        String text = edit.getText().toString();
        int matches = 0;
        int index = 0;
        for (int i = 0; i < keywords.length; i++){
            if ( text.toLowerCase().contains(keywords[i].toLowerCase())){
                matches++;
                index = i;
            }
        }
        if (matches >1 ){
            TextView tv1 = new TextView(this);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            // params.weight = 1.0f;
            params1.gravity = Gravity.RIGHT;
            tv1.setLayoutParams(params1);
            tv1.setForegroundGravity(Gravity.LEFT);
            tv1.setBackgroundResource(R.drawable.rounded_corner);
            tv1.setText("You cannot ask for multiple things at once");
            ll.addView(tv1);
        }
        else if (matches ==1){
            TextView tv1 = new TextView(this);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
            // params.weight = 1.0f;
            params1.gravity = Gravity.LEFT;
            tv1.setLayoutParams(params1);
            tv1.setForegroundGravity(Gravity.LEFT);
            tv1.setBackgroundResource(R.drawable.rounded_corner);
            tv1.setText("Here are your "+keywords[index]);
            ll.addView(tv1);
        }
        else {
            TextView tv1 = new TextView(this);
            LinearLayout.LayoutParams params1 = new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT);
           // params.weight = 1.0f;
            params1.gravity = Gravity.LEFT;
            tv1.setLayoutParams(params1);
            tv1.setForegroundGravity(Gravity.LEFT);
            tv1.setBackgroundResource(R.drawable.rounded_corner);
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
        startActivity(intent);
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
