package com.example.babycareproject.activity.activity.activity;

import android.Manifest;
import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Typeface;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


import com.example.babycareproject.activity.activity.models.EditProfilePojo;
import com.bumptech.glide.Glide;
import com.example.babycareproject.R;
import com.example.babycareproject.activity.activity.Utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import pub.devrel.easypermissions.EasyPermissions;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UserEditProfileActivity extends AppCompatActivity implements EasyPermissions.PermissionCallbacks {
    SharedPreferences sharedPreferences;
    String session;
    EditText et_name, et_phno, et_uname, et_password, et_email;
    TextView tv1, tv2, tv4, tv5, tv3;
    Button btn_reg,btn_edit;
    ProgressDialog progressDialog;
    List<EditProfilePojo> a1;
    ResponseData a2;
    ImageView iv_image_view,iv_edit_image;
    String str="http://babysitterprojectapp.com/BabySitter/";
    private static final int REQUEST_GALLERY_CODE = 200;
    private static final int READ_REQUEST_CODE = 300;
    private static final String SERVER_PATH = "http://babysitterprojectapp.com/";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usereditprofile);
        sharedPreferences = getSharedPreferences(Utils.SHREF, Context.MODE_PRIVATE);

        session = sharedPreferences.getString("user_name", "def-val");
        //Toast.makeText(AdminEditProfileActivity.this,session,Toast.LENGTH_SHORT).show();

        getSupportActionBar().setTitle("Edit Profile");
        getSupportActionBar().setHomeButtonEnabled(true);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        tv1 = (TextView) findViewById(R.id.tv1);
        tv2 = (TextView) findViewById(R.id.tv2);
        tv4 = (TextView) findViewById(R.id.tv4);
        tv5 = (TextView) findViewById(R.id.tv5);
        tv3 = (TextView) findViewById(R.id.tv3);


        btn_reg = (Button) findViewById(R.id.btn_reg);
        btn_edit= (Button) findViewById(R.id.btn_edit);
        et_name = (EditText) findViewById(R.id.et_name);
        et_phno = (EditText) findViewById(R.id.et_phno);
        et_uname = (EditText) findViewById(R.id.et_uname);
        et_password = (EditText) findViewById(R.id.et_password);
        et_email = (EditText) findViewById(R.id.et_email);
        iv_image_view=(ImageView)findViewById(R.id.iv_image_view);
        iv_edit_image=(ImageView)findViewById(R.id.iv_edit_image);
        et_uname.setText(session);
        et_uname.setFocusable(false);

        btn_edit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                Intent openGalleryIntent = new Intent(Intent.ACTION_PICK);
                openGalleryIntent.setType("image/*");
                startActivityForResult(openGalleryIntent, REQUEST_GALLERY_CODE);

            }
        });

        Typeface fontstyle = Typeface.createFromAsset(getApplicationContext().getAssets(), "fonts/Lato-Medium.ttf");
        tv1.setTypeface(fontstyle);
        tv2.setTypeface(fontstyle);
        tv4.setTypeface(fontstyle);
        tv5.setTypeface(fontstyle);
        btn_reg.setTypeface(fontstyle);
        et_name.setTypeface(fontstyle);
        et_phno.setTypeface(fontstyle);
        et_uname.setTypeface(fontstyle);
        et_password.setTypeface(fontstyle);
        et_email.setTypeface(fontstyle);
        tv3.setTypeface(fontstyle);


        progressDialog = new ProgressDialog(UserEditProfileActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<List<EditProfilePojo>> call = service.getUserProfile(session);

        call.enqueue(new Callback<List<EditProfilePojo>>() {
            @Override
            public void onResponse(Call<List<EditProfilePojo>> call, Response<List<EditProfilePojo>> response) {

                progressDialog.dismiss();
                a1 = response.body();
                // Toast.makeText(getApplicationContext(),""+response.body().size(),Toast.LENGTH_LONG).show();
                EditProfilePojo user = a1.get(0);

                et_name.setText(user.getName());

                et_phno.setText(user.getPhone());

                et_email.setText(user.getEmailid());

                et_password.setText(user.getPwd());

                Glide.with(UserEditProfileActivity.this).load(str+user.getImg_url()).into(iv_image_view);
                Glide.with(UserEditProfileActivity.this).load(str+user.getImg_url()).into(iv_edit_image);

            }

            @Override
            public void onFailure(Call<List<EditProfilePojo>> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(UserEditProfileActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

        btn_reg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                uploadImageToServer();

            }
        });

    }

    /*private void submitData() {
        String name = et_name.getText().toString();
        String email = et_email.getText().toString();
        String phno = et_phno.getText().toString();
        String pwd = et_password.getText().toString();

        progressDialog = new ProgressDialog(UserEditProfileActivity.this);
        progressDialog.setMessage("Loading....");
        progressDialog.show();

        session = sharedPreferences.getString("user_name", "def-val");
        Toast.makeText(UserEditProfileActivity.this, session, Toast.LENGTH_SHORT).show();


        EndPointUrl service = RetrofitInstance.getRetrofitInstance().create(EndPointUrl.class);
        Call<ResponseData> call = service.update_user_profile(name, phno, email, pwd, session);

        call.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {

                progressDialog.dismiss();
                a2 = response.body();
                EditProfilePojo user = a1.get(0);

                et_name.setText(user.getName());

                et_phno.setText(user.getPhone());

                et_email.setText(user.getEmailid());

                et_password.setText(user.getPwd());

                if (response.body().status.equals("true")) {
                    Toast.makeText(UserEditProfileActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                    finish();
                } else {
                    Toast.makeText(UserEditProfileActivity.this, response.body().message, Toast.LENGTH_LONG).show();
                }

            }

            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                progressDialog.dismiss();
                Toast.makeText(UserEditProfileActivity.this, "" + t.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });

    }*/
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        EasyPermissions.onRequestPermissionsResult(requestCode, permissions, grantResults, UserEditProfileActivity.this);
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == REQUEST_GALLERY_CODE && resultCode == Activity.RESULT_OK){
            uri = data.getData();
            Bitmap bitmap = null;
            try {
                bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), uri);
            } catch (IOException e) {
                e.printStackTrace();
            }
            iv_edit_image.setImageBitmap(bitmap);
            if(EasyPermissions.hasPermissions(this, Manifest.permission.READ_EXTERNAL_STORAGE)) {
                String filePath = getRealPathFromURIPath(uri, UserEditProfileActivity.this);
                //Toast.makeText(getApplicationContext(),filePath,Toast.LENGTH_SHORT).show();
                file = new File(filePath);

            }else{
                EasyPermissions.requestPermissions(this, getString(R.string.read_file), READ_REQUEST_CODE, Manifest.permission.READ_EXTERNAL_STORAGE);
            }
        }
    }
    private String getRealPathFromURIPath(Uri contentURI, Activity activity) {
        Cursor cursor = activity.getContentResolver().query(contentURI, null, null, null, null);
        if (cursor == null) {
            return contentURI.getPath();
        } else {
            cursor.moveToFirst();
            int idx = cursor.getColumnIndex(MediaStore.Images.ImageColumns.DATA);
            return cursor.getString(idx);
        }
    }
    File file;
    @Override
    public void onPermissionsGranted(int requestCode, List<String> perms) {
        if(uri != null){
            String filePath = getRealPathFromURIPath(uri, UserEditProfileActivity.this);
            file = new File(filePath);
            // uploadImageToServer();
        }
    }
    @Override
    public void onPermissionsDenied(int requestCode, List<String> perms) {


    }
    ProgressDialog pd;
    private Uri uri;
    private void uploadImageToServer(){
        pd=new ProgressDialog(UserEditProfileActivity.this);
        pd.setTitle("Loading");
        pd.show();
        Map<String, String> map = new HashMap<>();
        map.put("name",et_name.getText().toString());
        map.put("phone",et_phno.getText().toString());
        map.put("emailid",et_email.getText().toString());
        map.put("pwd1",et_password.getText().toString());
        map.put("uname1",session);


        RequestBody mFile = RequestBody.create(MediaType.parse("image/*"), file);
        MultipartBody.Part fileToUpload = MultipartBody.Part.createFormData("file", file.getName(), mFile);
        RequestBody filename = RequestBody.create(MediaType.parse("text/plain"), file.getName());
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(SERVER_PATH)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        EndPointUrl uploadImage = retrofit.create(EndPointUrl.class);
        Call<ResponseData> fileUpload = uploadImage.update_user_profile(fileToUpload, map);
        fileUpload.enqueue(new Callback<ResponseData>() {
            @Override
            public void onResponse(Call<ResponseData> call, Response<ResponseData> response) {
                pd.dismiss();
                Toast.makeText(UserEditProfileActivity.this, "Data is submitted successfully. ", Toast.LENGTH_LONG).show();
                finish();
            }
            @Override
            public void onFailure(Call<ResponseData> call, Throwable t) {
                pd.dismiss();
                Toast.makeText(UserEditProfileActivity.this, "Error"+t.getMessage(), Toast.LENGTH_LONG).show();
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

