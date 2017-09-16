package com.example.android.androiddevproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;

/**
 * Created by SEAMFIX PC on 14/09/2017.
 */

public class UserDetails extends AppCompatActivity {

    public static String userName;
    public static String avatarUrl;
    public static String htmlUrl;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        setContentView(R.layout.activity_info);


        updateUi();




    }

    public void shareUrl(View view){
        TextView html=(TextView) findViewById(R.id.textlink);

        String message="Check out this awesome developer @ "+userName+" "+ html.getText();
        Intent intents=new Intent(Intent.ACTION_SEND);
        intents.setType("text/plain");
        intents.putExtra(Intent.EXTRA_TEXT,message);
        Intent chooseIntent=Intent.createChooser(intents,"Share");
        startActivity(chooseIntent);



    }

    public void updateUi(){
        userName=getIntent().getStringExtra("userId");
        avatarUrl=getIntent().getStringExtra("avatarId");
        htmlUrl=getIntent().getStringExtra("htmlId");
        CircleImageView imageView=(CircleImageView) findViewById(R.id.image1);
        Picasso.with(getBaseContext()).load(avatarUrl).placeholder(R.drawable.images).resize(80,80).into(imageView);
        TextView nameView =(TextView) findViewById(R.id.text1);


        nameView.setText(userName);

        TextView htmlView=(TextView) findViewById(R.id.textlink);


        htmlView.setText(htmlUrl);


    }
}
