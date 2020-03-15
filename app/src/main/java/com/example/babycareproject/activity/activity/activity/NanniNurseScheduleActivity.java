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


import com.example.babycareproject.activity.activity.adapter.NanniNurseScheduleAdapter;
import com.example.babycareproject.activity.activity.models.NaniesNursesSchedulePojo;
import com.example.babycareproject.activity.activity.models.UserSchedulePojo;
import com.example.babycareproject.R;
import com.example.babycareproject.activity.activity.Utils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NanniNurseScheduleActivity extends AppCompatActivity {
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
    List<NaniesNursesSchedulePojo> al;
    public void serverData(){
        progressDialog = new ProgressDialog(NanniNurseScheduleActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();
        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        uname = sharedPreferences.getString("user_name", "");
        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<NaniesNursesSchedulePojo>> call = service.getNaniesNurseSchedule(uname);
        call.enqueue(new Callback<List<NaniesNursesSchedulePojo>>() {
            @Override
            public void onResponse(Call<List<NaniesNursesSchedulePojo>> call, Response<List<NaniesNursesSchedulePojo>> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(NanniNurseScheduleActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    al = response.body();
                    list_view.setAdapter(new NanniNurseScheduleAdapter(al, NanniNurseScheduleActivity.this));
                }
            }

            @Override
            public void onFailure(Call<List<NaniesNursesSchedulePojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(NanniNurseScheduleActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
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

