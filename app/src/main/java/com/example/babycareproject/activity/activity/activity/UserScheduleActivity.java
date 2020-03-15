package com.example.babycareproject.activity.activity.activity;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ListView;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.babycareproject.activity.activity.adapter.UserScheduleAdapter;
import com.example.babycareproject.activity.activity.models.SchedulePojo;
import com.example.babycareproject.activity.activity.models.UserSchedulePojo;
import com.example.babycareproject.R;
import com.example.babycareproject.activity.activity.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserScheduleActivity extends AppCompatActivity {
    ListView list_view;
    ProgressDialog progressDialog;
    List<UserSchedulePojo> a1;
    SharedPreferences sharedPreferences;
    String uname;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_userschedule);

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        uname = sharedPreferences.getString("user_name", "");

        getSupportActionBar().setTitle("Schedule");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        list_view=(ListView)findViewById(R.id.list_view);

        serverData();
    }
    List<SchedulePojo> al;
    public void serverData(){
        progressDialog = new ProgressDialog(UserScheduleActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        uname = sharedPreferences.getString("user_name", "");
        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<SchedulePojo>> call = service.getMySchdule(uname);
        call.enqueue(new Callback<List<SchedulePojo>>() {
            @Override
            public void onResponse(Call<List<SchedulePojo>> call, Response<List<SchedulePojo>> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(UserScheduleActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    al = response.body();
                    list_view.setAdapter(new UserScheduleAdapter(al, UserScheduleActivity.this));
                }
            }

            @Override
            public void onFailure(Call<List<SchedulePojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(UserScheduleActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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

}


