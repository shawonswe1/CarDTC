package com.r.cardtc;

import android.app.ProgressDialog;
import android.os.Bundle;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import com.r.cardtc.Adapter.Adapter;
import com.r.cardtc.Api.MyRetrofit;
import com.r.cardtc.Model.CarList;
import com.r.cardtc.Model.GetCar;
import com.r.cardtc.Model.model;

import java.util.ArrayList;
import java.util.List;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class IndiaFragment extends Fragment {
    private static final String Base_Url = "https://obderrorcode.com";
    RecyclerView recyclerView;
    Adapter adapter;
    List<CarList> carLists = new ArrayList<>();
    ProgressDialog progressDialog;

    public IndiaFragment() {

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_india, container, false);

        //RecyclerView & Adapter----------------------------------

        recyclerView = view.findViewById(R.id.indianRecycelrView);
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
        Call<List<GetCar>> call = myRetrofit.getCar("India");
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