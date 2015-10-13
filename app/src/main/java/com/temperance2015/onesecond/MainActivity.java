package com.temperance2015.onesecond;

import android.app.Activity;
import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends Activity{

    private long firstExitTime = 0L;
    private static final int EXIT_TIME = 2000;
    private double second;
    private Button mainButton;
    private TextView secondText;
    private long downTime;
    private long upTime;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        secondText = (TextView)findViewById(R.id.second_text);
        mainButton = (Button)findViewById(R.id.main_button);
        mainButton.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if(event.getAction() == MotionEvent.ACTION_DOWN){
                    downTime = System.currentTimeMillis();
                }
                else if (event.getAction() == MotionEvent.ACTION_UP){
                    upTime = System.currentTimeMillis();
                    second = (double)(upTime-downTime)/1000;
                    secondText.setText(second+"秒");
                }
                return false;
            }
        });
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
