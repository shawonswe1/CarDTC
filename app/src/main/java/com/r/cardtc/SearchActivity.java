package com.r.cardtc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.cardview.widget.CardView;
import androidx.constraintlayout.widget.ConstraintLayout;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.r.cardtc.Api.MyRetrofit;
import com.r.cardtc.Model.model;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SearchActivity extends AppCompatActivity {

    private static final String Base_Url = "https://obderrorcode.com";
    TextView defination,cause;
    String query;
    ProgressDialog progressDialog;

    ConstraintLayout layout;
    CardView cardView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_search);

        //Add back button------------------
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        layout = findViewById(R.id.layoutDataNotFound);
        layout.setVisibility(View.GONE);
        cardView = findViewById(R.id.cardView);
        cardView.setVisibility(View.GONE);


        Intent intent = getIntent();
        query = intent.getStringExtra("query");
        String code = query.toLowerCase();
        Log.e("My Query",code);

        progressDialog = new ProgressDialog(this);
        progressDialog.setTitle("Searching........");
        progressDialog.setMessage("Please Wait......");
        progressDialog.show();

        defination = findViewById(R.id.defination);
        cause = findViewById(R.id.causes);

        getDetails(code);
    }

    private void getDetails(String code)
    {
        //Retrofit ..................................
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyRetrofit myRetrofit = retrofit.create(MyRetrofit.class);
        Call<List<model>> call = myRetrofit.getSearch(code);
        call.enqueue(new Callback<List<model>>() {
            @Override
            public void onResponse(Call<List<model>> call, Response<List<model>> response) {
                List<model> list = response.body();
                if (list.size()>0)
                {
                    cardView.setVisibility(View.VISIBLE);
                    String code = list.get(0).getCode();
                    String details = list.get(0).getDetails();
                    String Cause = list.get(0).getCause();
                    defination.setText(code+"\n"+"-"+details);
                    cause.setText(Cause);

                }
                else
                {
                    layout.setVisibility(View.VISIBLE);
                    cardView.setVisibility(View.GONE);
                    Log.e("Code","Data Not Found");
                }

                progressDialog.dismiss();
                showList(response.body());
            }

            @Override
            public void onFailure(Call<List<model>> call, Throwable t) {
                Log.e("Error",t.getLocalizedMessage());
            }
        });
    }

    private void showList(List<model> body)
    {
        Log.e("List", String.valueOf(body.size()));

    }

    //Enable Back Button---------------
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {

        if (item.getItemId() == android.R.id.home)
        {
            startActivity(new Intent(SearchActivity.this,MainActivity.class));
//            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }
}