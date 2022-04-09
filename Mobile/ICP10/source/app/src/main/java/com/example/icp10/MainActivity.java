package com.example.icp10;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {

    TextView textView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        textView = findViewById(R.id.textview);

        //Github API
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://api.github.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        ApiCollections apiCollections = retrofit.create(ApiCollections.class);

        //Getting github Users Data
        Call<List<GithubUser>> usersCall = apiCollections.getData();

        usersCall.enqueue(new Callback<List<GithubUser>>() {
            @Override
            public void onResponse(Call<List<GithubUser>> call, Response<List<GithubUser>> response) {
                if (response.isSuccessful()) {
                    List<GithubUser> allUsers = response.body();
                    for (GithubUser user: allUsers) {

                        //Variable to store each user data
                        String userData = "";

                        //appending User data
                        userData += "User_ID: "+ user.getId() +"\n";
                        userData += "User_Name: "+ user.getUsername() + "\n\n";

                        //appending User data to dissplay view
                        textView.append(userData);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GithubUser>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "data Failed", Toast.LENGTH_SHORT);
            }
        });

    }
}
