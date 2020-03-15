package com.example.babycareproject.activity.activity.activity;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.babycareproject.R;

public class SlashScreenActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_slashscreen);
        final int ScreenDisplay = 1800;
        Thread t1=new Thread(){
            int wait1=0;
            public void run(){
                try{
                    while(wait1<=ScreenDisplay )
                    {
                        sleep(100);
                        wait1+=100;
                    }
                }
                catch(Exception e){
                    e.printStackTrace();
                }
                finally{
                    Intent intentg= new Intent(SlashScreenActivity.this, UserDashBoardActivity.class);
                    startActivity(intentg);
                    finish();

                }
            }
        };
        t1.start();
    }
}
