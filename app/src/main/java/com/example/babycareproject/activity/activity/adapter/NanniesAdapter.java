package com.example.babycareproject.activity.activity.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;


import com.example.babycareproject.activity.activity.activity.NaniBookingActivity;
import com.example.babycareproject.activity.activity.activity.ViewReviewRatingActivity;
import com.example.babycareproject.activity.activity.models.NanniesPojo;
import com.bumptech.glide.Glide;
import com.example.babycareproject.R;

import java.util.List;

public class NanniesAdapter extends BaseAdapter {
    List<NanniesPojo> ar;
    Context cnt;
    public NanniesAdapter(List<NanniesPojo> ar, Context cnt)
    {
        this.ar=ar;
        this.cnt=cnt;
    }
    @Override
    public int getCount() {
        return ar.size();
    }

    @Override
    public Object getItem(int i) {
        return i;
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(final int pos, View view, ViewGroup viewGroup)
    {
        LayoutInflater obj1 = (LayoutInflater)cnt.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        View obj2=obj1.inflate(R.layout.list_nannies,null);

        ImageView ivImg=(ImageView)obj2.findViewById(R.id.iv);
        Glide.with(cnt)
                .load("http://babysitterprojectapp.com/BabySitter/"+ar.get(pos).getImg_url())
                .into(ivImg);
        TextView tv_name=(TextView)obj2.findViewById(R.id.tv_name);
        tv_name.setText("Name  :"+ar.get(pos).getName());

        TextView tv_phno=(TextView)obj2.findViewById(R.id.tv_phno);
        tv_phno.setText("Phone No  :"+ar.get(pos).getPhone());

        TextView tv_emailid=(TextView)obj2.findViewById(R.id.tv_emailid);
        tv_emailid.setText("Email ID  :"+ar.get(pos).getEmailid());

        TextView tv_locoation=(TextView)obj2.findViewById(R.id.tv_locoation);
        tv_locoation.setText("Locoation  :"+ar.get(pos).getLocation());

        TextView tv_exper=(TextView)obj2.findViewById(R.id.tv_exper);
        tv_exper.setText("Experience (Years) :"+ar.get(pos).getExperience());

       /* TextView tv_rating=(TextView)obj2.findViewById(R.id.tv_rating);
        tv_rating.setText("Rating  :"+ar.get(pos).getRating());*/


        RatingBar ratingBar=(RatingBar)obj2.findViewById(R.id.ratingBar);
        ratingBar.setRating(Float.parseFloat(ar.get(pos).getRating()));


        Button btn_book=(Button)obj2.findViewById(R.id.btn_book);
        Button btn_review_rating=(Button)obj2.findViewById(R.id.btn_review_rating);
        btn_review_rating.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(cnt, ViewReviewRatingActivity.class);
                intent.putExtra("uname",ar.get(pos).getUname());
                cnt.startActivity(intent);

            }
        });

        btn_book.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(cnt, NaniBookingActivity.class);
                intent.putExtra("ID",ar.get(pos).getId());
                intent.putExtra("name",ar.get(pos).getName());
                intent.putExtra("phno",ar.get(pos).getPhone());
                intent.putExtra("Email",ar.get(pos).getEmailid());
                intent.putExtra("Uname",ar.get(pos).getUname());
                intent.putExtra("Experience",ar.get(pos).getExperience());
                intent.putExtra("Locoation",ar.get(pos).getLocation());
                intent.putExtra("User_type",ar.get(pos).getUser_type());
                intent.putExtra("Rating",ar.get(pos).getRating());

                cnt.startActivity(intent);

            }
        });

       

        return obj2;

    }
  
}
