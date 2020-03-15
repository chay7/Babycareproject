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


import com.example.babycareproject.activity.activity.adapter.NanniesAdapter;
import com.example.babycareproject.activity.activity.models.NanniesPojo;
import com.example.babycareproject.R;
import com.example.babycareproject.activity.activity.Utils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class NanniesActivity extends AppCompatActivity {
    ListView list_view;
    ProgressDialog progressDialog;
    List<NanniesPojo> a1;
    SharedPreferences sharedPreferences;
    String uname;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nannies);

        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);
        uname = sharedPreferences.getString("user_name", "");

        getSupportActionBar().setTitle("Nanies");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        list_view=(ListView)findViewById(R.id.list_view);
        a1= new ArrayList<>();
        serverData();
    }
       public void serverData(){
        progressDialog = new ProgressDialog(NanniesActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<NanniesPojo>> call = service.getNanies();
        call.enqueue(new Callback<List<NanniesPojo>>() {
            @Override
            public void onResponse(Call<List<NanniesPojo>> call, Response<List<NanniesPojo>> response) {
                progressDialog.dismiss();
                if(response.body()==null){
                    Toast.makeText(NanniesActivity.this,"No data found",Toast.LENGTH_SHORT).show();
                }else {
                    a1 = response.body();
                    list_view.setAdapter(new NanniesAdapter(a1, NanniesActivity.this));
                }
            }

            @Override
            public void onFailure(Call<List<NanniesPojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(NanniesActivity.this, "Something went wrong...Please try later!", Toast.LENGTH_SHORT).show();
            }
        });
    }
//    public boolean onCreateOptionsMenu(Menu menu) {
//        getMenuInflater().inflate(R.menu.menu, menu);//Menu Resource, Menu
//        return true;
//    }
    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
//            case R.id.idlogout:
//                // Toast.makeText(getApplicationContext(),"logout Selected",Toast.LENGTH_LONG).show();
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


