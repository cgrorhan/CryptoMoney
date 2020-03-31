package com.cagriorhan.kriptopara.view;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.cagriorhan.kriptopara.R;
import com.cagriorhan.kriptopara.adapter.RecylerViewAdapter;
import com.cagriorhan.kriptopara.model.CryptoModel;
import com.cagriorhan.kriptopara.services.CryptoAPI;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.Scheduler;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.CompositeDisposable;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
    ArrayList<CryptoModel> cryptoModels;
    String BASE_URL="https://api.nomics.com/v1/";
    Retrofit retrofit;
    RecyclerView recyclerView;
    RecylerViewAdapter recylerViewAdapter;
    CompositeDisposable compositeDisposable;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView=findViewById(R.id.recyclerView);

        //https://api.nomics.com/v1/currencies/ticker?key=55b01e9bfb1247f09de0bf57c6fde250

        Gson gson=new GsonBuilder().setLenient().create();

        retrofit=new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .addConverterFactory(GsonConverterFactory.create(gson))
                .build();
        loadData();
    }

    public void loadData(){
        final CryptoAPI cryptoAPI=retrofit.create(CryptoAPI.class);

        compositeDisposable=new CompositeDisposable();
        compositeDisposable.add(cryptoAPI.getData()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(this::handleResponse));


    }
    private void handleResponse(List<CryptoModel> cryptoModelList){

        cryptoModels= new ArrayList<>(cryptoModelList);

        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recylerViewAdapter=new RecylerViewAdapter(cryptoModels);
        recyclerView.setAdapter(recylerViewAdapter);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();

        compositeDisposable.clear();
    }
}














