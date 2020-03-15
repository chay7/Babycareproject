package com.example.babycareproject.activity.activity.activity;

import android.app.DatePickerDialog;
import android.app.ProgressDialog;
import android.app.TimePickerDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.DatePicker;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.TimePicker;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.babycareproject.R;
import com.example.babycareproject.activity.activity.Utils;

import java.util.Calendar;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NaniBookingActivity extends AppCompatActivity {
    TextView tv_name,tv_phno,tv_dob,tv_email,tv_experience,tv_location,tv_rating,tv_time;
    private DatePickerDialog.OnDateSetListener mDateSetListener;
    String date;
    Button btn_book;
    RatingBar ratingBar;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nanibooking);
       // Toast.makeText(getApplicationContext(),getIntent().getStringExtra("utype"),Toast.LENGTH_SHORT).show();
        getSupportActionBar().setTitle("Nanies/Nurses Details");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        btn_book=(Button)findViewById(R.id.btn_book);
        tv_name=(TextView)findViewById(R.id.tv_name);
        tv_email=(TextView)findViewById(R.id.tv_email);
        tv_phno=(TextView)findViewById(R.id.tv_phno);
        tv_dob=(TextView)findViewById(R.id.tv_dob);
        tv_experience=(TextView)findViewById(R.id.tv_location);
        tv_location=(TextView)findViewById(R.id.tv_experience);
        tv_time=(TextView)findViewById(R.id.tv_time);
        tv_name.setText("Name  :"+getIntent().getStringExtra("name"));
        tv_phno.setText("Phone Number :"+getIntent().getStringExtra("phno"));
        tv_email.setText("Email :"+getIntent().getStringExtra("Email"));
        tv_experience.setText("Experience :"+getIntent().getStringExtra("Experience")+" Yrs");
        tv_location.setText("Locoation :"+getIntent().getStringExtra("Locoation"));
        //tv_rating.setText("Rating :"+getIntent().getStringExtra("Rating"));

        ratingBar=(RatingBar)findViewById(R.id.ratingBar);
        ratingBar.setRating(Float.parseFloat(getIntent().getStringExtra("Rating")));

        tv_dob.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                timedatepicker();

            }
        });

        tv_time.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                setmTimePicker();

            }
        });
        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                submitData();
            }
        });



    }
    public void timedatepicker() {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);
        mDateSetListener = new DatePickerDialog.OnDateSetListener() {
            @Override
            public void onDateSet(DatePicker datePicker, int year, int month, int day) {
                month = month + 1;
                Log.d(String.valueOf(NaniBookingActivity.this), "onDateSet: mm/dd/yyy: " + month + "/" + day + "/" + year);
                //date = month + "/" + day + "/" + year;
                date=year + "-" + month + "-" + day;
                tv_dob.setText(date);


            }
        };
        DatePickerDialog dialog = new DatePickerDialog(
                NaniBookingActivity.this, android.R.style.Theme_Holo_Light_Dialog_MinWidth, mDateSetListener, year, month, day);

        dialog.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        dialog.getDatePicker().setMinDate(System.currentTimeMillis() - 1000);
        dialog.show();

    }
    ProgressDialog progressDialog;
    SharedPreferences sharedPreferences;
    String uname="";
    private void submitData() {
        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        uname = sharedPreferences.getString("user_name", "");
        progressDialog = new ProgressDialog(NaniBookingActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = service.add_schedule(tv_dob.getText().toString(),tv_time.getText().toString(),uname,getIntent().getStringExtra("name"),getIntent().getStringExtra("phno"),getIntent().getStringExtra("Email"),getIntent().getStringExtra("Uname"),getIntent().getStringExtra("utype"));
        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                if (response.body().status.equals("true")) {
                    progressDialog.dismiss();
                    Toast.makeText(NaniBookingActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(NaniBookingActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(NaniBookingActivity.this, t.getMessage(), Toast.LENGTH_LONG).show();
            }
        });
    }
//    public boolean onCreateOptionsMenu(Menu menu) {
//        // Inflate the menu; this adds items to the action bar if it is present.
//        getMenuInflater().inflate(R.menu.menu, menu);//Menu Resource, Menu
//        return true;
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.idlogout:
//                Intent i=new Intent(getApplicationContext(),MainActivity.class);
//                startActivity(i);
//                finish();

            case android.R.id.home:
                this.finish();

                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }
    public void setmTimePicker(){

        // TODO Auto-generated method stub
        Calendar mcurrentTime = Calendar.getInstance();
        int hour = mcurrentTime.get(Calendar.HOUR_OF_DAY);
        int minute = mcurrentTime.get(Calendar.MINUTE);
        TimePickerDialog mTimePicker;
        mTimePicker = new TimePickerDialog(NaniBookingActivity.this, new TimePickerDialog.OnTimeSetListener() {
            @Override
            public void onTimeSet(TimePicker timePicker, int selectedHour, int selectedMinute) {
                tv_time.setText( selectedHour + ":" + selectedMinute);
            }
        }, hour, minute, true);//Yes 24 hour time
        mTimePicker.setTitle("Select Time");
        mTimePicker.show();

    }
}
