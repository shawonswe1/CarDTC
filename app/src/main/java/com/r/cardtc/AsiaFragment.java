package com.r.cardtc;

import android.app.ProgressDialog;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.r.cardtc.Adapter.Adapter;
import com.r.cardtc.Api.MyRetrofit;
import com.r.cardtc.Model.CarList;
import com.r.cardtc.Model.GetCar;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.content.ContentValues.TAG;


public class AsiaFragment extends Fragment {
    private static final String Base_Url = "https://obderrorcode.com";


    RecyclerView recyclerView;
    Adapter adapter;
    List<CarList> carLists = new ArrayList<>();

    ProgressDialog progressDialog;
    public AsiaFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_asia, container, false);

        //RecyclerView & Adapter----------------------------------

        recyclerView = view.findViewById(R.id.ashianRecycelrView);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        progressDialog = new ProgressDialog(getContext());
        progressDialog.setTitle("Car List");
        progressDialog.setMessage("Please wait............");
        progressDialog.show();

        GetIndianCar();

        return view;
    }

    private void GetIndianCar()
    {
        //Retrofit ..................................
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Base_Url)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        MyRetrofit myRetrofit = retrofit.create(MyRetrofit.class);
        Call<List<GetCar>> call = myRetrofit.getCar("Ashiya");
        call.enqueue(new Callback<List<GetCar>>() {
            @Override
            public void onResponse(Call<List<GetCar>> call, Response<List<GetCar>> response) {
                showList(response.body());
            }

            @Override
            public void onFailure(Call<List<GetCar>> call, Throwable t) {

                Log.e("Error",t.getLocalizedMessage());
            }
        });
    }

    private void showList(List<GetCar> body) {
        Log.e("List",""+body.size());
        progressDialog.dismiss();
        adapter = new Adapter(getContext(),body);
        recyclerView.setAdapter(adapter);
    }
}