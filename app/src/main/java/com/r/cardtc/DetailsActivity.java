package com.r.cardtc;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.inputmethod.InputMethodManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.gms.ads.AdListener;
import com.google.android.gms.ads.AdLoader;
import com.google.android.gms.ads.AdRequest;
import com.google.android.gms.ads.AdView;
import com.google.android.gms.ads.InterstitialAd;
import com.google.android.gms.ads.MobileAds;
import com.google.android.gms.ads.formats.MediaView;
import com.google.android.gms.ads.formats.NativeAd;
import com.google.android.gms.ads.formats.NativeAdOptions;
import com.google.android.gms.ads.formats.UnifiedNativeAd;
import com.google.android.gms.ads.formats.UnifiedNativeAdView;
import com.google.android.gms.ads.initialization.InitializationStatus;
import com.google.android.gms.ads.initialization.OnInitializationCompleteListener;
import com.r.cardtc.Adapter.Adapter;
import com.r.cardtc.Api.MyRetrofit;
import com.r.cardtc.Model.CarList;
import com.r.cardtc.Model.model;

import java.util.ArrayList;
import java.util.Collections;
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

public class DetailsActivity extends AppCompatActivity {
    private static final String Base_Url = "https://obderrorcode.com";
    InterstitialAd mInterstitialAd;
    private AdView mAdView;
    ImageButton btnShare,back;
    Button findCode;
    EditText code ;
    ConstraintLayout resultLayout;
    TextView defination,cause;
    String c,image ;
    UnifiedNativeAd nativeAd;
    ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_details);
        //Add back button------------------
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

        findCode = findViewById(R.id.button);
        code = findViewById(R.id.Code);
        resultLayout = findViewById(R.id.constraintLayoutResult);
        resultLayout.setVisibility(View.GONE);
        defination = findViewById(R.id.defination);
        cause = findViewById(R.id.causes);
        imageView = findViewById(R.id.imageheader);
//        btnShare =  findViewById(R.id.btnShare);
//        back =  findViewById(R.id.back);
//        back.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v)
//            {
//                Intent intent=new Intent(DetailsActivity.this,MainActivity.class);
//                startActivity(intent);
//            }
//        });
//
//        btnShare.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View view) {
//                Intent intent = new Intent(Intent.ACTION_SEND);
//                intent.setType("text/plain");
//                String shareSubject = "";
//                String shareBody = "";
//                intent.putExtra(Intent.EXTRA_SUBJECT, shareSubject);
//                intent.putExtra(Intent.EXTRA_TEXT, shareBody);
//                startActivity(Intent.createChooser(intent,"Share via"));
//            }
//        });

        refreshAd();
        //receive data----------
        Intent i = getIntent();
//        final String selectedKey = i.getStringExtra("id");
        final String selectedKey = "A4BA0E";
        //Receive Image Url
        image = i.getStringExtra("image");
//        Log.e("image",image);

        findCode.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                resultLayout.setVisibility(View.VISIBLE);
                c = code.getText().toString().toLowerCase().trim();
                if (c.isEmpty())
                {
                    defination.setText("P0000 SAE Reserved \n-Usage not allowed except as padding in DTC");
                }
                else
                {

                    //Retrofit ..................................
                    Retrofit retrofit = new Retrofit.Builder()
                            .baseUrl(Base_Url)
                            .addConverterFactory(GsonConverterFactory.create())
                            .build();
                    MyRetrofit myRetrofit = retrofit.create(MyRetrofit.class);
                    Call<List<model>> call = myRetrofit.getDetails(selectedKey,c);
                    call.enqueue(new Callback<List<model>>() {
                        @Override
                        public void onResponse(Call<List<model>> call, Response<List<model>> response) {
                            List<model> list = response.body();

                            if(list.size()==0)
                            {
                                Log.e("Message","Data length is 0");
                            }
                            else
                            {
                                String code = list.get(0).getCode();
                                String details = list.get(0).getDetails();
                                String Cause = list.get(0).getCause();
                                //Set Image Url into imageView
//                            String Url = "http://obderrorcode.com/android";
//                            Glide.with(getApplicationContext()).load(Url+image).into(imageView);
                                //------------------------------------------//
                                defination.setText(code+"\n"+"-"+details);
                                cause.setText(Cause);

                                Log.e("Code",code+"\n"+"-"+details);
                            }

                            showList(response.body());
                        }

                        @Override
                        public void onFailure(Call<List<model>> call, Throwable t) {
                            Log.e("Error",t.getLocalizedMessage());
                        }
                    });

                }



                closeKeyBord();

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
            onBackPressed();
        }
        return super.onOptionsItemSelected(item);
    }

    private void closeKeyBord() {

        View view = this.getCurrentFocus();

        if (view!=null)
        {
            InputMethodManager imm = (InputMethodManager) getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.hideSoftInputFromWindow(view.getWindowToken(),0);
        }

    ScheduledExecutorService scheduledExecutorService = Executors.newSingleThreadScheduledExecutor();
        scheduledExecutorService.scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {


                        if (mInterstitialAd.isLoaded()) {
                            mInterstitialAd.show();
                        } else {
                            Log.d(TAG, "run: ads not showing");
                        }
                        prepareAD();
                    }
                });
            }

        }, 100, 100, TimeUnit.SECONDS);
//        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        MobileAds.initialize(this, new OnInitializationCompleteListener() {
            @Override
            public void onInitializationComplete(InitializationStatus initializationStatus) {
            }
        });

        mAdView = findViewById(R.id.adView);
        AdRequest adRequest = new AdRequest.Builder().build();
        mAdView.loadAd(adRequest);

    }


   public void prepareAD(){
         mInterstitialAd = new InterstitialAd(this);
        mInterstitialAd.setAdUnitId("ca-app-pub-4322875230010734/6366873970");
        mInterstitialAd.loadAd(new AdRequest.Builder().build());

    }
    private void populateUnifiedNativeAdView(UnifiedNativeAd nativeAd, UnifiedNativeAdView adView) {

        adView.setMediaView((MediaView) adView.findViewById(R.id.ad_media));
        adView.setHeadlineView(adView.findViewById(R.id.ad_headline));
        adView.setBodyView(adView.findViewById(R.id.ad_body));
        adView.setCallToActionView(adView.findViewById(R.id.ad_call_to_action));
        adView.setIconView(adView.findViewById(R.id.ad_app_icon));
        adView.setPriceView(adView.findViewById(R.id.ad_price));
        adView.setAdvertiserView(adView.findViewById(R.id.ad_advertiser));
        adView.setStoreView(adView.findViewById(R.id.ad_store));
        adView.setStarRatingView(adView.findViewById(R.id.ad_stars));

        ((TextView)adView.getHeadlineView()).setText(nativeAd.getHeadline());
        adView.getMediaView().setMediaContent(nativeAd.getMediaContent());

        if (nativeAd.getBody() == null) {
            adView.getBodyView().setVisibility(View.INVISIBLE);
        } else {
            adView.getBodyView().setVisibility(View.VISIBLE);
            ((TextView) adView.getBodyView()).setText(nativeAd.getBody());
        }
        if (nativeAd.getCallToAction() == null) {
            adView.getCallToActionView().setVisibility(View.INVISIBLE);
        } else {
            adView.getCallToActionView().setVisibility(View.VISIBLE);
            ((Button) adView.getCallToActionView()).setText(nativeAd.getCallToAction());
        }
        if (nativeAd.getIcon() == null) {
            adView.getIconView().setVisibility(View.GONE);
        } else {
            ((ImageView) adView.getIconView()).setImageDrawable(nativeAd.getIcon().getDrawable());
            adView.getIconView().setVisibility(View.VISIBLE);
        }
        if (nativeAd.getPrice() == null) {
            adView.getPriceView().setVisibility(View.INVISIBLE);
        } else {
            adView.getPriceView().setVisibility(View.VISIBLE);
            ((TextView) adView.getPriceView()).setText(nativeAd.getPrice());
        }
        if (nativeAd.getStore() == null) {
            adView.getStoreView().setVisibility(View.INVISIBLE);
        } else {
            adView.getStoreView().setVisibility(View.VISIBLE);
            ((TextView) adView.getStoreView()).setText(nativeAd.getStore());

        }
        if (nativeAd.getStarRating() == null) {
            adView.getStarRatingView().setVisibility(View.INVISIBLE);
        } else {
            ((RatingBar) adView.getStarRatingView()).setRating(nativeAd.getStarRating().floatValue());
            adView.getStarRatingView().setVisibility(View.VISIBLE);
        }
        if (nativeAd.getAdvertiser() == null) {
            adView.getAdvertiserView().setVisibility(View.INVISIBLE);

        } else {
            ((TextView) adView.getAdvertiserView()).setText(nativeAd.getAdvertiser());
            adView.getAdvertiserView().setVisibility(View.VISIBLE);
        }
        adView.setNativeAd(nativeAd);
    }
    private void refreshAd() {
        AdLoader.Builder builder = new AdLoader.Builder(this, getString(R.string.ADMOB_ADS_UNIT_ID));
        builder.forUnifiedNativeAd(new UnifiedNativeAd.OnUnifiedNativeAdLoadedListener() {
            @Override
            public void onUnifiedNativeAdLoaded(UnifiedNativeAd unifiedNativeAd) {
                if (nativeAd != null) {
                    nativeAd.destroy();
                }
                nativeAd = unifiedNativeAd;
                FrameLayout frameLayout = findViewById(R.id.fl_adplaceholder);

                UnifiedNativeAdView adView = (UnifiedNativeAdView) getLayoutInflater().inflate(R.layout.ad_unified, null);

                populateUnifiedNativeAdView(unifiedNativeAd, adView);
                frameLayout.removeAllViews();
                frameLayout.addView(adView);

            }
        });

        NativeAdOptions adOptions = new NativeAdOptions.Builder().build();
        builder.withNativeAdOptions(adOptions);
        AdLoader adLoader = builder.withAdListener (new AdListener(){

            @Override
            public void onAdFailedToLoad(int i) {

            }
        }).build();
        adLoader.loadAd(new AdRequest.Builder().build());

    }

    }

