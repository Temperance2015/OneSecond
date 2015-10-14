package com.temperance2015.onesecond;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ShareActionProvider;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{

    private long firstExitTime = 0L;
    private static final int EXIT_TIME = 2000;
    private double second;
    private Button mainButton;
    private TextView secondText;
    private TextView complimentText;
    private long downTime;
    private long upTime;
    private ShareActionProvider shareActionProvider;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        secondText = (TextView)findViewById(R.id.second_text);
        complimentText = (TextView)findViewById(R.id.compliment_text);
        mainButton = (Button)findViewById(R.id.main_button);
        mainButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    downTime = System.currentTimeMillis();
                    complimentText.setText("………………");
                }
                else if (event.getAction() == MotionEvent.ACTION_UP){
                    upTime = System.currentTimeMillis();
                    second = (double)(upTime-downTime)/1000;
                    secondText.setText(second+" Second");
                }
                if (second > 0.95 && second < 1.05){
                    complimentText.setText("Absolutely Fantastic!");
                }else if(second > 0.9 && second < 1.1){
                    complimentText.setText("Perfect!");
                }else if (second > 0.8 && second < 1.2){
                    complimentText.setText("Just so so");
                }else {
                    complimentText.setText("interesting");
                }
                return false;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_main, menu);
        MenuItem item = menu.findItem(R.id.action_share);
        shareActionProvider = (ShareActionProvider) item.getActionProvider();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_share) {
            Intent intent = new Intent(Intent.ACTION_SEND);
            share(intent);
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setShareIntent(Intent shareIntent){
        if (shareActionProvider != null){
            shareActionProvider.setShareIntent(shareIntent);
        }
    }

    private void share(Intent intent){
        intent.setType("image/");
        intent.putExtra(Intent.EXTRA_SUBJECT,"Share");
        intent.putExtra(Intent.EXTRA_TEXT, secondText.getText());
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        setShareIntent(intent);
    }

    @Override
    public void onBackPressed(){
        long curTime = System.currentTimeMillis();
        if (curTime - firstExitTime < EXIT_TIME){
            finish();
        }
        else {
            Toast.makeText(MainActivity.this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
            firstExitTime = curTime;
        }
    }

}
