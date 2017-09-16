package com.example.android.androiddevproject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.ShareCompat;
import android.support.v7.app.AppCompatActivity;
import android.text.util.Linkify;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.TextView;


import com.squareup.picasso.Picasso;

import de.hdodenhof.circleimageview.CircleImageView;


/**
 * Created by SEAMFIX PC on 14/09/2017.
 */

public class InfoActivity extends AppCompatActivity {

    private String login;
    private String avatar_url;
    private String html_url;

    private TextView username;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_info);

        //Displaying of the username
        username = (TextView)findViewById(R.id.text1);

        String data = getIntent().getExtras().getString("login");
        username.setText("GitHub User: " + data);

        //Creating the Link to users Github Page
        String html = getIntent().getExtras().getString("html_url");

        TextView textView = (TextView) findViewById(R.id.textlink);
        textView.setText(html);
        Linkify.addLinks(textView, Linkify.WEB_URLS);

        //Creating User Profile Picture

        CircleImageView imageView=(CircleImageView) findViewById(R.id.image1);
        Picasso.with(getBaseContext()).load(avatar_url).placeholder(R.drawable.images).resize(80,80).into(imageView);


    }

    private Intent createShareForecastIntent() {
        String data2 = getIntent().getExtras().getString("html_url");
        String data = getIntent().getExtras().getString("login");
        Intent shareIntent = ShareCompat.IntentBuilder.from(this)
                .setType("text/plain")
                .setText("Check out this awesome developer @" + data + ", " + data2)
                .getIntent();
        return shareIntent;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.info, menu);
        MenuItem menuItem = menu.findItem(R.id.action_share);
        menuItem.setIntent(createShareForecastIntent());
        return true;
    }
}

