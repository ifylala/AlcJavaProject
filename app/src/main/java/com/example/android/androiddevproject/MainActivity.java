package com.example.android.androiddevproject;

import android.app.LoaderManager;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;

import android.support.v7.app.AppCompatActivity;

import android.view.View;

import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.TextView;


import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity implements LoaderManager.LoaderCallbacks{



    ProgressBar progress;
    ArrayList<UserRecords> arrayList;
    ListView listForMainActivity;

    private static final String GITHUB_URL =
            "https://api.github.com/search/users?q=location:Lagos+language:Java?access_token=<7ce598bc56229ea4c1cc3663c497a48b57791e66>";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        arrayList =new ArrayList<>();


        listForMainActivity=(ListView) findViewById(R.id.list1);

        //Listener for the list to listen to click and launch another activity  using explicit intent
        listForMainActivity.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            public void onItemClick(AdapterView<?> listView,
                                    View v,
                                    int position,
                                    long id) {
                Object o = listForMainActivity.getItemAtPosition(position);

                UserRecords user = (UserRecords) o;

                Intent intent = new Intent(MainActivity.this, UserDetails.class);
                intent.putExtra("userId", user.getLogin());
                intent.putExtra("avatarId", user.getAvatarUrl());
                intent.putExtra("htmlId", user.getHtmlUrl());

                startActivity(intent);
            }


        });

        //Start the asynchronous task of fetching data from  github if there is connection
        isThereConnection();

    }




    public android.content.Loader<String> onCreateLoader(int id , Bundle args){

        return new HttpTaskLoader(MainActivity.this);
    }

    @Override
    public void onLoadFinished(android.content.Loader loader, Object o) {
        progress=(ProgressBar) findViewById(R.id.progress);
        progress.setIndeterminate(false);
        progress.setVisibility(View.GONE);
        String git=(String) o;
        getUserInfoFromJson(git);
        setListAdapter();

    }

    @Override
    public void onLoaderReset(android.content.Loader loader) {

    }





    //parse the Json String and populate the arrayList accordingly
    private void getUserInfoFromJson(String gitHubJson){
        try{
            JSONObject root=new JSONObject(gitHubJson);
            JSONArray items=root.getJSONArray("items");
            for (int i=0;i<items.length();i++){
                JSONObject eachUser=items.getJSONObject(i);
                arrayList.add(new UserRecords(eachUser.getString("login"),eachUser.getString("avatar_url"),
                        eachUser.getString("html_url") ));

            }


        }catch(JSONException e){
            e.printStackTrace();
        }





    }

    //Bind the arrayList  to the listForMainActivity using the custom UserClassAdapter class
    private void setListAdapter(){

        UserRecordsAdapter adapter=new UserRecordsAdapter(getApplicationContext(),R.layout.users_list_items,arrayList);
        listForMainActivity.setAdapter(adapter);


    }

    public void isThereConnection(){

        ConnectivityManager connection=(ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo networkInfo=connection.getActiveNetworkInfo();
        if(networkInfo!=null && networkInfo.isConnected()){

            getLoaderManager().initLoader(1,null,this).forceLoad();
            progress=(ProgressBar) findViewById(R.id.progress);
            progress.setIndeterminate(true);
            progress.setVisibility(View.VISIBLE);
        }
        else{

            TextView noConnectionState=(TextView) findViewById(R.id.noConnectionState);
            noConnectionState.setText("No Internet Connection");
        }


    }

}

